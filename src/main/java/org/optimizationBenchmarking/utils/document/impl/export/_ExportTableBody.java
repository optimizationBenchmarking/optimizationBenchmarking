package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableBody;

/** a body of a table in an export document */
final class _ExportTableBody extends TableBody {
  /**
   * Create a body of a table
   *
   * @param owner
   *          the owning table
   */
  _ExportTableBody(final _ExportTable owner) {
    super(owner);
    this.open();
  }
}
