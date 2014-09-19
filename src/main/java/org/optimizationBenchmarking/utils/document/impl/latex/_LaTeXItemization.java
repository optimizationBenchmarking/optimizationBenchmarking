package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Itemization;
import org.optimizationBenchmarking.utils.document.impl.abstr.StructuredText;

/** an itemization in a LaTeX document */
final class _LaTeXItemization extends Itemization {
  /**
   * Create a new itemization
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXItemization(final StructuredText owner) {
    super(owner);
    this.open();
  }
}
