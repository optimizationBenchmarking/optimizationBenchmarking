package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.EnumerationItem;

/** an enumeration item in an export document */
final class _ExportEnumerationItem extends EnumerationItem {
  /**
   * Create a new enumeration item
   *
   * @param owner
   *          the owning text
   */
  _ExportEnumerationItem(final _ExportEnumeration owner) {
    super(owner);
    this.open();
  }
}
