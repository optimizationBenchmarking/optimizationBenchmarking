package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics div operator */
public class MathDiv extends MathFunction {

  /**
   * Create an div function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathDiv(final BasicMath owner) {
    super(owner, 2, 2);
  }

}
