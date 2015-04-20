package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** The interface describing experiment sets */
public interface IExperimentSet extends IDataSet {

  /**
   * Get the experiments of this experiment set
   * 
   * @return experiments of this experiment set
   */
  @Override
  public abstract ArraySetView<? extends IExperiment> getData();

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

}
