package org.optimizationBenchmarking.utils.ml.clustering.spec;

/** The data clustering tool */
public interface IDataClusterer extends IClusterer {

  /** {@inheritDoc} */
  @Override
  public abstract IDataClusteringJobBuilder use();
}
