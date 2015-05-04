package org.optimizationBenchmarking.utils.chart.spec;

import java.awt.Font;

/** The interface for chart components which have titles */
public interface ITitledElement extends IChartElement {
  /**
   * Set the title of the element. Depending on the
   * {@link org.optimizationBenchmarking.utils.chart.spec.IChart#setLegendMode(ELegendMode)
   * legend mode} of the chart, the title may or may not be printed. If you
   * do not set a title, it will not be printed anyway.
   *
   * @param title
   *          the title
   */
  public abstract void setTitle(final String title);

  /**
   * Set the title font of the element (can only be used after
   * {@link #setTitle(String)}). Depending on the chart implementation,
   * this font may be scaled to a smaller (or maybe even larger) size to
   * create a more overall pleasing visual expression. Thus, you can
   * directly use fonts from a
   * {@link org.optimizationBenchmarking.utils.graphics.style.font.FontPalette}
   * which would potentially be too large or too small to look good and
   * expect the underlying
   * {@link org.optimizationBenchmarking.utils.chart.spec.IChartDriver
   * chart driver} implementation to resize them appropriately.
   *
   * @param titleFont
   *          the title font
   */
  public abstract void setTitleFont(final Font titleFont);

}
