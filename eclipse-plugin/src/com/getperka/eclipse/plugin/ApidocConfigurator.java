/*
 * #%L
 * Perka Eclipse Customizations
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
package com.getperka.eclipse.plugin;

import org.apache.maven.plugin.MojoExecution;
import org.eclipse.m2e.core.lifecyclemapping.model.IPluginExecutionMetadata;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.configurator.AbstractBuildParticipant;
import org.eclipse.m2e.core.project.configurator.MojoExecutionBuildParticipant;
import org.eclipse.m2e.jdt.AbstractJavaProjectConfigurator;

/**
 * Runs an invocation of the ApiDoc plugin as a simple MojoExecution.
 */
public class ApidocConfigurator extends AbstractJavaProjectConfigurator {

  @Override
  public AbstractBuildParticipant getBuildParticipant(IMavenProjectFacade projectFacade,
      MojoExecution execution, IPluginExecutionMetadata executionMetadata) {
    return new MojoExecutionBuildParticipant(execution, true);
  }

}
