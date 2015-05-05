package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Mul;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.SaturatingSub;

/**
 * An aggregate for quantiles.
 */
public final class QuantileAggregate extends _StatefulNumber {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the choice value */
  private final double m_p;

  /** the {@code double} data */
  private double[] m_doubleData;

  /** the {@code long} data */
  private long[] m_longData;

  /** the data is currently longs */
  private boolean m_dataIsLong;

  /** the size */
  private int m_size;

  /**
   * Create the quantile aggregate
   *
   * @param p
   *          the quantile
   */
  public QuantileAggregate(final double p) {
    super();

    if ((p >= 0d) && (p <= 1d)) {
      this.m_p = p;
    } else {
      throw new IllegalArgumentException(//
          "Quantile p value must be in [0,1], but is " + p); //$NON-NLS-1$
    }

    this.m_dataIsLong = true;
  }

  /**
   * Get the quantile parameter
   *
   * @return the quantile parameter
   */
  public final double getQuantile() {
    return this.m_p;
  }

  /**
   * Append a {@code long} to the internal list of {@code long}s.
   *
   * @param value
   *          the {@code long}
   */
  private final void __appendLongToLongs(final long value) {
    long[] data;
    int size;

    data = this.m_longData;
    if (data == null) {
      this.m_longData = data = new long[128];
      size = 0;
    } else {
      size = this.m_size;
      if (size >= data.length) {
        data = new long[size << 1];
        System.arraycopy(this.m_longData, 0, data, 0, size);
        this.m_longData = data;
      }
    }
    data[size] = value;
    this.m_size = (size + 1);
  }

  /**
   * Append a {@code double} to the internal list of {@code double}s.
   *
   * @param value
   *          the {@code double}
   */
  private final void __appendDoubleToDoubles(final double value) {
    double[] data;
    int size;

    data = this.m_doubleData;
    if (data == null) {
      this.m_doubleData = data = new double[128];
      size = 0;
    } else {
      size = this.m_size;
      if (size >= data.length) {
        data = new double[size << 1];
        System.arraycopy(this.m_doubleData, 0, data, 0, size);
        this.m_doubleData = data;
      }
    }
    data[size] = value;
    this.m_size = (size + 1);
  }

  /**
   * Append a {@code long} to the internal list of {@code long}s.
   *
   * @param value
   *          the {@code long}
   */
  private final void __appendDoubleToLongs(final double value) {
    long[] lData;
    double[] dData;
    int i, size;

    lData = this.m_longData;
    if (lData == null) {
      this.__appendDoubleToDoubles(value);
    } else {
      size = this.m_size;
      dData = new double[Math.max(128, (size << 1))];
      for (i = size; (--i) >= 0;) {
        dData[i] = lData[i];
      }
      dData[size] = value;
      this.m_size = (size + 1);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long value) {
    if (this.m_state != BasicNumber.STATE_NAN) {
      this.m_state = BasicNumber.STATE_EMPTY;
      if (this.m_dataIsLong) {
        this.__appendLongToLongs(value);
      } else {
        this.__appendDoubleToDoubles(value);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double value) {
    if (this.m_state != BasicNumber.STATE_NAN) {
      this.m_state = BasicNumber.STATE_EMPTY;
      if (value != value) {
        this.m_dataIsLong = false;
        this.m_state = BasicNumber.STATE_NAN;
      } else {
        if (this.m_dataIsLong) {
          if (NumericalTypes.isLong(value)) {
            this.append((long) value);
            return;
          }
          this.m_dataIsLong = false;
          this.__appendDoubleToLongs(value);
        } else {
          this.__appendDoubleToDoubles(value);
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void reset() {
    this.m_dataIsLong = true;
    this.m_size = 0;
    this.m_state = BasicNumber.STATE_EMPTY;
  }

  /** The set the quantile based on the {@code long} data. */
  private final void __computeLong() {
    final int count, intpos;
    final long[] data;
    final double low, pos, v;
    final long upper, lower;
    final double quantile, res;

    count = this.m_size;
    if (count <= 0) {
      return;
    }

    data = this.m_longData;

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

  /** The set the quantile based on the {@code double} data. */
  private final void __computeDouble() {
    final int count, intpos;
    final double[] data;
    final double low, pos, v, upper, lower, quantile;

    count = this.m_size;
    if (count <= 0) {
      return;
    }

    data = this.m_doubleData;

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
    Math.max(lower, Math.min(upper,//
        Add.INSTANCE.computeAsDouble(lower,//
            Mul.INSTANCE.computeAsDouble(v, (upper - lower))))));
  }

  /** compute the value of this quantile */
  private final void __compute() {
    if (this.m_dataIsLong) {
      this.__computeLong();
    } else {
      this.__computeDouble();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int getState() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      this.__compute();
    }
    return this.m_state;
  }

  /** {@inheritDoc} */
  @Override
  public final long longValue() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      this.__compute();
    }
    return super.longValue();
  }

  /** {@inheritDoc} */
  @Override
  public final double doubleValue() {
    if (this.m_state == BasicNumber.STATE_EMPTY) {
      this.__compute();
    }
    return super.doubleValue();
  }
}
