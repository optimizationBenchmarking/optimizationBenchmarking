package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;

/** a thread shoveling data from an input stream to the nirvana */
final class _DiscardInputStream extends _WorkerThread {

  /** the source */
  private final InputStream m_source;

  /**
   * create
   * 
   * @param source
   *          the source
   * @param log
   *          the logger
   */
  _DiscardInputStream(final InputStream source, final Logger log) {
    super("Discard-InputStream", log); //$NON-NLS-1$
    this.m_source = source;
  }

  /** {@inheritDoc} */
  @Override
  public final void run() {
    long s;

    try {
      try {
        while (this.m_alive) {
          s = this.m_source.skip(0x1ffffff0L);
          if (s <= 0L) {
            if (this.m_source.read() < 0) {// check for end-of-stream
              break;
            }
          }
        }
      } finally {
        this.m_source.close();
      }
    } catch (final Throwable t) {
      if ((this.m_log != null) && (this.m_log.isLoggable(Level.SEVERE))) {
        this.m_log.log(Level.SEVERE,
            "Error during discarding input stream (by skipping).", //$NON-NLS-1$
            t);
      }
      ErrorUtils.throwAsRuntimeException(t);
    }
  }
}
