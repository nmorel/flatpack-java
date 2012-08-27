/*
 * #%L
 * FlatPack Automatic Source Tool
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
package com.getperka.flatpack.fast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.AttributeRenderer;
import org.stringtemplate.v4.AutoIndentWriter;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

import com.getperka.cli.flags.Flag;
import com.getperka.flatpack.BaseHasUuid;
import com.getperka.flatpack.Embedded;
import com.getperka.flatpack.FlatPack;
import com.getperka.flatpack.FlatPackEntity;
import com.getperka.flatpack.InheritPrincipal;
import com.getperka.flatpack.JsonTypeName;
import com.getperka.flatpack.PersistenceAware;
import com.getperka.flatpack.PostUnpack;
import com.getperka.flatpack.SparseCollection;
import com.getperka.flatpack.SuppressDefaultValue;
import com.getperka.flatpack.TypeReference;
import com.getperka.flatpack.TypeSource;
import com.getperka.flatpack.client.FlatPackRequest;
import com.getperka.flatpack.client.Request;
import com.getperka.flatpack.client.dto.ApiDescription;
import com.getperka.flatpack.client.dto.EndpointDescription;
import com.getperka.flatpack.client.dto.EntityDescription;
import com.getperka.flatpack.client.dto.ParameterDescription;
import com.getperka.flatpack.client.impl.ApiBase;
import com.getperka.flatpack.client.impl.BasePersistenceAware;
import com.getperka.flatpack.client.impl.ConnectionRequestBase;
import com.getperka.flatpack.client.impl.FlatPackRequestBase;
import com.getperka.flatpack.collections.DirtyFlag;
import com.getperka.flatpack.ext.Property;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.ext.TypeHint;
import com.getperka.flatpack.util.FlatPackCollections;
import com.getperka.flatpack.util.FlatPackTypes;
import com.google.gson.stream.JsonReader;

/**
 * Generates simple Java POJO representations of a FlatPack API.
 */
public class JavaDialect implements Dialect {
  @Flag(tag = "apiIsPublic",
      help = "If false, the generated Api class will have package visibility",
      defaultValue = "true")
  static Boolean apiIsPublic;

  @Flag(tag = "packageName",
      help = "The name of the package that generated sources should belong to",
      defaultValue = "com.getperka.fast")
  static String packageName;

  @Flag(tag = "stripPathSegments",
      help = "The number of path segments to strip when creating method names", defaultValue = "0")
  static Integer stripPathSegments;

  @Flag(
      tag = "baseTypeArray",
      help = "A file containing a JSON array of payload names for DTOs that should be generated as a base type")
  static File baseTypeArrayFile;

  @Flag(tag = "typePrefix", help = "A prefix to apply to all generated type names",
      defaultValue = "")
  static String typePrefix = "";

  private static final Charset UTF8 = Charset.forName("UTF8");
  private static final List<Class<?>> WELL_KNOWN_TYPES = Arrays.<Class<?>> asList(
      ApiBase.class, Arrays.class, ConnectionRequestBase.class, Collections.class, DirtyFlag.class,
      Embedded.class, FlatPack.class, FlatPackCollections.class, FlatPackEntity.class,
      FlatPackRequest.class, FlatPackRequestBase.class, FlatPackTypes.class, HashSet.class,
      HttpURLConnection.class, InheritPrincipal.class, IOException.class, JsonTypeName.class,
      PermitAll.class, PersistenceAware.class, PostUnpack.class, Request.class, RolesAllowed.class,
      Set.class, SparseCollection.class, SuppressDefaultValue.class, TypeReference.class,
      TypeSource.class);

  protected static String upcase(String typeName) {
    return Character.toUpperCase(typeName.charAt(0)) + typeName.substring(1);
  }

  protected final Set<String> baseTypes = new HashSet<String>();
  private final Logger logger = LoggerFactory.getLogger(getClass());
  /**
   * Used at the end of the code-generation process to emit referenced enum values.
   */
  protected final Set<Type> usedEnums = new LinkedHashSet<Type>();

  @Override
  public void generate(ApiDescription api, File outputDir) throws IOException {
    STGroup group = loadGroup("java.stg");
    loadConcreteTypeMap();

    File packageDir = new File(outputDir, packageName.replace('.', '/'));
    if (!packageDir.isDirectory() && !packageDir.mkdirs()) {
      logger.error("Could not create output directory {}", packageDir.getPath());
      return;
    }

    Map<String, EntityDescription> allEntities = FlatPackCollections.mapForIteration();
    for (EntityDescription entity : api.getEntities()) {
      allEntities.put(entity.getTypeName(), entity);
      for (Iterator<Property> it = entity.getProperties().iterator(); it.hasNext();) {
        Property prop = it.next();
        if ("uuid".equals(prop.getName())) {
          // Crop the UUID property
          it.remove();
        } else if (!prop.getEnclosingTypeName().equals(entity.getTypeName())) {
          // Remove properties not declared in the current type
          it.remove();
        }
      }
    }
    // Ensure that the "real" implementations are used
    allEntities.remove("baseHasUuid");
    allEntities.remove("hasUuid");

    // Render entities
    for (EntityDescription entity : allEntities.values()) {
      ST entityST = group.getInstanceOf("entity")
          .add("entity", entity)
          .add("packageName", packageName);

      String simpleName = typePrefix + upcase(entity.getTypeName());
      if (baseTypes.contains(entity.getTypeName())) {
        simpleName += "Base";
      }
      render(entityST, packageDir, simpleName);
    }

    // Render referenced enumerations
    for (Type enumType : usedEnums) {
      ST enumST = group.getInstanceOf("enum")
          .add("enum", enumType)
          .add("packageName", packageName);

      render(enumST, packageDir, typePrefix + upcase(enumType.getName()));
    }

    String namePrefix = upcase(packageName.substring(packageName.lastIndexOf('.') + 1));

    // Render the Api convenience class
    ST apiST = group.getInstanceOf("api")
        .add("api", api)
        .add("packageName", packageName)
        .add("namePrefix", namePrefix)
        .add("apiIsPublic", apiIsPublic);
    render(apiST, packageDir, namePrefix + "Api");

    // Emit a manifest of all generated types
    ST typeSourceST = group.getInstanceOf("typeSource")
        .add("allEntities", allEntities.values())
        .add("packageName", packageName)
        .add("namePrefix", namePrefix);
    render(typeSourceST, packageDir, namePrefix + "TypeSource");
  }

  @Override
  public String getDialectName() {
    return "java";
  }

  /**
   * If {@value #concreteTypeMapFile} is defined, load the file into {@link #concreteTypeMap}.
   */
  protected void loadConcreteTypeMap() throws IOException {
    if (baseTypeArrayFile != null) {
      JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(
          baseTypeArrayFile), UTF8));
      reader.setLenient(true);
      reader.beginArray();
      while (reader.hasNext()) {
        baseTypes.add(reader.nextString());
      }
      reader.endArray();
      reader.close();
    }
  }

  /**
   * Load {@code java.stg} from the classpath and configure a number of model adaptors to add
   * virtual properties to the objects being rendered.
   */
  protected STGroup loadGroup(String template) {
    STGroup group = new STGroupFile(getClass().getResource(template), "UTF8", '<', '>');
    // EntityDescription are rendered as the FQN
    group.registerRenderer(EntityDescription.class, new AttributeRenderer() {
      @Override
      public String toString(Object o, String formatString, Locale locale) {
        EntityDescription entity = (EntityDescription) o;
        if (entity.getTypeName().equals("baseHasUuid")) {
          // Swap out for our hand-written base class
          return entity.isPersistent() ? BasePersistenceAware.class.getCanonicalName()
              : BaseHasUuid.class.getCanonicalName();
        }
        return packageName + "." + typePrefix + upcase(entity.getTypeName());
      }
    });
    // Types are registered as FQPN
    group.registerRenderer(Type.class, new AttributeRenderer() {
      @Override
      public String toString(Object o, String formatString, Locale locale) {
        Type type = (Type) o;
        return toString(type);
      }

      protected String toString(Type type) {
        switch (type.getJsonKind()) {
          case ANY:
            return Object.class.getCanonicalName();
          case BOOLEAN:
            return Boolean.class.getCanonicalName();
          case DOUBLE:
            return Double.class.getCanonicalName();
          case INTEGER:
            return Integer.class.getCanonicalName();
          case LIST:
            return List.class.getCanonicalName() + "<" + toString(type.getListElement()) + ">";
          case MAP:
            return Map.class.getCanonicalName() + "<" + toString(type.getMapKey()) + ","
              + toString(type.getMapValue()) + ">";
          case NULL:
            return Void.class.getCanonicalName();
          case STRING: {
            // Look for the presence of enum values
            if (type.getEnumValues() != null) {
              // Register a referenced enum
              usedEnums.add(type);
              return typePrefix + upcase(type.getName());
            }
            // Any other named type must be an entity type
            if (type.getName() != null) {
              // Allow type to be overridden
              return typePrefix + upcase(type.getName());
            }
            // Look for a type hint
            TypeHint hint = type.getTypeHint();
            if (hint != null) {
              return hint.getValue();
            }
            // Otherwise it must be a plain string
            return String.class.getCanonicalName();
          }
        }
        throw new UnsupportedOperationException("Unknown JsonKind " + type.getJsonKind());
      }
    });
    group.registerModelAdaptor(EndpointDescription.class, new ObjectModelAdaptor() {
      @Override
      public Object getProperty(Interpreter interp, ST self, Object o, Object property,
          String propertyName) throws STNoSuchPropertyException {
        EndpointDescription end = (EndpointDescription) o;
        if ("combinedArgs".equals(propertyName)) {
          // Return the path and query parameters together
          List<ParameterDescription> toReturn = new ArrayList<ParameterDescription>();
          if (end.getPathParameters() != null) {
            toReturn.addAll(end.getPathParameters());
          }
          if (end.getQueryParameters() != null) {
            toReturn.addAll(end.getQueryParameters());
          }
          return toReturn;
        } else if ("javaName".equals(propertyName) || "javaNameUpcase".equals(propertyName)) {
          // Convert a path like /api/2/foo/bar/{}/baz to fooBarBazMethod
          String path = end.getPath();
          String[] parts = path.split(Pattern.quote("/"));
          StringBuilder sb = new StringBuilder();
          for (int i = stripPathSegments, j = parts.length; i < j; i++) {
            try {
              String part = parts[i];
              if (part.length() == 0) {
                continue;
              }
              StringBuilder decodedPart = new StringBuilder(URLDecoder.decode(part, "UTF8"));
              // Trim characters that aren't legal
              for (int k = decodedPart.length() - 1; k >= 0; k--) {
                if (!Character.isJavaIdentifierPart(decodedPart.charAt(k))) {
                  decodedPart.deleteCharAt(k);
                }
              }
              // Append the new name part, using camel-cased names
              String newPart = decodedPart.toString();
              if (sb.length() > 0) {
                newPart = upcase(newPart);
              }
              sb.append(newPart);
            } catch (UnsupportedEncodingException e) {
              throw new RuntimeException(e);
            }
          }
          sb.append(upcase(end.getMethod().toLowerCase()));
          String javaName = sb.toString();
          if ("javaNameUpcase".equals(propertyName)) {
            javaName = upcase(javaName);
          }
          return javaName;
        } else if ("pathDecoded".equals(propertyName)) {
          // URL-decode the path in the endpoint description
          try {
            String decoded = URLDecoder.decode(end.getPath(), "UTF8");
            return decoded;
          } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
          }
        } else if ("hasPayload".equals(propertyName)) {
          return end.getEntity() != null;
        }
        return super.getProperty(interp, self, o, property, propertyName);
      }
    });
    group.registerModelAdaptor(EntityDescription.class, new ObjectModelAdaptor() {
      @Override
      public Object getProperty(Interpreter interp, ST self, Object o, Object property,
          String propertyName) throws STNoSuchPropertyException {
        EntityDescription entity = (EntityDescription) o;
        if ("baseType".equals(propertyName)) {
          return baseTypes.contains(entity.getTypeName());
        } else if ("payloadName".equals(propertyName)) {
          return entity.getTypeName();
        } else if ("supertype".equals(propertyName)) {
          EntityDescription supertype = entity.getSupertype();
          if (supertype == null) {
            return entity.isPersistent() ? BasePersistenceAware.class.getCanonicalName()
                : BaseHasUuid.class.getCanonicalName();
          } else {
            return supertype;
          }
        } else if ("simpleName".equals(propertyName)) {
          return typePrefix + upcase(entity.getTypeName());
        }
        return super.getProperty(interp, self, o, property, propertyName);
      }
    });
    group.registerModelAdaptor(ParameterDescription.class, new ObjectModelAdaptor() {
      @Override
      public Object getProperty(Interpreter interp, ST self, Object o, Object property,
          String propertyName) throws STNoSuchPropertyException {
        ParameterDescription param = (ParameterDescription) o;
        if ("javaNameUpcase".equals(propertyName)) {
          return upcase(param.getName());
        }
        return super.getProperty(interp, self, o, property, propertyName);
      }
    });
    group.registerModelAdaptor(Property.class, new ObjectModelAdaptor() {
      @Override
      public Object getProperty(Interpreter interp, ST self, Object o, Object property,
          String propertyName) throws STNoSuchPropertyException {
        Property p = (Property) o;
        if ("getterName".equals(propertyName)) {
          return upcase(p.getName());
        } else if ("getterPermitAll".equals(propertyName)) {
          return Collections.singleton("*").equals(p.getGetterRoleNames());
        } else if ("needsImplied".equals(propertyName)) {
          // Returns true if the property has @Implies / @OneToMany and is a list
          return p.getImpliedProperty() != null && p.getType().getListElement() != null;
        } else if ("setterPermitAll".equals(propertyName)) {
          return Collections.singleton("*").equals(p.getSetterRoleNames());
        }
        return super.getProperty(interp, self, o, property, propertyName);
      }
    });
    group.registerModelAdaptor(Type.class, new ObjectModelAdaptor() {

      @Override
      public Object getProperty(Interpreter interp, ST self, Object o, Object property,
          String propertyName) throws STNoSuchPropertyException {
        Type type = (Type) o;
        if ("flatTypes".equals(propertyName)) {
          return flatten(type);
        }
        return super.getProperty(interp, self, o, property, propertyName);
      }

      private List<String> flatten(Type type) {
        switch (type.getJsonKind()) {
          case ANY:
            return Collections.singletonList(Object.class.getCanonicalName());
          case BOOLEAN:
            return Collections.singletonList(Boolean.class.getCanonicalName());
          case DOUBLE:
            return Collections.singletonList(Double.class.getCanonicalName());
          case INTEGER:
            return Collections.singletonList(Integer.class.getCanonicalName());
          case LIST: {
            List<String> toReturn = new ArrayList<String>();
            toReturn.add(List.class.getCanonicalName());
            toReturn.addAll(flatten(type.getListElement()));
            return toReturn;
          }
          case MAP: {
            List<String> toReturn = new ArrayList<String>();
            toReturn.add(Map.class.getCanonicalName());
            toReturn.addAll(flatten(type.getMapKey()));
            toReturn.addAll(flatten(type.getMapValue()));
            return toReturn;
          }
          case NULL:
            return Collections.singletonList(Void.class.getCanonicalName());
          case STRING: {
            if (type.getName() != null) {
              return Collections.singletonList(typePrefix + upcase(type.getName()));
            } else if (type.getTypeHint() != null) {
              return Collections.singletonList(type.getTypeHint().getValue());
            } else {
              return Collections.singletonList(String.class.getCanonicalName());
            }
          }
        }
        throw new UnsupportedOperationException("Unknown JsonKind " + type.getJsonKind());
      }
    });
    group.registerModelAdaptor(String.class, new ObjectModelAdaptor() {

      @Override
      public Object getProperty(Interpreter interp, ST self, Object o, Object property,
          String propertyName) throws STNoSuchPropertyException {
        final String string = (String) o;
        if ("chunks".equals(propertyName)) {
          /*
           * Split a string into individual chunks that can be reflowed. This implementation is
           * pretty simplistic, but helps make the generated documentation at least somewhat more
           * readable.
           */
          return new Iterator<CharSequence>() {
            int index;
            int length = string.length();
            CharSequence next;

            {
              advance();
            }

            @Override
            public boolean hasNext() {
              return next != null;
            }

            @Override
            public CharSequence next() {
              CharSequence toReturn = next;
              advance();
              return toReturn;
            }

            @Override
            public void remove() {
              throw new UnsupportedOperationException();
            }

            private void advance() {
              int start = advance(false);
              int end = advance(true);
              if (start == end) {
                next = null;
              } else {
                next = string.subSequence(start, end);
              }
            }

            /**
             * Advance to next non-whitespace character.
             */
            private int advance(boolean whitespace) {
              while (index < length
                && (whitespace ^ Character.isWhitespace(string.charAt(index)))) {
                index++;
              }
              return index;
            }
          };
        }
        return super.getProperty(interp, self, o, property, propertyName);
      }
    });

    Map<String, Object> namesMap = new HashMap<String, Object>();
    for (Class<?> clazz : WELL_KNOWN_TYPES) {
      namesMap.put(clazz.getSimpleName(), clazz.getCanonicalName());
    }
    group.defineDictionary("names", namesMap);

    return group;
  }

  protected void render(ST enumST, File packageDir, String typeName) throws IOException {
    Writer fileWriter = new OutputStreamWriter(
        new FileOutputStream(new File(packageDir, typeName + ".java")), UTF8);
    AutoIndentWriter writer = new AutoIndentWriter(fileWriter);
    writer.setLineWidth(80);
    enumST.write(writer);
    fileWriter.close();
  }
}
