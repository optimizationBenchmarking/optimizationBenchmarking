package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.EMathOperators;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathFunction;

/** an mathematical function in a LaTeX document */
final class _LaTeXMathFunction extends MathFunction {
  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   * @param op
   *          the mathematical operator
   */
  _LaTeXMathFunction(final BasicMath owner, final EMathOperators op) {
    super(owner, op);
    this.open();
  }
}
