package org.optimizationBenchmarking.utils.tools.impl.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.impl.process.AbstractProcess;
import org.optimizationBenchmarking.utils.tools.impl.process.TextProcess;

/** A shell. */
public final class Shell extends AbstractProcess {

  /** the underlying process */
  private final TextProcess m_process;

  /**
   * create the shell
   *
   * @param logger
   *          the logger
   * @param process
   *          the underlying process
   */
  Shell(final Logger logger, final TextProcess process) {
    super(logger);
    this.m_process = process;
  }

  /** {@inheritDoc} */
  @Override
  public final int waitFor() throws IOException {
    return this.m_process.waitFor();
  }

  /** {@inheritDoc} */
  @Override
  public final void close() throws IOException {
    this.m_process.close();
  }

  /**
   * Get the standard input stream of the process
   *
   * @return the standard input stream of the process
   */
  public final BufferedWriter getStdIn() {
    return this.m_process.getStdIn();
  }

  /**
   * Get the standard output stream of the process
   *
   * @return the standard output stream of the process
   */
  public final BufferedReader getStdOut() {
    return this.m_process.getStdOut();
  }

  /**
   * Get the standard error stream of the process
   *
   * @return the standard error stream of the process
   */
  public final BufferedReader getStdError() {
    return this.m_process.getStdError();
  }
}
