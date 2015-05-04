package org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;

import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicProxy;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * An internal class for Java raster graphics and uses
 * {@link javax.imageio ImageIO}.
 */
abstract class _ImageIORasterGraphic extends GraphicProxy<Graphics2D> {

  /** the width */
  final int m_w;

  /** the height */
  final int m_h;

  /** the dpi along the x-axis */
  final double m_xDPI;

  /** the dpi along the y-axis */
  final double m_yDPI;

  /** the image */
  private final BufferedImage m_img;

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
  _ImageIORasterGraphic(final Path path, final Logger logger,
      final IFileProducerListener listener, final BufferedImage img,
      final Graphics2D g, final int w, final int h, final double xDPI,
      final double yDPI) {
    super(g, logger, listener, path);

    this.m_w = w;
    this.m_h = h;
    this.m_xDPI = xDPI;
    this.m_yDPI = yDPI;
    this.m_img = img;
  }

  /** {@inheritDoc} */
  @Override
  public final Rectangle getBounds() {
    return new Rectangle(0, 0, this.m_w, this.m_h);
  }

  /**
   * Does this image set up image meta data?
   *
   * @return {@code true} if and only if the image can setup image meta
   *         data
   */
  boolean _canSetupImageMetadata() {
    return false;
  }

  /**
   * Setup the image meta data
   *
   * @param metaData
   *          the meta data
   * @throws IIOInvalidTreeException
   *           if something goes wrong
   */
  void _setupImageMetadata(final IIOMetadata metaData)
      throws IIOInvalidTreeException {
    //
  }

  /**
   * Setup the image writer parameters
   *
   * @param params
   *          the parameters
   */
  void _setupImageWriterParameters(final ImageWriteParam params) {
    //
  }

  /**
   * Does this image set up image writer parameters?
   *
   * @return {@code true} if and only if the image can setup image writer
   *         parameters
   */
  boolean _canSetupImageWriterParameters() {
    return false;
  }

  /**
   * Get the image writer SPI
   *
   * @return the image writer spi
   */
  abstract ImageWriterSpi _getImageWriterSPI();

  /** {@inheritDoc} */
  @Override
  protected final void onClose() {
    final ImageWriterSpi imageWriterSPI;
    final ImageWriter writer;
    final ImageTypeSpecifier typeSpecifier;
    final ImageWriteParam imageWriterParams;
    final boolean canUseParams, canUseMeta;
    final IIOMetadata metaData;

    try {
      try {
        imageWriterSPI = this._getImageWriterSPI();
        if (imageWriterSPI != null) {

          writer = imageWriterSPI.createWriterInstance();
          if (writer != null) {

            canUseParams = this._canSetupImageWriterParameters();
            canUseMeta = this._canSetupImageMetadata();
            if (canUseParams || canUseMeta) {
              imageWriterParams = writer.getDefaultWriteParam();
              if (canUseParams && (imageWriterParams != null)) {
                this._setupImageWriterParameters(imageWriterParams);
              }
            } else {
              imageWriterParams = null;
            }

            if (canUseMeta
                && (imageWriterParams != null)
                && ((typeSpecifier = ImageTypeSpecifier
                    .createFromBufferedImageType(//
                    this.m_img.getType())) != null)) {
              metaData = writer.getDefaultImageMetadata(typeSpecifier,
                  imageWriterParams);
              if ((metaData != null) && //
                  (!(metaData.isReadOnly())) && //
                  (metaData.isStandardMetadataFormatSupported())) {
                this._setupImageMetadata(metaData);
              }
            } else {
              metaData = null;
            }

            try (final OutputStream os = PathUtils
                .openOutputStream(this.m_path)) {
              try (final ImageOutputStream ios = ImageIO
                  .createImageOutputStream(os)) {

                writer.setOutput(ios);

                if (metaData != null) {
                  writer.write(null, new IIOImage(this.m_img, null,
                      metaData), imageWriterParams);
                } else {
                  if (imageWriterParams != null) {
                    writer.write(null,
                        new IIOImage(this.m_img, null, null),
                        imageWriterParams);
                  } else {
                    writer.write(this.m_img);
                  }
                }
              }

            }
          }
        }
      } finally {
        super.onClose();
      }
    } catch (final Throwable tt) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow((//
          "Error while finalizing " + //$NON-NLS-1$
          this.getClass().getSimpleName()), true, tt);
    }
  }
}
