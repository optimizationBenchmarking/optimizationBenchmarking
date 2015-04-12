package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * A job builder for description jobs.
 */
public interface IDescriptionJobBuilder extends IEvaluationJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IDescriptionJobBuilder setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IDescriptionJobBuilder configure(
      final Configuration config);

  /**
   * Set the data to be evaluated
   * 
   * @param data
   *          the data to be evaluated
   * @return this builder
   */
  public abstract IDescriptionJobBuilder setData(final ExperimentSet data);
}
