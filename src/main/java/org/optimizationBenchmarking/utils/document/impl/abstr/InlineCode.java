package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/** A text class for in-line code */
public class InlineCode extends Text {

  /**
   * Create an in-line code text.
   * 
   * @param owner
   *          the owning FSM
   */
  public InlineCode(final HierarchicalFSM owner) {
    super(owner, null);
  }
}
