package org.optimizationBenchmarking.utils.reflection;

import java.security.PrivilegedAction;

/**
 * This small private class helps to get a system property by tunneling the
 * request through <code>PrivilegedAction</code>.
 */
final class _PropertyGetter implements PrivilegedAction<String> {
  /** The file to canonicalize. */
  private final String m_name;

  /**
   * The constructor of the property getter.
   * 
   * @param name
   *          the property to get
   */
  _PropertyGetter(final String name) {
    super();
    this.m_name = name;
  }

  /** {@inheritDoc} */
  @Override
  public final String run() {
    return System.getProperty(this.m_name);
  }

}
