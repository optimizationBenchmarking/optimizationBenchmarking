package org.optimizationBenchmarking.utils.error;

/** The re-throw mode which does not re-throw an error. */
final class _RethrowModeDontRethrow extends RethrowMode<RuntimeException> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  _RethrowModeDontRethrow() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final Throwable _handleToError(final String message,
      final boolean enforceMessage, final Object handle) {
    final String useMessage;
    Throwable re;

    useMessage = ((message != null) ? //
        (message + ' ' + RethrowMode.SYNTHETIC_RUNTIME_EXCEPTION_MSG)
        : RethrowMode.SYNTHETIC_RUNTIME_EXCEPTION_MSG);

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

      // handle is a Throwable, but a different message is given, so we use
      // handle as cause.
      return new RuntimeException(useMessage, ((Throwable) handle));
    }

    // If handle is a collection of errors, we create a RuntimeException
    // and add the errors as suppressed errors.
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
}
