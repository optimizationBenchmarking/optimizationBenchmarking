package org.optimizationBenchmarking.utils.graphics.drivers.pdf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.io.OutputStream;

import org.freehep.graphicsio.FontConstants;
import org.freehep.graphicsio.PageConstants;
import org.freehep.graphicsio.pdf.PDFGraphics2D;
import org.freehep.util.UserProperties;
import org.optimizationBenchmarking.utils.graphics.AbstractGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.Graphic;
import org.optimizationBenchmarking.utils.graphics.GraphicID;
import org.optimizationBenchmarking.utils.graphics.IGraphicListener;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * A driver which creates PDF graphics. This driver uses a very dirty hack
 * to enforce custom page sizes in the pdf output: It temporarily changes
 * the size of the
 * {@link org.freehep.graphicsio.PageConstants#INTERNATIONAL} page&hellip;
 * All using FreeHEP should thus synchronize on
 * <code>{@link org.freehep.graphicsio.PageConstants}.class</code> during
 * all code accessing page sizes.
 */
public class PDFGraphicDriver extends AbstractGraphicDriver {
  /** the properties */
  private final org.freehep.util.UserProperties m_props;

  /** the globally shared instance of the pdf graphic driver */
  public static final PDFGraphicDriver INSTANCE = new PDFGraphicDriver();

  /** the correct dimension to use */
  final Dimension m_correctDim;

  /** the dimension to temporarily destroy */
  final Dimension m_messWith;

  /** the hidden constructor */
  private PDFGraphicDriver() {
    super(".pdf"); //$NON-NLS-1$

    this.m_props = new org.freehep.util.UserProperties();

    PDFGraphics2D.setClipEnabled(true);

    this.m_props.putAll(PDFGraphics2D.getDefaultProperties());
    this.m_props.setProperty(PDFGraphics2D.EMBED_FONTS, true);
    this.m_props.setProperty(PDFGraphics2D.EMBED_FONTS_AS,
        FontConstants.EMBED_FONTS_TYPE3);
    this.m_props.setProperty(PDFGraphics2D.BACKGROUND_COLOR, Color.WHITE);
    this.m_props.setProperty(PDFGraphics2D.COMPRESS, true);
    this.m_props.setProperty(PDFGraphics2D.FIT_TO_PAGE, false);
    this.m_props.setProperty(PDFGraphics2D.VERSION,//
        PDFGraphics2D.VERSION6);
    this.m_props.setProperty(PDFGraphics2D.ORIENTATION,
        PageConstants.PORTRAIT);
    this.m_props.setProperty(PDFGraphics2D.PAGE_MARGINS, "0, 0, 0, 0"); //$NON-NLS-1$
    this.m_props.setProperty(PDFGraphics2D.PAGE_SIZE,
        PageConstants.INTERNATIONAL);

    synchronized (PageConstants.class) {
      this.m_messWith = PageConstants.getSize(PageConstants.INTERNATIONAL);
      this.m_correctDim = new Dimension(this.m_messWith.width,
          this.m_messWith.height);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected final Graphic doCreateGraphic(final GraphicID id,
      final Dimension2D size, final ELength sizeUnit,
      final IGraphicListener listener) {
    final UserProperties up;
    final PDFGraphics2D g;
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
    synchronized (PageConstants.class) {
      this.m_messWith.setSize(dim);
      try {
        synchronized (PDFGraphics2D.class) {
          PDFGraphics2D.setClipEnabled(true);

          g = new PDFGraphics2D(os, dim);
          g.setProperties(up);
          g.setMultiPage(false);

          g.startExport();
          g.setClip(0, 0, dim.width, dim.height);
        }
      } finally {
        this.m_messWith.setSize(this.m_correctDim);
      }
    }

    return new _PDFGraphic(g, id, listener, dim.width, dim.height);
  }

}
