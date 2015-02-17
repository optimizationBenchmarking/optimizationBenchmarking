package org.optimizationBenchmarking.utils.graphics.chart.spec;

import java.awt.Color;
import java.awt.Stroke;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** The interface for building data series */
public interface IDataSeries extends IDataElement {

  /**
   * set the title of the series
   * 
   * @param title
   *          the title
   */
  @Override
  public abstract void setTitle(final String title);

  /**
   * set the color of the data series
   * 
   * @param color
   *          the color
   */
  @Override
  public abstract void setColor(final Color color);

  /**
   * set the stroke of the data series
   * 
   * @param stroke
   *          the stroke
   */
  @Override
  public abstract void setStroke(final Stroke stroke);

  /**
   * Set the data of the data series. The required dimensions of the matrix
   * depend on the diagram or series type.
   * 
   * @param matrix
   *          the matrix to take the data from
   */
  public abstract void setData(final IMatrix matrix);

}
