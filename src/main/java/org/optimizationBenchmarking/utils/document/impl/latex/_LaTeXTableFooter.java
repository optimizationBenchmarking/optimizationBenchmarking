package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooter;

/** a footer of a table in a LaTeX document */
final class _LaTeXTableFooter extends TableFooter {
  /**
   * Create a footer of a table
   * 
   * @param owner
   *          the owning table
   */
  _LaTeXTableFooter(final _LaTeXTable owner) {
    super(owner);
    this.open();
  }
}
