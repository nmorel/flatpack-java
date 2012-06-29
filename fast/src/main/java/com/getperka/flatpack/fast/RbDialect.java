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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
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

  static String underscoreToCamelCase(String s) {
    String[] parts = s.split("_|-");
    String camelCaseString = "";
    for (String part : parts) {
      camelCaseString = camelCaseString + upcase(part);
    }
    return camelCaseString;
  }

  private static String upcase(String s) {
    return Character.toUpperCase(s.charAt(0)) + s.substring(1);
  }

  private final Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * Used at the end of the code-generation process to emit referenced enum values.
   */
  private final Set<Type> usedEnums = new LinkedHashSet<Type>();

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
    // ST apiST = group.getInstanceOf("api")
    // .add("api", api);

    // render(apiST, packageDir, namePrefix + "Api");

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
            if ("javaName".equals(propertyName)
              || "javaNameUpcase".equals(propertyName)) {
              // Convert a path like /api/2/foo/bar/{}/baz to fooBarBazMethod
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
              if (supertype == null) {
                return BaseHasUuid.class.getCanonicalName();
              } else {
                return supertype;
              }
            }

            else if ("simpleName".equals(propertyName)) {
              if (entity.getTypeName().equals("baseHasUuid")) {
                return "Flatpack::Core::" + BaseHasUuid.class.getSimpleName();
              }
              return upcase(entity.getTypeName());
            }

            else if ("requireName".equals(propertyName)) {
              if (entity.getTypeName().equals("baseHasUuid")) {
                return "flatpack_core";
              }
              return gemName + "/" + camelCaseToUnderscore(modelModuleName) + "/"
                + camelCaseToUnderscore(entity.getTypeName());
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
            if ("javaNameUpcase".equals(propertyName)) {
              return upcase(param.getName());
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
        return super.getProperty(interp, self, o, property, propertyName);
      }
    });

    group.registerModelAdaptor(Type.class, new ObjectModelAdaptor() {

      @Override
      public Object getProperty(Interpreter interp, ST self, Object o,
          Object property, String propertyName)
          throws STNoSuchPropertyException {
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
              return Collections.singletonList(upcase(type.getName()));
            } else if (type.getTypeHint() != null) {
              return Collections.singletonList(type.getTypeHint().getValue());
            } else {
              return Collections.singletonList(String.class.getCanonicalName());
            }
          }
        }
        throw new UnsupportedOperationException("Unknown JsonKind "
          + type.getJsonKind());
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
}
