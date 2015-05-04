package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeaderRow;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a row of a header of a table in a LaTeX document */
final class _LaTeXTableHeaderRow extends TableHeaderRow {

  /** the row color for table headers and footers */
  static final char[] ROW_COLOR = { '\\', 'r', 'o', 'w', 'c', 'o', 'l',
      'o', 'r', '{', 't', 'a', 'b', 'l', 'e', 'H', 'e', 'a', 'd', 'e',
      'r', 'F', 'o', 'o', 't', 'e', 'r', 'B', 'a', 'c', 'k', 'g', 'r',
      'o', 'u', 'n', 'd', 'C', 'o', 'l', 'o', 'r', '}', };

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
