package org.optimizationBenchmarking.utils.error;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.text.TextUtils;

/** Some utilities to deal with errors */
public final class ErrorUtils {

  /**
   * Turn a non-{@code null} object into an instance of
   * {@link java.lang.Throwable}.
   *
   * @param object
   *          the object
   * @return the {@link java.lang.Throwable}
   */
  static final Throwable _throwable(final Object object) {

    if (object instanceof Throwable) {
      return ((Throwable) object);
    }

    return new IllegalArgumentException(
        (((("Can only aggregate errors or aggregation handles, but tried to aggregate an instance of " + //$NON-NLS-1$
        TextUtils.className(object.getClass())) + " with String representation '") + object.toString()) + //$NON-NLS-1$
        '\'') + '.');
  }

  /**
   * <p>
   * Aggregate errors (instances of {@link java.lang.Throwable} or
   * aggregation handles. The goal of this method is to collect several
   * exceptions that may, together, lead to the failure of one routine. The
   * collected errors can then be
   * {@link org.optimizationBenchmarking.utils.error.RethrowMode#rethrow(String, boolean, Object)
   * thrown together}, as new error if necessary, via a certain
   * {@link org.optimizationBenchmarking.utils.error.RethrowMode} or logged
   * to a {@link java.util.logging.Logger} via the
   * {@link #logError(Logger, Level, String, Object, boolean, RethrowMode)}
   * -family of methods.
   * </p>
   * <p>
   * The return value is an "error aggregation handle" which must be used
   * instead of both {@code aggregationHandleOrError1} and
   * {@code aggregationHandleOrError2} in all future error processing
   * routines.
   * </p>
   * <p>
   * The resulting handles can be processed by the
   * {@link #logError(Logger, Level, String, Object, boolean, RethrowMode)}
   * -family of functions as well as by the instances of
   * {@link org.optimizationBenchmarking.utils.error.RethrowMode}.
   * </p>
   *
   * @param aggregationHandleOrError1
   *          The first error (instance of {@link java.lang.Throwable}) or
   *          aggregation handle, or {@code null} if no error was
   *          aggregated in this parameter. The general contract is that
   *          this error (list) was caused before the one in
   *          {@code aggregationHandleOrError2}.
   * @param aggregationHandleOrError2
   *          The second error (instance of {@link java.lang.Throwable}) or
   *          aggregation handle, or {@code null} if no error was
   *          aggregated in this parameter. The general contract is that
   *          this error (list) was caused after the one in
   *          {@code aggregationHandleOrError1}.
   * @return the aggregation handle, or {@code null} if both
   *         {@code aggregationHandleOrError1} and
   *         {@code aggregationHandleOrError2} were {@code null}
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final Object aggregateError(
      final Object aggregationHandleOrError1,
      final Object aggregationHandleOrError2) {
    final ArrayList<Throwable> useHandle;
    final Collection<Throwable> col;

    if (aggregationHandleOrError1 == null) {
      return aggregationHandleOrError2;
    }
    if (aggregationHandleOrError2 == null) {
      return aggregationHandleOrError1;
    }

    if (aggregationHandleOrError1 instanceof Collection) {
      if (aggregationHandleOrError2 instanceof Iterable) {
        col = ((Collection) aggregationHandleOrError1);
        for (final Object t : ((Iterable) aggregationHandleOrError2)) {
          if (t != null) {
            col.add(ErrorUtils._throwable(t));
          }
        }
      } else {
        ((Collection) aggregationHandleOrError1).add(//
            ErrorUtils._throwable(aggregationHandleOrError2));
      }
      return aggregationHandleOrError1;
    }

    if (aggregationHandleOrError2 instanceof Collection) {
      ((Collection) aggregationHandleOrError2).add(//
          ErrorUtils._throwable(aggregationHandleOrError1));
      return aggregationHandleOrError2;
    }

    useHandle = new ArrayList<>();
    useHandle.add(ErrorUtils._throwable(aggregationHandleOrError1));
    useHandle.add(ErrorUtils._throwable(aggregationHandleOrError2));
    return useHandle;
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
   * Log a {@link java.lang.Throwable error} or
   * {@link #aggregateError(Object, Object) aggregation handle} to a
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
   * Afterwards, depending on the chosen
   * {@link org.optimizationBenchmarking.utils.error.RethrowMode
   * rethrowMode}, throw the error again (e.g., as
   * {@link org.optimizationBenchmarking.utils.error.RethrowMode#AS_IO_EXCEPTION
   * IOException} or
   * {@link org.optimizationBenchmarking.utils.error.RethrowMode#AS_RUNTIME_EXCEPTION
   * RuntimeException}, or
   * {@link org.optimizationBenchmarking.utils.error.RethrowMode#DONT_RETHROW
   * discard} it.
   * </p>
   * <p>
   * Additionally, {@code message} will be stored in the generated error.
   * If the error is logged to the same logger and with
   * {@code forceLog=false} via
   * {@link #logError(Logger, Level, String, Object, boolean, RethrowMode)}
   * , it will not be logged. If the same error is logged to the same
   * {@code logger} again (with {@code forceLog==true}) or to another
   * logger, {@code message} will be listed as well.
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
   * @param errorOrAggregationHandle
   *          the error or aggregation handle: either an instance of
   *          {@link java.lang.Throwable} or a handle created via
   *          {@link #aggregateError(Object, Object)}
   * @param forceLog
   *          force logging: always log if the logger is not {@code null}
   *          and can log the log the specified {@code level}
   * @param rethrowMode
   *          the
   *          {@link org.optimizationBenchmarking.utils.error.RethrowMode
   *          rethrow mode}
   * @param <T>
   *          the error type which may be thrown
   * @throws T
   *           the error
   */
  public static final <T extends Throwable> void logError(
      final Logger logger, final String message,
      final Object errorOrAggregationHandle, final boolean forceLog,
      final RethrowMode<T> rethrowMode) throws T {
    ErrorUtils.logError(logger, Level.SEVERE, message,
        errorOrAggregationHandle, forceLog, rethrowMode);
  }

  /**
   * <p>
   * Log a {@link java.lang.Throwable error} or
   * {@link #aggregateError(Object, Object) aggregation handle} to a
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
   * Afterwards, depending on the chosen
   * {@link org.optimizationBenchmarking.utils.error.RethrowMode
   * rethrowMode}, throw the error again (e.g., as
   * {@link org.optimizationBenchmarking.utils.error.RethrowMode#AS_IO_EXCEPTION
   * IOException} or
   * {@link org.optimizationBenchmarking.utils.error.RethrowMode#AS_RUNTIME_EXCEPTION
   * RuntimeException}, or
   * {@link org.optimizationBenchmarking.utils.error.RethrowMode#DONT_RETHROW
   * discard} it.
   * </p>
   * <p>
   * Additionally, {@code message} will be stored in the generated error.
   * If the error is logged to the same logger and with
   * {@code forceLog=false} via
   * {@link #logError(Logger, Level, String, Object, boolean, RethrowMode)}
   * , it will not be logged. If the same error is logged to the same
   * {@code logger} again (with {@code forceLog==true}) or to another
   * logger, {@code message} will be listed as well.
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
   * @param errorOrAggregationHandle
   *          the error or aggregation handle: either an instance of
   *          {@link java.lang.Throwable} or a handle created via
   *          {@link #aggregateError(Object, Object)}
   * @param forceLog
   *          force logging: always log if the logger is not {@code null}
   *          and can log the log the specified {@code level}
   * @param rethrowMode
   *          the
   *          {@link org.optimizationBenchmarking.utils.error.RethrowMode
   *          rethrow mode}
   * @param <T>
   *          the error type which may be thrown
   * @throws T
   *           the error
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final <T extends Throwable> void logError(
      final Logger logger, final Level level, final String message,
      final Object errorOrAggregationHandle, final boolean forceLog,
      final RethrowMode<T> rethrowMode) throws T {
    final boolean isLogged;
    final RethrowMode<T> mode;
    final Throwable error;
    final String msg;
    final Level useLevel;

    useLevel = ((level != null) ? level : Level.SEVERE);
    mode = ((rethrowMode != null) ? rethrowMode
        : ((RethrowMode) (RethrowMode.DONT_RETHROW)));
    error = mode._handleToError(message, false, errorOrAggregationHandle);

    // If a logger is provided, then we try to log the error and the
    // message.
    if ((logger != null) && (logger.isLoggable(useLevel))) {
      isLogged = _LoggedSentinel._hasBeenLogged(error, logger, useLevel);
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
          _LoggedSentinel._markAsLogged(error, msg, logger, useLevel);
        }
      }
    }

    mode._rethrow(error);
  }
}
