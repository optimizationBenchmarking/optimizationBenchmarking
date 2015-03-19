package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.awt.Color;
import java.awt.Dimension;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Map;

import org.optimizationBenchmarking.utils.error.RethrowMode;
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
 * href="http://en.wikipedia.org/wiki/Encapsulated_PostScript">EPS</a>
 * graphics.
 */
public class FreeHEPEPSGraphicDriver extends AbstractGraphicDriver {

  /** the properties */
  private final Map<Object, Object> m_props;

  /** the error */
  private final Throwable m_error;

  /** the hidden constructor */
  FreeHEPEPSGraphicDriver() {
    super(EGraphicFormat.EPS);

    Map<Object, Object> o;
    Throwable error;

    error = null;
    try {
      o = FreeHEPEPSGraphicDriver.__initialize();
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
          TextUtils.className(FreeHEPEPSGraphicDriver.class)),
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
   * @return the properties
   * @throws ClassNotFoundException
   *           if a necessary class could not be loaded
   */
  private static final Map<Object, Object> __initialize()
      throws ClassNotFoundException {

    ReflectionUtils.ensureClassesAreLoaded(
        "org.freehep.graphicsio.ps.PSGraphics2D", //$NON-NLS-1$
        "org.freehep.graphics2d.TagString", //$NON-NLS-1$
        "org.freehep.graphics2d.font.FontUtilities", //$NON-NLS-1$
        "org.freehep.graphicsio.AbstractVectorGraphicsIO", //$NON-NLS-1$
        "org.freehep.graphicsio.FontConstants", //$NON-NLS-1$
        "org.freehep.graphicsio.ImageConstants", //$NON-NLS-1$
        //"org.freehep.graphicsio.ImageGraphics2D", //$NON-NLS-1$
        "org.freehep.graphicsio.InfoConstants", //$NON-NLS-1$
        "org.freehep.graphicsio.MultiPageDocument", //$NON-NLS-1$
        "org.freehep.graphicsio.PageConstants", //$NON-NLS-1$
        "org.freehep.util.ScientificFormat", //$NON-NLS-1$
        "org.freehep.util.UserProperties", //$NON-NLS-1$
        "org.freehep.util.images.ImageUtilities", //$NON-NLS-1$
        "org.freehep.graphics2d.font.FontEncoder"); //$NON-NLS-1$

    final org.freehep.util.UserProperties props = new org.freehep.util.UserProperties();

    org.freehep.graphicsio.ps.PSGraphics2D.setClipEnabled(true);

    props.putAll(org.freehep.graphicsio.ps.PSGraphics2D
        .getDefaultProperties());
    props.setProperty(org.freehep.graphicsio.ps.PSGraphics2D.PAGE_SIZE,
        org.freehep.graphicsio.PageConstants.BEST_FIT);
    props.setProperty(org.freehep.graphicsio.ps.PSGraphics2D.EMBED_FONTS,
        true);
    props.setProperty(
        org.freehep.graphicsio.ps.PSGraphics2D.EMBED_FONTS_AS,
        org.freehep.graphicsio.FontConstants.EMBED_FONTS_TYPE3);
    props.setProperty(org.freehep.graphicsio.ps.PSGraphics2D.PREVIEW,
        false);
    props.setProperty(org.freehep.graphicsio.ps.PSGraphics2D.PAGE_SIZE,
        org.freehep.graphicsio.ps.PSGraphics2D.CUSTOM_PAGE_SIZE);
    props.setProperty(
        org.freehep.graphicsio.ps.PSGraphics2D.BACKGROUND_COLOR,
        Color.WHITE);
    props.setProperty(org.freehep.graphicsio.ps.PSGraphics2D.PAGE_MARGINS,
        "0, 0, 0, 0"); //$NON-NLS-1$

    return props;
  }

  /**
   * get the instance of the FreeHEP EPS driver
   * 
   * @return the instance of the FreeHEP EPS driver
   */
  public static final FreeHEPEPSGraphicDriver getInstance() {
    return __FreeHEPEPSGraphicDriverLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "FreeHEP-based EPS Driver"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected final Graphic createGraphic(final GraphicBuilder builder) {

    final org.freehep.util.UserProperties up;
    final org.freehep.graphicsio.ps.PSGraphics2D g;
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

    up.setProperty(
        org.freehep.graphicsio.ps.PSGraphics2D.CUSTOM_PAGE_SIZE, dim);

    path = this.makePath(builder.getBasePath(),
        builder.getMainDocumentNameSuggestion());

    try {
      stream = PathUtils.openOutputStream(path);
    } catch (final Throwable thro) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow((((//
          "Error while opening OutputStream '") //$NON-NLS-1$
          + path) + "' for FreeHEPEPSGraphic."), //$NON-NLS-1$
          true, thro);
      return null; // we'll never get here
    }
    synchronized (org.freehep.graphicsio.ps.PSGraphics2D.class) {
      org.freehep.graphicsio.ps.PSGraphics2D.setClipEnabled(true);
      g = new org.freehep.graphicsio.ps.PSGraphics2D(stream, dim);
      g.setProperties(up);
      g.setMultiPage(false);
      GraphicUtils.setDefaultRenderingHints(g);
      g.startExport();
      g.setClip(0, 0, dim.width, dim.height);
    }

    return new _FreeHEPEPSGraphic(g, builder.getLogger(),
        builder.getFileProducerListener(), path, dim.width, dim.height);
  }

  /** the loader */
  private static final class __FreeHEPEPSGraphicDriverLoader {
    /**
     * the globally shared instance of the <a
     * href="http://en.wikipedia.org/wiki/Encapsulated_PostScript">EPS</a>
     * graphic driver
     */
    static final FreeHEPEPSGraphicDriver INSTANCE = new FreeHEPEPSGraphicDriver();

  }
}
