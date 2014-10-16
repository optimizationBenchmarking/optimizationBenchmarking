package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;

/**
 * This class computes the minimum of a set of numbers.
 */
public final class MinimumAggregate extends ScalarAggregate {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** instantiate */
  public MinimumAggregate() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  public final void append(final long value) {
    switch (this.m_state) {

      case STATE_EMPTY:
      case STATE_POSITIVE_OVERFLOW:
      case STATE_POSITIVE_INFINITY: {
        this.m_state = BasicNumber.STATE_INTEGER;
        this.m_long = value;
        return;
      }

      case STATE_INTEGER: {
        if (value < this.m_long) {
          this.m_long = value;
        }
        return;
      }

      case STATE_DOUBLE: {
        if (value < this.m_double) {
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
    final long l;

    if (this.m_state == BasicNumber.STATE_NAN) {
      return;
    }

    if (value < Long.MIN_VALUE) {
      if (value <= Double.NEGATIVE_INFINITY) {
        this.m_state = BasicNumber.STATE_NEGATIVE_INFINITY;
        return;
      }
    } else {
      if (value > Long.MAX_VALUE) {
        if (value >= Double.POSITIVE_INFINITY) {
          if (this.m_state == BasicNumber.STATE_EMPTY) {
            this.m_state = BasicNumber.STATE_POSITIVE_INFINITY;
          }
          return;
        }
      } else {
        if (value != value) {
          this.m_state = BasicNumber.STATE_NAN;
          return;
        }
        l = ((long) value);
        if (l == value) {
          this.append(l);
          return;
        }
      }
    }

    switch (this.m_state) {
      case STATE_EMPTY:
      case STATE_POSITIVE_OVERFLOW:
      case STATE_POSITIVE_INFINITY: {
        this.m_state = BasicNumber.STATE_DOUBLE;
        this.m_double = value;
        return;
      }

      case STATE_INTEGER: {
        if (value < this.m_long) {
          this.m_state = BasicNumber.STATE_DOUBLE;
          this.m_double = value;
        }
        return;
      }

      case STATE_DOUBLE: {
        if (value < this.m_double) {
          this.m_double = value;
        }
      }
    }
  }
}
