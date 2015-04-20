package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IAppendixJobBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IAppendixModule;
import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * The abstract basic implementation for appendix modules.
 */
public abstract class AppendixModule extends
    _EvaluationModule<ExperimentSet> implements IAppendixModule {

  /** create */
  protected AppendixModule() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final IAppendixJobBuilder use() {
    this.checkCanUse();
    return new _AppendixJobBuilder(this);
  }

  /**
   * Create the appendix job.
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
  public abstract AppendixJob createJob(ExperimentSet data,
      Configuration config, Logger logger);
}
