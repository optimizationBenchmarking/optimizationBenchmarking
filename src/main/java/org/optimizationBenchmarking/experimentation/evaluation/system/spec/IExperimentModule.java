package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

/**
 * A module which processes single experiments and writes data about them
 * to the output.
 */
public interface IExperimentModule extends IEvaluationModule {

  /** {@inheritDoc} */
  @Override
  public abstract IExperimentJobBuilder use();

}
