package org.optimizationBenchmarking.utils.tools.impl.browser;

/** A browser descriptor */
final class _BrowserDesc {
  /** the parameters we need to provide */
  final String[] m_parameters;

  /** is the browser reliale? */
  final boolean m_reliable;

  /** does this browser need a windows batch wrapper? */
  final boolean m_needsWinBatchWrap;

  /**
   * create the browser description
   *
   * @param isReliable
   *          is the browser reliable?
   * @param needsWinBatchWrap
   *          does this browser need a windows batch wrapper?
   * @param parameters
   *          the parameters to be supplied to the browser
   */
  _BrowserDesc(final boolean isReliable, final boolean needsWinBatchWrap,
      final String[] parameters) {
    super();
    this.m_parameters = parameters;
    this.m_reliable = isReliable;
    this.m_needsWinBatchWrap = needsWinBatchWrap;
  }

  /**
   * create the browser description
   *
   * @param isReliable
   *          is the browser reliable?
   */
  _BrowserDesc(final boolean isReliable) {
    this(isReliable, false, null);
  }
}
