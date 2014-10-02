package org.optimizationBenchmarking.utils.graphics.graphic.drivers.freeHEP;

import java.nio.file.Path;

import org.freehep.graphicsio.svg.SVGGraphics2D;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;

/**
 * the internal <a
 * href="http://en.wikipedia.org/wiki/Scalable_Vector_Graphics">SVG</a>
 * (graphics)
 */
final class _FreeHEPSVGGraphic extends
    _FreeHEPAbstractVectorGraphicsProxy<SVGGraphics2D> {

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
  _FreeHEPSVGGraphic(final SVGGraphics2D graphic, final Path path,
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
    return EGraphicFormat.SVG;
  }
}
