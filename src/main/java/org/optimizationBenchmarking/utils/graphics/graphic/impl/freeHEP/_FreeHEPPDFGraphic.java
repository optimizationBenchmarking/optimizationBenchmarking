package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.awt.Dimension;
import java.nio.file.Path;

import org.freehep.graphicsio.PageConstants;
import org.freehep.graphicsio.pdf.PDFGraphics2D;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;

/**
 * the internal <a
 * href="http://en.wikipedia.org/wiki/Portable_Document_Format">PDF</a>
 * graphic
 */
final class _FreeHEPPDFGraphic extends
    _FreeHEPAbstractVectorGraphicsProxy<PDFGraphics2D> {

  /**
   * instantiate
   * 
   * @param graphic
   *          the graphic to use
   * @param path
   *          the path under which the contents of the graphic are stored
   * @param listener
   *          the object to notify when we are closed, or {@code null} if
   *          none needs to be notified
   * @param w
   *          the width
   * @param h
   *          the height
   */
  _FreeHEPPDFGraphic(final PDFGraphics2D graphic, final Path path,
      final IObjectListener listener, final int w, final int h) {
    super(graphic, path, listener, w, h);
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

  /** {@inheritDoc} */
  @Override
  protected final Object getPathEntryObjectID() {
    return EGraphicFormat.PDF;
  }
}
