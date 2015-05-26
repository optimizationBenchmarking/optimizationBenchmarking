package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeader;

/** a header of a table in an export document */
final class _ExportTableHeader extends TableHeader {
  /**
   * Create a header of a table
   *
   * @param owner
   *          the owning table
   */
  _ExportTableHeader(final _ExportTable owner) {
    super(owner);
    this.open();
  }
}
