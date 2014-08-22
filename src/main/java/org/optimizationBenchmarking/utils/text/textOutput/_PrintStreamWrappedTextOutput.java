package org.optimizationBenchmarking.utils.text.textOutput;

import java.io.PrintStream;

import org.optimizationBenchmarking.utils.text.CharArrayCharSequence;

/**
 * A version of the text output wrapped around a
 * {@link java.io.PrintStream}.
 */
final class _PrintStreamWrappedTextOutput extends
    _AppendableWrappedTextOutputBase<PrintStream> {

  /**
   * Create a wrapped text output
   * 
   * @param out
   *          the output
   */
  _PrintStreamWrappedTextOutput(final PrintStream out) {
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
  public final AbstractTextOutput append(final char c) {
    this.m_out.write(c);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] chars) {
    this.m_out.print(chars);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s) {
    this.m_out.print(s);
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
    if ((start <= 0) && (end >= chars.length)) {
      this.m_out.print(chars);
    } else {
      if (start < end) {
        this.m_out.append(new CharArrayCharSequence(chars, start,
            (end - start)));
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s, final int start, final int end) {
    this.m_out.append(s, start, end);
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
