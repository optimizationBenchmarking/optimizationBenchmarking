package org.optimizationBenchmarking.utils.predicates;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * The negation of a condition
 * 
 * @param <T>
 *          the element type this condition applies to.
 */
public final class NotPredicate<T> extends HashObject implements
    IPredicate<T>, Serializable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * the predicate to negate
   * 
   * @serial the predicate to negate (must not be {@code null}
   */
  private final IPredicate<? super T> m_c;

  /**
   * The condition
   * 
   * @param c
   *          the predicate to negate (must not be {@code null}
   */
  public NotPredicate(final IPredicate<? super T> c) {
    super();

    this.m_c = c;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.hashCode(this.m_c);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final T param) {
    return (!(this.m_c.check(param)));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public final boolean equals(final Object o) {
    final NotPredicate c;

    if (o == this) {
      return true;
    }

    if (o instanceof NotPredicate) {
      c = ((NotPredicate) o);
      return (this.m_c.equals(c.m_c));
    }
    return false;
  }
}
