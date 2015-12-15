package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDataClusteringJobBuilder;

/** The base class for data clustering job builders. */
public class DataClusteringJobBuilder
    extends ClusteringJobBuilder<DataClusteringJobBuilder>
    implements IDataClusteringJobBuilder {

  /**
   * create the clustering job builder
   *
   * @param clusterer
   *          the owning clusterer
   */
  protected DataClusteringJobBuilder(final DataClusterer clusterer) {
    super(clusterer);
  }

  /** {@inheritDoc} */
  @Override
  protected void validate() {
    super.validate();
    ClusteringJobBuilder.checkMatrix(this.m_matrix, false);
  }

  /** {@inheritDoc} */
  @Override
  public final DataClusteringJobBuilder setData(final IMatrix matrix) {
    return this.setMatrix(matrix, false);
  }
}
