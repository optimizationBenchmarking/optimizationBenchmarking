package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * A job builder for experiment set evaluation jobs.
 */
public interface IExperimentSetJobBuilder extends IEvaluationJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IExperimentSetJobBuilder setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IExperimentSetJobBuilder configure(
      final Configuration config);

  /**
   * Set the data to be evaluated
   * 
   * @param data
   *          the data to be evaluated
   * @return this builder
   */
  public abstract IExperimentSetJobBuilder setData(
      final IExperimentSet data);
}
