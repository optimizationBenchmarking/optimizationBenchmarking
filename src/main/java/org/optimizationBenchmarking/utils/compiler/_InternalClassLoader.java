package org.optimizationBenchmarking.utils.compiler;

import java.security.SecureClassLoader;

/** an internal class loader */
final class _InternalClassLoader extends SecureClassLoader {

  /** create */
  _InternalClassLoader() {
    super();
  }

  /**
   * add a class
   *
   * @param name
   *          the class name
   * @param bajfo
   *          the file object
   */
  final void _add(final String name, final _ByteArrayJavaFileObject bajfo) {
    final byte[] b;
    b = bajfo.getBytes();
    this.defineClass(name, b, 0, b.length);
  }

  /**
   * load a class
   *
   * @param name
   *          the class name
   */
  final void _load(final String name) {
    try {
      this.loadClass(name);
    } catch (final ClassNotFoundException cnfe) {
      throw new RuntimeException(//
          "The compiled class " + name + //$NON-NLS-1$
              " was defined, but could not be loaded, which is very odd.",//$NON-NLS-1$
          cnfe);
    }
  }
}
