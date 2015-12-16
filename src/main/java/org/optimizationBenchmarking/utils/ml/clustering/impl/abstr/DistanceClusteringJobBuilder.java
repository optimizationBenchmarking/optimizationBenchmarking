package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.clustering.impl.dist.EuclideanDistance;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDataClusteringJobBuilder;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDistanceClusteringJobBuilder;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IDistanceMeasure;

/** The base class for distance clustering job builders. */
public class DistanceClusteringJobBuilder
    extends ClusteringJobBuilder<DistanceClusteringJobBuilder>
    implements IDistanceClusteringJobBuilder, IDataClusteringJobBuilder {

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

  /**
   * Create the distance measure to be used for converting data matrices to
   * distance matrices in {@link #setData(IMatrix)}.
   *
   * @return the distance measure
   */
  protected IDistanceMeasure createDistanceMeasure() {
    return new EuclideanDistance();
  }

  /** {@inheritDoc} */
  @Override
  public final DistanceClusteringJobBuilder setData(final IMatrix matrix) {
    ClusteringJobBuilder.checkMatrix(matrix, false);
    return this.setDistanceMatrix(ClusteringTools.dataToDistanceMatrix(//
        matrix, this.createDistanceMeasure()));
  }
}
