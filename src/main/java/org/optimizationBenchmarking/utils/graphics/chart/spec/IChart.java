package org.optimizationBenchmarking.utils.graphics.chart.spec;

import java.awt.Graphics2D;

import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/** The base interface for charts */
public interface IChart extends ITitledElement, IToolJob {

  /**
   * Set the graphic to paint on
   * 
   * @param graphic
   *          the graphic to paint on
   */
  public abstract void setGraphic(final Graphics2D graphic);

  /**
   * Set the style set to use for painting
   * 
   * @param styles
   *          the style set to use for painting
   */
  public abstract void setStyleSet(final StyleSet styles);
}
