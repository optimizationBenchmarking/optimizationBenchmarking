package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.parsers.StringParser;
import org.optimizationBenchmarking.utils.reflection.GetStaticConstant;
import org.optimizationBenchmarking.utils.reflection.GetStaticConstantByName;
import org.optimizationBenchmarking.utils.tasks.Task;

/**
 * parse a constant
 * 
 * @param <T>
 *          the instance type
 */
final class _ConstantParser<T> extends Parser<T> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the owning class */
  private final Class<?> m_owning;
  /** the base class */
  private final Class<T> m_base;

  /**
   * create
   * 
   * @param owning
   *          the owning class
   * @param baseClass
   *          the base class of the instance to return, used to check type
   *          consistency
   */
  _ConstantParser(final Class<?> owning, final Class<T> baseClass) {
    super();

    if (baseClass == null) {
      throw new IllegalArgumentException(//
          "A base class must be provided."); //$NON-NLS-1$
    }
    this.m_base = baseClass;
    this.m_owning = owning;
  }

  /** {@inheritDoc} */
  @Override
  public final T parseString(final String string) throws Exception {
    final T t;
    final Task<T> task;
    final String s;

    s = StringParser.INSTANCE.parseString(string);

    if (this.m_owning == null) {
      task = new GetStaticConstantByName<>(s, this.m_base);
    } else {
      task = new GetStaticConstant<>(this.m_owning, s, this.m_base);
    }
    t = task.call();
    this.validate(t);
    return t;
  }

  /** {@inheritDoc} */
  @Override
  public final T parseObject(final Object o) throws Exception {
    final Class<T> base;
    final T obj;

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
