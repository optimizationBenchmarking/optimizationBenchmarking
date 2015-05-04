package org.optimizationBenchmarking.utils.predicates;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A condition that is constant.
 */
public final class ConstantPredicate extends HashObject implements
    IPredicate<Object>, Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the {@code true} condition */
  public static final ConstantPredicate TRUE = new ConstantPredicate(true);

  /** the {@code false} condition */
  public static final ConstantPredicate FALSE = new ConstantPredicate(
      false);

  /**
   * the value
   *
   * @serial the boolean value returned by the predicate
   */
  private final boolean m_value;

  /**
   * create
   *
   * @param value
   *          the value
   */
  private ConstantPredicate(final boolean value) {
    super();
    this.m_value = value;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.hashCode(this.m_value);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final Object param) {
    return this.m_value;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || ((o instanceof ConstantPredicate) && (((ConstantPredicate) o).m_value == this.m_value)));
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return (this.m_value ? ConstantPredicate.TRUE
        : ConstantPredicate.FALSE);
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return (this.m_value ? ConstantPredicate.TRUE
        : ConstantPredicate.FALSE);
  }

}
