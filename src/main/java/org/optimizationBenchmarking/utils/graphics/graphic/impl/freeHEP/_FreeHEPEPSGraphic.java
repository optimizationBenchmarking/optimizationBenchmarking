package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.awt.Dimension;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.freehep.graphicsio.ps.PSGraphics2D;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;

/** The internal wrapper for freeHEP EPS graphics */
final class _FreeHEPEPSGraphic extends PSGraphics2D {

  /** the logger */
  private final Logger m_logger;

  /**
   * Create the wrapper
   *
   * @param _os
   *          the output stream
   * @param size
   *          the size
   * @param logger
   *          the logger
   */
  _FreeHEPEPSGraphic(final OutputStream _os, final Dimension size,
      final Logger logger) {
    super(_os, size);
    this.m_logger = logger;
  }

  /** {@inheritDoc} */
  @Override
  public final void writeComment(final String comment) {
    // ignore
  }

  /** {@inheritDoc} */
  @Override
  public final void writeError(final Exception exception) {
    ErrorUtils.logError(this.m_logger,//
        "Error while creating EPS graphic.", //$NON-NLS-1$
        exception, true, RethrowMode.AS_RUNTIME_EXCEPTION);
  }

  /** {@inheritDoc} */
  @Override
  public final void writeWarning(final String warning) {
    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.WARNING))) {
      this.m_logger.warning(warning);
    }
  }
}
