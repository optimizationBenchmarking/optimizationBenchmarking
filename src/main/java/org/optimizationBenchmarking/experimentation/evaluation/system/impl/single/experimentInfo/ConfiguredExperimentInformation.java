package org.optimizationBenchmarking.experimentation.evaluation.system.impl.single.experimentInfo;

import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.ConfiguredExperimentModule;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
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
  public void process(final Experiment data,
      final ISectionContainer sectionContainer) {

    try (final ISection section = sectionContainer.section(null)) {
      try (final IPlainText title = section.title()) {
        title.append("Basic Information"); //$NON-NLS-1$
      }

      try (final ISectionBody body = section.body()) {
        body.append(data.getName());
        // body.append(' ');
        // body.append(this.m_experiment.getDescription());
      }
    }
  }
}
