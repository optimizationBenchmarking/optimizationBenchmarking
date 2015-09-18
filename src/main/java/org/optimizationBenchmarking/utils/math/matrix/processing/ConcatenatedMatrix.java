package org.optimizationBenchmarking.utils.math.matrix.processing;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.math.matrix.AbstractMatrix;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * A concatenated matrix is a matrix composed of several components
 */
public final class ConcatenatedMatrix extends AbstractMatrix {

  /** the matrixes */
  private final IMatrix[][] m_matrices;

  /** the number of rows */
  private final int m_m;

  /** a lookup table for rows */
  private final int[] m_mLookup;

  /** the number of columns */
  private final int m_n;

  /** a lookup table for columns */
  private final int[] m_nLookup;

  /** is this an integer matrix? */
  private final boolean m_isInteger;

  /**
   * Create the concatenated matrix
   *
   * @param matrices
   *          the matrix array
   */
  public ConcatenatedMatrix(final IMatrix[][] matrices) {
    super();

    final int[] mLookup, nLookup;
    final int nRows, nCols;
    int i, j, m, n, rowHeight, colWidth;
    IMatrix[] row;
    IMatrix cur;
    boolean isInteger;

    if (matrices == null) {
      throw new IllegalArgumentException(//
          "The matrix array cannot be null."); //$NON-NLS-1$
    }

    nRows = matrices.length;
    if (nRows <= 0) {
      throw new IllegalArgumentException(//
          "The matrix array cannot have 0 rows."); //$NON-NLS-1$
    }
    this.m_mLookup = mLookup = new int[nRows];

    nCols = matrices[0].length;
    if (nCols <= 0) {
      throw new IllegalArgumentException(//
          "The matrix array cannot have 0 columns."); //$NON-NLS-1$
    }
    this.m_nLookup = nLookup = new int[nCols];

    m = n = 0;
    isInteger = true;
    for (i = 0; i < nRows; i++) {
      row = matrices[i];

      if (row == null) {
        throw new IllegalArgumentException(//
            "The " + i + //$NON-NLS-1$
                "th row in the set of matrices is null.");//$NON-NLS-1$
      }

      rowHeight = row[0].m();

      if (i == 0) {
        if (rowHeight <= 0) {
          throw new IllegalArgumentException(//
              "Now matrix without rows (m<=0) can be part of a concatenated matrix."); //$NON-NLS-1$
        }
      }
      m += rowHeight;
      mLookup[i] = m;

      n = 0;
      for (j = 0; j < nCols; j++) {
        cur = row[j];
        isInteger &= cur.isIntegerMatrix();
        colWidth = cur.n();
        n += colWidth;
        if (i == 0) {
          if (colWidth <= 0) {
            throw new IllegalArgumentException(//
                "Now matrix without columns (n<=0) can be part of a concatenated matrix."); //$NON-NLS-1$
          }
          nLookup[j] = n;
        }
        if (n != nLookup[j]) {
          throw new IllegalArgumentException(//
              "Number of matrix columns does not math, found " //$NON-NLS-1$
                  + colWidth + " but expected " + //$NON-NLS-1$
                  matrices[0][j].n());
        }
        if (cur.m() != rowHeight) {
          throw new IllegalArgumentException(//
              "Number of matrix rows does not math, found " //$NON-NLS-1$
                  + cur.m() + " but expected " + //$NON-NLS-1$
                  rowHeight);
        }
      }
    }

    this.m_matrices = matrices;
    this.m_isInteger = isInteger;
    this.m_m = m;
    this.m_n = n;
  }

  /** {@inheritDoc} */
  @Override
  public final int m() {
    return this.m_m;
  }

  /** {@inheritDoc} */
  @Override
  public final int n() {
    return this.m_n;
  }

  /** {@inheritDoc} */
  @Override
  public final double getDouble(final int row, final int column) {
    final int[] rows, cols;
    final int useRow, useCol;
    int i, j;

    find: {
      rows = this.m_mLookup;
      i = Arrays.binarySearch(rows, row);
      if (i < 0) {
        i = -(i + 1);
      } else {
        ++i;
      }
      if (i >= rows.length) {
        break find;
      }
      if (i > 0) {
        useRow = (row - rows[i - 1]);
      } else {
        useRow = row;
      }

      cols = this.m_nLookup;
      j = Arrays.binarySearch(cols, column);
      if (j < 0) {
        j = -(j + 1);
      } else {
        j++;
      }
      if (j >= cols.length) {
        break find;
      }
      if (j > 0) {
        useCol = (column - cols[j - 1]);
      } else {
        useCol = column;
      }

      return this.m_matrices[i][j].getDouble(useRow, useCol);
    }

    return super.getDouble(row, column);
  }

  /** {@inheritDoc} */
  @Override
  public final long getLong(final int row, final int column) {
    final int[] rows, cols;
    final int useRow, useCol;
    int i, j;

    find: {
      rows = this.m_mLookup;
      i = Arrays.binarySearch(rows, row);
      if (i < 0) {
        i = -(i + 1);
      } else {
        ++i;
      }
      if (i >= rows.length) {
        break find;
      }
      if (i > 0) {
        useRow = (row - rows[i - 1]);
      } else {
        useRow = row;
      }

      cols = this.m_nLookup;
      j = Arrays.binarySearch(cols, column);
      if (j < 0) {
        j = -(j + 1);
      } else {
        j++;
      }
      if (j >= cols.length) {
        break find;
      }
      if (j > 0) {
        useCol = (column - cols[j - 1]);
      } else {
        useCol = column;
      }

      return this.m_matrices[i][j].getLong(useRow, useCol);
    }

    return super.getLong(row, column);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isIntegerMatrix() {
    return this.m_isInteger;
  }
}
