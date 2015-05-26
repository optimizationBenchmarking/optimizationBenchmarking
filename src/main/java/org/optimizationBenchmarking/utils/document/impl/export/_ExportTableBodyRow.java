package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableBodyRow;

/** a row of a body of a table in an export document */
final class _ExportTableBodyRow extends TableBodyRow {
  /**
   * Create a row of a body of a table
   *
   * @param owner
   *          the owning table body
   */
  _ExportTableBodyRow(final _ExportTableBody owner) {
    super(owner);
    this.open();
  }
}
