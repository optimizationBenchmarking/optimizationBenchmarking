package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableBodyRow;

/** a row of a body of a table in a XHTML document */
final class _XHTML10TableBodyRow extends TableBodyRow {
  /**
   * Create a row of a body of a table
   * 
   * @param owner
   *          the owning table body
   */
  _XHTML10TableBodyRow(final _XHTML10TableBody owner) {
    super(owner);
    this.open();
  }
}
