package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathAbs;

/** an mathematical absolute function in a LaTeX document */
final class _LaTeXMathAbs extends MathAbs {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathAbs(final BasicMath owner) {
    super(owner);
    this.open();
  }

}
