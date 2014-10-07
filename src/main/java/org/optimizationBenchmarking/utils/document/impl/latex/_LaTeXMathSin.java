package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSin;

/** an mathematical sine function in a LaTeX document */
final class _LaTeXMathSin extends MathSin {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathSin(final BasicMath owner) {
    super(owner);
    this.open();
  }
}
