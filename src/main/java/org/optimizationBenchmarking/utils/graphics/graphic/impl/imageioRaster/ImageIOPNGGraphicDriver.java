package org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A driver which creates <a
 * href="http://en.wikipedia.org/wiki/Portable_Network_Graphics">PNG</a>
 * graphics Java's raster graphics.
 */
public final class ImageIOPNGGraphicDriver extends
    _ImageIORasterGraphicDriver {
  /**
   * the default graphic <a
   * href="http://en.wikipedia.org/wiki/Portable_Network_Graphics">PNG</a>
   * driver instance
   */
  public static final ImageIOPNGGraphicDriver DEFAULT_INSTANCE = //
  new ImageIOPNGGraphicDriver(EGraphicFormat.DEFAULT_COLOR_MODEL,
      EGraphicFormat.DEFAULT_DPI);

  /**
   * Create a new png driver for based on {@link javax.imageio ImageIO}.
   * 
   * @param dotsPerInch
   *          the dots per inch
   * @param colors
   *          the colors
   */
  public ImageIOPNGGraphicDriver(final EColorModel colors,
      final int dotsPerInch) {
    super("png", colors, dotsPerInch); //$NON-NLS-1$
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
      final OutputStream os, final IObjectListener listener,
      final BufferedImage img, final Graphics2D g, final int w,
      final int h, final double xDPI, final double yDPI, final String type) {
    return new _ImageIOPNGGraphic(path, os, listener, img, g, w, h, xDPI,
        yDPI, type);
  }

  /** {@inheritDoc} */
  @Override
  public final EGraphicFormat getGraphicFormat() {
    return EGraphicFormat.PNG;
  }
}
