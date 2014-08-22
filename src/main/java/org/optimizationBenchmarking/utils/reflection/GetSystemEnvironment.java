package org.optimizationBenchmarking.utils.reflection;

import java.security.AccessController;

import org.optimizationBenchmarking.utils.tasks.Task;

/**
 * The system environment-getting task
 */
public final class GetSystemEnvironment extends Task<String> {
  /** The file to canonicalize. */
  private final String m_name;

  /**
   * The constructor of the environment getter.
   * 
   * @param name
   *          the property to get
   */
  public GetSystemEnvironment(final String name) {
    super();
    this.m_name = name;
  }

  /** {@inheritDoc} */
  @Override
  public final String call() {
    try {
      return System.getenv(this.m_name);
    } catch (final Throwable t) {
      return AccessController.doPrivileged(//
          new _EnvironmentGetter(this.m_name));
    }
  }

}
