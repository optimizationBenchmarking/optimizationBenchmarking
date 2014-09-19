package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooterRow;

/** a row of a footer of a table in a XHTML document */
final class _XHTML10TableFooterRow extends TableFooterRow {
  /**
   * Create a row of a footer of a table
   * 
   * @param owner
   *          the owning table footer
   */
  _XHTML10TableFooterRow(final _XHTML10TableFooter owner) {
    super(owner);
    this.open();
  }
}
