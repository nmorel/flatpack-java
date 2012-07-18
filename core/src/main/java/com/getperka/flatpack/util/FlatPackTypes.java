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
package com.getperka.flatpack.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Miscellaneous typesystem-querying methods.
 */
public class FlatPackTypes {

  public static final List<Class<?>> BOXED_TYPES = Collections.unmodifiableList(
      Arrays.<Class<?>> asList(Boolean.class, Byte.class, Character.class, Double.class,
          Float.class, Integer.class, Long.class, Short.class, Void.class));

  public static final List<Class<?>> PRIMITIVE_TYPES = Collections.unmodifiableList(
      Arrays.<Class<?>> asList(boolean.class, byte.class, char.class, double.class, float.class,
          int.class, long.class, short.class, void.class));

  public static final Charset UTF8 = Charset.forName("UTF8");

  public static Class<?> box(Class<?> primitive) {
    assert PRIMITIVE_TYPES.contains(primitive);
    return BOXED_TYPES.get(PRIMITIVE_TYPES.indexOf(primitive));
  }

  /**
   * Construct as possible parameterized type from a flattened representation of a generic
   * parameterization.
   */
  public static Type createType(Class<?>... flatParameterization) {
    return createType(Arrays.<Type> asList(flatParameterization));
  }

  /**
   * Construct as possible parameterized type from a flattened representation of a generic
   * parameterization.
   */
  public static Type createType(List<? extends Type> flatParameterization) {
    return createType(flatParameterization.iterator());
  }

  /**
   * Construct as possible parameterized type from a flattened representation of a generic
   * parameterization.
   */
  public static Type createType(Type... flatParameterization) {
    return createType(Arrays.<Type> asList(flatParameterization));
  }

  /**
   * Portable implementation of decapitalize.
   */
  public static String decapitalize(String string) {
    if (string == null || string.isEmpty()) {
      return string;
    } else if (string.length() == 1) {
      return String.valueOf(Character.toLowerCase(string.charAt(0)));
    } else {
      return Character.toLowerCase(string.charAt(0)) + string.substring(1);
    }
  }

  /**
   * Determine the erasure of a given type.
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<T> erase(Type type) {
    if (type instanceof Class) {
      return (Class<T>) type;
    }
    if (type instanceof GenericArrayType) {
      Class<?> component = erase(((GenericArrayType) type).getGenericComponentType());
      return (Class<T>) Array.newInstance(component, 0).getClass();
    }
    if (type instanceof ParameterizedType) {
      return erase(((ParameterizedType) type).getRawType());
    }
    if (type instanceof TypeVariable) {
      return erase(((TypeVariable<?>) type).getBounds()[0]);
    }
    if (type instanceof WildcardType) {
      return erase(((WildcardType) type).getUpperBounds()[0]);
    }
    throw new RuntimeException("Unhandled type " + type.getClass().getName());
  }

  /**
   * Recursively flatten a ParameterizedType into a list of simple types.
   */
  public static List<Type> flatten(Type type) {
    List<Type> toReturn = FlatPackCollections.listForAny();
    if (type instanceof ParameterizedType) {
      ParameterizedType param = (ParameterizedType) type;
      toReturn.add(erase(param.getRawType()));
      for (Type arg : param.getActualTypeArguments()) {
        toReturn.addAll(flatten(arg));
      }
    } else {
      toReturn.add(type);
    }
    return toReturn;
  }

  /**
   * Given a type {@code Foo<Bar, Baz>} or a type derived therefrom and search class {@code Foo},
   * return {@code Bar, Baz}.
   */
  public static Type[] getParameterization(Class<?> search, Type... types) {
    for (Type type : types) {
      if (type == null) {
        continue;
      } else if (type instanceof Class<?>) {
        /*
         * Classes are straightforward. If there's a FooList extends ArrayList<Foo> (thus
         * implementing List<Foo>), we trawl the super-interface and then super-class hierarchies.
         */
        Class<?> clazz = (Class<?>) type;
        if (search.equals(clazz)) {
          return search.getTypeParameters();
        }
        Type[] found = getParameterization(search, clazz.getGenericSuperclass());
        if (found != null) {
          return found;
        }
        found = getParameterization(search, clazz.getGenericInterfaces());
        if (found != null) {
          return found;
        }
      } else if (type instanceof ParameterizedType) {
        /*
         * First, build a map of the raw type's type parameters to types used in the
         * parameterization. For example, List<Foo> has a raw type of List<T> and the map has T ->
         * Foo.
         */
        ParameterizedType param = (ParameterizedType) type;
        Type[] actualTypeArguments = param.getActualTypeArguments();
        Class<?> erased = erase(param.getRawType());
        Type[] typeParameters = erased.getTypeParameters();

        Map<Type, Type> map = FlatPackCollections.mapForLookup();
        for (int i = 0, j = typeParameters.length; i < j; i++) {
          map.put(typeParameters[i], actualTypeArguments[i]);
        }

        /*
         * If we've descended into the type hierarchy enough to find the type we're looking for, say
         * Collection, we'll use its type parameters to pull the relevant data from the previous
         * map. Otherwise, ascend into the super-interfaces and extract the relevant type parameters
         * (e.g. List<T> extends Collection<T> so we can find the T from Collection).
         */
        Type[] lookFor = search.equals(erased) ? search.getTypeParameters()
            : getParameterization(search, erased.getGenericInterfaces());
        if (lookFor == null) {
          lookFor = getParameterization(search, erased.getGenericSuperclass());
          if (lookFor == null) {
            return null;
          }
        }

        // /*
        // * Now that we have the type parameters to look for, pull them out of the collected map.
        // */
        // List<Type> toReturn = FlatPackCollections.listForAny();
        // for (int i = 0, j = lookFor.length; i < j; i++) {
        // Type found = map.get(lookFor[i]);
        // if (found != null) {
        // toReturn.add(found);
        // }
        // }
        List<Type> toReturn = FlatPackCollections.listForAny();
        for (int i = 0, j = lookFor.length; i < j; i++) {
          toReturn.add(replaceTypes(map, lookFor[i]));
        }

        // Done.
        return toReturn.toArray(new Type[toReturn.size()]);
      }
    }
    return null;
  }

  /**
   * Given a type {@code Foo<Bar, Baz>} or a type derived therefrom and search class {@code Foo},
   * return {@code Bar}. If the given type is not assignable to {@code search}, {@code null} will be
   * returned.
   */
  public static Type getSingleParameterization(Type type, Class<?> search) {
    Type[] types = getParameterization(search, type);
    return types == null ? null : types[0];
  }

  /**
   * Erases {@link WildcardType} and {@link TypeVariable} instances, returning all other Types
   * unmodified.
   */
  public static Type instantiable(Type type) {
    if (type instanceof TypeVariable || type instanceof WildcardType) {
      return erase(type);
    }
    return type;
  }

  /**
   * Given a class that represents a boxed primitive type, return the primitive type.
   */
  public static Class<?> unbox(Class<?> boxed) {
    assert BOXED_TYPES.contains(boxed);
    return PRIMITIVE_TYPES.get(BOXED_TYPES.indexOf(boxed));
  }

  /**
   * @see #createType(List)
   */
  private static Type createType(Iterator<? extends Type> it) {
    final Type raw = it.next();
    if (!(raw instanceof Class<?>)) {
      return raw;
    }
    TypeVariable<?>[] vars = ((Class<?>) raw).getTypeParameters();

    // If the type has no parameterizations, just return it
    if (vars.length == 0) {
      return raw;
    }
    // Create the parameter values
    final Type[] typeArguments = new Type[vars.length];
    for (int i = 0, j = typeArguments.length; i < j; i++) {
      typeArguments[i] = createType(it);
    }

    // Return a carrier object
    return new ParameterizedType() {
      @Override
      public Type[] getActualTypeArguments() {
        return typeArguments;
      }

      @Override
      public Type getOwnerType() {
        return null;
      }

      @Override
      public Type getRawType() {
        return raw;
      }
    };
  }

  private static Type replaceTypes(Map<Type, Type> replacements, Type toReplace) {
    List<Type> flat = flatten(toReplace);
    for (ListIterator<Type> it = flat.listIterator(); it.hasNext();) {
      Type type = it.next();
      if (replacements.containsKey(type)) {
        it.set(replacements.get(type));
      }
    }
    return createType(flat);
  }
}
