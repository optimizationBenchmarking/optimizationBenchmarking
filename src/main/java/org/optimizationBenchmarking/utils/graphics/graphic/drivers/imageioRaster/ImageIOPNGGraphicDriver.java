package org.optimizationBenchmarking.utils.graphics.graphic.drivers.imageioRaster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;

/**
 * A driver which creates <a
 * href="http://en.wikipedia.org/wiki/Portable_Network_Graphics">PNG</a>
 * graphics Java's raster graphics.
 */
public final class ImageIOPNGGraphicDriver extends
    _ImageIORasterGraphicDriver {
  /**
   * the default graphic <a
   * href="http://en.wikipedia.org/wiki/Portable_Network_Graphics">PNG</a>
   * driver instance
   */
  public static final ImageIOPNGGraphicDriver DEFAULT_INSTANCE = //
  new ImageIOPNGGraphicDriver(EGraphicFormat.DEFAULT_COLOR_MODEL,
      EGraphicFormat.DEFAULT_DPI);

  /**
   * Create a new png driver for based on {@link javax.imageio ImageIO}.
   * 
   * @param dotsPerInch
   *          the dots per inch
   * @param colors
   *          the colors
   */
  public ImageIOPNGGraphicDriver(final EColorModel colors,
      final int dotsPerInch) {
    super("png", colors, dotsPerInch); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final _ImageIORasterGraphic _create(final Path path,
      final OutputStream os, final IObjectListener listener,
      final BufferedImage img, final Graphics2D g, final int w,
      final int h, final double xDPI, final double yDPI, final String type) {
    return new _ImageIOPNGGraphic(path, os, listener, img, g, w, h, xDPI,
        yDPI, type);
  }
}
