package org.optimizationBenchmarking.utils.document.impl.abstr;

/** An in-line mathematics output class */
public class InlineMath extends BasicMath {

  /**
   * Create an in-line mathematics output.
   * 
   * @param owner
   *          the owning complex text
   */
  public InlineMath(final ComplexText owner) {
    super(owner, null);
  }

  /** {@inheritDoc} */
  @Override
  protected ComplexText getOwner() {
    return ((ComplexText) (super.getOwner()));
  }
}
