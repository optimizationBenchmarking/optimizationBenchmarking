package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooterCell;
import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;

/** a footer cell of a table in an export document */
final class _ExportTableFooterCell extends TableFooterCell {
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
  _ExportTableFooterCell(final _ExportTableFooterRow owner,
      final int cols, final int rows, final ETableCellDef[] def) {
    super(owner, cols, rows, def);
    this.open();
  }
}
