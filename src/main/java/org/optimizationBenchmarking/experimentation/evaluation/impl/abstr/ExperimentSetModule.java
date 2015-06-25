package org.optimizationBenchmarking.experimentation.evaluation.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationJob;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IExperimentSetJobBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IExperimentSetModule;
import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * The abstract basic implementation for experiment set modules.
 */
public abstract class ExperimentSetModule extends
    _EvaluationModule<IExperimentSet> implements IExperimentSetModule {

  /** create */
  protected ExperimentSetModule() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final IExperimentSetJobBuilder use() {
    this.checkCanUse();
    return new _ExperimentSetJobBuilder(this);
  }

  /**
   * Create the experiment set job.
   *
   * @param data
   *          the experiment set to be processed by the job
   * @param config
   *          the configuration
   * @param logger
   *          the logger to write log information to, or {@code null} if no
   *          log info should be written
   */
  @Override
  public abstract IEvaluationJob createJob(IExperimentSet data,
      Configuration config, Logger logger);
}
