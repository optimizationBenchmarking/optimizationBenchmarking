package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate;

/**
 * An implementation of {@code run} which only relies on indirect
 * information from the data point and dimension set.
 */
final class _BasicRun extends Run {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the dimensions */
  final ArraySetView<Dimension> m_dims;

  /**
   * Create the basic run
   *
   * @param data
   *          the data
   * @param dims
   *          the dimension set
   */
  _BasicRun(final DataPoint[] data, final ArraySetView<Dimension> dims) {
    super(data);
    this.m_dims = dims;
  }

  /** {@inheritDoc} */
  @Override
  public final void aggregateColumn(final int column,
      final IAggregate aggregate) {
    for (final DataPoint dp : this.m_data) {
      dp.aggregateColumn(column, aggregate);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void aggregateRow(final int row, final IAggregate aggregate) {
    this.m_data.get(row).aggregateRow(0, aggregate);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isIntegerMatrix() {
    for (final Dimension dim : this.m_dims) {
      if (dim.m_primitiveType.isFloat()) {
        return false;
      }
    }
    return true;
  }

  /**
   * find a long value data point
   *
   * @param value
   *          the value
   * @param data
   *          the data array
   * @param dimension
   *          the dimension
   * @return the point
   */
  private static final DataPoint __findLong(final long value,
      final ArrayListView<DataPoint> data, final Dimension dimension) {
    final int dim;
    final boolean isIncreasing, quality;
    DataPoint midVal, next;
    int low, sh, high, mid, i;
    long val;
    low = 0;
    sh = high = (data.size() - 1);
    dim = dimension.m_id;
    isIncreasing = dimension.m_direction.isIncreasing();

    while (low <= high) {
      mid = ((low + high) >>> 1);
      midVal = data.get(mid);
      val = midVal.getLong(dim);

      if (val == value) {
        if (dimension.m_direction.isStrict()) {
          return midVal;
        }

        for (i = mid; (--i) >= 0;) {
          next = data.get(i);
          if (next.getLong(dim) != value) {
            return midVal;
          }
          midVal = next;
        }
        return midVal;
      }

      if (isIncreasing ^ (val > value)) {
        low = (mid + 1);
      } else {
        high = (mid - 1);
      }
    }

    quality = dimension.m_dimensionType.isSolutionQualityMeasure();
    if (low <= 0) {
      return (quality ? data.get(0) : null);
    }
    if (low > sh) {
      return (quality ? null : data.get(sh));
    }
    return data.get(quality ? low : (low - 1));
  }

  /**
   * find a byte value data point
   *
   * @param value
   *          the value
   * @param data
   *          the data array
   * @param dimension
   *          the dimension
   * @return the point
   */
  private static final DataPoint __findByte(final byte value,
      final ArrayListView<DataPoint> data, final Dimension dimension) {
    final int dim;
    final boolean isIncreasing, quality;
    DataPoint midVal, next;
    int low, sh, high, mid, i;
    byte val;
    low = 0;
    sh = high = (data.size() - 1);
    dim = dimension.m_id;
    isIncreasing = dimension.m_direction.isIncreasing();

    while (low <= high) {
      mid = ((low + high) >>> 1);
      midVal = data.get(mid);
      val = midVal.getByte(dim);

      if (value == val) {
        if (dimension.m_direction.isStrict()) {
          return midVal;
        }

        for (i = mid; (--i) >= 0;) {
          next = data.get(i);
          if (next.getByte(dim) != value) {
            return midVal;
          }
          midVal = next;
        }
        return midVal;
      }

      if (isIncreasing ^ (val > value)) {
        low = (mid + 1);
      } else {
        high = (mid - 1);
      }
    }

    quality = dimension.m_dimensionType.isSolutionQualityMeasure();
    if (low <= 0) {
      return (quality ? data.get(0) : null);
    }
    if (low > sh) {
      return (quality ? null : data.get(sh));
    }
    return data.get(quality ? low : (low - 1));
  }

  /**
   * find a short value data point
   *
   * @param value
   *          the value
   * @param data
   *          the data array
   * @param dimension
   *          the dimension
   * @return the point
   */
  private static final DataPoint __findShort(final short value,
      final ArrayListView<DataPoint> data, final Dimension dimension) {
    final int dim;
    final boolean isIncreasing, quality;
    DataPoint midVal, next;
    int low, sh, high, mid, i;
    short val;
    low = 0;
    sh = high = (data.size() - 1);
    dim = dimension.m_id;
    isIncreasing = dimension.m_direction.isIncreasing();

    while (low <= high) {
      mid = ((low + high) >>> 1);
      midVal = data.get(mid);
      val = midVal.getShort(dim);

      if (value == val) {
        if (dimension.m_direction.isStrict()) {
          return midVal;
        }

        for (i = mid; (--i) >= 0;) {
          next = data.get(i);
          if (next.getShort(dim) != value) {
            return midVal;
          }
          midVal = next;
        }
        return midVal;
      }

      if (isIncreasing ^ (val > value)) {
        low = (mid + 1);
      } else {
        high = (mid - 1);
      }
    }

    quality = dimension.m_dimensionType.isSolutionQualityMeasure();
    if (low <= 0) {
      return (quality ? data.get(0) : null);
    }
    if (low > sh) {
      return (quality ? null : data.get(sh));
    }
    return data.get(quality ? low : (low - 1));
  }

  /**
   * find a int value data point
   *
   * @param value
   *          the value
   * @param data
   *          the data array
   * @param dimension
   *          the dimension
   * @return the point
   */
  private static final DataPoint __findInt(final int value,
      final ArrayListView<DataPoint> data, final Dimension dimension) {
    final int dim;
    final boolean isIncreasing, quality;
    DataPoint midVal, next;
    int low, sh, high, mid, i;
    int val;
    low = 0;
    sh = high = (data.size() - 1);
    dim = dimension.m_id;
    isIncreasing = dimension.m_direction.isIncreasing();

    while (low <= high) {
      mid = ((low + high) >>> 1);
      midVal = data.get(mid);
      val = midVal.getInt(dim);

      if (value == val) {
        if (dimension.m_direction.isStrict()) {
          return midVal;
        }
        for (i = mid; (--i) >= 0;) {
          next = data.get(i);
          if (next.getInt(dim) != value) {
            return midVal;
          }
          midVal = next;
        }
        return midVal;
      }

      if (isIncreasing ^ (val > value)) {
        low = (mid + 1);
      } else {
        high = (mid - 1);
      }
    }

    quality = dimension.m_dimensionType.isSolutionQualityMeasure();
    if (low <= 0) {
      return (quality ? data.get(0) : null);
    }
    if (low > sh) {
      return (quality ? null : data.get(sh));
    }
    return data.get(quality ? low : (low - 1));
  }

  /**
   * find a float value data point
   *
   * @param value
   *          the value
   * @param data
   *          the data array
   * @param dimension
   *          the dimension
   * @return the point
   */
  private static final DataPoint __findFloat(final float value,
      final ArrayListView<DataPoint> data, final Dimension dimension) {
    final int dim;
    final boolean isIncreasing, quality;
    DataPoint midVal, next;
    int low, sh, high, mid, i, res;
    float val;
    low = 0;
    sh = high = (data.size() - 1);
    dim = dimension.m_id;
    isIncreasing = dimension.m_direction.isIncreasing();

    while (low <= high) {
      mid = ((low + high) >>> 1);
      midVal = data.get(mid);
      val = midVal.getFloat(dim);

      res = EComparison.compareFloats(value, val);

      if (res == 0) {
        if (dimension.m_direction.isStrict()) {
          return midVal;
        }

        for (i = mid; (--i) >= 0;) {
          next = data.get(i);
          if (EComparison.compareFloats(next.getFloat(dim), value) != 0) {
            return midVal;
          }
          midVal = next;
        }
        return midVal;
      }

      if (isIncreasing ^ (res < 0)) {
        low = (mid + 1);
      } else {
        high = (mid - 1);
      }
    }

    quality = dimension.m_dimensionType.isSolutionQualityMeasure();
    if (low <= 0) {
      return (quality ? data.get(0) : null);
    }
    if (low > sh) {
      return (quality ? null : data.get(sh));
    }
    return data.get(quality ? low : (low - 1));
  }

  /**
   * find a double value data point
   *
   * @param value
   *          the value
   * @param data
   *          the data array
   * @param dimension
   *          the dimension
   * @return the point
   */
  private static final DataPoint __findDouble(final double value,
      final ArrayListView<DataPoint> data, final Dimension dimension) {
    final int dim;
    final boolean isIncreasing, quality;
    DataPoint midVal, next;
    int low, sh, high, mid, i, res;
    double val;
    low = 0;
    sh = high = (data.size() - 1);
    dim = dimension.m_id;
    isIncreasing = dimension.m_direction.isIncreasing();

    while (low <= high) {
      mid = ((low + high) >>> 1);
      midVal = data.get(mid);
      val = midVal.getDouble(dim);

      res = EComparison.compareDoubles(value, val);
      if (res == 0) {
        if (dimension.m_direction.isStrict()) {
          return midVal;
        }

        for (i = mid; (--i) >= 0;) {
          next = data.get(i);
          if (EComparison.compareDoubles(next.getDouble(dim), value) != 0) {
            return midVal;
          }
          midVal = next;
        }
        return midVal;

      }

      if (isIncreasing ^ (res < 0)) {
        low = (mid + 1);
      } else {
        high = (mid - 1);
      }
    }

    quality = dimension.m_dimensionType.isSolutionQualityMeasure();
    if (low <= 0) {
      return (quality ? data.get(0) : null);
    }
    if (low > sh) {
      return (quality ? null : data.get(sh));
    }
    return data.get(quality ? low : (low - 1));
  }

  /** {@inheritDoc} */
  @Override
  public final DataPoint find(final int column, final double value) {
    final Dimension dim;

    dim = this.m_dims.get(column);
    switch (dim.m_primitiveType) {
      case BYTE: {
        return _BasicRun.__findByte(((byte) value), this.m_data, dim);
      }
      case SHORT: {
        return _BasicRun.__findShort(((short) value), this.m_data, dim);
      }
      case INT: {
        return _BasicRun.__findInt(((int) value), this.m_data, dim);
      }
      case LONG: {
        return _BasicRun.__findLong(((long) value), this.m_data, dim);
      }
      case FLOAT: {
        return _BasicRun.__findFloat(((float) value), this.m_data, dim);
      }
      default: {
        return _BasicRun.__findDouble(value, this.m_data, dim);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final DataPoint find(final int column, final long value) {
    final Dimension dim;

    dim = this.m_dims.get(column);
    switch (dim.m_primitiveType) {
      case BYTE: {
        return _BasicRun.__findByte(((byte) value), this.m_data, dim);
      }
      case SHORT: {
        return _BasicRun.__findShort(((short) value), this.m_data, dim);
      }
      case INT: {
        return _BasicRun.__findInt(((int) value), this.m_data, dim);
      }
      case LONG: {
        return _BasicRun.__findLong(value, this.m_data, dim);
      }
      case FLOAT: {
        return _BasicRun.__findFloat(value, this.m_data, dim);
      }
      default: {
        return _BasicRun.__findDouble(value, this.m_data, dim);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix selectColumns(final int... cols) {
    int i;

    checker: {
      i = 0;
      for (final int j : cols) {
        if (j != (i++)) {
          break checker;
        }
      }
      if (i == this.n()) {
        return this;
      }
    }

    return new _BasicRunColumns(this, cols);
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix selectRows(final int... rows) {
    if (rows.length == 1) {
      return this.m_data.get(rows[0]);
    }
    return super.selectRows(rows);
  }
}
