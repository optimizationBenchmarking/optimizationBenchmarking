package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooterRow;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a row of a footer of a table in a LaTeX document */
final class _LaTeXTableFooterRow extends TableFooterRow {
  /**
   * Create a row of a footer of a table
   *
   * @param owner
   *          the owning table footer
   */
  _LaTeXTableFooterRow(final _LaTeXTableFooter owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;

    super.onOpen();

    out = this.getTextOutput();

    out.append(_LaTeXTableHeaderRow.ROW_COLOR);
    LaTeXDriver._endLine(out);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    out.append('\\');
    out.append('\\');
    LaTeXDriver._endLine(out);

    super.onClose();
  }
}
