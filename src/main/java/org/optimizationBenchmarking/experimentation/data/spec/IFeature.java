package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A feature of an experiment.
 */
public interface IFeature extends IProperty {
  /**
   * Get the owning feature set
   *
   * @return the owning feature set
   */
  @Override
  public abstract IFeatureSet getOwner();

  /**
   * Get the set of feature values
   *
   * @return the set of feature values
   */
  @Override
  public abstract ArrayListView<? extends IFeatureValue> getData();

  /**
   * Obtain a feature value fitting to a given value object
   *
   * @param value
   *          the feature value object
   * @return the feature value, or {@code null} if none could be found
   */
  @Override
  public abstract IFeatureValue findValue(final Object value);

  /**
   * The feature value record indicating generalization
   *
   * @return the feature value record indicating generalization
   */
  @Override
  public abstract IFeatureValue getGeneralized();

}
