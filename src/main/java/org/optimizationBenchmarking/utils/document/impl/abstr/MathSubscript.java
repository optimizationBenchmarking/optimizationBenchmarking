package org.optimizationBenchmarking.utils.document.impl.abstr;

/** A text class for sub-script mathematical context */
public class MathSubscript extends BasicMath {

  /**
   * Create a sub-script mathematical context.
   * 
   * @param owner
   *          the owning FSM
   */
  protected MathSubscript(final BasicMath owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected BasicMath getOwner() {
    return ((BasicMath) (super.getOwner()));
  }
}
