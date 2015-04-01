package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.awt.Color;
import java.awt.Dimension;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Logger;

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
 * href="http://en.wikipedia.org/wiki/Windows_Metafile">EMF</a> graphics.
 */
public final class FreeHEPEMFGraphicDriver extends AbstractGraphicDriver {
  /** the properties */
  private final Map<Object, Object> m_props;

  /** the error */
  private final Throwable m_error;

  /** the hidden constructor */
  FreeHEPEMFGraphicDriver() {
    super(EGraphicFormat.EMF);

    Map<Object, Object> o;
    Throwable error;

    error = null;
    try {
      o = FreeHEPEMFGraphicDriver.__initialize();
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
          TextUtils.className(FreeHEPEMFGraphicDriver.class)),
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
   *           if at least one required class could not be loaded
   */
  private static final Map<Object, Object> __initialize()
      throws ClassNotFoundException {
    ReflectionUtils.ensureClassesAreLoaded(//
        "org.freehep.graphicsio.emf.EMFGraphics2D", //$NON-NLS-1$
        "org.freehep.graphics2d.PrintColor", //$NON-NLS-1$;
        "org.freehep.graphics2d.VectorGraphics", //$NON-NLS-1$;
        "org.freehep.graphics2d.font.FontEncoder", //$NON-NLS-1$;
        "org.freehep.graphics2d.font.FontUtilities", //$NON-NLS-1$;
        "org.freehep.graphicsio.AbstractVectorGraphicsIO", //$NON-NLS-1$
        "org.freehep.graphicsio.PageConstants", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.AlphaBlend", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.BeginPath", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.CreateBrushIndirect", //$NON-NLS-1$
        "org.freehep.graphicsio.emf.gdi.DeleteObject", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.EOF", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.EndPath", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.ExtCreateFontIndirectW", //$NON-NLS-1$
        "org.freehep.graphicsio.emf.gdi.ExtCreatePen", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.ExtLogFontW", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.ExtLogPen", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.ExtTextOutW", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.FillPath", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.LogBrush32", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.ModifyWorldTransform", //$NON-NLS-1$
        "org.freehep.graphicsio.emf.gdi.RestoreDC", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.SaveDC", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.SelectClipPath", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.SelectObject", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.SetBkMode", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.SetMapMode", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.SetMiterLimit", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.SetPolyFillMode", //$NON-NLS-1$
        "org.freehep.graphicsio.emf.gdi.SetTextAlign", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.SetTextColor", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.SetViewportExtEx", //$NON-NLS-1$
        "org.freehep.graphicsio.emf.gdi.SetViewportOrgEx", //$NON-NLS-1$
        "org.freehep.graphicsio.emf.gdi.SetWindowExtEx", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.SetWindowOrgEx", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.SetWorldTransform", //$NON-NLS-1$
        "org.freehep.graphicsio.emf.gdi.StrokeAndFillPath", //$NON-NLS-1$
        "org.freehep.graphicsio.emf.gdi.StrokePath", //$NON-NLS-1$;
        "org.freehep.graphicsio.emf.gdi.TextW", //$NON-NLS-1$;
        "org.freehep.graphicsio.font.FontTable", //$NON-NLS-1$;
        "org.freehep.util.UserProperties", //$NON-NLS-1$;
        "org.freehep.util.images.ImageUtilities"); //$NON-NLS-1$

    final org.freehep.util.UserProperties props = new org.freehep.util.UserProperties();
    props.putAll(org.freehep.graphicsio.emf.EMFGraphics2D
        .getDefaultProperties());
    props.setProperty(
        org.freehep.graphicsio.emf.EMFGraphics2D.BACKGROUND_COLOR,
        Color.WHITE);

    return props;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "FreeHEP-based EMF Driver"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected final Graphic createGraphic(final GraphicBuilder builder) {

    final org.freehep.util.UserProperties up;
    final _FreeHEPEMFGraphic g;
    final double wd, hd;
    final Dimension dim;
    final ELength sizeUnit;
    final Path path;
    final PhysicalDimension size;
    final Logger logger;

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
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow((((//
          "Error while opening OutputStream '") //$NON-NLS-1$
          + path) + "' for FreeHEPEMFGraphic."), //$NON-NLS-1$
          true, thro);
      return null; // we'll never get here
    }
    logger = builder.getLogger();
    synchronized (org.freehep.graphicsio.emf.EMFGraphics2D.class) {
      g = new _FreeHEPEMFGraphic(stream, dim, logger);
      g.setProperties(up);
      GraphicUtils.setDefaultRenderingHints(g);
      g.setDeviceIndependent(true);
      g.startExport();
      g.setClip(0, 0, dim.width, dim.height);
    }

    return new _FreeHEPEMFGraphicWrapper(g, logger,
        builder.getFileProducerListener(), path, dim.width, dim.height);
  }

  /**
   * get the instance of the FreeHEP EMF driver
   * 
   * @return the instance of the FreeHEP EMF driver
   */
  public static final FreeHEPEMFGraphicDriver getInstance() {
    return __FreeHEPEMFGraphicDriverLoader.INSTANCE;
  }

  /** the internal loader for lazy instantion */
  private static final class __FreeHEPEMFGraphicDriverLoader {

    /** the globally shared instance of the emf graphic driver */
    static final FreeHEPEMFGraphicDriver INSTANCE = new FreeHEPEMFGraphicDriver();
  }
}
