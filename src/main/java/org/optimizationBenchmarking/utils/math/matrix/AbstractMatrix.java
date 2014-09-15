package org.optimizationBenchmarking.utils.math.matrix;

import java.util.Iterator;

import org.optimizationBenchmarking.utils.collections.ArrayUtils;
import org.optimizationBenchmarking.utils.collections.iterators.InstanceIterator;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.matrix.impl.ByteMatrix1D;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix1D;
import org.optimizationBenchmarking.utils.math.matrix.impl.FloatMatrix1D;
import org.optimizationBenchmarking.utils.math.matrix.impl.IntMatrix1D;
import org.optimizationBenchmarking.utils.math.matrix.impl.LongMatrix1D;
import org.optimizationBenchmarking.utils.math.matrix.impl.ShortMatrix1D;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** The base class for matrix implementations. */
public class AbstractMatrix implements IMatrix, ITextable {

  /** create */
  protected AbstractMatrix() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public int m() {
    return 0;
  }

  /** {@inheritDoc} */
  @Override
  public int n() {
    return 0;
  }

  /** {@inheritDoc} */
  @Override
  public double getDouble(final int row, final int column) {
    throw new IndexOutOfBoundsException(
        ((("Matrix access getDouble(" + row) + ',') + column + //$NON-NLS-1$
            " is invalid, the valid index range is (0.." + this.m() + //$NON-NLS-1$
            ", 0.." + this.n()) + '.'); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public long getLong(final int row, final int column) {
    throw new IndexOutOfBoundsException(
        ((("Matrix access getLong(" + row) + ',') + column + //$NON-NLS-1$
            " is invalid, the valid index range is (0.." + this.m() + //$NON-NLS-1$
            ", 0.." + this.n()) + '.'); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public boolean isIntegerMatrix() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public IMatrix selectColumns(final int... cols) {
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

    return new MatrixColumns<>(this, cols);
  }

  /** {@inheritDoc} */
  @Override
  public IMatrix selectRows(final int... rows) {
    int i;

    checker: {
      i = 0;
      for (final int j : rows) {
        if (j != (i++)) {
          break checker;
        }
      }
      if (i == this.m()) {
        return this;
      }
    }

    return new MatrixRows<>(this, rows);
  }

  /** {@inheritDoc} */
  @Override
  public IMatrix transpose() {
    if ((this.m() > 1) || (this.n() > 1)) {
      return new TransposedMatrix<>(this);
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    final int n, m;
    int row, col;

    textOut.append('[');
    n = this.n();
    m = this.m();
    if (this.isIntegerMatrix()) {
      for (row = 0; row < m; row++) {
        if (row > 0) {
          textOut.append(',');
          textOut.append(' ');
        }
        textOut.append('[');
        for (col = 0; col < n; col++) {
          if (col > 0) {
            textOut.append(',');
            textOut.append(' ');
          }
          textOut.append(Long.toString(this.getLong(row, col)));

        }
        textOut.append(']');
      }
    } else {
      for (row = 0; row < m; row++) {
        if (row > 0) {
          textOut.append(',');
          textOut.append(' ');
        }
        textOut.append('[');
        for (col = 0; col < n; col++) {
          if (col > 0) {
            textOut.append(',');
            textOut.append(' ');
          }
          textOut.append(Double.toString(this.getDouble(row, col)));

        }
        textOut.append(']');
      }
    }
    textOut.append(']');
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final MemoryTextOutput sb;
    sb = new MemoryTextOutput(this.m() * this.n() * 12);
    this.toText(sb);
    return sb.toString();
  }

  /** {@inheritDoc} */
  @Override
  public Iterator<IMatrix> iterateColumns() {
    if (this.n() <= 1) {
      return new InstanceIterator<IMatrix>(this);
    }

    return new MatrixColumnIterator<>(this);
  }

  /** {@inheritDoc} */
  @Override
  public Iterator<IMatrix> iterateRows() {
    if (this.m() <= 1) {
      return new InstanceIterator<IMatrix>(this);
    }

    return new MatrixRowIterator<>(this);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    final IMatrix p;
    final int n;
    final boolean in;

    int i, j;

    if (o == this) {
      return true;
    }

    if (o instanceof IMatrix) {
      p = ((IMatrix) o);
      i = this.m();
      if (i != p.m()) {
        return false;
      }
      n = this.n();
      if (n != p.n()) {
        return false;
      }

      in = this.isIntegerMatrix();
      if (in != p.isIntegerMatrix()) {
        return false;
      }

      if (in) {
        for (; (--i) >= 0;) {
          for (j = n; (--j) >= 0;) {
            if (this.getLong(i, j) != p.getLong(i, j)) {
              return false;
            }
          }
        }
        return true;
      }

      for (; (--i) >= 0;) {
        for (j = n; (--j) >= 0;) {
          if (this.getDouble(i, j) != p.getDouble(i, j)) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    final int m, n;
    int hc, i, j;

    m = this.m();
    n = this.n();
    hc = 1;
    if (this.isIntegerMatrix()) {
      for (i = 0; i < m; i++) {
        for (j = 0; j < n; j++) {
          hc = HashUtils.combineHashes(hc,
              HashUtils.hashCode(this.getLong(i, j)));
        }
      }
      return hc;
    }

    for (i = 0; i < m; i++) {
      for (j = 0; j < n; j++) {
        hc = HashUtils.combineHashes(hc,
            HashUtils.hashCode(this.getDouble(i, j)));
      }
    }
    return hc;
  }

  /** {@inheritDoc} */
  @Override
  public IMatrix copy() {
    return AbstractMatrix.copy(this);
  }

  // /**
  // * Copy a given matrix. This method is designed for speed and does not
  // * consider much about the memory but is relatively fast.
  // *
  // * @param matrix
  // * the matrix to copy
  // * @return the copy of the matrix
  // */
  // public static final IMatrix copy(final IMatrix matrix) {
  // final long[] ldata;
  // final double[] ddata;
  // final int m, n;
  // int i, j, k;
  //
  // m = matrix.m();
  // n = matrix.n();
  // k = (m * n);
  //
  // if (matrix.isIntegerMatrix()) {
  // ldata = new long[k];
  //
  // for (i = m; (--i) >= 0;) {
  // for (j = n; (--j) >= 0;) {
  // ldata[--k] = matrix.getLong(i, j);
  // }
  // }
  //
  // return new LongMatrix1D(ldata, m, n);
  // }
  //
  // ddata = new double[k];
  //
  // for (i = m; (--i) >= 0;) {
  // for (j = n; (--j) >= 0;) {
  // ddata[--k] = matrix.getDouble(i, j);
  // }
  // }
  //
  // return new DoubleMatrix1D(ddata, m, n);
  // }

  /**
   * Copy a given matrix and by using as little permanent memory as
   * possible.
   * 
   * @param matrix
   *          the matrix to copy
   * @return the copy of the matrix
   */
  public static final IMatrix copy(final IMatrix matrix) {
    final long[] ldata;
    final double[] ddata;
    final int m, n;
    boolean isFloat, isLong, isInt, isShort, isByte;
    int i, j, k;
    long l;
    double d;

    m = matrix.m();
    n = matrix.n();
    k = (m * n);
    isLong = isInt = isShort = isByte = true;

    if (matrix.isIntegerMatrix()) {
      ldata = new long[k];

      for (i = m; (--i) >= 0;) {
        for (j = n; (--j) >= 0;) {
          ldata[--k] = l = matrix.getLong(i, j);
          if (isInt) {
            if ((l < Integer.MIN_VALUE) || (l > Integer.MAX_VALUE)) {
              isInt = isShort = isByte = false;
            } else {
              if (isShort) {
                if ((l < Short.MIN_VALUE) || (l > Short.MAX_VALUE)) {
                  isShort = isByte = false;
                } else {
                  if (isByte) {
                    if ((l < Byte.MIN_VALUE) || (l > Byte.MAX_VALUE)) {
                      isByte = false;
                    }
                  }
                }
              }
            }
          }
        }
      }

      if (isByte) {
        return new ByteMatrix1D(ArrayUtils.longsToBytes(ldata), m, n);
      }
      if (isShort) {
        return new ShortMatrix1D(ArrayUtils.longsToShorts(ldata), m, n);
      }
      if (isInt) {
        return new IntMatrix1D(ArrayUtils.longsToInts(ldata), m, n);
      }
      return new LongMatrix1D(ldata, m, n);
    }

    ddata = new double[k];
    isFloat = isLong = true;

    for (i = m; (--i) >= 0;) {
      for (j = n; (--j) >= 0;) {
        ddata[--k] = d = matrix.getDouble(i, j);
        if (isFloat && (((float) d) != d)) {
          isFloat = false;
        }
        if (isLong) {
          l = ((long) d);
          if (l == d) {
            if (isInt) {
              if ((l < Integer.MIN_VALUE) || (l > Integer.MAX_VALUE)) {
                isInt = isShort = isByte = false;
              } else {
                if (isShort) {
                  if ((l < Short.MIN_VALUE) || (l > Short.MAX_VALUE)) {
                    isShort = isByte = false;
                  } else {
                    if (isByte) {
                      if ((l < Byte.MIN_VALUE) || (l > Byte.MAX_VALUE)) {
                        isByte = false;
                      }
                    }
                  }
                }
              }
            }
          } else {
            isLong = isInt = isShort = isByte = false;
          }
        }
      }
    }

    if (isByte) {
      return new ByteMatrix1D(ArrayUtils.doublesToBytes(ddata), m, n);
    }
    if (isShort) {
      return new ShortMatrix1D(ArrayUtils.doublesToShorts(ddata), m, n);
    }
    if (isInt) {
      return new IntMatrix1D(ArrayUtils.doublesToInts(ddata), m, n);
    }
    if (isLong) {
      return new LongMatrix1D(ArrayUtils.doublesToLongs(ddata), m, n);
    }
    if (isFloat) {
      return new FloatMatrix1D(ArrayUtils.doublesToFloats(ddata), m, n);
    }
    return new DoubleMatrix1D(ddata, m, n);
  }
}
