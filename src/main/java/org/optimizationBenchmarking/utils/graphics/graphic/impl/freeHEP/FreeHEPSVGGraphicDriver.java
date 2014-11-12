package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.awt.Color;
import java.awt.Dimension;
import java.io.OutputStream;
import java.nio.file.Path;

import org.freehep.graphicsio.svg.SVGGraphics2D;
import org.freehep.util.UserProperties;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.GraphicUtils;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * A driver which creates <a
 * href="http://en.wikipedia.org/wiki/Scalable_Vector_Graphics">SVG</a>
 * (graphics).
 */
public class FreeHEPSVGGraphicDriver extends AbstractGraphicDriver {
  /** the properties */
  private final org.freehep.util.UserProperties m_props;

  /** the hidden constructor */
  FreeHEPSVGGraphicDriver() {
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
    return "FreeHEP-based SVG Driver"; //$NON-NLS-1$
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
      GraphicUtils.setDefaultRenderingHints(g);
      g.startExport();
    }
    GraphicUtils.setDefaultRenderingHints(g);

    return new _FreeHEPSVGGraphic(g, path, listener, dim.width, dim.height);
  }

  /** {@inheritDoc} */
  @Override
  public final EGraphicFormat getGraphicFormat() {
    return EGraphicFormat.SVG;
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
