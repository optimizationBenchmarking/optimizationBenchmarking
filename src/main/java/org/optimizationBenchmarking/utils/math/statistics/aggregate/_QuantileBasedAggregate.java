package org.optimizationBenchmarking.utils.math.statistics.aggregate;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.math.BasicNumber;

/**
 * A base class for quantile-based aggregates
 */
abstract class _QuantileBasedAggregate extends _StatefulNumber {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the quantile data store to use. */
  private final QuantileDataStore m_store;

  /** the current time stamp */
  private long m_timestamp;

  /**
   * Create the quantile-based aggregate
   *
   * @param store
   *          the quantile data store
   */
  _QuantileBasedAggregate(final QuantileDataStore store) {
    super();

    if (store == null) {
      throw new IllegalArgumentException(//
          "Quantile data store must not be null."); //$NON-NLS-1$
    }

    this.m_store = store;
  }

  /**
   * compute the {@code long} value
   *
   * @param data
   *          the {@code long} data
   * @param count
   *          the number of elements in {@code data}
   */
  abstract void _computeLong(final long[] data, final int count);

  /**
   * compute the {@code double} value
   *
   * @param data
   *          the {@code double} data
   * @param count
   *          the number of elements in {@code data}
   */
  abstract void _computeDouble(final double[] data, final int count);

  /** compute the value of this quantile */
  @SuppressWarnings("fallthrough")
  private final void __compute() {

    this.m_timestamp = this.m_store.m_timestamp;

    switch (this.m_store.m_state) {

      case QuantileDataStore.IS_EMPTY: {
        this.m_state = BasicNumber.STATE_EMPTY;
        return;
      }

      case QuantileDataStore.HAS_DATA_LONG: {
        Arrays.sort(this.m_store.m_longData, 0, this.m_store.m_size);
        this.m_store.m_state = QuantileDataStore.IS_SORTED_LONG;
      }

      case QuantileDataStore.IS_SORTED_LONG: {
        this._computeLong(this.m_store.m_longData, this.m_store.m_size);
        return;
      }

      case QuantileDataStore.HAS_DATA_DOUBLE: {
        Arrays.sort(this.m_store.m_doubleData, 0, this.m_store.m_size);
        this.m_store.m_state = QuantileDataStore.IS_SORTED_DOUBLE;
      }

      case QuantileDataStore.IS_SORTED_DOUBLE: {
        this._computeDouble(this.m_store.m_doubleData,
            this.m_store.m_size);
        return;
      }

      default: {
        this.m_state = BasicNumber.STATE_NAN;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int getState() {
    if (this.m_timestamp != this.m_store.m_timestamp) {
      this.__compute();
    }
    return this.m_state;
  }

  /** {@inheritDoc} */
  @Override
  public final long longValue() {
    if (this.m_timestamp != this.m_store.m_timestamp) {
      this.__compute();
    }
    return super.longValue();
  }

  /** {@inheritDoc} */
  @Override
  public final double doubleValue() {
    if (this.m_timestamp != this.m_store.m_timestamp) {
      this.__compute();
    }
    return super.doubleValue();
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long value) {
    this.m_state = BasicNumber.STATE_EMPTY;
    this.m_store.append(value);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double value) {
    this.m_state = BasicNumber.STATE_EMPTY;
    this.m_store.append(value);
  }

  /** {@inheritDoc} */
  @Override
  public final void reset() {
    super.reset();
    this.m_store.reset();
  }
}
