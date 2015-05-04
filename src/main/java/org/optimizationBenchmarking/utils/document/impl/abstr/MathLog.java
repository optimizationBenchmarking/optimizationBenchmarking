package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics log operator */
public class MathLog extends MathFunction {

  /**
   * Create an log function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathLog(final BasicMath owner) {
    super(owner, 2, 2);
  }

}
