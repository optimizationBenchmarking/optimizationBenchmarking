package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * An enumeration
 */
public class Enumeration extends List<EnumerationItem> {

  /** the enumeration depth */
  private final int m_enumDepth;

  /**
   * Create a new enumeration
   *
   * @param owner
   *          the owning text
   */
  @SuppressWarnings("resource")
  protected Enumeration(final StructuredText owner) {
    super(owner);

    HierarchicalFSM o;

    outer: {
      inner: {
        for (o = owner; o != null;) {
          if (o instanceof Enumeration) {
            this.m_enumDepth = (1 + (((Enumeration) o).m_enumDepth));
            break outer;
          }
          if (o instanceof DocumentElement) {
            o = ((DocumentElement) o)._owner();
          } else {
            break inner;
          }
        }
      }
      this.m_enumDepth = 0;
    }

  }

  /**
   * Obtain the number of enumerations into which this enumerations is
   * nested.
   *
   * @return the number of "outer" enumerations into which this
   *         enumerations is embedded, or {@code 0} if this is a top-level
   *         enumerations
   */
  public final int getEnumerationDepth() {
    return this.m_enumDepth;
  }

  /** {@inheritDoc} */
  @Override
  protected EnumerationItem createItem() {
    return this.m_driver.createEnumerationItem(this);
  }
}
