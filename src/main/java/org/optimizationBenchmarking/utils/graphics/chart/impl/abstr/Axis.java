package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import java.awt.Font;

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

  /**
   * create the axis
   * 
   * @param title
   *          the title
   * @param titleFont
   *          the title font
   * @param min
   *          the minimum value
   * @param max
   *          the maximum value
   * @param tickFont
   *          the tick font
   */
  Axis(final String title, final Font titleFont, final double min,
      final double max, final Font tickFont) {
    super(title, titleFont);

    _AxisBuilder._assertMin(min);
    _AxisBuilder._assertMax(max);
    if (max <= min) {
      throw new IllegalArgumentException(//
          ((("Axis range [" + min) //$NON-NLS-1$
              + ',') + max)
              + "] is invalid, the minimum must be less than the maximum."); //$NON-NLS-1$
    }
    this.m_min = min;
    this.m_max = max;
    this.m_tickFont = tickFont;
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
   * Get the tick font of this axis, or {@code null} if none is specified
   * 
   * @return the tick font of this axis, or {@code null} if none is
   *         specified
   */
  public final Font getTickFont() {
    return this.m_tickFont;
  }
}
