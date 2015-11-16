package org.optimizationBenchmarking.utils.parallel;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * A future which represents an immediate, successful execution.
 *
 * @param <T>
 *          the result type
 */
final class _ImmediateFuture<T> implements Future<T> {

  /** a shared future with {@code null} result */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private static final _ImmediateFuture NULL_FUTURE = new _ImmediateFuture(
      null);

  /** the result */
  private final T m_result;

  /**
   * create the future
   *
   * @param result
   *          the result
   */
  private _ImmediateFuture(final T result) {
    super();
    this.m_result = result;
  }

  /**
   * A {@link java.util.concurrent.Future future} representing an
   * already-finished computation.
   *
   * @param <T>
   *          the data type
   * @param result
   *          the result
   * @return the future
   */
  static final <T> Future<T> _create(final T result) {
    return ((result == null) ? _ImmediateFuture.NULL_FUTURE
        : new _ImmediateFuture<>(result));
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
  public final T get() {
    return this.m_result;
  }

  /** {@inheritDoc} */
  @Override
  public final T get(final long timeout, final TimeUnit unit) {
    return this.m_result;
  }
}
