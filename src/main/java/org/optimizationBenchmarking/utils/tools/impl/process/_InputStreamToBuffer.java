package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer;

/** a thread shoveling data from an input stream to a buffer */
final class _InputStreamToBuffer extends _WorkerThread {

  /** the destination */
  private final ByteProducerConsumerBuffer m_dest;
  /** the source */
  private final InputStream m_source;

  /**
   * create
   * 
   * @param dest
   *          the destination
   * @param source
   *          the source
   * @param log
   *          the logger
   */
  _InputStreamToBuffer(final ByteProducerConsumerBuffer dest,
      final InputStream source, final Logger log) {
    super("InputStream-to-Buffer", log); //$NON-NLS-1$
    this.m_dest = dest;
    this.m_alive = true;
    this.m_source = source;
  }

  /** {@inheritDoc} */
  @Override
  public final void run() {
    final byte[] buffer;
    int s;

    buffer = new byte[4096];
    try {
      try {
        while (this.m_alive) {
          if (this.m_dest.isClosed()) {
            break;
          }
          s = this.m_source.read(buffer);
          if (s <= 0) {
            break;
          }
          this.m_dest.writeToBuffer(buffer, 0, s);
        }
      } finally {
        try {
          this.m_dest.close();
        } finally {
          this.m_source.close();
        }
      }
    } catch (final Throwable t) {
      if ((this.m_log != null) && (this.m_log.isLoggable(Level.SEVERE))) {
        this.m_log
            .log(
                Level.SEVERE,
                "Error during shoveling bytes from input stream of external process to byte buffer.", //$NON-NLS-1$
                t);
      }
      ErrorUtils.throwAsRuntimeException(t);
    }
  }
}
