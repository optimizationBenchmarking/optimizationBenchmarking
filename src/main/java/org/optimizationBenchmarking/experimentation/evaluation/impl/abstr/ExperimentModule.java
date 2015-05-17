package org.optimizationBenchmarking.experimentation.evaluation.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IExperimentJobBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IExperimentModule;
import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * The abstract basic implementation for experiment modules.
 */
public abstract class ExperimentModule extends
    _EvaluationModule<IExperiment> implements IExperimentModule {

  /** create */
  protected ExperimentModule() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final IExperimentJobBuilder use() {
    this.checkCanUse();
    return new _ExperimentJobBuilder(this);
  }

  /**
   * Create the experiment job.
   *
   * @param data
   *          the experiment to be processed by the job
   * @param config
   *          the configuration
   * @param logger
   *          the logger to write log information to, or {@code null} if no
   *          log info should be written
   */
  @Override
  public abstract ExperimentJob createJob(IExperiment data,
      Configuration config, Logger logger);
}
