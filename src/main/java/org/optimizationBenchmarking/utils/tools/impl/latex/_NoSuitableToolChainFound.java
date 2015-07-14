package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.BasicCollection;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/** no suitable tool chain was found */
final class _NoSuitableToolChainFound extends LaTeXJob {

  /** the listener */
  private final IFileProducerListener m_listener;

  /**
   * Create the tool chain job
   *
   * @param listener
   *          the file producer listener
   * @param logger
   *          the logger
   */
  _NoSuitableToolChainFound(final IFileProducerListener listener,
      final Logger logger) {
    super(logger);
    this.m_listener = listener;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final Void call() {
    final Logger logger;

    logger = this.getLogger();
    if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
      logger.warning(//
          "No LaTeX tool chain was found which can deal with specified requirements, so the document cannot be compiled."); //$NON-NLS-1$
    }

    if (this.m_listener != null) {
      synchronized (this.m_listener) {
        this.m_listener.onFilesFinalized(//
            (Collection) (BasicCollection.EMPTY_COLLECTION));
      }
    }

    return null;
  }

  /**
   * Always returns {@code false}: This job does nothing.
   *
   * @return false
   */
  @Override
  public final boolean canCompileToPDF() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "No Suitable LaTeX Tool Chain Found"; //$NON-NLS-1$
  }
}
