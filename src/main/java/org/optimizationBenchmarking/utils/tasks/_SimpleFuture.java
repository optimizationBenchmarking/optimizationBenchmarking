package org.optimizationBenchmarking.utils.tasks;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * a simple future
 * 
 * @param <T>
 *          the return type
 */
final class _SimpleFuture<T> implements Future<T> {

  /** the return value */
  private final T m_ret;

  /** the error */
  private final Throwable m_error;

  /**
   * create
   * 
   * @param ret
   *          the return value
   * @param error
   *          the error
   */
  _SimpleFuture(final T ret, final Throwable error) {
    super();
    this.m_ret = ret;
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
    if (this.m_error != null) {
      throw new ExecutionException(this.m_error);
    }
    return this.m_ret;
  }

  /** {@inheritDoc} */
  @Override
  public final T get(final long timeout, final TimeUnit unit)
      throws ExecutionException {
    if (this.m_error != null) {
      throw new ExecutionException(this.m_error);
    }
    return this.m_ret;
  }

}
