package org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.imageio.spi.ImageWriterSpi;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * An internal class for <a
 * href="http://en.wikipedia.org/wiki/BMP_file_format">BMP</a> Java raster
 * graphics.
 */
final class _ImageIOBMPGraphic extends _ImageIORasterGraphic {

  /**
   * instantiate
   *
   * @param path
   *          the path
   * @param logger
   *          the logger
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
   * @param img
   *          the buffered image
   */
  _ImageIOBMPGraphic(final Path path, final Logger logger,
      final IFileProducerListener listener, final BufferedImage img,
      final Graphics2D g, final int w, final int h, final double xDPI,
      final double yDPI) {
    super(path, logger, listener, img, g, w, h, xDPI, yDPI);
  }

  /** {@inheritDoc} */
  @Override
  final ImageWriterSpi _getImageWriterSPI() {
    return ImageIOBMPGraphicDriver._ImageIOBMPSPILoader.SPI;
  }

  /** {@inheritDoc} */
  @Override
  public final EGraphicFormat getGraphicFormat() {
    return EGraphicFormat.BMP;
  }
}
