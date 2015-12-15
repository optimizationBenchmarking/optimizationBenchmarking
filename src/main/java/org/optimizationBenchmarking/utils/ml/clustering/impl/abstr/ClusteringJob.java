package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringJob;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/** The base class for clustering jobs. */
public abstract class ClusteringJob extends ToolJob
    implements IClusteringJob {

  /** the number of classes, {@code -1} if unspecified */
  protected final int m_classes;

  /** the matrix representing the data to be clustered */
  protected IMatrix m_matrix;

  /**
   * create the clustering job
   *
   * @param builder
   *          the job builder
   * @param isMatrixDistance
   *          is the matrix a distance matrix?
   */
  protected ClusteringJob(final ClusteringJobBuilder<?> builder,
      final boolean isMatrixDistance) {
    super(builder);

    ClusteringJobBuilder._checkClusterNumber(//
        this.m_classes = builder.m_classes, true);
    ClusteringJobBuilder.checkMatrix(this.m_matrix = builder.m_matrix,
        isMatrixDistance);
  }

  /**
   * Perform the clustering and return a solution record. The result of
   * this method will be automatically
   * {@link ClusteringTools#normalizeClusters(int[])} normalized.
   *
   * @return the solution
   * @throws Exception
   *           if something goes wrong
   */
  protected abstract ClusteringSolution cluster() throws Exception;

  /** {@inheritDoc} */
  @Override
  public final IClusteringResult call() throws IllegalArgumentException {
    ClusteringSolution solution;
    Throwable error;
    String message;

    error = null;
    try {
      solution = this.cluster();
      if (MathUtils.isFinite(solution.quality)) {
        ClusteringTools.normalizeClusters(solution.assignment);
        return solution;
      }
    } catch (final IllegalArgumentException iae) {
      throw iae;
    } catch (final Throwable cause) {
      error = cause;
    } finally {
      this.m_matrix = null;
    }

    message = (("Could not cluster the data with method ") //$NON-NLS-1$
        + this + '.');
    if (error != null) {
      throw new IllegalArgumentException(message, error);
    }
    throw new IllegalArgumentException(message);
  }
}
