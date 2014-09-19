package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.MathArgument;

/** an mathematical function argument in a LaTeX document */
final class _LaTeXMathArgument extends MathArgument {
  /**
   * Create a new mathematical function argument
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathArgument(final _LaTeXMathFunction owner) {
    super(owner);
    this.open();
  }
}
