package org.optimizationBenchmarking.utils.io.xml;

import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalText;

/**
 * The base class for all elements of our XML output API.
 */
public class XMLBase extends HierarchicalText {

  /**
   * Create the hierarchical XML writer
   *
   * @param owner
   *          the owning hierarchical fsm
   * @param out
   *          the {@link java.lang.Appendable}
   */
  XMLBase(final HierarchicalFSM owner, final Appendable out) {
    super(owner, out);
  }

  /**
   * Create a new xml element.
   *
   * @return the element record
   */
  public final XMLElement element() {
    return new XMLElement(this);
  }
}
