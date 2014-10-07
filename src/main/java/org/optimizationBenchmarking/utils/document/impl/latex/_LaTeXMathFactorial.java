package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathFactorial;

/** an mathematical factorial function in a LaTeX document */
final class _LaTeXMathFactorial extends MathFactorial {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathFactorial(final BasicMath owner) {
    super(owner);
    this.open();
  }

}
