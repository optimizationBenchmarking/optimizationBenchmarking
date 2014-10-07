package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathCompare;

/** an mathematical compare function in a LaTeX document */
final class _LaTeXMathCompare extends MathCompare {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   * @param cmp
   *          the comparator
   */
  _LaTeXMathCompare(final BasicMath owner, final EComparison cmp) {
    super(owner, cmp);
  }
}
