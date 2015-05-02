package org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.NumericalTypes;

/** A modifiable {@code double} value. */
final class _Double extends _Number {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the value */
  private double m_value;

  /** create */
  _Double() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int getState() {
    final double val;

    val = this.m_value;
    if (val <= Double.NEGATIVE_INFINITY) {
      return BasicNumber.STATE_NEGATIVE_INFINITY;
    }
    if (val >= Double.POSITIVE_INFINITY) {
      return BasicNumber.STATE_POSITIVE_INFINITY;
    }
    if (val != val) {
      return BasicNumber.STATE_NAN;
    }

    if ((NumericalTypes.getTypes(val) & NumericalTypes.IS_LONG) != 0) {
      return BasicNumber.STATE_INTEGER;
    }

    return BasicNumber.STATE_DOUBLE;
  }

  /** {@inheritDoc} */
  @Override
  public final long longValue() {
    return ((long) (this.m_value));
  }

  /** {@inheritDoc} */
  @Override
  public final double doubleValue() {
    return this.m_value;
  }

  /** {@inheritDoc} */
  @Override
  final void _setDoubleValue(final double value) {
    this.m_value = value;
  }
}
