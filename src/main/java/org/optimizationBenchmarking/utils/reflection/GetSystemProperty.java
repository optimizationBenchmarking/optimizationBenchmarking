package org.optimizationBenchmarking.utils.reflection;

import java.security.AccessController;

import org.optimizationBenchmarking.utils.tasks.Task;

/**
 * The system property-getting task
 */
public final class GetSystemProperty extends Task<String> {
  /** The file to canonicalize. */
  private final String m_name;

  /**
   * The constructor of the property getter.
   * 
   * @param name
   *          the property to get
   */
  public GetSystemProperty(final String name) {
    super();
    this.m_name = name;
  }

  /** {@inheritDoc} */
  @Override
  public final String call() {
    try {
      return System.getProperty(this.m_name);
    } catch (final Throwable t) {
      return AccessController
          .doPrivileged(new _PropertyGetter(this.m_name));
    }
  }

}
