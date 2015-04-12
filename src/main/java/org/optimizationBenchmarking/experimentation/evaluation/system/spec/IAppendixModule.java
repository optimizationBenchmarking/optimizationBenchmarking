package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

/**
 * A module which adds some information to the appendix. This might be
 * detailed algorithms, program code, elaborate or verbose explanations,
 * etc.
 */
public interface IAppendixModule extends IEvaluationModule {

  /** {@inheritDoc} */
  @Override
  public abstract IAppendixJobBuilder use();

}
