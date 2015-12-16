package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import org.optimizationBenchmarking.utils.ml.clustering.spec.IDataClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDistanceClusterer;

/** The base class for distance clustering tools. */
public abstract class DistanceClusterer
    extends Clusterer<DistanceClusteringJobBuilder>
    implements IDistanceClusterer, IDataClusterer {

  /** create */
  protected DistanceClusterer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final DistanceClusteringJobBuilder use() {
    this.checkCanUse();
    return new DistanceClusteringJobBuilder(this);
  }
}