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
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A driver which creates Java's raster graphics to use
 * {@link javax.imageio ImageIO}.
 */
abstract class _ImageIORasterGraphicDriver extends AbstractGraphicDriver {

  /** the dots per inch */
  final int m_dpi;

  /** the color model */
  final EColorModel m_colors;

  /**
   * the hidden constructor
   * 
   * @param type
   *          the graphic type
   * @param dotsPerInch
   *          the dots per inch
   * @param colors
   *          the colors
   */
  _ImageIORasterGraphicDriver(final EGraphicFormat type,
      final EColorModel colors, final int dotsPerInch) {
    super(type);

    if ((dotsPerInch <= 1) || (dotsPerInch >= 1000000)) {
      throw new IllegalArgumentException("Illegal DPI: " + dotsPerInch); //$NON-NLS-1$
    }

    if (colors == null) {
      throw new IllegalArgumentException("Color model must not be null."); //$NON-NLS-1$
    }

    this.m_dpi = dotsPerInch;
    this.m_colors = colors;
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

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_dpi),
        HashUtils.hashCode(this.m_colors));
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(this.m_colors.toString());
    textOut.append(" Graphics @ "); //$NON-NLS-1$
    textOut.append(this.m_dpi);
    textOut.append(" DPI"); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final Graphic createGraphic(final Logger logger,
      final IFileProducerListener listener, final Path basePath,
      final String mainDocumentNameSuggestion, final PhysicalDimension size) {
    final BufferedImage img;
    final Graphics2D g;
    final double w, h, wIn, hIn, hDPI, wDPI;
    final int wPt, hPt, wPx, hPx;
    final ELength sizeUnit;

    w = size.getWidth();
    h = size.getHeight();
    sizeUnit = size.getUnit();
    wPt = Math.max(1, ((int) ((0.5d + //
        sizeUnit.convertTo(w, ELength.PT)))));
    hPt = Math.max(1, ((int) ((0.5d + //
        sizeUnit.convertTo(h, ELength.PT)))));

    wIn = ELength.PT.convertTo(((double) wPt), ELength.INCH);
    hIn = ELength.PT.convertTo(((double) hPt), ELength.INCH);
    wPx = Math.max(1, ((int) ((Math.ceil(wIn * this.m_dpi)) + 0.5d)));
    hPx = Math.max(1, ((int) ((Math.ceil(hIn * this.m_dpi)) + 0.5d)));

    wDPI = (wPx / wIn);
    hDPI = (hPx / hIn);

    img = new BufferedImage(wPx, hPx, this.m_colors.getBufferedImageType());
    g = ((Graphics2D) (img.getGraphics()));
    GraphicUtils.setDefaultRenderingHints(g);

    if ((wPx != wPt) || (hPx != hPt)) {
      g.scale((((double) wPx) / wPt), (((double) hPx) / hPt));
    }

    return this._create(
        this.makePath(basePath, mainDocumentNameSuggestion), logger,
        listener, img, g, wPt, hPt, wDPI, hDPI);
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
   * @return the graphic
   */
  abstract _ImageIORasterGraphic _create(final Path path,
      final Logger logger, final IFileProducerListener listener,
      final BufferedImage img, final Graphics2D g, final int w,
      final int h, final double xDPI, final double yDPI);

  /** {@inheritDoc} */
  @Override
  public final ColorPalette getColorPalette() {
    return this.m_colors.getDefaultPalette();
  }
}
