package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathMul;

/** an mathematical mul function in a LaTeX document */
final class _LaTeXMathMul extends MathMul {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathMul(final BasicMath owner) {
    super(owner);
  }
}
