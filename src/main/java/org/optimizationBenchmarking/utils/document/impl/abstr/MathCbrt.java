package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics cbrt operator */
public class MathCbrt extends MathFunction {

  /**
   * Create an cbrt function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathCbrt(final BasicMath owner) {
    super(owner, 1, 1);
  }

}
