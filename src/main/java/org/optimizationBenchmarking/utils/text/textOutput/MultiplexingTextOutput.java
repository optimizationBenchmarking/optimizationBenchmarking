package org.optimizationBenchmarking.utils.text.textOutput;

import java.io.Reader;
import java.io.Writer;

import org.optimizationBenchmarking.utils.io.encoding.IStreamEncoded;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;

/** A text output device multiplexing its output to two other devices */
public final class MultiplexingTextOutput extends AbstractTextOutput {

  /** the first text output to multiplex to */
  private final ITextOutput m_a;
  /** the second text output to multiplex to */
  private final ITextOutput m_b;

  /**
   * Create the multiplexing text output
   *
   * @param a
   *          the first text output to multiplex to
   * @param b
   *          the second text output to multiplex to
   */
  public MultiplexingTextOutput(final ITextOutput a, final ITextOutput b) {
    super();
    if (a == null) {
      throw new IllegalArgumentException(//
          "First text output device to multiplex to cannot be null."); //$NON-NLS-1$
    }
    if (b == null) {
      throw new IllegalArgumentException(//
          "Second text output device to multiplex to cannot be null."); //$NON-NLS-1$
    }
    this.m_a = a;
    this.m_b = b;
  }

  /** {@inheritDoc} */
  @Override
  public final MultiplexingTextOutput append(final CharSequence csq) {
    this.m_a.append(csq);
    this.m_b.append(csq);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final MultiplexingTextOutput append(final CharSequence csq,
      final int start, final int end) {
    this.m_a.append(csq, start, end);
    this.m_b.append(csq, start, end);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final MultiplexingTextOutput append(final char c) {
    this.m_a.append(c);
    this.m_b.append(c);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s) {
    this.m_a.append(s);
    this.m_b.append(s);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s, final int start, final int end) {
    this.m_a.append(s, start, end);
    this.m_b.append(s, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] chars) {
    this.m_a.append(chars);
    this.m_b.append(chars);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] chars, final int start,
      final int end) {
    this.m_a.append(chars, start, end);
    this.m_b.append(chars, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final byte v) {
    this.m_a.append(v);
    this.m_b.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final short v) {
    this.m_a.append(v);
    this.m_b.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final int v) {
    this.m_a.append(v);
    this.m_b.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long v) {
    this.m_a.append(v);
    this.m_b.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final float v) {
    this.m_a.append(v);
    this.m_b.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double v) {
    this.m_a.append(v);
    this.m_b.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final boolean v) {
    this.m_a.append(v);
    this.m_b.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final Object o) {
    this.m_a.append(o);
    this.m_b.append(o);
  }

  /** {@inheritDoc} */
  @Override
  public final void appendLineBreak() {
    this.m_a.appendLineBreak();
    this.m_b.appendLineBreak();
  }

  /** {@inheritDoc} */
  @Override
  public final void appendNonBreakingSpace() {
    this.m_a.appendNonBreakingSpace();
    this.m_b.appendNonBreakingSpace();
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() {//
    this.m_a.flush();
    this.m_b.flush();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final StreamEncoding<? extends Reader, ? extends Writer> getStreamEncoding() {
    final StreamEncoding a, b;

    if (this.m_a instanceof IStreamEncoded) {
      a = ((IStreamEncoded) (this.m_a)).getStreamEncoding();
    } else {
      a = null;
    }
    if (this.m_b instanceof IStreamEncoded) {
      b = ((IStreamEncoded) (this.m_b)).getStreamEncoding();
    } else {
      b = null;
    }

    if ((a != null) && (a == b)) {
      return a;
    }

    return super.getStreamEncoding();
  }
}
