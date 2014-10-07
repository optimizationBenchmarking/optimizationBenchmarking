package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathCos;

/** an mathematical cosine function in a LaTeX document */
final class _LaTeXMathCos extends MathCos {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathCos(final BasicMath owner) {
    super(owner);
    this.open();
  }

}
