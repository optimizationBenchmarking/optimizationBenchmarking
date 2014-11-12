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
 * A driver which creates Java's raster <a
 * href="http://en.wikipedia.org/wiki/Graphics_Interchange_Format">GIF</a>
 * graphics.
 */
public final class ImageIOGIFGraphicDriver extends
    _ImageIORasterGraphicDriver {

  /**
   * Create a new png driver for based on {@link javax.imageio ImageIO}.
   * 
   * @param dotsPerInch
   *          the dots per inch
   * @param colors
   *          the colors
   */
  public ImageIOGIFGraphicDriver(final EColorModel colors,
      final int dotsPerInch) {
    super("gif",//$NON-NLS-1$
        ImageIOGIFGraphicDriver.__fixColors(colors), dotsPerInch);
  }

  /**
   * Get the default instance of the GIF driver based on Java's imaging API
   * 
   * @return the default instance of the GIF driver based on Java's imaging
   *         API
   */
  public static final ImageIOGIFGraphicDriver getDefaultInstance() {
    return __ImageIOGIFGraphicDriverLoader.DEFAULT_INSTANCE;
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

  /** {@inheritDoc} */
  @Override
  final _ImageIORasterGraphic _create(final Path path,
      final OutputStream os, final IObjectListener listener,
      final BufferedImage img, final Graphics2D g, final int w,
      final int h, final double xDPI, final double yDPI, final String type) {
    return new _ImageIOGIFGraphic(path, os, listener, img, g, w, h, xDPI,
        yDPI, type);
  }

  /** {@inheritDoc} */
  @Override
  public final EGraphicFormat getGraphicFormat() {
    return EGraphicFormat.GIF;
  }

  /** the default loader */
  private static final class __ImageIOGIFGraphicDriverLoader {
    /** the default graphic GIF driver instance */
    static final ImageIOGIFGraphicDriver DEFAULT_INSTANCE = //
    new ImageIOGIFGraphicDriver(EGraphicFormat.DEFAULT_COLOR_MODEL,
        EGraphicFormat.DEFAULT_DPI);
  }
}
