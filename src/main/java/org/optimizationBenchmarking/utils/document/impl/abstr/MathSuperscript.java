package org.optimizationBenchmarking.utils.document.impl.abstr;

/** A text class for super-script mathematical context */
public class MathSuperscript extends BasicMath {

  /**
   * Create a super-script mathematical context.
   * 
   * @param owner
   *          the owning FSM
   */
  public MathSuperscript(final BasicMath owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected BasicMath getOwner() {
    return ((BasicMath) (super.getOwner()));
  }
}
