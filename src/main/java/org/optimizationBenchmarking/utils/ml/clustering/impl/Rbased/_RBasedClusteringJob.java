package org.optimizationBenchmarking.utils.ml.clustering.impl.Rbased;

import java.io.IOException;

import org.optimizationBenchmarking.utils.io.StreamLineIterator;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.R.R;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.R.REngine;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.clustering.impl.ref.ClusteringJob;
import org.optimizationBenchmarking.utils.ml.clustering.impl.ref.ClusteringJobBuilder;
import org.optimizationBenchmarking.utils.ml.clustering.impl.ref.ClusteringSolution;

/** The {@code R}-based clustering job. */
final class _RBasedClusteringJob extends ClusteringJob {
  /**
   * create the clustering job
   *
   * @param builder
   *          the job builder
   */
  _RBasedClusteringJob(final ClusteringJobBuilder builder) {
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
      engine.setMatrix("data", this.m_matrix);//$NON-NLS-1$
      this.m_matrix = null;
      engine.setLong("nCluster", this.m_classes);//$NON-NLS-1$
      try {
        try (final StreamLineIterator iterator = new StreamLineIterator(//
            _RBasedClusteringJob.class, "cluster.txt")) {//$NON-NLS-1$
          engine.execute(iterator);
        }
      } catch (final Throwable error) {
        throw new IllegalStateException(//
            "Error while communicating REngine. Maybe the data is just too odd, or some required packages are missing and cannot be installed.", //$NON-NLS-1$
            error);
      }

      result = engine.getMatrix("clusters"); //$NON-NLS-1$
      quality = engine.getDouble("quality"); //$NON-NLS-1$
    } catch (final IOException ioe) {
      throw new IllegalStateException(//
          "Error while starting REngine. Maybe R is not installed properly?", //$NON-NLS-1$
          ioe);
    }

    n = result.n();
    solution = new ClusteringSolution(new int[n], quality);
    for (; (--n) >= 0;) {
      solution.assignment[n] = ((int) (result.getLong(0, n)));
    }

    return solution;
  }
}