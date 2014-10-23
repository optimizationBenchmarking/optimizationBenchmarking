package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.nio.file.Path;

import org.freehep.graphicsio.emf.EMFGraphics2D;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;

/**
 * the internal <a
 * href="http://en.wikipedia.org/wiki/Windows_Metafile">EMF</a> graphic
 * based on the <a
 * href="http://java.freehep.org/vectorgraphics">FreeHEP</a> library
 */
final class _FreeHEPEMFGraphic extends
    _FreeHEPAbstractVectorGraphicsProxy<EMFGraphics2D> {

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
  _FreeHEPEMFGraphic(final EMFGraphics2D graphic, final Path path,
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

  /** {@inheritDoc} */
  @Override
  protected final Object getPathEntryObjectID() {
    return EGraphicFormat.EMF;
  }
}
