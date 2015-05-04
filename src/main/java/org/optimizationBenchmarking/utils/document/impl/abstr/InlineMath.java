package org.optimizationBenchmarking.utils.document.impl.abstr;

/** An in-line mathematics output class */
public class InlineMath extends BasicMath {

  /**
   * Create an in-line mathematics output.
   *
   * @param owner
   *          the owning complex text
   */
  protected InlineMath(final ComplexText owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected ComplexText getOwner() {
    return ((ComplexText) (super.getOwner()));
  }

  /** {@inheritDoc} */
  @Override
  final int minArgs() {
    return 1;
  }

  /** {@inheritDoc} */
  @Override
  final int maxArgs() {
    return 1;
  }
}
