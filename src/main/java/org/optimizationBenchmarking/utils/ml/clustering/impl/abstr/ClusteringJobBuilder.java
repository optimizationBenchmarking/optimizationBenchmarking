package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringJobBuilder;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;

/** The base class for clustering job builders. */
public class ClusteringJobBuilder
    extends ToolJobBuilder<ClusteringJob, ClusteringJobBuilder>
    implements IClusteringJobBuilder {

  /** the owning clusterer */
  private final Clusterer m_clusterer;

  /** the data matrix */
  IMatrix m_matrix;

  /** the number of classes, {@code -1} if unspecified */
  int m_classes;

  /**
   * create the clustering job builder
   *
   * @param clusterer
   *          the owning clusterer
   */
  protected ClusteringJobBuilder(final Clusterer clusterer) {
    super();
    this.m_classes = (-1);
    this.m_clusterer = clusterer;
  }

  /**
   * check a matrix
   *
   * @param matrix
   *          the matrix
   */
  static final void _checkMatrix(final IMatrix matrix) {
    if (matrix == null) {
      throw new IllegalArgumentException("Matrix cannot be null."); //$NON-NLS-1$
    }
    if (matrix.m() <= 0) {
      throw new IllegalArgumentException(
          "Matrix must have at least one row."); //$NON-NLS-1$
    }
    if (matrix.n() <= 0) {
      throw new IllegalArgumentException(
          "Matrix must have at least one column."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final ClusteringJobBuilder setData(final IMatrix matrix) {
    ClusteringJobBuilder._checkMatrix(matrix);
    this.m_matrix = matrix;
    return this;
  }

  /**
   * check a cluster number
   *
   * @param number
   *          the number of clusters
   * @param undefAllowed
   */
  static final void _checkClusterNumber(final int number,
      final boolean undefAllowed) {
    if (number <= 0) {
      if (undefAllowed && (number == (-1))) {
        return;
      }
      throw new IllegalArgumentException(//
          "Invalid cluster number, must be at least one, but you specified " //$NON-NLS-1$
              + number);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final IClusteringJobBuilder setClusterNumber(final int number) {
    ClusteringJobBuilder._checkClusterNumber(number, false);
    this.m_classes = number;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected void validate() {
    super.validate();
    ClusteringJobBuilder._checkClusterNumber(this.m_classes, true);
    ClusteringJobBuilder._checkMatrix(this.m_matrix);
  }

  /**
   * Get the number of classes or clusters we want.
   *
   * @return the number of clusters, or {@code -1} if unspecified
   */
  public final int getClusterNumber() {
    return this.m_classes;
  }

  /**
   * Get the data matrix
   *
   * @return the data to be clustered
   */
  public final IMatrix getData() {
    return this.m_matrix;
  }

  /** {@inheritDoc} */
  @Override
  public final ClusteringJob create() {
    return this.m_clusterer.create(this);
  }

}
