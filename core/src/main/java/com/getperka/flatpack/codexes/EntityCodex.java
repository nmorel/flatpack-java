/*
 * #%L
 * FlatPack serialization code
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
package com.getperka.flatpack.codexes;

import static com.getperka.flatpack.util.FlatPackTypes.erase;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Provider;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.PersistenceAware;
import com.getperka.flatpack.PostUnpack;
import com.getperka.flatpack.PreUnpack;
import com.getperka.flatpack.ext.Codex;
import com.getperka.flatpack.ext.DeserializationContext;
import com.getperka.flatpack.ext.EntityResolver;
import com.getperka.flatpack.ext.JsonKind;
import com.getperka.flatpack.ext.Property;
import com.getperka.flatpack.ext.SerializationContext;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.ext.TypeContext;
import com.getperka.flatpack.util.FlatPackCollections;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.google.inject.TypeLiteral;

/**
 * Support for reading and writing entities that are known by {@link TypeContext}.
 * 
 * @param <T> the type of entity to encode
 */
public class EntityCodex<T extends HasUuid> extends Codex<T> {

  private Class<T> clazz;
  @Inject
  private EntityResolver entityResolver;
  @com.google.inject.Inject(optional = true)
  private Provider<T> provider;
  private List<Method> preUnpackMethods;
  private List<Method> postUnpackMethods;
  @Inject
  private TypeContext typeContext;

  protected EntityCodex() {}

  /**
   * Performs a minimal amount of work to create an empty stub object to fill in later.
   * 
   * @param element a JsonObject containing a {@code uuid} property. If {@code null}, a
   *          randomly-generated UUID will be assigned to the allocated object
   * @param context this method will call {@link DeserializationContext#putEntity} to store the
   *          newly-allocated entity
   */
  public T allocate(JsonElement element, DeserializationContext context) {
    JsonElement uuidElement = element.getAsJsonObject().get("uuid");
    if (uuidElement == null) {
      context.fail(new IllegalArgumentException("Data entry missing uuid:\n"
        + element.toString()));
    }
    UUID uuid = UUID.fromString(uuidElement.getAsString());
    T toReturn = allocate(uuid, context, true);

    // Register PostUnpack methods
    if (!postUnpackMethods.isEmpty()) {
      context.addPostWork(new PostUnpackInvoker(toReturn, postUnpackMethods));
    }
    return toReturn;
  }

  @Override
  public Type describe() {
    return new Type.Builder()
        .withJsonKind(JsonKind.STRING)
        .withName(typeContext.getPayloadName(clazz))
        .build();
  }

  @Override
  public String getPropertySuffix() {
    return "Uuid";
  }

  @Override
  public T readNotNull(JsonElement element, DeserializationContext context) {
    UUID uuid = UUID.fromString(element.getAsString());
    HasUuid entity = context.getEntity(uuid);
    /*
     * If the UUID is a reference to an entity that isn't in the data section, delegate to the
     * allocate() method. The entity will either be provided by an EntityResolver or a blank entity
     * will be created if possible.
     */
    if (entity == null) {
      entity = allocate(uuid, context, true);
    }
    try {
      return clazz.cast(entity);
    } catch (ClassCastException e) {
      throw new ClassCastException("Cannot cast a " + entity.getClass().getName()
        + " to a " + clazz.getName() + ". Duplicate UUID in data payload?");
    }
  }

  public void readProperties(T object, JsonObject element, DeserializationContext context) {
    context.pushPath("(EntityCodex.readProperties())" + object.getUuid());

    try {
      // Ignore incoming data with just a UUID value to avoid unnecessary warnings
      if (element.entrySet().size() == 1 && element.has("uuid")) {
        return;
      }

      if (!context.checkAccess(object)) {
        return;
      }

      // Allow the object to see the data that's about to be applied
      for (Method m : preUnpackMethods) {
        if (m.getParameterTypes().length == 0) {
          m.invoke(object);
        } else {
          m.invoke(object, element);
        }
      }

      List<String> roles = context.getRoles();
      for (Property prop : typeContext.extractProperties(clazz)) {
        if (!prop.maySet(roles)) {
          continue;
        }

        String simplePropertyName = prop.getName();
        context.pushPath("." + simplePropertyName);
        try {
          Object value;
          if (prop.isEmbedded()) {
            /*
             * Embedded objects are never referred to by uuid in the payload, so an instance will
             * need to be allocated before reading in the properties.
             */
            @SuppressWarnings("unchecked")
            EntityCodex<HasUuid> codex = (EntityCodex<HasUuid>) prop.getCodex();
            HasUuid embedded = codex.allocate(UUID.randomUUID(), context, false);
            codex.readProperties(embedded, element, context);
            value = embedded;
          } else {

            @SuppressWarnings("unchecked")
            Codex<Object> codex = (Codex<Object>) prop.getCodex();

            // merchant would become merchantUuid
            String payloadPropertyName = simplePropertyName + codex.getPropertySuffix();

            // Ignore undefined property values, while allowing explicit nullification
            if (!element.has(payloadPropertyName)) {
              continue;
            }

            value = codex.read(element.get(payloadPropertyName), context);
          }

          if (value == null && prop.getSetter().getParameterTypes()[0].isPrimitive()) {
            // Don't try to pass a null to a primitive setter
            continue;
          }

          // Perhaps set the other side of a OneToMany relationship
          Property impliedPropery = prop.getImpliedProperty();
          if (impliedPropery != null && value != null) {
            // Ensure that any linked property is also mutable
            if (!impliedPropery.maySet(roles) || !checkAccess(value, context)) {
              context.addWarning(object,
                  "Ignoring property %s because the inverse relationship (%s) may not be set",
                  prop.getName(), impliedPropery.getName());
              continue;
            }
            context.addPostWork(new ImpliedPropertySetter(context, impliedPropery, value, object));
          }

          // Set the value
          prop.getSetter().invoke(object, value);

          // Record the value as having been set
          context.addModified(object, prop);
        } catch (Exception e) {
          context.fail(e);
        } finally {
          context.popPath();
        }
      }
    } catch (Exception e) {
      context.fail(e);
    } finally {
      context.popPath();
    }
  }

  @Override
  public void scanNotNull(T object, SerializationContext context) throws Exception {
    // Handle subtypes of the expected type by delegating to a more specific implementation
    Codex<HasUuid> maybeSubtype = typeContext.getCodex(object.getClass());
    if (this == maybeSubtype) {
      if (context.add(object)) {
        traverse(object, false, context, null);
      }
    } else {
      maybeSubtype.scanNotNull(object, context);
    }
  }

  /**
   * For debugging use only.
   */
  @Override
  public String toString() {
    return clazz.getCanonicalName();
  }

  @Override
  public void writeNotNull(T object, SerializationContext context) throws IOException {
    JsonWriter writer = context.getWriter();
    writer.value(object.getUuid().toString());
  }

  public void writeProperties(T object, SerializationContext context) {
    context.pushPath("(EntityCodex.writeProperties())" + object.getUuid());
    try {
      JsonWriter writer = context.getWriter();
      writer.beginObject();
      traverse(object, false, context, writer);
      writer.endObject();
    } catch (Exception e) {
      context.fail(e);
    } finally {
      context.popPath();
    }
  }

  @Inject
  void inject(TypeLiteral<T> clazz) {
    this.clazz = erase(clazz.getType());

    List<Method> pre = new ArrayList<Method>();
    List<Method> post = new ArrayList<Method>();
    // Iterate over all methods in the type and then its supertypes
    for (Class<?> lookAt = this.clazz; lookAt != null; lookAt = lookAt.getSuperclass()) {
      for (Method m : lookAt.getDeclaredMethods()) {
        Class<?>[] params = m.getParameterTypes();
        switch (params.length) {
          case 0:
            if (m.isAnnotationPresent(PreUnpack.class)) {
              m.setAccessible(true);
              pre.add(m);
            }
            if (m.isAnnotationPresent(PostUnpack.class)) {
              m.setAccessible(true);
              post.add(m);
            }
            break;
          case 1:
            if (m.isAnnotationPresent(PreUnpack.class) && params[0].equals(JsonObject.class)) {
              m.setAccessible(true);
              pre.add(m);
            }
            break;
        }
      }
    }
    // Reverse the list to call supertype methods first
    Collections.reverse(pre);
    Collections.reverse(post);
    preUnpackMethods = pre.isEmpty() ? Collections.<Method> emptyList() :
        Collections.unmodifiableList(pre);
    postUnpackMethods = post.isEmpty() ? Collections.<Method> emptyList() :
        Collections.unmodifiableList(post);
  }

  private T allocate(UUID uuid, DeserializationContext context, boolean useResolvers) {
    T toReturn = null;
    boolean resolved = false;

    // Possibly delegate to injected resolvers
    if (useResolvers) {
      try {
        toReturn = entityResolver.resolve(clazz, uuid);
      } catch (Exception e) {
        context.fail(e);
      }
      if (toReturn != null) {
        resolved = true;
      }
    }

    // Otherwise try to construct a new instance
    if (toReturn == null && provider != null) {
      toReturn = provider.get();
    }

    toReturn.setUuid(uuid);
    context.putEntity(uuid, toReturn, resolved);
    return toReturn;
  }

  /**
   * A fan-out to to {@link DeserializationContext#checkAccess(HasUuid)} that will accept
   * collections.
   */
  private boolean checkAccess(Object object, DeserializationContext ctx) {
    if (object instanceof HasUuid) {
      return ctx.checkAccess((HasUuid) object);
    }
    if (object instanceof Iterable) {
      for (Object obj : ((Iterable<?>) object)) {
        if (!checkAccess(obj, ctx)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * A scuzzy method that either scans, writes, or reads based on whether or not {@code writer} is
   * null
   */
  private void traverse(T object, boolean isEmbedded, SerializationContext context,
      JsonWriter writer) throws Exception {

    if (object == null) return;

    Set<String> dirtyPropertyNames;
    if (object instanceof PersistenceAware) {
      dirtyPropertyNames = FlatPackCollections.setForIteration();
      // Always write out uuid
      dirtyPropertyNames.add("uuid");
      dirtyPropertyNames.addAll(((PersistenceAware) object).dirtyPropertyNames());
    } else {
      dirtyPropertyNames = null;
    }

    // Write all properties
    for (Property prop : typeContext.extractProperties(clazz)) {
      // Check access
      if (!prop.mayGet(context.getRoles())) {
        continue;
      }
      // Ignore OneToMany type properties unless specifically requested
      if (prop.isDeepTraversalOnly() && !context.getTraversalMode().writeAllProperties()) {
        continue;
      }
      // Don't emit a redundant uuid property
      if (isEmbedded && "uuid".equals(prop.getName())) {
        continue;
      }
      // Skip clean properties
      if (dirtyPropertyNames != null && !dirtyPropertyNames.contains(prop.getName())) {
        continue;
      }
      context.pushPath("." + prop.getName());
      try {
        // Extract the value
        prop.getGetter().setAccessible(true);
        Object value = prop.getGetter().invoke(object);

        // Figure out how to interpret the value
        @SuppressWarnings("unchecked")
        Codex<Object> codex = (Codex<Object>) prop.getCodex();

        if (prop.isEmbedded()) {
          @SuppressWarnings("unchecked")
          EntityCodex<HasUuid> embeddedCodex = (EntityCodex<HasUuid>) prop.getCodex();
          embeddedCodex.traverse((HasUuid) value, true, context, writer);
        } else if (writer == null) {
          // Either scan or write the property value
          codex.scan(value, context);
        } else if (!(prop.isSuppressDefaultValue() && codex.isDefaultValue(value))) {
          // Write the value of the property, optionally suppressing default values
          writer.name(prop.getName() + codex.getPropertySuffix());
          codex.write(value, context);
        }
      } catch (Exception e) {
        context.fail(e);
      } finally {
        context.popPath();
      }
    }
  }
}