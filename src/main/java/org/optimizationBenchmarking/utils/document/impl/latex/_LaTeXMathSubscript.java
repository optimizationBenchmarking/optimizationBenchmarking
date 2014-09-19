package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSubscript;

/** an mathematical subscript element of a section in a LaTeX document */
final class _LaTeXMathSubscript extends MathSubscript {
  /**
   * create the mathematical subscript element
   * 
   * @param owner
   *          the owner
   */
  _LaTeXMathSubscript(final BasicMath owner) {
    super(owner);
    this.open();
  }
}
