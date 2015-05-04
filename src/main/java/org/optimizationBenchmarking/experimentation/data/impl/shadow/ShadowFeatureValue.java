package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;

/**
 * A shadow feature value is basically a shadow of another feature value
 * with a different owner and potentially different attributes. If all
 * associated data of this element is the same, it will delegate
 * attribute-based computations to that feature value.
 */
public class ShadowFeatureValue extends
    _ShadowPropertyValue<IFeature, IFeatureValue> implements IFeatureValue {

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
    super(owner, shadow);
  }
}
