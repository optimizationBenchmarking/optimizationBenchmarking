package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * An enumeration item
 */
public class EnumerationItem extends ListItem {
  /**
   * Create a structured text.
   *
   * @param owner
   *          the owning FSM
   */
  protected EnumerationItem(final Enumeration owner) {
    super(owner);
  }

  /**
   * Get the owning enumeration list
   *
   * @return the owning enumeration list
   */
  @Override
  protected Enumeration getOwner() {
    return ((Enumeration) (super.getOwner()));
  }
}
