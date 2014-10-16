package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.functions.combinatoric.GCD;

/** The arithmetic mean */
final class _Mean extends _InternalAggregate {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create the mean
   * 
   * @param info
   *          the info
   */
  _Mean(final StatisticInfo info) {
    super(info);
  }

  /**
   * set a long value
   * 
   * @param lres
   *          the long value
   */
  private final void _setLong(final long lres) {
    ScalarAggregate agg;

    this.m_state = BasicNumber.STATE_INTEGER;

    // ok, the result is a long value ... but is it inside the range?
    agg = this.m_info.m_min.m_agg;
    switch (agg.m_state) {
      case STATE_INTEGER: {
        if (lres < agg.m_long) {
          this.m_long = agg.m_long;
          return;
        }
      }
      case STATE_DOUBLE: {
        if (lres < agg.m_double) {
          this.m_state = BasicNumber.STATE_DOUBLE;
          this.m_double = agg.m_double;
          return;
        }
      }
      case STATE_NEGATIVE_OVERFLOW:
      case STATE_NEGATIVE_INFINITY: {
        break;
      }
      default: {// NAN, OVERFLOW, +INFINITY
        this.m_state = agg.m_state;
        return;
      }
    }

    agg = this.m_info.m_max.m_agg;
    switch (agg.m_state) {
      case STATE_INTEGER: {
        if (lres > agg.m_long) {
          this.m_long = agg.m_long;
          return;
        }
      }
      case STATE_DOUBLE: {
        if (lres > agg.m_double) {
          this.m_state = BasicNumber.STATE_DOUBLE;
          this.m_double = agg.m_double;
          return;
        }
      }
      case STATE_POSITIVE_OVERFLOW:
      case STATE_POSITIVE_INFINITY: {
        break;
      }
      default: {// NAN, UNDERFLOW, -INFINITY
        this.m_state = agg.m_state;
        return;
      }
    }

    this.m_long = lres;
  }

  /**
   * set a double value
   * 
   * @param dres
   *          the double value
   */
  private final void _setDouble(final double dres) {
    ScalarAggregate agg;
    final long lres;

    if (dres < Long.MIN_VALUE) {
      if (dres <= Double.NEGATIVE_INFINITY) {
        this._assign(this.m_info.m_min.m_agg);
        return;
      }
    } else {
      if (dres > Long.MAX_VALUE) {
        if (dres >= Double.POSITIVE_INFINITY) {
          this._assign(this.m_info.m_max.m_agg);
          return;
        }
      } else {
        if (dres != dres) {
          this.m_state = BasicNumber.STATE_NAN;
        } else {
          lres = ((long) dres);
          if (lres == dres) {
            this._setLong(lres);
            return;
          }
        }
      }
    }

    this.m_state = BasicNumber.STATE_DOUBLE;

    agg = this.m_info.m_min.m_agg;
    switch (agg.m_state) {
      case STATE_INTEGER: {
        if (dres < agg.m_long) {
          this.m_long = agg.m_long;
          this.m_state = BasicNumber.STATE_INTEGER;
          return;
        }
      }
      case STATE_DOUBLE: {
        if (dres < agg.m_double) {
          this.m_double = agg.m_double;
          return;
        }
      }
      case STATE_NEGATIVE_OVERFLOW:
      case STATE_NEGATIVE_INFINITY: {
        break;
      }
      default: {// OVERFLOW, NAN, +INFINITY
        this.m_state = agg.m_state;
        return;
      }
    }

    agg = this.m_info.m_max.m_agg;
    switch (agg.m_state) {
      case STATE_INTEGER: {
        if (dres > agg.m_long) {
          this.m_long = agg.m_long;
          this.m_state = BasicNumber.STATE_INTEGER;
          return;
        }
      }
      case STATE_DOUBLE: {
        if (dres > agg.m_double) {
          this.m_double = agg.m_double;
          return;
        }
      }
      case STATE_POSITIVE_OVERFLOW:
      case STATE_POSITIVE_INFINITY: {
        break;
      }
      default: {// UNDERFLOW, NAN, -INFINITY
        this.m_state = agg.m_state;
        return;
      }
    }

    this.m_double = dres;
  }

  /** {@inheritDoc} */
  @Override
  final void _calc() {
    StableSum s;
    final long count, lsum;
    double div;
    long lres;

    count = this.m_info.m_count;
    s = this.m_info.m_sum.m_agg;

    switch (s.m_state) {
      case STATE_INTEGER: {
        lsum = s.m_long;
        lres = (lsum / count);
        if ((lres * count) == lsum) {
          this._setLong(lres);
          return;
        }

        lres = GCD.INSTANCE.compute(lsum, count);
        div = ((double) (lsum / lres)) / ((double) (count / lres));
        if ((div > Double.NEGATIVE_INFINITY)
            && (div < Double.POSITIVE_INFINITY) && (div == div)) {
          this._setDouble(div);
          return;
        }
        break;
      }

      case STATE_DOUBLE: {
        div = (s.m_double / count);
        if ((div > Double.NEGATIVE_INFINITY)
            && (div < Double.POSITIVE_INFINITY) && (div == div)) {
          this._setDouble(div);
          return;
        }
        break;
      }

      case STATE_POSITIVE_OVERFLOW:
      case STATE_NEGATIVE_OVERFLOW: {
        break;
      }

      default: {
        this.m_state = s.m_state;
        return;
      }
    }

    // overflow or underflow: let's try the compensated mean sum
    s = this.m_info.m_meanSum;
    switch (s.m_state) {
      case STATE_INTEGER: {
        this._setLong(s.m_long);
        return;
      }
      case STATE_DOUBLE: {
        this._setDouble(s.m_double);
        return;
      }
      case STATE_POSITIVE_INFINITY: {
        this._assign(this.m_info.m_max.m_agg);
        return;
      }
      case STATE_NEGATIVE_INFINITY: {
        this._assign(this.m_info.m_min.m_agg);
        return;
      }
      default: {
        this._assign(s);
      }
    }
  }
}
