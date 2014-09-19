package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.ItemizationItem;

/** an itemization item in a XHTML document */
final class _XHTML10ItemizationItem extends ItemizationItem {
  /**
   * Create a new itemization item
   * 
   * @param owner
   *          the owning text
   */
  _XHTML10ItemizationItem(final _XHTML10Itemization owner) {
    super(owner);
    this.open();
  }
}
