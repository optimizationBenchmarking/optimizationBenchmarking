package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An experiment.
 */
public interface IExperiment extends INamedElement, IDataSet {

  /**
   * Get the owning experiment set
   * 
   * @return the owning experiment set
   */
  @Override
  public abstract IExperimentSet getOwner();

  /**
   * Get the instance runs of this experiment
   * 
   * @return the instance runs of this experiment
   */
  @Override
  public abstract ArraySetView<? extends IInstanceRuns> getData();

  /**
   * Get the parameter map.
   * 
   * @return the parameter map.
   */
  public abstract IParameterSetting getParameterSetting();

}
