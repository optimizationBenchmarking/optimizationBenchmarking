package org.optimizationBenchmarking.utils.parallel;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Execute jobs in sequence and wait for their completion. If the current
 * thread is part of a {@link java.util.concurrent.ForkJoinPool}, the
 * submitted tasks will actually be submitted to said pool. Otherwise, they
 * will simple be executed in a sequential fashion. The {@code execute}
 * methods will always wait until all tasks have completed (or failed). It
 * behaves no different from executing all tasks in the current thread, one
 * by one.
 */
final class _ExecuteSequentiallyAndWait extends JobExecutor {

  /** the globally shared instance */
  static final _ExecuteSequentiallyAndWait INSTANCE = new _ExecuteSequentiallyAndWait();

  /** create */
  private _ExecuteSequentiallyAndWait() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final <T> Future<T> execute(final T result, final Runnable job) {
    return JobExecutor._executeImmediately(result, job);
  }

  /** {@inheritDoc} */
  @Override
  public final <T> Future<T> execute(final Callable<T> job) {
    return JobExecutor._executeImmediately(job);
  }

  /** {@inheritDoc} */
  @Override
  public final <T> Future<T>[] execute(final T result,
      final Runnable... jobs) {
    return JobExecutor._executeImmediately(result, jobs);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final <T> Future<T>[] execute(final Callable<T>... jobs) {
    return JobExecutor._executeImmediately(jobs);
  }
}
