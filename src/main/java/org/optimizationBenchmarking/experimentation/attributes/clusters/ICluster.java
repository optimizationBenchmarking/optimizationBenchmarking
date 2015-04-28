package org.optimizationBenchmarking.experimentation.attributes.clusters;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;

/**
 * A cluster is a sub-set of an experiment set.
 */
public interface ICluster extends IExperimentSet {

  /**
   * Obtain the owning clustering
   * 
   * @return the owning clustering
   */
  @Override
  public abstract IClustering getOwner();

  /**
   * Obtain a suggestion for a path component of figures drawn based on
   * this clustering
   * 
   * @return a suggestion for a path component of figures drawn based on
   *         this clustering
   */
  public abstract String getPathComponentSuggestion();
}
