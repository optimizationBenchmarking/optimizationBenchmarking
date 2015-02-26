package org.optimizationBenchmarking.utils.chart.spec;

import java.awt.Color;
import java.awt.Stroke;

/** The basic interface for building data elements */
public interface IDataElement extends ITitledElement {

  /**
   * set the title of the data element
   * 
   * @param title
   *          the title
   */
  @Override
  public abstract void setTitle(final String title);

  /**
   * set the color of the data element
   * 
   * @param color
   *          the color
   */
  public abstract void setColor(final Color color);

  /**
   * set the stroke of the data element
   * 
   * @param stroke
   *          the stroke
   */
  public abstract void setStroke(final Stroke stroke);

}
