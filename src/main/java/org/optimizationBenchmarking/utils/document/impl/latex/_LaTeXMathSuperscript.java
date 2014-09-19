package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSuperscript;

/** an mathematical superscript element of a section in a LaTeX document */
final class _LaTeXMathSuperscript extends MathSuperscript {
  /**
   * create the mathematical superscript element
   * 
   * @param owner
   *          the owner
   */
  _LaTeXMathSuperscript(final BasicMath owner) {
    super(owner);
    this.open();
  }
}
