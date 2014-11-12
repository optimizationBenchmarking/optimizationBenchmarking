package org.optimizationBenchmarking.utils.graphics.graphic;

import org.optimizationBenchmarking.utils.graphics.graphic.impl.NullGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPEMFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPEPSGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPPDFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPSVGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIOGIFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIOJPEGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIOPNGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;

/**
 * An enumeration with graphics formats.
 */
public enum EGraphicFormat {

  /** A graphics driver which discards all output */
  NULL(false) {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return NullGraphicDriver.getInstance();
    }
  },
  /**
   * the <a
   * href="http://en.wikipedia.org/wiki/Encapsulated_PostScript">EPS</a>
   * format
   */
  EPS(true) {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return FreeHEPEPSGraphicDriver.getInstance();
    }
  },

  /**
   * the <a
   * href="http://en.wikipedia.org/wiki/Portable_Document_Format">PDF</a>
   * format
   */
  PDF(true) {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return FreeHEPPDFGraphicDriver.getInstance();
    }
  },

  /**
   * the <a
   * href="http://en.wikipedia.org/wiki/Scalable_Vector_Graphics">SVG</a>
   * format
   */
  SVG(true) {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return FreeHEPSVGGraphicDriver.getInstance();
    }
  },

  /**
   * the <a href="http://en.wikipedia.org/wiki/Windows_Metafile">EMF</a>
   * format
   */
  EMF(true) {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return FreeHEPEMFGraphicDriver.getInstance();
    }
  },

  /** the <a href="http://en.wikipedia.org/wiki/JPEG">JPEG</a> format */
  JPEG(false) {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return ImageIOJPEGGraphicDriver.getDefaultInstance();
    }

    /** {@inheritDoc} */
    @Override
    public IGraphicDriver getDriver(final EColorModel color,
        final int dotsPerInch, final double quality) {
      return new ImageIOJPEGGraphicDriver(color, dotsPerInch,
          ((float) quality));
    }
  },

  /**
   * the <a
   * href="http://en.wikipedia.org/wiki/Portable_Network_Graphics">PNG</a>
   * format
   */
  PNG(false) {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return ImageIOPNGGraphicDriver.getDefaultInstance();
    }

    /** {@inheritDoc} */
    @Override
    public IGraphicDriver getDriver(final EColorModel color,
        final int dotsPerInch, final double quality) {
      return new ImageIOPNGGraphicDriver(color, dotsPerInch);
    }
  },

  /**
   * the <a
   * href="http://en.wikipedia.org/wiki/Graphics_Interchange_Format">
   * GIF</a> format
   */
  GIF(false) {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return ImageIOGIFGraphicDriver.getDefaultInstance();
    }

    /** {@inheritDoc} */
    @Override
    public IGraphicDriver getDriver(final EColorModel color,
        final int dotsPerInch, final double quality) {
      return new ImageIOGIFGraphicDriver(color, dotsPerInch);
    }
  },

  ;

  /** the default color model */
  public static final EColorModel DEFAULT_COLOR_MODEL = EColorModel.RBG_24_BIT;

  /** the default value for dots per inch */
  public static final int DEFAULT_DPI = 300;

  /** the default image encoding quality */
  public static final double DEFAULT_QUALITY = 0.8d;

  /** is this a vector graphic format? */
  private final boolean m_isVector;

  /**
   * Create the graphics format specifier
   * 
   * @param isVector
   *          is this a vector graphic?
   */
  private EGraphicFormat(final boolean isVector) {
    this.m_isVector = isVector;
  }

  /**
   * Get the default driver of this format
   * 
   * @return the default driver of this format
   */
  public IGraphicDriver getDefaultDriver() {
    throw new UnsupportedOperationException();
  }

  /**
   * Get a driver producing images of the color model and resolution. Not
   * all color models and resolutions need to be supported. Some graphics
   * drivers, for example, may be resolution-independent (vector graphics).
   * Others may only support specific color models. This method tries to
   * return a graphic driver instance which comes a close as possible to
   * the requested configuration, but does not guarantee to match it.
   * 
   * @param color
   *          the color model
   * @param dotsPerInch
   *          the resolution in dots per
   *          {@link org.optimizationBenchmarking.utils.math.units.ELength#INCH
   *          inch}
   * @param quality
   *          the encoding quality: 1 is best, 0 is worst
   * @return the driver
   */
  public IGraphicDriver getDriver(final EColorModel color,
      final int dotsPerInch, final double quality) {
    return this.getDefaultDriver();
  }

  /**
   * <p>
   * Is this graphic format representing an (infinite-precision) vector
   * graphic or a (finite precision) pixel (i.e., raster) graphic?
   * </p>
   * <p>
   * A vector graphic can draw objects with perceived infinite precision.
   * For instance, a horizontal line that consists of 1'000'000 points will
   * be presented as, well, line with 1'000'000 points in a vector graphic.
   * A pixel (raster) graphic, may, for example, map to 1024*640 pixel. A
   * horizontal line will then only consist of 1024 points, regardless of
   * its logical point count. This may play a role when rendering graphics
   * to a file. A vector graphic can become really huge if complex objects
   * are rendered into it. A pixel graphic cannot exceed a given maximum
   * size regardless.
   * </p>
   * 
   * @return {@code true} if the format is a vector graphic, {@code false}
   *         if it is a pixel graphic
   * @see org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic#isVectorGraphic()
   */
  public final boolean isVectorFormat() {
    return this.m_isVector;
  }
}
