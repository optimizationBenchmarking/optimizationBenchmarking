package org.optimizationBenchmarking.utils.graphics.style.stroke;

import java.awt.Graphics2D;
import java.awt.Stroke;

import org.optimizationBenchmarking.utils.graphics.style.StyleApplication;

/** the stroke application */
final class _StrokeApplication extends StyleApplication {

  /** the stroke */
  private final Stroke m_c;

  /**
   * the style
   *
   * @param g
   *          the graphic
   * @param c
   *          the stroke
   */
  _StrokeApplication(final Graphics2D g, final Stroke c) {
    super(g);
    this.m_c = g.getStroke();
    g.setStroke(c);
  }

  /** {@inheritDoc} */
  @Override
  protected final void cleanUp(final Graphics2D g) {
    g.setStroke(this.m_c);
  }
}
