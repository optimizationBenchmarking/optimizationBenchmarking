package org.optimizationBenchmarking.utils.math.statistics.ranking;

import org.optimizationBenchmarking.utils.comparison.EComparison;

/** a ranked {@code double} */
final class _RankedDouble extends _RankedElement {

  /** the original value */
  final double m_value;

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
    if (o instanceof _RankedDouble) {
      return EComparison.compareDoubles(this.m_value,
          ((_RankedDouble) o).m_value);
    }
    if (o instanceof _RankedLong) {
      return (-(EComparison.compareLongToDouble(((_RankedLong) o).m_value,
          this.m_value)));
    }
    return (-(o.compareTo(this)));
  }
}
