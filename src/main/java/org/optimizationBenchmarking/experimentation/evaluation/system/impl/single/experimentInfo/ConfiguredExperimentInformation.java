package org.optimizationBenchmarking.experimentation.evaluation.system.impl.single.experimentInfo;

import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.ConfiguredExperimentModule;

/** A fixed configuration of the experiment information module. */
public class ConfiguredExperimentInformation extends
    ConfiguredExperimentModule {

  /**
   * Create the configured experiment information
   * 
   * @param setup
   *          the setup
   */
  protected ConfiguredExperimentInformation(
      final ExperimentInformationSetup setup) {
    super(setup);
  }
}
