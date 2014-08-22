package org.optimizationBenchmarking.utils.compiler;

import java.io.ByteArrayOutputStream;

/** the byte array output stream */
final class _BOS extends ByteArrayOutputStream {

  /** is the stream closed? */
  volatile boolean m_closed;

  /** create */
  _BOS() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    this.m_closed = true;
  }
}
