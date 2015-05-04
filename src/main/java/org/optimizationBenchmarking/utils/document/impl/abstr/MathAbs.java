package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics absolute value operator */
public class MathAbs extends MathFunction {

  /**
   * Create a absolute value function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathAbs(final BasicMath owner) {
    super(owner, 1, 1);
  }

}
