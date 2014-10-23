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

  /**
   * the <a
   * href="http://en.wikipedia.org/wiki/Encapsulated_PostScript">EPS</a>
   * format
   */
  EPS {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return FreeHEPEPSGraphicDriver.INSTANCE;
    }
  },

  /**
   * the <a
   * href="http://en.wikipedia.org/wiki/Portable_Document_Format">PDF</a>
   * format
   */
  PDF {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return FreeHEPPDFGraphicDriver.INSTANCE;
    }
  },

  /**
   * the <a
   * href="http://en.wikipedia.org/wiki/Scalable_Vector_Graphics">SVG</a>
   * format
   */
  SVG {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return FreeHEPSVGGraphicDriver.INSTANCE;
    }
  },

  /**
   * the <a href="http://en.wikipedia.org/wiki/Windows_Metafile">EMF</a>
   * format
   */
  EMF {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return FreeHEPEMFGraphicDriver.INSTANCE;
    }
  },

  /** the <a href="http://en.wikipedia.org/wiki/JPEG">JPEG</a> format */
  JPEG {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return ImageIOJPEGGraphicDriver.DEFAULT_INSTANCE;
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
  PNG {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return ImageIOPNGGraphicDriver.DEFAULT_INSTANCE;
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
  GIF {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return ImageIOGIFGraphicDriver.DEFAULT_INSTANCE;
    }

    /** {@inheritDoc} */
    @Override
    public IGraphicDriver getDriver(final EColorModel color,
        final int dotsPerInch, final double quality) {
      return new ImageIOGIFGraphicDriver(color, dotsPerInch);
    }
  },

  /** A graphics driver which discards all output */
  NULL {
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return NullGraphicDriver.INSTANCE;
    }
  };

  /** the default color model */
  public static final EColorModel DEFAULT_COLOR_MODEL = EColorModel.RBG_24_BIT;

  /** the default value for dots per inch */
  public static final int DEFAULT_DPI = 300;

  /** the default image encoding quality */
  public static final double DEFAULT_QUALITY = 0.8d;

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
}
