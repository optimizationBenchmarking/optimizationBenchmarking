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
   * @param index
   *          the item index
   */
  public EnumerationItem(final Enumeration owner, final int index) {
    super(owner, index);
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
