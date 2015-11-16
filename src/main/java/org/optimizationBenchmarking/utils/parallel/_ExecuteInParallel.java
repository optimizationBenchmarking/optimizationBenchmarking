package org.optimizationBenchmarking.utils.parallel;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;

/**
 * Execute jobs in parallel (if possible) and without waiting for their
 * completion. If the current thread is part of a
 * {@link java.util.concurrent.ForkJoinPool}, the submitted tasks will
 * actually be submitted to said pool.
 */
final class _ExecuteInParallel extends JobExecutor {

  /** the globally shared instance */
  static final _ExecuteInParallel INSTANCE = new _ExecuteInParallel();

  /** create */
  private _ExecuteInParallel() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final <T> Future<T> execute(final T result, final Runnable job) {
    return JobExecutor._executeInParallel(result, job);
  }

  /** {@inheritDoc} */
  @Override
  public final <T> Future<T> execute(final Callable<T> job) {
    return JobExecutor._executeInParallel(job);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final <T> Future<T>[] execute(final T result,
      final Runnable... jobs) {
    final ForkJoinTask<T>[] tasks;
    final ForkJoinPool pool;
    int i;

    i = jobs.length;
    if (i <= 0) {// no tasks, we can quit directly
      return new Future[0];
    }

    if ((pool = JobExecutor._getForkJoinPool()) != null) {
      tasks = new ForkJoinTask[i];
      for (; (--i) >= 0;) {
        tasks[i] = pool.submit(jobs[i], result);
      }
      return tasks;
    }

    // not in a fork-join pool: execute as is
    return JobExecutor._executeImmediately(result, jobs);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final <T> Future<T>[] execute(final Callable<T>... jobs) {
    final ForkJoinTask<T>[] tasks;
    final ForkJoinPool pool;
    int i;

    i = jobs.length;
    if (i <= 0) {// no tasks, we can quit directly
      return new Future[0];
    }

    if ((pool = JobExecutor._getForkJoinPool()) != null) {
      tasks = new ForkJoinTask[i];
      for (; (--i) >= 0;) {
        tasks[i] = pool.submit(jobs[i]);
      }
      return tasks;
    }

    // not in a fork-join pool: execute as is
    return JobExecutor._executeImmediately(jobs);
  }

  /** {@inheritDoc} */
  @Override
  public final <T> void execute(final Collection<Future<? super T>> dest,
      final T result, final Runnable... jobs) {
    final ForkJoinPool pool;
    int i;

    i = jobs.length;
    if (i > 0) {

      if ((pool = JobExecutor._getForkJoinPool()) != null) {
        for (; (--i) >= 0;) {
          dest.add(pool.submit(jobs[i], result));
        }
      }

      // not in a fork-join pool: execute as is
      JobExecutor._executeImmediately(dest, result, jobs);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final <T> void execute(final Collection<Future<? super T>> dest,
      final Callable<T>... jobs) {
    final ForkJoinPool pool;
    int i;

    i = jobs.length;
    if (i > 0) {

      if ((pool = JobExecutor._getForkJoinPool()) != null) {
        for (; (--i) >= 0;) {
          dest.add(pool.submit(jobs[i]));
        }
      }

      // not in a fork-join pool: execute as is
      JobExecutor._executeImmediately(dest, jobs);
    }
  }
}
