package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IDescriptionJobBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IDescriptionModule;
import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * The abstract basic implementation for description modules.
 */
public abstract class DescriptionModule extends
    _EvaluationModule<ExperimentSet> implements IDescriptionModule {

  /** create */
  protected DescriptionModule() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final IDescriptionJobBuilder use() {
    this.checkCanUse();
    return new _DescriptionJobBuilder(this);
  }

  /**
   * Create the description job.
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
  public abstract DescriptionJob createJob(ExperimentSet data,
      Configuration config, Logger logger);
}
