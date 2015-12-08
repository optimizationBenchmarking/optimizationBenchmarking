package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Mul;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.SaturatingSub;

/**
 * An aggregate for quantile ranges.
 */
public final class InterQuantileRangeAggregate
    extends _QuantileBasedAggregate {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the lower choice value */
  private final double m_pLow;
  /** the upper choice value */
  private final double m_pUp;

  /**
   * Create the quantile aggregate
   *
   * @param pLow
   *          the lower quantile
   * @param pUp
   *          the upper quantil
   * @param store
   *          the quantile data store
   */
  InterQuantileRangeAggregate(final double pLow, final double pUp,
      final QuantileDataStore store) {
    super(store);

    if ((pLow >= 0d) && (pLow < pUp) && (pUp <= 1d)) {
      this.m_pLow = pLow;
      this.m_pUp = pUp;
    } else {
      throw new IllegalArgumentException(//
          "Quantile limits value must be in 0<=low<up<=1, but are low=" //$NON-NLS-1$
              + pLow + " and up=" + pUp);//$NON-NLS-1$
    }
  }

  /**
   * Create the quantile aggregate
   *
   * @param pLow
   *          the lower quantile
   * @param pUp
   *          the upper quantil
   */
  public InterQuantileRangeAggregate(final double pLow, final double pUp) {
    this(pLow, pUp, new QuantileDataStore());
  }

  /**
   * Get the lower quantile parameter
   *
   * @return the lower quantile parameter
   */
  public final double getLowerQuantile() {
    return this.m_pLow;
  }

  /**
   * Get the upper quantile parameter
   *
   * @return the upper quantile parameter
   */
  public final double getUpperQuantile() {
    return this.m_pUp;
  }

  /** {@inheritDoc} */
  @Override
  final void _computeLong(final long[] data, final int count) {
    long lowLong, upLong, upper, lower;
    boolean lowIsLong, upIsLong;
    double lowDouble, upDouble, res, low, pos, v, quantile;
    int intpos;

    if (count <= 0) {
      return;
    }

    if (count <= 1) {
      this._setLong(0L);
      return;
    }

    quantile = this.m_pLow;

    lowLong = Long.MAX_VALUE;
    lowDouble = Double.POSITIVE_INFINITY;

    // First, let's compute the lower quantile
    findLow: {
      if (quantile <= 0d) {
        lowLong = data[0];
        lowIsLong = true;
        break findLow;
      }

      if (quantile >= 1d) {
        lowLong = data[count - 1];
        lowIsLong = true;
        break findLow;
      }

      pos = (quantile * (count + 1));
      if (pos < 1d) {
        lowLong = data[0];
        lowIsLong = true;
        break findLow;
      }

      if (pos >= count) {
        lowLong = data[count - 1];
        lowIsLong = true;
        break findLow;
      }

      low = Math.floor(pos);
      intpos = ((int) low);
      lower = data[intpos - 1];

      v = (pos - low);
      if (v <= 0d) {
        lowLong = lower;
        lowIsLong = true;
        break findLow;
      }

      upper = data[intpos];
      if (upper <= lower) {
        lowLong = lower;
        lowIsLong = true;
        break findLow;
      }

      if (SaturatingSub.getOverflowType(upper, lower) == 0) {
        res = Mul.INSTANCE.computeAsDouble(v, (upper - lower));
      } else {
        res = Mul.INSTANCE.computeAsDouble(v,
            (((double) upper) - ((double) lower)));
      }

      lowDouble = Math.max(lower,
          Math.min(upper, Add.INSTANCE.computeAsDouble(lower, res)));
      if (NumericalTypes.isLong(lowDouble)) {
        lowLong = ((long) lowDouble);
        lowIsLong = true;
      } else {
        lowIsLong = false;
      }
    }

    // Now, let's compute the upper quantile
    upLong = Long.MIN_VALUE;
    upDouble = Double.NEGATIVE_INFINITY;
    quantile = this.m_pUp;

    findUp: {
      if (quantile <= 0d) {
        upLong = data[0];
        upIsLong = true;
        break findUp;
      }

      if (quantile >= 1d) {
        upLong = data[count - 1];
        upIsLong = true;
        break findUp;
      }

      pos = (quantile * (count + 1));
      if (pos < 1d) {
        upLong = data[0];
        upIsLong = true;
        break findUp;
      }

      if (pos >= count) {
        upLong = data[count - 1];
        upIsLong = true;
        break findUp;
      }

      low = Math.floor(pos);
      intpos = ((int) low);
      lower = data[intpos - 1];

      v = (pos - low);
      if (v <= 0d) {
        upLong = lower;
        upIsLong = true;
        break findUp;
      }

      upper = data[intpos];
      if (upper <= lower) {
        upLong = lower;
        upIsLong = true;
        break findUp;
      }

      if (SaturatingSub.getOverflowType(upper, lower) == 0) {
        res = Mul.INSTANCE.computeAsDouble(v, (upper - lower));
      } else {
        res = Mul.INSTANCE.computeAsDouble(v,
            (((double) upper) - ((double) lower)));
      }

      upDouble = Math.max(lower,
          Math.min(upper, Add.INSTANCE.computeAsDouble(lower, res)));
      if (NumericalTypes.isLong(upDouble)) {
        upLong = ((long) upDouble);
        upIsLong = true;
      } else {
        upIsLong = false;
      }
    }

    // now compute
    if (lowIsLong) {
      if (upIsLong) {
        if (SaturatingSub.getOverflowType(upLong, lowLong) == 0) {
          this._setLong(upLong - lowLong);
          return;
        }
        upDouble = upLong;
      }
      lowDouble = lowLong;
    } else {
      if (upIsLong) {
        upDouble = upLong;
      }
    }

    this._setDoubleFully(upDouble - lowDouble);
  }

  /** {@inheritDoc} */
  @Override
  final void _computeDouble(final double[] data, final int count) {
    int intpos;
    double low, pos, v, upper, lower, quantile, lowRes, upRes;

    if (count <= 0) {
      return;
    }

    if (count <= 1) {
      this._setDoubleFully(data[0]);
      return;
    }

    quantile = this.m_pLow;

    // compute lower quantile
    findLow: {
      if (quantile <= 0d) {
        lowRes = data[0];
        break findLow;
      }

      if (quantile >= 1d) {
        lowRes = data[count - 1];
        break findLow;
      }

      pos = (quantile * (count + 1));
      if (pos < 1d) {
        lowRes = data[0];
        break findLow;
      }

      if (pos >= count) {
        lowRes = data[count - 1];
        break findLow;
      }

      low = Math.floor(pos);
      intpos = ((int) low);
      lower = data[intpos - 1];

      v = (pos - low);
      if (v <= 0d) {
        lowRes = lower;
        break findLow;
      }

      upper = data[intpos];
      if (upper <= lower) {
        lowRes = lower;
        break findLow;
      }

      lowRes = Math.max(lower,
          Math.min(upper, //
              Add.INSTANCE.computeAsDouble(lower, //
                  Mul.INSTANCE.computeAsDouble(v, (upper - lower)))));
    }

    quantile = this.m_pUp;

    // compute upper quantile
    findUp: {
      if (quantile <= 0d) {
        upRes = data[0];
        break findUp;
      }

      if (quantile >= 1d) {
        upRes = data[count - 1];
        break findUp;
      }

      pos = (quantile * (count + 1));
      if (pos < 1d) {
        upRes = data[0];
        break findUp;
      }

      if (pos >= count) {
        upRes = data[count - 1];
        break findUp;
      }

      low = Math.floor(pos);
      intpos = ((int) low);
      lower = data[intpos - 1];

      v = (pos - low);
      if (v <= 0d) {
        upRes = lower;
        break findUp;
      }

      upper = data[intpos];
      if (upper <= lower) {
        upRes = lower;
        break findUp;
      }

      upRes = Math.max(lower,
          Math.min(upper, //
              Add.INSTANCE.computeAsDouble(lower, //
                  Mul.INSTANCE.computeAsDouble(v, (upper - lower)))));
    }

    // make the result
    this._setDoubleFully(upRes - lowRes);
  }
}
