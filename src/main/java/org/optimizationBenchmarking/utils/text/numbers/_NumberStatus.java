package org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.text.ETextCase;

/** a number status */
final class _NumberStatus {

  /** do we need a space */
  boolean m_needsSpace;

  /** the text case */
  ETextCase m_case;

  /**
   * Create the number status record
   *
   * @param textCase
   *          the number status record
   */
  _NumberStatus(final ETextCase textCase) {
    super();
    this.m_case = textCase;
  }

}
