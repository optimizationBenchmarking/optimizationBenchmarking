package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathRoot;

/** an mathematical root function in a LaTeX document */
final class _LaTeXMathRoot extends MathRoot {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathRoot(final BasicMath owner) {
    super(owner);
  }
}
