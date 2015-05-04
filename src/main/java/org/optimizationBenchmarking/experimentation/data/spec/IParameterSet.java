package org.optimizationBenchmarking.experimentation.data.spec;

import java.util.Map;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * The set of experiment parameters.
 */
public interface IParameterSet extends IPropertySet {
  /**
   * Get the parameters of this parameter set
   *
   * @return the parameters of this parameter set
   */
  @Override
  public abstract ArrayListView<? extends IParameter> getData();

  /**
   * Obtain a parameter fitting to a given name
   *
   * @param name
   *          the parameter name
   * @return the parameter, or {@code null} if none could be found
   */
  @Override
  public abstract IParameter find(final String name);

  /**
   * Create a parameter setting based on the given collection of values.
   * All values not contained in {@code values} are considered as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Parameter#getGeneralized()
   * generalized}
   *
   * @param values
   *          the set of parameter values
   * @return the parameter setting
   */
  @Override
  public abstract IParameterSetting createSettingFromValues(
      final Iterable<? extends IPropertyValue> values);

  /**
   * Create a parameter setting based on the given mapping of parameter
   * names to values. All values not contained in {@code values} are
   * considered as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Parameter#getGeneralized()
   * generalized}
   *
   * @param values
   *          the mapping of names to values
   * @return the parameter setting
   */
  @Override
  public abstract IParameterSetting createSettingFromMapping(
      final Map<String, Object> values);

  /**
   * Create a parameter setting based on the given mapping of parameter
   * names to values. All values not contained in {@code values} are
   * considered as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Parameter#getGeneralized()
   * generalized}
   *
   * @param values
   *          the mapping of names to values
   * @return the parameter setting
   */
  @Override
  public abstract IParameterSetting createSettingFromMapping(
      final Map.Entry<String, Object>[] values);

  /**
   * Create a parameter setting based on the given mapping of parameter
   * names to values. All values not contained in {@code values} are
   * considered as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Parameter#getGeneralized()
   * generalized}
   *
   * @param values
   *          the mapping of names to values
   * @return the parameter setting
   */
  @Override
  public abstract IParameterSetting createSettingFromMapping(
      final Iterable<Map.Entry<String, Object>> values);

  /**
   * Create a parameter setting based on the given collection of values.
   * All values not contained in {@code values} are considered as
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Parameter#getGeneralized()
   * generalized}
   *
   * @param values
   *          the set of parameter values
   * @return the parameter setting
   */
  @Override
  public abstract IParameterSetting createSettingFromValues(
      final IPropertyValue... values);
}
