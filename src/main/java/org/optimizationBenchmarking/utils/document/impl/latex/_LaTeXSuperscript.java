package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.Superscript;

/** an superscript element of a section in a LaTeX document */
final class _LaTeXSuperscript extends Superscript {
  /**
   * create the superscript element
   * 
   * @param owner
   *          the owner
   */
  _LaTeXSuperscript(final ComplexText owner) {
    super(owner);
    this.open();
  }
}
