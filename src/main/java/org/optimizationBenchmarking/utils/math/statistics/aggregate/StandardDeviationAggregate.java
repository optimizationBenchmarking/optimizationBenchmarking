package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.BasicNumberWrapper;
import org.optimizationBenchmarking.utils.math.functions.power.Sqrt;

/**
 * An aggregate for computing the standard deviation.
 */
public final class StandardDeviationAggregate extends MeanBasedAggregate {

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

  /** {@inheritDoc} */
  @Override
  public final BasicNumberWrapper getSum() {
    return this.m_variance.getSum();
  }

  /** {@inheritDoc} */
  @Override
  public final BasicNumberWrapper getMinimum() {
    return this.m_variance.getMinimum();
  }

  /** {@inheritDoc} */
  @Override
  public final BasicNumberWrapper getMaximum() {
    return this.m_variance.getMaximum();
  }

  /** {@inheritDoc} */
  @Override
  public final long getCountValue() {
    return this.m_variance.getCountValue();
  }

  /** {@inheritDoc} */
  @Override
  public final BasicNumber getCount() {
    return this.m_variance.getCount();
  }

  /** {@inheritDoc} */
  @Override
  public final BasicNumberWrapper getArithmeticMean() {
    return this.m_variance.getArithmeticMean();
  }

  /**
   * Get a basic number wrapper accessing the second moment
   *
   * @return the second moment
   */
  public final BasicNumberWrapper getSecondMoment() {
    return this.m_variance.getSecondMoment();
  }

  /**
   * Get a basic number wrapper accessing the variance
   *
   * @return the variance
   */
  public final BasicNumberWrapper getVariance() {
    return new BasicNumberWrapper(this.m_variance);
  }
}
