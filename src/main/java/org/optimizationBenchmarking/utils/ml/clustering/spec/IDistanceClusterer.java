package org.optimizationBenchmarking.utils.ml.clustering.spec;

/** The distance clustering tool */
public interface IDistanceClusterer extends IClusterer {

  /** {@inheritDoc} */
  @Override
  public abstract IDistanceClusteringJobBuilder use();
}
