package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;

/** The holder for the experiment set statistics */
final class _ExperimentSetStatistics extends _Part {
  /**
   * create the experiment set statistics module
   * 
   * @param children
   *          the children
   */
  _ExperimentSetStatistics(final _PseudoModule[] children) {
    super(children);
  }

  /** {@inheritDoc} */
  @Override
  final void _writeIntro(final ExperimentSet data, final ISectionBody body) {
    //
  }

  /** {@inheritDoc} */
  @Override
  final void _title(final ExperimentSet data, final IPlainText title) {
    title.append("Performance Comparisons"); //$NON-NLS-1$
  }
}
