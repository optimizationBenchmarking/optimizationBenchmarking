package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathDivInline;

/** an mathematical div inline function in a LaTeX document */
final class _LaTeXMathDivInline extends MathDivInline {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathDivInline(final BasicMath owner) {
    super(owner);
  }
}
