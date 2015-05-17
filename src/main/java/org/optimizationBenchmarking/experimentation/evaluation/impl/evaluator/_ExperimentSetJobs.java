package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationJob;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;

/** The holder for the experiment set statistics */
final class _ExperimentSetJobs extends _PartJob {
  /**
   * create the experiment set statistics module
   *
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @param children
   *          the children
   */
  _ExperimentSetJobs(final IExperimentSet data, final Logger logger,
      final IEvaluationJob[] children) {
    super(data, logger, children);
  }

  /** {@inheritDoc} */
  @Override
  final String _getName() {
    return "Experiment Set Statistics"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final void _writeSectionIntroduction(final IExperimentSet data,
      final ISectionBody body) {
    //
  }

  /**
   * With this method, we make sure that one (hopefully unique) color is
   * assigned to each experiment.
   *
   * @param data
   *          {@inheritDoc}
   * @param section
   *          {@inheritDoc}
   */
  @Override
  final void _beforeSection(final IExperimentSet data,
      final ISection section) {
    final StyleSet styles;

    super._beforeSection(data, section);

    styles = section.getStyles();
    for (final IExperiment experiment : data.getData()) {
      styles.getColor(experiment.getName(), true);
    }
  }

  /** {@inheritDoc} */
  @Override
  final void _writeSectionTitle(final IExperimentSet data,
      final IPlainText title) {
    title.append("Performance Comparisons"); //$NON-NLS-1$
  }
}
