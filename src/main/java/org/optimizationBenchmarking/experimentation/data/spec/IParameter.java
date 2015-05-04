package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * The interface for parameters of an experiment.
 */
public interface IParameter extends IProperty {
  /**
   * Get the owning parameter set
   *
   * @return the owning parameter set
   */
  @Override
  public abstract IParameterSet getOwner();

  /**
   * Get the set of parameter values
   *
   * @return the set of parameter values
   */
  @Override
  public abstract ArrayListView<? extends IParameterValue> getData();

  /**
   * Obtain a parameter value fitting to a given value object
   *
   * @param value
   *          the parameter value object
   * @return the parameter value, or {@code null} if none could be found
   */
  @Override
  public abstract IParameterValue findValue(final Object value);

  /**
   * The parameter value record indicating generalization
   *
   * @return the parameter value record indicating generalization
   */
  @Override
  public abstract IParameterValue getGeneralized();

  /**
   * Obtain the parameter value record indicating an unspecified value, or
   * {@code null} if all experiments have a specific value for this
   * parameter.
   *
   * @return the parameter value record indicating an unspecified value, or
   *         {@code null} if all experiments have a specific value for this
   *         parameter.
   */
  public abstract IParameterValue getUnspecified();
}
