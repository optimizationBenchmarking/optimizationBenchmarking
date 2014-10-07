package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathDiv;

/** an mathematical div function in a LaTeX document */
final class _LaTeXMathDiv extends MathDiv {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathDiv(final BasicMath owner) {
    super(owner);
  }
}
