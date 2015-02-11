package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.parsers.StringParser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/**
 * parse a constant
 * 
 * @param <T>
 *          the instance type
 */
final class _InstanceParser<T> extends Parser<T> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the base class */
  private final Class<T> m_base;

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
    this.m_base = baseClass;
  }

  /** {@inheritDoc} */
  @Override
  public final T parseString(final String string) throws Exception {
    final T fieldValue;

    fieldValue = ReflectionUtils.getInstanceByName(this.m_base,
        StringParser.INSTANCE.parseString(string));
    this.validate(fieldValue);
    return fieldValue;
  }

  /** {@inheritDoc} */
  @Override
  public final T parseObject(final Object o) throws Exception {
    final Class<T> base;
    final T obj;

    if (o == null) {
      return null;
    }

    base = this.m_base;
    if (base.isInstance(o)) {
      obj = base.cast(o);
      this.validate(obj);
      return obj;
    }

    return this.parseString(String.valueOf(o));
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
    return this.m_base;
  }
}
