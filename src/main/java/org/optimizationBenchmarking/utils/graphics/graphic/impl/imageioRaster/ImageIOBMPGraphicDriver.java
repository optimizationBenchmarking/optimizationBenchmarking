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
 * href="http://en.wikipedia.org/wiki/BMP_file_format">BMP</a> graphics
 * Java's raster graphics.
 */
public final class ImageIOBMPGraphicDriver extends
_ImageIORasterGraphicDriver {

  /**
   * Get the default instance of the BMP driver based on Java's imaging API
   *
   * @return the default instance of the BMP driver based on Java's imaging
   *         API
   */
  public static final ImageIOBMPGraphicDriver getInstance() {
    return __ImageIOBMPGraphicDriverLoader.INSTANCE;
  }

  /**
   * Create a new BMP driver for based on {@link javax.imageio ImageIO}.
   */
  ImageIOBMPGraphicDriver() {
    super(EGraphicFormat.BMP);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (ImageIOBMPGraphicDriver._ImageIOBMPSPILoader.SPI != null);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "javax.imageio-based BMP Driver"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final _ImageIORasterGraphic _create(final Path path,
      final Logger logger, final IFileProducerListener listener,
      final BufferedImage img, final Graphics2D g, final int w,
      final int h, final double xDPI, final double yDPI,
      final double quality) {
    return new _ImageIOBMPGraphic(path, logger, listener, img, g, w, h,
        xDPI, yDPI);
  }

  /** the loader for the BMP SPI */
  static final class _ImageIOBMPSPILoader {
    /** the image writer spi */
    static final ImageWriterSpi SPI = //
        _ImageIORasterGraphicDriver.getSPI(EGraphicFormat.BMP);
  }

  /** the default loader */
  private static final class __ImageIOBMPGraphicDriverLoader {
    /**
     * the default graphic <a
     * href="http://en.wikipedia.org/wiki/BMP_file_format" >BMP</a> driver
     * instance
     */
    static final ImageIOBMPGraphicDriver INSTANCE = new ImageIOBMPGraphicDriver();
  }
}
