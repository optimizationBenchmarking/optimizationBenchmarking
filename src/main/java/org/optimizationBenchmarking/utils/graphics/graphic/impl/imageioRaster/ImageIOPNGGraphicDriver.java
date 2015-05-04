package org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.imageio.spi.ImageWriterSpi;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A driver which creates <a
 * href="http://en.wikipedia.org/wiki/Portable_Network_Graphics">PNG</a>
 * graphics Java's raster graphics.
 */
public final class ImageIOPNGGraphicDriver extends
    _ImageIORasterGraphicDriver {

  /**
   * Get the instance of the PNG driver based on Java's imaging API
   *
   * @return the instance of the PNG driver based on Java's imaging API
   */
  public static final ImageIOPNGGraphicDriver getInstance() {
    return __ImageIOPNGGraphicDriverLoader.INSTANCE;
  }

  /**
   * Create a new png driver for based on {@link javax.imageio ImageIO}.
   */
  ImageIOPNGGraphicDriver() {
    super(EGraphicFormat.PNG);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (ImageIOPNGGraphicDriver._ImageIOPNGSPILoader.SPI != null);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "javax.imageio-based PNG Driver"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final _ImageIORasterGraphic _create(final Path path,
      final Logger logger, final IFileProducerListener listener,
      final BufferedImage img, final Graphics2D g, final int w,
      final int h, final double xDPI, final double yDPI,
      final double quality) {
    return new _ImageIOPNGGraphic(path, logger, listener, img, g, w, h,
        xDPI, yDPI);
  }

  /** the loader for the png SPI */
  static final class _ImageIOPNGSPILoader {
    /** the image writer spi */
    static final ImageWriterSpi SPI = //
    _ImageIORasterGraphicDriver.getSPI(EGraphicFormat.PNG);
  }

  /** the default loader */
  private static final class __ImageIOPNGGraphicDriverLoader {
    /**
     * the default graphic <a
     * href="http://en.wikipedia.org/wiki/Portable_Network_Graphics"
     * >PNG</a> driver instance
     */
    static final ImageIOPNGGraphicDriver INSTANCE = //
    new ImageIOPNGGraphicDriver();
  }
}
