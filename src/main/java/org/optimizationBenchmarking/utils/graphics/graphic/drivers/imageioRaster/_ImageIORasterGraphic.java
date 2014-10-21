package org.optimizationBenchmarking.utils.graphics.graphic.drivers.imageioRaster;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.graphic.GraphicProxy;

/**
 * An internal class for Java raster graphics and uses
 * {@link javax.imageio ImageIO}.
 */
class _ImageIORasterGraphic extends GraphicProxy<Graphics2D> {

  /** the width */
  final int m_w;

  /** the height */
  final int m_h;

  /** the dpi along the x-axis */
  final double m_xDPI;

  /** the dpi along the y-axis */
  final double m_yDPI;

  /** the type */
  private final String m_type;

  /** the image */
  private final BufferedImage m_img;

  /** the output stream */
  private final OutputStream m_os;

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
  _ImageIORasterGraphic(final Path path, final OutputStream os,
      final IObjectListener listener, final BufferedImage img,
      final Graphics2D g, final int w, final int h, final double xDPI,
      final double yDPI, final String type) {
    super(g, path, listener);

    this.m_w = w;
    this.m_h = h;
    this.m_xDPI = xDPI;
    this.m_yDPI = yDPI;
    this.m_img = img;
    this.m_type = type;
    this.m_os = os;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isVectorGraphic() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final Rectangle getBounds() {
    return new Rectangle(0, 0, this.m_w, this.m_h);
  }

  /**
   * set the DPI
   * 
   * @param metaData
   *          the metadata
   * @throws IIOInvalidTreeException
   *           if something goes wrong
   */
  @SuppressWarnings("unused")
  void _setDPI(final IIOMetadata metaData) throws IIOInvalidTreeException {
    //
  }

  /**
   * Set the output quality of the image
   * 
   * @param params
   *          the parameters
   */
  void _setQuality(final ImageWriteParam params) {
    //
  }

  /**
   * should we try to store meta-data?
   * 
   * @return {@code true} if meta-data storing should be attempted
   */
  boolean _tryMetaData() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  protected final void onClose() {
    final ImageTypeSpecifier typeSpecifier;
    final boolean shouldMeta;
    Iterator<ImageWriter> it;
    ImageWriter useWriter, writer;
    ImageWriteParam useParam, param;
    boolean canMeta;
    IIOMetadata useMetaData, metaData;

    shouldMeta = this._tryMetaData();
    try {
      try {
        this.m_out.dispose();

        canMeta = false;
        useWriter = null;
        useMetaData = null;
        useParam = null;
        typeSpecifier = ImageTypeSpecifier
            .createFromBufferedImageType(this.m_img.getType());

        it = ImageIO.getImageWritersByFormatName(this.m_type);

        finder: while (it.hasNext()) {
          writer = it.next();
          if (writer.getClass().getCanonicalName().contains("freehep")) { //$NON-NLS-1$
            continue;
          }
          param = writer.getDefaultWriteParam();

          metaData = writer.getDefaultImageMetadata(typeSpecifier, param);
          if (shouldMeta) {
            if ((!(metaData.isReadOnly()))
                && metaData.isStandardMetadataFormatSupported()) {
              canMeta = true;
              useWriter = writer;
              useParam = param;
              useMetaData = metaData;
              break finder;
            }
          }
          if (useWriter == null) {
            useWriter = writer;
            useParam = param;
            if (!shouldMeta) {
              break;
            }
          }
        }

        try (final OutputStream os = this.m_os) {
          try (final ImageOutputStream ios = ImageIO
              .createImageOutputStream(os)) {

            useWriter.setOutput(ios);

            if (shouldMeta && (useParam != null)) {
              this._setQuality(useParam);
            }

            if (canMeta) {
              this._setDPI(useMetaData);
              useWriter.write(null, new IIOImage(this.m_img, null,
                  useMetaData), useParam);
            } else {
              if (useParam != null) {
                useWriter.write(null,
                    new IIOImage(this.m_img, null, null), useParam);
              } else {
                useWriter.write(this.m_img);
              }
            }
          }
        }

      } finally {
        super.onClose();
      }
    } catch (final Throwable tt) {
      ErrorUtils.throwAsRuntimeException(tt);
    }
  }
}
