package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** The base class for data series elements */
public class CompiledDataSeries extends CompiledDataElement {

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
    super(id, title, titleFont, color, stroke);

    CompiledDataSeries._checkMatrix(data);
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
   * Get the data associated with this data series
   *
   * @return the data associated with this data series
   */
  public final IMatrix getData() {
    return this.m_data;
  }
}
