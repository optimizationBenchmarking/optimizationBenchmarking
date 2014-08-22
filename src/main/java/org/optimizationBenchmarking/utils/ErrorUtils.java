package org.optimizationBenchmarking.utils;

import java.io.IOException;

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
   * error took place. In this case, if there also was a
   * {@code recoverable}
   * 
   * @param unrecoverable
   *          the error
   * @param recoverable
   *          the recoverable error
   * @throws RuntimeException
   *           if there was an unrecoverable RuntimeException
   * @throws IOException
   *           if there was an io exception
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
   * Aggregate two {@link java.lang.Throwable throwables}
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
}
