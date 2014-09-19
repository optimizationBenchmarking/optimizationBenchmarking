package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.EMathOperators;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathFunction;

/** an mathematical function in a XHTML document */
final class _XHTML10MathFunction extends MathFunction {
  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   * @param op
   *          the mathematical operator
   */
  _XHTML10MathFunction(final BasicMath owner, final EMathOperators op) {
    super(owner, op);
    this.open();
  }
}
