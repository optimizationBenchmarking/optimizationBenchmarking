package org.optimizationBenchmarking.utils.graphics.drivers.freeHEP;

import java.awt.Dimension;

import org.freehep.graphicsio.PageConstants;
import org.freehep.graphicsio.pdf.PDFGraphics2D;
import org.optimizationBenchmarking.utils.graphics.GraphicID;
import org.optimizationBenchmarking.utils.graphics.IGraphicListener;

/** the internal pdf graphic */
final class _FreeHEPPDFGraphic extends
    _FreeHEPAbstractVectorGraphicsProxy<PDFGraphics2D> {

  /**
   * instantiate
   * 
   * @param graphic
   *          the graphic to use
   * @param id
   *          the graphic id identifying this graphic and the path under
   *          which the contents of the graphic are stored
   * @param listener
   *          the object to notify when we are closed, or {@code null} if
   *          none needs to be notified
   * @param w
   *          the width
   * @param h
   *          the height
   */
  _FreeHEPPDFGraphic(final PDFGraphics2D graphic, final GraphicID id,
      final IGraphicListener listener, final int w, final int h) {
    super(graphic, id, listener, w, h);
  }

  /** {@inheritDoc} */
  @Override
  protected final void onClose() {
    final Dimension mess;

    synchronized (PageConstants.class) {
      mess = FreeHEPPDFGraphicDriver.INSTANCE.m_messWith;
      mess.setSize(this.m_w, this.m_h);
      try {
        try {
          this.m_out.endExport();
        } finally {
          super.onClose();
        }
      } finally {
        mess.setSize(FreeHEPPDFGraphicDriver.INSTANCE.m_correctDim);
      }
    }
  }
}
