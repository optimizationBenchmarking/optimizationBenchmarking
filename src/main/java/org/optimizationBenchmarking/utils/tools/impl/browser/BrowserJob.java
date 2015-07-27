package org.optimizationBenchmarking.utils.tools.impl.browser;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;

/** The browser job */
public final class BrowserJob extends ToolJob implements Closeable {

  /** the internal process */
  private ExternalProcess m_process;

  /** the temporary directory, or {@code null} if none is needed */
  private TempDir m_temp;

  /**
   * Create the browser job,
   *
   * @param process
   *          the browser process
   * @param logger
   *          the logger
   * @param temp
   *          the temporary directory, or {@code null} if none is needed
   */
  BrowserJob(final Logger logger, final ExternalProcess process,
      final TempDir temp) {
    super(logger);
    if (process == null) {
      throw new IllegalArgumentException("Browser process cannot be null."); //$NON-NLS-1$
    }
    this.m_process = process;
    this.m_temp = temp;
  }

  /**
   * Wait until the browser has been closed. This method is very
   * unreliable. Modern browsers often work with multiple processes. If a
   * browser is already open and we open a new one, then the new instance
   * may just forward the URL to the already running one and terminate. In
   * other words, although the user is still on our web site, it looks as
   * if he has closed the browser.
   *
   * @return the return value
   */
  public final int waitFor() {
    final ExternalProcess proc;

    proc = this.m_process;
    if (proc != null) {
      return proc.waitFor();
    }
    throw new IllegalStateException("Process already closed."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final void close() throws IOException {
    ExternalProcess proc;
    TempDir temp;

    synchronized (this) {
      proc = this.m_process;
      temp = this.m_temp;
      this.m_process = null;
      this.m_temp = null;
    }

    try {
      if (proc != null) {
        proc.close();
      }
    } finally {
      if (temp != null) {
        temp.close();
      }
    }
  }
}
