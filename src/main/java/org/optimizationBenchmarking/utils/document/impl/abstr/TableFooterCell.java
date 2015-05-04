package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;

/**
 * A cell in a table footer
 */
public class TableFooterCell extends TableCell {

  /**
   * Create the table footer cell
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
  protected TableFooterCell(final TableFooterRow owner, final int cols,
      final int rows, final ETableCellDef[] def) {
    super(owner, cols, rows, def);
  }

  /** {@inheritDoc} */
  @Override
  protected TableFooterRow getOwner() {
    return ((TableFooterRow) (super.getOwner()));
  }
}
