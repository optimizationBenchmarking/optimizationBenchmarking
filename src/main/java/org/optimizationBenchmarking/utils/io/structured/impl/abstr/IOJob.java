package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/** The base class for I/O jobs */
public class IOJob extends ToolJob {

  /** the even finer log level */
  public static final Level FINER_LOG_LEVEL = Level.FINEST;

  /** the finer log level */
  public static final Level FINE_LOG_LEVEL = Level.FINER;

  /** the default log level */
  public static final Level DEFAULT_LOG_LEVEL = Level.FINE;

  /** the long id */
  long m_lid;

  /** the id of this job */
  String m_id;

  /** the owning tool */
  @SuppressWarnings("rawtypes")
  final IOTool m_tool;

  /** the job token: a multi-purpose variable */
  Object m_token;

  /** the current location */
  Object m_current;

  /**
   * create the _IOJob
   * 
   * @param logger
   *          the logger
   * @param tool
   *          the owning tool
   */
  IOJob(final Logger logger, final IOTool<?> tool) {
    super(logger);
    _IOJobBuilder._validateTool(tool);
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
    final Logger logger;
    logger = this.getLogger();
    return ((logger != null) && (logger.isLoggable(level)));
  }

  /**
   * Can we log at the default log level?
   * 
   * @return {@code true} if we can log at the default log level,
   *         {@code false} otherwise
   */
  public final boolean canLog() {
    return this.canLog(IOJob.DEFAULT_LOG_LEVEL);
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
    final Logger logger;

    logger = this.getLogger();
    if (logger != null) {
      logger.log(level, (this._id() + message));
    }
  }

  /**
   * Log a given message at a given level
   * 
   * @param level
   *          the level
   * @param message
   *          the message
   * @param information
   *          an information object
   */
  public final void log(final Level level, final String message,
      final Object information) {
    final Logger logger;
    logger = this.getLogger();
    if (logger != null) {
      logger.log(level, (this._id() + message), information);
    }
  }

  /**
   * Log a given message at the default log level
   * 
   * @param message
   *          the message
   */
  public final void log(final String message) {
    this.log(IOJob.DEFAULT_LOG_LEVEL, message);
  }

  /**
   * Handle an error
   * 
   * @param throwable
   *          the error
   * @param message
   *          the message
   * @throws IOException
   *           if something fails
   */
  public final void handleError(final Throwable throwable,
      final String message) throws IOException {
    final Object o;
    String msg;

    msg = this._id();

    if ((message == null) || (message.length() <= 0)) {
      msg += "failed without providing a detailed error message."; //$NON-NLS-1$
    } else {
      msg += message;
    }

    if ((o = this.m_current) != null) {
      msg += ((((" Possible source of error: '" + //$NON-NLS-1$ 
      o) + "' of class ") + //$NON-NLS-1$
      TextUtils.className(o.getClass())) + //
      " (but this information may not be reliable).");//$NON-NLS-1$
    }

    ErrorUtils.logError(this.getLogger(), msg, throwable, true);
    ErrorUtils.throwAsIOException(throwable);
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

  /**
   * Get the token returned by
   * {@link org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOTool#before(IOJob, Object)}
   * 
   * @return the token
   */
  public final Object getToken() {
    return this.m_token;
  }
}
