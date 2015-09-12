package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.IOException;

/** a bridge for invoking the process closer */
final class _TextProcessBridge implements IProcessCloser<_BasicProcess> {

  /** the target text process */
  TextProcess m_target;

  /** create */
  _TextProcessBridge() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final void beforeClose(final _BasicProcess process)
      throws IOException {
    final TextProcess target;

    synchronized (this) {
      target = this.m_target;
      this.m_target = null;
    }

    if (target != null) {
      target._beforeClose();
    }
  }
}
