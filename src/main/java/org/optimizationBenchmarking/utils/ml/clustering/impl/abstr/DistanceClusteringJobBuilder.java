package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDataClusteringJobBuilder;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDistanceClusteringJobBuilder;

/** The base class for distance clustering job builders. */
public final class DistanceClusteringJobBuilder
    extends ClusteringJobBuilder<DistanceClusteringJobBuilder>
    implements IDistanceClusteringJobBuilder, IDataClusteringJobBuilder {

  /**
   * is the matrix a distance matrix ({@code true}) or a data matrix (
   * {@code false})?
   */
  boolean m_matrixIsDistanceMatrix;

  /**
   * create the clustering job builder
   *
   * @param clusterer
   *          the owning clusterer
   */
  DistanceClusteringJobBuilder(final DistanceClusterer clusterer) {
    super(clusterer);
  }

  /** {@inheritDoc} */
  @Override
  protected void validate() {
    super.validate();
    ClusteringJobBuilder.checkMatrix(this.m_matrix,
        this.m_matrixIsDistanceMatrix);
  }

  /** {@inheritDoc} */
  @Override
  public final DistanceClusteringJobBuilder setDistanceMatrix(
      final IMatrix matrix) {
    this.setMatrix(matrix, true);
    this.m_matrixIsDistanceMatrix = true;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final DistanceClusteringJobBuilder setData(final IMatrix matrix) {
    this.setMatrix(matrix, false);
    this.m_matrixIsDistanceMatrix = false;
    return this;
  }
}
