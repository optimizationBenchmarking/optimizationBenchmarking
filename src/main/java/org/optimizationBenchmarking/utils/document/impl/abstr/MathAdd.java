package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics add operator */
public class MathAdd extends MathFunction {

  /**
   * Create an add function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathAdd(final BasicMath owner) {
    super(owner, 2, 32);
  }

}
