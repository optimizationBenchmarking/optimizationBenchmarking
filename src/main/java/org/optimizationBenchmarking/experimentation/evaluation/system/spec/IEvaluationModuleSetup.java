package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.tools.spec.IConfigurableToolJobBuilder;

/**
 * The process of setting up a module.
 */
public interface IEvaluationModuleSetup extends
    IConfigurableToolJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IEvaluationModuleSetup setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IEvaluationModuleSetup configure(
      final Configuration config);

  /** {@inheritDoc} */
  @Override
  public abstract IConfiguredModule create();
}
