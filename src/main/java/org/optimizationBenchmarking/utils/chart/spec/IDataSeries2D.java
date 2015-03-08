package org.optimizationBenchmarking.utils.chart.spec;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** The interface for building data series */
public interface IDataSeries2D extends IDataSeries {

  /**
   * Set a starting point of the 2D series (line). If you do not set a
   * starting point, the start will be taken from the
   * {@link #setData(IMatrix) data matrix}.
   * 
   * @param x
   *          the {@code x}-coordinate of the starting point
   * @param y
   *          the {@code y}-coordinate of the starting point
   */
  public abstract void setStart(final double x, final double y);

  /**
   * Set an end point for the 2D series (line). If you do not set a ending
   * point, the end will be taken from the {@link #setData(IMatrix) data
   * matrix}.
   * 
   * @param x
   *          the {@code x}-coordinate of the end point
   * @param y
   *          the {@code y}-coordinate of the end point
   */
  public abstract void setEnd(final double x, final double y);
}
