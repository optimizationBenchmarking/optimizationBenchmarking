package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentJobBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentModule;
import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * The abstract basic implementation for experiment modules.
 */
public abstract class ExperimentModule extends
    _EvaluationModule<Experiment> implements IExperimentModule {

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
  public abstract ExperimentJob createJob(Experiment data,
      Configuration config, Logger logger);
}
