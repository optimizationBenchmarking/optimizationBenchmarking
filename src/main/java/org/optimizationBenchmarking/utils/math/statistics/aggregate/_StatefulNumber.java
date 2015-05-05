package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;

/** a stateful number */
abstract class _StatefulNumber extends ScalarAggregate {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the state */
  int m_state;

  /** the long value */
  long m_long;

  /** the double value */
  double m_double;

  /** the empty state */
  _StatefulNumber() {
    super();
    this.m_state = BasicNumber.STATE_EMPTY;
    this.m_double = Double.NaN;
    this.m_long = 0L;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isInteger() {
    return (this.m_state == BasicNumber.STATE_INTEGER);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isReal() {
    return ((this.m_state >= BasicNumber.STATE_INTEGER) && (this.m_state <= BasicNumber.STATE_DOUBLE));
  }

  /** {@inheritDoc} */
  @Override
  public long longValue() {
    switch (this.m_state) {
      case STATE_INTEGER: {
        return this.m_long;
      }
      case STATE_DOUBLE: {
        if (this.m_double >= Long.MAX_VALUE) {
          return Long.MAX_VALUE;
        }
        if (this.m_double <= Long.MIN_VALUE) {
          return Long.MIN_VALUE;
        }
        return ((long) (this.m_double));
      }
      case STATE_POSITIVE_OVERFLOW:
      case STATE_POSITIVE_INFINITY: {
        return Long.MAX_VALUE;
      }
      case STATE_NEGATIVE_OVERFLOW:
      case STATE_NEGATIVE_INFINITY: {
        return Long.MIN_VALUE;
      }
      default: {
        return 0L;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public double doubleValue() {
    switch (this.m_state) {
      case STATE_INTEGER: {
        return this.m_long;
      }
      case STATE_DOUBLE: {
        return this.m_double;
      }
      case STATE_POSITIVE_OVERFLOW:
      case STATE_POSITIVE_INFINITY: {
        return Double.POSITIVE_INFINITY;
      }
      case STATE_NEGATIVE_OVERFLOW:
      case STATE_NEGATIVE_INFINITY: {
        return Double.NEGATIVE_INFINITY;
      }
      default: {
        return Double.NaN;
      }
    }
  }

  /**
   * copy the value of a given number
   *
   * @param src
   *          the number to copy
   */
  final void _assign(final _StatefulNumber src) {
    this.m_double = src.m_double;
    this.m_long = src.m_long;
    this.m_state = src.m_state;
  }

  /** reset all internal state information */
  @Override
  public void reset() {
    this._setEmpty();
  }

  /** {@inheritDoc} */
  @Override
  public int getState() {
    return this.m_state;
  }

  /** set this number to empty */
  final void _setEmpty() {
    this.m_state = BasicNumber.STATE_EMPTY;
    this.m_double = Double.NaN;
    this.m_long = 0L;
  }

  /** set this number to NaN */
  final void _setNaN() {
    this.m_state = BasicNumber.STATE_NAN;
    this.m_double = Double.NaN;
    this.m_long = 0L;
  }

  /** Set this number to positive infinity */
  final void _setPositiveInfinity() {
    this.m_state = BasicNumber.STATE_POSITIVE_INFINITY;
    this.m_double = Double.POSITIVE_INFINITY;
    this.m_long = Long.MAX_VALUE;
  }

  /** Set this number to positive overflow */
  final void _setPositiveOverflow() {
    this.m_state = BasicNumber.STATE_POSITIVE_OVERFLOW;
    this.m_double = Double.POSITIVE_INFINITY;
    this.m_long = Long.MAX_VALUE;
  }

  /** Set this number to negative infinity */
  final void _setNegativeInfinity() {
    this.m_state = BasicNumber.STATE_NEGATIVE_INFINITY;
    this.m_double = Double.NEGATIVE_INFINITY;
    this.m_long = Long.MIN_VALUE;
  }

  /** Set this number to negative overflow */
  final void _setNegativeOverflow() {
    this.m_state = BasicNumber.STATE_NEGATIVE_OVERFLOW;
    this.m_double = Double.NEGATIVE_INFINITY;
    this.m_long = Long.MIN_VALUE;
  }
}
