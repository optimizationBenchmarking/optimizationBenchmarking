package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSub;

/** an mathematical sub function in a LaTeX document */
final class _LaTeXMathSub extends MathSub {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathSub(final BasicMath owner) {
    super(owner);
  }
}
