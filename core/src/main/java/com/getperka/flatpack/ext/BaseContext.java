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

import static com.getperka.flatpack.util.FlatPackCollections.listForAny;
import static com.getperka.flatpack.util.FlatPackCollections.mapForIteration;

import java.io.Closeable;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import com.getperka.flatpack.FlatPackEntity;
import com.getperka.flatpack.HasUuid;

/**
 * Contains common data that affects the serialization process.
 * <p>
 * This class also provides an error-assignment mechanism that tracks the current path of the
 * serializer/deserializer through the object graph. When writing a {@link Codex} subtype, the
 * following pattern should be used:
 *
 * <pre>
 * void read/write(BaseContext context) {
 *   context.pushPath("useful information");
 *   try {
 *     // Do work, possibly utilizing other Codexes
 *   } catch (CheckedException e) {
 *     context.fail(e);
 *   } finally {
 *     context.popPath();
 *   }
 * }
 * </pre>
 */
public abstract class BaseContext implements Closeable {

  private final Deque<String> path = new ArrayDeque<String>();
  private final List<Callable<?>> postWork = listForAny();

  @Inject
  private Principal principal;
  @Inject
  private PrincipalMapper principalMapper;
  private final Map<UUID, String> warnings = mapForIteration();

  BaseContext() {
    path.addLast("<root>");
  }

  /**
   * Add a Callable to be executed when the (de)-serialization pass is completed. This is used
   * primarily for fixing up "implied" properties across on-to-many relationships.
   */
  public void addPostWork(Callable<?> r) {
    postWork.add(r);
  }

  /**
   * Add a warning message to be reported via the {@link FlatPackEntity} being processed.
   */
  public void addWarning(HasUuid entity, String format, Object... args) {
    warnings.put(entity.getUuid(), String.format(format, args));
  }

  @Override
  public void close() throws IOException {}

  /**
   * Updates the exception's stack trace with the current path elements and performs a "sneaky"
   * throw to continue to propagate the (checked) exception up the stack.
   */
  public void fail(Throwable e) {
    StackTraceElement[] stack = e.getStackTrace();
    if (!"FlatPack".equals(stack[0].getClassName())) {
      StackTraceElement[] newStack = new StackTraceElement[stack.length + 1];
      newStack[0] = new StackTraceElement("FlatPack", toString(), null, 0);
      System.arraycopy(stack, 0, newStack, 1, stack.length);
      e.setStackTrace(newStack);
    }
    this.<RuntimeException> sneakyThrow(e);
  }

  public Principal getPrincipal() {
    return principal;
  }

  public List<String> getRoles() {
    PrincipalMapper mapper = principalMapper;
    List<String> toReturn = null;
    if (principal != null && mapper != null) {
      toReturn = mapper.getRoles(principal);
    }
    return toReturn == null ? Collections.<String> emptyList() :
        Collections.unmodifiableList(toReturn);
  }

  public Map<UUID, String> getWarnings() {
    return Collections.unmodifiableMap(warnings);
  }

  /**
   * Removes the topmost path.
   *
   * @return the removed path element
   */
  public String popPath() {
    return path.removeLast();
  }

  /**
   * Adds a path to the error-reporting stack.
   *
   * @param element the path element to add
   */
  public void pushPath(String element) {
    path.addLast(element);
  }

  /**
   * Executes all work items passed ino {@link #addPostWork(Callable)}.
   */
  public void runPostWork() {
    // Sort work by priority
    Collections.sort(postWork, new PostWorkComparator());
    for (Callable<?> callable : postWork) {
      pushPath("<postWork>" + callable.getClass().getName());
      try {
        callable.call();
      } catch (Exception e) {
        fail(e);
      } finally {
        popPath();
      }
    }
    postWork.clear();
  }

  /**
   * Returns the current error-reporting stack.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (String p : path) {
      sb.append(p);
    }
    return sb.toString();
  }

  /**
   * A method to throw a possibly checked exception.
   */
  @SuppressWarnings("unchecked")
  private <T extends Throwable> void sneakyThrow(Throwable t) throws T {
    throw (T) t;
  }
}