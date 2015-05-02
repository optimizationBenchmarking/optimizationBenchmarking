package org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.matrix.AbstractMatrix;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * A matrix iterator which iterates over two dimensions of a set of
 * matrices.
 */
public abstract class MatrixIterator2D extends AbstractMatrix implements
    Iterator<Number> {

  /** the matrices to iterate over */
  final IMatrix[] m_matrices;

  /** the x-dimension */
  final int m_xDim;

  /** the y-dimension */
  final int m_yDim;

  /** the current m-indexes */
  final int[] m_indexes;

  /** the number for the {@code x}-coordinates */
  _Number m_x;

  /** do we have a next element? */
  boolean m_hasNext;

  /** the number of columns */
  int m_currentN;

  /**
   * Create a new 2d matrix iterator
   * 
   * @param matrices
   *          the list of matrices
   * @param xDim
   *          the first (x) dimension
   * @param yDim
   *          the second (y) dimension
   */
  MatrixIterator2D(final int xDim, final int yDim, final IMatrix[] matrices) {
    super();

    this.m_xDim = xDim;
    this.m_yDim = yDim;
    this.m_matrices = matrices;

    this.m_indexes = new int[matrices.length];
  }

  /**
   * Iterate over a set of matrices
   * 
   * @param xDim
   *          the {@code x}-dimension
   * @param yDim
   *          the {@code y}-dimension
   * @param matrices
   *          the matrices
   * @return the iterator
   */
  public static final MatrixIterator2D iterate(final int xDim,
      final int yDim, final IMatrix[] matrices) {
    int i;
    boolean canLongX, canLongY;

    if (matrices == null) {
      throw new IllegalArgumentException(//
          "Matrix array cannot be null."); //$NON-NLS-1$
    }

    i = matrices.length;
    if (i <= 0) {
      throw new IllegalArgumentException(//
          "There must be at least one matrix to iterate over.");//$NON-NLS-1$
    }
    i = 0;
    canLongX = canLongY = true;

    for (final IMatrix matrix : matrices) {
      if (matrix == null) {
        throw new IllegalArgumentException(//
            "Matrix " + i + //$NON-NLS-1$
                " cannot be null.");//$NON-NLS-1$
      }

      try {
        if (!(matrix.selectRows(xDim).isIntegerMatrix())) {
          canLongX = false;
        }
        if (!(matrix.selectRows(yDim).isIntegerMatrix())) {
          canLongY = false;
        }
      } catch (final Throwable t) {
        throw new IllegalArgumentException(((//
            "Error in matrix " + i) + //$NON-NLS-1$
            ", see causing exception."),//$NON-NLS-1$
            t);
      }
    }

    if (canLongX && canLongY) {
      return new _MatrixIterator2DXLongYLong(xDim, yDim, matrices);
    }
    return null;
  }

  /**
   * Get the maximum value that {@link #n()} can take on in the case that
   * data is available from matrices for a given value of {@link #next() x}
   * .
   * 
   * @return the maximum value of {@link #n()}
   */
  public final int nMax() {
    return this.m_matrices.length;
  }

  /**
   * there is always exactly one row in this matrix
   * 
   * @return 1
   */
  @Override
  public final int m() {
    return 1;
  }

  /**
   * get the current number of columns
   * 
   * @return the current number of columns
   */
  @Override
  public final int n() {
    return this.m_currentN;
  }

  /** Find the next element. */
  abstract void _findNext();

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    if (this.m_hasNext) {
      return true;
    }
    this._findNext();
    return this.m_hasNext;
  }

  /** {@inheritDoc} */
  @Override
  public final BasicNumber next() {
    check: {
      if (this.m_hasNext) {
        break check;
      }
      this._findNext();
      if (this.m_hasNext) {
        break check;
      }
      throw new NoSuchElementException(//
          "The MatrixIterator2D does not contain another element. You should have checked hasNext() before invoking next()."//$NON-NLS-1$
      );
    }
    this.m_hasNext = false;

    return this.m_x;
  }

  /** {@inheritDoc} */
  @Override
  public final void remove() {
    throw new UnsupportedOperationException();
  }
}
