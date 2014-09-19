package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.ItemizationItem;

/** an itemization item in a LaTeX document */
final class _LaTeXItemizationItem extends ItemizationItem {
  /**
   * Create a new itemization item
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXItemizationItem(final _LaTeXItemization owner) {
    super(owner);
    this.open();
  }
}
