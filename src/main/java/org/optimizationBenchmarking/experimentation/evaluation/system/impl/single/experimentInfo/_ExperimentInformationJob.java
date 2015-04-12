package org.optimizationBenchmarking.experimentation.evaluation.system.impl.single.experimentInfo;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.ExperimentJob;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/** A job of the experiment information module. */
final class _ExperimentInformationJob extends ExperimentJob {

  /**
   * Create the experiment information job
   * 
   * @param data
   *          the data
   * @param logger
   *          the logger
   */
  _ExperimentInformationJob(final Experiment data, final Logger logger) {
    super(data, logger);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doMain(final Experiment data,
      final ISectionContainer sectionContainer, final Logger logger) {

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
