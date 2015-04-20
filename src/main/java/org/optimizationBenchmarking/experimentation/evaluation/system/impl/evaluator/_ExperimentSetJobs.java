package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationJob;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;

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
  _ExperimentSetJobs(final ExperimentSet data, final Logger logger,
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
  final void _writeSectionIntroduction(final ExperimentSet data,
      final ISectionBody body) {
    //
  }

  /** {@inheritDoc} */
  @Override
  final void _writeSectionTitle(final ExperimentSet data,
      final IPlainText title) {
    title.append("Performance Comparisons"); //$NON-NLS-1$
  }
}
