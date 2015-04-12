package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationJob;

/** The holder for the appendices */
final class _AppendixJobs extends _PartJob {
  /**
   * create the appendices module
   * 
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @param children
   *          the children
   */
  _AppendixJobs(final ExperimentSet data, final Logger logger,
      final IEvaluationJob[] children) {
    super(data, logger, children);
  }

  /** {@inheritDoc} */
  @Override
  final String _getName() {
    return "Appendices"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final int _getMinSectionsToOpenNewSection() {
    return Integer.MAX_VALUE;
  }
}
