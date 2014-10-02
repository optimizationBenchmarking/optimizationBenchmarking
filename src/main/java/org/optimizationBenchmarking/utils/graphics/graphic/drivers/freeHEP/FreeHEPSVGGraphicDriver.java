package org.optimizationBenchmarking.utils.graphics.graphic.drivers.freeHEP;

import java.awt.Color;
import java.awt.Dimension;
import java.io.OutputStream;
import java.nio.file.Path;

import org.freehep.graphicsio.svg.SVGGraphics2D;
import org.freehep.util.UserProperties;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.Graphic;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * A driver which creates <a
 * href="http://en.wikipedia.org/wiki/Scalable_Vector_Graphics">SVG</a>
 * (graphics).
 */
public class FreeHEPSVGGraphicDriver extends AbstractGraphicDriver {
  /** the properties */
  private final org.freehep.util.UserProperties m_props;

  /**
   * the globally shared instance of the <a
   * href="http://en.wikipedia.org/wiki/Scalable_Vector_Graphics">SVG</a>
   * graphic driver
   */
  public static final FreeHEPSVGGraphicDriver INSTANCE = new FreeHEPSVGGraphicDriver();

  /** the hidden constructor */
  private FreeHEPSVGGraphicDriver() {
    super("svgz"); //$NON-NLS-1$

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
  @Override
  protected final Graphic doCreateGraphic(final Path path,
      final OutputStream os, final PhysicalDimension size,
      final IObjectListener listener) {
    final UserProperties up;
    final SVGGraphics2D g;
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

    synchronized (SVGGraphics2D.class) {
      g = new SVGGraphics2D(os, dim);
      g.setProperties(up);
      g.setClip(0, 0, dim.width, dim.height);
      AbstractGraphicDriver.setDefaultRenderingHints(g);
      g.startExport();
    }
    AbstractGraphicDriver.setDefaultRenderingHints(g);

    return new _FreeHEPSVGGraphic(g, path, listener, dim.width, dim.height);
  }

}
