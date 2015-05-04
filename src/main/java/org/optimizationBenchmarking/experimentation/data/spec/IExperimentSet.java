package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** The interface describing experiment sets */
public interface IExperimentSet extends INamedElementSet {

  /**
   * Get the experiments of this experiment set
   *
   * @return experiments of this experiment set
   */
  @Override
  public abstract ArrayListView<? extends IExperiment> getData();

  /**
   * Get the set of measurement dimensions
   *
   * @return the set of measurement dimensions
   */
  public abstract IDimensionSet getDimensions();

  /**
   * Get the set of problem instances
   *
   * @return the set of problem instances
   */
  public abstract IInstanceSet getInstances();

  /**
   * Get the set of instance features
   *
   * @return the set of instance features
   */
  public abstract IFeatureSet getFeatures();

  /**
   * Get the set of parameters
   *
   * @return the set of parameters
   */
  public abstract IParameterSet getParameters();

  /**
   * Find the experiment of the given name
   *
   * @return the experiment with the given name, or {@code null} if none
   *         was found of that name
   */
  @Override
  public abstract IExperiment find(final String name);
}
