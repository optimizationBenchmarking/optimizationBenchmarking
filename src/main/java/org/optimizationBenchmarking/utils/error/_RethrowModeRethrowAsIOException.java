package org.optimizationBenchmarking.utils.error;

import java.io.IOException;

/**
 * The re-throw mode which does re-throw an as {@link java.io.IOException}.
 */
final class _RethrowModeRethrowAsIOException extends
    RethrowMode<IOException> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the synthetic message */
  private static final String SYNTHETIC_MSG = //
  "This is a synthetic instance of java.io.IOException generated to wrap one or multiple real exceptions. See the causing and suppressed errors to find the real cause."; //$NON-NLS-1$

  /** create */
  _RethrowModeRethrowAsIOException() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final IOException _handleToError(final String message,
      final boolean enforceMessage, final Object handle) {
    final String useMessage;
    IOException re;

    useMessage = ((message != null) ? //
    (message + ' ' + _RethrowModeRethrowAsIOException.SYNTHETIC_MSG)
        : _RethrowModeRethrowAsIOException.SYNTHETIC_MSG);

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

    // If handle is a collection of errors, we create an IOException
    // and add the errors as suppressed errors.
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
   * @return {@link org.optimizationBenchmarking.utils.error.RethrowMode#AS_IO_EXCEPTION}
   */
  private final Object readResolve() {
    return RethrowMode.AS_IO_EXCEPTION;
  }

  /**
   * write-replace
   *
   * @return {@link org.optimizationBenchmarking.utils.error.RethrowMode#AS_IO_EXCEPTION}
   */
  private final Object writeReplace() {
    return RethrowMode.AS_IO_EXCEPTION;
  }
}
