package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.Experiment;

/**
 * The kind of job which can carry out the work of an
 * {@link org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentModule}
 * .
 */
public abstract class ExperimentJob extends _EvaluationJob<Experiment> {
  /**
   * Create the experiment evaluation job
   * 
   * @param data
   *          the data
   * @param logger
   *          the logger
   */
  protected ExperimentJob(final Experiment data, final Logger logger) {
    super(data, logger);
  }
}
