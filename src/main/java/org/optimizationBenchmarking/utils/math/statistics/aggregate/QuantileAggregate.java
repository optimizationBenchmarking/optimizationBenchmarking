package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Mul;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.SaturatingSub;

/** An aggregate for quantiles. */
public final class QuantileAggregate extends _QuantileBasedAggregate {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the choice value */
  private final double m_p;

  /**
   * Create the quantile aggregate
   *
   * @param p
   *          the quantile
   * @param store
   *          the quantile data store
   */
  QuantileAggregate(final double p, final QuantileDataStore store) {
    super(store);

    if ((p >= 0d) && (p <= 1d)) {
      this.m_p = p;
    } else {
      throw new IllegalArgumentException(//
          "Quantile p value must be in [0,1], but is " + p); //$NON-NLS-1$
    }
  }

  /**
   * Create the quantile aggregate
   *
   * @param p
   *          the quantile
   */
  public QuantileAggregate(final double p) {
    this(p, new QuantileDataStore());
  }

  /**
   * Get the quantile parameter
   *
   * @return the quantile parameter
   */
  public final double getQuantile() {
    return this.m_p;
  }

  /** {@inheritDoc} */
  @Override
  final void _computeLong(final long[] data, final int count) {
    final int intpos;
    final double low, pos, v;
    final long upper, lower;
    final double quantile, res;

    if (count <= 0) {
      return;
    }

    if (count <= 1) {
      this._setLong(data[0]);
      return;
    }

    Arrays.sort(data, 0, count);
    quantile = this.m_p;

    if (quantile <= 0d) {
      this._setLong(data[0]);
      return;
    }

    if (quantile >= 1d) {
      this._setLong(data[count - 1]);
      return;
    }

    pos = (quantile * (count + 1));
    if (pos < 1d) {
      this._setLong(data[0]);
      return;
    }

    if (pos >= count) {
      this._setLong(data[count - 1]);
      return;
    }

    low = Math.floor(pos);
    intpos = ((int) low);
    lower = data[intpos - 1];

    v = (pos - low);
    if (v <= 0d) {
      this._setLong(lower);
      return;
    }

    upper = data[intpos];
    if (upper <= lower) {
      this._setLong(lower);
      return;
    }

    if (SaturatingSub.getOverflowType(upper, lower) == 0) {
      res = Mul.INSTANCE.computeAsDouble(v, (upper - lower));
    } else {
      res = Mul.INSTANCE.computeAsDouble(v,
          (((double) upper) - ((double) lower)));
    }

    this._setDoubleFully(Math.max(lower,
        Math.min(upper, Add.INSTANCE.computeAsDouble(lower, res))));
  }

  /** {@inheritDoc} */
  @Override
  final void _computeDouble(final double[] data, final int count) {
    final int intpos;
    final double low, pos, v, upper, lower, quantile;

    if (count <= 0) {
      return;
    }

    if (count <= 1) {
      this._setDoubleFully(data[0]);
      return;
    }

    Arrays.sort(data, 0, count);
    quantile = this.m_p;

    if (quantile <= 0d) {
      this._setDoubleFully(data[0]);
      return;
    }

    if (quantile >= 1d) {
      this._setDoubleFully(data[count - 1]);
      return;
    }

    pos = (quantile * (count + 1));
    if (pos < 1d) {
      this._setDoubleFully(data[0]);
      return;
    }

    if (pos >= count) {
      this._setDoubleFully(data[count - 1]);
      return;
    }

    low = Math.floor(pos);
    intpos = ((int) low);
    lower = data[intpos - 1];

    v = (pos - low);
    if (v <= 0d) {
      this._setDoubleFully(lower);
      return;
    }

    upper = data[intpos];
    if (upper <= lower) {
      this._setDoubleFully(lower);
      return;
    }

    this._setDoubleFully(//
        Math.max(lower,
            Math.min(upper, //
                Add.INSTANCE.computeAsDouble(lower, //
                    Mul.INSTANCE.computeAsDouble(v, (upper - lower))))));
  }

}
