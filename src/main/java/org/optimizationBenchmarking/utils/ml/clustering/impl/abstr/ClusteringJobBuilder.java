package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringJob;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringJobBuilder;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;

/**
 * The base class for clustering job builders.
 *
 * @param <R>
 *          the return type of the setter methods
 */
public class ClusteringJobBuilder<R extends ClusteringJobBuilder<R>>
    extends ToolJobBuilder<IClusteringJob, R>
    implements IClusteringJobBuilder {

  /** the owning clusterer */
  private final Clusterer<?> m_clusterer;

  /** the number of classes, {@code -1} if unspecified */
  int m_classes;

  /** the matrix */
  IMatrix m_matrix;

  /**
   * create the clustering job builder
   *
   * @param clusterer
   *          the owning clusterer
   */
  protected ClusteringJobBuilder(final Clusterer<?> clusterer) {
    super();
    this.m_classes = (-1);
    this.m_clusterer = clusterer;
  }

  /**
   * Set the internal matrix. Regardless whether this is a distance or a
   * data clustering job builder, there can be at most one matrix.
   *
   * @param matrix
   *          the matrix to set
   * @param isDistance
   *          is the matrix a distance matrix
   * @return this builder
   */
  @SuppressWarnings("unchecked")
  protected final R setMatrix(final IMatrix matrix,
      final boolean isDistance) {
    ClusteringJobBuilder.checkMatrix(matrix, isDistance);
    this.m_matrix = matrix;
    return ((R) this);
  }

  /**
   * check a matrix
   *
   * @param matrix
   *          the matrix
   * @param isDistance
   *          is the matrix a distance matrix?
   */
  protected static final void checkMatrix(final IMatrix matrix,
      final boolean isDistance) {
    final int m, n;
    if (matrix == null) {
      throw new IllegalArgumentException("Matrix cannot be null."); //$NON-NLS-1$
    }
    if ((m = matrix.m()) <= 0) {
      throw new IllegalArgumentException(
          "Matrix must have at least one row."); //$NON-NLS-1$
    }
    if ((n = matrix.n()) <= 0) {
      throw new IllegalArgumentException(
          "Matrix must have at least one column."); //$NON-NLS-1$
    }
    if (isDistance && (m != n)) {
      throw new IllegalArgumentException(//
          "Distance/disimilarity matrix must be quadratic, but you specified an "//$NON-NLS-1$
              + m + " by " + n + " matrix."); //$NON-NLS-1$//$NON-NLS-2$
    }
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
  @SuppressWarnings("unchecked")
  @Override
  public final R setClusterNumber(final int number) {
    ClusteringJobBuilder._checkClusterNumber(number, false);
    this.m_classes = number;
    return ((R) this);
  }

  /**
   * Get this builder's matrix
   *
   * @return this builder's matrix
   */
  protected final IMatrix getMatrix() {
    return this.m_matrix;
  }

  /** {@inheritDoc} */
  @Override
  protected void validate() {
    super.validate();
    ClusteringJobBuilder._checkClusterNumber(this.m_classes, true);
  }

  /**
   * Get the number of classes or clusters we want.
   *
   * @return the number of clusters, or {@code -1} if unspecified
   */
  public final int getClusterNumber() {
    return this.m_classes;
  }

  /** {@inheritDoc} */
  @Override
  public final IClusteringJob create() {
    this.validate();
    return this.m_clusterer._create(this);
  }
}
