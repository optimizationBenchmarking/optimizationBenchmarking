package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathNegate;

/** an mathematical negate function in a LaTeX document */
final class _LaTeXMathNegate extends MathNegate {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathNegate(final BasicMath owner) {
    super(owner);
    this.open();
  }
}
