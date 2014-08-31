package org.optimizationBenchmarking.utils.graphics.drivers.imageioRaster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.optimizationBenchmarking.utils.graphics.EColorModel;
import org.optimizationBenchmarking.utils.graphics.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.GraphicID;
import org.optimizationBenchmarking.utils.graphics.IGraphicListener;

/**
 * A driver which creates Java's raster <a
 * href="http://en.wikipedia.org/wiki/JPEG">JPEG</a> graphics.
 */
public final class ImageIOJPEGGraphicDriver extends
    _ImageIORasterGraphicDriver {

  /** the default graphic JPEG driver instance */
  public static final ImageIOJPEGGraphicDriver DEFAULT_INSTANCE = //
  new ImageIOJPEGGraphicDriver(EGraphicFormat.DEFAULT_COLOR_MODEL,
      EGraphicFormat.DEFAULT_DPI,
      ((float) (EGraphicFormat.DEFAULT_QUALITY)));

  /** the quality */
  private final float m_quality;

  /**
   * Create a new png driver for based on {@link javax.imageio ImageIO}.
   * 
   * @param dotsPerInch
   *          the dots per inch
   * @param colors
   *          the colors
   * @param quality
   *          the quality
   */
  public ImageIOJPEGGraphicDriver(final EColorModel colors,
      final int dotsPerInch, final float quality) {
    super("jpg", //$NON-NLS-1$
        ImageIOJPEGGraphicDriver.__fixColors(colors), dotsPerInch);
    if ((quality < 0f) || (quality > 1f) || (quality != quality)) {
      throw new IllegalArgumentException("Illegal quality: " + quality);//$NON-NLS-1$
    }

    this.m_quality = quality;
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
      case RBGA_32_BIT: {
        return EColorModel.RBG_24_BIT;
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
    return new _ImageIOJPEGGraphic(id, listener, img, g, w, h, xDPI, yDPI,
        this.m_quality, type);
  }
}
