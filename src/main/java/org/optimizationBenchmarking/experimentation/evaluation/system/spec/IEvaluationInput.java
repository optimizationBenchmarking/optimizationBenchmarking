package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;

/**
 * A source for data to be used in the evaluation process.
 */
public interface IEvaluationInput {

  /**
   * Obtain the experiment set to be evaluated. This method must only be
   * called at most once, otherwise it may throw an
   * {@link java.lang.IllegalStateException}.
   *
   * @return the experiment set
   * @throws Exception
   *           if something fails
   */
  public abstract IExperimentSet getExperimentSet() throws Exception;
}
