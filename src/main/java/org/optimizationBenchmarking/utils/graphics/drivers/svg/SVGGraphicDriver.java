package org.optimizationBenchmarking.utils.graphics.drivers.svg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.io.OutputStream;

import org.freehep.graphicsio.svg.SVGGraphics2D;
import org.freehep.util.UserProperties;
import org.optimizationBenchmarking.utils.graphics.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.Graphic;
import org.optimizationBenchmarking.utils.graphics.GraphicID;
import org.optimizationBenchmarking.utils.graphics.IGraphicListener;
import org.optimizationBenchmarking.utils.math.units.ELength;

/** A driver which creates SVG (graphics). */
public class SVGGraphicDriver extends AbstractGraphicDriver {
  /** the properties */
  private final org.freehep.util.UserProperties m_props;

  /** the globally shared instance of the svg graphic driver */
  public static final SVGGraphicDriver INSTANCE = new SVGGraphicDriver();

  /** the hidden constructor */
  private SVGGraphicDriver() {
    super(".svgz"); //$NON-NLS-1$

    this.m_props = new org.freehep.util.UserProperties();

    this.m_props.putAll(SVGGraphics2D.getDefaultProperties());

    this.m_props.setProperty(SVGGraphics2D.EMBED_FONTS, true);
    this.m_props.setProperty(SVGGraphics2D.BACKGROUND_COLOR, Color.WHITE);
    this.m_props.setProperty(SVGGraphics2D.COMPRESS, true);
    this.m_props.setProperty(SVGGraphics2D.STYLABLE, true);
    this.m_props.setProperty(SVGGraphics2D.VERSION,
        SVGGraphics2D.VERSION_1_1);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected final Graphic doCreateGraphic(final GraphicID id,
      final Dimension2D size, final ELength sizeUnit,
      final IGraphicListener listener) {
    final UserProperties up;
    final SVGGraphics2D g;
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
    synchronized (SVGGraphics2D.class) {
      g = new SVGGraphics2D(os, dim);
      g.setProperties(up);
      g.startExport();
      g.setClip(0, 0, dim.width, dim.height);
    }

    return new _SVGGraphic(g, id, listener, dim.width, dim.height);
  }

}
