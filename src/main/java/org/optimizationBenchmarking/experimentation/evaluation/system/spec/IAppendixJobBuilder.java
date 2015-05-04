package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * A job builder for appendix jobs.
 */
public interface IAppendixJobBuilder extends IEvaluationJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IAppendixJobBuilder setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IAppendixJobBuilder configure(final Configuration config);

  /**
   * Set the data to be evaluated
   *
   * @param data
   *          the data to be evaluated
   * @return this builder
   */
  public abstract IAppendixJobBuilder setData(final IExperimentSet data);
}
