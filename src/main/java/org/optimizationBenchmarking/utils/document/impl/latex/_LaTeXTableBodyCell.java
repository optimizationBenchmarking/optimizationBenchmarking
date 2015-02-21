package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableBodyCell;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;

/** a body cell of a table in a LaTeX document */
final class _LaTeXTableBodyCell extends TableBodyCell {
  /**
   * Create a body cell of a table
   * 
   * @param owner
   *          the owning row
   * @param cols
   *          the number of columns occupied by the cell
   * @param rows
   *          the number of rows occupied by the cell
   * @param def
   *          the cell definition
   */
  _LaTeXTableBodyCell(final _LaTeXTableBodyRow owner, final int cols,
      final int rows, final TableCellDef[] def) {
    super(owner, cols, rows, def);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    boolean b;

    super.onOpen();

    if (this.hasIrregularDefinition()) {
      b = false;
      if (this.getRowSpan() > 1) {
        b = true;
        ((_LaTeXDocument) (this.getDocument()))._registerMultiRowCell();
      }
      if ((!b) || (this.getColumnSpan() > 1)) {
        ((_LaTeXDocument) (this.getDocument()))._registerMultiColCell();
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(' ');
  }
}
