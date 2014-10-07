package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSqrt;

/** an mathematical sqrt function in a LaTeX document */
final class _LaTeXMathSqrt extends MathSqrt {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathSqrt(final BasicMath owner) {
    super(owner);
  }
}
