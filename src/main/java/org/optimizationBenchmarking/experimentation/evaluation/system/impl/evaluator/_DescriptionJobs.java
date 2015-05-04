package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;

/** The holder for the descriptions */
final class _DescriptionJobs extends _PartJob {
  /**
   * create the descriptions module
   *
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @param children
   *          the children
   */
  _DescriptionJobs(final IExperimentSet data, final Logger logger,
      final _PseudoJob[] children) {
    super(data, logger, children);
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
    title.append("Background"); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final String _getName() {
    return "Descriptions"; //$NON-NLS-1$
  }
}
