/*
 * #%L
 * API Documentation Plugin
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
package com.getperka.flatpack.apidoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.Scanner;
import org.sonatype.plexus.build.incremental.BuildContext;
import org.tautua.markdownpapers.HtmlEmitter;
import org.tautua.markdownpapers.ast.Code;
import org.tautua.markdownpapers.ast.CodeText;
import org.tautua.markdownpapers.ast.Document;
import org.tautua.markdownpapers.ast.Header;
import org.tautua.markdownpapers.ast.ParserTreeConstants;
import org.tautua.markdownpapers.ast.Tag;
import org.tautua.markdownpapers.ast.TagAttribute;
import org.tautua.markdownpapers.ast.Text;
import org.tautua.markdownpapers.parser.ParseException;
import org.tautua.markdownpapers.parser.Parser;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

/**
 * Generate API documentation using a Doclet and include it in the project's output.
 * 
 * @goal apidoc
 * @phase process-sources
 * @threadSafe
 */
public class ApidocMojo extends AbstractMojo {
  /**
   * A subclass of HtmlEmitter that spies on the document to retrieve the first header to use as a
   * title string.
   */
  private class TitleSpyHtmlEmitter extends HtmlEmitter {
    private boolean seenHeader;
    private boolean spyOnText;
    private final StringBuilder text = new StringBuilder();

    private TitleSpyHtmlEmitter(Appendable buffer) {
      super(buffer);
    }

    public String getTitle() {
      return text.toString().trim();
    }

    @Override
    public void visit(Header node) {
      boolean doIt = !seenHeader;
      if (node.getLevel() == 1 && doIt) {
        seenHeader = true;
        spyOnText = true;
      }
      super.visit(node);
      if (doIt) {
        spyOnText = false;
      }
    }

    @Override
    public void visit(Tag node) {
      if (!"example".equals(node.getName())) {
        super.visit(node);
        return;
      }
      String className = null;
      String methodDesc = null;
      for (TagAttribute attr : node.getAttributes()) {
        if ("class".equals(attr.getName())) {
          className = attr.getValue();
        } else if ("method".equals(attr.getName())) {
          methodDesc = attr.getValue();
        }
      }
      String contents;

      String key = className + ":" + methodDesc + ":contents";
      String packageName = className.substring(0, className.lastIndexOf('.'));
      File f = new File(outputDirectory, packageName.replace('.', '/') + "/package.json");
      if (f.canRead()) {
        InputStreamReader reader;
        try {
          reader = new InputStreamReader(new FileInputStream(f), UTF8);
        } catch (FileNotFoundException e) {
          // The canRead() above should prevent this
          throw new RuntimeException(e);
        }
        JsonObject obj = new Gson().fromJson(reader, JsonElement.class).getAsJsonObject();
        if (obj.has(key)) {
          contents = obj.get(key).getAsString();
        } else {
          contents = "No @Example annotation? " + key;
        }
      } else {
        contents = "Cannot read " + f.getPath();
      }

      CodeText codeText = new CodeText(ParserTreeConstants.JJTCODETEXT);
      codeText.append(contents);
      Code code = new Code(ParserTreeConstants.JJTCODE);
      code.jjtAddChild(codeText, 0);
      visit(code);
    }

    @Override
    public void visit(Text node) {
      if (spyOnText) {
        if (node.getValue() != null) {
          text.append(node.getValue());
        }
      }
      super.visit(node);
    }
  }

  /**
   * Used to look up Artifacts in the remote repository.
   * 
   * @component
   * @required
   * @readonly
   */
  protected ArtifactResolver artifactResolver;

  /**
   * Used to look up Artifacts in the remote repository.
   * 
   * @component
   * @required
   * @readonly
   */
  protected ArtifactFactory factory;

  /**
   * Location of the local repository.
   * 
   * @parameter expression="${localRepository}"
   * @readonly
   * @required
   */
  protected ArtifactRepository localRepository;

  /**
   * List of Remote Repositories to be used by the resolver.
   * 
   * @parameter expression="${project.remoteArtifactRepositories}"
   * @readonly
   * @required
   */
  @SuppressWarnings("rawtypes")
  protected List remoteRepositories;

  /**
   * Used to look up Artifacts in the remote repository.
   * 
   * @component
   * @required
   * @readonly
   */
  protected ArtifactMetadataSource source;

  /**
   * A source directory for extra files to add to the api documentation. If this directory exists,
   * its contents will be included in the generated apidoc.
   * 
   * @parameter default-value="${basedir}/src/main/apidoc"
   */
  private File apidocDirectory;

  /**
   * The name of the Doclet to execute
   * 
   * @parameter
   * @required
   */
  private String docletClass;

  /**
   * @component
   * @required
   * @readonly
   */
  private BuildContext buildContext;

  /**
   * The destination directory for the generated documentation.
   * 
   * @parameter default-value="${project.build.directory}/apidoc"
   */
  private File outputDirectory;

  /**
   * @parameter default-value="${project}"
   * @readonly
   * @required
   */
  private MavenProject project;

  /**
   * The source directory for the java source files. This path will be used to supply the javadoc
   * tool's input.
   * 
   * @parameter default-value="${basedir}/src/main/java"
   */
  private File sourceDirectory;

  /**
   * The packages over which the doclet should be executed.
   * 
   * @parameter
   * @required
   */
  private String subpackages;

  private static final Charset UTF8 = Charset.forName("UTF-8");

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (!buildContext.isIncremental()) {
      extractDocStrings();
    }

    convertMarkdown();
  }

  private void convertMarkdown() {
    if (!apidocDirectory.isDirectory()) {
      return;
    }

    // The directory that the html and resources are emitted into
    File apidocOutputDir = new File(outputDirectory, "apidoc");

    // Copy random resources
    Scanner copyScanner = buildContext.newScanner(apidocDirectory, true);
    copyScanner.setExcludes(new String[] { "apidoc_template.html", "**/*.md" });
    copyScanner.scan();
    for (String copyPath : copyScanner.getIncludedFiles()) {
      File copyFrom = new File(apidocDirectory, copyPath);
      File outFile = new File(apidocOutputDir, copyPath);
      if (buildContext.isUptodate(outFile, copyFrom)) {
        continue;
      }
      try {
        InputStream in = new FileInputStream(copyFrom);
        outFile.getParentFile().mkdirs();
        OutputStream out = buildContext.newFileOutputStream(outFile);
        IOUtil.copy(in, out);
        in.close();
        out.close();
      } catch (IOException e) {
        buildContext.addMessage(copyFrom, 0, 0, "Could not copy resource",
            BuildContext.SEVERITY_ERROR, e);
      }
    }

    // Look for all changed *.md files
    Scanner inputScanner = buildContext.newScanner(apidocDirectory, false);
    inputScanner.setIncludes(new String[] { "**/*.md" });
    inputScanner.scan();
    String[] dirtyPaths = inputScanner.getIncludedFiles();
    if (dirtyPaths.length == 0) {
      return;
    }

    apidocOutputDir.mkdirs();

    // Get the manifest from a previous run
    @SuppressWarnings("unchecked")
    // A map of file paths to titles
    Map<String, String> manifest = (Map<String, String>) buildContext.getValue("manifestMap");
    if (manifest == null) {
      manifest = new TreeMap<String, String>();
      buildContext.setValue("manifestMap", manifest);
    }

    for (String relativePath : dirtyPaths) {
      File mdFile = new File(apidocDirectory, relativePath);
      try {
        Reader in = new InputStreamReader(new FileInputStream(mdFile), UTF8);

        String relativeFragmentPath = relativePath.substring(0,
            relativePath.lastIndexOf('.')) + ".htmlf";
        File outFile = new File(apidocOutputDir, relativeFragmentPath);

        // Make sure the parent diretory exists
        outFile.getParentFile().mkdirs();
        Writer out = new OutputStreamWriter(buildContext.newFileOutputStream(outFile), UTF8);

        Parser parser = new Parser(in);
        TitleSpyHtmlEmitter emitter = new TitleSpyHtmlEmitter(out);
        Document document = parser.parse();
        document.accept(emitter);

        in.close();
        out.close();

        manifest.put(relativeFragmentPath, emitter.getTitle());
      } catch (ParseException e) {
        buildContext.addMessage(mdFile, 0, 0, "Could not parse markdown",
            BuildContext.SEVERITY_ERROR, e);
      } catch (Exception e) {
        buildContext.addMessage(mdFile, 0, 0, "Error processing file", BuildContext.SEVERITY_ERROR,
            e);
      }
    }

    // Write a simple manifest file of all fragments
    try {
      File manifestFile = new File(apidocOutputDir, "manifest");
      JsonWriter writer = new JsonWriter(new OutputStreamWriter(
          buildContext.newFileOutputStream(manifestFile), UTF8));
      writer.beginObject();
      for (Map.Entry<String, String> entry : manifest.entrySet()) {
        writer.name(entry.getKey());
        writer.value(entry.getValue());
      }
      writer.endObject();
      writer.close();
    } catch (Exception e) {
      buildContext.addMessage(apidocDirectory, 0, 0, "Colud not generate manifest",
          BuildContext.SEVERITY_ERROR, e);
    }
  }

  @SuppressWarnings({ "restriction", "unchecked" })
  private void extractDocStrings() throws MojoFailureException {
    Artifact jar = factory.createArtifact(project.getGroupId(), project.getArtifactId(),
        project.getVersion(), "compile", "jar");
    Set<Artifact> artifacts;
    try {
      ArtifactResolutionResult res = artifactResolver.resolveTransitively(
          project.getDependencyArtifacts(),
          jar, remoteRepositories,
          localRepository, source);
      artifacts = res.getArtifacts();
    } catch (ArtifactNotFoundException e) {
      throw new MojoFailureException("Could not resolve jar", e);
    } catch (ArtifactResolutionException e) {
      throw new MojoFailureException("Could not resolve jar", e);
    }
    getLog().debug("Resolved " + artifacts.toString());
    List<String> args = new ArrayList<String>();

    StringBuilder sb = new StringBuilder();
    for (Artifact a : artifacts) {
      sb.append(":").append(a.getFile().getAbsolutePath());
    }

    args.add("-classpath");
    args.add(sb.substring(1));
    args.add("-doclet");
    args.add(docletClass);
    args.add("-sourcepath");
    args.add(sourceDirectory.getAbsolutePath());
    args.add("-subpackages");
    args.add(subpackages);
    args.add("-d");
    args.add(outputDirectory.getAbsolutePath());

    int ret = com.sun.tools.javadoc.Main.execute(args.toArray(new String[args.size()]));
    if (ret != 0) {
      throw new MojoFailureException("Javadoc tool returned status code " + ret);
    }

    Resource resource = new Resource();
    resource.setDirectory(outputDirectory.getPath());
    project.addResource(resource);

    buildContext.refresh(outputDirectory);
  }
}
