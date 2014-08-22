package org.optimizationBenchmarking.utils.reflection;

import java.security.PrivilegedAction;

/**
 * This small private class helps to get a system environment value by
 * tunneling the request through <code>PrivilegedAction</code>.
 */
final class _EnvironmentGetter implements PrivilegedAction<String> {
  /** The file to canonicalize. */
  private final String m_name;

  /**
   * The constructor of the property getter.
   * 
   * @param name
   *          the property to get
   */
  _EnvironmentGetter(final String name) {
    super();
    this.m_name = name;
  }

  /** {@inheritDoc} */
  @Override
  public final String run() {
    return System.getenv(this.m_name);
  }

}
