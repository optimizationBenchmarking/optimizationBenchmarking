package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;

/** The holder for the descriptions */
final class _Descriptions extends _Part {
  /**
   * create the descriptions module
   * 
   * @param logger
   *          the logger
   * @param children
   *          the children
   */
  _Descriptions(final Logger logger, final _PseudoModule[] children) {
    super(logger, children);
  }

  /** {@inheritDoc} */
  @Override
  final void _writeIntro(final ExperimentSet data, final ISectionBody body) {
    //
  }

  /** {@inheritDoc} */
  @Override
  final void _title(final ExperimentSet data, final IPlainText title) {
    title.append("Background"); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final String _getName() {
    return "Descriptions"; //$NON-NLS-1$
  }
}
