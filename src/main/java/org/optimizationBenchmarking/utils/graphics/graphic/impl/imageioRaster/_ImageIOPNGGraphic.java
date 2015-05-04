package org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.spi.ImageWriterSpi;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * An internal class for <a
 * href="http://en.wikipedia.org/wiki/Portable_Network_Graphics">PNG</a>
 * Java raster graphics.
 */
final class _ImageIOPNGGraphic extends _ImageIORasterGraphic {

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
  _ImageIOPNGGraphic(final Path path, final Logger logger,
      final IFileProducerListener listener, final BufferedImage img,
      final Graphics2D g, final int w, final int h, final double xDPI,
      final double yDPI) {
    super(path, logger, listener, img, g, w, h, xDPI, yDPI);
  }

  /** {@inheritDoc} */
  @Override
  final boolean _canSetupImageMetadata() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  final void _setupImageMetadata(final IIOMetadata metaData)
      throws IIOInvalidTreeException {
    final IIOMetadataNode h, v, dim;

    h = new IIOMetadataNode("HorizontalPixelSize"); //$NON-NLS-1$
    h.setAttribute("value", //$NON-NLS-1$
        Double.toString(ELength.MM.convertTo(this.m_xDPI, ELength.INCH)));

    v = new IIOMetadataNode("VerticalPixelSize"); //$NON-NLS-1$
    v.setAttribute("value", //$NON-NLS-1$
        Double.toString(ELength.MM.convertTo(this.m_xDPI, ELength.INCH)));

    dim = new IIOMetadataNode("Dimension"); //$NON-NLS-1$
    dim.appendChild(h);
    dim.appendChild(v);

    final IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0"); //$NON-NLS-1$
    root.appendChild(dim);

    metaData.mergeTree("javax_imageio_1.0", root); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final ImageWriterSpi _getImageWriterSPI() {
    return ImageIOPNGGraphicDriver._ImageIOPNGSPILoader.SPI;
  }

  /** {@inheritDoc} */
  @Override
  public final EGraphicFormat getGraphicFormat() {
    return EGraphicFormat.PNG;
  }
}
