package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooterCell;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;

/** a footer cell of a table in a XHTML document */
final class _XHTML10TableFooterCell extends TableFooterCell {
  /**
   * Create a footer cell of a table
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
  _XHTML10TableFooterCell(final _XHTML10TableFooterRow owner,
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
