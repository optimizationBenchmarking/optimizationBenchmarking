package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathLd;

/** an mathematical ld function in a LaTeX document */
final class _LaTeXMathLd extends MathLd {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathLd(final BasicMath owner) {
    super(owner);
  }
}
