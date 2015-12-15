package org.optimizationBenchmarking.utils.math.matrix.impl;

import org.optimizationBenchmarking.utils.IImmutable;
import org.optimizationBenchmarking.utils.math.matrix.AbstractMatrix;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * A square matrix whose diagonal elements are all {@code 0} and the
 * elements below the diagonal mirror those above, backed by a
 * one-dimensional array of {@code double}.
 */
public final class DoubleDistanceMatrix1D extends AbstractMatrix
    implements IImmutable {

  /** the m */
  private final int m_m;

  /** the data */
  private final double[] m_data;

  /**
   * create the matrix
   *
   * @param data
   *          the data
   * @param m
   *          the m
   */
  public DoubleDistanceMatrix1D(final double[] data, final int m) {
    super();

    if ((data == null) || (data.length <= 0)) {
      throw new IllegalArgumentException(//
          "Matrix data must not be null and must have at least one row."); //$NON-NLS-1$
    }

    if (data.length != (((m * (m - 1)) >>> 1))) {
      throw new IllegalArgumentException(//
          ((("Distance matrix data must contain exactly " + //$NON-NLS-1$
              (((m * (m - 1)) >>> 1)) + //
              " non-zero elements to facilitate an " + m) + '*') + //$NON-NLS-1$
              m + " distance matrix, but contains " + data.length) + '.'); //$NON-NLS-1$
    }

    this.m_data = data;
    this.m_m = m;
  }

  /** {@inheritDoc} */
  @Override
  public final int m() {
    return this.m_m;
  }

  /** {@inheritDoc} */
  @Override
  public final int n() {
    return this.m_m;
  }

  /** {@inheritDoc} */
  @Override
  public final double getDouble(final int row, final int column) {
    if ((row >= 0) && (row < this.m_m) && (column >= 0)
        && (column < this.m_m)) {
      if (row < column) {
        return this.m_data[(((row * this.m_m) - ((row * (row + 3)) >>> 1))
            + column) - 1];
      }
      if (row > column) {
        return this.m_data[(((column * this.m_m)
            - ((column * (column + 3)) >>> 1)) + row) - 1];
      }
      return 0d;
    }
    return super.getDouble(row, column);// throw IndexOutOfBoundsException
  }

  /** {@inheritDoc} */
  @Override
  public final long getLong(final int row, final int column) {
    if ((row >= 0) && (row < this.m_m) && (column >= 0)
        && (column < this.m_m)) {
      if (row < column) {
        return ((long) (this.m_data[(((row * this.m_m)
            - ((row * (row + 3)) >>> 1)) + column) - 1]));
      }
      if (row > column) {
        return ((long) (this.m_data[(((column * this.m_m)
            - ((column * (column + 3)) >>> 1)) + row) - 1]));
      }
      return 0L;
    }
    return super.getLong(row, column);// throw IndexOutOfBoundsException
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isIntegerMatrix() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix copy() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix transpose() {
    return this;
  }

  /**
   * Get the array backing this matrix
   *
   * @return the array backing this matrix
   */
  public final double[] getDataRef() {
    return this.m_data;
  }
}
