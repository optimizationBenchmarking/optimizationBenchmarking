package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.parsers.ClassParser;
import org.optimizationBenchmarking.utils.parsers.Parser;

/**
 * an instance parser
 * 
 * @param <T>
 *          the instance type
 */
final class _InstanceParser<T> extends Parser<T> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the base class */
  private final ClassParser<T> m_classParser;

  /**
   * create
   * 
   * @param baseClass
   *          the base class of the instance to return, used to check type
   *          consistency
   */
  _InstanceParser(final Class<T> baseClass) {
    super();

    if (baseClass == null) {
      throw new IllegalArgumentException(//
          "A base class must be provided."); //$NON-NLS-1$
    }
    this.m_classParser = new ClassParser<>(baseClass);
  }

  /**
   * parse a class
   * 
   * @param clazz
   *          the class
   * @return the instance
   * @throws ClassCastException
   *           if the class did not fit
   * @throws IllegalAccessException
   *           if the access was illegal
   * @throws InstantiationException
   *           the the instantiation failed
   */
  private final T __parseClass(final Class<? extends T> clazz)
      throws ClassCastException, InstantiationException,
      IllegalAccessException {
    final T ret;

    ret = clazz.newInstance();
    this.validate(ret);
    return ret;
  }

  /** {@inheritDoc} */
  @Override
  public final T parseString(final String string)
      throws ClassCastException, ClassNotFoundException,
      InstantiationException, IllegalAccessException {
    return this.__parseClass(this.m_classParser.parseString(string));
  }

  /** {@inheritDoc} */
  @Override
  public final T parseObject(final Object o)
      throws IllegalArgumentException, InstantiationException,
      IllegalAccessException, LinkageError, ExceptionInInitializerError,
      ClassNotFoundException, ClassCastException {
    final Class<T> base;
    final T obj;

    base = this.m_classParser.getBaseClass();
    if (base.isInstance(o)) {
      obj = base.cast(o);
      this.validate(obj);
      return obj;
    }

    return this.__parseClass(this.m_classParser.parseObject(o));
  }

  /** {@inheritDoc} */
  @Override
  public final void validate(final T instance)
      throws IllegalArgumentException {
    if (instance == null) {
      throw new IllegalArgumentException("Instance must not be null."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final Class<T> getOutputClass() {
    return this.m_classParser.getBaseClass();
  }
}
