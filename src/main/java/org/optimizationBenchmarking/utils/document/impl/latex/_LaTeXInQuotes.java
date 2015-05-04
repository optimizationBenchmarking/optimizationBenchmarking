package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.InQuotes;
import org.optimizationBenchmarking.utils.document.impl.abstr.PlainText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an in-quotes element of a section in a LaTeX document */
final class _LaTeXInQuotes extends InQuotes {
  /**
   * create the in-quotes element
   *
   * @param owner
   *          the owner
   */
  _LaTeXInQuotes(final PlainText owner) {
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
