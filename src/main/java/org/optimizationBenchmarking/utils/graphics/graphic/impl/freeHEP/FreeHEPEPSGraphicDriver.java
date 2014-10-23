package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.awt.Color;
import java.awt.Dimension;
import java.io.OutputStream;
import java.nio.file.Path;

import org.freehep.graphicsio.FontConstants;
import org.freehep.graphicsio.PageConstants;
import org.freehep.graphicsio.ps.PSGraphics2D;
import org.freehep.util.UserProperties;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.GraphicUtils;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * A driver which creates <a
 * href="http://en.wikipedia.org/wiki/Encapsulated_PostScript">EPS</a>
 * graphics.
 */
public class FreeHEPEPSGraphicDriver extends AbstractGraphicDriver {
  /** the properties */
  private final org.freehep.util.UserProperties m_props;

  /**
   * the globally shared instance of the <a
   * href="http://en.wikipedia.org/wiki/Encapsulated_PostScript">EPS</a>
   * graphic driver
   */
  public static final FreeHEPEPSGraphicDriver INSTANCE = new FreeHEPEPSGraphicDriver();

  /** the hidden constructor */
  private FreeHEPEPSGraphicDriver() {
    super("eps"); //$NON-NLS-1$

    this.m_props = new org.freehep.util.UserProperties();

    PSGraphics2D.setClipEnabled(true);

    this.m_props.putAll(PSGraphics2D.getDefaultProperties());
    this.m_props.setProperty(PSGraphics2D.PAGE_SIZE,
        PageConstants.BEST_FIT);
    this.m_props.setProperty(PSGraphics2D.EMBED_FONTS, true);
    this.m_props.setProperty(PSGraphics2D.EMBED_FONTS_AS,
        FontConstants.EMBED_FONTS_TYPE3);
    this.m_props.setProperty(PSGraphics2D.PREVIEW, false);
    this.m_props.setProperty(PSGraphics2D.PAGE_SIZE,
        PSGraphics2D.CUSTOM_PAGE_SIZE);
    this.m_props.setProperty(PSGraphics2D.BACKGROUND_COLOR, Color.WHITE);
    this.m_props.setProperty(PSGraphics2D.PAGE_MARGINS, "0, 0, 0, 0"); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "FreeHEP-based EPS Driver"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final Graphic doCreateGraphic(final Path path,
      final OutputStream os, final PhysicalDimension size,
      final IObjectListener listener) {
    final UserProperties up;
    final PSGraphics2D g;
    final double wd, hd;
    final Dimension dim;

    final ELength sizeUnit;

    up = new UserProperties();
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

    up.setProperty(PSGraphics2D.CUSTOM_PAGE_SIZE, dim);

    synchronized (PSGraphics2D.class) {
      PSGraphics2D.setClipEnabled(true);
      g = new PSGraphics2D(os, dim);
      g.setProperties(up);
      g.setMultiPage(false);
      GraphicUtils.setDefaultRenderingHints(g);
      g.startExport();
      g.setClip(0, 0, dim.width, dim.height);
    }
    GraphicUtils.setDefaultRenderingHints(g);

    return new _FreeHEPEPSGraphic(g, path, listener, dim.width, dim.height);
  }

}
