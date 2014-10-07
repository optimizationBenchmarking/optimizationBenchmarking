package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathPow;

/** an mathematical pow function in a LaTeX document */
final class _LaTeXMathPow extends MathPow {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathPow(final BasicMath owner) {
    super(owner);
  }
}
