package org.optimizationBenchmarking.utils.io.encoding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * An unknown text encoding.
 */
final class _UnknownTextEncoding extends
    StreamEncoding<BufferedReader, BufferedWriter> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  _UnknownTextEncoding() {
    super("text", true); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final BufferedReader wrapInputStream(final InputStream input)
      throws IOException {
    return new BufferedReader(new InputStreamReader(input));
  }

  /** {@inheritDoc} */
  @Override
  public final BufferedWriter wrapOutputStream(final OutputStream output)
      throws IOException {
    return new BufferedWriter(new OutputStreamWriter(output));
  }

  /** {@inheritDoc} */
  @Override
  public final BufferedReader wrapReader(final Reader input)
      throws IOException {
    return _UnknownTextEncoding._wrapReader(input);
  }

  /** {@inheritDoc} */
  @Override
  public final BufferedWriter wrapWriter(final Writer output)
      throws IOException {
    return _UnknownTextEncoding._wrapWriter(output);
  }

  /**
   * Wrap a reader into a stream representing this encoding
   *
   * @param input
   *          the reader to wrap
   * @return the wrapped stream
   */
  static final BufferedReader _wrapReader(final Reader input) {
    StreamEncoding<?, ?> e;

    if (input instanceof BufferedReader) {
      return ((BufferedReader) input);
    }

    e = StreamEncoding.getStreamEncoding(input);
    if ((e != null) && (e != StreamEncoding.UNKNOWN)) {
      return new _EncodedBufferedReader(input, e);
    }

    return new BufferedReader(input);
  }

  /**
   * Wrap a writer into a stream representing this encoding
   *
   * @param output
   *          the writer to wrap
   * @return the wrapped stream
   */
  static final BufferedWriter _wrapWriter(final Writer output) {
    StreamEncoding<?, ?> e;

    if (output instanceof BufferedWriter) {
      return ((BufferedWriter) output);
    }

    e = StreamEncoding.getStreamEncoding(output);
    if ((e != null) && (e != StreamEncoding.UNKNOWN)) {
      return new _EncodedBufferedWriter(output, e);
    }

    return new BufferedWriter(output);
  }

  /** {@inheritDoc} */
  @Override
  public final Class<BufferedWriter> getOutputClass() {
    return BufferedWriter.class;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<BufferedReader> getInputClass() {
    return BufferedReader.class;
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  @Override
  final Object writeReplace() {
    return StreamEncoding.TEXT;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  @Override
  final Object readResolve() {
    return StreamEncoding.TEXT;
  }
}
