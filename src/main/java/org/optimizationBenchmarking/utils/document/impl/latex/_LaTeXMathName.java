package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathName;

/** an mathematical name element of a section in a LaTeX document */
final class _LaTeXMathName extends MathName {
  /**
   * create the mathematical in-braces element
   * 
   * @param owner
   *          the owner
   */
  _LaTeXMathName(final BasicMath owner) {
    super(owner);
    this.open();
  }

}
