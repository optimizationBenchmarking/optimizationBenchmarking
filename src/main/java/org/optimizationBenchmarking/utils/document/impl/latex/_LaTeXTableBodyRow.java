package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableBodyRow;

/** a row of a body of a table in a LaTeX document */
final class _LaTeXTableBodyRow extends TableBodyRow {
  /**
   * Create a row of a body of a table
   * 
   * @param owner
   *          the owning table body
   */
  _LaTeXTableBodyRow(final _LaTeXTableBody owner) {
    super(owner);
    this.open();
  }
}
