package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * An itemization
 */
public class Itemization extends List<ItemizationItem> {

  /** the itemization depth */
  private final int m_itemDepth;

  /**
   * Create a new itemization
   *
   * @param owner
   *          the owning text
   */
  @SuppressWarnings("resource")
  protected Itemization(final StructuredText owner) {
    super(owner);

    HierarchicalFSM o;

    outer: {
      inner: {
        for (o = owner; o != null;) {
          if (o instanceof Itemization) {
            this.m_itemDepth = (1 + (((Itemization) o).m_itemDepth));
            break outer;
          }
          if (o instanceof DocumentElement) {
            o = ((DocumentElement) o)._owner();
          } else {
            break inner;
          }
        }
      }
      this.m_itemDepth = 0;
    }

  }

  /**
   * Obtain the number of itemizations into which this itemizations is
   * nested.
   *
   * @return the number of "outer" itemizations into which this
   *         itemizations is embedded, or {@code 0} if this is a top-level
   *         itemizations
   */
  public final int getItemizationDepth() {
    return this.m_itemDepth;
  }

  /** {@inheritDoc} */
  @Override
  final ItemizationItem createItem() {
    return this.m_driver.createItemizationItem(this);
  }
}
