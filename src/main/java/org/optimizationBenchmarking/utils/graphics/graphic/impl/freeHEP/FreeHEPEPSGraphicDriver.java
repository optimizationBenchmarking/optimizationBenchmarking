package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.awt.Color;
import java.awt.Dimension;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.GraphicUtils;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A driver which creates <a
 * href="http://en.wikipedia.org/wiki/Encapsulated_PostScript">EPS</a>
 * graphics.
 */
public class FreeHEPEPSGraphicDriver extends AbstractGraphicDriver {
  /** the properties */
  private final Map<Object, Object> m_props;

  /** the hidden constructor */
  FreeHEPEPSGraphicDriver() {
    super(EGraphicFormat.EPS);
    Map<Object, Object> o;

    try {
      o = FreeHEPEPSGraphicDriver.__initialize();
    } catch (final Throwable t) {
      o = null;
    }
    this.m_props = o;
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

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (this.m_props != null);
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
  @Override
  protected final Graphic createGraphic(final Logger logger,
      final IFileProducerListener listener, final Path basePath,
      final String mainDocumentNameSuggestion, final PhysicalDimension size) {

    final org.freehep.util.UserProperties up;
    final org.freehep.graphicsio.ps.PSGraphics2D g;
    final double wd, hd;
    final Dimension dim;
    final ELength sizeUnit;
    final Path path;

    up = new org.freehep.util.UserProperties();
    up.putAll(this.m_props);

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

    path = this.makePath(basePath, mainDocumentNameSuggestion);
    synchronized (org.freehep.graphicsio.ps.PSGraphics2D.class) {
      org.freehep.graphicsio.ps.PSGraphics2D.setClipEnabled(true);
      g = new org.freehep.graphicsio.ps.PSGraphics2D(
          PathUtils.openOutputStream(path), dim);
      g.setProperties(up);
      g.setMultiPage(false);
      GraphicUtils.setDefaultRenderingHints(g);
      g.startExport();
      g.setClip(0, 0, dim.width, dim.height);
    }
    GraphicUtils.setDefaultRenderingHints(g);

    return new _FreeHEPEPSGraphic(g, logger, listener, path, dim.width,
        dim.height);
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
