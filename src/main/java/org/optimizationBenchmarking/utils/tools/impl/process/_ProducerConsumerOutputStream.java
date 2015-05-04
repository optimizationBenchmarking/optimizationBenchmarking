package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.OutputStream;

import org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer;

/**
 * An output stream writing to a byte-based
 * {@link org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer
 * producer/consumer buffer}. If this stream is closed, it will also
 * {@link org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer#close()
 * close} the buffer.
 */
final class _ProducerConsumerOutputStream extends OutputStream {
  /** the buffer to write to */
  private final ByteProducerConsumerBuffer m_buffer;

  /**
   * Create the stream
   *
   * @param buffer
   *          the buffer to write to
   */
  _ProducerConsumerOutputStream(final ByteProducerConsumerBuffer buffer) {
    super();
    this.m_buffer = buffer;
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final int b) {
    this.m_buffer.writeToBuffer(new byte[] { ((byte) b) }, 0, 1);
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final byte[] b) {
    this.m_buffer.writeToBuffer(b, 0, b.length);
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final byte[] b, final int off, final int len) {
    this.m_buffer.writeToBuffer(b, off, len);
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    this.m_buffer.close();
  }
}
