package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.CodeBody;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the body of code object in a LaTeX document */
final class _LaTeXCodeBody extends CodeBody {

  /** the encoded code body */
  private ITextOutput m_codeOut;

  /**
   * create the code body
   * 
   * @param owner
   *          the owning FSM
   */
  _LaTeXCodeBody(final _LaTeXCode owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.m_codeOut = ((LaTeXDriver) (this.getDriver()))._encodeCode(
        this.getTextOutput(), this.encodedText());
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.m_codeOut = null;
    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final ITextOutput append(final CharSequence csq) {
    this.assertNoChildren();
    this.m_codeOut.append(csq);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final ITextOutput append(final CharSequence csq,
      final int start, final int end) {
    this.assertNoChildren();
    this.m_codeOut.append(csq, start, end);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final ITextOutput append(final char c) {
    this.assertNoChildren();
    this.m_codeOut.append(c);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final String s) {
    this.assertNoChildren();
    this.m_codeOut.append(s);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final String s, final int start,
      final int end) {
    this.assertNoChildren();
    this.m_codeOut.append(s, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final char[] chars) {
    this.assertNoChildren();
    this.m_codeOut.append(chars);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final char[] chars,
      final int start, final int end) {
    this.assertNoChildren();
    this.m_codeOut.append(chars, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final byte v) {
    this.assertNoChildren();
    this.m_codeOut.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final short v) {
    this.assertNoChildren();
    this.m_codeOut.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final int v) {
    this.assertNoChildren();
    this.m_codeOut.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final long v) {
    this.assertNoChildren();
    this.m_codeOut.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final float v) {
    this.assertNoChildren();
    this.m_codeOut.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final double v) {
    this.assertNoChildren();
    this.m_codeOut.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final boolean v) {
    this.assertNoChildren();
    this.m_codeOut.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void append(final Object o) {
    this.assertNoChildren();
    this.m_codeOut.append(o);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.m_codeOut.appendLineBreak();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendNonBreakingSpace() {
    this.assertNoChildren();
    this.m_codeOut.append(' ');
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized void flush() {
    this.m_codeOut.flush();
    super.flush();
  }

}
