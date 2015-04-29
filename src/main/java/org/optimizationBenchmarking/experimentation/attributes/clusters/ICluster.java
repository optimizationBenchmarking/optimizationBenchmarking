package org.optimizationBenchmarking.experimentation.attributes.clusters;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.ISemanticComponent;

/**
 * A cluster is a sub-set of an experiment set.
 */
public interface ICluster extends IExperimentSet, ISemanticComponent {

  /**
   * Obtain the owning clustering
   * 
   * @return the owning clustering
   */
  @Override
  public abstract IClustering getOwner();
}
