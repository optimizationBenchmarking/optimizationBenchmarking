package org.optimizationBenchmarking.experimentation.data.spec;

import java.util.Map;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * The set of experiment features.
 */
public interface IFeatureSet extends IPropertySet {
  /**
   * Get the features of this feature set
   *
   * @return the features of this feature set
   */
  @Override
  public abstract ArrayListView<? extends IFeature> getData();

  /**
   * Obtain a feature fitting to a given name
   *
   * @param name
   *          the feature name
   * @return the feature, or {@code null} if none could be found
   */
  @Override
  public abstract IFeature find(final String name);

  /**
   * Create a feature setting based on the given collection of values. All
   * values not contained in {@code values} are considered as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Feature#getGeneralized()
   * generalized}
   *
   * @param values
   *          the set of feature values
   * @return the feature setting
   */
  @Override
  public abstract IFeatureSetting createSettingFromValues(
      final Iterable<? extends IPropertyValue> values);

  /**
   * Create a feature setting based on the given mapping of feature names
   * to values. All values not contained in {@code values} are considered
   * as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Feature#getGeneralized()
   * generalized}
   *
   * @param values
   *          the mapping of names to values
   * @return the feature setting
   */
  @Override
  public abstract IFeatureSetting createSettingFromMapping(
      final Map<String, Object> values);

  /**
   * Create a feature setting based on the given mapping of feature names
   * to values. All values not contained in {@code values} are considered
   * as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Feature#getGeneralized()
   * generalized}
   *
   * @param values
   *          the mapping of names to values
   * @return the feature setting
   */
  @Override
  public abstract IFeatureSetting createSettingFromMapping(
      final Map.Entry<String, Object>[] values);

  /**
   * Create a feature setting based on the given mapping of feature names
   * to values. All values not contained in {@code values} are considered
   * as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Feature#getGeneralized()
   * generalized}
   *
   * @param values
   *          the mapping of names to values
   * @return the feature setting
   */
  @Override
  public abstract IFeatureSetting createSettingFromMapping(
      final Iterable<Map.Entry<String, Object>> values);

  /**
   * Create a feature setting based on the given collection of values. All
   * values not contained in {@code values} are considered as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Feature#getGeneralized()
   * generalized}
   *
   * @param values
   *          the set of feature values
   * @return the feature setting
   */
  @Override
  public abstract IFeatureSetting createSettingFromValues(
      final IPropertyValue... values);
}
