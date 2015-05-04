package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * the internal <a
 * href="http://en.wikipedia.org/wiki/Windows_Metafile">EMF</a> graphic
 * based on the <a
 * href="http://java.freehep.org/vectorgraphics">FreeHEP</a> library
 */
final class _FreeHEPEMFGraphicWrapper extends
_FreeHEPAbstractVectorGraphicsProxy<_FreeHEPEMFGraphic> {

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
  _FreeHEPEMFGraphicWrapper(final _FreeHEPEMFGraphic graphic,
      final Logger log, final IFileProducerListener listener,
      final Path path, final int w, final int h) {
    super(graphic, log, listener, path, w, h);
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
    return EGraphicFormat.EMF;
  }
}
