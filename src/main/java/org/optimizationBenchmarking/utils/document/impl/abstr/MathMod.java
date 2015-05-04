package org.optimizationBenchmarking.utils.document.impl.abstr;

/** a mathematics mod operator */
public class MathMod extends MathFunction {

  /**
   * Create an mod function
   *
   * @param owner
   *          the owning FSM
   */
  protected MathMod(final BasicMath owner) {
    super(owner, 2, 2);
  }

}
