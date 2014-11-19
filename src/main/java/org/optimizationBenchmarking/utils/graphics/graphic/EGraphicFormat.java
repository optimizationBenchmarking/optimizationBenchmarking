package org.optimizationBenchmarking.utils.graphics.graphic;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.NullGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPEMFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPEPSGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPPDFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPSVGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIOBMPGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIOGIFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIOJPEGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIOPNGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * An enumeration with graphics formats.
 */
public enum EGraphicFormat implements IFileType {

  /** A graphics driver which discards all output */
  NULL(TextUtils.NULL_STRING, false, null, null) {
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
  EPS("Encapsulated PostScript", true,//$NON-NLS-1$
      "eps", "image/eps") { //$NON-NLS-1$//$NON-NLS-2$
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
  PDF("Portable Document Format", true,//$NON-NLS-1$
      "pdf", "application/pdf") { //$NON-NLS-1$//$NON-NLS-2$
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
  SVG("Scalable Vector Graphics", true,//$NON-NLS-1$
      "svg", "image/svg+xml") { //$NON-NLS-1$//$NON-NLS-2$
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return FreeHEPSVGGraphicDriver.getPlainInstance();
    }
  },

  /**
   * the <a
   * href="http://en.wikipedia.org/wiki/Scalable_Vector_Graphics">SVG</a>
   * format
   */
  SVGZ("Compressed Scalable Vector Graphics", SVG.m_isVector,//$NON-NLS-1$
      "svgz", SVG.m_mime) { //$NON-NLS-1$
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return FreeHEPSVGGraphicDriver.getCompressedInstance();
    }
  },

  /**
   * the <a href="http://en.wikipedia.org/wiki/Windows_Metafile">EMF</a>
   * format
   */
  EMF("Enhanced Metafile", true,//$NON-NLS-1$
      "emf", "image/x-emf") { //$NON-NLS-1$//$NON-NLS-2$
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return FreeHEPEMFGraphicDriver.getInstance();
    }
  },

  /** the <a href="http://en.wikipedia.org/wiki/JPEG">JPEG</a> format */
  JPEG("JPEG Image", false,//$NON-NLS-1$
      "jpg", "image/jpeg") { //$NON-NLS-1$//$NON-NLS-2$
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return ImageIOJPEGGraphicDriver.getDefaultInstance();
    }

    /** {@inheritDoc} */
    @Override
    public IGraphicDriver getDriver(final EColorModel color,
        final int dotsPerInch, final double quality) {
      return ImageIOJPEGGraphicDriver.getInstance(color, dotsPerInch,
          ((float) quality));
    }
  },

  /**
   * the <a
   * href="http://en.wikipedia.org/wiki/Portable_Network_Graphics">PNG</a>
   * format
   */
  PNG("Portable Network Graphics", false,//$NON-NLS-1$
      "png", "image/png") { //$NON-NLS-1$//$NON-NLS-2$
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return ImageIOPNGGraphicDriver.getDefaultInstance();
    }

    /** {@inheritDoc} */
    @Override
    public IGraphicDriver getDriver(final EColorModel color,
        final int dotsPerInch, final double quality) {
      return ImageIOPNGGraphicDriver.getInstance(color, dotsPerInch);
    }
  },

  /**
   * the <a
   * href="http://en.wikipedia.org/wiki/Graphics_Interchange_Format">
   * GIF</a> format
   */
  GIF("Graphics Interchange Format", false,//$NON-NLS-1$
      "gif", "image/gif") { //$NON-NLS-1$//$NON-NLS-2$
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return ImageIOGIFGraphicDriver.getDefaultInstance();
    }

    /** {@inheritDoc} */
    @Override
    public IGraphicDriver getDriver(final EColorModel color,
        final int dotsPerInch, final double quality) {
      return ImageIOGIFGraphicDriver.getInstance(color, dotsPerInch);
    }
  },
  /**
   * the <a href="http://en.wikipedia.org/wiki/BMP_file_format">BMP</a>
   * format
   */
  BMP("Bitmap Image Format", false,//$NON-NLS-1$
      "bmp", "image/bmp") { //$NON-NLS-1$//$NON-NLS-2$
    /** {@inheritDoc} */
    @Override
    public final IGraphicDriver getDefaultDriver() {
      return ImageIOBMPGraphicDriver.getDefaultInstance();
    }

    /** {@inheritDoc} */
    @Override
    public IGraphicDriver getDriver(final EColorModel color,
        final int dotsPerInch, final double quality) {
      return ImageIOBMPGraphicDriver.getInstance(color, dotsPerInch);
    }
  },

  ;

  /** the default color model */
  public static final EColorModel DEFAULT_COLOR_MODEL = EColorModel.RBG_24_BIT;

  /** the default value for dots per inch */
  public static final int DEFAULT_DPI = 300;

  /** the default image encoding quality */
  public static final double DEFAULT_QUALITY = 0.8d;

  /** the set of graphic formats */
  public static final ArraySetView<EGraphicFormat> INSTANCES = //
  new ArraySetView<>(EGraphicFormat.values());

  /** is this a vector graphic format? */
  private final boolean m_isVector;

  /** the file type's name */
  private final String m_name;

  /** the suffix for files of this type */
  private final String m_suffix;

  /** the mime type */
  private final String m_mime;

  /**
   * Create the graphics format specifier
   * 
   * @param name
   *          the file type's name
   * @param isVector
   *          is this a vector graphic?
   * @param suffix
   *          the file suffix
   * @param mime
   *          the mime type
   */
  private EGraphicFormat(final String name, final boolean isVector,
      final String suffix, final String mime) {
    this.m_name = name;
    this.m_isVector = isVector;
    this.m_suffix = suffix;
    this.m_mime = mime;
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
   */
  public final boolean isVectorFormat() {
    return this.m_isVector;
  }

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return this.m_suffix;
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return this.m_mime;
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return this.m_name;
  }
}
