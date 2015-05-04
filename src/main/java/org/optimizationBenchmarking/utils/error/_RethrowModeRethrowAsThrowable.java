package org.optimizationBenchmarking.utils.error;

/**
 * The re-throw mode which does re-throw an error as
 * {@link java.lang.Throwable}.
 */
final class _RethrowModeRethrowAsThrowable extends RethrowMode<Throwable> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;
  /** the synthetic message */
  private static final String SYNTHETIC_MSG = //
  "This is a synthetic instance of java.lang.Throwable generated to wrap one or multiple real exceptions. See the causing and suppressed errors to find the real cause."; //$NON-NLS-1$

  /** create */
  _RethrowModeRethrowAsThrowable() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final Throwable _handleToError(final String message,
      final boolean enforceMessage, final Object handle) {
    final String useMessage;
    Throwable re;

    useMessage = ((message != null) ? (message + ' ' + _RethrowModeRethrowAsThrowable.SYNTHETIC_MSG)
        : _RethrowModeRethrowAsThrowable.SYNTHETIC_MSG);

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

    // If handle is a collection of errors, we create a Throwable
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

  /**
   * read-resolve
   *
   * @return {@link org.optimizationBenchmarking.utils.error.RethrowMode#AS_THROWABLE}
   */
  private final Object readResolve() {
    return RethrowMode.AS_THROWABLE;
  }

  /**
   * write-replace
   *
   * @return {@link org.optimizationBenchmarking.utils.error.RethrowMode#AS_THROWABLE}
   */
  private final Object writeReplace() {
    return RethrowMode.AS_THROWABLE;
  }
}
