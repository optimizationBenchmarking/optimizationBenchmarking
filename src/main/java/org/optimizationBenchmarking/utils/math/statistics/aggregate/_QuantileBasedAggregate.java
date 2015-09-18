package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.NumericalTypes;

/**
 * A base class for quantile-based aggregates
 */
abstract class _QuantileBasedAggregate extends _StatefulNumber {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the {@code double} data */
  private double[] m_doubleData;

  /** the {@code long} data */
  private long[] m_longData;

  /** the data is currently longs */
  private boolean m_dataIsLong;

  /** the size */
  private int m_size;

  /** Create the quantile-based aggregate */
  public _QuantileBasedAggregate() {
    super();
    this.m_dataIsLong = true;
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
      this.m_doubleData = dData = new double[Math.max(128, (size << 1))];
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

  /**
   * Compute the result based on the {@code long} data.
   *
   * @param data
   *          the data
   * @param count
   *          the number of elements in the data
   */
  abstract void _computeLong(final long[] data, final int count);

  /**
   * Compute the result based on the {@code double} data.
   *
   * @param data
   *          the data
   * @param count
   *          the number of elements in the data
   */
  abstract void _computeDouble(final double[] data, final int count);

  /** compute the value of this quantile */
  private final void __compute() {
    if (this.m_dataIsLong) {
      this._computeLong(this.m_longData, this.m_size);
    } else {
      this._computeDouble(this.m_doubleData, this.m_size);
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
