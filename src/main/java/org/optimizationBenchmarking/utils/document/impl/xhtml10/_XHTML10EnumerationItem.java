package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.EnumerationItem;

/** an enumeration item in a XHTML document */
final class _XHTML10EnumerationItem extends EnumerationItem {
  /**
   * Create a new enumeration item
   * 
   * @param owner
   *          the owning text
   */
  _XHTML10EnumerationItem(final _XHTML10Enumeration owner) {
    super(owner);
    this.open();
  }
}
