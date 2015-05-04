package org.optimizationBenchmarking.utils.graphics.style.font;

import java.awt.Font;
import java.awt.Graphics2D;

import org.optimizationBenchmarking.utils.graphics.style.StyleApplication;

/** the font application */
final class _FontApplication extends StyleApplication {

  /** the font */
  private final Font m_c;

  /**
   * the style
   *
   * @param g
   *          the graphic
   * @param c
   *          the font
   */
  _FontApplication(final Graphics2D g, final Font c) {
    super(g);
    this.m_c = g.getFont();
    g.setFont(c);
  }

  /** {@inheritDoc} */
  @Override
  protected final void cleanUp(final Graphics2D g) {
    g.setFont(this.m_c);
  }
}
