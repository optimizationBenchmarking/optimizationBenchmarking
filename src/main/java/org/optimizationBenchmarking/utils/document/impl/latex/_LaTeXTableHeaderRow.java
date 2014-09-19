package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeaderRow;

/** a row of a header of a table in a LaTeX document */
final class _LaTeXTableHeaderRow extends TableHeaderRow {
  /**
   * Create a row of a header of a table
   * 
   * @param owner
   *          the owning table header
   */
  _LaTeXTableHeaderRow(final _LaTeXTableHeader owner) {
    super(owner);
    this.open();
  }
}
