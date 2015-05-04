package org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.imageio.spi.ImageWriterSpi;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A driver which creates Java's raster <a
 * href="http://en.wikipedia.org/wiki/JPEG">JPEG</a> graphics.
 */
public final class ImageIOJPEGGraphicDriver extends
_ImageIORasterGraphicDriver {

  /** Create a new JPEG driver for based on {@link javax.imageio ImageIO}. */
  ImageIOJPEGGraphicDriver() {
    super(EGraphicFormat.JPEG);
  }

  /** {@inheritDoc} */
  @Override
  final _ImageIORasterGraphic _create(final Path path,
      final Logger logger, final IFileProducerListener listener,
      final BufferedImage img, final Graphics2D g, final int w,
      final int h, final double xDPI, final double yDPI,
      final double quality) {
    return new _ImageIOJPEGGraphic(path, logger, listener, img, g, w, h,
        xDPI, yDPI, ((float) quality));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (ImageIOJPEGGraphicDriver._ImageIOJPEGSPILoader.SPI != null);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "javax.imageio-based JPEG Driver"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final EColorModel _processColorModel(final EColorModel model) {
    switch (model) {
      case GRAY_16_BIT: {
        return EColorModel.GRAY_8_BIT;
      }
      case ARGB_32_BIT: {
        return EColorModel.RGB_24_BIT;
      }
      default: {
        return model;
      }
    }
  }

  /**
   * Get the instance of the JPEG driver based on Java's imaging API
   *
   * @return the instance of the JPEG driver based on Java's imaging API
   */
  public static final ImageIOJPEGGraphicDriver getInstance() {
    return __ImageIOJPEGGraphicDriverLoader.INSTANCE;
  }

  /** the loader for the JPEG SPI */
  static final class _ImageIOJPEGSPILoader {
    /** the image writer spi */
    static final ImageWriterSpi SPI = //
        _ImageIORasterGraphicDriver.getSPI(EGraphicFormat.JPEG);
  }

  /** the default loader */
  private static final class __ImageIOJPEGGraphicDriverLoader {

    /** the default graphic JPEG driver instance */
    static final ImageIOJPEGGraphicDriver INSTANCE = //
        new ImageIOJPEGGraphicDriver();
  }
}
