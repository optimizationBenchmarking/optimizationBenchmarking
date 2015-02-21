package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.ItemizationItem;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an itemization item in a LaTeX document */
final class _LaTeXItemizationItem extends ItemizationItem {
  /**
   * Create a new itemization item
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXItemizationItem(final _LaTeXItemization owner) {
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
