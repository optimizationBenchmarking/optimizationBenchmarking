package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;

/**
 * A shadow feature is basically a shadow of another feature with a
 * different owner and potentially different attributes. If all associated
 * data of this element is the same, it will delegate attribute-based
 * computations to that feature.
 */
public class ShadowFeature extends
    _ShadowProperty<IFeatureSet, IFeature, IFeatureValue> implements
    IFeature {
  /**
   * create the shadow feature
   *
   * @param owner
   *          the owning feature
   * @param shadow
   *          the feature value to shadow
   * @param selection
   *          the selected feature values
   */
  public ShadowFeature(final IFeatureSet owner, final IFeature shadow,
      final Collection<? extends IFeatureValue> selection) {
    super(owner, shadow, selection);
  }

  /** {@inheritDoc} */
  @Override
  final IFeatureValue _shadow(final IFeatureValue value) {
    return new ShadowFeatureValue(this, value);
  }

}
