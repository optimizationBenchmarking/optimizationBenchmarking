package org.optimizationBenchmarking.experimentation.evaluation.system.impl.single.experimentInfo;

import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.ExperimentJob;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
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
    try (final ISection section = this.m_container.section(null)) {
      try (final IPlainText title = section.title()) {
        title.append("Basic Information"); //$NON-NLS-1$
      }

      try (final ISectionBody body = section.body()) {
        body.append(this.m_experiment.getName());
        // body.append(' ');
        // body.append(this.m_experiment.getDescription());
      }
    }

  }

}
