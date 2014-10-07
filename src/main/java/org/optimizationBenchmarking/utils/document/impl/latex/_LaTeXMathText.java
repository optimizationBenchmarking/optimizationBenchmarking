package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathText;

/** an mathematical text element of a section in a LaTeX document */
final class _LaTeXMathText extends MathText {
  /**
   * create the mathematical in-braces element
   * 
   * @param owner
   *          the owner
   */
  _LaTeXMathText(final BasicMath owner) {
    super(owner);
    this.open();
  }

}
