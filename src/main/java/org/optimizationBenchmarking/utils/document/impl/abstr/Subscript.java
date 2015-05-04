package org.optimizationBenchmarking.utils.document.impl.abstr;

/** A text class for sub-script text */
public class Subscript extends PlainText {

  /**
   * Create a sub-script text.
   *
   * @param owner
   *          the owning FSM
   */
  protected Subscript(final Text owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected Text getOwner() {
    return ((Text) (super.getOwner()));
  }
}
