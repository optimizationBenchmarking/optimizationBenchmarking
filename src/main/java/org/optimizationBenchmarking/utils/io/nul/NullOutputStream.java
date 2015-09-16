package org.optimizationBenchmarking.utils.io.nul;

import java.io.OutputStream;

/** An output stream that discards its output */
public final class NullOutputStream extends OutputStream {

  /** the null output stream */
  public static final NullOutputStream INSTANCE = new NullOutputStream();

  /** create */
  private NullOutputStream() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final int b) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final byte[] b) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final byte[] b, final int off, final int len) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    // do nothing
  }
}
