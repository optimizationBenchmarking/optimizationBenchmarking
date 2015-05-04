package org.optimizationBenchmarking.utils.chart.spec;

import java.awt.Color;
import java.awt.Stroke;

/** The basic interface for building data elements */
public interface IDataElement extends ITitledElement {

  /**
   * Set the color of the data element.
   *
   * @param color
   *          the color
   */
  public abstract void setColor(final Color color);

  /**
   * Set the stroke of the data element. If you do not set a stroke, a
   * default stroke will be used.
   *
   * @param stroke
   *          the stroke
   */
  public abstract void setStroke(final Stroke stroke);

}
