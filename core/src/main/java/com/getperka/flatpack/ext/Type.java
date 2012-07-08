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
package com.getperka.flatpack.ext;

import static com.getperka.flatpack.util.FlatPackTypes.UTF8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;

import com.getperka.flatpack.BaseHasUuid;

/**
 * A simple JSON type description.
 * 
 * @see Codex#describe()
 */
public class Type extends BaseHasUuid {
  public static class Builder {
    private Type type = new Type();

    public Type build() {
      Type toReturn = type;
      type = null;
      return toReturn;
    }

    public Builder withEnumValues(List<String> enumValues) {
      type.enumValues = Collections.unmodifiableList(new ArrayList<String>(enumValues));
      return this;
    }

    public Builder withJsonKind(JsonKind kind) {
      type.jsonKind = kind;
      return this;
    }

    public Builder withListElement(Type t) {
      type.listElement = t;
      return this;
    }

    public Builder withMapKey(Type t) {
      type.mapKey = t;
      return this;
    }

    public Builder withMapValue(Type t) {
      type.mapValue = t;
      return this;
    }

    public Builder withName(String name) {
      type.name = name;
      return this;
    }

    public Builder withTypeHint(TypeHint hint) {
      type.hint = hint;
      return this;
    }
  }

  private JsonKind jsonKind;
  private List<String> enumValues;
  private String name;
  private Type listElement;
  private Type mapKey;
  private Type mapValue;

  private TypeHint hint;

  @Inject
  private Type() {}

  @PermitAll
  public List<String> getEnumValues() {
    return enumValues;
  }

  @PermitAll
  public JsonKind getJsonKind() {
    return jsonKind;
  }

  @PermitAll
  public Type getListElement() {
    return listElement;
  }

  @PermitAll
  public Type getMapKey() {
    return mapKey;
  }

  @PermitAll
  public Type getMapValue() {
    return mapValue;
  }

  @PermitAll
  public String getName() {
    return name;
  }

  @PermitAll
  public TypeHint getTypeHint() {
    return hint;
  }

  /**
   * Returns a human-readable description of the type.
   */
  @Override
  public String toString() {
    switch (jsonKind) {
      case LIST:
        return "[ " + listElement + " ]";
      case MAP:
        return "{ " + mapKey + " : " + mapValue + " }";
    }
    if (name != null) {
      return name;
    }
    if (hint != null) {
      return jsonKind.toString() + ":" + hint.getValue();
    }
    return jsonKind.toString();
  }

  @Override
  protected UUID defaultUuid() {
    String key = getClass().getName() + ":" + toString();
    return UUID.nameUUIDFromBytes(key.getBytes(UTF8));
  }

  void setEnumValues(List<String> enumValues) {
    this.enumValues = enumValues;
  }

  void setJsonKind(JsonKind kind) {
    this.jsonKind = kind;
  }

  void setListElement(Type listElement) {
    this.listElement = listElement;
  }

  void setMapKey(Type mapKey) {
    this.mapKey = mapKey;
  }

  void setMapValue(Type mapValue) {
    this.mapValue = mapValue;
  }

  void setName(String name) {
    this.name = name;
  }

  void setTypeHint(TypeHint hint) {
    this.hint = hint;
  }
}