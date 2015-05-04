package org.optimizationBenchmarking.utils.document.impl.abstr;

/** A text class for super-script text */
public class Superscript extends PlainText {

  /**
   * Create a super-script text.
   *
   * @param owner
   *          the owning FSM
   */
  protected Superscript(final Text owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected Text getOwner() {
    return ((Text) (super.getOwner()));
  }
}
