package org.optimizationBenchmarking.utils.math.statistics.ranking;

import java.math.BigDecimal;

import org.optimizationBenchmarking.utils.comparison.EComparison;

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
    final long val, oLong;
    final double oDouble, thisDouble;
    final _RankedDouble rd;

    if (o instanceof _RankedLong) {
      return Long.compare(this.m_value, ((_RankedLong) o).m_value);
    }

    if (o instanceof _RankedDouble) {
      rd = ((_RankedDouble) o);

      oDouble = rd.m_value;
      if (oDouble >= _RankedElement.MAX_LONG_DOUBLE) {
        return 1;
      }
      if (oDouble <= _RankedElement.MIN_LONG_DOUBLE) {
        return (-1);
      }
      val = this.m_value;

      oLong = ((long) oDouble);
      if (oLong == oDouble) {
        return Long.compare(val, oLong);
      }

      thisDouble = val;
      if (((long) thisDouble) == val) {
        return EComparison.compareDoubles(thisDouble, oDouble);
      }

      if (this.m_decimal == null) {
        this.m_decimal = new BigDecimal(val);
      }
      if (rd.m_decimal == null) {
        rd.m_decimal = new BigDecimal(oDouble);
      }

      return this.m_decimal.compareTo(rd.m_decimal);
    }
    return (-(o.compareTo(this)));
  }
}
