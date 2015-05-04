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
 * href="http://en.wikipedia.org/wiki/Graphics_Interchange_Format">GIF</a>
 * graphics.
 */
public final class ImageIOGIFGraphicDriver extends
    _ImageIORasterGraphicDriver {

  /** Create a new GIF driver for based on {@link javax.imageio ImageIO}. */
  ImageIOGIFGraphicDriver() {
    super(EGraphicFormat.GIF);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (ImageIOGIFGraphicDriver._ImageIOGIFSPILoader.SPI != null);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "javax.imageio-based GIF Driver"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final _ImageIORasterGraphic _create(final Path path,
      final Logger logger, final IFileProducerListener listener,
      final BufferedImage img, final Graphics2D g, final int w,
      final int h, final double xDPI, final double yDPI,
      final double quality) {
    return new _ImageIOGIFGraphic(path, logger, listener, img, g, w, h,
        xDPI, yDPI);
  }

  /** {@inheritDoc} */
  @Override
  final EColorModel _processColorModel(final EColorModel model) {
    switch (model) {
      case GRAY_16_BIT: {
        return EColorModel.GRAY_8_BIT;
      }
      default: {
        return model;
      }
    }
  }

  /**
   * Get the default instance of the GIF driver based on Java's imaging API
   *
   * @return the default instance of the GIF driver based on Java's imaging
   *         API
   */
  public static final ImageIOGIFGraphicDriver getInstance() {
    return __ImageIOGIFGraphicDriverLoader.INSTANCE;
  }

  /** the loader for the GIF SPI */
  static final class _ImageIOGIFSPILoader {
    /** the image writer spi */
    static final ImageWriterSpi SPI = //
    _ImageIORasterGraphicDriver.getSPI(EGraphicFormat.GIF);
  }

  /** the default loader */
  private static final class __ImageIOGIFGraphicDriverLoader {
    /**
     * the default graphic <a
     * href="http://en.wikipedia.org/wiki/Graphics_Interchange_Format"
     * >GIF</a> driver instance
     */
    static final ImageIOGIFGraphicDriver INSTANCE = new ImageIOGIFGraphicDriver();
  }
}
