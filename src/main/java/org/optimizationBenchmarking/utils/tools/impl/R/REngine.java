package org.optimizationBenchmarking.utils.tools.impl.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.encoding.TextEncoding;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;

/** The {@code R} Engine */
public final class REngine extends ToolJob implements Closeable {

  /** the instance of {@code R} */
  private final ExternalProcess m_process;

  /** the input read from the process */
  private final BufferedReader m_in;

  /** the output to be written to the process */
  private final BufferedWriter m_out;

  /** the logger to use */
  private final Logger m_log;

  /**
   * create
   * 
   * @param process
   *          the process
   * @param logger
   *          the logger
   * @throws IOException
   *           if it must
   */
  REngine(final ExternalProcess process, final Logger logger)
      throws IOException {
    super();

    final TextEncoding e;

    this.m_process = process;
    this.m_log = logger;

    e = R._encoding();
    this.m_in = e.wrapInputStream(process.getStdOut());
    this.m_out = e.wrapOutputStream(process.getStdIn());

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info("R engine successfully started"); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void close() throws IOException {
    Throwable error;

    error = null;
    try {
      this.m_out.write("q()"); //$NON-NLS-1$
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    }
    try {
      this.m_out.newLine();
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    }

    try {
      this.m_out.flush();
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    }

    try {
      this.m_process.waitFor();
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    }

    try {
      this.m_out.close();
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    }

    try {
      this.m_in.close();
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    }

    try {
      this.m_process.close();
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    }

    if (error != null) {
      if ((this.m_log != null) && (this.m_log.isLoggable(Level.SEVERE))) {
        this.m_log.log(Level.SEVERE, "Error while shutting down R.", //$NON-NLS-1$
            error);
      }
      ErrorUtils.throwAsIOException(error);
    } else {
      if ((this.m_log != null) && (this.m_log.isLoggable(Level.INFO))) {
        this.m_log.info("R engine shut down gracefully."); //$NON-NLS-1$
      }
    }

  }
}
