package org.optimizationBenchmarking.experimentation.evaluation.impl.all.ecdf;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.functions.ecdf.ECDF;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.impl.all.function.FunctionJob;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;

/** A job of the ECDF module. */
final class _AllECDFJob extends FunctionJob {

  /**
   * Create the ECDF job
   *
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @param config
   *          the configuration
   */
  _AllECDFJob(final IExperimentSet data, final Configuration config,
      final Logger logger) {
    super(data, ECDF.create(data, config), config, logger);
  }

  /** {@inheritDoc} */
  @Override
  protected final ScalarAggregate getYAxisMinimumAggregate() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  protected final double getYAxisMinimumValue() {
    return 0d;
  }

  /** {@inheritDoc} */
  @Override
  protected final ScalarAggregate getYAxisMaximumAggregate() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  protected final double getYAxisMaximumValue() {
    return 1d;
  }
}
