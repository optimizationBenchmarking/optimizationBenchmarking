package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathNumber;

/** an mathematical number element of a section in a LaTeX document */
final class _LaTeXMathNumber extends MathNumber {
  /**
   * create the mathematical in-braces element
   *
   * @param owner
   *          the owner
   */
  _LaTeXMathNumber(final BasicMath owner) {
    super(owner);
    this.open();
  }

}
