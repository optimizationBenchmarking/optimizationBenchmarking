package org.optimizationBenchmarking.utils.error;

import java.io.IOException;
import java.io.Serializable;

/**
 * <p>
 * A {@link RethrowMode} allows us to "cast" a {@link java.lang.Throwable}
 * to an arbitrary sub-class such as {@link java.lang.RuntimeException} or
 * {@link java.io.IOException} and to {@code throw} this new error. There
 * are three use-cases for this functionality:
 * </p>
 * <ol>
 * <li>Methods which are not allowed to throw a certain type of exception
 * but call other methods which might do exactly that. One example are
 * implementations of an API which are not supposed to generate
 * {@link java.io.IOException}s, but use Java's IO. The methods of our
 * interface
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput},
 * for instance, do not throw {@link java.io.IOException}s, as they may be
 * implemented by working on memory buffers and since otherwise, we would
 * need a lot of {@code try...catch} code around them in the many places we
 * use them. However, we also implement this interface as wrapper around
 * {@link java.io.Writer}s, for instance (see
 * {@link org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput#wrap(Appendable)}
 * . These implementations call methods which may cause
 * {@link java.io.IOException}s but are not allow to throw such exceptions,
 * so they can simply throw them as {@link #AS_RUNTIME_EXCEPTION
 * RuntimeException}s.</li>
 * <li>The second use case is in our advanced
 * {@link org.optimizationBenchmarking.utils.error.ErrorUtils#logError(java.util.logging.Logger, java.util.logging.Level, String, Object, boolean, RethrowMode)}
 * error logging provided by class
 * {@link org.optimizationBenchmarking.utils.error.ErrorUtils}. We can log
 * an exception to a given {@link java.util.logging.Logger} and then either
 * {@link #DONT_RETHROW discard it} or directly re-throw it.</li>
 * <li>Finally, {@link RethrowMode}s are the <em>only</em> way to convert
 * {@link org.optimizationBenchmarking.utils.error.ErrorUtils#aggregateError(Object, Object)
 * error aggregation handles} back to exceptions. These handles allow us to
 * gather one or many errors which together may have caused a process to
 * fail and (with a {@link RethrowMode}) throw them as one single
 * exception. This provides additional semantic clarity to whoever is
 * reading the error logs or receiving the errors: She can clearly see that
 * a given set of errors belongs together, has together contributed to the
 * failure of one single process, and she can see the sequence and order of
 * these errors. Otherwise, there may be multiple entries in an error log
 * or, if the errors are {@link java.lang.Throwable#printStackTrace()}ed to
 * different streams, maybe even a corrupted or unclear sequence.</li>
 * </ol>
 *
 * @param <T>
 *          the error thrown
 */
public abstract class RethrowMode<T extends Throwable> implements
Serializable {

  /**
   * This re-throw mode will consume the error and not re-throw it.
   */
  public static final RethrowMode<RuntimeException> DONT_RETHROW = //
      new _RethrowModeDontRethrow();

  /**
   * This re-throw mode will throw the error as
   * {@link java.lang.RuntimeException}.
   */
  public static final RethrowMode<RuntimeException> AS_RUNTIME_EXCEPTION = //
      new _RethrowModeRethrowAsRuntimeException();

  /**
   * This re-throw mode will throw the error as {@link java.io.IOException}
   * .
   */
  public static final RethrowMode<IOException> AS_IO_EXCEPTION = //
      new _RethrowModeRethrowAsIOException();

  /**
   * This re-throw mode will throw the error as {@link java.lang.Throwable}
   * .
   */
  public static final RethrowMode<Throwable> AS_THROWABLE = //
      new _RethrowModeRethrowAsThrowable();
  /**
   * This re-throw mode will throw the error as
   * {@link java.lang.UnsupportedOperationException} .
   */
  public static final RethrowMode<UnsupportedOperationException> AS_UNSUPPORTED_OPERATION_EXCEPTION = //
      new _RethrowModeRethrowAsUnsupportedOperationException();

  /**
   * This re-throw mode will throw the error as
   * {@link java.lang.IllegalStateException} .
   */
  public static final RethrowMode<IllegalStateException> AS_ILLEGAL_STATE_EXCEPTION = //
      new _RethrowModeRethrowAsIllegalStateException();

  /**
   * This re-throw mode will throw the error as
   * {@link java.lang.IllegalArgumentException} .
   */
  public static final RethrowMode<IllegalArgumentException> AS_ILLEGAL_ARGUMENT_EXCEPTION = //
      new _RethrowModeRethrowAsIllegalArgumentException();

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the synthetic message */
  static final String SYNTHETIC_RUNTIME_EXCEPTION_MSG = //
      "This is a synthetic instance of java.lang.RuntimeException generated to wrap one or multiple real exceptions. See the causing and suppressed errors to find the real cause."; //$NON-NLS-1$

  /** create a new rethrow mode */
  RethrowMode() {
    super();
  }

  /**
   * Convert an aggregation handle to an error.
   *
   * @param message
   *          the message
   * @param enforceMessage
   *          create a new error even if {@code handle} is already one of
   *          the right type
   * @param handle
   *          either an instance of {@link java.lang.Throwable} or an
   *          aggregation handle returned from
   *          {@link org.optimizationBenchmarking.utils.error.ErrorUtils#aggregateError(Object, Object)}
   * @return the error
   */
  abstract Throwable _handleToError(final String message,
      final boolean enforceMessage, final Object handle);

  /**
   * Throw the error(s) represented by the aggregation handle
   * {@code handle} as the exception type supported by this rethrow mode.
   *
   * @param message
   *          the message
   * @param enforceMessage
   *          Should a new error be created and thrown even if
   *          {@code handle} already represents an exception of the same
   *          type?
   * @param handle
   *          either an instance of {@link java.lang.Throwable} or an
   *          aggregation handle returned from
   *          {@link org.optimizationBenchmarking.utils.error.ErrorUtils#aggregateError(Object, Object)}
   * @throws T
   *           the error type thrown
   */
  public final void rethrow(final String message,
      final boolean enforceMessage, final Object handle) throws T {
    this._rethrow(this._handleToError(message, enforceMessage, handle));
  }

  /**
   * Throw the error
   *
   * @param error
   *          the error
   * @throws T
   *           and here it's thrown
   */
  @SuppressWarnings("unchecked")
  void _rethrow(final Throwable error) throws T {
    if (error != null) {
      throw ((T) error);
    }
  }
}
