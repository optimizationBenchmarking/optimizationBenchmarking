package org.optimizationBenchmarking.utils.math.statistics.ranking;

/** a ranked {@code long} */
final class _RankedNaN extends _RankedElement {

  /** the strategy */
  final ENaNStrategy m_strategy;

  /**
   * create the ranked {@code long}
   *
   * @param index1
   *          the first index of the original value
   * @param index2
   *          the second index of the original value
   * @param strategy
   *          the strategy
   */
  _RankedNaN(final int index1, final int index2,
      final ENaNStrategy strategy) {
    super(index1, index2);
    this.m_strategy = strategy;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final _RankedElement o) {
    if (o instanceof _RankedLong) {
      return this.m_strategy._compareLong();
    }
    if (o instanceof _RankedDouble) {
      return this.m_strategy._compareDouble(((_RankedDouble) o).m_value);
    }
    return 0;
  }
}
