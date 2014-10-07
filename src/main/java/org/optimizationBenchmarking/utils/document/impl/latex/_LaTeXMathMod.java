package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathMod;

/** an mathematical mod function in a LaTeX document */
final class _LaTeXMathMod extends MathMod {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathMod(final BasicMath owner) {
    super(owner);
  }
}
