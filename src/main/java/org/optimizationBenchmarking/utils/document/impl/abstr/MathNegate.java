package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics negate operator */
public class MathNegate extends MathFunction {

  /**
   * Create a negate function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathNegate(final BasicMath owner) {
    super(owner, 1, 1);
  }

}
