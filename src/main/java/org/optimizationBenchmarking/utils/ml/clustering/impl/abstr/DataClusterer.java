package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import org.optimizationBenchmarking.utils.ml.clustering.spec.IDataClusterer;

/** The base class for data clustering tools. */
public abstract class DataClusterer
    extends Clusterer<DataClusteringJobBuilder> implements IDataClusterer {

  /** create */
  protected DataClusterer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final DataClusteringJobBuilder use() {
    return new DataClusteringJobBuilder(this);
  }
}