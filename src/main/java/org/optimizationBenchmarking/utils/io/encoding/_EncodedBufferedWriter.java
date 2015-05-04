package org.optimizationBenchmarking.utils.io.encoding;

import java.io.BufferedWriter;
import java.io.Writer;

/** an encoded buffered reader */
final class _EncodedBufferedWriter extends BufferedWriter implements
    IStreamEncoded {

  /** the encoding */
  private final StreamEncoding<?, ?> m_encoding;

  /**
   * create
   *
   * @param r
   *          the writer
   * @param e
   *          the encoding
   */
  _EncodedBufferedWriter(final Writer r, final StreamEncoding<?, ?> e) {
    super(r);
    this.m_encoding = e;
  }

  /** {@inheritDoc} */
  @Override
  public final StreamEncoding<?, ?> getStreamEncoding() {
    return this.m_encoding;
  }
}
