package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

/** The base class for scalar data elements */
public class CompiledDataScalar extends CompiledDataElement {

  /** the data matrix */
  private final Number m_data;

  /**
   * Create a scalar data
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
   *          the scalar number
   */
  protected CompiledDataScalar(final int id, final String title,
      final Font titleFont, final Color color, final Stroke stroke,
      final Number data) {
    super(id, title, titleFont, color, stroke);

    if (data == null) {
      throw new IllegalArgumentException(//
          "Scalar data cannot be null.");//$NON-NLS-1$
    }
    this.m_data = data;
  }

  /**
   * Get the number associated with this scalar data
   *
   * @return the number associated with this scalar data
   */
  public final Number getData() {
    return this.m_data;
  }
}
