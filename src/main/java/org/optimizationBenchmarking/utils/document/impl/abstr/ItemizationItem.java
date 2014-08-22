package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * An enumeration item
 */
public class ItemizationItem extends ListItem {
  /**
   * Create a structured text.
   * 
   * @param owner
   *          the owning FSM
   * @param index
   *          the item index
   */
  protected ItemizationItem(final Itemization owner, final int index) {
    super(owner, index);
  }

  /**
   * Get the owning itemization list
   * 
   * @return the owning itemization list
   */
  @Override
  protected Itemization getOwner() {
    return ((Itemization) (super.getOwner()));
  }
}
