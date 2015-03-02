package org.optimizationBenchmarking.utils.error;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * This is not really an exception. It is a placeholder which can be
 * {@link java.lang.Throwable#addSuppressed(Throwable) added} to the
 * suppressed exception list of an error. It then reminds us that the error
 * has already been logged to a given logger and does not necessarily need
 * to be logged there again.
 */
final class _LoggedSentinel extends Throwable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the empty stack trace */
  private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];

  /** the logger */
  private final Logger m_logger;

  /** the log level */
  private final Level m_level;

  /**
   * create the logged sentinel
   * 
   * @param logger
   *          the logger
   * @param level
   *          the log level
   * @param message
   */
  private _LoggedSentinel(final Logger logger, final Level level,
      final String message) {
    super(((((("This error has been logged to logger '" + //$NON-NLS-1$
        _LoggedSentinel.__loggerName(logger)) + "' with message '") + //$NON-NLS-1$
        message) + "' at level ") + level),//$NON-NLS-1$
        null, false, false);
    this.m_logger = logger;
    this.m_level = level;
  }

  /**
   * Is the given {@link java.lang.Throwable} has a sentinel for the
   * provided logger?
   * 
   * @param logger
   *          the logger
   * @param error
   *          the {@link java.lang.Throwable}
   * @param level
   *          the log level
   * @return {@code true} if {@code t} is an indicator that something was
   *         logged to logger
   */
  static final boolean _hasBeenLogged(final Throwable error,
      final Logger logger, final Level level) {
    final Throwable[] suppressed;
    _LoggedSentinel sentinel;

    if (error == null) {
      return false;
    }
    suppressed = error.getSuppressed();
    if (suppressed == null) {
      return false;
    }

    for (final Throwable t : suppressed) {
      if ((t != null) && (t instanceof _LoggedSentinel)) {
        sentinel = ((_LoggedSentinel) t);
        if (sentinel.m_logger == logger) {
          if (sentinel.m_level.intValue() >= level.intValue()) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Mark the given {@link java.lang.Throwable} as logged
   * 
   * @param error
   *          the {@link java.lang.Throwable} to be marked
   * @param message
   *          the message under which the error was logged
   * @param logger
   *          the logger
   * @param level
   *          the log level
   */
  static final void _markAsLogged(final Throwable error,
      final String message, final Logger logger, final Level level) {
    if (error != null) {
      error.addSuppressed(new _LoggedSentinel(logger, level, message));
    }
  }

  /**
   * get the name of a logger
   * 
   * @param logger
   *          the logger
   * @return the string
   */
  private static final String __loggerName(final Logger logger) {
    final String name;

    name = logger.getName();
    if (name != null) {
      return name;
    }
    return ((((TextUtils.className(logger.getClass())) + '#') + System
        .identityHashCode(logger)));
  }

  /** {@inheritDoc} */
  @Override
  public final void printStackTrace(final PrintStream s) {
    s.println(this.getMessage());
  }

  /** {@inheritDoc} */
  @Override
  public final void printStackTrace(final PrintWriter s) {
    s.println(this.getMessage());
  }

  /** {@inheritDoc} */
  @Override
  public final void printStackTrace() {
    this.printStackTrace(System.err);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("sync-override")
  @Override
  public final Throwable fillInStackTrace() {
    return this; // do nothing
  }

  /** {@inheritDoc} */
  @SuppressWarnings("sync-override")
  @Override
  public final Throwable getCause() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final StackTraceElement[] getStackTrace() {
    return _LoggedSentinel.EMPTY_STACK_TRACE;
  }

  /** {@inheritDoc} */
  @Override
  public final void setStackTrace(final StackTraceElement[] stackTrace) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.getMessage();
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("sync-override")
  public final Throwable initCause(final Throwable cause) {
    throw new UnsupportedOperationException(//
        "Cannot set cause of a log sentinel."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o instanceof _LoggedSentinel) && (this.m_logger == (((_LoggedSentinel) o).m_logger)));
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.m_logger.hashCode();
  }
}
