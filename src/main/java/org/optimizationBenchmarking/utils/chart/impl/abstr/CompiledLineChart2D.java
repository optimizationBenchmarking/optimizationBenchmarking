package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Font;

import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** A compiled 2D line chart, ready for painting. */
public class CompiledLineChart2D extends CompiledChart {

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
  protected CompiledLineChart2D(final String title, final Font titleFont,
      final ELegendMode legendMode, final CompiledAxis x,
      final CompiledAxis y, final ArrayListView<CompiledLine2D> lines) {
    super(title, titleFont, legendMode);

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
    this.m_xAxis = x;
    this.m_yAxis = y;
    this.m_lines = lines;
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
