package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.functions.combinatoric.GCD;

/** The variance mean */
final class _Variance extends _InternalAggregate {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create the mean
   * 
   * @param info
   *          the info
   */
  _Variance(final StatisticInfo info) {
    super(info);
  }

  /** {@inheritDoc} */
  @Override
  final void _calc() {
    StableSum s;
    final long count, lsum;
    double div;
    long lres;

    count = (this.m_info.m_count - 1L);

    if (count <= 0L) {
      if (count <= (-1L)) {
        this.m_state = BasicNumber.STATE_NAN;
      } else {
        this.m_state = BasicNumber.STATE_INTEGER;
        this.m_long = 0L;
      }
      return;
    }

    s = this.m_info.m_m2.m_agg;

    switch (s.m_state) {
      case STATE_INTEGER: {
        lsum = s.m_long;
        lres = (lsum / count);
        if ((lres * count) == lsum) {
          this.m_long = lres;
          this.m_state = BasicNumber.STATE_INTEGER;
          return;
        }

        lres = GCD.INSTANCE.computeAsLong(lsum, count);
        div = ((double) (lsum / lres)) / ((double) (count / lres));
        if ((div > Double.NEGATIVE_INFINITY)
            && (div < Double.POSITIVE_INFINITY) && (div == div)) {
          this.m_double = div;
          this.m_state = BasicNumber.STATE_DOUBLE;
          return;
        }
        break;
      }

      case STATE_DOUBLE: {
        div = (s.m_double / count);
        if ((div > Double.NEGATIVE_INFINITY)
            && (div < Double.POSITIVE_INFINITY) && (div == div)) {
          this.m_double = div;
          this.m_state = BasicNumber.STATE_DOUBLE;
          return;
        }
        break;
      }

      case STATE_POSITIVE_OVERFLOW:
      case STATE_NEGATIVE_OVERFLOW: {
        break;
      }

      case STATE_NEGATIVE_INFINITY: {
        this.m_state = BasicNumber.STATE_POSITIVE_INFINITY;
        return;
      }
      default: {
        this.m_state = s.m_state;
        return;
      }
    }
  }
}
