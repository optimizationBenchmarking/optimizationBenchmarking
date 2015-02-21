package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.EnumerationItem;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an enumeration item in a LaTeX document */
final class _LaTeXEnumerationItem extends EnumerationItem {
  /**
   * Create a new enumeration item
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXEnumerationItem(final _LaTeXEnumeration owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;

    super.onOpen();

    out = this.getTextOutput();
    out.append(LaTeXDriver.ITEM);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    LaTeXDriver._endLine(this.getTextOutput());
    super.onClose();
  }
}
