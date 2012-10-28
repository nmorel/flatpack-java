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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

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
import com.getperka.flatpack.client.dto.ApiDescription;
import com.getperka.flatpack.client.dto.EndpointDescription;
import com.getperka.flatpack.client.dto.EntityDescription;
import com.getperka.flatpack.client.dto.ParameterDescription;
import com.getperka.flatpack.ext.Property;
import com.getperka.flatpack.ext.Type;
import com.getperka.flatpack.util.FlatPackCollections;

/**
 * Generates simple Ruby representations of a FlatPack API.
 */
public class RbDialect implements Dialect {
  @Flag(tag = "gemName",
      help = "The name of the generated gem",
      defaultValue = "changeme")
  static String gemName;

  @Flag(tag = "moduleName",
      help = "The name of the generated top-level module",
      defaultValue = "NoName")
  static String moduleName;

  @Flag(tag = "modelModuleName",
      help = "The name of the generated model sub-module",
      defaultValue = "Dto")
  static String modelModuleName;

  private static final Charset UTF8 = Charset.forName("UTF8");

  static String camelCaseToUnderscore(String s) {
    return s.replaceAll(
        String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])",
            "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"), "_")
        .toLowerCase();
  }

  private static String upcase(String s) {
    return Character.toUpperCase(s.charAt(0)) + s.substring(1);
  }

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void generate(ApiDescription api, File outputDir) throws IOException {

    // define our output directories
    File genOutput = new File(outputDir, "generated");
    File moduleOutput = new File(genOutput, gemName);

    // delete our root output directory if one already exists
    if (genOutput.exists()) {
      delete(genOutput);
    }

    // first collect just our model entities
    Map<String, EntityDescription> allEntities = FlatPackCollections
        .mapForIteration();
    for (EntityDescription entity : api.getEntities()) {
      allEntities.put(entity.getTypeName(), entity);
      for (Iterator<Property> it = entity.getProperties().iterator(); it
          .hasNext();) {
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

    // rendr the list of require statements
    List<String> requires = new ArrayList<String>();
    requires.add(gemName + "/client_api");
    for (EntityDescription desc : allEntities.values()) {
      requires.add(gemName + "/model/"
        + camelCaseToUnderscore(desc.getTypeName()));
    }
    STGroup group = loadGroup();
    ST entityST = group.getInstanceOf("module")
        .add("requires", requires);
    render(entityST, genOutput, gemName + "Fast");

    // Render entities
    File modelOutput = new File(moduleOutput, "model");
    for (EntityDescription entity : allEntities.values()) {
      entityST = group.getInstanceOf("entity").add("entity", entity);
      render(entityST, modelOutput, upcase(entity.getTypeName()));
    }

    // Render referenced enumerations

    /*
     * for (Type enumType : usedEnums) { ST enumST = group.getInstanceOf("enum") .add("enum",
     * enumType).add("packageName", packageName); render(enumST, packageDir, typePrefix +
     * upcase(enumType.getName())); }
     */

    // Render the Api convenience class
    ST apiST = group.getInstanceOf("api").add("api", api);
    render(apiST, moduleOutput, "clientApi");

    // Emit a manifest of all generated types ST typeSourceST =
    /*
     * group.getInstanceOf("typeSource").add("allEntities", allEntities.values()).add("packageName",
     * packageName).add("namePrefix", namePrefix); render(typeSourceST, packageDir, namePrefix +
     * "TypeSource");
     */

    // File gemfiles = new File(getClass().getResource("/gemfiles").getFile());
    // for (String file : (gemfiles.list())) {
    // copy(new File(gemfiles, file), new File(gemOutput, file));
    // }
  }

  @Override
  public String getDialectName() {
    return "rb";
  }

  private void delete(File f) {
    if (f.isDirectory()) {
      for (File c : f.listFiles()) {
        delete(c);
      }
    }
    if (!f.delete()) {
      logger.error("Could not delete " + f);
    }
  }

  /**
   * Load {@code java.stg} from the classpath and configure a number of model adaptors to add
   * virtual properties to the objects being rendered.
   */
  private STGroup loadGroup() {

    STGroup group = new STGroupFile(getClass().getResource("rb.stg"), "UTF8", '<', '>');
    // EntityDescription are rendered as the FQN
    group.registerRenderer(EntityDescription.class, new AttributeRenderer() {

      @Override
      public String toString(Object o, String formatString, Locale locale) {
        EntityDescription entity = (EntityDescription) o;
        if (entity.getTypeName().equals("baseHasUuid")) {
          return BaseHasUuid.class.getCanonicalName();
        }
        return upcase(entity.getTypeName());
      }
    });

    group.registerModelAdaptor(EndpointDescription.class,
        new ObjectModelAdaptor() {
          @Override
          public Object getProperty(Interpreter interp, ST self, Object o,
              Object property, String propertyName)
              throws STNoSuchPropertyException {
            EndpointDescription end = (EndpointDescription) o;
            if ("className".equals(propertyName) || "methodName".equals(propertyName)) {
              // Convert a path like /api/2/foo/bar/{}/baz to FooBarBazMethod
              String path = end.getPath();
              String[] parts = path.split(Pattern.quote("/"));
              StringBuilder sb = new StringBuilder();
              for (int i = 3, j = parts.length; i < j; i++) {
                try {
                  String part = parts[i];
                  if (part.length() == 0) {
                    continue;
                  }
                  StringBuilder decodedPart = new StringBuilder(URLDecoder
                      .decode(part, "UTF8"));
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
              String name = sb.toString();

              return "className".equals(propertyName) ? upcase(name) : camelCaseToUnderscore(name);
            } else if ("pathDecoded".equals(propertyName)) {
              // URL-decode the path in the endpoint description
              try {
                String decoded = URLDecoder.decode(end.getPath(), "UTF8");
                return decoded;
              } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
              }
            }
            return super.getProperty(interp, self, o, property, propertyName);
          }
        });

    group.registerModelAdaptor(EntityDescription.class,
        new ObjectModelAdaptor() {
          @Override
          public Object getProperty(Interpreter interp, ST self, Object o,
              Object property, String propertyName)
              throws STNoSuchPropertyException {

            EntityDescription entity = (EntityDescription) o;
            if ("payloadName".equals(propertyName)) {
              return entity.getTypeName();
            }

            else if ("supertype".equals(propertyName)) {
              EntityDescription supertype = entity.getSupertype();
              return supertype == null ? new EntityDescription("baseHasUuid", null) : supertype;
            }

            else if ("simpleName".equals(propertyName)) {
              if (entity.getTypeName().equals("baseHasUuid")) {
                return "Flatpack::Core::" + BaseHasUuid.class.getSimpleName();
              }
              return upcase(entity.getTypeName());
            }

            else if ("requireName".equals(propertyName)) {
              return requireNameForType(entity.getTypeName());
            }

            else if ("properties".equals(propertyName)) {
              List<Property> properties = new ArrayList<Property>();
              for (Property p : entity.getProperties()) {
                if (!p.isEmbedded()) {
                  properties.add(p);
                }
              }
              return properties;
            }

            else if ("entityProperties".equals(propertyName)) {

              Map<String, Property> propertyMap = new HashMap<String, Property>();
              for (Property p : entity.getProperties()) {

                // TODO if we decide to encode enum types, we'll want to remove the second condition
                if (p.getType().getName() != null && p.getType().getEnumValues() == null) {
                  propertyMap.put(p.getName(), p);
                }
              }

              return propertyMap.values();
            }

            else if ("embeddedEntityProperties".equals(propertyName)) {
              Map<String, Property> propertyMap = new HashMap<String, Property>();
              for (Property p : entity.getProperties()) {
                if (p.getType().getName() != null &&
                  p.getType().getEnumValues() == null && p.isEmbedded()) {

                  propertyMap.put(p.getName(), p);
                }
              }
              return propertyMap.values();
            }

            return super.getProperty(interp, self, o, property, propertyName);
          }
        });

    group.registerModelAdaptor(ParameterDescription.class,
        new ObjectModelAdaptor() {
          @Override
          public Object getProperty(Interpreter interp, ST self, Object o,
              Object property, String propertyName)
              throws STNoSuchPropertyException {
            ParameterDescription param = (ParameterDescription) o;
            if ("underscoreName".equals(propertyName)) {
              return camelCaseToUnderscore(param.getName());
            }
            return super.getProperty(interp, self, o, property, propertyName);
          }
        });

    group.registerModelAdaptor(Property.class, new ObjectModelAdaptor() {
      @Override
      public Object getProperty(Interpreter interp, ST self, Object o,
          Object property, String propertyName)
          throws STNoSuchPropertyException {
        Property p = (Property) o;
        if ("attrName".equals(propertyName)) {
          return camelCaseToUnderscore(p.getName());
        }

        else if ("requireName".equals(propertyName)) {
          return requireNameForType(p.getType().getName());
        }
        return super.getProperty(interp, self, o, property, propertyName);
      }
    });

    group.registerModelAdaptor(Type.class, new ObjectModelAdaptor() {

      @Override
      public Object getProperty(Interpreter interp, ST self, Object o,
          Object property, String propertyName)
          throws STNoSuchPropertyException {

        if (propertyName.equals("name")) {
          return moduleName + "::" + modelModuleName + "::" + upcase(((Type) o).getName());
        }
        return super.getProperty(interp, self, o, property, propertyName);
      }
    });

    group.registerModelAdaptor(String.class, new ObjectModelAdaptor() {

      @Override
      public Object getProperty(Interpreter interp, ST self, Object o,
          Object property, String propertyName)
          throws STNoSuchPropertyException {
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
    namesMap.put("gemName", gemName);
    namesMap.put("moduleName", moduleName);
    namesMap.put("modelModuleName", modelModuleName);
    group.defineDictionary("names", namesMap);

    return group;
  }

  private void render(ST enumST, File packageDir, String typeName)
      throws IOException {

    if (!packageDir.isDirectory() && !packageDir.mkdirs()) {
      logger
          .error("Could not create output directory {}", packageDir.getPath());
      return;
    }
    Writer fileWriter = new OutputStreamWriter(new FileOutputStream(new File(
        packageDir, camelCaseToUnderscore(typeName) + ".rb")), UTF8);
    AutoIndentWriter writer = new AutoIndentWriter(fileWriter);
    writer.setLineWidth(80);
    enumST.write(writer);
    fileWriter.close();
  }

  private String requireNameForType(String type) {
    if (type.equals("baseHasUuid")) {
      return "flatpack_core";
    }
    return gemName + "/" + camelCaseToUnderscore(modelModuleName) + "/"
      + camelCaseToUnderscore(type);
  }
}
