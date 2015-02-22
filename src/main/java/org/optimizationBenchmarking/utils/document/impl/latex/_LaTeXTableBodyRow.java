package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableBodyRow;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a row of a body of a table in a LaTeX document */
final class _LaTeXTableBodyRow extends TableBodyRow {

  /** the row color */
  private static final char[] ROW_COLOR = { '\\', 'r', 'o', 'w', 'c', 'o',
      'l', 'o', 'r', '{', 't', 'a', 'b', 'l', 'e', 'B', 'o', 'd', 'y',
      'E', 'v', 'e', 'n', 'R', 'o', 'w', 'B', 'a', 'c', 'k', 'g', 'r',
      'o', 'u', 'n', 'd', 'C', 'o', 'l', 'o', 'r', '}', };

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
    final ITextOutput out;

    super.onOpen();

    if ((this.getIndex() & 1) == 0) {
      out = this.getTextOutput();
      out.append(_LaTeXTableBodyRow.ROW_COLOR);
      LaTeXDriver._endLine(out);
    }
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
