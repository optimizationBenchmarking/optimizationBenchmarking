package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableBody;

/** a body of a table in a LaTeX document */
final class _LaTeXTableBody extends TableBody {
  /**
   * Create a body of a table
   * 
   * @param owner
   *          the owning table
   */
  _LaTeXTableBody(final _LaTeXTable owner) {
    super(owner);
    this.open();
  }
}
