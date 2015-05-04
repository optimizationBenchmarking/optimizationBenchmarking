package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.IOException;
import java.io.InputStream;

import org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer;

/**
 * An input stream reading from a byte-based
 * {@link org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer
 * producer/consumer buffer}. If this stream is closed, it will also
 * {@link org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer#close()
 * close} the buffer.
 */
final class _ProducerConsumerInputStream extends InputStream {

  /** the buffer to read from */
  private final ByteProducerConsumerBuffer m_buffer;

  /**
   * Create the stream
   *
   * @param buffer
   *          the buffer to read from
   */
  _ProducerConsumerInputStream(final ByteProducerConsumerBuffer buffer) {
    super();
    this.m_buffer = buffer;
  }

  /** {@inheritDoc} */
  @Override
  public final int read() {
    final byte[] b;
    final int r;

    b = new byte[1];
    r = this.m_buffer.readFromBuffer(b, 0, 1);
    if (r == 1) {
      return b[0];
    }
    if (r == (-1)) {
      return (-1);
    }
    throw new IllegalStateException(
        "Requested to read 1 byte, but got " + r); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final int read(final byte[] b) {
    return this.m_buffer.readFromBuffer(b, 0, b.length);
  }

  /** {@inheritDoc} */
  @Override
  public final int read(final byte[] b, final int off, final int len) {
    return this.m_buffer.readFromBuffer(b, off, len);
  }

  /** {@inheritDoc} */
  @Override
  public final long skip(final long n) {
    return this.m_buffer.deleteFromBuffer((int) (Math.min(
        Integer.MAX_VALUE, n)));
  }

  /** {@inheritDoc} */
  @Override
  public final int available() {
    return this.m_buffer.size();
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    this.m_buffer.close();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("sync-override")
  @Override
  public final void mark(final int readlimit) {
    // do nothing
  }

  /** {@inheritDoc} */
  @SuppressWarnings("sync-override")
  @Override
  public final void reset() throws IOException {
    throw new IOException("mark/reset not supported"); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final boolean markSupported() {
    return false;
  }

}
