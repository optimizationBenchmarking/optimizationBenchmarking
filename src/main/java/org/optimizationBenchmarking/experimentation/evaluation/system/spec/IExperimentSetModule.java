package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

/**
 * A module which processes a set of experiments and writes data about them
 * to the output.
 */
public interface IExperimentSetModule extends IEvaluationModule {

  /** {@inheritDoc} */
  @Override
  public abstract IExperimentSetJobBuilder use();

}
