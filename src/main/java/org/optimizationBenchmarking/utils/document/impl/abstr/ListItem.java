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
   */
  protected ListItem(final List<?> owner) {
    super(owner);
    this.m_index = owner.m_index;
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
