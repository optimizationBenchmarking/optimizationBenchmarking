package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.optimizationBenchmarking.utils.chart.spec.ELineType;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** The base class for lines */
public class CompiledLine2D extends CompiledDataSeries {

  /** the line type */
  private final ELineType m_type;

  /**
   * Create a data series
   *
   * @param id
   *          the id
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
   * @param type
   *          the line type
   */
  protected CompiledLine2D(final int id, final String title,
      final Font titleFont, final Color color, final Stroke stroke,
      final IMatrix data, final ELineType type) {
    super(id, title, titleFont, color, stroke, data);
    CompiledLine2D._assertType(type);
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
