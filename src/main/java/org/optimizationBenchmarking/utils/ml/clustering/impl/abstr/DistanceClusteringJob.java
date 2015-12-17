package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import org.optimizationBenchmarking.utils.ml.clustering.impl.dist.EuclideanDistance;
import org.optimizationBenchmarking.utils.ml.clustering.impl.dist.MeasureBasedDistanceMatrixBuilder;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDistanceMeasure;

/** The base class for distance-based clustering jobs. */
public abstract class DistanceClusteringJob extends ClusteringJob {
  /**
   * is the matrix a distance matrix ({@code true}) or a data matrix (
   * {@code false})?
   */
  private final boolean m_matrixIsDistanceMatrix;

  /**
   * create the clustering job
   *
   * @param builder
   *          the job builder
   */
  protected DistanceClusteringJob(
      final DistanceClusteringJobBuilder builder) {
    super(builder, builder.m_matrixIsDistanceMatrix);
    this.m_matrixIsDistanceMatrix = builder.m_matrixIsDistanceMatrix;
  }

  /**
   * Create the distance measure to be used for converting data matrices to
   * distance matrices.
   *
   * @return the distance measure
   */
  protected IDistanceMeasure createDistanceMeasure() {
    return new EuclideanDistance();
  }

  /** {@inheritDoc} */
  @Override
  final ClusteringSolution _cluster() throws Exception {
    if (!(this.m_matrixIsDistanceMatrix)) {
      this.m_matrix = new MeasureBasedDistanceMatrixBuilder(this.m_matrix,
          this.createDistanceMeasure()).call();
    }
    return this.cluster();
  }
}
