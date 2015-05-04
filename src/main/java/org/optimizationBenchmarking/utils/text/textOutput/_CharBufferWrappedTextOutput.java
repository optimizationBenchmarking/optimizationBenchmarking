package org.optimizationBenchmarking.utils.text.textOutput;

import java.nio.CharBuffer;

/**
 * A basic version of the text output wrapped around a
 * {@link java.nio.CharBuffer}.
 */
class _CharBufferWrappedTextOutput extends
    _AppendableWrappedTextOutputBase<CharBuffer> {

  /**
   * Create a wrapped text output
   *
   * @param out
   *          the output
   */
  _CharBufferWrappedTextOutput(final CharBuffer out) {
    super(out);
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput append(final CharSequence csq) {
    this.m_out.append(csq);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput append(final char c) {
    this.m_out.append(c);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput append(final CharSequence csq,
      final int start, final int end) {
    this.m_out.append(csq, start, end);
    return this;
  }

}
