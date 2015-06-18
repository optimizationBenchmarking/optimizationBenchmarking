package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics sqr operator */
public class MathSqr extends MathFunction {

  /**
   * Create an sqr function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathSqr(final BasicMath owner) {
    super(owner, 1, 1);
  }

}
