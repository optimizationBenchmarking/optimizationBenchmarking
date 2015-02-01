package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import java.awt.Font;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ELegendMode;

/** A line chart. */
public class CompiledLineChart extends CompiledTitledElement {

  /** the legend mode */
  private final ELegendMode m_legendMode;

  /** the x-axis */
  private final CompiledAxis m_xAxis;
  /** the y-axis */
  private final CompiledAxis m_yAxis;
  /** the lines */
  private final ArrayListView<CompiledLine2D> m_lines;

  /**
   * Create a titled element
   * 
   * @param title
   *          the title, or {@code null} if no title is specified
   * @param titleFont
   *          the title font, or {@code null} if no specific font is set
   * @param legendMode
   *          the legend mode
   * @param x
   *          the x-axis
   * @param y
   *          the y-axis
   * @param lines
   *          the lines
   */
  protected CompiledLineChart(final String title, final Font titleFont,
      final ELegendMode legendMode, final CompiledAxis x,
      final CompiledAxis y, final ArrayListView<CompiledLine2D> lines) {
    super(title, titleFont);

    if (x == null) {
      throw new IllegalArgumentException("X-axis must not be null."); //$NON-NLS-1$
    }
    if (y == null) {
      throw new IllegalArgumentException("Y-axis must not be null."); //$NON-NLS-1$
    }
    if ((lines == null) || (lines.isEmpty())) {
      throw new IllegalArgumentException(
          "Line set must not be null or empty."); //$NON-NLS-1$
    }
    if (legendMode == null) {
      throw new IllegalArgumentException("Legend mode must not be null.");//$NON-NLS-1$
    }

    this.m_legendMode = legendMode;
    this.m_xAxis = x;
    this.m_yAxis = y;
    this.m_lines = lines;
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

  /**
   * Get the x-axis
   * 
   * @return the x-axis
   */
  public final CompiledAxis getXAxis() {
    return this.m_xAxis;
  }

  /**
   * Get the y-axis
   * 
   * @return the y-axis
   */
  public final CompiledAxis getYAxis() {
    return this.m_yAxis;
  }

  /**
   * Get the lines
   * 
   * @return the lines
   */
  public final ArrayListView<CompiledLine2D> getLines() {
    return this.m_lines;
  }
}
