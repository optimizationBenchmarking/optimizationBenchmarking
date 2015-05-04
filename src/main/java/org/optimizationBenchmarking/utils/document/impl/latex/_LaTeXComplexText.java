package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentElement;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a complex text in a LaTeX document */
final class _LaTeXComplexText extends ComplexText {
  /**
   * create the complex text
   *
   * @param owner
   *          the owning element
   */
  _LaTeXComplexText(final DocumentElement owner) {
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
