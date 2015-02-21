package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeaderRow;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a row of a header of a table in a LaTeX document */
final class _LaTeXTableHeaderRow extends TableHeaderRow {
  /**
   * Create a row of a header of a table
   * 
   * @param owner
   *          the owning table header
   */
  _LaTeXTableHeaderRow(final _LaTeXTableHeader owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();

    out.append('\\');
    out.append('\\');
    LaTeXDriver._endLine(out);

    out.append(_LaTeXTable.HLINE);
    LaTeXDriver._endLine(out);

    super.onClose();
  }
}
