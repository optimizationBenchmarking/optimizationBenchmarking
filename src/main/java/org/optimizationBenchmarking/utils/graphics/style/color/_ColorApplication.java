package org.optimizationBenchmarking.utils.graphics.style.color;

import java.awt.Color;
import java.awt.Graphics2D;

import org.optimizationBenchmarking.utils.graphics.style.StyleApplication;

/** the color application */
final class _ColorApplication extends StyleApplication {

  /** the color */
  private final Color m_c;

  /**
   * the style
   *
   * @param g
   *          the graphic
   * @param c
   *          the color
   */
  _ColorApplication(final Graphics2D g, final Color c) {
    super(g);
    this.m_c = g.getColor();
    g.setColor(c);
  }

  /** {@inheritDoc} */
  @Override
  protected final void cleanUp(final Graphics2D g) {
    g.setColor(this.m_c);
  }
}
