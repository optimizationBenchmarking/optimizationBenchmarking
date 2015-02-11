package org.optimizationBenchmarking.experimentation.evaluation.system.impl.single.experimentInfo;

import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.ExperimentJob;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/**
 * The experiment information main job.
 */
public class ExperimentInformationMainJob extends
    ExperimentJob<ConfiguredExperimentInformation> {

  /**
   * Create the main job
   * 
   * @param config
   *          the configuration
   * @param experiment
   *          the experiment
   * @param container
   *          the section container
   */
  protected ExperimentInformationMainJob(
      final ConfiguredExperimentInformation config,
      final Experiment experiment, final ISectionContainer container) {
    super(config, experiment, container);
  }

  /** {@inheritDoc} */
  @Override
  protected void execute() {
    // TODO Auto-generated method stub

  }

}
