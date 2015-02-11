package org.optimizationBenchmarking.experimentation.evaluation.system.impl.single.experimentInfo;

import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.ConfiguredExperimentModule;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

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

  /** {@inheritDoc} */
  @Override
  public ExperimentInformationMainJob createMainJob(final Experiment data,
      final ISectionContainer sectionContainer) {
    return new ExperimentInformationMainJob(this, data, sectionContainer);
  }
}
