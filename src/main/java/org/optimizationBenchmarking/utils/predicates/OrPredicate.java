package org.optimizationBenchmarking.utils.predicates;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * The combination of two predicates with logical {@code or}
 * 
 * @param <T>
 *          the element type this condition applies to.
 */
public final class OrPredicate<T> extends HashObject implements
    IPredicate<T>, Serializable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * the first predicate
   * 
   * @serial the first predicate to evaluate (must not be {@code null}
   */
  private final IPredicate<? super T> m_a;
  /**
   * the second predicate
   * 
   * @serial the second predicate to evaluate (must not be {@code null}
   */
  private final IPredicate<? super T> m_b;

  /**
   * The condition
   * 
   * @param a
   *          the first predicate to evaluate (must not be {@code null}
   * @param b
   *          the second predicate to evaluate (must not be {@code null}
   */
  public OrPredicate(final IPredicate<? super T> a,
      final IPredicate<? super T> b) {
    super();

    this.m_a = a;
    this.m_b = b;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashesUnsorted(//
        HashUtils.hashCode(this.m_a), //
        HashUtils.hashCode(this.m_b));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final T param) {
    return (this.m_a.check(param) || this.m_b.check(param));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public final boolean equals(final Object o) {
    final OrPredicate c;

    if (o == this) {
      return true;
    }

    if (o instanceof OrPredicate) {
      c = ((OrPredicate) o);
      return ((this.m_a.equals(c.m_a) && this.m_a.equals(c.m_b)) || //
      (this.m_a.equals(c.m_b) && this.m_a.equals(c.m_a)));
    }
    return false;
  }
}
