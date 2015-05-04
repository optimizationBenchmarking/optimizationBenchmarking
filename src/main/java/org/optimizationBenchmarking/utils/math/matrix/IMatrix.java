package org.optimizationBenchmarking.utils.math.matrix;

import java.util.Iterator;

import org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate;

/**
 * A two-dimensional {@link #m() m}&times;{@link #n() n}-matrix of
 * {@code double} values.
 */
public interface IMatrix {

  /**
   * The number of rows in the matrix, i.e., the matrix height.
   *
   * @return the number of rows in the matrix, i.e., the matrix height
   */
  public abstract int m();

  /**
   * The number of columns in the matrix, i.e., the matrix width.
   *
   * @return the number of columns in the matrix, i.e., the matrix width
   */
  public abstract int n();

  /**
   * Get the {@code double} value at the given row and column.
   *
   * @param row
   *          the row to get the value at
   * @param column
   *          the column to get the value
   * @return the value of the given location
   */
  public abstract double getDouble(final int row, final int column);

  /**
   * Get the {@code long} value at the given row and column.
   *
   * @param row
   *          the row to get the value at
   * @param column
   *          the column to get the value
   * @return the value of the given location
   */
  public abstract long getLong(final int row, final int column);

  /**
   * Are all values in this matrix integers? If so, you should use
   * {@link #getLong(int, int)} to access them. Otherwise, you must use
   * {@link #getDouble(int, int)}.
   *
   * @return {@code true} if and only if all values in this matrix are
   *         {@code long} integers
   */
  public abstract boolean isIntegerMatrix();

  /**
   * Select a set of columns from this matrix and provide a {@link IMatrix}
   * view on them
   *
   * @param cols
   *          the columns to select
   * @return the matrix view on these columns
   */
  public abstract IMatrix selectColumns(final int... cols);

  /**
   * Select a set of rows from this matrix and provide a {@link IMatrix}
   * view on them
   *
   * @param rows
   *          the rows to select
   * @return the matrix view on these rows
   */
  public abstract IMatrix selectRows(final int... rows);

  /**
   * Obtain the transposed matrix of this matrix
   *
   * @return the transposed matrix of this matrix
   */
  public abstract IMatrix transpose();

  /**
   * Provide an {@link java.util.Iterator iterator} which can iterate over
   * the columns of this matrix. Each time
   * {@link java.util.Iterator#next()} is called, it will return a column
   * vector (i.e., a {@link #m() m}&times;1 vector) representing one of the
   * columns of this matrix. It is not allowed to preserve such an object
   * between two calls to {@link java.util.Iterator#next()}. The
   * {@link java.util.Iterator iterator} itself may implement the
   * {@link IMatrix} interface and return itself on each call, thus
   * reducing the number of objects allocated.
   *
   * @return an {@link java.util.Iterator iterator} iterating over the
   *         column vectors making up this matrix.
   */
  public abstract Iterator<IMatrix> iterateColumns();

  /**
   * Provide an {@link java.util.Iterator iterator} which can iterate over
   * the rows of this matrix. Each time {@link java.util.Iterator#next()}
   * is called, it will return a row vector (i.e., a 1&times;{@link #n() m}
   * vector) representing one of the rows of this matrix. It is not allowed
   * to preserve such an object between two calls to
   * {@link java.util.Iterator#next()}. The {@link java.util.Iterator
   * iterator} itself may implement the {@link IMatrix} interface and
   * return itself on each call, thus reducing the number of objects
   * allocated.
   *
   * @return an {@link java.util.Iterator iterator} iterating over the row
   *         vectors making up this matrix.
   */
  public abstract Iterator<IMatrix> iterateRows();

  /**
   * Create a copy of this matrix if this matrix is somehow mutable. This
   * copy will not be affected by any change of any underlying data
   * structure. An object which is an instance of this interface may return
   * itself if it is guaranteed to be immutable. If it somehow may be
   * modified, it should return a copy. Ideally, if a copy is made, it
   * should be as compact as possible (memory-wise), and if multiple
   * similarly compact representations exist, the fastest (access-wise)
   * should be returned.
   *
   * @return the matrix copy
   */
  public abstract IMatrix copy();

  /**
   * Aggregate the values in a given column
   *
   * @param column
   *          the column index
   * @param aggregate
   *          the aggregate to append to
   */
  public abstract void aggregateColumn(final int column,
      final IAggregate aggregate);

  /**
   * Aggregate the values in a given row
   *
   * @param row
   *          the row index
   * @param aggregate
   *          the aggregate to append to
   */
  public abstract void aggregateRow(final int row,
      final IAggregate aggregate);
}
