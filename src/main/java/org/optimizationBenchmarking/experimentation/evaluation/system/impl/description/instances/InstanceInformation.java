package org.optimizationBenchmarking.experimentation.evaluation.system.impl.description.instances;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.DescriptionModule;
import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * The instance information module. This module prints some basic
 * information about the benchmark instances.
 */
public final class InstanceInformation extends DescriptionModule {

  /** create the instance information tool */
  InstanceInformation() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final _InstanceInformationJob createJob(
      final IExperimentSet data, final Configuration config,
      final Logger logger) {
    return new _InstanceInformationJob(data, config, logger);
  }

  /**
   * Get the globally shared instance of the instance information module
   * 
   * @return the globally shared instance of the instance information
   *         module
   */
  public static final InstanceInformation getInstance() {
    return __InstanceInformationLoader.INSTANCE;
  }

  /** the instance information loader */
  private static final class __InstanceInformationLoader {
    /** the shared instance */
    static final InstanceInformation INSTANCE = new InstanceInformation();
  }
}
