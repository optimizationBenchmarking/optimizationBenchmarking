package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

/**
 * A statistic module dealing with experiment sets.
 */
public interface IExperimentSetModule extends IEvaluationModule {

  /** {@inheritDoc} */
  @Override
  public abstract IExperimentSetModuleSetup use();
}
