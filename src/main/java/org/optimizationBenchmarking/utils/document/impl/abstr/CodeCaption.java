package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * the caption of a code block
 */
public class CodeCaption extends ComplexText {
  /**
   * create the caption
   *
   * @param owner
   *          the owner
   */
  protected CodeCaption(final Code owner) {
    super(owner);
  }

  /**
   * Get the owning code block
   *
   * @return the owning code block
   */
  @Override
  protected Code getOwner() {
    return ((Code) (super.getOwner()));
  }
}
