package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

/**
 * The class for all axes
 */
public final class Axis extends _TitledElement {

  /** the minimum */
  private final double m_min;
  /** the maximum */
  private final double m_max;
  /** the tick font */
  private final Font m_tickFont;
  /** the stroke */
  private final Stroke m_axisStroke;
  /** the axis color */
  private final Color m_axisColor;
  /** the grid line stroke */
  private final Stroke m_gridLineStroke;
  /** the grid line color */
  private final Color m_gridLineColor;

  /**
   * create the axis
   * 
   * @param title
   *          the title
   * @param axisColor
   *          the axis color
   * @param axisStroke
   *          the axis stroke
   * @param titleFont
   *          the title font
   * @param min
   *          the minimum value
   * @param max
   *          the maximum value
   * @param tickFont
   *          the tick font
   * @param gridLineStroke
   *          the grid line stroke
   * @param gridLineColor
   *          the grid line color
   */
  Axis(final String title, final Font titleFont, final Font tickFont,
      final Stroke axisStroke, final Color axisColor,
      final Stroke gridLineStroke, final Color gridLineColor,
      final double min, final double max) {
    super(title, titleFont);

    _AxisBuilder._assertMin(min);
    _AxisBuilder._assertMax(max);
    if (max <= min) {
      throw new IllegalArgumentException(//
          ((("Axis range [" + min) //$NON-NLS-1$
              + ',') + max)
              + "] is invalid, the minimum must be less than the maximum."); //$NON-NLS-1$
    }
    if (tickFont == null) {
      throw new IllegalArgumentException("Tick font cannot be null."); //$NON-NLS-1$
    }
    if (axisStroke == null) {
      throw new IllegalArgumentException("Axis stroke cannot be null."); //$NON-NLS-1$
    }
    if (axisColor == null) {
      throw new IllegalArgumentException("Axis color cannot be null."); //$NON-NLS-1$
    }
    if (gridLineStroke == null) {
      throw new IllegalArgumentException(
          "Grid line stroke cannot be null."); //$NON-NLS-1$
    }
    if (gridLineColor == null) {
      throw new IllegalArgumentException("Grid line color cannot be null."); //$NON-NLS-1$
    }
    this.m_min = min;
    this.m_max = max;
    this.m_tickFont = tickFont;
    this.m_axisStroke = axisStroke;
    this.m_axisColor = axisColor;
    this.m_gridLineStroke = gridLineStroke;
    this.m_gridLineColor = gridLineColor;
  }

  /**
   * Get the minimum value of this axis
   * 
   * @return the minimum value of this axis
   */
  public final double getMinimum() {
    return this.m_min;
  }

  /**
   * Get the maximum value of this axis
   * 
   * @return the maximum value of this axis
   */
  public final double getMaximum() {
    return this.m_max;
  }

  /**
   * Get the tick font of this axis
   * 
   * @return the tick font of this axis
   */
  public final Font getTickFont() {
    return this.m_tickFont;
  }

  /**
   * Get the axis stroke
   * 
   * @return the axis stroke
   */
  public final Stroke getAxisStroke() {
    return this.m_axisStroke;
  }

  /**
   * Get the axis color
   * 
   * @return the axis color
   */
  public final Color getAxisColor() {
    return this.m_axisColor;
  }

  /**
   * Get the grid line stroke
   * 
   * @return the grid line stroke
   */
  public final Stroke getGridLineStroke() {
    return this.m_gridLineStroke;
  }

  /**
   * Get the grid line color
   * 
   * @return the grid line color
   */
  public final Color getGridLineColor() {
    return this.m_gridLineColor;
  }
}
