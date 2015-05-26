package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooter;

/** a footer of a table in an export document */
final class _ExportTableFooter extends TableFooter {
  /**
   * Create a footer of a table
   *
   * @param owner
   *          the owning table
   */
  _ExportTableFooter(final _ExportTable owner) {
    super(owner);
    this.open();
  }
}
