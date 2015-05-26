package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.ItemizationItem;

/** an itemization item in an export document */
final class _ExportItemizationItem extends ItemizationItem {

  /**
   * Create a new itemization item
   *
   * @param owner
   *          the owning text
   */
  _ExportItemizationItem(final _ExportItemization owner) {
    super(owner);
    this.open();
  }
}
