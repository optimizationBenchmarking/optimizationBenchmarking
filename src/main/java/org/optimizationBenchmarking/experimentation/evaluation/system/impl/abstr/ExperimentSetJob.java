package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSet;

/**
 * The kind of job which can carry out the work of an
 * {@link org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentSetModule}
 * .
 */
public abstract class ExperimentSetJob extends
    _EvaluationJob<ExperimentSet> {
  /**
   * Create the experiment set evaluation job
   * 
   * @param data
   *          the data
   * @param logger
   *          the logger
   */
  protected ExperimentSetJob(final ExperimentSet data, final Logger logger) {
    super(data, logger);
  }
}
