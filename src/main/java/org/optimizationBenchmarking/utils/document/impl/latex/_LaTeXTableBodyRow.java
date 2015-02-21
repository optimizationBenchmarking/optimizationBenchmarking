package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableBodyRow;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a row of a body of a table in a LaTeX document */
final class _LaTeXTableBodyRow extends TableBodyRow {
  /**
   * Create a row of a body of a table
   * 
   * @param owner
   *          the owning table body
   */
  _LaTeXTableBodyRow(final _LaTeXTableBody owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    // final ITextOutput out;

    super.onOpen();

    // out = this.getTextOutput();

    // TODO: color even rows
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
