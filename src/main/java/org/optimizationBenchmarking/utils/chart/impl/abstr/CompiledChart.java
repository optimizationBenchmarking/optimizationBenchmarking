package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Font;

import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;

/** A compiled chart, ready for painting. */
public class CompiledChart extends CompiledTitledElement {

  /** the legend mode */
  private final ELegendMode m_legendMode;

  /**
   * Create a titled element
   *
   * @param title
   *          the title, or {@code null} if no title is specified
   * @param titleFont
   *          the title font, or {@code null} if no specific font is set
   * @param legendMode
   *          the legend mode
   */
  protected CompiledChart(final String title, final Font titleFont,
      final ELegendMode legendMode) {
    super(title, titleFont);

    if (legendMode == null) {
      throw new IllegalArgumentException("Legend mode must not be null.");//$NON-NLS-1$
    }

    this.m_legendMode = legendMode;
  }

  /**
   * Get the legend mode
   *
   * @return the legend mode defining whether and how the legend data
   *         should be printed
   */
  public final ELegendMode getLegendMode() {
    return this.m_legendMode;
  }

}
