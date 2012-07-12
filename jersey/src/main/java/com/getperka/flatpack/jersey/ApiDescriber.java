/*
 * #%L
 * FlatPack Jersey integration
 * %%
 * Copyright (C) 2012 Perka Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.getperka.flatpack.jersey;

import static com.getperka.flatpack.util.FlatPackCollections.mapForIteration;
import static com.getperka.flatpack.util.FlatPackCollections.mapForLookup;
import static com.getperka.flatpack.util.FlatPackCollections.setForIteration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriBuilder;

import com.getperka.flatpack.FlatPack;
import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.TypeReference;
import com.getperka.flatpack.client.dto.ApiDescription;
import com.getperka.flatpack.client.dto.EndpointDescription;
import com.getperka.flatpack.client.dto.EntityDescription;
import com.getperka.flatpack.client.dto.ParameterDescription;
import com.getperka.flatpack.ext.Property;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.ext.TypeContext;
import com.getperka.flatpack.util.FlatPackCollections;
import com.getperka.flatpack.util.FlatPackTypes;
import com.google.gson.Gson;

/**
 * Analyzes a FlatPack instance and a API methods to produce an {@link ApiDescription}.
 */
public class ApiDescriber {
  private static final Pattern linkPattern =
      Pattern.compile("[{]@link[\\s]+([^\\s}]+)([^}]*)?[}]");

  private final Set<String> allRoles = Collections.singleton("*");
  private final Collection<Method> apiMethods;
  private final Map<Package, Map<String, String>> docStringsByPackage = mapForLookup();
  private final Map<String, String> classesToPayloadNames = mapForLookup();
  private final TypeContext ctx;
  private final Map<Class<? extends HasUuid>, EntityDescription> descriptions = mapForIteration();
  private Set<Class<? extends HasUuid>> entitiesToExtract = setForIteration();
  private Set<Class<? extends HasUuid>> ignoreSubtypesOf = Collections.emptySet();
  private Set<String> limitRoles;
  private final Map<String, Class<? extends HasUuid>> payloadNamesToClasses = mapForLookup();
  private final Map<Class<? extends HasUuid>, Set<Class<? extends HasUuid>>> typeHierarchy = mapForLookup();

  public ApiDescriber(FlatPack flatpack, Collection<Method> apiMethods) {
    this.apiMethods = apiMethods;
    ctx = flatpack.getTypeContext();
  }

  /**
   * Analyze the Methods provided to the constructor and produce an ApiDescription.
   */
  public ApiDescription describe() throws IOException {
    ApiDescription description = new ApiDescription();

    List<EntityDescription> entities = new ArrayList<EntityDescription>();
    description.setEntities(entities);

    // Create a map of simple class names to payload names for resolving @link tags
    for (Class<? extends HasUuid> clazz : ctx.getEntityTypes()) {
      classesToPayloadNames.put(clazz.getCanonicalName(), ctx.getPayloadName(clazz));
      payloadNamesToClasses.put(ctx.getPayloadName(clazz), clazz);

      // Popuplate the typeHiererchy map
      for (Class<?> superclass = clazz.getSuperclass(); superclass != null
        && HasUuid.class.isAssignableFrom(superclass); superclass = superclass.getSuperclass()) {
        Class<? extends HasUuid> superUuid = superclass.asSubclass(HasUuid.class);
        Set<Class<? extends HasUuid>> set = typeHierarchy.get(superUuid);
        if (set == null) {
          set = setForIteration();
          typeHierarchy.put(superUuid, set);
        }
        set.add(clazz);
      }
    }

    // Extract API endpoints
    List<EndpointDescription> endpoints = new ArrayList<EndpointDescription>();
    description.setEndpoints(endpoints);

    for (Method method : apiMethods) {
      EndpointDescription desc = describeEndpoint(method);
      if (desc != null) {
        endpoints.add(desc);
      }
    }

    // Extract all entities
    do {
      Set<Class<? extends HasUuid>> toProcess = entitiesToExtract;
      entitiesToExtract = setForIteration();
      for (Class<? extends HasUuid> clazz : toProcess) {
        entities.add(describeEntity(clazz));
      }
    } while (!entitiesToExtract.isEmpty());

    return description;
  }

  public ApiDescriber ignoreSubtypesOf(Collection<? extends Class<? extends HasUuid>> toIgnore) {
    ignoreSubtypesOf = setForIteration();
    ignoreSubtypesOf.addAll(toIgnore);
    return this;
  }

  /**
   * Only extract items that may be accessesd by the given roles.
   */
  public ApiDescriber limitRoles(Collection<String> limitRoles) {
    this.limitRoles = setForIteration();
    this.limitRoles.addAll(limitRoles);
    return this;
  }

  private EndpointDescription describeEndpoint(Method method) throws IOException {
    Class<?> declaringClass = method.getDeclaringClass();

    // Determine the HTTP access method
    String methodName = null;
    for (Annotation annotation : method.getAnnotations()) {
      // The HTTP method is declared as a meta-annotation on the @GET, @PUT, etc. annotation
      HttpMethod methodAnnotation = annotation.annotationType().getAnnotation(HttpMethod.class);
      if (methodAnnotation != null) {
        methodName = methodAnnotation.value();
      }
    }
    if (methodName == null) {
      return null;
    }

    // Create a key for looking up the method's doc strings
    String methodKey;
    {
      StringBuilder methodKeyBuilder = new StringBuilder(declaringClass.getName())
          .append(":").append(method.getName()).append("(");
      boolean needsComma = false;
      for (Class<?> clazz : method.getParameterTypes()) {
        if (needsComma) {
          methodKeyBuilder.append(", ");
        } else {
          needsComma = true;
        }
        methodKeyBuilder.append(clazz.getName());
      }
      methodKeyBuilder.append(")");
      methodKey = methodKeyBuilder.toString();
    }

    // Determine the endpoint path
    UriBuilder builder = UriBuilder.fromPath("");
    if (declaringClass.isAnnotationPresent(Path.class)) {
      builder.path(declaringClass);
    }
    if (method.isAnnotationPresent(Path.class)) {
      builder.path(method);
    }
    // This path has special characters URL-escaped, so we'll undo the escaping
    String path = builder.build().toString();
    path = URLDecoder.decode(path, "UTF8");

    // Build the EndpointDescription
    EndpointDescription desc = new EndpointDescription(methodName, path);
    List<ParameterDescription> pathParams = new ArrayList<ParameterDescription>();
    List<ParameterDescription> queryParams = new ArrayList<ParameterDescription>();
    Annotation[][] annotations = method.getParameterAnnotations();
    java.lang.reflect.Type[] parameters = method.getGenericParameterTypes();
    for (int i = 0, j = parameters.length; i < j; i++) {
      Type paramType = reference(parameters[i]);
      if (annotations[i].length == 0) {
        // Assume that an un-annotated parameter is the main entity type
        desc.setEntity(paramType);
      } else {
        for (Annotation annotation : annotations[i]) {
          if (PathParam.class.equals(annotation.annotationType())) {
            PathParam pathParam = (PathParam) annotation;
            ParameterDescription param = new ParameterDescription(desc, pathParam.value(),
                paramType);
            String docString = getDocStrings(declaringClass).get(methodKey + "[" + i + "]");
            param.setDocString(replaceLinks(docString));
            pathParams.add(param);
          } else if (QueryParam.class.equals(annotation.annotationType())) {
            QueryParam queryParam = (QueryParam) annotation;
            ParameterDescription param = new ParameterDescription(desc, queryParam.value(),
                paramType);
            String docString = getDocStrings(declaringClass).get(methodKey + "[" + i + "]");
            param.setDocString(replaceLinks(docString));
            queryParams.add(param);
          }
        }
      }
    }

    // If the returned entity type is described, extract the information
    FlatPackResponse responseAnnotation = method.getAnnotation(FlatPackResponse.class);
    if (responseAnnotation != null) {
      Type returnType = reference(FlatPackTypes.createType(responseAnnotation.value()));
      desc.setReturnType(returnType);
    }

    String docString = getDocStrings(declaringClass).get(methodKey);
    desc.setDocString(replaceLinks(docString));
    desc.setPathParameters(pathParams.isEmpty() ? null : pathParams);
    desc.setRoleNames(extractRoles(method));
    desc.setQueryParameters(queryParams.isEmpty() ? null : queryParams);
    return desc;
  }

  private EntityDescription describeEntity(Class<? extends HasUuid> clazz) throws IOException {
    if (descriptions.containsKey(clazz)) {
      return descriptions.get(clazz);
    }
    // Use a mutable property list to support filtering below
    EntityDescription entity = new EntityDescription(ctx.getPayloadName(clazz),
        new ArrayList<Property>(ctx.extractProperties(clazz)));
    descriptions.put(clazz, entity);

    // Link the supertype
    if (HasUuid.class.isAssignableFrom(clazz.getSuperclass())) {
      entity.setSupertype(describeEntity(clazz.getSuperclass().asSubclass(HasUuid.class)));
    }

    // Attach the docstring
    Map<String, String> strings = getDocStrings(clazz);
    String docString = strings.get(clazz.getName());
    if (docString != null) {
      entity.setDocString(replaceLinks(docString));
    }

    // Iterate over the properties
    for (Iterator<Property> it = entity.getProperties().iterator(); it.hasNext();) {
      Property prop = it.next();

      // Filter by roles
      if (limitRoles != null) {
        if (!prop.mayGet(limitRoles) && !prop.maySet(limitRoles)) {
          it.remove();
          continue;
        }
      }

      // Record a reference to (possibly) an entity type
      reference(prop.getType());

      // The property set include all properties defined in supertypes
      Class<?> declaringClass = prop.getGetter().getDeclaringClass();
      strings = getDocStrings(declaringClass);
      if (strings != null) {
        String memberName = declaringClass.getName() + ":" + prop.getGetter().getName() + "()";
        prop.setDocString(replaceLinks(strings.get(memberName)));
      }
    }
    return entity;
  }

  private Set<String> extractRoles(Method method) {
    RolesAllowed annotation = method.getAnnotation(RolesAllowed.class);
    if (annotation == null) {
      return allRoles;
    }
    Set<String> toReturn = FlatPackCollections.setForIteration();
    toReturn.addAll(Arrays.asList(annotation.value()));
    return Collections.unmodifiableSet(toReturn);
  }

  /**
   * Load the {@code package.json} file from the class's package.
   */
  private Map<String, String> getDocStrings(Class<?> clazz) throws IOException {
    Map<String, String> toReturn = docStringsByPackage.get(clazz.getPackage());
    if (toReturn != null) {
      return toReturn;
    }

    InputStream stream = clazz.getResourceAsStream("package.json");
    if (stream == null) {
      toReturn = Collections.emptyMap();
    } else {
      Reader reader = new InputStreamReader(stream, FlatPackTypes.UTF8);
      toReturn = new Gson().fromJson(reader, new TypeReference<Map<String, String>>() {}.getType());
      reader.close();
    }

    docStringsByPackage.put(clazz.getPackage(), toReturn);
    return toReturn;
  }

  private void reference(Class<? extends HasUuid> clazz) {
    if (clazz != null && !descriptions.containsKey(clazz)) {
      entitiesToExtract.add(clazz);

      if (ignoreSubtypesOf.contains(clazz)) {
        return;
      }

      Set<Class<? extends HasUuid>> subtypes = typeHierarchy.get(clazz);
      if (subtypes != null) {
        for (Class<? extends HasUuid> subtype : subtypes) {
          reference(subtype);
        }
      }
    }
  }

  /**
   * Convert a reflection Type into FlatPack's typesystem. This method will also record any
   * referenced entities.
   */
  private Type reference(java.lang.reflect.Type t) {
    Type type = ctx.getCodex(t).describe();
    reference(type);
    return type;
  }

  /**
   * Traverse a type, looking for references to entities. This should be a visitor.
   */
  private void reference(Type type) {
    if (type.getName() != null) {
      Class<? extends HasUuid> clazz = payloadNamesToClasses.get(type.getName());
      reference(clazz);
    }
    if (type.getListElement() != null) {
      reference(type.getListElement());
    }
    if (type.getMapKey() != null) {
      reference(type.getMapKey());
    }
    if (type.getMapValue() != null) {
      reference(type.getMapValue());
    }
  }

  /**
   * Replace any {@literal {@link} tags in a docString with something easier for the viewer app to
   * deal with.
   */
  private String replaceLinks(String docString) {
    if (docString == null) {
      return null;
    }

    // Matcher uses StringBuffer and not StringBuilder
    StringBuffer sb = new StringBuffer();
    Matcher m = linkPattern.matcher(docString);
    while (m.find()) {
      String name = m.group(1);
      // TODO: Support field references, API method references
      String payloadName = classesToPayloadNames.get(name);
      if (payloadName == null) {
        // Just append the original text
        if (m.group(2) != null) {
          m.appendReplacement(sb, m.group(2));
        } else {
          m.appendReplacement(sb, m.group(1));
        }
      } else {
        /*
         * This is colluding with the viewer app, but it's much simpler than re-implementing another
         * {@link} replacement in the viewer.
         */
        String displayString = m.group(2) == null ? payloadName : m.group(2);
        m.appendReplacement(sb, "<entityReference payloadName='" + payloadName + "'>"
          + displayString + "</entityReference>");
      }
    }
    m.appendTail(sb);
    return sb.toString();
  }
}
