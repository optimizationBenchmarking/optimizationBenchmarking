package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/**
 * A base class for processes.
 */
public abstract class AbstractProcess extends ToolJob implements Closeable {

  /**
   * create
   *
   * @param log
   *          the logger to use
   */
  protected AbstractProcess(final Logger log) {
    super(log);
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
}
