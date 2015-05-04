package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics sub operator */
public class MathSub extends MathFunction {

  /**
   * Create an sub function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathSub(final BasicMath owner) {
    super(owner, 2, 32);
  }

}
