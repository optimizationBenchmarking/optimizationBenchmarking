package org.optimizationBenchmarking.utils.parallel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * A future which represents an error in execution.
 *
 * @param <T>
 *          the result type
 */
final class _ExceptionFuture<T> implements Future<T> {

  /** the encountered error */
  private final Throwable m_error;

  /**
   * create the future
   *
   * @param error
   *          the encountered error
   */
  _ExceptionFuture(final Throwable error) {
    super();
    this.m_error = error;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean cancel(final boolean mayInterruptIfRunning) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isCancelled() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isDone() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final T get() throws ExecutionException {
    throw new ExecutionException(//
        "An error was encountered while executing a task.", //$NON-NLS-1$
        this.m_error);
  }

  /** {@inheritDoc} */
  @Override
  public final T get(final long timeout, final TimeUnit unit)
      throws ExecutionException {
    return this.get();
  }
}
