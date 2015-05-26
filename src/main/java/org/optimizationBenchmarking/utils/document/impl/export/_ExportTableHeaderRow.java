package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeaderRow;

/** a row of a header of a table in an export document */
final class _ExportTableHeaderRow extends TableHeaderRow {
  /**
   * Create a row of a header of a table
   *
   * @param owner
   *          the owning table header
   */
  _ExportTableHeaderRow(final _ExportTableHeader owner) {
    super(owner);
    this.open();
  }
}
