package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSetting;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;
import org.optimizationBenchmarking.utils.collections.iterators.ArrayIterator;

/** a concrete feature setting */
public final class FeatureSetting extends
    _PropertySetting<FeatureValue, Feature> implements IFeatureSetting {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create
   *
   * @param params
   *          the feature values
   * @param isGeneral
   *          is this setting generalized?
   */
  FeatureSetting(final PropertyValue<?>[] params, final boolean isGeneral) {
    super(params, isGeneral);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final ArrayIterator<IFeatureValue> iterator() {
    return new ArrayIterator(this.m_values);
  }
}
