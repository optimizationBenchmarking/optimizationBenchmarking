package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooterRow;

/** a row of a footer of a table in a LaTeX document */
final class _LaTeXTableFooterRow extends TableFooterRow {
  /**
   * Create a row of a footer of a table
   * 
   * @param owner
   *          the owning table footer
   */
  _LaTeXTableFooterRow(final _LaTeXTableFooter owner) {
    super(owner);
    this.open();
  }
}
