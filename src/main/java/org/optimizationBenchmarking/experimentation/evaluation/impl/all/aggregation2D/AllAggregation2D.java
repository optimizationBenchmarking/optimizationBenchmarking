package org.optimizationBenchmarking.experimentation.evaluation.impl.all.aggregation2D;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.impl.abstr.ExperimentSetModule;
import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * The Aggregation2D painter. This module paints the Aggregation2D for
 * different experiments into one diagram.
 */
public final class AllAggregation2D extends ExperimentSetModule {

  /** the aggregation parameter */
  public static final String PARAM_AGGREGATION = "aggregation"; //$NON-NLS-1$

  /** create the instance information tool */
  AllAggregation2D() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final _AllAggregation2DJob createJob(final IExperimentSet data,
      final Configuration config, final Logger logger) {
    return new _AllAggregation2DJob(data, config, logger);
  }

  /**
   * Get the globally shared instance of the instance information module
   *
   * @return the globally shared instance of the instance information
   *         module
   */
  public static final AllAggregation2D getInstance() {
    return __Aggregation2DLoader.INSTANCE;
  }

  /** the Aggregation2D loader */
  private static final class __Aggregation2DLoader {
    /** the shared instance */
    static final AllAggregation2D INSTANCE = new AllAggregation2D();
  }
}
