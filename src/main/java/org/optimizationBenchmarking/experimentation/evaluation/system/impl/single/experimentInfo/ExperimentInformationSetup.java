package org.optimizationBenchmarking.experimentation.evaluation.system.impl.single.experimentInfo;

import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.ExperimentModuleSetup;

/**
 * The setup builder for the experiment information.
 */
public class ExperimentInformationSetup
    extends
    ExperimentModuleSetup<ConfiguredExperimentInformation, ExperimentInformationSetup> {

  /** create */
  protected ExperimentInformationSetup() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public ConfiguredExperimentInformation create() {
    return new ConfiguredExperimentInformation(this);
  }

}
