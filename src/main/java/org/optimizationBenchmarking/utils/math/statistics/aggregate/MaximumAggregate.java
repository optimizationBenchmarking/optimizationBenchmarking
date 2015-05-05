package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.NumericalTypes;

/**
 * This class computes the maximum of a set of numbers.
 */
public final class MaximumAggregate extends _StatefulNumber {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** instantiate */
  public MaximumAggregate() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  public final void append(final long value) {
    switch (this.m_state) {

      case STATE_EMPTY:
      case STATE_NEGATIVE_OVERFLOW:
      case STATE_NEGATIVE_INFINITY: {
        this.m_state = BasicNumber.STATE_INTEGER;
        this.m_long = value;
        return;
      }

      case STATE_INTEGER: {
        if (value > this.m_long) {
          this.m_long = value;
        }
        return;
      }

      case STATE_DOUBLE: {
        if (value > this.m_double) {
          this.m_state = BasicNumber.STATE_INTEGER;
          this.m_long = value;
        }
      }
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  public final void append(final double value) {

    if (this.m_state == BasicNumber.STATE_NAN) {
      return;
    }

    if (NumericalTypes.isLong(value)) {
      this.append((long) value);
      return;
    }

    if (value <= Double.NEGATIVE_INFINITY) {
      if (this.m_state == BasicNumber.STATE_EMPTY) {
        this._setNegativeInfinity();
      }
      return;
    }
    if (value >= Double.POSITIVE_INFINITY) {
      this._setPositiveInfinity();
      return;
    }

    if (value != value) {
      this.m_state = BasicNumber.STATE_NAN;
      return;
    }

    switch (this.m_state) {
      case STATE_EMPTY:
      case STATE_NEGATIVE_OVERFLOW:
      case STATE_NEGATIVE_INFINITY: {
        this.m_state = BasicNumber.STATE_DOUBLE;
        this.m_double = value;
        return;
      }

      case STATE_INTEGER: {
        if (value > this.m_long) {
          this.m_state = BasicNumber.STATE_DOUBLE;
          this.m_double = value;
        }
        return;
      }

      case STATE_DOUBLE: {
        if (value > this.m_double) {
          this.m_double = value;
        }
      }
    }
  }
}
