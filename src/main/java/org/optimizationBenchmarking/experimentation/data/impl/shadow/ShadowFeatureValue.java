package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;

/**
 * A shadow feature value is basically a shadow of another feature value
 * with a different owner, but delegates attribute-based computations to
 * that feature value.
 */
public class ShadowFeatureValue extends
    _ShadowDataElement<IFeature, IFeatureValue> implements IFeatureValue {

  /**
   * create the shadow feature value
   * 
   * @param owner
   *          the owning feature
   * @param shadow
   *          the feature value to shadow
   */
  public ShadowFeatureValue(final IFeature owner,
      final IFeatureValue shadow) {
    super(shadow);
    this.m_owner = owner;
  }

  /** {@inheritDoc} */
  @Override
  public final Object getValue() {
    return this.m_shadowUnpacked.getValue();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isGeneralized() {
    return this.m_shadowUnpacked.isGeneralized();
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
