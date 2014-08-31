package org.optimizationBenchmarking.utils.graphics.drivers.imageioRaster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;

import org.optimizationBenchmarking.utils.graphics.GraphicID;
import org.optimizationBenchmarking.utils.graphics.IGraphicListener;
import org.w3c.dom.Element;

/**
 * An internal class for <a
 * href="http://en.wikipedia.org/wiki/JPEG">JPEG</a> Java raster graphics.
 */
final class _ImageIOJPEGGraphic extends _ImageIORasterGraphic {

  /** the quality */
  private final float m_quality;

  /**
   * instantiate
   * 
   * @param id
   *          the graphic id identifying this graphic and the path under
   *          which the contents of the graphic are stored
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
   * @param quality
   *          the image quality
   */
  _ImageIOJPEGGraphic(final GraphicID id, final IGraphicListener listener,
      final BufferedImage img, final Graphics2D g, final int w,
      final int h, final double xDPI, final double yDPI,
      final float quality, final String type) {
    super(id, listener, img, g, w, h, xDPI, yDPI, type);

    this.m_quality = quality;
  }

  /** {@inheritDoc} */
  @Override
  final void _setDPI(final IIOMetadata metaData)
      throws IIOInvalidTreeException {
    final Element tree, jfif;
    tree = ((Element) (metaData.getAsTree("javax_imageio_jpeg_image_1.0"))); //$NON-NLS-1$
    jfif = ((Element) (tree.getElementsByTagName("app0JFIF").item(0)));//$NON-NLS-1$
    jfif.setAttribute("Xdensity",//$NON-NLS-1$ 
        Long.toString(Math.max(1L, Math.round(this.m_xDPI))));
    jfif.setAttribute("Ydensity",//$NON-NLS-1$ 
        Long.toString(Math.max(1L, Math.round(this.m_yDPI))));
    jfif.setAttribute("resUnits", "1"); //$NON-NLS-1$//$NON-NLS-2$
    metaData.setFromTree("javax_imageio_jpeg_image_1.0", tree);//$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  void _setQuality(final ImageWriteParam params) {
    params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    params.setCompressionQuality(this.m_quality);
  }

}
