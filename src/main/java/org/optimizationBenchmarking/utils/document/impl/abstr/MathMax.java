package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics maximum function operator */
public class MathMax extends MathFunction {

  /**
   * Create a maximum function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathMax(final BasicMath owner) {
    super(owner, 1, 32);
  }

}
