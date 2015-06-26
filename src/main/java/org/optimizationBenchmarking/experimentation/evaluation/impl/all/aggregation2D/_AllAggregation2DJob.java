package org.optimizationBenchmarking.experimentation.evaluation.impl.all.aggregation2D;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.functions.aggregation2D.Aggregation2D;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.impl.all.function.FunctionJob;
import org.optimizationBenchmarking.utils.config.Configuration;

/** A job of the Aggregation2D module. */
final class _AllAggregation2DJob extends FunctionJob {

  /**
   * Create the Aggregation2D job
   *
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @param config
   *          the configuration
   */
  _AllAggregation2DJob(final IExperimentSet data,
      final Configuration config, final Logger logger) {
    super(data, Aggregation2D.create(data, config), config, logger);
  }
}
