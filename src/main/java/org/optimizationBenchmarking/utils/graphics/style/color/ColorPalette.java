package org.optimizationBenchmarking.utils.graphics.style.color;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.optimizationBenchmarking.utils.graphics.graphic.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.Palette;

/**
 * A color palette.
 */
public class ColorPalette extends Palette<ColorStyle> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the black color */
  static final ColorStyle BLACK = new ColorStyle(
      "black", Color.BLACK.getRGB()); //$NON-NLS-1$

  /** the white color */
  static final ColorStyle WHITE = new ColorStyle(
      "white", Color.WHITE.getRGB()); //$NON-NLS-1$

  /**
   * create the palette
   * 
   * @param data
   *          the palette data
   */
  ColorPalette(final ColorStyle[] data) {
    super(data);
  }

  /**
   * Get the black color
   * 
   * @return the black color
   */
  public final ColorStyle getBlack() {
    return ColorPalette.BLACK;
  }

  /**
   * Get the white color
   * 
   * @return the white color
   */
  public final ColorStyle getWhite() {
    return ColorPalette.WHITE;
  }

  /** {@inheritDoc} */
  @Override
  public final void initialize(final Graphics2D graphics) {
    final Rectangle2D r;
    final Color w, b;

    w = this.getWhite();

    graphics.setBackground(w);
    graphics.setColor(w);
    graphics.setPaint(w);

    if (graphics instanceof Graphic) {
      r = ((Graphic) graphics).getBounds();
    } else {
      r = new Rectangle2D.Double(-Integer.MAX_VALUE, -Integer.MAX_VALUE,
          (2d * Integer.MAX_VALUE), (2d * Integer.MAX_VALUE));
    }
    graphics.fill(r);

    b = this.getBlack();
    graphics.setColor(b);
    graphics.setPaint(b);
  }
}
