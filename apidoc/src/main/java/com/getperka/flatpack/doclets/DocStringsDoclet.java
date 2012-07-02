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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
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
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.SeeTag;
import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Tag;

@SuppressWarnings("restriction")
public class DocStringsDoclet {
  private static final String EXAMPLE_TYPE_NAME = "com.getperka.flatpack.Example";
  private static final Charset UTF8 = Charset.forName("UTF8");

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

  private int braceCount(CharSequence chars, int count) {
    for (int i = 0, j = chars.length(); i < j; i++) {
      switch (chars.charAt(i)) {
        case '{':
          count++;
          break;
        case '}':
          count--;
          break;
      }
    }
    return count;
  }

  /**
   * Counts the number of leading whitespace characters to compute the amount of padding to apply to
   * extracted method contents. Returns {@link Integer#MAX_VALUE} if the string is empty or contains
   * only whitespace.
   */
  private int countInitialWhitespace(CharSequence chars) {
    for (int i = 0, j = chars.length(); i < j; i++) {
      if (!Character.isWhitespace(chars.charAt(i))) {
        return i;
      }
    }
    return Integer.MAX_VALUE;
  }

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

    String classKey = key(clazz);
    // Possibly extract the class's contents
    if (hasAnnotation(clazz, EXAMPLE_TYPE_NAME)) {
      String contents = extractContents(clazz);
      writer.name(classKey + ":contents");
      writer.value(contents);
    }
    String doc = docString(clazz);
    if (!doc.isEmpty()) {
      writer.name(classKey);
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
      String methodKey = key(m);

      // Possibly extract the method's contents
      if (hasAnnotation(m, EXAMPLE_TYPE_NAME)) {
        String contents = extractContents(m);
        writer.name(methodKey + ":contents");
        writer.value(contents);
      }

      doc = docString(m);
      if (doc.isEmpty()) {
        continue;
      }
      writer.name(methodKey);
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
        writer.name(methodKey + "[" + position + "]");
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

  private String extractContents(Doc doc) throws IOException {
    SourcePosition position = doc.position();
    File f = position.file();
    if (f == null) {
      return null;
    }

    BufferedReader r = new BufferedReader(new FileReader(f));
    for (int i = 0, j = position.line() - 1; i < j; i++) {
      r.readLine();
    }

    List<String> strings = new ArrayList<String>();
    int padCount = Integer.MAX_VALUE;
    int braceCount = 0;
    do {
      String line = r.readLine();
      braceCount = braceCount(line, braceCount);
      if (braceCount >= 0) {
        strings.add(line);
        padCount = Math.min(padCount, countInitialWhitespace(line));
      }
    } while (braceCount > 0);

    StringBuilder sb = new StringBuilder();
    boolean needsNewline = false;
    for (String s : strings) {
      if (needsNewline) {
        sb.append("\n");
      } else {
        needsNewline = true;
      }
      String toAppend;
      if (s.length() > padCount) {
        toAppend = s.substring(padCount);
      } else {
        toAppend = "";
      }
      sb.append(toAppend);
    }
    return sb.toString();
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

  private boolean hasAnnotation(ProgramElementDoc doc, String typeName) {
    for (AnnotationDesc annotation : doc.annotations()) {
      if (typeName.equals(annotation.annotationType().qualifiedTypeName())) {
        return true;
      }
    }
    return false;
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
