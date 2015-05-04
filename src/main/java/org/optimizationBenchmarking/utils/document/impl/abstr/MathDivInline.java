package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics inline div operator */
public class MathDivInline extends MathFunction {

  /**
   * Create an inline div function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathDivInline(final BasicMath owner) {
    super(owner, 2, 2);
  }

}
