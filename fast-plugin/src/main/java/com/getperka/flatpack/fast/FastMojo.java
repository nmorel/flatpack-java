/*
 * #%L
 * FlatPack Automatic Source Tool Plugin
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
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.sonatype.plexus.build.incremental.BuildContext;

/**
 * Generate FlatPack API access libraries.
 * 
 * @goal fast
 * @phase generate-sources
 */
public class FastMojo extends AbstractMojo {
  /**
   * @component
   * @required
   * @readonly
   */
  protected BuildContext buildContext;

  /**
   * The destination directory for the generated source.
   * 
   * @parameter default-value="${project.build.directory}/generated-sources/flatpack"
   */
  protected File outputDirectory;

  /**
   * @parameter default-value="${project}"
   * @readonly
   * @required
   */
  protected MavenProject project;

  /**
   * The file to load the ApiDefinition from.
   * 
   * @parameter
   * @required
   */
  protected File source;

  /**
   * The source dialect to emit.
   * 
   * @parameter default-value="java"
   */
  protected String dialect;

  /**
   * The package name the generated clasess should be emitted into.
   * 
   * @parameter default-value="com.getperka.fast"
   */
  protected String packageName;

  /**
   * An optional prefix to apply to all generated type names.
   * 
   * @parameter
   */
  protected String typePrefix;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (!buildContext.hasDelta(source)) {
      return;
    }

    try {
      JavaDialect.packageName = packageName;
      JavaDialect.typePrefix = typePrefix == null ? "" : typePrefix;
      if (!new FastTool().generate(source.toURI(), dialect, outputDirectory)) {
        throw new MojoFailureException("Could not generate sources");
      }
    } catch (IOException e) {
      throw new MojoExecutionException("Could not run code generator", e);
    }
    addSource();
  }

  protected void addSource() {
    project.addCompileSourceRoot(outputDirectory.getAbsolutePath());
    buildContext.refresh(outputDirectory);
  }

}
