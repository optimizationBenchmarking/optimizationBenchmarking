package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathLg;

/** an mathematical lg function in a LaTeX document */
final class _LaTeXMathLg extends MathLg {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathLg(final BasicMath owner) {
    super(owner);
  }
}
