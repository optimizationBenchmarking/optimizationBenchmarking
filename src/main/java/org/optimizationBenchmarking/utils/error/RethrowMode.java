package org.optimizationBenchmarking.utils.error;

import java.io.IOException;
import java.io.Serializable;

/**
 * The rethrow modes.
 * 
 * @param <G>
 *          the type of generated error
 * @param <T>
 *          the error thrown
 */
public abstract class RethrowMode<G extends Throwable, T extends Throwable>
    implements Serializable {

  /** the synthetic message */
  static final String SYNTHETIC_RUNTIME_EXCEPTION_MSG = //
  "This is a synthetic RuntimeException generated to wrap one or multiple real exceptions. See the causing and suppressed errors to find the real cause."; //$NON-NLS-1$

  /**
   * This re-throw mode will consume the error and not re-throw it.
   */
  public static final RethrowMode<Throwable, RuntimeException> DONT_RETHROW = //
  new RethrowMode<Throwable, RuntimeException>() {

    /** the serial version uid */
    private static final long serialVersionUID = 1L;

    /** {@inheritDoc} */
    @SuppressWarnings("rawtypes")
    @Override
    final Throwable _handleToError(final String message,
        final boolean enforceMessage, final Object handle) {
      final String useMessage;
      Throwable re;

      useMessage = ((message != null) ? //
      (message + ' ' + SYNTHETIC_RUNTIME_EXCEPTION_MSG)
          : SYNTHETIC_RUNTIME_EXCEPTION_MSG);

      if (handle == null) {
        // No error handle is provided: generate a new, vanilla
        // RuntimeException
        return new RuntimeException(useMessage);
      }

      if (handle instanceof Throwable) {
        // OK, handle represents a singular error.

        // If handle either no message is provided or the same message as
        // stored in handle, we can directly return it.
        re = ((Throwable) handle);

        if ((message == null) || (!enforceMessage)) {
          return re;
        }

        if (message.equals(re.getMessage())) {
          return re;
        }

        if (message.equals(re.getLocalizedMessage())) {
          return re;
        }

        // handle is a Throwable, but either not a RuntimeException or a
        // different message is given, so we use handle as cause.
        return new RuntimeException(useMessage, ((Throwable) handle));
      }

      // If handle is a collection of errors, we
      re = new RuntimeException(useMessage);

      if (handle instanceof Iterable) {
        for (final Object t : ((Iterable) handle)) {
          if (t != null) {
            re.addSuppressed(ErrorUtils._throwable(t));
          }
        }
      }

      return re;
    }

    /** {@inheritDoc} */
    @Override
    final void _rethrow(final Throwable error) {
      // do nothing
    }

    /**
     * read-resolve
     * 
     * @return {@link org.optimizationBenchmarking.utils.error.RethrowMode#DONT_RETHROW}
     */
    private final Object readResolve() {
      return RethrowMode.DONT_RETHROW;
    }

    /**
     * write-replace
     * 
     * @return {@link org.optimizationBenchmarking.utils.error.RethrowMode#DONT_RETHROW}
     */
    private final Object writeReplace() {
      return RethrowMode.DONT_RETHROW;
    }
  };

  /**
   * This re-throw mode will throw the error as
   * {@link java.lang.RuntimeException}.
   */
  public static final RethrowMode<RuntimeException, RuntimeException> THROW_AS_RUNTIME_EXCEPTION = //
  new RethrowMode<RuntimeException, RuntimeException>() {

    /** the serial version uid */
    private static final long serialVersionUID = 1L;

    /** {@inheritDoc} */
    @SuppressWarnings("rawtypes")
    @Override
    final RuntimeException _handleToError(final String message,
        final boolean enforceMessage, final Object handle) {
      final String useMessage;
      RuntimeException re;

      useMessage = ((message != null) ? //
      (message + ' ' + SYNTHETIC_RUNTIME_EXCEPTION_MSG)
          : SYNTHETIC_RUNTIME_EXCEPTION_MSG);

      if (handle == null) {
        // No error handle is provided: generate a new, vanilla
        // RuntimeException
        return new RuntimeException(useMessage);
      }

      if (handle instanceof Throwable) {
        // OK, handle represents a singular error.

        if (handle instanceof RuntimeException) {
          // If handle is a RuntimeException and either no message is
          // provided or the same message as stored in handle, we can
          // directly return it.
          re = ((RuntimeException) handle);

          if ((message == null) || (!enforceMessage)) {
            return re;
          }

          if (message.equals(re.getMessage())) {
            return re;
          }

          if (message.equals(re.getLocalizedMessage())) {
            return re;
          }
        }

        // handle is a Throwable, but either not a RuntimeException or a
        // different message is given, so we use handle as cause.
        return new RuntimeException(useMessage, ((Throwable) handle));
      }

      // If handle is a collection of errors, we
      re = new RuntimeException(useMessage);

      if (handle instanceof Iterable) {
        for (final Object t : ((Iterable) handle)) {
          if (t != null) {
            re.addSuppressed(ErrorUtils._throwable(t));
          }
        }
      }

      return re;
    }

    /**
     * read-resolve
     * 
     * @return {@link org.optimizationBenchmarking.utils.error.RethrowMode#THROW_AS_RUNTIME_EXCEPTION}
     */
    private final Object readResolve() {
      return RethrowMode.THROW_AS_RUNTIME_EXCEPTION;
    }

    /**
     * write-replace
     * 
     * @return {@link org.optimizationBenchmarking.utils.error.RethrowMode#THROW_AS_RUNTIME_EXCEPTION}
     */
    private final Object writeReplace() {
      return RethrowMode.THROW_AS_RUNTIME_EXCEPTION;
    }
  };

  /**
   * This re-throw mode will throw the error as {@link java.io.IOException}
   * .
   */
  public static final RethrowMode<IOException, IOException> THROW_AS_IO_EXCEPTION = new RethrowMode<IOException, IOException>() {

    /** the serial version uid */
    private static final long serialVersionUID = 1L;
    /** the synthetic message */
    private static final String SYNTHETIC_MSG = //
    "This is a synthetic IOException generated to wrap one or multiple real exceptions. See the causing and suppressed errors to find the real cause."; //$NON-NLS-1$

    /** {@inheritDoc} */
    @SuppressWarnings("rawtypes")
    @Override
    final IOException _handleToError(final String message,
        final boolean enforceMessage, final Object handle) {
      final String useMessage;
      IOException re;

      useMessage = ((message != null) ? //
      (message + ' ' + SYNTHETIC_MSG)
          : SYNTHETIC_MSG);

      if (handle == null) {
        // No error handle is provided: generate a new, vanilla
        // IOException
        return new IOException(useMessage);
      }

      if (handle instanceof Throwable) {
        // OK, handle represents a singular error.

        if (handle instanceof IOException) {
          // If handle is a IOException and either no message is
          // provided or the same message as stored in handle, we can
          // directly return it.
          re = ((IOException) handle);

          if ((message == null) || (!enforceMessage)) {
            return re;
          }

          if (message.equals(re.getMessage())) {
            return re;
          }

          if (message.equals(re.getLocalizedMessage())) {
            return re;
          }
        }

        // handle is a Throwable, but either not a IOException or a
        // different message is given, so we use handle as cause.
        return new IOException(useMessage, ((Throwable) handle));
      }

      // If handle is a collection of errors, we
      re = new IOException(useMessage);

      if (handle instanceof Iterable) {
        for (final Object t : ((Iterable) handle)) {
          if (t != null) {
            re.addSuppressed(ErrorUtils._throwable(t));
          }
        }
      }

      return re;
    }

    /**
     * read-resolve
     * 
     * @return {@link org.optimizationBenchmarking.utils.error.RethrowMode#THROW_AS_IO_EXCEPTION}
     */
    private final Object readResolve() {
      return RethrowMode.THROW_AS_IO_EXCEPTION;
    }

    /**
     * write-replace
     * 
     * @return {@link org.optimizationBenchmarking.utils.error.RethrowMode#THROW_AS_IO_EXCEPTION}
     */
    private final Object writeReplace() {
      return RethrowMode.THROW_AS_IO_EXCEPTION;
    }
  };

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

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
  abstract G _handleToError(final String message,
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
  void _rethrow(final G error) throws T {
    if (error != null) {
      throw ((T) error);
    }
  }
}
