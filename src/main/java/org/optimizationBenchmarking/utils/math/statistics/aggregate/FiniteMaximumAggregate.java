package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.NumericalTypes;

/**
 * This class computes the maximum of a set of numbers, but ignores all
 * infinite or NaN numbers.
 */
public final class FiniteMaximumAggregate extends ScalarAggregate {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** instantiate */
  public FiniteMaximumAggregate() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  public final void append(final long value) {
    switch (this.m_state) {

      case STATE_EMPTY:
      case STATE_NEGATIVE_OVERFLOW:
      case STATE_NEGATIVE_INFINITY:
      case STATE_POSITIVE_OVERFLOW:
      case STATE_POSITIVE_INFINITY:
      case STATE_NAN: {
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

    if ((NumericalTypes.getTypes(value) & NumericalTypes.IS_LONG) != 0) {
      this.append((long) value);
      return;
    }

    if ((value <= Double.NEGATIVE_INFINITY) || //
        (value >= Double.POSITIVE_INFINITY) || //
        (value != value)) {
      return;
    }

    switch (this.m_state) {
      case STATE_EMPTY:
      case STATE_NEGATIVE_OVERFLOW:
      case STATE_NEGATIVE_INFINITY:
      case STATE_POSITIVE_OVERFLOW:
      case STATE_POSITIVE_INFINITY:
      case STATE_NAN: {
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
