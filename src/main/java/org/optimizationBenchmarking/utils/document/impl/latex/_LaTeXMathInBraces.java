package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathInBraces;

/** an mathematical in-braces element of a section in a LaTeX document */
final class _LaTeXMathInBraces extends MathInBraces {
  /**
   * create the mathematical in-braces element
   * 
   * @param owner
   *          the owner
   */
  _LaTeXMathInBraces(final BasicMath owner) {
    super(owner);
    this.open();
  }
}
