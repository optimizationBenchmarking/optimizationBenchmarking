package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooterRow;

/** a row of a footer of a table in an export document */
final class _ExportTableFooterRow extends TableFooterRow {
  /**
   * Create a row of a footer of a table
   *
   * @param owner
   *          the owning table footer
   */
  _ExportTableFooterRow(final _ExportTableFooter owner) {
    super(owner);
    this.open();
  }
}
