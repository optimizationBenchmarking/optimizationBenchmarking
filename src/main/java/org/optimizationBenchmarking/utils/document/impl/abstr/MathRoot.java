package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics root operator */
public class MathRoot extends MathFunction {

  /**
   * Create an root function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathRoot(final BasicMath owner) {
    super(owner, 2, 2);
  }

}
