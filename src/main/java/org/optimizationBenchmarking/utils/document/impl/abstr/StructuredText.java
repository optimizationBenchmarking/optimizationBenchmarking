package org.optimizationBenchmarking.utils.document.impl.abstr;

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
  protected Enumeration createEnumeration() {
    return new Enumeration(this);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized Enumeration enumeration() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createEnumeration();
  }

  /**
   * Create an itemization
   * 
   * @return an itemization
   */
  protected Itemization createItemization() {
    return new Itemization(this);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized Itemization itemization() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createItemization();
  }
}
