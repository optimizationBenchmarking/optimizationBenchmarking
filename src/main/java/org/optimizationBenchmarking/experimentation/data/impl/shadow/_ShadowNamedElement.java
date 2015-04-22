package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;

/**
 * A shadow named element is basically a shadow of another named element
 * with a different owner and potentially different attributes. If all
 * associated data of this element is the same, it will delegate
 * attribute-based computations to that named element.
 * 
 * @param <OT>
 *          the owner type
 * @param <ST>
 *          the shadow type
 */
class _ShadowNamedElement<OT extends IDataElement, ST extends INamedElement>
    extends _ShadowDataElement<OT, ST> implements INamedElement {

  /**
   * create the shadow named element
   * 
   * @param owner
   *          the owning element
   * @param shadow
   *          the named element to shadow
   */
  _ShadowNamedElement(final OT owner, final ST shadow) {
    super(owner, shadow);
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return this.m_shadowUnpacked.getName();
  }

  /** {@inheritDoc} */
  @Override
  public final String getDescription() {
    return this.m_shadowUnpacked.getDescription();
  }
}
