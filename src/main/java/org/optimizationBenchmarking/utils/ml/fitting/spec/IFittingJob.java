package org.optimizationBenchmarking.utils.ml.fitting.spec;

import java.util.concurrent.Callable;

import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/**
 * The fitting job interface. A fitting job can return an instance of the
 * {@link org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingResult}
 * .
 */
public interface IFittingJob extends IToolJob, Callable<IFittingResult> {
  /**
   * Fit the model to the provided data and return the fitting result.
   *
   * @return the fitting result
   * @throws IllegalArgumentException
   *           if the data is invalid or it is impossible to fit the model
   *           to it
   */
  @Override
  public abstract IFittingResult call() throws IllegalArgumentException;
}
