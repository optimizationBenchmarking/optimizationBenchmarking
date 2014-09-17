package org.optimizationBenchmarking.utils.graphics.graphic.drivers.freeHEP;

import java.nio.file.Path;

import org.freehep.graphicsio.ps.PSGraphics2D;
import org.optimizationBenchmarking.utils.document.IObjectListener;

/**
 * the internal <a
 * href="http://en.wikipedia.org/wiki/Encapsulated_PostScript">EPS</a>
 * graphic based on the <a
 * href="http://java.freehep.org/vectorgraphics">FreeHEP</a> library
 */
final class _FreeHEPEPSGraphic extends
    _FreeHEPAbstractVectorGraphicsProxy<PSGraphics2D> {

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
  _FreeHEPEPSGraphic(final PSGraphics2D graphic, final Path path,
      final IObjectListener listener, final int w, final int h) {
    super(graphic, path, listener, w, h);
  }

  /** {@inheritDoc} */
  @Override
  protected final void onClose() {
    try {
      this.m_out.endExport();
    } finally {
      super.onClose();
    }
  }
}
