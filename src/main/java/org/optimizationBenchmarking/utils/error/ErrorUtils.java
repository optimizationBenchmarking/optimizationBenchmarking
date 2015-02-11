package org.optimizationBenchmarking.utils.error;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Some utilities to deal with errors */
public final class ErrorUtils {

  /**
   * Throw an {@link java.lang.RuntimeException} error if an
   * {@code unrecoverable} error took place. In this case, if there also
   * was a {@code recoverable}
   * 
   * @param unrecoverable
   *          the error
   * @param recoverable
   *          the recoverable error
   * @throws RuntimeException
   *           if there was an error
   */
  public static final void throwAsRuntimeException(
      final Throwable unrecoverable, final Throwable recoverable)
      throws RuntimeException {
    Throwable a, b;

    a = unrecoverable;
    b = recoverable;
    if (a == null) {
      a = b;
      b = null;
    }
    if (a == null) {
      throw new IllegalArgumentException(//
          "A null error has been encountered?"); //$NON-NLS-1$      
    }
    if (b != null) {
      a.addSuppressed(b);
    }

    if (a instanceof RuntimeException) {
      throw ((RuntimeException) a);
    }
    throw new RuntimeException(//
        "An unrecoverable error was detected and is re-thrown as RuntimeException.", //$NON-NLS-1$
        a);
  }

  /**
   * Throw an {@link java.lang.RuntimeException} error if an
   * {@code unrecoverable} error took place.
   * 
   * @param unrecoverable
   *          the error
   * @throws RuntimeException
   *           if there was an error
   */
  public static final void throwAsRuntimeException(
      final Throwable unrecoverable) throws RuntimeException {
    ErrorUtils.throwAsRuntimeException(unrecoverable, null);
  }

  /**
   * Throw an {@link java.io.IOException} error if an {@code unrecoverable}
   * error took place. If an {@link java.lang.RuntimeException} took place,
   * do not throw it as {@link java.io.IOException}, but again directly as
   * {@link java.lang.RuntimeException}. In this case, if there also was a
   * {@code recoverable} error, throw that as suppressed error.
   * 
   * @param unrecoverable
   *          the error
   * @param recoverable
   *          the recoverable error
   * @throws RuntimeException
   *           if there was an unrecoverable
   *           {@link java.lang.RuntimeException} or the input exception
   *           was already a {@link java.lang.RuntimeException}
   * @throws IOException
   *           if there was an {@link java.io.IOException}
   */
  public static final void throwAsIOException(
      final Throwable unrecoverable, final Throwable recoverable)
      throws RuntimeException, IOException {

    Throwable a, b;

    a = unrecoverable;
    b = recoverable;
    if (a == null) {
      a = b;
      b = null;
    }
    if (a == null) {
      throw new IllegalArgumentException(//
          "A null error has been encountered?"); //$NON-NLS-1$      
    }
    if (b != null) {
      a.addSuppressed(b);
    }

    if (a instanceof RuntimeException) {
      throw ((RuntimeException) a);
    }
    if (a instanceof IOException) {
      throw ((IOException) a);
    }
    throw new IOException(//
        "An unrecoverable error was detected and is re-thrown as IOException.", //$NON-NLS-1$
        a);
  }

  /**
   * Throw an {@link java.io.IOException} error if an {@code unrecoverable}
   * error took place. In this case, if there also was a
   * {@code recoverable}
   * 
   * @param unrecoverable
   *          the error
   * @throws RuntimeException
   *           if there was an unrecoverable RuntimeException
   * @throws IOException
   *           if there was an io exception
   */
  public static final void throwAsIOException(final Throwable unrecoverable)
      throws RuntimeException, IOException {
    ErrorUtils.throwAsIOException(unrecoverable, null);
  }

  /**
   * ScalarAggregate two {@link java.lang.Throwable throwables}
   * 
   * @param oldError
   *          the error which was caught first, or {@code null}
   * @param newError
   *          the error which was caught second, or {@code null}
   * @return an error that represents both errors and can be thrown
   */
  public static final Throwable aggregateError(final Throwable oldError,
      final Throwable newError) {
    if (oldError == null) {
      return newError;
    }
    if (newError == null) {
      return oldError;
    }
    oldError.addSuppressed(newError);
    return oldError;
  }

  /**
   * A placeholder for forbidden methods, such as forbidden private
   * constructors.
   */
  public static final void doNotCall() {
    throw new UnsupportedOperationException(//
        "You are not allowed to call this method."); //$NON-NLS-1$
  }

  /**
   * Write an error to a logger, if the logger is not {@code null} and can
   * log the log level {@link java.util.logging.Level#SEVERE}. If the error
   * has been logged, it will never be logged to the same logger again
   * unless {@code forceLog} is {@code true}.
   * 
   * @param logger
   *          the logger to log to
   * @param message
   *          the message
   * @param error
   *          the error
   * @param forceLog
   *          force logging: always log if the logger is not {@code null}
   *          and can log the log level
   *          {@link java.util.logging.Level#SEVERE} is supported
   */
  public static final void logError(final Logger logger,
      final String message, final Throwable error, final boolean forceLog) {
    final boolean isLogged;
    final String msg;

    // If a logger is provided, then we try to log the error and the
    // message.
    if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
      isLogged = _LoggedSentinel._hasBeenLogged(error, logger);
      if (forceLog || (!isLogged)) {

        if (message != null) {
          msg = message;
        } else {
          msg = (("The error \"" + error) + "\" was detected."); //$NON-NLS-2$//$NON-NLS-1$
        }

        try {
          logger.log(Level.SEVERE, msg, error);
        } catch (final Throwable t) {
          throw new RuntimeException(((//
              "Error when logging error message '"//$NON-NLS-1$ 
              + msg) + '\'') + '.');
        } finally {
          _LoggedSentinel._markAsLogged(error, msg, logger);
        }
      }
    }
  }
}
