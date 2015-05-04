package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * the internal <a
 * href="http://en.wikipedia.org/wiki/Scalable_Vector_Graphics">SVG</a>
 * (graphics)
 */
final class _FreeHEPSVGGraphicWrapper extends
    _FreeHEPAbstractVectorGraphicsProxy<_FreeHEPSVGGraphic> {

  /** the graphic format */
  private final EGraphicFormat m_format;

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
   * @param format
   *          the graphics format
   */
  _FreeHEPSVGGraphicWrapper(final _FreeHEPSVGGraphic graphic,
      final Logger log, final IFileProducerListener listener,
      final Path path, final int w, final int h,
      final EGraphicFormat format) {
    super(graphic, log, listener, path, w, h);
    this.m_format = format;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doClose() {
    try {
      this.m_out.endExport();
    } finally {
      super.doClose();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final EGraphicFormat getGraphicFormat() {
    return this.m_format;
  }
}
