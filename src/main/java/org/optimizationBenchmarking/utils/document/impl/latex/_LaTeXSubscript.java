package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.Subscript;

/** an subscript element of a section in a LaTeX document */
final class _LaTeXSubscript extends Subscript {
  /**
   * create the subscript element
   * 
   * @param owner
   *          the owner
   */
  _LaTeXSubscript(final ComplexText owner) {
    super(owner);
    this.open();
  }
}
