package org.optimizationBenchmarking.utils.graphics.drivers.imageioRaster;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;

import org.optimizationBenchmarking.utils.graphics.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.EColorModel;
import org.optimizationBenchmarking.utils.graphics.Graphic;
import org.optimizationBenchmarking.utils.graphics.GraphicID;
import org.optimizationBenchmarking.utils.graphics.IGraphicListener;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * A driver which creates Java's raster graphics to use
 * {@link javax.imageio ImageIO}.
 */
abstract class _ImageIORasterGraphicDriver extends AbstractGraphicDriver {

  /** the dots per inch */
  private final int m_dpi;

  /** the type */
  private final String m_type;

  /** the color model */
  private final int m_colors;

  /**
   * the hidden constructor
   * 
   * @param type
   *          the graphic type
   * @param dotsPerInch
   *          the dots per inch
   * @param colors
   *          the colors
   */
  _ImageIORasterGraphicDriver(final String type, final EColorModel colors,
      final int dotsPerInch) {
    super('.' + type);

    if ((dotsPerInch <= 1) || (dotsPerInch >= 1000000)) {
      throw new IllegalArgumentException("Illegal DPI: " + dotsPerInch); //$NON-NLS-1$
    }

    this.m_dpi = dotsPerInch;

    this.m_colors = colors.getBufferedImageType();
    this.m_type = type;
  }

  /** {@inheritDoc} */
  @Override
  protected final Graphic doCreateGraphic(final GraphicID id,
      final Dimension2D size, final ELength sizeUnit,
      final IGraphicListener listener) {
    final BufferedImage img;
    final Graphics2D g;
    final double w, h, wIn, hIn, hDPI, wDPI;
    final int wPt, hPt, wPx, hPx;

    w = size.getWidth();
    h = size.getHeight();
    wPt = Math
        .max(1, ((int) ((0.5d + sizeUnit.convertTo(w, ELength.PT)))));
    hPt = Math
        .max(1, ((int) ((0.5d + sizeUnit.convertTo(h, ELength.PT)))));

    wIn = ELength.PT.convertTo(((double) wPt), ELength.INCH);
    hIn = ELength.PT.convertTo(((double) hPt), ELength.INCH);
    wPx = Math.max(1, ((int) ((Math.ceil(wIn * this.m_dpi)) + 0.5d)));
    hPx = Math.max(1, ((int) ((Math.ceil(hIn * this.m_dpi)) + 0.5d)));

    wDPI = (wPx / wIn);
    hDPI = (hPx / hIn);

    img = new BufferedImage(wPx, hPx, this.m_colors);
    g = ((Graphics2D) (img.getGraphics()));

    if ((wPx != wPt) || (hPx != hPt)) {
      g.scale((((double) wPx) / wPt), (((double) hPx) / hPt));
    }

    return this._create(id, listener, img, g, wPt, hPt, wDPI, hDPI,
        this.m_type);
  }

  /**
   * create the graphic
   * 
   * @param id
   *          the graphic id identifying this graphic and the path under
   *          which the contents of the graphic are stored
   * @param listener
   *          the object to notify when we are closed, or {@code null} if
   *          none needs to be notified
   * @param g
   *          the graphics
   * @param w
   *          the width
   * @param h
   *          the height
   * @param xDPI
   *          the resolution along the x-axis
   * @param yDPI
   *          the resolution along the y-axis
   * @param type
   *          the type
   * @param img
   *          the buffered image
   * @return the graphic
   */
  abstract _ImageIORasterGraphic _create(final GraphicID id,
      final IGraphicListener listener, final BufferedImage img,
      final Graphics2D g, final int w, final int h, final double xDPI,
      final double yDPI, final String type);
}
