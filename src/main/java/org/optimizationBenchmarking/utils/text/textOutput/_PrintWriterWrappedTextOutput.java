package org.optimizationBenchmarking.utils.text.textOutput;

import java.io.PrintWriter;

/**
 * A version of the text output wrapped around a
 * {@link java.io.PrintWriter}.
 */
final class _PrintWriterWrappedTextOutput extends
    _AppendableWrappedTextOutputBase<PrintWriter> {

  /**
   * Create a wrapped text output
   *
   * @param out
   *          the output
   */
  _PrintWriterWrappedTextOutput(final PrintWriter out) {
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
  public final AbstractTextOutput append(final CharSequence csq,
      final int start, final int end) {
    this.m_out.append(csq, start, end);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] chars) {
    this.m_out.write(chars);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s) {
    this.m_out.write(s);
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput append(final char c) {
    this.m_out.write(c);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() {
    this.m_out.flush();
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
  public final void appendLineBreak() {
    this.m_out.println();
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final byte v) {
    this.m_out.print(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final short v) {
    this.m_out.print(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final int v) {
    this.m_out.print(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long v) {
    this.m_out.print(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final float v) {
    this.m_out.print(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double v) {
    this.m_out.print(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final boolean v) {
    this.m_out.print(v);
  }
}
