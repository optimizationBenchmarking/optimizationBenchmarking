package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/**
 * A base class for processes.
 */
public abstract class BasicProcess extends ToolJob implements Closeable {

  /** the closer */
  IProcessCloser<BasicProcess> m_closer;

  /**
   * create
   *
   * @param log
   *          the logger to use
   * @param closer
   *          the process closer
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  BasicProcess(final Logger log, final IProcessCloser closer) {
    super(log);
    this.m_closer = closer;
  }

  /**
   * Wait until the process has finished and obtain its return value.
   *
   * @return the return value
   * @throws IOException
   *           if i/o fails
   */
  public abstract int waitFor() throws IOException;

  /**
   * Terminate the process if it is still alive
   *
   * @throws IOException
   *           if i/o fails
   */
  @Override
  public abstract void close() throws IOException;

  /**
   * Invoke the process closer
   *
   * @throws IOException
   *           if i/o fails
   */
  final void _beforeClose() throws IOException {
    final IProcessCloser<BasicProcess> closer;
    closer = this.m_closer;
    if (closer != null) {
      this.m_closer = null;
      closer.beforeClose(this);
    }
  }
}
