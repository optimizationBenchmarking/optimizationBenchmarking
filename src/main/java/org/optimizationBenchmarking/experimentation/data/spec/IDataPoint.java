package org.optimizationBenchmarking.experimentation.data.spec;

import java.util.List;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** The basic interface for data points. */
public interface IDataPoint extends IMatrix, List<Number>,
    Comparable<IDataPoint> {
  /**
   * Get the value at index {@code index}, as {@code double}.
   *
   * @param index
   *          the index
   * @return the value at that index, as {@code double}
   */
  public abstract double getDouble(final int index);

  /**
   * Get the value at index {@code index}, as {@code float}.
   *
   * @param index
   *          the index
   * @return the value at that index, as {@code float}
   */
  public abstract float getFloat(final int index);

  /**
   * Get the value at index {@code index}, as {@code long}.
   *
   * @param index
   *          the index
   * @return the value at that index, as {@code long}
   */
  public abstract long getLong(final int index);

  /**
   * Get the value at index {@code index}, as {@code int}.
   *
   * @param index
   *          the index
   * @return the value at that index, as {@code int}
   */
  public abstract int getInt(final int index);

  /**
   * Get the value at index {@code index}, as {@code short}.
   *
   * @param index
   *          the index
   * @return the value at that index, as {@code short}
   */
  public abstract short getShort(final int index);

  /**
   * Get the value at index {@code index}, as {@code byte}.
   *
   * @param index
   *          the index
   * @return the value at that index, as {@code byte}
   */
  public abstract byte getByte(final int index);

  /**
   * Get the value at the given {@code dimension}
   *
   * @param dimension
   *          the dimension
   * @return the number
   */
  @Override
  public abstract Number get(final int dimension);

  /**
   * store the data of this point into the destination {@code double} array
   *
   * @param dest
   *          the destination {@code double} array
   * @param destStart
   *          the starting index where the copying should begin
   */
  public abstract void toArray(final double[] dest, final int destStart);

  /**
   * store the data of this point into the destination {@code long} array
   *
   * @param dest
   *          the destination {@code long} array
   * @param destStart
   *          the starting index where the copying should begin
   */
  public abstract void toArray(final long[] dest, final int destStart);

  /**
   * store the data of this point into the destination {@code int} array
   *
   * @param dest
   *          the destination {@code int} array
   * @param destStart
   *          the starting index where the copying should begin
   */
  public abstract void toArray(final int[] dest, final int destStart);

  /**
   * A data point is a row vector (or row matrix) with {@link #m() m}=1
   * rows.
   *
   * @return 1
   */
  @Override
  public abstract int m();

  /**
   * A data point is a row vector (or row matrix) with {@link #size()}
   * columns.
   *
   * @return the same as {@link #size()}
   * @see #m()
   * @see #size()
   */
  @Override
  public abstract int n();
}
