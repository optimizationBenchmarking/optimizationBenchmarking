package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IStyle;
import org.optimizationBenchmarking.utils.document.spec.IStyleContext;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * An internal base class for stuff with styles.
 */
class _StyledElement extends DocumentPart implements IStyleContext {

  /** the style set */
  final StyleSet m_styles;

  /**
   * Create a new styled element
   * 
   * @param owner
   *          the owning fsm
   */
  protected _StyledElement(final HierarchicalFSM owner) {
    super(owner, null);
    this.m_styles = new StyleSet(Document._getStyleSet(owner));
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle createTextStyle() {
    return this.m_styles.createTextStyle();
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle createGraphicalStyle() {
    return this.m_styles.createGraphicalStyle();
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle emphasized() {
    return this.m_styles.emphasized();
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle plain() {
    return this.m_styles.plain();
  }
}
