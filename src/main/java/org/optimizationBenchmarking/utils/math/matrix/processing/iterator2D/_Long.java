package org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D;

import org.optimizationBenchmarking.utils.math.BasicNumber;

/** A modifiable {@code long} value. */
final class _Long extends _Number {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;
  /** the value */
  private long m_value;

  /** create */
  _Long() {
    super();
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
  final void _setLongValue(final long value) {
    this.m_value = value;
  }
}
