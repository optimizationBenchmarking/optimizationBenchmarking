package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableCaption;

/** the caption of a table in an export document */
final class _ExportTableCaption extends TableCaption {
  /**
   * Create the caption of a table
   *
   * @param owner
   *          the owning table
   */
  _ExportTableCaption(final _ExportTable owner) {
    super(owner);
    this.open();
  }
}
