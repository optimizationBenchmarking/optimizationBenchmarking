package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics factorial operator */
public class MathFactorial extends MathFunction {

  /**
   * Create a factorial function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathFactorial(final BasicMath owner) {
    super(owner, 1, 1);
  }

}
