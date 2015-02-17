package org.optimizationBenchmarking.utils.graphics.chart.spec;

import java.awt.Color;
import java.awt.Stroke;

/** The interface for building scalar data elements */
public interface IDataScalar extends IDataElement {

  /**
   * set the title of the scalar data
   * 
   * @param title
   *          the title
   */
  @Override
  public abstract void setTitle(final String title);

  /**
   * set the color of the scalar data
   * 
   * @param color
   *          the color
   */
  @Override
  public abstract void setColor(final Color color);

  /**
   * set the stroke of the scalar data
   * 
   * @param stroke
   *          the stroke
   */
  @Override
  public abstract void setStroke(final Stroke stroke);

  /**
   * Set the value of the scalar data as {@code double}
   * 
   * @param value
   *          the value
   */
  public abstract void setData(final double value);

  /**
   * Set the value of the scalar data as {@code long}
   * 
   * @param value
   *          the value
   */
  public abstract void setData(final long value);

}
