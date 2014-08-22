package org.optimizationBenchmarking.utils.reflection;

import org.optimizationBenchmarking.utils.tasks.Task;

/**
 * Get the value of a static constant by its name.
 * 
 * @param <T>
 *          the constant type
 */
public final class GetStaticConstantByName<T> extends Task<T> {

  /** the identifier */
  private final String m_identifier;

  /** the base class */
  private final Class<T> m_base;

  /**
   * Create the get-static-constant-by-name action
   * 
   * @param identifier
   *          the fully qualified identifier of the constant
   * @param base
   *          the type of the constant
   */
  public GetStaticConstantByName(final String identifier,
      final Class<T> base) {
    super();
    this.m_identifier = identifier;
    this.m_base = base;
  }

  /** {@inheritDoc} */
  @Override
  public final T call() throws NoSuchFieldException, SecurityException,
      IllegalArgumentException, IllegalAccessException, LinkageError,
      ExceptionInInitializerError, ClassNotFoundException,
      ClassCastException {
    int idx;

    idx = this.m_identifier.lastIndexOf('#');
    if (idx < 0) {
      idx = this.m_identifier.lastIndexOf('.');
    }

    return new GetStaticConstant<>(//
        new FindClass<>(this.m_identifier.substring(0, idx), null).call(),//
        this.m_identifier.substring(idx + 1),//
        this.m_base).call();
  }
}
