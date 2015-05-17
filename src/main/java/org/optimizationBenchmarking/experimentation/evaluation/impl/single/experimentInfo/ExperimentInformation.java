package org.optimizationBenchmarking.experimentation.evaluation.impl.single.experimentInfo;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.evaluation.impl.abstr.ExperimentModule;
import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * The experiment information module. This module prints some basic
 * information about an experiment.
 */
public final class ExperimentInformation extends ExperimentModule {

  /** create the experiment information tool */
  ExperimentInformation() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final _ExperimentInformationJob createJob(final IExperiment data,
      final Configuration config, final Logger logger) {
    return new _ExperimentInformationJob(data, logger);
  }

  /**
   * Get the globally shared instance of the experiment information module
   *
   * @return the globally shared instance of the experiment information
   *         module
   */
  public static final ExperimentInformation getInstance() {
    return __ExperimentInformationLoader.INSTANCE;
  }

  /** the experiment information loader */
  private static final class __ExperimentInformationLoader {
    /** the shared instance */
    static final ExperimentInformation INSTANCE = new ExperimentInformation();
  }
}
