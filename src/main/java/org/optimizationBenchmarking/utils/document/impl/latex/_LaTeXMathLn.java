package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathLn;

/** an mathematical ln function in a LaTeX document */
final class _LaTeXMathLn extends MathLn {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathLn(final BasicMath owner) {
    super(owner);
  }
}
