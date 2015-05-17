package org.optimizationBenchmarking.experimentation.evaluation.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * A job builder for experiment evaluation jobs.
 */
public interface IExperimentJobBuilder extends IEvaluationJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IExperimentJobBuilder setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IExperimentJobBuilder configure(
      final Configuration config);

  /**
   * Set the data to be evaluated
   *
   * @param data
   *          the data to be evaluated
   * @return this builder
   */
  public abstract IExperimentJobBuilder setData(final IExperiment data);
}
