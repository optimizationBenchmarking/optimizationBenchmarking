package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.SectionBody;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a section body of a section in a LaTeX document */
final class _LaTeXSectionBody extends SectionBody {
  /**
   * create the section body
   *
   * @param owner
   *          the owner
   */
  _LaTeXSectionBody(final _LaTeXSection owner) {
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
