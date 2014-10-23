package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.awt.Color;
import java.awt.Dimension;
import java.io.OutputStream;
import java.nio.file.Path;

import org.freehep.graphicsio.emf.EMFGraphics2D;
import org.freehep.util.UserProperties;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.GraphicUtils;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * A driver which creates <a
 * href="http://en.wikipedia.org/wiki/Windows_Metafile">EMF</a> graphics.
 */
public final class FreeHEPEMFGraphicDriver extends AbstractGraphicDriver {
  /** the properties */
  private final org.freehep.util.UserProperties m_props;

  /** the globally shared instance of the emf graphic driver */
  public static final FreeHEPEMFGraphicDriver INSTANCE = new FreeHEPEMFGraphicDriver();

  /** the hidden constructor */
  private FreeHEPEMFGraphicDriver() {
    super("emf"); //$NON-NLS-1$

    this.m_props = new org.freehep.util.UserProperties();

    this.m_props.putAll(EMFGraphics2D.getDefaultProperties());
    this.m_props.setProperty(EMFGraphics2D.BACKGROUND_COLOR, Color.WHITE);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "FreeHEP-based EMF Driver"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final Graphic doCreateGraphic(final Path path,
      final OutputStream os, final PhysicalDimension size,
      final IObjectListener listener) {
    final UserProperties up;
    final EMFGraphics2D g;
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

    synchronized (EMFGraphics2D.class) {
      g = new EMFGraphics2D(os, dim);
      g.setProperties(up);
      GraphicUtils.setDefaultRenderingHints(g);
      g.startExport();
      g.setClip(0, 0, dim.width, dim.height);
    }
    GraphicUtils.setDefaultRenderingHints(g);

    return new _FreeHEPEMFGraphic(g, path, listener, dim.width, dim.height);
  }

}
