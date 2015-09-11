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
   * {@code true} if and only if {@link #waitFor()} is reliable,
   * {@code false} otherwise.
   */
  private final boolean m_isWaitForReliable;

  /**
   * Create the browser job,
   *
   * @param process
   *          the browser process or {@code null} if desktop browsing is
   *          used
   * @param logger
   *          the logger
   * @param temp
   *          the temporary directory, or {@code null} if none is needed
   * @param isWaitForReliable
   *          {@code true} if and only if {@link #waitFor()} is reliable,
   *          {@code false} otherwise
   */
  BrowserJob(final Logger logger, final ExternalProcess process,
      final TempDir temp, final boolean isWaitForReliable) {
    super(logger);
    if (process == null) {
      if (Browser._BrowserPath.DESKTOP == null) {
        throw new IllegalArgumentException(
            "Browser process cannot be null."); //$NON-NLS-1$
      }
    }
    this.m_process = process;
    this.m_temp = temp;
    this.m_isWaitForReliable = isWaitForReliable;
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
   * @throws IOException
   *           if i/o fails
   */
  public final int waitFor() throws IOException {
    final ExternalProcess proc;

    proc = this.m_process;
    if (proc != null) {
      return proc.waitFor();
    }
    if (Browser._BrowserPath.DESKTOP == null) {
      throw new IllegalStateException("Process already closed."); //$NON-NLS-1$
    }
    return 0;
  }

  /**
   * As stated in its documentation, the {@link #waitFor()} method cannot
   * be made reliable in the general case: It may return although the
   * browser is still open. This is due to the multi-process structure of
   * modern browsers. However, in some configurations (such as Internet
   * Explorer under Windows) it can be reliable. This function here tells
   * you whether {@link #waitFor()} will only return when the browser has
   * actually been closed (or crashed), or whether it may return earlier.
   *
   * @return {@code true} if and only if {@link #waitFor()} is reliable,
   *         {@code false} otherwise
   */
  public final boolean isWaitForReliable() {
    return this.m_isWaitForReliable;
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
