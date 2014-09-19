package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeader;

/** a header of a table in a LaTeX document */
final class _LaTeXTableHeader extends TableHeader {
  /**
   * Create a header of a table
   * 
   * @param owner
   *          the owning table
   */
  _LaTeXTableHeader(final _LaTeXTable owner) {
    super(owner);
    this.open();
  }
}
