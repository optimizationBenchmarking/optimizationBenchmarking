package org.optimizationBenchmarking.utils.math.text;

import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.BasicNumber;

/** A named long constant */
public final class NamedLongConstant extends StringNamedConstant {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the value */
  private final long m_value;

  /**
   * create the long constant
   *
   * @param value
   *          the value
   * @param name
   *          the name of the constant
   */
  NamedLongConstant(final long value, final String name) {
    super(name);
    this.m_value = value;
  }

  /** {@inheritDoc} */
  @Override
  public final int getState() {
    return BasicNumber.STATE_INTEGER;
  }

  /** {@inheritDoc} */
  @Override
  public final long longValue() {
    return this.m_value;
  }

  /** {@inheritDoc} */
  @Override
  public final double doubleValue() {
    return this.m_value;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_value),
        HashUtils.hashCode(this.m_name));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final NamedLongConstant other;
    if (o == this) {
      return true;
    }
    if (o instanceof NamedLongConstant) {
      other = ((NamedLongConstant) o);
      return (((this.m_value == other.m_value) && //
          this.m_name.equals(other.m_name)));
    }
    return false;
  }
}
