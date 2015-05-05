package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.functions.power.Sqrt;

/**
 * An aggregate for computing the standard deviation.
 */
public final class StandardDeviationAggregate extends ScalarAggregate {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the internal aggregate */
  private final VarianceAggregate m_variance;

  /** create */
  public StandardDeviationAggregate() {
    super();
    this.m_variance = new VarianceAggregate();
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long value) {
    this.m_variance.append(value);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double value) {
    this.m_variance.append(value);
  }

  /** {@inheritDoc} */
  @Override
  public final int getState() {
    return this.m_variance.getState();
  }

  /** {@inheritDoc} */
  @Override
  public final void reset() {
    this.m_variance.reset();
  }

  /** {@inheritDoc} */
  @Override
  public final long longValue() {
    switch (this.m_variance.getState()) {
      case STATE_INTEGER: {
        return Sqrt.INSTANCE.computeAsLong(this.m_variance.longValue());
      }
      case STATE_DOUBLE: {
        return ((long) (Sqrt.INSTANCE.computeAsDouble(//
            this.m_variance.doubleValue())));
      }
      case STATE_POSITIVE_OVERFLOW:
      case STATE_NEGATIVE_OVERFLOW:
      case STATE_POSITIVE_INFINITY:
      case STATE_NEGATIVE_INFINITY: {
        return Long.MAX_VALUE;
      }
      default: {
        return 0L;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final double doubleValue() {
    switch (this.m_variance.getState()) {
      case STATE_INTEGER: {
        return Sqrt.INSTANCE.computeAsDouble(this.m_variance.longValue());
      }
      case STATE_DOUBLE: {
        return Sqrt.INSTANCE.computeAsDouble(//
            this.m_variance.doubleValue());
      }
      case STATE_POSITIVE_OVERFLOW:
      case STATE_NEGATIVE_OVERFLOW:
      case STATE_POSITIVE_INFINITY:
      case STATE_NEGATIVE_INFINITY: {
        return Double.POSITIVE_INFINITY;
      }
      default: {
        return Double.NaN;
      }
    }
  }
}
