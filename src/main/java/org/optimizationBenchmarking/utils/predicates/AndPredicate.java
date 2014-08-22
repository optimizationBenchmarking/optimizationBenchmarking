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
public final class AndPredicate<T> extends HashObject implements
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
  public AndPredicate(final IPredicate<? super T> a,
      final IPredicate<? super T> b) {
    super();

    this.m_a = a;
    this.m_b = b;
  }

  /**
   * calculate the hash code
   * 
   * @return the hash code
   */
  protected final int calcHash() {
    return HashUtils.combineHashesUnsorted(//
        HashUtils.hashCode(this.m_a),//
        HashUtils.hashCode(this.m_b));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final T param) {
    return (this.m_a.check(param) && this.m_b.check(param));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public final boolean equals(final Object o) {
    final AndPredicate c;

    if (o == this) {
      return true;
    }

    if (o instanceof AndPredicate) {
      c = ((AndPredicate) o);
      return ((this.m_a.equals(c.m_a) && this.m_a.equals(c.m_b)) || //
      (this.m_a.equals(c.m_b) && this.m_a.equals(c.m_a)));
    }
    return false;
  }

}
