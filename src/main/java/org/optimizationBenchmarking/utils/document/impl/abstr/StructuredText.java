package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IList;
import org.optimizationBenchmarking.utils.document.spec.IStructuredText;
import org.optimizationBenchmarking.utils.document.spec.IStyle;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * A structured text
 */
public class StructuredText extends ComplexText implements IStructuredText {
  /**
   * Create a structured text.
   * 
   * @param owner
   *          the owning FSM
   * @param style
   *          the style
   */
  protected StructuredText(final HierarchicalFSM owner, final IStyle style) {
    super(owner, null, style);
  }

  /**
   * Create an enumeration
   * 
   * @return an enumeration
   */
  protected IList createEnumeration() {
    return new Enumeration(this);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized IList enumeration() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createEnumeration();
  }

  /**
   * Create an itemization
   * 
   * @return an itemization
   */
  protected IList createItemization() {
    return new Itemization(this);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized IList itemization() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createItemization();
  }
}
