package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Emphasize;
import org.optimizationBenchmarking.utils.document.impl.abstr.PlainText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an emphasize element of in a LaTeX document */
final class _LaTeXEmphasize extends Emphasize {
  /** the emph command in text */
  private static final char[] EMPH_BEGIN = _LaTeXStyledText.EMPH_BEGIN;

  /**
   * create the emphasize element
   *
   * @param owner
   *          the owner
   */
  _LaTeXEmphasize(final PlainText owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    final ITextOutput out;

    this.assertNoChildren();

    out = this.getTextOutput();
    out.appendLineBreak();
    out.appendLineBreak();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();

    this.getTextOutput().append(_LaTeXEmphasize.EMPH_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    out.append('}');
    out.append('}');

    super.onClose();
  }
}
