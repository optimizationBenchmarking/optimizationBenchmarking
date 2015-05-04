package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.CodeCaption;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the caption of code object in a LaTeX document */
final class _LaTeXCodeCaption extends CodeCaption {

  /** the float caption */
  private static final char[] FLOAT_CAPTION = { ',', 'c', 'a', 'p', 't',
      'i', 'o', 'n', '=', '{' };

  /**
   * create the code caption
   *
   * @param owner
   *          the owning FSM
   */
  _LaTeXCodeCaption(final _LaTeXCode owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(_LaTeXCodeCaption.FLOAT_CAPTION);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(' ');
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    out.append('}');
    out.append(']');
    out.appendLineBreak();

    super.onClose();
  }
}
