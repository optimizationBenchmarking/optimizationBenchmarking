package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;

/** a feature selection */
final class _FeatureSelection extends
_PropertySelection<IFeatureSet, IFeature, IFeatureValue> {

  /**
   * create the feature selection
   *
   * @param set
   *          the original set
   */
  _FeatureSelection(final IFeatureSet set) {
    super(set);
  }

  /** {@inheritDoc} */
  @Override
  final _Selection<IFeature, IFeatureValue> _createSelection(
      final IFeature element) {
    return new _FeatureValueSelection(element);
  }

  /** {@inheritDoc} */
  @Override
  final IFeatureSet _shadow(final IFeatureSet original,
      final Collection<IFeature> elements) {
    return new ShadowFeatureSet(null, original, elements);
  }
}
