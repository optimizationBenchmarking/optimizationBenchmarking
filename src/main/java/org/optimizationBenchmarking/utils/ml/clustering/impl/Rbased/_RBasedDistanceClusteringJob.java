package org.optimizationBenchmarking.utils.ml.clustering.impl.Rbased;

import java.io.IOException;

import org.optimizationBenchmarking.utils.io.StreamLineIterator;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.R.R;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.R.REngine;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleDistanceMatrix1D;
import org.optimizationBenchmarking.utils.ml.clustering.impl.abstr.ClusteringSolution;
import org.optimizationBenchmarking.utils.ml.clustering.impl.abstr.DistanceClusteringJob;
import org.optimizationBenchmarking.utils.ml.clustering.impl.abstr.DistanceClusteringJobBuilder;

/** The {@code R}-based distance clustering job. */
final class _RBasedDistanceClusteringJob extends DistanceClusteringJob {
  /**
   * create the clustering job
   *
   * @param builder
   *          the job builder
   */
  _RBasedDistanceClusteringJob(
      final DistanceClusteringJobBuilder builder) {
    super(builder);
  }

  /** {@inheritDoc} */
  @Override
  protected final ClusteringSolution cluster() throws Exception {
    final IMatrix result;
    final double quality;
    final ClusteringSolution solution;
    int n;

    try (final REngine engine = R.getInstance().use()
        .setLogger(this.getLogger()).create()) {

      engine.setMatrix("distance", //$NON-NLS-1$
          (this.m_matrix instanceof DoubleDistanceMatrix1D)//
              ? ((DoubleDistanceMatrix1D) (this.m_matrix)).asRowVector()//
              : this.m_matrix);
      engine.setLong("m", this.m_matrix.m()); //$NON-NLS-1$
      this.m_matrix = null;
      engine.setLong("nCluster", this.m_classes);//$NON-NLS-1$
      try {
        try (final StreamLineIterator iterator = new StreamLineIterator(//
            _RBasedDistanceClusteringJob.class, "distanceCluster.txt")) {//$NON-NLS-1$
          engine.execute(iterator);
        }
      } catch (final Throwable error) {
        throw new IllegalStateException(//
            "Error while communicating REngine. Maybe the distance matrix is just too odd, or some required packages are missing and cannot be installed.", //$NON-NLS-1$
            error);
      }

      result = engine.getMatrix("clusters"); //$NON-NLS-1$
      quality = engine.getDouble("quality"); //$NON-NLS-1$
    } catch (final IOException ioe) {
      throw new IllegalStateException(//
          "Error while starting REngine. Maybe R is not installed properly?", //$NON-NLS-1$
          ioe);
    } finally {
      this.m_matrix = null;
    }

    n = result.n();
    solution = new ClusteringSolution(new int[n], quality);
    for (; (--n) >= 0;) {
      solution.assignment[n] = ((int) (result.getLong(0, n)));
    }

    return solution;
  }
}