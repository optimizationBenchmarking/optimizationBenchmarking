package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import org.optimizationBenchmarking.utils.ml.clustering.spec.IDistanceClusterer;

/** The base class for distance clustering tools. */
public abstract class DistanceClusterer extends
    Clusterer<DataClusteringJobBuilder> implements IDistanceClusterer {

  /** create */
  protected DistanceClusterer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final DistanceClusteringJobBuilder use() {
    return new DistanceClusteringJobBuilder(this);
  }
}