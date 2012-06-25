/*
 * #%L
 * Javadoc tool plugins
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
package com.getperka.flatpack.doclets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.stream.JsonWriter;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.SeeTag;
import com.sun.javadoc.Tag;

@SuppressWarnings("restriction")
public class DocStringsDoclet {
  public static LanguageVersion languageVersion() {
    return LanguageVersion.JAVA_1_5;
  }

  public static void main(String[] args) {
    List<String> args2 = new ArrayList<String>();
    args2.add("-doclet");
    args2.add(DocStringsDoclet.class.getName());
    args2.addAll(Arrays.asList(args));
    com.sun.tools.javadoc.Main.execute(args2.toArray(new String[args2.size()]));
  }

  public static int optionLength(String option) {
    if ("-d".equals(option)) {
      return 2;
    }
    return 0;
  }

  public static boolean start(RootDoc root) {
    try {
      new DocStringsDoclet().exec(root);
    } catch (Throwable e) {
      root.printError(e.getClass().getName() + " : " + e.getMessage());
      e.printStackTrace();
    }
    return true;
  }

  private File outputDir;
  private Map<PackageDoc, JsonWriter> writersByPackage = new HashMap<PackageDoc, JsonWriter>();

  private static final Charset UTF8 = Charset.forName("UTF8");

  private String docString(Doc doc) {
    return docString(doc.inlineTags());
  }

  private String docString(ParamTag tag) {
    return docString(tag.inlineTags());
  }

  private String docString(Tag[] inlineTags) {
    StringBuilder text = new StringBuilder();
    for (Tag tag : inlineTags) {
      if ("Text".equals(tag.kind())) {
        text.append(tag.text());
      } else if ("@see".equals(tag.kind())) {
        SeeTag see = (SeeTag) tag;
        text.append("{").append(tag.name()).append(" ");
        if (see.referencedMember() != null) {
          text.append(key(see.referencedMember()));
        } else if (see.referencedClass() != null) {
          text.append(key(see.referencedClass()));
        }
        String tagText = tag.text();
        int idx = tagText.indexOf(' ');
        // Pull out "ModelType some other test" -> "some other text"
        if (idx != -1) {
          tagText = tagText.substring(idx + 1);
        }
        text.append(" ").append(tagText).append("}");
      } else {
        // Append all other tags as though they're html, e.g. {@code foo}
        String tagName = tag.name().substring(1);
        text.append("<" + tagName + ">" + tag.text() + "</" + tagName + ">");
      }
    }
    return text.toString();
  }

  private void examineClass(ClassDoc clazz) throws IOException {
    JsonWriter writer = getJsonWriter(clazz);

    String doc = docString(clazz);
    if (!doc.isEmpty()) {
      writer.name(key(clazz));
      writer.value(doc);
    }

    for (FieldDoc f : clazz.fields(true)) {
      doc = docString(f);
      if (doc.isEmpty()) {
        continue;
      }
      writer.name(key(f));
      writer.value(doc);
    }

    for (MethodDoc m : clazz.methods(true)) {
      doc = docString(m);
      if (doc.isEmpty()) {
        continue;
      }
      writer.name(key(m));
      writer.value(doc);

      Map<String, Integer> namesToPositions = new HashMap<String, Integer>();
      for (Parameter param : m.parameters()) {
        namesToPositions.put(param.name(), namesToPositions.size());
      }
      for (ParamTag tag : m.paramTags()) {
        Integer position = namesToPositions.get(tag.parameterName());
        // Handle @param tags for non-existant parameters
        if (position == null) {
          continue;
        }
        writer.name(key(m) + "[" + position + "]");
        writer.value(docString(tag));
      }
    }
  }

  private void exec(RootDoc root) throws IOException {
    extractOptions(root);
    for (ClassDoc clazz : root.classes()) {
      examineClass(clazz);
    }
    for (JsonWriter writer : writersByPackage.values()) {
      writer.endObject();
      writer.close();
    }
  }

  private void extractOptions(RootDoc doc) {
    for (String[] option : doc.options()) {
      String name = option[0];
      if ("-d".equals(name)) {
        outputDir = new File(option[1]);
        outputDir.mkdirs();
      }
    }
  }

  private JsonWriter getJsonWriter(ClassDoc clazz) throws IOException {
    PackageDoc pkg = clazz.containingPackage();
    JsonWriter toReturn = writersByPackage.get(pkg);
    if (toReturn == null) {
      toReturn = new JsonWriter(openWriter(pkg.name().replace('.', '/') + "/package.json"));
      toReturn.setIndent("  ");
      writersByPackage.put(pkg, toReturn);

      toReturn.beginObject();
    }
    return toReturn;
  }

  private String key(ClassDoc clazz) {
    return clazz.qualifiedName();
  }

  private String key(Doc doc) {
    if (doc instanceof ClassDoc) {
      return key((ClassDoc) doc);
    } else if (doc instanceof FieldDoc) {
      return key((FieldDoc) doc);
    } else if (doc instanceof MethodDoc) {
      return key((MethodDoc) doc);
    }
    throw new IllegalArgumentException("Unspported type " + doc.getClass().getName());
  }

  private String key(FieldDoc field) {
    return key(field.containingClass()) + ":" + field.name();
  }

  private String key(MethodDoc method) {
    return key(method.containingClass()) + ":" + method.name() + method.signature();
  }

  /**
   * Returns a PrintWriter for writing to the named file in the output directory.
   */
  private Writer openWriter(String relativePath) throws IOException {
    File outputFile = outputFile(relativePath);
    outputFile.getParentFile().mkdirs();
    return new OutputStreamWriter(new FileOutputStream(outputFile), UTF8);
  }

  private File outputFile(String relativePath) {
    return new File(outputDir, relativePath);
  }
}
