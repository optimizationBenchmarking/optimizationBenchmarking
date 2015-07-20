package org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The iterator when all dimensions are {@code long}s
 */
final class _MatrixIterator2DXDoubleYDouble extends MatrixIterator2D {

  /** the {@code y}-values */
  private final double[] m_y;

  /** the type of the coordinates */
  private int m_type;

  /**
   * Create a new 2d matrix iterator
   *
   * @param matrices
   *          the list of matrices
   * @param xDim
   *          the first (x) dimension
   * @param yDim
   *          the second (y) dimension
   * @param allowEarlyEnd
   *          do we allow an early end for some matrices, or should all
   *          iterations end at the same position?
   */
  _MatrixIterator2DXDoubleYDouble(final int xDim, final int yDim,
      final IMatrix[] matrices, final boolean allowEarlyEnd) {
    super(xDim, yDim, matrices, allowEarlyEnd);

    double minVal, curVal;

    this.m_x = new _Double();
    this.m_y = new double[matrices.length];

    // find the first, smallest x value
    minVal = Double.POSITIVE_INFINITY;
    for (final IMatrix matrix : matrices) {
      if (matrix.m() > 0) {
        curVal = MatrixIterator2D._d(matrix.getDouble(0, this.m_xDim));
        if (curVal < minVal) {
          minVal = curVal;
        }
      }
    }

    this.__setX(minVal);
  }

  /**
   * set the x-coordinate
   *
   * @param x
   *          the x-coordinate
   */
  private final void __setX(final double x) {
    IMatrix matrix;
    double val;
    int have, index, position, max, type;

    have = 0;
    type = (-1);
    outer: for (index = 0; index < this.m_indexes.length; index++) {
      position = this.m_indexes[index];
      matrix = this.m_matrices[index];
      max = matrix.m();

      if (position >= max) {
        continue;
      }

      looper: for (; position < max; position++) {
        if (EComparison.compareDoubles(MatrixIterator2D._d(//
            matrix.getDouble(position, this.m_xDim)), x) > 0) {
          break looper;
        }
      }
      if ((--position) < 0) {
        continue outer;
      }

      this.m_y[have] = val = MatrixIterator2D._d(matrix.getDouble(
          position, this.m_yDim));
      if (this.m_allowEarlyEnd && (position >= (max - 1))) {
        position = max;
      }
      this.m_indexes[index] = position;
      this.m_sources[have] = index;
      ++have;
      type &= NumericalTypes.getTypes(val);
    }

    this.m_currentN = have;
    if (have > 0) {
      this.m_hasNext = true;
      this.m_x._setDoubleValue(x);
      this.m_type = (type | NumericalTypes.IS_DOUBLE);
    } else {
      this.m_hasNext = false;
      this.m_x._setDoubleValue(Double.POSITIVE_INFINITY);
      this.m_type = 0;
    }
  }

  /** {@inheritDoc} */
  @Override
  final void _findNext() {
    final double oldX;
    final int xDim, yDim;
    IMatrix matrix;
    double smallestLarger, xAtPosition, yAtPosition, yAtOldPosition;
    int index, position, max, oldPosition;
    boolean noSmallestLarger;

    oldX = MatrixIterator2D._d(this.m_x.doubleValue());
    xDim = this.m_xDim;
    yDim = this.m_yDim;
    smallestLarger = Double.POSITIVE_INFINITY;
    noSmallestLarger = true;

    // Find the smallest x-coordinate which is larger than the previous
    // x-coordinate. If we are not at the beginning of a matrix, then we
    // look for such an x-coordinate which also has a different
    // y-coordinate.
    for (index = this.m_indexes.length; (--index) >= 0;) {
      matrix = this.m_matrices[index];
      max = matrix.m();

      oldPosition = this.m_indexes[index];
      if (oldPosition >= max) {
        continue; // This matrix has already reached its end.
      }

      // Obtain the x and y value of the previous position.
      xAtPosition = MatrixIterator2D._d(matrix
          .getDouble(oldPosition, xDim));
      yAtPosition = yAtOldPosition = MatrixIterator2D._d(matrix.getDouble(
          oldPosition, yDim));
      position = oldPosition;

      // Try to increase the position in order to find the next coordinate.
      inner: for (;;) {
        if ((xAtPosition > oldX) && //
            (noSmallestLarger || //
            (EComparison.compareDoubles(xAtPosition, smallestLarger) < 0))) {
          // The new x-coordinate must be larger than the old one but
          // smaller than the smallest such increase we found before
          // (unless we did not discover a next point yet).
          if ((position <= 0)//
              || (EComparison.compareDoubles(yAtPosition, yAtOldPosition) != 0)//
              || (this.m_allowEarlyEnd && (position >= (max - 1)))) {
            // The y-must be different, because otherwise we can simply
            // omit the point.
            smallestLarger = xAtPosition;
            noSmallestLarger = false;
            break inner;
          }
        }

        // If we get here, the current position does not belong to such a
        // required next step, so we try to increase it.
        if ((++position) >= max) {
          break inner;
        }
        xAtPosition = MatrixIterator2D
            ._d(matrix.getDouble(position, xDim));
        yAtPosition = MatrixIterator2D
            ._d(matrix.getDouble(position, yDim));
      }
    }

    if (noSmallestLarger) {
      // We did not find a next x-coordinate which satisfies our request.
      // Thus, let's see if we can pick the largest possible coordinate.
      // This coordinate will denote the end of the iteration.
      smallestLarger = oldX;
      for (final IMatrix matrixx : this.m_matrices) {
        position = matrixx.m();
        if (position > 0) {
          xAtPosition = MatrixIterator2D._d(matrixx.getDouble(
              (position - 1), xDim));
          if (EComparison.compareDoubles(xAtPosition, smallestLarger) > 0) {
            smallestLarger = xAtPosition;
            noSmallestLarger = false;
          }
        }
      }
    }

    if (noSmallestLarger || (smallestLarger <= oldX)) {
      this.m_hasNext = false;
      this.m_currentN = 0;
      this.m_x._setDoubleValue(Double.POSITIVE_INFINITY);
      this.m_type = 0;
    } else {
      this.__setX(smallestLarger);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final double getDouble(final int row, final int column) {
    if ((row == 0) && (column >= 0) && (column < this.m_currentN)) {
      return this.m_y[column];
    }
    return super.getDouble(row, column);
  }

  /** {@inheritDoc} */
  @Override
  public final long getLong(final int row, final int column) {
    if ((row == 0) && (column >= 0) && (column < this.m_currentN)) {
      return ((long) (this.m_y[column]));
    }
    return super.getLong(row, column);
  }

  /**
   * This kind of matrix is always an integer matrix.
   *
   * @return {@code true}
   */
  @Override
  public final boolean isIntegerMatrix() {
    return ((this.m_type & NumericalTypes.IS_LONG) != 0);
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append(this.m_x.doubleValue());
    textOut.append(':');
    textOut.append(' ');
    super.toText(textOut);
  }
}