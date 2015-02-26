package org.optimizationBenchmarking.utils.chart.spec;

import java.awt.Font;

/** The interface for chart components which have titles */
public interface ITitledElement extends IChartElement {
  /**
   * set the title of the element
   * 
   * @param title
   *          the title
   */
  public abstract void setTitle(final String title);

  /**
   * set the title font of the element (can only be used after
   * {@link #setTitle(String)})
   * 
   * @param titleFont
   *          the title font
   */
  public abstract void setTitleFont(final Font titleFont);

}
