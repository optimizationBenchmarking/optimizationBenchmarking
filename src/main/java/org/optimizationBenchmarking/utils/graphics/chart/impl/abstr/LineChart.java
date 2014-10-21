package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import java.awt.Font;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** A line chart. */
public class LineChart extends _TitledElement {

  /** should we show the legend */
  private final boolean m_showLegend;

  /** the x-axis */
  private final Axis m_xAxis;
  /** the y-axis */
  private final Axis m_yAxis;
  /** the lines */
  private final ArrayListView<Line2D> m_lines;

  /**
   * Create a titled element
   * 
   * @param title
   *          the title, or {@code null} if no title is specified
   * @param titleFont
   *          the title font, or {@code null} if no specific font is set
   * @param showLegend
   *          should the legend be printed?
   * @param x
   *          the x-axis
   * @param y
   *          the y-axis
   * @param lines
   *          the lines
   */
  LineChart(final String title, final Font titleFont,
      final boolean showLegend, final Axis x, final Axis y,
      final ArrayListView<Line2D> lines) {
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

    this.m_showLegend = showLegend;
    this.m_xAxis = x;
    this.m_yAxis = y;
    this.m_lines = lines;
  }

  /**
   * Should we show the legend
   * 
   * @return {@code true} if a legend should be printed, {@code false}
   *         otherwise
   */
  public final boolean shouldLegendBeShown() {
    return this.m_showLegend;
  }

  /**
   * Get the x-axis
   * 
   * @return the x-axis
   */
  public final Axis getXAxis() {
    return this.m_xAxis;
  }

  /**
   * Get the y-axis
   * 
   * @return the y-axis
   */
  public final Axis getYAxis() {
    return this.m_yAxis;
  }

  /**
   * Get the lines
   * 
   * @return the lines
   */
  public final ArrayListView<Line2D> getLines() {
    return this.m_lines;
  }
}
