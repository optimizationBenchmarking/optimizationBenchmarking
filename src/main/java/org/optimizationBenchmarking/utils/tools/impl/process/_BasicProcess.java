package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * A base class for processes.
 */
abstract class _BasicProcess extends AbstractProcess {

  /** the closer */
  IProcessCloser<_BasicProcess> m_closer;

  /**
   * create
   *
   * @param log
   *          the logger to use
   * @param closer
   *          the process closer
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  _BasicProcess(final Logger log, final IProcessCloser closer) {
    super(log);
    this.m_closer = closer;
  }

  /**
   * Invoke the process closer
   *
   * @throws IOException
   *           if i/o fails
   */
  final void _beforeClose() throws IOException {
    final IProcessCloser<_BasicProcess> closer;
    closer = this.m_closer;
    if (closer != null) {
      this.m_closer = null;
      closer.beforeClose(this);
    }
  }
}
