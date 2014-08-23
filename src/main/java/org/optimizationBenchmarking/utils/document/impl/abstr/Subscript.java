package org.optimizationBenchmarking.utils.document.impl.abstr;

/** A text class for sub-script text */
public class Subscript extends Text {

  /**
   * Create a sub-script text.
   * 
   * @param owner
   *          the owning FSM
   */
  public Subscript(final ComplexText owner) {
    super(owner, null);
  }

  /** {@inheritDoc} */
  @Override
  protected ComplexText getOwner() {
    return ((ComplexText) (super.getOwner()));
  }
}
