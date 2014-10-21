package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.optimizationBenchmarking.utils.graphics.chart.spec.ELineType;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** The base class for lines */
public final class Line2D extends _DataSeries2D {

  /** the line type */
  private final ELineType m_type;

  /**
   * Create a data series
   * 
   * @param title
   *          the title
   * @param titleFont
   *          the title font
   * @param color
   *          the color
   * @param stroke
   *          the stroke
   * @param data
   *          the matrix
   * @param hasStart
   *          do we have a starting point?
   * @param startX
   *          the start x
   * @param startY
   *          the start y
   * @param hasEnd
   *          do we have a ending point?
   * @param endX
   *          the end x
   * @param endY
   *          the end y
   * @param type
   *          the line type
   */
  Line2D(final String title, final Font titleFont, final Color color,
      final Stroke stroke, final IMatrix data, final boolean hasStart,
      final double startX, final double startY, final boolean hasEnd,
      final double endX, final double endY, final ELineType type) {
    super(title, titleFont, color, stroke, data, hasStart, startX, startY,
        hasEnd, endX, endY);
    Line2D._assertType(type);
    this.m_type = type;
  }

  /**
   * Assert the line type
   * 
   * @param type
   *          the line type
   */
  static final void _assertType(final ELineType type) {
    if (type == null) {
      throw new IllegalArgumentException("Line type must not be null."); //$NON-NLS-1$
    }
  }

  /**
   * Get the line type
   * 
   * @return the line type
   */
  public final ELineType getType() {
    return this.m_type;
  }

}
