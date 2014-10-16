package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;

/**
 * a proxy number
 * 
 * @param <T>
 *          the aggregate type
 */
final class _ProxyScalarAggregate<T extends ScalarAggregate> extends
    BasicNumber {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the proxied number */
  final T m_agg;

  /**
   * create the proxy number
   * 
   * @param number
   *          the basic number
   */
  _ProxyScalarAggregate(final T number) {
    super();
    this.m_agg = number;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isInteger() {
    return (this.m_agg.m_state == BasicNumber.STATE_INTEGER);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isReal() {
    return ((this.m_agg.m_state >= BasicNumber.STATE_INTEGER) && (this.m_agg.m_state <= BasicNumber.STATE_DOUBLE));
  }

  /** {@inheritDoc} */
  @Override
  public final long longValue() {
    return this.m_agg.longValue();
  }

  /** {@inheritDoc} */
  @Override
  public final double doubleValue() {
    return this.m_agg.doubleValue();
  }

  /** {@inheritDoc} */
  @Override
  public final int getState() {
    return this.m_agg.m_state;
  }
}
