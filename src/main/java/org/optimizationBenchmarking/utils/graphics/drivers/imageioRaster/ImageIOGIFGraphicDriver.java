package org.optimizationBenchmarking.utils.graphics.drivers.imageioRaster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.optimizationBenchmarking.utils.graphics.EColorModel;
import org.optimizationBenchmarking.utils.graphics.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.GraphicID;
import org.optimizationBenchmarking.utils.graphics.IGraphicListener;

/**
 * A driver which creates Java's raster <a
 * href="http://en.wikipedia.org/wiki/Graphics_Interchange_Format">GIF</a>
 * graphics.
 */
public final class ImageIOGIFGraphicDriver extends
    _ImageIORasterGraphicDriver {
  /** the default graphic GIF driver instance */
  public static final ImageIOGIFGraphicDriver DEFAULT_INSTANCE = //
  new ImageIOGIFGraphicDriver(EGraphicFormat.DEFAULT_COLOR_MODEL,
      EGraphicFormat.DEFAULT_DPI);

  /**
   * Create a new png driver for based on {@link javax.imageio ImageIO}.
   * 
   * @param dotsPerInch
   *          the dots per inch
   * @param colors
   *          the colors
   */
  public ImageIOGIFGraphicDriver(final EColorModel colors,
      final int dotsPerInch) {
    super("gif",//$NON-NLS-1$
        ImageIOGIFGraphicDriver.__fixColors(colors), dotsPerInch);
  }

  /**
   * fix the color model
   * 
   * @param model
   *          the color model
   * @return the fixed version
   */
  private static final EColorModel __fixColors(final EColorModel model) {
    switch (model) {
      case GRAY_16_BIT: {
        return EColorModel.GRAY_8_BIT;
      }
      default: {
        return model;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  final _ImageIORasterGraphic _create(final GraphicID id,
      final IGraphicListener listener, final BufferedImage img,
      final Graphics2D g, final int w, final int h, final double xDPI,
      final double yDPI, final String type) {
    return new _ImageIOGIFGraphic(id, listener, img, g, w, h, xDPI, yDPI,
        type);
  }
}
