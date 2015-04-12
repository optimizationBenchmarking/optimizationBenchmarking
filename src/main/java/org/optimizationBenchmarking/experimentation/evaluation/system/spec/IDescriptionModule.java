package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

/**
 * A module which provides some general descriptions about the experimental
 * procedure, the evaluation process, the benchmark instances, the
 * statistical methods applied, etc.
 */
public interface IDescriptionModule extends IEvaluationModule {

  /** {@inheritDoc} */
  @Override
  public abstract IDescriptionJobBuilder use();

}
