package org.optimizationBenchmarking.experimentation.data.spec;

import java.util.Map;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A set of properties, such as those belonging to an experiment or
 * instance.
 */
public interface IPropertySet extends INamedElementSet {

  /**
   * Get the owning experiment set
   * 
   * @return the owning experiment set
   */
  @Override
  public abstract IExperimentSet getOwner();

  /**
   * Get the properties of this property set
   * 
   * @return the properties of this property set
   */
  @Override
  public abstract ArrayListView<? extends IProperty> getData();

  /**
   * Obtain a property fitting to a given name
   * 
   * @param name
   *          the property name
   * @return the property, or {@code null} if none could be found
   */
  @Override
  public abstract IProperty find(final String name);

  /**
   * Create a property setting based on the given collection of values. All
   * values not contained in {@code values} are considered as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Parameter#getGeneralized()
   * generalized}
   * 
   * @param values
   *          the set of property values
   * @return the property setting
   */
  public abstract IPropertySetting createSettingFromValues(
      final Iterable<? extends IPropertyValue> values);

  /**
   * Create a property setting based on the given mapping of property names
   * to values. All values not contained in {@code values} are considered
   * as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Parameter#getGeneralized()
   * generalized}
   * 
   * @param values
   *          the mapping of names to values
   * @return the property setting
   */
  public abstract IPropertySetting createSettingFromMapping(
      final Map<String, Object> values);

  /**
   * Create a property setting based on the given mapping of property names
   * to values. All values not contained in {@code values} are considered
   * as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Parameter#getGeneralized()
   * generalized}
   * 
   * @param values
   *          the mapping of names to values
   * @return the property setting
   */
  public abstract IPropertySetting createSettingFromMapping(
      final Map.Entry<String, Object>[] values);

  /**
   * Create a property setting based on the given mapping of property names
   * to values. All values not contained in {@code values} are considered
   * as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Parameter#getGeneralized()
   * generalized}
   * 
   * @param values
   *          the mapping of names to values
   * @return the property setting
   */
  public abstract IPropertySetting createSettingFromMapping(
      final Iterable<Map.Entry<String, Object>> values);

  /**
   * Create a property setting based on the given collection of values. All
   * values not contained in {@code values} are considered as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Parameter#getGeneralized()
   * generalized}
   * 
   * @param values
   *          the set of property values
   * @return the property setting
   */
  public abstract IPropertySetting createSettingFromValues(
      final IPropertyValue... values);
}
