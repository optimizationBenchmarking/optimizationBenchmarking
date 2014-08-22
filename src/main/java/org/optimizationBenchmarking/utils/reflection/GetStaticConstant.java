package org.optimizationBenchmarking.utils.reflection;

import org.optimizationBenchmarking.utils.tasks.Task;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * Get the value of a static constant
 * 
 * @param <T>
 *          the type of the constant
 */
public final class GetStaticConstant<T> extends Task<T> {

  /** the class */
  private final Class<?> m_clazz;

  /** the constant's name */
  private final String m_name;

  /** the base class to cast to */
  private final Class<T> m_base;

  /**
   * Create the get-static-constant action
   * 
   * @param clazz
   *          the class which hosts the constant
   * @param name
   *          the constant's name
   */
  public GetStaticConstant(final Class<?> clazz, final String name) {
    this(clazz, name, null);
  }

  /**
   * Create the get-static-constant action
   * 
   * @param clazz
   *          the class which hosts the constant
   * @param name
   *          the constant's name
   * @param base
   *          the type of the constant
   */
  public GetStaticConstant(final Class<?> clazz, final String name,
      final Class<T> base) {
    super();
    this.m_clazz = clazz;
    this.m_name = name;
    this.m_base = base;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final T call() throws NoSuchFieldException, SecurityException,
      IllegalArgumentException, IllegalAccessException {
    final Object t;

    t = this.m_clazz.getField(TextUtils.prepare(this.m_name)).get(null);

    if (this.m_base == null) {
      return ((T) t);
    }

    return this.m_base.cast(t);
  }
}
