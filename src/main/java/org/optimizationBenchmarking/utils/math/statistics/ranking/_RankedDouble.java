package org.optimizationBenchmarking.utils.math.statistics.ranking;

import java.math.BigDecimal;

import org.optimizationBenchmarking.utils.comparison.EComparison;

/** a ranked {@code double} */
final class _RankedDouble extends _RankedElement {

  /** the original value */
  final double m_value;

  /**
   * the big decimal used when comparing {@code double}s and {@code long}s
   * cannot be facilitated in any other way
   */
  BigDecimal m_decimal;

  /**
   * create the ranked {@code double}
   *
   * @param index1
   *          the first index of the original value
   * @param index2
   *          the second index of the original value
   * @param value
   *          the {@code double} value
   */
  _RankedDouble(final int index1, final int index2, final double value) {
    super(index1, index2);
    this.m_value = value;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final _RankedElement o) {
    final _RankedLong rl;
    final double val, oDouble;
    final long oLong, thisLong;

    if (o instanceof _RankedDouble) {
      return EComparison.compareDoubles(this.m_value,
          ((_RankedDouble) o).m_value);
    }

    if (o instanceof _RankedLong) {
      val = this.m_value;
      if (val >= _RankedElement.MAX_LONG_DOUBLE) {
        return 1;
      }
      if (val <= _RankedElement.MIN_LONG_DOUBLE) {
        return (-1);
      }

      rl = ((_RankedLong) o);
      oLong = rl.m_value;

      thisLong = ((long) val);
      if (thisLong == val) {
        return Long.compare(thisLong, oLong);
      }

      oDouble = oLong;
      if (((long) oDouble) == oLong) {
        return EComparison.compareDoubles(val, oDouble);
      }

      if (this.m_decimal == null) {
        this.m_decimal = new BigDecimal(val);
      }
      if (rl.m_decimal == null) {
        rl.m_decimal = new BigDecimal(oLong);
      }
      return this.m_decimal.compareTo(rl.m_decimal);
    }
    return (-(o.compareTo(this)));
  }
}
