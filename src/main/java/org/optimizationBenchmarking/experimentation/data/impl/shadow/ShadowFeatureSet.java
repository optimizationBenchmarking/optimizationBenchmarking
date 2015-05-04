package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSetting;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A shadow feature set is basically a shadow of another feature set with a
 * different owner and potentially different attributes. If all associated
 * data of this element is the same, it will delegate attribute-based
 * computations to that feature set.
 */
public class ShadowFeatureSet extends
_ShadowPropertySet<IFeatureSet, IFeature, IFeatureSetting> implements
IFeatureSet {
  /**
   * create the shadow feature set
   *
   * @param owner
   *          the owning feature
   * @param shadow
   *          the feature value to shadow
   * @param selection
   *          the selected features
   */
  public ShadowFeatureSet(final IExperimentSet owner,
      final IFeatureSet shadow,
      final Collection<? extends IFeature> selection) {
    super(owner, shadow, selection);
  }

  /** {@inheritDoc} */
  @Override
  final IFeature _shadow(final IFeature element) {
    return new ShadowFeature(this, element, null);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  final IFeatureSetting _createSettingFromArray(
      final IPropertyValue[] values) {
    return new _ShadowFeatureSetting(new ArraySetView(values));
  }

}
