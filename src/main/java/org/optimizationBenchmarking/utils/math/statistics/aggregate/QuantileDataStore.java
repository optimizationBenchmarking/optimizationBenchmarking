package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import org.optimizationBenchmarking.utils.math.BasicNumberWrapper;
import org.optimizationBenchmarking.utils.math.NumericalTypes;

/** A class for storing quantile data. */
public final class QuantileDataStore implements IAggregate {

  /** the data store is empty */
  static final int IS_EMPTY = 0;
  /** the data store has some {@code long[]} data, but is not sorted */
  static final int HAS_DATA_LONG = (QuantileDataStore.IS_EMPTY + 1);
  /** the data store has some {@code double[]} data, but is not sorted */
  static final int HAS_DATA_DOUBLE = (QuantileDataStore.HAS_DATA_LONG + 1);
  /** the data store contains {@code long[]} data and has been sorted */
  static final int IS_SORTED_LONG = (QuantileDataStore.HAS_DATA_DOUBLE
      + 1);
  /** the data store contains {@code double[]} data and has been sorted */
  static final int IS_SORTED_DOUBLE = (QuantileDataStore.IS_SORTED_LONG
      + 1);
  /** the data store contains at least one {@link Double#NaN} */
  static final int IS_NAN = (QuantileDataStore.IS_SORTED_DOUBLE + 1);

  /** the {@code double} data */
  double[] m_doubleData;

  /** the {@code long} data */
  long[] m_longData;

  /** the size */
  int m_size;

  /** the internal state */
  int m_state;

  /** Create the quantile data store */
  public QuantileDataStore() {
    super();
    this.m_state = QuantileDataStore.IS_EMPTY;
  }

  /**
   * Create a quantile data store based on a set of {@code double}s. The
   * supplied data array may be modified by future operations.
   *
   * @param data
   *          the data
   * @param size
   *          the size
   */
  public QuantileDataStore(final double[] data, final int size) {
    super();

    double current, last;
    int index;
    boolean sorted;

    this.m_state = QuantileDataStore.IS_EMPTY;
    this.m_longData = null;
    this.m_doubleData = null;
    this.m_size = 0;

    if (data == null) {
      throw new IllegalArgumentException(//
          "double[] data cannot be null."); //$NON-NLS-1$
    }

    if (size <= 0) {
      return;
    }

    current = Double.POSITIVE_INFINITY;
    sorted = true;
    for (index = size; (--index) >= 0;) {
      last = current;
      current = data[index];
      if (current != current) {
        this.m_state = QuantileDataStore.IS_NAN;
        return;
      }
      sorted = (sorted && (current <= last));
    }

    this.m_state = (sorted ? QuantileDataStore.IS_SORTED_DOUBLE
        : QuantileDataStore.HAS_DATA_DOUBLE);
    this.m_state = size;
    this.m_doubleData = data;
  }

  /**
   * Create a quantile data store based on a set of {@code double}s. The
   * supplied data array may be modified by future operations.
   *
   * @param data
   *          the data
   * @param size
   *          the size
   */
  public QuantileDataStore(final long[] data, final int size) {
    super();

    long current, last;
    int index;
    boolean sorted;

    this.m_state = QuantileDataStore.IS_EMPTY;
    this.m_longData = null;
    this.m_doubleData = null;
    this.m_size = 0;

    if (data == null) {
      throw new IllegalArgumentException(//
          "long[] data cannot be null."); //$NON-NLS-1$
    }

    if (size <= 0) {
      return;
    }

    current = Long.MAX_VALUE;
    sorted = true;
    for (index = size; (--index) >= 0;) {
      last = current;
      current = data[index];
      sorted = (sorted && (current <= last));
    }

    this.m_state = (sorted ? QuantileDataStore.IS_SORTED_LONG
        : QuantileDataStore.HAS_DATA_LONG);
    this.m_state = size;
    this.m_longData = data;
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
  @SuppressWarnings("incomplete-switch")
  @Override
  public final void append(final long value) {
    switch (this.m_state) {
      case IS_EMPTY:
      case HAS_DATA_LONG:
      case IS_SORTED_LONG: {
        this.__appendLongToLongs(value);
        this.m_state = QuantileDataStore.HAS_DATA_LONG;
        return;
      }
      case HAS_DATA_DOUBLE:
      case IS_SORTED_DOUBLE: {
        this.__appendDoubleToDoubles(value);
        this.m_state = QuantileDataStore.HAS_DATA_DOUBLE;
        return;
      }
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  public final void append(final double value) {
    if (value != value) {
      this.m_state = QuantileDataStore.IS_NAN;
      return;
    }

    switch (this.m_state) {
      case IS_EMPTY:
      case HAS_DATA_LONG:
      case IS_SORTED_LONG: {
        if (NumericalTypes.isLong(value)) {
          this.__appendLongToLongs((long) value);
          this.m_state = QuantileDataStore.HAS_DATA_LONG;
        } else {
          this.__appendDoubleToLongs(value);
          this.m_state = QuantileDataStore.HAS_DATA_DOUBLE;
        }
        return;
      }
      case HAS_DATA_DOUBLE:
      case IS_SORTED_DOUBLE: {
        this.__appendDoubleToDoubles(value);
        this.m_state = QuantileDataStore.HAS_DATA_DOUBLE;
      }
    }
  }

  /** Reset the quantile data store */
  public final void reset() {
    this.m_size = 0;
    this.m_state = QuantileDataStore.IS_EMPTY;
  }

  /**
   * Visit a given {@code int}. This method forwards to
   * {@link #append(long)}.
   *
   * @param value
   *          the value to visit
   */
  @Override
  public final void append(final int value) {
    this.append((long) value);
  }

  /**
   * Visit a given {@code short}. This method forwards to
   * {@link #append(long)}.
   *
   * @param value
   *          the value to visit
   */
  @Override
  public final void append(final short value) {
    this.append((long) value);
  }

  /**
   * Visit a given {@code byte}. This method forwards to
   * {@link #append(long)}.
   *
   * @param value
   *          the value to visit
   */
  @Override
  public final void append(final byte value) {
    this.append((long) value);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final float value) {
    this.append((double) value);
  }

  /**
   * Append a number to this aggregate
   *
   * @param v
   *          the number
   */
  public final void append(final Number v) {
    if (v == null) {
      throw new IllegalArgumentException(//
          "Cannot add null number to quantile store."); //$NON-NLS-1$
    }

    switch (NumericalTypes.getMinType(v)) {
      case NumericalTypes.IS_BYTE:
      case NumericalTypes.IS_SHORT:
      case NumericalTypes.IS_INT:
      case NumericalTypes.IS_LONG: {
        this.append(v.longValue());
        return;
      }
      default: {
        this.append(v.doubleValue());
      }
    }
  }

  /**
   * Get the quantile
   *
   * @param p
   *          the quantile
   * @return a number representing the quantile based on the current state
   *         of this data store
   */
  public final BasicNumberWrapper getQuantile(final double p) {
    return new BasicNumberWrapper(new QuantileAggregate(p, this));
  }

  /**
   * Get the quantile range
   *
   * @param pLow
   *          the lower quantile
   * @param pUp
   *          the upper quantile
   * @return a number representing the inter-quantile range based on the
   *         current state of this data store
   */
  public final BasicNumberWrapper getInterQuantileRange(final double pLow,
      final double pUp) {
    return new BasicNumberWrapper(
        new InterQuantileRangeAggregate(pLow, pUp, this));
  }
}
