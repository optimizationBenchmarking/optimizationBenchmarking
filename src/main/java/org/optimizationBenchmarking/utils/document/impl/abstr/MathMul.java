package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics mul operator */
public class MathMul extends MathFunction {

  /**
   * Create an mul function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathMul(final BasicMath owner) {
    super(owner, 2, 32);
  }

}
