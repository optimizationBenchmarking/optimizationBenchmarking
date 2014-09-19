package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.InlineMath;

/** an inline math element of a section in a LaTeX document */
final class _LaTeXInlineMath extends InlineMath {
  /**
   * create the inline math element
   * 
   * @param owner
   *          the owner
   */
  _LaTeXInlineMath(final ComplexText owner) {
    super(owner);
    this.open();
  }
}
