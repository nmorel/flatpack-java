/*
 * #%L
 * Classpath Search TypeSource
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
package com.getperka.flatpack.search;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.getperka.cli.classpath.Search;
import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.TypeSource;
import com.getperka.flatpack.util.FlatPackCollections;

/**
 * A TypeSource that uses a runtime scan of the JVM's classpath.
 */
public class SearchTypeSource implements TypeSource {
  private List<String> packageNames;

  /**
   * Searches the entire classpath for HasUuid types.
   */
  public SearchTypeSource() {}

  public SearchTypeSource(List<String> packageNames) {
    this.packageNames = packageNames;
  }

  public SearchTypeSource(String... packageNames) {
    this(Arrays.asList(packageNames));
  }

  @Override
  public Set<Class<?>> getTypes() {
    Set<Class<?>> toReturn = FlatPackCollections.setForIteration();
    Search<?> search = Search.forClasses()
        .assignableTo(HasUuid.class)
        .into(toReturn);
    if (packageNames != null) {
      search.acrossPackages(packageNames);
    }
    search.execute();
    return toReturn;
  }

}
