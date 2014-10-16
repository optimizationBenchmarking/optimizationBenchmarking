package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;

/** The arithmetic mean */
abstract class _InternalAggregate extends _StatefulNumber {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the mean */
  final StatisticInfo m_info;

  /**
   * create the mean
   * 
   * @param info
   *          the info
   */
  _InternalAggregate(final StatisticInfo info) {
    super();
    this.m_info = info;
  }

  /** calculate */
  abstract void _calc();

  /** {@inheritDoc} */
  @Override
  public final boolean isInteger() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      this._calc();
    }
    return (this.m_state == BasicNumber.STATE_INTEGER);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isReal() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      this._calc();
    }
    return ((this.m_state >= BasicNumber.STATE_INTEGER) && (this.m_state <= BasicNumber.STATE_DOUBLE));
  }

  /** {@inheritDoc} */
  @Override
  public final int getState() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      this._calc();
    }
    return this.m_state;
  }

  /** {@inheritDoc} */
  @Override
  public final double doubleValue() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      this._calc();
    }
    return super.doubleValue();
  }

  /** {@inheritDoc} */
  @Override
  public final long longValue() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      this._calc();
    }
    return super.longValue();
  }

}
