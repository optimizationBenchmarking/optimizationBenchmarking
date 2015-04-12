package org.optimizationBenchmarking.utils.error;

/**
 * The re-throw mode which does re-throw an as
 * {@link java.lang.IllegalArgumentException}.
 */
final class _RethrowModeRethrowAsIllegalArgumentException extends
    RethrowMode<IllegalArgumentException> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  _RethrowModeRethrowAsIllegalArgumentException() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final IllegalArgumentException _handleToError(final String message,
      final boolean enforceMessage, final Object handle) {
    final String useMessage;
    IllegalArgumentException re;

    useMessage = ((message != null) ? //
    (message + ' ' + RethrowMode.SYNTHETIC_RUNTIME_EXCEPTION_MSG)
        : RethrowMode.SYNTHETIC_RUNTIME_EXCEPTION_MSG);

    if (handle == null) {
      // No error handle is provided: generate a new, vanilla
      // IllegalArgumentException
      return new IllegalArgumentException(useMessage);
    }

    if (handle instanceof Throwable) {
      // OK, handle represents a singular error.

      if (handle instanceof IllegalArgumentException) {
        // If handle is an IllegalArgumentException and either no message
        // is provided or the same message as stored in handle, we can
        // directly return it.
        re = ((IllegalArgumentException) handle);

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

      // handle is a Throwable, but either not an IllegalArgumentException
      // or a different message is given, so we use handle as cause.
      return new IllegalArgumentException(useMessage, ((Throwable) handle));
    }

    // If handle is a collection of errors, we create an
    // IllegalArgumentException and add the errors as suppressed errors.
    re = new IllegalArgumentException(useMessage);

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
   * @return {@link org.optimizationBenchmarking.utils.error.RethrowMode#AS_UNSUPPORTED_OPERATION_EXCEPTION}
   */
  private final Object readResolve() {
    return RethrowMode.AS_ILLEGAL_ARGUMENT_EXCEPTION;
  }

  /**
   * write-replace
   * 
   * @return {@link org.optimizationBenchmarking.utils.error.RethrowMode#AS_UNSUPPORTED_OPERATION_EXCEPTION}
   */
  private final Object writeReplace() {
    return RethrowMode.AS_ILLEGAL_ARGUMENT_EXCEPTION;
  }
}
