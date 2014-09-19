package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.EnumerationItem;

/** an enumeration item in a LaTeX document */
final class _LaTeXEnumerationItem extends EnumerationItem {
  /**
   * Create a new enumeration item
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXEnumerationItem(final _LaTeXEnumeration owner) {
    super(owner);
    this.open();
  }
}
