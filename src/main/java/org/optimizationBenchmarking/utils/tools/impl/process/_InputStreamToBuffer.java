package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.InputStream;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer;

/**
 * A thread shoveling data from an {@link java.io.InputStream} to a
 * {@link org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer
 * buffer} as long as <code>{@link #m_mode}=0</code>. As soon as
 * <code>{@link #m_mode}=1</code>, the data from the input stream will be
 * {@link java.io.InputStream#skip(long) skipped} over. If
 * <code>{@link #m_mode}=2</code>, all activity is ceased.
 */
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
    this.m_source = source;
  }

  /** {@inheritDoc} */
  @Override
  public final void run() {
    byte[] buffer;
    int s;

    try {
      try {
        try {
          buffer = new byte[4096];
          while (this.m_mode < 2) {
            if (this.m_dest.isClosed()) {
              break;
            }
            s = this.m_source.read(buffer);
            if (s <= 0) {
              break;
            }
            if (this.m_mode < 1) {
              this.m_dest.writeToBuffer(buffer, 0, s);
            }
          }
          buffer = null;
        } finally {
          this.m_dest.close();
        }
      } finally {
        this.m_source.close();
      }
    } catch (final Throwable t) {
      ErrorUtils
          .logError(
              this.m_log,
              "Error during shoveling bytes from external process to byte buffer.",//$NON-NLS-1$
              t, true, RethrowMode.AS_RUNTIME_EXCEPTION);
    }
  }
}
