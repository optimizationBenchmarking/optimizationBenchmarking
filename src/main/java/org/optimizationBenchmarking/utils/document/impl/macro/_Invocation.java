package org.optimizationBenchmarking.utils.document.impl.macro;

import java.lang.reflect.Method;

/** a method invocation */
final class _Invocation {

  /** the object id */
  final int m_object;

  /** the method */
  final Method m_method;

  /** the parameters */
  final Object[] m_params;

  /**
   * the returned object index, or {@code -1} if no wrapped object is
   * returned
   */
  final int m_retObject;

  /**
   * Create the invocation
   *
   * @param object
   *          the object
   * @param method
   *          the method
   * @param params
   *          the parameters
   * @param retObject
   *          the returned object index, or {@code -1} if no wrapped object
   *          is returned
   */
  _Invocation(final int object, final Method method,
      final Object[] params, final int retObject) {
    super();
    this.m_object = object;
    this.m_method = method;
    this.m_params = params;
    this.m_retObject = retObject;
  }
}
