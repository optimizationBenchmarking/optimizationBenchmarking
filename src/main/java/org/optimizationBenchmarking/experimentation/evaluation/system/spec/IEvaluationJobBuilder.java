package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.IConfigurable;
import org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder;

/**
 * A job builder for evaluation jobs.
 */
public interface IEvaluationJobBuilder extends IToolJobBuilder,
IConfigurable {

  /** {@inheritDoc} */
  @Override
  public abstract IEvaluationJobBuilder setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IEvaluationJobBuilder configure(
      final Configuration config);

  /** {@inheritDoc} */
  @Override
  public abstract IEvaluationJob create() throws Exception;
}
