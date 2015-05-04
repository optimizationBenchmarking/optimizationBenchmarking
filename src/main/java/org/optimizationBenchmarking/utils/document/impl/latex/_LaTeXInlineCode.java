package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.InlineCode;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** an inline code element of a section in a LaTeX document */
final class _LaTeXInlineCode extends InlineCode {

  /** the listing begin */
  private static final char[] BEGIN_INLINE_LISTING = { '\\', 'l', 's',
    't', 'i', 'n', 'l', 'i', 'n', 'e' };

  /** the body */
  private final MemoryTextOutput m_body;

  /**
   * create the inline code element
   *
   * @param owner
   *          the owner
   */
  _LaTeXInlineCode(final ComplexText owner) {
    super(owner);
    this.m_body = new MemoryTextOutput();
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean mustChildBeBuffered(final HierarchicalText child) {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  protected final void processBufferedOutputFromChild(
      final HierarchicalText child, final MemoryTextOutput out) {
    this.m_body.append(out);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final ITextOutput append(final CharSequence csq) {
    this.assertNoChildren();
    this.m_body.append(csq);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final ITextOutput append(final CharSequence csq,
      final int start, final int end) {
    this.assertNoChildren();
    this.m_body.append(csq, start, end);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final ITextOutput append(final char c) {
    this.assertNoChildren();
    this.m_body.append(c);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final String s) {
    this.assertNoChildren();
    this.m_body.append(s);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final String s, final int start,
      final int end) {
    this.assertNoChildren();
    this.m_body.append(s, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final char[] chars) {
    this.assertNoChildren();
    this.m_body.append(chars);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final char[] chars,
      final int start, final int end) {
    this.assertNoChildren();
    this.m_body.append(chars, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final byte v) {
    this.assertNoChildren();
    this.m_body.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final short v) {
    this.assertNoChildren();
    this.m_body.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final int v) {
    this.assertNoChildren();
    this.m_body.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final long v) {
    this.assertNoChildren();
    this.m_body.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final float v) {
    this.assertNoChildren();
    this.m_body.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final double v) {
    this.assertNoChildren();
    this.m_body.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final boolean v) {
    this.assertNoChildren();
    this.m_body.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final Object o) {
    this.assertNoChildren();
    this.m_body.append(o);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendNonBreakingSpace() {
    this.assertNoChildren();
    this.m_body.append(' ');
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized void flush() {
    this.m_body.flush();
    super.flush();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(' ');
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();

    ((LaTeXDocument) (this.getDocument()))._registerCode();
    this.getTextOutput().append(_LaTeXInlineCode.BEGIN_INLINE_LISTING);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final MemoryTextOutput body;
    final ITextOutput out;
    char delim;

    body = this.m_body;

    findDelim: for (delim = 33; delim <= 127; delim++) {
      switch (delim) {
        case '[':
        case '%':
        case '{':
        case '}': {
          continue findDelim;
        }
        default: {
          if (Character.isAlphabetic(delim) || //
              Character.isWhitespace(delim)) {
            continue findDelim;
          }
        }
      }

      if (!(body.contains(delim))) {
        break;
      }
    }

    if (delim >= 128) {
      throw new IllegalArgumentException(//
          "The inline code '" //$NON-NLS-1$
          + this.m_body + //
          "' cannot be included in the LaTeX document as it includes all characters which could be used as delimiters."); //$NON-NLS-1$
    }

    out = this.getTextOutput();
    out.append(delim);
    out.append(body);
    out.append(delim);

    super.onClose();
  }
}
