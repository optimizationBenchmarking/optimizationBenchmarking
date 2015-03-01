package org.optimizationBenchmarking.utils.error;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Some utilities to deal with errors */
public final class ErrorUtils {

  /**
   * Aggregate two errors or aggregation handles
   * 
   * @param aggregationHandleOrError1
   *          the first error or aggregation handle
   * @param aggregationHandleOrError2
   *          the second error or aggregation handle
   * @return the aggregation handle
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final Object aggregateError(
      final Object aggregationHandleOrError1,
      final Object aggregationHandleOrError2) {
    final ArrayList<Throwable> useHandle;

    if (aggregationHandleOrError1 == null) {
      return aggregationHandleOrError2;
    }
    if (aggregationHandleOrError2 == null) {
      return aggregationHandleOrError1;
    }

    if (aggregationHandleOrError1 instanceof Throwable) {
      if (aggregationHandleOrError2 instanceof ArrayList) {
        useHandle = ((ArrayList) (aggregationHandleOrError2));
        useHandle.add((Throwable) aggregationHandleOrError1);
        return useHandle;
      }
      useHandle = new ArrayList<>();
      useHandle.add((Throwable) aggregationHandleOrError1);
      useHandle.add((Throwable) aggregationHandleOrError2);
      return useHandle;
    }

    useHandle = ((ArrayList) aggregationHandleOrError1);
    if (aggregationHandleOrError2 instanceof ArrayList) {
      useHandle.addAll((ArrayList) aggregationHandleOrError2);
    } else {
      useHandle.add((Throwable) aggregationHandleOrError2);
    }
    return useHandle;
  }

  /**
   * Throw an aggregated error as {@link java.lang.RuntimeException}
   * 
   * @param message
   *          the error message
   * @param aggregationHandle
   * @throws RuntimeException
   *           will <em>always</em> be thrown
   */
  @SuppressWarnings("unchecked")
  public static final void throwRuntimeException(final String message,
      final Object aggregationHandle) throws RuntimeException {
    final RuntimeException re;

    if (aggregationHandle instanceof Throwable) {
      if (message != null) {
        throw new RuntimeException(message,
            ((Throwable) aggregationHandle));
      }
      if (aggregationHandle instanceof RuntimeException) {
        throw ((RuntimeException) aggregationHandle);
      }
      throw new RuntimeException(((Throwable) aggregationHandle));
    }

    if (message != null) {
      re = new RuntimeException(message);
    } else {
      re = new RuntimeException();
    }

    if (aggregationHandle instanceof ArrayList) {
      for (final Throwable t : ((ArrayList<Throwable>) aggregationHandle)) {
        re.addSuppressed(t);
      }
    }

    throw re;
  }

  /**
   * Throw an aggregated error as {@link java.io.IOException}
   * 
   * @param message
   *          the error message
   * @param aggregationHandle
   * @throws IOException
   *           will <em>always</em> be thrown
   */
  @SuppressWarnings("unchecked")
  public static final void throwIOException(final String message,
      final Object aggregationHandle) throws IOException {
    final IOException re;

    if (aggregationHandle instanceof Throwable) {
      if (message != null) {
        throw new IOException(message, ((Throwable) aggregationHandle));
      }
      if (aggregationHandle instanceof IOException) {
        throw ((IOException) aggregationHandle);
      }
      throw new IOException(((Throwable) aggregationHandle));
    }

    if (message != null) {
      re = new IOException(message);
    } else {
      re = new IOException();
    }

    if (aggregationHandle instanceof ArrayList) {
      for (final Throwable t : ((ArrayList<Throwable>) aggregationHandle)) {
        re.addSuppressed(t);
      }
    }

    throw re;
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
   * <p>
   * Log the {@link java.lang.Throwable error} to a
   * {@link java.util.logging.Logger} with message {@code message}, if
   * <ol>
   * <li>the logger is not {@code null},</li>
   * <li>the logger {@link java.util.logging.Logger#isLoggable(Level) can
   * log} the log level {@link java.util.logging.Level#SEVERE}, and</li>
   * <li>either
   * <ul>
   * <li>the error has not yet been logged to the {@code logger} before or</li>
   * <li>{@code forceLog==true}</li>
   * </ul>
   * </li>
   * </ol>
   * <p>
   * Additionally, {@code message} will be stored in {@code error}. If the
   * same {@code error} is logged to the same {@code logger} again (with
   * {@code forceLog==true}) or to another logger, {@code message} will be
   * listed as well.
   * </p>
   * <p>
   * This central error logging routine makes sure that the same error is
   * not logged too often and the logs may get congested. Thus, we can very
   * liberally add error logging calls in our code.
   * </p>
   * <p>
   * The parameter {@code forceLog} should only be set to {@code true} if
   * {@code message} contains additional information which may help us
   * track down the error. For example, if {@code message} is
   * {@code "An error has been detected while reading a file"}, then
   * {@code forceLog} should be {@code false}. If {@code message} is
   * instead
   * {@code "An error has been detected while reading the file foo.txt"},
   * {@code forceLog} should be {@code true}.
   * </p>
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
    ErrorUtils.logError(logger, Level.SEVERE, message, error, forceLog);
  }

  /**
   * <p>
   * Log the {@link java.lang.Throwable error} to a
   * {@link java.util.logging.Logger} with message {@code message}, if
   * <ol>
   * <li>the logger is not {@code null},</li>
   * <li>the logger {@link java.util.logging.Logger#isLoggable(Level) can
   * log} the specified log {@link java.util.logging.Level level}, and</li>
   * <li>either
   * <ul>
   * <li>the error has not yet been logged to the {@code logger} before or</li>
   * <li>{@code forceLog==true}</li>
   * </ul>
   * </li>
   * </ol>
   * <p>
   * Additionally, {@code message} will be stored in {@code error}. If the
   * same {@code error} is logged to the same {@code logger} again (with
   * {@code forceLog==true}) or to another logger, {@code message} will be
   * listed as well.
   * </p>
   * <p>
   * This central error logging routine makes sure that the same error is
   * not logged too often and the logs may get congested. Thus, we can very
   * liberally add error logging calls in our code.
   * </p>
   * <p>
   * The parameter {@code forceLog} should only be set to {@code true} if
   * {@code message} contains additional information which may help us
   * track down the error. For example, if {@code message} is
   * {@code "An error has been detected while reading a file"}, then
   * {@code forceLog} should be {@code false}. If {@code message} is
   * instead
   * {@code "An error has been detected while reading the file foo.txt"},
   * {@code forceLog} should be {@code true}.
   * </p>
   * 
   * @param logger
   *          the logger to log to
   * @param level
   *          the log level to use, or {@code null} to use the default
   *          level ({@link java.util.logging.Level#SEVERE})
   * @param message
   *          the message
   * @param error
   *          the error
   * @param forceLog
   *          force logging: always log if the logger is not {@code null}
   *          and can log the log the specified {@code level}
   */
  public static final void logError(final Logger logger,
      final Level level, final String message, final Throwable error,
      final boolean forceLog) {
    final boolean isLogged;
    final String msg;
    final Level useLevel;

    useLevel = ((level != null) ? level : Level.SEVERE);

    // If a logger is provided, then we try to log the error and the
    // message.
    if ((logger != null) && (logger.isLoggable(useLevel))) {
      isLogged = _LoggedSentinel._hasBeenLogged(error, logger);
      if (forceLog || (!isLogged)) {

        if (message != null) {
          msg = message;
        } else {
          msg = (("The error \"" + error) + "\" was detected."); //$NON-NLS-2$//$NON-NLS-1$
        }

        try {
          logger.log(useLevel, msg, error);
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
