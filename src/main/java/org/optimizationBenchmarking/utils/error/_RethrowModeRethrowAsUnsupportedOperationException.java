package org.optimizationBenchmarking.utils.error;

/**
 * The re-throw mode which does re-throw an as
 * {@link java.lang.UnsupportedOperationException}.
 */
final class _RethrowModeRethrowAsUnsupportedOperationException extends
    RethrowMode<UnsupportedOperationException> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  _RethrowModeRethrowAsUnsupportedOperationException() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final UnsupportedOperationException _handleToError(final String message,
      final boolean enforceMessage, final Object handle) {
    final String useMessage;
    UnsupportedOperationException re;

    useMessage = ((message != null) ? //
    (message + ' ' + RethrowMode.SYNTHETIC_RUNTIME_EXCEPTION_MSG)
        : RethrowMode.SYNTHETIC_RUNTIME_EXCEPTION_MSG);

    if (handle == null) {
      // No error handle is provided: generate a new, vanilla
      // UnsupportedOperationException
      return new UnsupportedOperationException(useMessage);
    }

    if (handle instanceof Throwable) {
      // OK, handle represents a singular error.

      if (handle instanceof UnsupportedOperationException) {
        // If handle is an UnsupportedOperationException and either no
        // message is provided or the same message as stored in handle, we
        // can directly return it.
        re = ((UnsupportedOperationException) handle);

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

      // handle is a Throwable, but either not an
      // UnsupportedOperationException or a different message is given, so
      // we use handle as cause.
      return new UnsupportedOperationException(useMessage,
          ((Throwable) handle));
    }

    // If handle is a collection of errors, we create an
    // UnsupportedOperationException and add the errors as suppressed
    // errors.
    re = new UnsupportedOperationException(useMessage);

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
    return RethrowMode.AS_UNSUPPORTED_OPERATION_EXCEPTION;
  }

  /**
   * write-replace
   * 
   * @return {@link org.optimizationBenchmarking.utils.error.RethrowMode#AS_UNSUPPORTED_OPERATION_EXCEPTION}
   */
  private final Object writeReplace() {
    return RethrowMode.AS_UNSUPPORTED_OPERATION_EXCEPTION;
  }
}
