package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathAdd;

/** an mathematical add function in a LaTeX document */
final class _LaTeXMathAdd extends MathAdd {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathAdd(final BasicMath owner) {
    super(owner);
  }
}
