package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;

/**
 * A class capable of gathering statistical information from a data source
 * in a single pass.
 */
public class StatisticInfo implements IAggregate {

  /**
   * @serial The count of all values.
   * @see #getCount()
   */
  long m_count;
  /**
   * @serial The minimum value.
   * @see #getMinimum()
   */
  final _ProxyScalarAggregate<MinimumAggregate> m_min;
  /**
   * @serial The maximum value.
   * @see #getMaximum()
   */
  final _ProxyScalarAggregate<MaximumAggregate> m_max;

  /** @serial the sum */
  final _ProxyScalarAggregate<StableSum> m_sum;

  /** the mean accessor */
  private final _Mean m_mean;

  /** the sum used to aggregate the mean */
  final StableSum m_meanSum;

  /** @serial the second moment */
  final _ProxyScalarAggregate<StableSum> m_m2;

  /** @serial the third moment */
  final _ProxyScalarAggregate<StableSum> m_m3;

  /** @serial the fourth moment */
  final _ProxyScalarAggregate<StableSum> m_m4;

  /** the variance */
  private final _Variance m_var;

  /** Create a new statistic info bag. */
  public StatisticInfo() {
    super();

    this.m_min = new _ProxyScalarAggregate<>(new MinimumAggregate());
    this.m_max = new _ProxyScalarAggregate<>(new MaximumAggregate());
    this.m_sum = new _ProxyScalarAggregate<>(new StableSum());
    this.m_mean = new _Mean(this);
    this.m_meanSum = new StableSum();
    this.m_m2 = new _ProxyScalarAggregate<>(new StableSum());
    this.m_m3 = new _ProxyScalarAggregate<>(new StableSum());
    this.m_m4 = new _ProxyScalarAggregate<>(new StableSum());
    this.m_var = new _Variance(this);
  }

  /** Reset the statistics info record. */
  public final void reset() {
    this.m_count = 0L;

    this.m_min.m_agg.reset();
    this.m_max.m_agg.reset();
    this.m_sum.m_agg.reset();
    this.m_mean.reset();
    this.m_meanSum.reset();
    this.m_m2.m_agg.reset();
    this.m_m3.m_agg.reset();
    this.m_m4.m_agg.reset();
  }

  /**
   * Obtain the count of elements evaluated.
   * 
   * @return The count of elements evaluated.
   */
  public final long getCount() {
    return this.m_count;
  }

  /**
   * Get the basic number representing the minimum
   * 
   * @return the basic number representing the minimum
   */
  public final BasicNumber getMinimum() {
    return this.m_min;
  }

  /**
   * Get the basic number representing the maximum
   * 
   * @return the basic number representing the maximum
   */
  public final BasicNumber getMaximum() {
    return this.m_max;
  }

  /**
   * Get the basic number representing the sum
   * 
   * @return the basic number representing the sum
   */
  public final BasicNumber getSum() {
    return this.m_sum;
  }

  /**
   * Get the basic number representing the arithmetic mean
   * 
   * @return the basic number representing the arithmetic mean
   */
  public final BasicNumber getArithmeticMean() {
    return this.m_mean;
  }

  /**
   * Get the basic number representing the variance
   * 
   * @return the basic number representing the variance
   */
  public final BasicNumber getVariance() {
    return this.m_var;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final byte v) {
    this.append((long) v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final short v) {
    this.append((long) v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final int v) {
    this.append((long) v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long v) {
    this.m_count++;
    this.m_min.m_agg.append(v);
    this.m_max.m_agg.append(v);
    this.m_sum.m_agg.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final float v) {
    this.append((double) v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double v) {
    final long n, n1;
    final double delta, delta_n, delta_n2, mean, term1, M2, M3;// , M4;

    n1 = this.m_count;
    this.m_count = n = (n1 + 1l);

    mean = this.m_mean.doubleValue();

    this.m_min.m_agg.append(v);
    this.m_max.m_agg.append(v);
    this.m_sum.m_agg.append(v);

    delta = (v - mean);
    delta_n = (delta / n);
    delta_n2 = (delta_n * delta_n);
    term1 = (delta * delta_n * n1);

    this.m_meanSum.append(delta_n);

    if (n1 > 0l) {
      M3 = this.m_m3.m_agg.doubleValue();
      M2 = this.m_m2.m_agg.doubleValue();
    } else {
      M2 = M3 = 0d;
      this.m_m2.m_agg.m_long = 0L;
      this.m_m2.m_agg.m_state = BasicNumber.STATE_INTEGER;
      this.m_m3.m_agg.m_long = 0L;
      this.m_m3.m_agg.m_state = BasicNumber.STATE_INTEGER;
      this.m_m4.m_agg.m_long = 0L;
      this.m_m4.m_agg.m_state = BasicNumber.STATE_INTEGER;
    }

    this.m_m4.m_agg.append(//
        ((term1 * delta_n2 * (((n * n) - (3L * n)) + 3L)) + (6L * delta_n2 * M2))
            - (4L * delta_n * M3));

    this.m_m3.m_agg.append(//
        ((term1 * delta_n * (n - 2L))) - (3d * delta_n * M2));

    this.m_m2.m_agg.append(term1);

  }

}
