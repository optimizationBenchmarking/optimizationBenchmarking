package org.optimizationBenchmarking.utils.tools.impl.browser;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;

/** The browser job */
public final class BrowserJob extends ToolJob implements Closeable {

  /** the internal process */
  private final ExternalProcess m_process;

  /**
   * Create the browser job,
   *
   * @param process
   *          the browser process
   * @param logger
   *          the logger
   */
  BrowserJob(final Logger logger, final ExternalProcess process) {
    super(logger);
    if (process == null) {
      throw new IllegalArgumentException("Browser process cannot be null."); //$NON-NLS-1$
    }
    this.m_process = process;
  }

  /**
   * Wait until the browser has been closed
   *
   * @return the return value
   */
  public final int waitFor() {
    return this.m_process.waitFor();
  }

  /** {@inheritDoc} */
  @Override
  public void close() throws IOException {
    this.m_process.close();
  }
}
