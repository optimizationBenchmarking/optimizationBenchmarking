package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics ln operator */
public class MathLn extends MathFunction {

  /**
   * Create an ln function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathLn(final BasicMath owner) {
    super(owner, 1, 1);
  }

}
