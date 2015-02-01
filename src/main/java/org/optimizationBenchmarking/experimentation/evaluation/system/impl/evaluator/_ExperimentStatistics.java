package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import org.optimizationBenchmarking.experimentation.evaluation.data.Experiment;
import org.optimizationBenchmarking.experimentation.evaluation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/** The holder for the experiment statistics */
final class _ExperimentStatistics extends _Part {

  /**
   * create the experiment statistics module
   * 
   * @param children
   *          the children
   */
  _ExperimentStatistics(final _PseudoModule[] children) {
    super(children);
  }

  /** {@inheritDoc} */
  @Override
  final void _writeIntro(final ExperimentSet data, final ISectionBody body) {
    final ArraySetView<Experiment> experiments;

    experiments = data.getData();
    if (experiments.size() == 1) {
      this.__writeIntro(experiments.get(0), body);
      return;
    }
  }

  /**
   * Write the intro for a given experiment.
   * 
   * @param data
   *          the data
   * @param body
   *          the body
   */
  private final void __writeIntro(final Experiment data,
      final ISectionBody body) {
    //
  }

  /** {@inheritDoc} */
  @Override
  final void _title(final ExperimentSet data, final IPlainText title) {
    title.append("Performance Evaluation"); //$NON-NLS-1$
  }

  /**
   * create a section for an experiment
   * 
   * @param data
   *          the data
   * @param dest
   *          the destination
   */
  private final void __experimentSection(final Experiment data,
      final ISectionContainer dest) {
    try (final ISection section = dest.section(null)) {
      try (final IPlainText title = section.title()) {
        title.append("Evaluation of Experiment "); //$NON-NLS-1$
        title.append(data.getName());
      }
      try (final ISectionBody body = section.body()) {
        this.__writeIntro(data, body);
        super._doJobs(data, body);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  void _body(final ExperimentSet data, final ISectionBody body) {
    final int experimentCount;
    final ArraySetView<Experiment> experiments;

    if ((this.m_children != null) && (this.m_children.length > 0)) {
      experiments = data.getData();
      experimentCount = experiments.size();
      for (final Experiment experiment : experiments) {
        if (experimentCount > 1) {
          this.__experimentSection(experiment, body);
        } else {
          super._doJobs(experiment, body);
        }
      }
    }
  }

}
