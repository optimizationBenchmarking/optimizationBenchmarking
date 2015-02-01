package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** The base class for data series elements */
public class CompiledDataSeries2D extends CompiledDataSeries {

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
   */
  protected CompiledDataSeries2D(final int id, final String title,
      final Font titleFont, final Color color, final Stroke stroke,
      final IMatrix data, final boolean hasStart, final double startX,
      final double startY, final boolean hasEnd, final double endX,
      final double endY) {
    super(id, title, titleFont, color, stroke,
        (((hasStart || hasEnd) ? CompiledDataSeries2D
            ._checkMatrix2D(new _WrappedMatrix2D(CompiledDataSeries2D
                ._checkMatrix2D(data), hasStart, startX, startY, hasEnd,
                endX, endY)) : CompiledDataSeries2D._checkMatrix2D(data))));
  }

  /**
   * check a given matrix
   * 
   * @param data
   *          the matrix
   * @return the matrix
   */
  static final IMatrix _checkMatrix2D(final IMatrix data) {
    CompiledDataSeries._checkMatrix(data);
    if (data.n() != 2) {
      throw new IllegalArgumentException(
          "A matrix for 2D-lines must have 2 columns, but this matrix has " //$NON-NLS-1$
              + data.n());
    }
    return data;
  }
}
