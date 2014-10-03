package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeaderCell;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;

/** a header cell of a table in a XHTML document */
final class _XHTML10TableHeaderCell extends TableHeaderCell {
  /**
   * Create a header cell of a table
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
  _XHTML10TableHeaderCell(final _XHTML10TableHeaderRow owner,
      final int cols, final int rows, final TableCellDef[] def) {
    super(owner, cols, rows, def);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(XHTML10Driver.BR);
  }
}
