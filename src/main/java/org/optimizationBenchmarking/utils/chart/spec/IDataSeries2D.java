package org.optimizationBenchmarking.utils.chart.spec;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** The interface for building data series */
public interface IDataSeries2D extends IDataSeries {

  /**
   * Set the data of the data series. This matrix must be an
   * <code>{@link org.optimizationBenchmarking.utils.math.matrix.IMatrix#m() m}&times;2</code>
   * -matrix, i.e., one with two columns.
   * 
   * @param matrix
   *          the matrix to take the data from
   */
  @Override
  public abstract void setData(final IMatrix matrix);

  /**
   * Set a starting point of the 2D series (line)
   * 
   * @param x
   *          the {@code x}-coordinate of the starting point
   * @param y
   *          the {@code y}-coordinate of the starting point
   */
  public abstract void setStart(final double x, final double y);

  /**
   * Set an end point for the 2D series (line)
   * 
   * @param x
   *          the {@code x}-coordinate of the end point
   * @param y
   *          the {@code y}-coordinate of the end point
   */
  public abstract void setEnd(final double x, final double y);
}
