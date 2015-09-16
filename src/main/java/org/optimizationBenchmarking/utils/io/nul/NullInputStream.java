package org.optimizationBenchmarking.utils.io.nul;

import java.io.InputStream;

/**
 * An input stream which does not provide any data. It will never throw any
 * exception.
 */
public final class NullInputStream extends InputStream {

  /** the null input stream */
  public static final NullInputStream INSTANCE = new NullInputStream();

  /** create */
  private NullInputStream() {
    super();
  }

  /**
   * {@inheritDoc}
   *
   * @return always {@code -1}
   */
  @Override
  public final int read() {
    return (-1);
  }

  /**
   * {@inheritDoc}
   *
   * @return always {@code -1}
   */
  @Override
  public final int read(final byte[] b) {
    return (-1);
  }

  /**
   * {@inheritDoc}
   *
   * @return always {@code -1}
   */
  @Override
  public final int read(final byte[] b, final int off, final int len) {
    return (-1);
  }

  /**
   * {@inheritDoc}
   *
   * @return always {@code Math.max(n, 0L)}
   */
  @Override
  public final long skip(final long n) {
    return Math.max(n, 0L);
  }

  /**
   * {@inheritDoc}
   *
   * @return always {@code 0}
   */
  @Override
  public final int available() {
    return 0;
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    // nothing
  }

  /** {@inheritDoc} */
  @SuppressWarnings("sync-override")
  @Override
  public final void mark(final int readlimit) {
    // nothing
  }

  /** {@inheritDoc} */
  @SuppressWarnings("sync-override")
  @Override
  public final void reset() {
    // nothing
  }

  /**
   * {@inheritDoc}
   *
   * @return always {@code true}
   */
  @Override
  public final boolean markSupported() {
    return true;
  }
}
