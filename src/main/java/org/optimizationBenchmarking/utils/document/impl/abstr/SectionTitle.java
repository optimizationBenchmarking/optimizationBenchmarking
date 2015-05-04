package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A title of a section
 */
public class SectionTitle extends ComplexText {
  /**
   * create the title
   *
   * @param owner
   *          the owner
   */
  protected SectionTitle(final Section owner) {
    super(owner);
  }

  /**
   * Get the owning section
   *
   * @return the owning section
   */
  @Override
  protected Section getOwner() {
    return ((Section) (super.getOwner()));
  }
}
