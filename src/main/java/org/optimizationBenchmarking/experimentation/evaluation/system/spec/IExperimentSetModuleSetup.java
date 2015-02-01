package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * The setup for an experiment-set module.
 */
public interface IExperimentSetModuleSetup extends IEvaluationModuleSetup {

  /** {@inheritDoc} */
  @Override
  public abstract IExperimentSetModuleSetup setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IExperimentSetModuleSetup configure(
      final Configuration config);

  /** {@inheritDoc} */
  @Override
  public abstract IConfiguredExperimentSetModule create();

}
