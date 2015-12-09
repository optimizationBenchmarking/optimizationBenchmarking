package org.optimizationBenchmarking.utils.ml.clustering.spec;

import java.util.concurrent.Callable;

import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/** A job of the clusterer. */
public interface IClusteringJob
    extends IToolJob, Callable<IClusteringResult> {
  /**
   * Perform the clustering of the data.
   *
   * @return the clustering result
   * @throws IllegalArgumentException
   *           if the input data could not be clustered
   */
  @Override
  public abstract IClusteringResult call() throws IllegalArgumentException;
}
