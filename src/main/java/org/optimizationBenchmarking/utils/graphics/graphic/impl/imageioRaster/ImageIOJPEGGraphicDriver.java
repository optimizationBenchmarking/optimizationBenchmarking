package org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A driver which creates Java's raster <a
 * href="http://en.wikipedia.org/wiki/JPEG">JPEG</a> graphics.
 */
public final class ImageIOJPEGGraphicDriver extends
    _ImageIORasterGraphicDriver {

  /** the default graphic JPEG driver instance */
  public static final ImageIOJPEGGraphicDriver DEFAULT_INSTANCE = //
  new ImageIOJPEGGraphicDriver(EGraphicFormat.DEFAULT_COLOR_MODEL,
      EGraphicFormat.DEFAULT_DPI,
      ((float) (EGraphicFormat.DEFAULT_QUALITY)));

  /** the quality */
  private final float m_quality;

  /**
   * Create a new png driver for based on {@link javax.imageio ImageIO}.
   * 
   * @param dotsPerInch
   *          the dots per inch
   * @param colors
   *          the colors
   * @param quality
   *          the quality
   */
  public ImageIOJPEGGraphicDriver(final EColorModel colors,
      final int dotsPerInch, final float quality) {
    super("jpg", //$NON-NLS-1$
        ImageIOJPEGGraphicDriver.__fixColors(colors), dotsPerInch);
    if ((quality < 0f) || (quality > 1f) || (quality != quality)) {
      throw new IllegalArgumentException("Illegal quality: " + quality);//$NON-NLS-1$
    }

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

  /** {@inheritDoc} */
  @Override
  final _ImageIORasterGraphic _create(final Path path,
      final OutputStream os, final IObjectListener listener,
      final BufferedImage img, final Graphics2D g, final int w,
      final int h, final double xDPI, final double yDPI, final String type) {
    return new _ImageIOJPEGGraphic(path, os, listener, img, g, w, h, xDPI,
        yDPI, this.m_quality, type);
  }

  /** {@inheritDoc} */
  @Override
  public final EGraphicFormat getGraphicFormat() {
    return EGraphicFormat.JPEG;
  }
}
