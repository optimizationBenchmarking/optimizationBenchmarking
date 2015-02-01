package org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageWriterSpi;

import org.optimizationBenchmarking.utils.graphics.GraphicUtils;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A driver which creates Java's raster graphics to use
 * {@link javax.imageio ImageIO}.
 */
abstract class _ImageIORasterGraphicDriver extends AbstractGraphicDriver {

  /**
   * the hidden constructor
   * 
   * @param type
   *          the graphic type
   */
  _ImageIORasterGraphicDriver(final EGraphicFormat type) {
    super(type);
  }

  /**
   * create the writer spi
   * 
   * @param type
   *          the graphic type
   * @return an image writer service provider for a given graphic type
   */
  static final ImageWriterSpi getSPI(final EGraphicFormat type) {
    try {
      Iterator<ImageWriterSpi> spiIt;
      ImageWriterSpi spi, use, emergency;
      IIORegistry reg;
      String[] lst;
      String cmp;
      boolean canBreak;

      use = emergency = null;
      try {

        // try to find a writer provider supporting our format
        reg = IIORegistry.getDefaultInstance();
        if (reg != null) {
          spiIt = reg.getServiceProviders(ImageWriterSpi.class, false);
          if (spiIt != null) {
            finderLoop: while (spiIt.hasNext()) {
              spi = spiIt.next();
              if (spi != null) {

                if (spi.getClass().getCanonicalName().contains("freehep")) { //$NON-NLS-1$
                  if ((emergency == null)
                      || (spi.isStandardImageMetadataFormatSupported() && (!(emergency
                          .isStandardImageMetadataFormatSupported())))) {
                    emergency = spi;
                  }
                  continue finderLoop; // no freeHEP drivers
                }

                checkFormat: {
                  // let's see if a mime type can match
                  cmp = type.getMIMEType();
                  if (cmp != null) {
                    lst = spi.getMIMETypes();
                    if (lst == null) {
                      continue finderLoop;
                    }
                    for (final String ss : lst) {
                      if (cmp.equalsIgnoreCase(ss)) {
                        break checkFormat;
                      }
                    }
                    continue;
                  }

                  // ok, no mime type know, let's compare the file suffix
                  cmp = type.getDefaultSuffix();
                  if (cmp != null) {
                    lst = spi.getFileSuffixes();
                    if (lst == null) {
                      continue finderLoop;
                    }
                    for (final String ss : lst) {
                      if (cmp.equalsIgnoreCase(ss)) {
                        break checkFormat;
                      }
                    }
                    continue finderLoop;
                  }

                  continue finderLoop;
                }

                // the format fits!
                canBreak = spi.isStandardImageMetadataFormatSupported();
                if (canBreak || (use == null)) {
                  use = spi;
                  if (canBreak) {
                    return use;
                  }
                  continue finderLoop;
                }
              }
            }
          }
        }
      } catch (final Throwable t) {
        //
      }

      if (use == null) {
        return emergency;
      }
      return use;
    } catch (final Throwable tt) {
      return null;
    }
  }

  /**
   * pre-process the color
   * 
   * @param model
   *          the model
   * @return the pre-processed model
   */
  EColorModel _processColorModel(final EColorModel model) {
    return model;
  }

  /** {@inheritDoc} */
  @Override
  protected final Graphic createGraphic(final GraphicBuilder builder) {
    final BufferedImage img;
    final Graphics2D g;
    final double w, h, wIn, hIn, hDPI, wDPI;
    final int wPt, hPt, wPx, hPx, dpi;
    final ELength sizeUnit;
    final PhysicalDimension size;

    size = builder.getSize();

    w = size.getWidth();
    h = size.getHeight();
    sizeUnit = size.getUnit();
    wPt = Math.max(1, ((int) ((0.5d + //
        sizeUnit.convertTo(w, ELength.PT)))));
    hPt = Math.max(1, ((int) ((0.5d + //
        sizeUnit.convertTo(h, ELength.PT)))));

    wIn = ELength.PT.convertTo(((double) wPt), ELength.INCH);
    hIn = ELength.PT.convertTo(((double) hPt), ELength.INCH);
    dpi = builder.getDotsPerInch();
    wPx = Math.max(1, ((int) ((Math.ceil(wIn * dpi)) + 0.5d)));
    hPx = Math.max(1, ((int) ((Math.ceil(hIn * dpi)) + 0.5d)));

    wDPI = (wPx / wIn);
    hDPI = (hPx / hIn);

    img = new BufferedImage(wPx, hPx, this._processColorModel(
        builder.getColorModel()).getBufferedImageType());
    g = ((Graphics2D) (img.getGraphics()));
    GraphicUtils.setDefaultRenderingHints(g);

    if ((wPx != wPt) || (hPx != hPt)) {
      g.scale((((double) wPx) / wPt), (((double) hPx) / hPt));
    }

    return this._create(
        this.makePath(builder.getBasePath(),
            builder.getMainDocumentNameSuggestion()), builder.getLogger(),
        builder.getFileProducerListener(), img, g, wPt, hPt, wDPI, hDPI,
        builder.getQuality());
  }

  /**
   * create the graphic
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
   * @param quality
   *          the quality
   * @return the graphic
   */
  abstract _ImageIORasterGraphic _create(final Path path,
      final Logger logger, final IFileProducerListener listener,
      final BufferedImage img, final Graphics2D g, final int w,
      final int h, final double xDPI, final double yDPI,
      final double quality);
}
