package org.optimizationBenchmarking.utils.graphics.drivers.freeHEP;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.io.OutputStream;

import org.freehep.graphicsio.emf.EMFGraphics2D;
import org.freehep.util.UserProperties;
import org.optimizationBenchmarking.utils.graphics.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.Graphic;
import org.optimizationBenchmarking.utils.graphics.GraphicID;
import org.optimizationBenchmarking.utils.graphics.IGraphicListener;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * A driver which creates <a
 * href="http://en.wikipedia.org/wiki/Windows_Metafile">EMF</a> graphics.
 */
public class FreeHEPEMFGraphicDriver extends AbstractGraphicDriver {
  /** the properties */
  private final org.freehep.util.UserProperties m_props;

  /** the globally shared instance of the emf graphic driver */
  public static final FreeHEPEMFGraphicDriver INSTANCE = new FreeHEPEMFGraphicDriver();

  /** the hidden constructor */
  private FreeHEPEMFGraphicDriver() {
    super(".emf"); //$NON-NLS-1$

    this.m_props = new org.freehep.util.UserProperties();

    this.m_props.putAll(EMFGraphics2D.getDefaultProperties());
    this.m_props.setProperty(EMFGraphics2D.BACKGROUND_COLOR, Color.WHITE);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected final Graphic doCreateGraphic(final GraphicID id,
      final Dimension2D size, final ELength sizeUnit,
      final IGraphicListener listener) {
    final UserProperties up;
    final EMFGraphics2D g;
    final double wd, hd;
    final Dimension dim;
    final OutputStream os;

    up = new UserProperties();
    up.putAll(this.m_props);

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

    os = AbstractGraphicDriver.createOutputStream(id);
    synchronized (EMFGraphics2D.class) {
      g = new EMFGraphics2D(os, dim);
      g.setProperties(up);
      AbstractGraphicDriver.setDefaultRenderingHints(g);
      g.startExport();
      g.setClip(0, 0, dim.width, dim.height);
    }
    AbstractGraphicDriver.setDefaultRenderingHints(g);

    return new _FreeHEPEMFGraphic(g, id, listener, dim.width, dim.height);
  }

}
