package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.logging.Logger;

/** The holder for the appendices */
final class _Appendices extends _PseudoModule {
  /**
   * create the appendices module
   * 
   * @param logger
   *          the logger
   * @param children
   *          the children
   */
  _Appendices(final Logger logger, final _PseudoModule[] children) {
    super(logger, children);
  }

  /** {@inheritDoc} */
  @Override
  final String _getName() {
    return "Appendix"; //$NON-NLS-1$
  }
}
