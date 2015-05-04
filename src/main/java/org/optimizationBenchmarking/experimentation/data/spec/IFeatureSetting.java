package org.optimizationBenchmarking.experimentation.data.spec;

import java.util.Iterator;

/** A concrete setting of instance features. */
public interface IFeatureSetting extends IPropertySetting,
Iterable<IFeatureValue> {

  /**
   * Iterate over the feature values in this setting.
   *
   * @return an {@link java.util.Iterator} over the feature values in this
   *         setting.
   */
  @Override
  public abstract Iterator<IFeatureValue> iterator();

}
