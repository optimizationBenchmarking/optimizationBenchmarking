package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics sqrt operator */
public class MathSqrt extends MathFunction {

  /**
   * Create an sqrt function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathSqrt(final BasicMath owner) {
    super(owner, 1, 1);
  }

}
