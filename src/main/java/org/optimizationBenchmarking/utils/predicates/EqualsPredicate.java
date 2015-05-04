package org.optimizationBenchmarking.utils.predicates;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A predicate that returns {@code true} if two objects equal each other.
 */
public final class EqualsPredicate extends HashObject implements
IPredicate<Object>, Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * The object to compare with
   *
   * @serial the object to compare with
   */
  private final Object m_compare;

  /**
   * create
   *
   * @param compare
   *          the object to compare with
   */
  public EqualsPredicate(final Object compare) {
    super();
    this.m_compare = compare;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final Object param) {
    return ((param == this.m_compare) || //
        ((param != null) && param.equals(this.m_compare)));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || //
        ((o instanceof EqualsPredicate) && //
            (this.check(((EqualsPredicate) o).m_compare))));
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.hashCode(this.m_compare);
  }
}
