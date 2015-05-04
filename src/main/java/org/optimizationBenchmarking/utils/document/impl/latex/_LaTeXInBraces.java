package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.InBraces;
import org.optimizationBenchmarking.utils.document.impl.abstr.PlainText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an in-braces element of a section in a LaTeX document */
final class _LaTeXInBraces extends InBraces {
  /**
   * create the in-braces element
   *
   * @param owner
   *          the owner
   */
  _LaTeXInBraces(final PlainText owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    final ITextOutput textOut;

    this.assertNoChildren();
    textOut = this.getTextOutput();
    textOut.appendLineBreak();
    textOut.appendLineBreak();
  }
}
