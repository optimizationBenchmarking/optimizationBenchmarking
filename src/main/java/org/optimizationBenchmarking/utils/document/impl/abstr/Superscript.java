package org.optimizationBenchmarking.utils.document.impl.abstr;

/** A text class for super-script text */
public class Superscript extends Text {

  /**
   * Create a super-script text.
   * 
   * @param owner
   *          the owning FSM
   */
  public Superscript(final ComplexText owner) {
    super(owner, null);
  }

  /** {@inheritDoc} */
  @Override
  protected ComplexText getOwner() {
    return ((ComplexText) (super.getOwner()));
  }
}
