package org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.imageio.spi.ImageWriterSpi;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A driver which creates Java's raster <a
 * href="http://en.wikipedia.org/wiki/JPEG">JPEG</a> graphics.
 */
public final class ImageIOJPEGGraphicDriver extends
    _ImageIORasterGraphicDriver {

  /** the default color model */
  static final EColorModel DEFAULT_COLOR_MODEL = ImageIOJPEGGraphicDriver
      .__fixColors(EGraphicFormat.DEFAULT_COLOR_MODEL);

  /** the default quality */
  static final float DEFAULT_QUALITY = ((float) (EGraphicFormat.DEFAULT_QUALITY));

  /** the quality */
  private final float m_quality;

  /**
   * Create a new JPEG driver for based on {@link javax.imageio ImageIO}.
   * 
   * @param dotsPerInch
   *          the dots per inch
   * @param colors
   *          the colors
   * @param quality
   *          the quality
   */
  ImageIOJPEGGraphicDriver(final EColorModel colors,
      final int dotsPerInch, final float quality) {
    super(EGraphicFormat.JPEG, colors, dotsPerInch);
    this.m_quality = quality;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final ImageIOJPEGGraphicDriver d;
    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o instanceof ImageIOJPEGGraphicDriver) {
      d = ((ImageIOJPEGGraphicDriver) o);
      return ((this.m_dpi == d.m_dpi) && //
          (EComparison.equals(this.m_colors, d.m_colors)) && //
      (EComparison.compareFloats(this.m_quality, d.m_quality) == 0));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_dpi),//
            HashUtils.hashCode(this.m_colors)),//
        HashUtils.hashCode(this.m_quality));
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append("javax.imageio-based JPEG Driver for "); //$NON-NLS-1$
    super.toText(textOut);
    textOut.append(" and Encoding Quality "); //$NON-NLS-1$
    textOut.append(this.m_quality);
  }

  /** {@inheritDoc} */
  @Override
  final _ImageIORasterGraphic _create(final Path path,
      final Logger logger, final IFileProducerListener listener,
      final BufferedImage img, final Graphics2D g, final int w,
      final int h, final double xDPI, final double yDPI) {
    return new _ImageIOJPEGGraphic(path, logger, listener, img, g, w, h,
        xDPI, yDPI, this.m_quality);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (ImageIOJPEGGraphicDriver._ImageIOJPEGSPILoader.SPI != null);
  }

  /**
   * fix the color model
   * 
   * @param model
   *          the color model
   * @return the fixed version
   */
  private static final EColorModel __fixColors(final EColorModel model) {
    switch (model) {
      case GRAY_16_BIT: {
        return EColorModel.GRAY_8_BIT;
      }
      case RBGA_32_BIT: {
        return EColorModel.RBG_24_BIT;
      }
      default: {
        return model;
      }
    }
  }

  /**
   * Get the default instance of the JPEG driver based on Java's imaging
   * API
   * 
   * @return the default instance of the JPEG driver based on Java's
   *         imaging API
   */
  public static final ImageIOJPEGGraphicDriver getDefaultInstance() {
    return __ImageIOJPEGGraphicDriverLoader.DEFAULT_INSTANCE;
  }

  /**
   * Get an instance of the JPEG driver for based on {@link javax.imageio
   * ImageIO}.
   * 
   * @param dotsPerInch
   *          the dots per inch
   * @param colors
   *          the colors
   * @param quality
   *          the quality
   * @return the {@link ImageIOJPEGGraphicDriver} instance
   */
  public static final ImageIOJPEGGraphicDriver getInstance(
      final EColorModel colors, final int dotsPerInch, final float quality) {
    final EColorModel c;

    if ((quality < 0f) || (quality > 1f) || (quality != quality)) {
      throw new IllegalArgumentException("Illegal quality: " + quality);//$NON-NLS-1$
    }

    c = ImageIOJPEGGraphicDriver.__fixColors(colors);

    if ((c == ImageIOJPEGGraphicDriver.DEFAULT_COLOR_MODEL)
        && (dotsPerInch == EGraphicFormat.DEFAULT_DPI)
        && (quality == ImageIOJPEGGraphicDriver.DEFAULT_QUALITY)) {
      return ImageIOJPEGGraphicDriver.getDefaultInstance();
    }
    return new ImageIOJPEGGraphicDriver(c, dotsPerInch, quality);
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
    static final ImageIOJPEGGraphicDriver DEFAULT_INSTANCE = //
    new ImageIOJPEGGraphicDriver(
        ImageIOJPEGGraphicDriver.DEFAULT_COLOR_MODEL,
        EGraphicFormat.DEFAULT_DPI,
        ImageIOJPEGGraphicDriver.DEFAULT_QUALITY);
  }
}
