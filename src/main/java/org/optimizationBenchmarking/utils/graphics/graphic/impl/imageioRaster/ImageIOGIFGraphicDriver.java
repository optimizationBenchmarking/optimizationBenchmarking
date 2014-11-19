package org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.imageio.spi.ImageWriterSpi;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A driver which creates Java's raster <a
 * href="http://en.wikipedia.org/wiki/Graphics_Interchange_Format">GIF</a>
 * graphics.
 */
public final class ImageIOGIFGraphicDriver extends
    _ImageIORasterGraphicDriver {

  /** the default color model */
  static final EColorModel DEFAULT_COLOR_MODEL = ImageIOGIFGraphicDriver
      .__fixColors(EGraphicFormat.DEFAULT_COLOR_MODEL);

  /**
   * Create a new GIF driver for based on {@link javax.imageio ImageIO}.
   * 
   * @param dotsPerInch
   *          the dots per inch
   * @param colors
   *          the colors
   */
  ImageIOGIFGraphicDriver(final EColorModel colors, final int dotsPerInch) {
    super(EGraphicFormat.GIF, colors, dotsPerInch);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (ImageIOGIFGraphicDriver._ImageIOGIFSPILoader.SPI != null);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final ImageIOGIFGraphicDriver d;
    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o instanceof ImageIOGIFGraphicDriver) {
      d = ((ImageIOGIFGraphicDriver) o);
      return ((this.m_dpi == d.m_dpi) && //
      (EComparison.equals(this.m_colors, d.m_colors)));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append("javax.imageio-based GIF Driver for "); //$NON-NLS-1$
    super.toText(textOut);
  }

  /** {@inheritDoc} */
  @Override
  final _ImageIORasterGraphic _create(final Path path,
      final Logger logger, final IFileProducerListener listener,
      final BufferedImage img, final Graphics2D g, final int w,
      final int h, final double xDPI, final double yDPI) {
    return new _ImageIOGIFGraphic(path, logger, listener, img, g, w, h,
        xDPI, yDPI);
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
  public static final ImageIOGIFGraphicDriver getDefaultInstance() {
    return __ImageIOGIFGraphicDriverLoader.INSTANCE;
  }

  /**
   * Get the instance of the GIF driver with the given setup
   * 
   * @param dotsPerInch
   *          the dots per inch
   * @param colors
   *          the colors
   * @return the corresponding instance
   */
  public static final ImageIOGIFGraphicDriver getInstance(
      final EColorModel colors, final int dotsPerInch) {
    final EColorModel c;
    c = ImageIOGIFGraphicDriver.__fixColors(colors);
    if ((c == ImageIOGIFGraphicDriver.DEFAULT_COLOR_MODEL)
        && (dotsPerInch == EGraphicFormat.DEFAULT_DPI)) {
      return ImageIOGIFGraphicDriver.getDefaultInstance();
    }
    return new ImageIOGIFGraphicDriver(c, dotsPerInch);
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
    static final ImageIOGIFGraphicDriver INSTANCE = //
    new ImageIOGIFGraphicDriver(
        ImageIOGIFGraphicDriver.DEFAULT_COLOR_MODEL,
        EGraphicFormat.DEFAULT_DPI);
  }
}
