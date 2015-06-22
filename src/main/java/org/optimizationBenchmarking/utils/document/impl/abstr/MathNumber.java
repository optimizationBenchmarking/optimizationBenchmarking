package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A text class for mathematical numbers. Its contents will be rendered in
 * a number-like style.
 */
public class MathNumber extends Text {

  /**
   * Create a text.
   *
   * @param owner
   *          the owning FSM
   */
  protected MathNumber(final BasicMath owner) {
    super(owner);
  }
}
