package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.awt.Dimension;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.freehep.graphicsio.PageConstants;
import org.freehep.graphicsio.pdf.PDFGraphics2D;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

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
   * @param log
   *          the logger
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
  _FreeHEPPDFGraphic(final PDFGraphics2D graphic, final Logger log,
      final IFileProducerListener listener, final Path path, final int w,
      final int h) {
    super(graphic, log, listener, path, w, h);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doClose() {
    final Dimension mess;

    synchronized (PageConstants.class) {
      mess = FreeHEPPDFGraphicDriver.s_messWith;
      mess.setSize(this.m_w, this.m_h);
      try {
        try {
          this.m_out.endExport();
        } finally {
          super.doClose();
        }
      } finally {
        mess.setSize(FreeHEPPDFGraphicDriver.s_correctDim);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final EGraphicFormat getGraphicFormat() {
    return EGraphicFormat.PDF;
  }
}
