package org.optimizationBenchmarking.utils.predicates;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A predicate that returns {@code true} if two object variables reference
 * the same object.
 */
public final class SamePredicate extends HashObject implements
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
  public SamePredicate(final Object compare) {
    super();
    this.m_compare = compare;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final Object param) {
    return (param == this.m_compare);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || //
        ((o instanceof SamePredicate) && //
            (this.m_compare == (((SamePredicate) o).m_compare))));
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.hashCode(this.m_compare);
  }
}
