package org.optimizationBenchmarking.utils.graphics.drivers.eps;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.io.OutputStream;

import org.freehep.graphicsio.FontConstants;
import org.freehep.graphicsio.PageConstants;
import org.freehep.graphicsio.ps.PSGraphics2D;
import org.freehep.util.UserProperties;
import org.optimizationBenchmarking.utils.graphics.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.Graphic;
import org.optimizationBenchmarking.utils.graphics.GraphicID;
import org.optimizationBenchmarking.utils.graphics.IGraphicListener;
import org.optimizationBenchmarking.utils.math.units.ELength;

/** A driver which creates EPS graphics. */
public class EPSGraphicDriver extends AbstractGraphicDriver {
  /** the properties */
  private final org.freehep.util.UserProperties m_props;

  /** the globally shared instance of the eps graphic driver */
  public static final EPSGraphicDriver INSTANCE = new EPSGraphicDriver();

  /** the hidden constructor */
  private EPSGraphicDriver() {
    super(".eps"); //$NON-NLS-1$

    this.m_props = new org.freehep.util.UserProperties();

    PSGraphics2D.setClipEnabled(true);

    this.m_props.putAll(PSGraphics2D.getDefaultProperties());
    this.m_props.setProperty(PSGraphics2D.PAGE_SIZE,
        PageConstants.BEST_FIT);
    this.m_props.setProperty(PSGraphics2D.EMBED_FONTS, true);
    this.m_props.setProperty(PSGraphics2D.EMBED_FONTS_AS,
        FontConstants.EMBED_FONTS_TYPE3);
    this.m_props.setProperty(PSGraphics2D.BACKGROUND_COLOR, Color.WHITE);
    this.m_props.setProperty(PSGraphics2D.PAGE_MARGINS, "0, 0, 0, 0"); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected final Graphic doCreateGraphic(final GraphicID id,
      final Dimension2D size, final ELength sizeUnit,
      final IGraphicListener listener) {
    final UserProperties up;
    final PSGraphics2D g;
    final double wd, hd;
    final Dimension dim;
    final OutputStream os;

    up = new UserProperties();
    up.putAll(this.m_props);

    wd = sizeUnit.convertTo(size.getWidth(), ELength.POINT_POSTSCRIPT);
    hd = sizeUnit.convertTo(size.getHeight(), ELength.POINT_POSTSCRIPT);
    dim = new Dimension();
    if ((wd <= 0d) || (wd >= Integer.MAX_VALUE) || (hd <= 0d)
        || (hd >= Integer.MAX_VALUE)
        || ((dim.width = ((int) (0.5d + wd))) <= 0)
        || ((dim.height = ((int) (0.5d + hd))) <= 0)) {
      throw new IllegalArgumentException("Invalid size " + size + //$NON-NLS-1$
          " translated to " + dim);//$NON-NLS-1$
    }

    os = AbstractGraphicDriver.createOutputStream(id);
    synchronized (PSGraphics2D.class) {
      PSGraphics2D.setClipEnabled(true);
      g = new PSGraphics2D(os, dim);
      g.setProperties(up);
      g.setMultiPage(false);
      setDefaultRenderingHints(g);
      g.startExport();
      g.setClip(0, 0, dim.width, dim.height);
    }
    setDefaultRenderingHints(g);

    return new _EPSGraphic(g, id, listener, dim.width, dim.height);
  }

}
