package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

/**
 * A statistic module dealing with experiments.
 */
public interface IExperimentModule extends IEvaluationModule {

  /** {@inheritDoc} */
  @Override
  public abstract IExperimentModuleSetup use();
}
