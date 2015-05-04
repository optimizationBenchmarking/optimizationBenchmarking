package org.optimizationBenchmarking.utils.text.textOutput;

import java.io.IOException;
import java.io.Writer;

/**
 * A {@link java.io.Writer} wrapped around a {@link java.io.Writer}
 */
final class _WriterWriter extends Writer {

  /** the internal text output */
  private final Writer m_out;

  /**
   * Create a wrapped text output
   *
   * @param out
   *          the output
   */
  _WriterWriter(final Writer out) {
    super(out);
    this.m_out = out;
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final int c) throws IOException {
    this.m_out.write(c);
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final char cbuf[]) throws IOException {
    this.m_out.write(cbuf);
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final char cbuf[], final int off, final int len)
      throws IOException {
    this.m_out.write(cbuf, off, len);
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final String str) throws IOException {
    this.m_out.write(str);
  }

  /** {@inheritDoc} */
  @Override
  public final void write(final String str, final int off, final int len)
      throws IOException {
    this.m_out.write(str, off, len);
  }

  /** {@inheritDoc} */
  @Override
  public final Writer append(final CharSequence csq) throws IOException {
    this.m_out.append(csq);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final Writer append(final CharSequence csq, final int start,
      final int end) throws IOException {
    this.m_out.append(csq, start, end);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final Writer append(final char c) throws IOException {
    this.m_out.append(c);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() throws IOException {
    this.m_out.flush();
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    // do nothing
  }

}
