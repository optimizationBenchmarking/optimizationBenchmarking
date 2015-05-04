package org.optimizationBenchmarking.utils.text.textOutput;

import java.io.CharArrayWriter;

/**
 * A version of the text output wrapped around an
 * {@link java.io.CharArrayWriter}.
 */
final class _CharArrayWriterWrappedTextOutput extends
    _AppendableWrappedTextOutputBase<CharArrayWriter> {

  /**
   * Create a wrapped text output
   *
   * @param out
   *          the output
   */
  _CharArrayWriterWrappedTextOutput(final CharArrayWriter out) {
    super(out);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] chars, final int start,
      final int end) {
    this.m_out.write(chars, start, (end - start));
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s, final int start, final int end) {
    this.m_out.write(s, start, (end - start));
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput append(final CharSequence csq) {
    this.m_out.append(csq);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput append(final CharSequence csq,
      final int start, final int end) {
    this.m_out.append(csq, start, end);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput append(final char c) {
    this.m_out.write(c);
    return this;
  }
}
