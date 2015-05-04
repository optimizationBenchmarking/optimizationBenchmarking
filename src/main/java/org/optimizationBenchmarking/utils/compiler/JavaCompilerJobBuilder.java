package org.optimizationBenchmarking.utils.compiler;

import java.util.HashMap;

import javax.tools.JavaFileObject;

import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;

/**
 * A job builder for creating java code on the fly.
 */
public final class JavaCompilerJobBuilder extends
ToolJobBuilder<JavaCompilerJob, JavaCompilerJobBuilder> {

  /** the files to compile */
  private final HashMap<String, JavaFileObject> m_files;

  /** create */
  JavaCompilerJobBuilder() {
    super();
    this.m_files = new HashMap<>();
  }

  /**
   * Add a class to this java compiler job
   *
   * @param name
   *          the class name
   * @param body
   *          the class body
   * @return this builder
   */
  public final JavaCompilerJobBuilder addClass(final String name,
      final CharSequence body) {
    final String useName, useBody;

    useName = TextUtils.normalize(name);
    if (useName == null) {
      throw new IllegalArgumentException("Name '" + name + //$NON-NLS-1$
          "' is not a valid class name.");//$NON-NLS-1$
    }

    if (body == null) {
      throw new IllegalArgumentException(//
          "Class '" + name + //$NON-NLS-1$
          "' cannot have a null body."); //$NON-NLS-1$
    }
    useBody = TextUtils.prepare(body.toString());
    if (useBody == null) {
      throw new IllegalArgumentException(//
          "The characer sequence holding the body of class '" + name + //$NON-NLS-1$
          "' returns the invalid body '" + useBody//$NON-NLS-1$
          + "' from toString()."); //$NON-NLS-1$

    }
    synchronized (this.m_files) {
      if (!(this.m_files.containsKey(name))) {
        this.m_files.put(name, new _CharSequenceJavaFileObject(useName,
            useBody));
        return this;
      }
    }

    throw new IllegalStateException("Class of name '" + //$NON-NLS-1$
        name + "' already exists."); //$NON-NLS-1$
  }

  /**
   * Create the java compiler job
   *
   * @return the java compiler job
   */
  @Override
  public final JavaCompilerJob create() {
    this.validate();
    synchronized (this.m_files) {
      return new JavaCompilerJob(this.getLogger(), this.m_files.values());
    }
  }
}
