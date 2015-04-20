package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationJob;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;

/** The holder for the experiment statistics */
final class _ExperimentJobs extends _PartJob {

  /**
   * create the experiment statistics module
   * 
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @param children
   *          the children
   */
  _ExperimentJobs(final IExperimentSet data, final Logger logger,
      final IEvaluationJob[] children) {
    super(data, logger, children);
  }

  /** {@inheritDoc} */
  @Override
  final String _getName() {
    return "Per-Experiment Performance Evaluation"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final void _writeSectionIntroduction(final IExperimentSet data,
      final ISectionBody body) {
    //
  }

  /** {@inheritDoc} */
  @Override
  final void _writeSectionTitle(final IExperimentSet data,
      final IPlainText title) {
    if (data.getData().size() > 1) {
      title.append(this._getName());
    } else {
      title.append("Performance Evaluation"); //$NON-NLS-1$
    }
  }

}
