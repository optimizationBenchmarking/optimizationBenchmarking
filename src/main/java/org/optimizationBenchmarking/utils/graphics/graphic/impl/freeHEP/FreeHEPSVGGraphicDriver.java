package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.awt.Color;
import java.awt.Dimension;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Map;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.GraphicUtils;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A driver which creates <a
 * href="http://en.wikipedia.org/wiki/Scalable_Vector_Graphics">SVG</a>
 * (graphics).
 */
public class FreeHEPSVGGraphicDriver extends AbstractGraphicDriver {
  /** the properties */
  private final Map<Object, Object> m_props;

  /** the error */
  private final Throwable m_error;

  /** the hidden constructor */
  FreeHEPSVGGraphicDriver() {
    super(EGraphicFormat.SVG);

    Map<Object, Object> o;
    Throwable error;

    error = null;
    try {
      o = FreeHEPSVGGraphicDriver._initialize(false);
    } catch (final Throwable t) {
      o = null;
      error = t;
    }
    this.m_props = o;
    this.m_error = error;
  }

  /** {@inheritDoc} */
  @Override
  public final void checkCanUse() {
    if (this.m_error != null) {
      throw new UnsupportedOperationException(
          ("Cannot use " + //$NON-NLS-1$
          TextUtils.className(FreeHEPSVGGraphicDriver.class)),
          this.m_error);
    }
    super.checkCanUse();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return ((this.m_props != null) && (this.m_error == null));
  }

  /**
   * try to initialize
   * 
   * @param compress
   *          should we compress?
   * @return the properties
   * @throws ClassNotFoundException
   *           if at least one of the class could not be loaded
   */
  static final Map<Object, Object> _initialize(final boolean compress)
      throws ClassNotFoundException {

    ReflectionUtils.ensureClassesAreLoaded(
        "org.freehep.graphics2d.font.FontUtilities", //$NON-NLS-1$
        "org.freehep.graphicsio.AbstractVectorGraphicsIO", //$NON-NLS-1$
        "org.freehep.graphicsio.FontConstants", //$NON-NLS-1$
        "org.freehep.graphicsio.ImageConstants", //$NON-NLS-1$
        //     "org.freehep.graphicsio.ImageGraphics2D", //$NON-NLS-1$
        "org.freehep.graphicsio.InfoConstants", //$NON-NLS-1$
        "org.freehep.graphicsio.PageConstants", //$NON-NLS-1$
        "org.freehep.util.UserProperties", //$NON-NLS-1$
        "org.freehep.util.Value", //$NON-NLS-1$
        "org.freehep.util.io.Base64OutputStream", //$NON-NLS-1$
        "org.freehep.util.io.WriterOutputStream", //$NON-NLS-1$
        "org.freehep.xml.util.XMLWriter"); //$NON-NLS-1$

    final org.freehep.util.UserProperties props = new org.freehep.util.UserProperties();
    props.putAll(org.freehep.graphicsio.svg.SVGGraphics2D
        .getDefaultProperties());

    props.setProperty(
        org.freehep.graphicsio.svg.SVGGraphics2D.EMBED_FONTS, false);
    props.setProperty(
        org.freehep.graphicsio.svg.SVGGraphics2D.BACKGROUND_COLOR,
        Color.WHITE);
    props.setProperty(org.freehep.graphicsio.svg.SVGGraphics2D.COMPRESS,
        compress);
    props.setProperty(org.freehep.graphicsio.svg.SVGGraphics2D.STYLABLE,
        true);
    props.setProperty(org.freehep.graphicsio.svg.SVGGraphics2D.VERSION,
        org.freehep.graphicsio.svg.SVGGraphics2D.VERSION_1_1);

    return props;
  }

  /**
   * get the instance of the FreeHEP SVG driver
   * 
   * @return the instance of the FreeHEP SVG driver
   */
  public static final FreeHEPSVGGraphicDriver getInstance() {
    return __FreeHEPSVGGraphicDriverLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return ("FreeHEP-based SVG Driver"); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected final Graphic createGraphic(final GraphicBuilder builder) {
    final org.freehep.util.UserProperties up;
    final org.freehep.graphicsio.svg.SVGGraphics2D g;
    final double wd, hd;
    final Dimension dim;
    final ELength sizeUnit;
    final Path path;
    final PhysicalDimension size;
    OutputStream stream;

    up = new org.freehep.util.UserProperties();
    up.putAll(this.m_props);

    size = builder.getSize();
    sizeUnit = size.getUnit();
    wd = sizeUnit.convertTo(size.getWidth(), ELength.POINT);
    hd = sizeUnit.convertTo(size.getHeight(), ELength.POINT);
    dim = new Dimension();
    if ((wd <= 0d) || (wd >= Integer.MAX_VALUE) || (hd <= 0d)
        || (hd >= Integer.MAX_VALUE)
        || ((dim.width = ((int) (0.5d + wd))) <= 0)
        || ((dim.height = ((int) (0.5d + hd))) <= 0)) {
      throw new IllegalArgumentException("Invalid size " + size + //$NON-NLS-1$
          " translated to " + dim);//$NON-NLS-1$
    }

    path = this.makePath(builder.getBasePath(),
        builder.getMainDocumentNameSuggestion());
    try {
      stream = PathUtils.openOutputStream(path);
    } catch (final Throwable thro) {
      ErrorUtils.throwAsRuntimeException(thro);
      return null; // we'll never get here
    }
    synchronized (org.freehep.graphicsio.svg.SVGGraphics2D.class) {
      g = new org.freehep.graphicsio.svg.SVGGraphics2D(stream, dim);
      g.setProperties(up);
      g.setClip(0, 0, dim.width, dim.height);
      GraphicUtils.setDefaultRenderingHints(g);
      g.startExport();
    }
    GraphicUtils.setDefaultRenderingHints(g);

    return new _FreeHEPSVGGraphic(g, builder.getLogger(),
        builder.getFileProducerListener(), path, dim.width, dim.height,
        this.getFileType());
  }

  /** the loader class */
  private static final class __FreeHEPSVGGraphicDriverLoader {
    /**
     * the globally shared instance of the <a
     * href="http://en.wikipedia.org/wiki/Scalable_Vector_Graphics">SVG</a>
     * graphic driver
     */
    static final FreeHEPSVGGraphicDriver INSTANCE = new FreeHEPSVGGraphicDriver();
  }

}
