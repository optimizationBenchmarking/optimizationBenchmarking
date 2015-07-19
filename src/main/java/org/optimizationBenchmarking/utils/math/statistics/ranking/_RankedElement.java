package org.optimizationBenchmarking.utils.math.statistics.ranking;

/** a ranked element */
abstract class _RankedElement implements Comparable<_RankedElement> {

  /**
   * the lowest possible {@code double} value which cannot be compared to
   * any {@code long}
   */
  static final double MAX_LONG_DOUBLE = Math.nextUp(Long.MAX_VALUE);
  /**
   * the largest possible {@code double} value which cannot be compared to
   * any {@code long}
   */
  static final double MIN_LONG_DOUBLE = Math.nextAfter(Long.MIN_VALUE,
      Double.NEGATIVE_INFINITY);

  /** the first index of the original value */
  final int m_index1;

  /** the second index of the original value */
  final int m_index2;

  /** the rank this element received */
  double m_rank;

  /**
   * create the ranked element
   *
   * @param index1
   *          the first index of the original value
   * @param index2
   *          the second index of the original value
   */
  _RankedElement(final int index1, final int index2) {
    super();
    this.m_index1 = index1;
    this.m_index2 = index2;
  }
}
