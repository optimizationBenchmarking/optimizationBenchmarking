package org.optimizationBenchmarking.utils.io.nul;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * A buffered reader which reads nothing. It will never throw any
 * exception.
 */
public final class NullBufferedReader extends BufferedReader {

  /** the globally shared instance of the null reader */
  public static final NullBufferedReader INSTANCE = new NullBufferedReader();

  /** create */
  private NullBufferedReader() {
    super(NullReader.INSTANCE, 1);
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
      return NullReader.INSTANCE.skip(n);
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
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final void mark(final int readAheadLimit) throws IOException {
    NullReader.INSTANCE.mark(readAheadLimit);
  }

  /** {@inheritDoc} */
  @Override
  public final void reset() throws IOException {
    NullReader.INSTANCE.reset();
  }

  /** {@inheritDoc} */
  @Override
  public final void close() { //
  }

  /** {@inheritDoc} */
  @Override
  public final String readLine() {
    return null;
  }
}
