package org.optimizationBenchmarking.utils.math.statistics.ranking;

/** a ranked element */
abstract class _RankedElement implements Comparable<_RankedElement> {

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
