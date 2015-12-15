package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDistanceClusteringJobBuilder;

/** The base class for distance clustering job builders. */
public class DistanceClusteringJobBuilder
    extends ClusteringJobBuilder<DistanceClusteringJobBuilder>
    implements IDistanceClusteringJobBuilder {

  /**
   * create the clustering job builder
   *
   * @param clusterer
   *          the owning clusterer
   */
  protected DistanceClusteringJobBuilder(
      final DistanceClusterer clusterer) {
    super(clusterer);
  }

  /** {@inheritDoc} */
  @Override
  protected void validate() {
    super.validate();
    ClusteringJobBuilder.checkMatrix(this.m_matrix, true);
  }

  /** {@inheritDoc} */
  @Override
  public final DistanceClusteringJobBuilder setDistanceMatrix(
      final IMatrix matrix) {
    return this.setMatrix(matrix, true);
  }
}
