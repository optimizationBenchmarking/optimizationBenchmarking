package org.optimizationBenchmarking.utils.text.textOutput;

import java.io.Writer;

import org.optimizationBenchmarking.utils.io.NullWriter;

/**
 * A {@link java.io.Writer} wrapped around a
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 */
public final class TextOutputWriter extends Writer {

  /** the internal text output */
  private final ITextOutput m_out;

  /**
   * Create a wrapped text output
   *
   * @param out
   *          the output
   */
  protected TextOutputWriter(final ITextOutput out) {
    super(out);
    this.m_out = out;
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final int c) {
    this.m_out.append((char) c);
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final char cbuf[]) {
    this.m_out.append(cbuf);
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final char cbuf[], final int off, final int len) {
    this.m_out.append(cbuf, off, (off + len));
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final String str) {
    this.m_out.append(str);
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final String str, final int off, final int len) {
    this.m_out.append(str, off, (off + len));
  }

  /** {@inheritDoc} */
  @Override
  public final Writer append(final CharSequence csq) {
    this.m_out.append(csq);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final Writer append(final CharSequence csq, final int start,
      final int end) {
    this.m_out.append(csq, start, end);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final Writer append(final char c) {
    this.m_out.append(c);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() {
    this.m_out.flush();
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {//
  }

  /**
   * Wrap a given text output interface into a writer
   *
   * @param out
   *          the interface to be wrapped
   * @return the writer
   */
  @SuppressWarnings("rawtypes")
  public static final Writer wrap(final ITextOutput out) {
    final _AppendableWrappedTextOutputBase x;

    if ((out == null) || (out instanceof NullTextOutput)) {
      return NullWriter.INSTANCE;
    }
    if (out instanceof _AppendableWrappedTextOutputBase) {
      x = ((_AppendableWrappedTextOutputBase) out);
      if (x.m_out instanceof Writer) {
        return new _WriterWriter(((Writer) (x.m_out)));
      }
    }

    return new TextOutputWriter(out);
  }
}
