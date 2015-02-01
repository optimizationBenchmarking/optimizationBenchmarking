package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * The setup for an experiment module.
 */
public interface IExperimentModuleSetup extends IEvaluationModuleSetup {

  /** {@inheritDoc} */
  @Override
  public abstract IExperimentModuleSetup setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IExperimentModuleSetup configure(
      final Configuration config);

  /** {@inheritDoc} */
  @Override
  public abstract IConfiguredExperimentModule create();

}
