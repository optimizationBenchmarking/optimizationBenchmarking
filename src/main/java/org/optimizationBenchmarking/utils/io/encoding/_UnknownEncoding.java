package org.optimizationBenchmarking.utils.io.encoding;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * An unknown encoding.
 */
final class _UnknownEncoding extends StreamEncoding<Closeable, Closeable> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  _UnknownEncoding() {
    super("unknown", true); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final Closeable wrapInputStream(final InputStream input)
      throws IOException {
    return input;
  }

  /** {@inheritDoc} */
  @Override
  public final Closeable wrapOutputStream(final OutputStream output)
      throws IOException {
    return output;
  }

  /** {@inheritDoc} */
  @Override
  public final Closeable wrapReader(final Reader input) throws IOException {
    return input;
  }

  /** {@inheritDoc} */
  @Override
  public final Closeable wrapWriter(final Writer output)
      throws IOException {
    return output;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Closeable> getOutputClass() {
    return Closeable.class;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Closeable> getInputClass() {
    return Closeable.class;
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  @Override
  final Object writeReplace() {
    return StreamEncoding.UNKNOWN;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  @Override
  final Object readResolve() {
    return StreamEncoding.UNKNOWN;
  }
}
