package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathLog;

/** an mathematical log function in a LaTeX document */
final class _LaTeXMathLog extends MathLog {

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathLog(final BasicMath owner) {
    super(owner);
  }
}
