package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.OutputStream;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer;

/**
 * A thread shoveling data from a
 * {@link org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer
 * buffer} to an {@link java.io.OutputStream} as long as
 * <code>{@link #m_mode}&le;1</code>. As soon as
 * <code>{@link #m_mode}==2</code>, it will cease all activity.
 */
final class _BufferToOutputStream extends _WorkerThread {

  /** the source */
  private final ByteProducerConsumerBuffer m_source;
  /** the destination */
  private final OutputStream m_dest;

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
  _BufferToOutputStream(final OutputStream dest,
      final ByteProducerConsumerBuffer source, final Logger log) {
    super("Buffer-to-OutputStream", log); //$NON-NLS-1$
    this.m_dest = dest;
    this.m_source = source;
  }

  /** {@inheritDoc} */
  @Override
  public final void run() {
    byte[] buffer;
    int s;

    buffer = new byte[4096];
    try {
      try {
        while (this.m_mode < 2) {
          if (this.m_source.isClosed()) {
            break;
          }
          s = this.m_source.readFromBuffer(buffer, 0, buffer.length);
          if (s <= 0) {
            break;
          }
          this.m_dest.write(buffer, 0, s);
          this.m_dest.flush();
        }
      } finally {
        try {
          this.m_dest.close();
        } finally {
          this.m_source.close();
        }
      }
    } catch (final Throwable t) {
      ErrorUtils
          .logError(
              this.m_log,
              "Error during shoveling bytes from byte buffer to external process.", //$NON-NLS-1$
              t, true, RethrowMode.AS_RUNTIME_EXCEPTION);
    }
  }
}
