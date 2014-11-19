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
 * A driver which creates <a
 * href="http://en.wikipedia.org/wiki/Portable_Network_Graphics">PNG</a>
 * graphics Java's raster graphics.
 */
public final class ImageIOPNGGraphicDriver extends
    _ImageIORasterGraphicDriver {

  /**
   * Get the default instance of the PNG driver based on Java's imaging API
   * 
   * @return the default instance of the PNG driver based on Java's imaging
   *         API
   */
  public static final ImageIOPNGGraphicDriver getDefaultInstance() {
    return __ImageIOPNGGraphicDriverLoader.INSTANCE;
  }

  /**
   * Get the instance of the PNG driver with the given setup
   * 
   * @param dotsPerInch
   *          the dots per inch
   * @param colors
   *          the colors
   * @return the corresponding instance
   */
  public static final ImageIOPNGGraphicDriver getInstance(
      final EColorModel colors, final int dotsPerInch) {
    if ((colors == EGraphicFormat.DEFAULT_COLOR_MODEL)
        && (dotsPerInch == EGraphicFormat.DEFAULT_DPI)) {
      return ImageIOPNGGraphicDriver.getDefaultInstance();
    }
    return new ImageIOPNGGraphicDriver(colors, dotsPerInch);
  }

  /**
   * Create a new png driver for based on {@link javax.imageio ImageIO}.
   * 
   * @param dotsPerInch
   *          the dots per inch
   * @param colors
   *          the colors
   */
  ImageIOPNGGraphicDriver(final EColorModel colors, final int dotsPerInch) {
    super(EGraphicFormat.PNG, colors, dotsPerInch);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (ImageIOPNGGraphicDriver._ImageIOPNGSPILoader.SPI != null);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final ImageIOPNGGraphicDriver d;
    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o instanceof ImageIOPNGGraphicDriver) {
      d = ((ImageIOPNGGraphicDriver) o);
      return ((this.m_dpi == d.m_dpi) && //
      (EComparison.equals(this.m_colors, d.m_colors)));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append("javax.imageio-based PNG Driver for "); //$NON-NLS-1$
    super.toText(textOut);
  }

  /** {@inheritDoc} */
  @Override
  final _ImageIORasterGraphic _create(final Path path,
      final Logger logger, final IFileProducerListener listener,
      final BufferedImage img, final Graphics2D g, final int w,
      final int h, final double xDPI, final double yDPI) {
    return new _ImageIOPNGGraphic(path, logger, listener, img, g, w, h,
        xDPI, yDPI);
  }

  /** the loader for the png SPI */
  static final class _ImageIOPNGSPILoader {
    /** the image writer spi */
    static final ImageWriterSpi SPI = //
    _ImageIORasterGraphicDriver.getSPI(EGraphicFormat.PNG);
  }

  /** the default loader */
  private static final class __ImageIOPNGGraphicDriverLoader {
    /**
     * the default graphic <a
     * href="http://en.wikipedia.org/wiki/Portable_Network_Graphics"
     * >PNG</a> driver instance
     */
    static final ImageIOPNGGraphicDriver INSTANCE = //
    new ImageIOPNGGraphicDriver(EGraphicFormat.DEFAULT_COLOR_MODEL,
        EGraphicFormat.DEFAULT_DPI);
  }
}
