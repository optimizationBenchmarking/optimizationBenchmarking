package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

/** The base class for data elements */
public class CompiledDataElement extends CompiledTitledElement {

  /** the id of this data series */
  private final int m_id;

  /** the color of this element */
  private final Color m_color;

  /** the stroke of this element */
  private final Stroke m_stroke;

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
   */
  protected CompiledDataElement(final int id, final String title,
      final Font titleFont, final Color color, final Stroke stroke) {
    super(title, titleFont);

    if (stroke == null) {
      throw new IllegalArgumentException("Stroke must not be null."); //$NON-NLS-1$
    }
    if (color == null) {
      throw new IllegalArgumentException("Color must not be null."); //$NON-NLS-1$
    }
    this.m_id = id;
    this.m_color = color;
    this.m_stroke = stroke;
  }

  /**
   * Get the color associated with this data series
   *
   * @return the color associated with this data element
   */
  public final Color getColor() {
    return this.m_color;
  }

  /**
   * Get the stroke associated with this data element
   *
   * @return the stroke associated with this data element
   */
  public final Stroke getStroke() {
    return this.m_stroke;
  }

  /**
   * Get the id of this data element
   *
   * @return the unique identifier of this data element
   */
  public final int getID() {
    return this.m_id;
  }
}
