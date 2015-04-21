package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** A set of runs for a given instance */
public interface IInstanceRuns extends IElementSet {

  /**
   * Get the owning experiment
   * 
   * @return the owning experiment
   */
  @Override
  public abstract IExperiment getOwner();

  /**
   * Get the instance to which this run set belongs
   * 
   * @return the instance to which this run set belongs
   */
  public abstract IInstance getInstance();

  /**
   * Get the runs of this instance runs set
   * 
   * @return the runs of this instance runs set
   */
  @Override
  public abstract ArraySetView<? extends IRun> getData();
}
