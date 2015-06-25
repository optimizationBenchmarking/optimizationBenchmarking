package org.optimizationBenchmarking.experimentation.evaluation.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;

/**
 * The kind of job which can carry out the work of an
 * {@link org.optimizationBenchmarking.experimentation.evaluation.spec.IDescriptionModule}
 * .
 */
public abstract class DescriptionJob extends EvaluationJob<IExperimentSet> {
  /**
   * Create the description evaluation job
   *
   * @param data
   *          the data
   * @param logger
   *          the logger
   */
  protected DescriptionJob(final IExperimentSet data, final Logger logger) {
    super(data, logger);
  }
}
