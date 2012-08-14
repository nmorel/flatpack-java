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
   * If {@code false}, the generated Api class will have package visibility.
   * 
   * @parameter default-value="true"
   */
  protected boolean apiIsPublic;

  /**
   * @component
   * @required
   * @readonly
   */
  protected BuildContext buildContext;

  /**
   * A file containing a JSON array that lists payload type names that should be generated as base
   * types to be implemented by a user-provided concrete type. DTOs.
   * <p>
   * 
   * <pre>
   * [ "employee", "manager" ]
   * </pre>
   * 
   * <pre>
   * public class Employee extends EmployeeBase {
   *   // Custom code goes here
   * }
   * </pre>
   * 
   * @parameter
   */
  protected File baseTypes;

  /**
   * The source dialect to emit.
   * 
   * @parameter default-value="java"
   */
  protected String dialect;

  /**
   * The destination directory for the generated source.
   * 
   * @parameter default-value="${project.build.directory}/generated-sources/flatpack"
   */
  protected File outputDirectory;

  /**
   * The package name the generated clasess should be emitted into.
   * 
   * @parameter default-value="com.getperka.fast"
   */
  protected String packageName;

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
   * The number of path segments to remove from the generated URLs when creating method names.
   * 
   * @parameter default-value="0"
   */
  protected int stripPathSegments;

  /**
   * An optional prefix to apply to all generated type names.
   * 
   * @parameter default-value=""
   */
  protected String typePrefix;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (!buildContext.hasDelta(source)) {
      return;
    }

    try {
      JavaDialect.apiIsPublic = apiIsPublic;
      JavaDialect.baseTypeArrayFile = baseTypes;
      JavaDialect.packageName = packageName;
      JavaDialect.stripPathSegments = stripPathSegments;
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
