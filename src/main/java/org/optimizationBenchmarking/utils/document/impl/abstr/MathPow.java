package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics pow operator */
public class MathPow extends MathFunction {

  /**
   * Create an pow function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathPow(final BasicMath owner) {
    super(owner, 2, 2);
  }

}
