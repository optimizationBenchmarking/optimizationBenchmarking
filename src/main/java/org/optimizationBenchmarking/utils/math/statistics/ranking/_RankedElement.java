package org.optimizationBenchmarking.utils.math.statistics.ranking;

import java.math.BigDecimal;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.math.NumericalTypes;

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

  /**
   * compare a {@code long} to a {@code double}.
   *
   * @param a
   *          the {@code long}
   * @param b
   *          the {@code double}
   * @return the comparison result
   */
  static final int _compareLongDouble(final _RankedLong a,
      final _RankedDouble b) {
    final long longA, longB;
    final double doubleA, doubleB;

    doubleB = b.m_value;
    if (doubleB > NumericalTypes.MAX_LONG_DOUBLE) {
      return (-1);
    }
    if (doubleB < NumericalTypes.MIN_LONG_DOUBLE) {
      return 1;
    }

    longA = a.m_value;
    longB = ((long) doubleB);
    if (longB == doubleB) {
      return Long.compare(longA, longB);
    }

    if ((longA >= NumericalTypes.MIN_DOUBLE_LONG)
        && (longA <= NumericalTypes.MAX_DOUBLE_LONG)) {
      doubleA = longA;
      if (((long) doubleA) == longA) {
        return EComparison.compareDoubles(doubleA, doubleB);
      }
    }

    return new BigDecimal(longA).compareTo(new BigDecimal(doubleB));
  }
}
