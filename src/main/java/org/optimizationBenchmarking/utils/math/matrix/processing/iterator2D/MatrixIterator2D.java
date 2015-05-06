package org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.matrix.AbstractMatrix;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * <p>
 * A matrix iterator allows to iterate over a set of matrices along one
 * dimension and to build a row matrix representing the values of a second
 * dimension for each iteration step.
 * </p>
 * <p>
 * This object here represents a matrix which is changed in each iteration
 * step. I.e., if you call {@link #next()}, you obtain the next {@code x}
 * value as an instance of
 * {@link org.optimizationBenchmarking.utils.math.BasicNumber}. At the same
 * time, the common matrix operations such as {@link #getDouble(int, int)}
 * and {@link #getLong(int, int)} become available to access the {@code y}
 * values corresponding to the {@code x} value. Since this class somewhat
 * follows the contract of {@link java.util.Iterator}, make sure to always
 * call {@link #hasNext()} before invoking {@link #next()}.
 * <p>
 * But what does this iterator do exactly? Let us use an example. Assume
 * that we have the following three matrices:
 * </p>
 * <ol>
 * <li>Matrix 1:
 * <table border="1" summary="Matrix 1: A matrix with two columns.">
 * <caption>Matrix 1: A matrix with two columns.</caption>
 * <tr>
 * <th>{@code x}</th>
 * <th>{@code y}</th>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td>10</td>
 * </tr>
 * <tr>
 * <td>5</td>
 * <td>55</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>60</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>61</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>62</td>
 * </tr>
 * <tr>
 * <td>7</td>
 * <td>70</td>
 * </tr>
 * <tr>
 * <td>15</td>
 * <td>70</td>
 * </tr>
 * <tr>
 * <td>16</td>
 * <td>71</td>
 * </tr>
 * </table>
 * </li>
 * <li>Matrix 2:
 * <table border="1" summary="Matrix 2: A matrix with two columns.">
 * <caption>Matrix 2: A matrix with two columns.</caption>
 * <tr>
 * <th>{@code x}</th>
 * <th>{@code y}</th>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>22</td>
 * </tr>
 * <tr>
 * <td>4</td>
 * <td>40</td>
 * </tr>
 * <tr>
 * <td>5</td>
 * <td>56</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>63</td>
 * </tr>
 * <tr>
 * <td>8</td>
 * <td>80</td>
 * </tr>
 * <tr>
 * <td>8</td>
 * <td>80</td>
 * </tr>
 * <tr>
 * <td>12</td>
 * <td>80</td>
 * </tr>
 * <tr>
 * <td>13</td>
 * <td>80</td>
 * </tr>
 * </table>
 * </li>
 * <li>Matrix 3:
 * <table border="1" summary="Matrix 3: A matrix with two columns.">
 * <caption>Matrix 3: A matrix with two columns.</caption>
 * <tr>
 * <th>{@code x}</th>
 * <th>{@code y}</th>
 * </tr>
 * <tr>
 * <td>3</td>
 * <td>36</td>
 * </tr>
 * <tr>
 * <td>4</td>
 * <td>50</td>
 * </tr>
 * <tr>
 * <td>5</td>
 * <td>57</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>64</td>
 * </tr>
 * <tr>
 * <td>9</td>
 * <td>90</td>
 * </tr>
 * <tr>
 * <td>10</td>
 * <td>90</td>
 * </tr>
 * <tr>
 * <td>11</td>
 * <td>92</td>
 * </tr>
 * <tr>
 * <td>20</td>
 * <td>92</td>
 * </tr>
 * </table>
 * </li>
 * </ol>
 * <p>
 * Each of these matrices has two columns and several rows. Let us call the
 * first column "{@code x}" and the second column "{@code y}". The values
 * in the {@code x} column must be (and are in the example) rising,
 * although not necessarily steadily. No restrictions are imposed on the
 * values in the {@code y} column.
 * </p>
 * <p>
 * Assume that we want to walk along the {@code x} dimension, which maybe
 * represents a time axis, and for each distinct value of {@code x}, we
 * want to know a corresponding value of {@code y} from each of the three
 * matrices. In other words, for each value of {@code x}, we want to get a
 * row matrix of exactly one row and (at most) three columns (plus the
 * associated {@code x} value).
 * </p>
 * <p>
 * The "at most" in the previous sentence becomes clear when we look at
 * matrices 1 and 2 and notice that the first {@code x}-value in matrix 1
 * is {@code 1} while it is {@code 2} in matrix 2 (and {@code 3} in matrix
 * 3. Thus, for {@code x=1}, we have a row matrix with exactly one column
 * and the value {@code 10} in that column.
 * </p>
 * <p>
 * In the next iteration step, {@code x=2} and there are two columns: the
 * value from matrix 1 is still {@code 10}, since there is no value for
 * exactly {@code 2}. From matrix 2, we get {@code 22}. The next iteration
 * steps continue like that.
 * </p>
 * <p>
 * There may be an {@code x} value with multiple associated {@code y}
 * values: In matrix 1, {@code x=2} corresponds to {@code y=60},
 * {@code y=61}, and {@code y=62}. In this case, we only consider
 * {@code y=62}. The idea is that if {@code x} is indeed a time dimension
 * and we may take multiple measurements ({@code y} values) within the same
 * time unit. In this case, we be interested in the last one, since in
 * optimization, the newest result is usually the best.
 * </p>
 * <p>
 * On the other hand, we may ignore time steps where nothing changes
 * entirely. One such example is {@code x=10} in matrix 3, which has the
 * same corresponding {@code y} value as {@code x=9}, namely {@code y=90}.
 * We can simply ignore it in the iteration.
 * </p>
 * <p>
 * An exception to the rule is the very last {@code x} value, which we
 * always include in the iteration. Although {@code x=20} in matrix 3 has
 * the same value as {@code x=11}, we will present it as very last
 * iteration step, so that a calling routine can see the full range of the
 * {@code x} axis.
 * </p>
 * <p>
 * All in all, the iteration over matrices 1, 2, and 3 would look as
 * follows:
 * </p>
 * <ol>
 * <li>{@code x=1}, row matrix = {@code (10)}</li>
 * <li>{@code x=2}, row matrix = {@code (10, 22)}</li>
 * <li>{@code x=3}, row matrix = {@code (10, 22, 36)}</li>
 * <li>{@code x=4}, row matrix = {@code (10, 40, 50)}</li>
 * <li>{@code x=5}, row matrix = {@code (55, 56, 57)}</li>
 * <li>{@code x=6}, row matrix = {@code (62, 63, 64)}</li>
 * <li>{@code x=7}, row matrix = {@code (70, 63, 64)}</li>
 * <li>{@code x=8}, row matrix = {@code (70, 80, 64)}</li>
 * <li>{@code x=9}, row matrix = {@code (70, 80, 90)}</li>
 * <li>{@code x=11}, row matrix = {@code (70, 80, 92)}</li>
 * <li>{@code x=16}, row matrix = {@code (71, 80, 92)}</li>
 * <li>{@code x=20}, row matrix = {@code (71,, 80, 92)}</li>
 * </ol>
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
   * Check a {@code double} and throw an exception if we encounter NaN
   *
   * @param d
   *          the double
   * @return {@code d}
   */
  static final double _d(final double d) {
    if (d != d) {
      throw new IllegalArgumentException("Encountered nan!"); //$NON-NLS-1$
    }
    return d;
  }

  /**
   * Create the iterator to iterate over a set of matrices.
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

    if (canLongX) {
      if (canLongY) {
        return new _MatrixIterator2DXLongYLong(xDim, yDim, matrices);
      }
      return new _MatrixIterator2DXLongYDouble(xDim, yDim, matrices);
    }
    if (canLongY) {
      return new _MatrixIterator2DXDoubleYLong(xDim, yDim, matrices);
    }
    return new _MatrixIterator2DXDoubleYDouble(xDim, yDim, matrices);
  }

  /**
   * Create the iterator to iterate over a set of matrices.
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
      final int yDim, final Collection<? extends IMatrix> matrices) {
    if (matrices == null) {
      throw new IllegalArgumentException(//
          "Matrix collection to iterate over cannot be null."); //$NON-NLS-1$
    }
    return MatrixIterator2D.iterate(xDim, yDim,
        matrices.toArray(new IMatrix[matrices.size()]));
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
   * Get the current number of columns. This may be less than
   * {@link #nMax()} but is always <code>&gt;0</code>.
   *
   * @return the current number of columns
   */
  @Override
  public final int n() {
    return this.m_currentN;
  }

  /** The internal method to find the next iteration element. */
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

  /**
   * Obtain the next value of the {@code x} coordinate. Only after this
   * method has been called, the common matrix operations are permissible
   * on this instance.
   *
   * @return the next value of the {@code x} coordinate
   */
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
