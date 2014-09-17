package org.optimizationBenchmarking.utils.graphics.graphic.drivers.imageioRaster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.IObjectListener;

/**
 * An internal class for <a
 * href="http://en.wikipedia.org/wiki/Graphics_Interchange_Format">GIF</a>
 * Java raster graphics.
 */
final class _ImageIOGIFGraphic extends _ImageIORasterGraphic {

  /**
   * instantiate
   * 
   * @param path
   *          the path
   * @param os
   *          the output stream
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
   */
  _ImageIOGIFGraphic(final Path path, final OutputStream os,
      final IObjectListener listener, final BufferedImage img,
      final Graphics2D g, final int w, final int h, final double xDPI,
      final double yDPI, final String type) {
    super(path, os, listener, img, g, w, h, xDPI, yDPI, type);
  }

  // /** {@inheritDoc} */
  // @Override
  // final void _setDPI(final IIOMetadata metaData)
  // throws IIOInvalidTreeException {
  // final IIOMetadataNode h, v, dim;
  //
  //    h = new IIOMetadataNode("HorizontalPixelSize"); //$NON-NLS-1$
  //    h.setAttribute("value", //$NON-NLS-1$
  // Double.toString(ELength.MM.convertTo(this.m_xDPI, ELength.INCH)));
  //
  //    v = new IIOMetadataNode("VerticalPixelSize"); //$NON-NLS-1$
  //    v.setAttribute("value", //$NON-NLS-1$
  // Double.toString(ELength.MM.convertTo(this.m_xDPI, ELength.INCH)));
  //
  //    dim = new IIOMetadataNode("Dimension"); //$NON-NLS-1$
  // dim.appendChild(h);
  // dim.appendChild(v);
  //
  //    IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0"); //$NON-NLS-1$
  // root.appendChild(dim);
  //
  //    metaData.mergeTree("javax_imageio_1.0", root); //$NON-NLS-1$
  // }

  /** {@inheritDoc} */
  @Override
  final boolean _tryMetaData() {
    return false;
  }
}
