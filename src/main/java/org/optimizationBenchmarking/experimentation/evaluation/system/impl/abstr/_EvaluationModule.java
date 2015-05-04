package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * An internal base-class for evaluation modules.
 *
 * @param <DT>
 *          the data set type
 */
abstract class _EvaluationModule<DT extends IElementSet> extends
    EvaluationModule {

  /** create the module */
  _EvaluationModule() {
    super();
  }

  /**
   * Create the evaluation job
   *
   * @param data
   *          the data
   * @param config
   *          the configuration
   * @param logger
   *          the logger
   * @return the job
   */
  abstract _EvaluationJob<DT> createJob(final DT data,
      final Configuration config, final Logger logger);

}
