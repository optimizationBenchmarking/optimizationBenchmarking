package org.optimizationBenchmarking.utils.chart.impl.abstr;

import org.optimizationBenchmarking.utils.math.matrix.AbstractMatrix;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate;

/** an internal matrix class */
final class _WrappedMatrix2D extends AbstractMatrix {

  /** the matrix to wrap */
  private final IMatrix m_matrix;

  /** do we have a starting point? */
  private final boolean m_hasStart;
  /** the start x */
  private final double m_startX;
  /** the start y */
  private final double m_startY;

  /** do we have a ending point? */
  private final boolean m_hasEnd;
  /** the end x */
  private final double m_endX;
  /** the end y */
  private final double m_endY;

  /** the length */
  private final int m_len;

  /**
   * create
   *
   * @param matrix
   *          the matrix to wrap
   * @param hasStart
   *          do we have a starting point?
   * @param startX
   *          the start x
   * @param startY
   *          the start y
   * @param hasEnd
   *          do we have a ending point?
   * @param endX
   *          the end x
   * @param endY
   *          the end y
   */
  _WrappedMatrix2D(final IMatrix matrix, final boolean hasStart,
      final double startX, final double startY, final boolean hasEnd,
      final double endX, final double endY) {
    super();

    CompiledDataSeries2D._checkMatrix2D(matrix);
    this.m_matrix = matrix;

    this.m_hasStart = hasStart;
    if (hasStart) {
      _WrappedMatrix2D._assertCoordinate(startX);
      _WrappedMatrix2D._assertCoordinate(startY);
      this.m_startX = startX;
      this.m_startY = startY;
    } else {
      this.m_startX = this.m_startY = Double.NaN;
    }

    this.m_hasEnd = hasEnd;
    if (hasEnd) {
      _WrappedMatrix2D._assertCoordinate(endX);
      _WrappedMatrix2D._assertCoordinate(endY);
      this.m_endX = endX;
      this.m_endY = endY;
    } else {
      this.m_endX = this.m_endY = Double.NaN;
    }

    this.m_len = matrix.m();
  }

  /**
   * assert whether or not a coordinate is valid
   *
   * @param v
   *          the coordinate
   */
  static final void _assertCoordinate(final double v) {
    if (v != v) {
      throw new IllegalArgumentException(v + " is not a valid coordinate."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int m() {
    return this.m_matrix.m();
  }

  /** {@inheritDoc} */
  @Override
  public final int n() {
    return this.m_matrix.n();
  }

  /** {@inheritDoc} */
  @Override
  public final double getDouble(final int row, final int column) {
    final int useRow;

    checker: {
      if (this.m_hasStart) {
        if (row <= 0) {
          if (row == 0) {
            if (column == 0) {
              return this.m_startX;
            }
            if (column == 1) {
              return this.m_startY;
            }
          }
          break checker;
        }
        useRow = (row - 1);
      } else {
        useRow = row;
      }

      if (useRow >= this.m_len) {
        if (this.m_hasEnd && (useRow == this.m_len)) {
          if (column == 0) {
            return this.m_endX;
          }
          if (column == 1) {
            return this.m_endY;
          }
        }
        break checker;
      }
      return this.m_matrix.getDouble(useRow, column);
    }
    return super.getDouble(row, column); // fail
  }

  /** {@inheritDoc} */
  @Override
  public final long getLong(final int row, final int column) {
    final int useRow;

    checker: {
      if (this.m_hasStart) {
        if (row <= 0) {
          if (row == 0) {
            if (column == 0) {
              return ((long) (this.m_startX));
            }
            if (column == 1) {
              return ((long) (this.m_startY));
            }
          }
          break checker;
        }
        useRow = (row - 1);
      } else {
        useRow = row;
      }

      if (useRow >= this.m_len) {
        if (this.m_hasEnd && (useRow == this.m_len)) {
          if (column == 0) {
            return ((long) (this.m_endX));
          }
          if (column == 1) {
            return ((long) (this.m_endY));
          }
        }
        break checker;
      }
      return this.m_matrix.getLong(useRow, column);
    }
    return super.getLong(row, column); // fail
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isIntegerMatrix() {
    if (this.m_matrix.isIntegerMatrix()) {
      if (this.m_hasStart
          && ((((long) (this.m_startX)) != this.m_startX) || (((long) (this.m_startY)) != this.m_startY))) {
        return false;
      }
      if (this.m_hasEnd
          && ((((long) (this.m_endX)) != this.m_endX) || (((long) (this.m_endY)) != this.m_endY))) {
        return false;
      }

      return true;
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final void aggregateColumn(final int column,
      final IAggregate aggregate) {
    this.m_matrix.aggregateColumn(column, aggregate);
  }

  /** {@inheritDoc} */
  @Override
  public final void aggregateRow(final int row, final IAggregate aggregate) {
    final int useRow;

    checker: {
      if (this.m_hasStart) {
        if (row <= 0) {
          if (row == 0) {
            aggregate.append(this.m_startX);
            aggregate.append(this.m_startY);
            return;
          }
          break checker;
        }
        useRow = (row - 1);
      } else {
        useRow = row;
      }

      if (useRow >= this.m_len) {
        if (this.m_hasEnd && (useRow == this.m_len)) {
          aggregate.append(this.m_endX);
          aggregate.append(this.m_endY);
          return;
        }
        break checker;
      }
      this.m_matrix.aggregateRow(useRow, aggregate);
      return;
    }
    throw new IllegalArgumentException(//
        ((("Invalid row index: " + row) + //$NON-NLS-1$
            ", must be in (0, ") + this.m()) + '.');//$NON-NLS-1$
  }
}
