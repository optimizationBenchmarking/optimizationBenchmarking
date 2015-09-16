package org.optimizationBenchmarking.utils.io.nul;

import java.io.IOException;
import java.io.Reader;

/** A reader which reads nothing. It will never throw any exception. */
public final class NullReader extends Reader {

  /** the globally shared instance of the null reader */
  public static final NullReader INSTANCE = new NullReader();

  /** create */
  private NullReader() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public int read(final java.nio.CharBuffer target) {
    return (-1);
  }

  /** {@inheritDoc} */
  @Override
  public final int read() {
    return (-1);
  }

  /** {@inheritDoc} */
  @Override
  public final int read(final char cbuf[]) {
    return (-1);
  }

  /** {@inheritDoc} */
  @Override
  public final int read(final char cbuf[], final int off, final int len) {
    return (-1);
  }

  /** {@inheritDoc} */
  @Override
  public final long skip(final long n) throws IOException {
    if (n < 0L) {
      return super.skip(n);
    }
    return n;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean ready() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean markSupported() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final void mark(final int readAheadLimit) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void reset() {//
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    //
  }
}
