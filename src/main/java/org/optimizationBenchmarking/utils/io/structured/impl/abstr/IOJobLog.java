package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** The base class for I/O jobs */
public class IOJobLog {

  /** the logger */
  final Logger m_logger;

  /** the long id */
  long m_lid;

  /** the id of this job */
  String m_id;

  /** the owning tool */
  final IOTool<?> m_tool;

  /**
   * create the _IOJob
   * 
   * @param logger
   *          the logger
   * @param tool
   *          the owning tool
   */
  IOJobLog(final Logger logger, final IOTool<?> tool) {
    super();
    _IOJobBuilder._validateTool(tool);
    this.m_logger = logger;
    this.m_tool = tool;
  }

  /**
   * append this job's id
   * 
   * @param textOut
   *          the text buffer
   */
  void _appendID(final MemoryTextOutput textOut) {
    textOut.append(this.m_tool.getClass().getSimpleName());
    textOut.append('@');
    if (this.m_lid == 0L) {
      this.m_lid = this.m_tool.m_jobCounter.incrementAndGet();
    }
    textOut.append(this.m_lid);
    textOut.append('(');
  }

  /**
   * Can we log something at the given level
   * 
   * @param level
   *          the level
   * @return {@code true} if we can, {@code false} if we cannot
   */
  public final boolean canLog(final Level level) {
    return ((this.m_logger != null) && (this.m_logger.isLoggable(level)));
  }

  /**
   * Can we log at the default log level?
   * 
   * @return {@code true} if we can log at the default log level,
   *         {@code false} otherwise
   */
  public final boolean canLog() {
    return this.canLog(IOTool.DEFAULT_LOG_LEVEL);
  }

  /**
   * Log a given message at a given level
   * 
   * @param level
   *          the level
   * @param message
   *          the message
   */
  public final void log(final Level level, final String message) {
    this.m_logger.log(level, (this._id() + message));
  }

  /**
   * Log a given message at the default log level
   * 
   * @param message
   *          the message
   */
  public final void log(final String message) {
    this.log(IOTool.DEFAULT_LOG_LEVEL, message);
  }

  /**
   * Handle an error
   * 
   * @param throwable
   *          the error
   * @param message
   *          the message
   * @throws IOException
   *           the exception
   */
  public final void handleError(final Throwable throwable,
      final String message) throws IOException {
    final IOException ioe;
    String msg;

    if (throwable instanceof _HandledIOException) {
      throw ((IOException) throwable);
    }

    msg = this._id();
    if (message == null) {
      msg += "failed without providing a detailed error message."; //$NON-NLS-1$
    } else {
      msg += message;
    }

    if (throwable == null) {
      ioe = new _HandledIOException(msg);
    } else {
      ioe = new _HandledIOException(msg, throwable);
    }

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.SEVERE))) {
      try {
        throw ioe;
      } catch (final IOException exc) {
        this.m_logger.log(Level.SEVERE, msg, exc);
      }
    }
    throw ioe;
  }

  /**
   * Get the id
   * 
   * @return the id
   */
  final String _id() {
    final MemoryTextOutput mto;
    if (this.m_id == null) {
      mto = new MemoryTextOutput();
      this._appendID(mto);
      mto.append(')');
      mto.append(':');
      mto.append(' ');
      this.m_id = mto.toString();
    }
    return this.m_id;
  }
}
