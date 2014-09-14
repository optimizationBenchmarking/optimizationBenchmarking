package org.optimizationBenchmarking.experimentation.data;

/** a concrete feature setting */
public final class FeatureSetting extends
    _PropertySetting<FeatureValue, Feature> {

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
  FeatureSetting(final _PropertyValue<?>[] params, final boolean isGeneral) {
    super(params, isGeneral);
  }

}
