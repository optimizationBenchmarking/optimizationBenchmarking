package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics minimum function operator */
public class MathMin extends MathFunction {

  /**
   * Create a minimum function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathMin(final BasicMath owner) {
    super(owner, 1, 32);
  }

}
