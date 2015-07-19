package org.optimizationBenchmarking.utils.math.statistics.ranking;

import java.math.BigDecimal;

/** a ranked {@code long} */
final class _RankedLong extends _RankedElement {

  /** the original value */
  final long m_value;

  /**
   * the big decimal used when comparing {@code double}s and {@code long}s
   * cannot be facilitated in any other way
   */
  BigDecimal m_decimal;

  /**
   * create the ranked {@code long}
   *
   * @param index1
   *          the first index of the original value
   * @param index2
   *          the second index of the original value
   * @param value
   *          the {@code long} value
   */
  _RankedLong(final int index1, final int index2, final long value) {
    super(index1, index2);
    this.m_value = value;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final _RankedElement o) {
    if (o instanceof _RankedLong) {
      return Long.compare(this.m_value, ((_RankedLong) o).m_value);
    }
    if (o instanceof _RankedDouble) {
      return _RankedElement._compareLongDouble(this, ((_RankedDouble) o));
    }
    return (-(o.compareTo(this)));
  }
}
