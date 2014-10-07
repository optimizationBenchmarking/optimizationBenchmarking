package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathTan;

/** an mathematical tangent function in a LaTeX document */
final class _LaTeXMathTan extends MathTan {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathTan(final BasicMath owner) {
    super(owner);
    this.open();
  }

}
