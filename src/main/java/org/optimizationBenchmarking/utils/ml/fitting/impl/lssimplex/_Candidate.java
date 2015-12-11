package org.optimizationBenchmarking.utils.ml.fitting.impl.lssimplex;

import org.optimizationBenchmarking.utils.ml.fitting.impl.abstr.FittingCandidateSolution;

/** an internal candidate solution */
final class _Candidate extends FittingCandidateSolution {

  /**
   * the methods this solution was input to or output of the specified
   * method
   */
  int m_processedBy;

  /**
   * Create the fitting candidate solution
   *
   * @param parameterCount
   *          the number of parameters
   */
  _Candidate(final int parameterCount) {
    super(parameterCount);
  }
}
