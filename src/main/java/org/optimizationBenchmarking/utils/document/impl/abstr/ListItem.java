package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A list item.
 */
public class ListItem extends StructuredText {

  /** the index of this list item */
  private final int m_index;

  /**
   * Create a list item.
   * 
   * @param owner
   *          the owning FSM
   * @param index
   *          the item's index
   */
  protected ListItem(final List<?> owner, final int index) {
    super(owner, DocumentPart._plain(owner));
    this.m_index = index;
  }

  /**
   * Get the list item's index
   * 
   * @return the list item's index
   */
  public final int getIndex() {
    return this.m_index;
  }
}
