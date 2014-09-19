package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeaderRow;

/** a row of a header of a table in a XHTML document */
final class _XHTML10TableHeaderRow extends TableHeaderRow {
  /**
   * Create a row of a header of a table
   * 
   * @param owner
   *          the owning table header
   */
  _XHTML10TableHeaderRow(final _XHTML10TableHeader owner) {
    super(owner);
    this.open();
  }
}
