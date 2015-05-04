package org.optimizationBenchmarking.utils.io.encoding;

import java.io.BufferedReader;
import java.io.Reader;

/** an encoded buffered reader */
final class _EncodedBufferedReader extends BufferedReader implements
    IStreamEncoded {

  /** the encoding */
  private final StreamEncoding<?, ?> m_encoding;

  /**
   * create
   *
   * @param r
   *          the reader
   * @param e
   *          the encoding
   */
  _EncodedBufferedReader(final Reader r, final StreamEncoding<?, ?> e) {
    super(r);
    this.m_encoding = e;
  }

  /** {@inheritDoc} */
  @Override
  public final StreamEncoding<?, ?> getStreamEncoding() {
    return this.m_encoding;
  }
}
