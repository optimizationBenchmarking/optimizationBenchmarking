package org.optimizationBenchmarking.utils.io.encoding;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * An unknown encoding.
 */
final class _UnknownBinaryEncoding extends
    StreamEncoding<InputStream, OutputStream> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  _UnknownBinaryEncoding() {
    super("binary", true); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final InputStream wrapInputStream(final InputStream input)
      throws IOException {
    return input;
  }

  /** {@inheritDoc} */
  @Override
  public final OutputStream wrapOutputStream(final OutputStream output)
      throws IOException {
    return output;
  }

  /** {@inheritDoc} */
  @Override
  public final InputStream wrapReader(final Reader input)
      throws IOException {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public final OutputStream wrapWriter(final Writer output)
      throws IOException {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<OutputStream> getOutputClass() {
    return OutputStream.class;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<InputStream> getInputClass() {
    return InputStream.class;
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  @Override
  final Object writeReplace() {
    return StreamEncoding.BINARY;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  @Override
  final Object readResolve() {
    return StreamEncoding.BINARY;
  }
}
