package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.Table;
import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** a table in an export document */
final class _ExportTable extends Table {
  /**
   * Create a table
   *
   * @param owner
   *          the owning section body
   * @param index
   *          the table index in the owning section
   * @param useLabel
   *          the label to use
   * @param spansAllColumns
   *          does the table span all columns
   * @param definition
   *          the table cell definition
   */
  _ExportTable(final _ExportSectionBody owner, final ILabel useLabel,
      final boolean spansAllColumns, final int index,
      final ETableCellDef[] definition) {
    super(owner, useLabel, spansAllColumns, index, definition);
    this.open();
  }
}
