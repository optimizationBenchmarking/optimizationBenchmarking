package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeaderCell;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;

/** a header cell of a table in a LaTeX document */
final class _LaTeXTableHeaderCell extends TableHeaderCell {
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
  _LaTeXTableHeaderCell(final _LaTeXTableHeaderRow owner, final int cols,
      final int rows, final TableCellDef[] def) {
    super(owner, cols, rows, def);
    this.open();
  }
}
