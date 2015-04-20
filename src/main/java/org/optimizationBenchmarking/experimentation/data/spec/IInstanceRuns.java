package org.optimizationBenchmarking.experimentation.data.spec;

/** A set of runs for a given instance */
public interface IInstanceRuns extends IRuns {

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

}
