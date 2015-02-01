package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** The base class for data series elements */
public class CompiledDataSeries extends CompiledTitledElement {

  /** the id of this data series */
  private final int m_id;

  /** the color of this element */
  private final Color m_color;

  /** the stroke of this element */
  private final Stroke m_stroke;

  /** the data matrix */
  final IMatrix m_data;

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
   */
  protected CompiledDataSeries(final int id, final String title,
      final Font titleFont, final Color color, final Stroke stroke,
      final IMatrix data) {
    super(title, titleFont);

    CompiledDataSeries._checkMatrix(data);
    if (stroke == null) {
      throw new IllegalArgumentException("Stroke must not be null."); //$NON-NLS-1$
    }
    if (color == null) {
      throw new IllegalArgumentException("Color must not be null."); //$NON-NLS-1$
    }
    this.m_id = id;
    this.m_color = color;
    this.m_stroke = stroke;
    this.m_data = data;
  }

  /**
   * check a given matrix
   * 
   * @param data
   *          the matrix to check
   */
  static final void _checkMatrix(final IMatrix data) {
    if ((data == null) || (data.m() <= 0) || (data.n() <= 0)) {
      throw new IllegalArgumentException("Data matrix " + data //$NON-NLS-1$
          + " is empty and cannot be represented as data series."); //$NON-NLS-1$
    }
  }

  /**
   * Get the color associated with this data series
   * 
   * @return the color associated with this data series
   */
  public final Color getColor() {
    return this.m_color;
  }

  /**
   * Get the stroke associated with this data series
   * 
   * @return the stroke associated with this data series
   */
  public final Stroke getStroke() {
    return this.m_stroke;
  }

  /**
   * Get the data associated with this data series
   * 
   * @return the data associated with this data series
   */
  public final IMatrix getData() {
    return this.m_data;
  }

  /**
   * Get the id of this data series
   * 
   * @return the unique identifier of this data series
   */
  public final int getID() {
    return this.m_id;
  }
}
