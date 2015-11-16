package org.optimizationBenchmarking.utils.parallel;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;

/**
 * A job executor is a bridge from "normal" Java to code running in an
 * {@link java.util.concurrent.ExecutorService}. It allows you to execute
 * jobs (i.e., either {@link java.lang.Runnable}s or
 * {@link java.util.concurrent.Callable}s) in the current thread or
 * executor service according to some specific policy. The executor service
 * is automatically detected and used if the current thread is inside an
 * executor service. In other words, this allows for transparent use of
 * parallelism. (Currently, only {@link java.util.concurrent.ForkJoinPool}s
 * can be detected.)
 */
public abstract class JobExecutor {

  /** create */
  JobExecutor() {
    super();
  }

  /**
   * Execute a {@link java.lang.Runnable}.
   *
   * @param job
   *          the job to run
   * @param result
   *          the result to be returned by the
   *          {@link java.util.concurrent.Future} representing the job
   * @return {@link java.util.concurrent.Future} representing the job in
   *         execution
   * @param <T>
   *          the data type of the result
   */
  public abstract <T> Future<T> execute(final T result,
      final Runnable job);

  /**
   * Execute a {@link java.util.concurrent.Callable}.
   *
   * @param job
   *          the job to run
   * @return {@link java.util.concurrent.Future} representing the job in
   *         execution
   * @param <T>
   *          the data type of the result
   */
  public abstract <T> Future<T> execute(final Callable<T> job);

  /**
   * Execute a set of {@link java.lang.Runnable}s.
   *
   * @param jobs
   *          the jobs to run
   * @param result
   *          the result to be returned by the
   *          {@link java.util.concurrent.Future}s representing the jobs
   * @return {@link java.util.concurrent.Future} representing the jobs in
   *         execution
   * @param <T>
   *          the data type of the result
   */
  public abstract <T> Future<T>[] execute(final T result,
      final Runnable... jobs);

  /**
   * Execute a {@link java.util.concurrent.Callable}s.
   *
   * @param jobs
   *          the jobs to run
   * @return {@link java.util.concurrent.Future} representing the job in
   *         execution
   * @param <T>
   *          the data type of the result
   */
  @SuppressWarnings("unchecked")
  public abstract <T> Future<T>[] execute(final Callable<T>... jobs);

  /**
   * Execute a set of {@link java.lang.Runnable}s and append the
   * corresponding {@link java.util.concurrent.Future}s to a
   * {@java.util.Collection collection}.
   *
   * @param dest
   *          the collection to receive the
   *          {@link java.util.concurrent.Future}s
   * @param jobs
   *          the jobs to run
   * @param result
   *          the result to be returned by the
   *          {@link java.util.concurrent.Future}s representing the jobs
   * @param <T>
   *          the data type of the result
   */
  public abstract <T> void execute(Collection<Future<? super T>> dest,
      final T result, final Runnable... jobs);

  /**
   * Execute a {@link java.util.concurrent.Callable}s and append the
   * corresponding {@link java.util.concurrent.Future}s to a
   * {@java.util.Collection collection}
   *
   * @param dest
   *          the collection to receive the
   *          {@link java.util.concurrent.Future}s
   * @param jobs
   *          the jobs to run
   * @param <T>
   *          the data type of the result
   */
  @SuppressWarnings("unchecked")
  public abstract <T> void execute(Collection<Future<? super T>> dest,
      final Callable<T>... jobs);

  /**
   * <p>
   * Obtain the job executor which executes jobs in a sequential fashion,
   * but potentially in parallel to the current thread. In other words, if
   * multiple jobs are submitted to {@link #execute(Callable...)} or
   * {@link #execute(Object, Runnable...)}, these jobs will be executed in
   * the order in which they were submitted. The job at index {@code i+1}
   * will not begin before the job at index {@code i} has finished.
   * However, the jobs may run in parallel to the current thread and the
   * {@code execute} methods all may return before the jobs have completed.
   * If jobs are submitted via multiple, different calls to the
   * {@code execute} methods, no guarantee is provided about their
   * execution order.
   * </p>
   * <p>
   * It should be noted that no guarantee is provided that the jobs are
   * actually executed in parallel: This may happen only if we currently
   * are in an {@link java.util.concurrent.ForkJoinPool} or have access to
   * such a pool. Otherwise, the jobs may or may not just be executed in
   * sequence.
   * </p>
   *
   * @return the job executor which executes jobs in the sequence but
   *         potentially in parallel to the current thread.
   */
  public static final JobExecutor sequential() {
    return _ExecuteSequentially.INSTANCE;
  }

  /**
   * Obtain the job executor which executes jobs in a sequential fashion
   * and waits until they have finished. In other words, this executor may
   * as well run them all one-by-one in the current thread (and it actually
   * does exactly this).
   *
   * @return the job executor which executes jobs in the sequence and waits
   *         until all of them are done
   */
  public static final JobExecutor sequentialAndWait() {
    return _ExecuteSequentiallyAndWait.INSTANCE;
  }

  /**
   * <p>
   * Obtain the job executor which executes jobs in a parallel fashion. In
   * other words, if multiple jobs are submitted to
   * {@link #execute(Callable...)} or {@link #execute(Object, Runnable...)}
   * , these jobs may be executed in any order. The job at index
   * {@code i+1} may begin and even finish before the job at index
   * {@code i} has been executed or finished. The jobs may run in parallel
   * to the current thread and the {@code execute} methods all may return
   * before the jobs have completed.
   * </p>
   * <p>
   * It should be noted that no guarantee is provided that the jobs are
   * actually executed in parallel: This may happen only if we currently
   * are in an {@link java.util.concurrent.ForkJoinPool} or have access to
   * such a pool. Otherwise, the jobs may or may not just be executed in
   * sequence.
   * </p>
   *
   * @return the job executor which executes jobs in the maximally parallel
   *         fashion.
   */
  public static final JobExecutor parallel() {
    return _ExecuteInParallel.INSTANCE;
  }

  /**
   * <p>
   * Obtain the job executor which executes jobs in a parallel fashion and
   * waits until they are completed. In other words, if multiple jobs are
   * submitted to {@link #execute(Callable...)} or
   * {@link #execute(Object, Runnable...)} , these jobs may be executed in
   * any order in parallel. The job at index {@code i+1} may begin and even
   * finish before the job at index {@code i} has been executed or
   * finished. The jobs may run in parallel to the current thread but the
   * {@code execute} methods will not return until all jobs supplied to
   * them have finished, i.e., have been completed (or failed).
   * </p>
   * <p>
   * It should be noted that no guarantee is provided that the jobs are
   * actually executed in parallel: This may happen only if we currently
   * are in an {@link java.util.concurrent.ForkJoinPool} or have access to
   * such a pool. Otherwise, the jobs may or may not just be executed in
   * sequence.
   * </p>
   *
   * @return the job executor which executes jobs in parallel but waits
   *         until they are done.
   */
  public static final JobExecutor parallelAndWait() {
    return _ExecuteInParallelAndWait.INSTANCE;
  }

  /**
   * Execute a {@link java.lang.Runnable} immediately and wait for it to
   * terminate.
   *
   * @param job
   *          the job to run
   * @param result
   *          the result to be returned by the
   *          {@link java.util.concurrent.Future} representing the job
   * @return {@link java.util.concurrent.Future} representing the job in
   *         execution
   * @param <T>
   *          the data type of the result
   */
  static final <T> Future<T> _executeImmediately(final T result,
      final Runnable job) {
    try {
      job.run();
      return _ImmediateFuture._create(result);
    } catch (final Throwable error) {
      return new _ExceptionFuture<>(error);
    }
  }

  /**
   * Execute a {@link java.util.concurrent.Callable} immediately and wait
   * for it to terminate.
   *
   * @param job
   *          the job to run
   * @return {@link java.util.concurrent.Future} representing the job in
   *         execution
   * @param <T>
   *          the data type of the result
   */
  static final <T> Future<T> _executeImmediately(final Callable<T> job) {
    try {
      return _ImmediateFuture._create(job.call());
    } catch (final Throwable error) {
      return new _ExceptionFuture<>(error);
    }
  }

  /**
   * Execute a {@link java.lang.Runnable} in parallel without waiting for
   * its termination.
   *
   * @param job
   *          the job to run
   * @param result
   *          the result to be returned by the
   *          {@link java.util.concurrent.Future} representing the job
   * @return {@link java.util.concurrent.Future} representing the job in
   *         execution
   * @param <T>
   *          the data type of the result
   */
  static final <T> Future<T> _executeInParallel(final T result,
      final Runnable job) {
    final ForkJoinPool pool;

    if ((pool = JobExecutor._getForkJoinPool()) != null) {
      return pool.submit(job, result);
    }

    return JobExecutor._executeImmediately(result, job);
  }

  /**
   * Execute a {@link java.util.concurrent.Callable} in parallel without
   * waiting for its termination.
   *
   * @param job
   *          the job to run
   * @return {@link java.util.concurrent.Future} representing the job in
   *         execution
   * @param <T>
   *          the data type of the result
   */
  static final <T> Future<T> _executeInParallel(final Callable<T> job) {
    final ForkJoinPool pool;

    if ((pool = JobExecutor._getForkJoinPool()) != null) {
      return pool.submit(job);
    }

    return JobExecutor._executeImmediately(job);
  }

  /**
   * Execute a set of {@link java.lang.Runnable}s.
   *
   * @param jobs
   *          the jobs to run
   * @param result
   *          the result to be returned by the
   *          {@link java.util.concurrent.Future}s representing the jobs
   * @return {@link java.util.concurrent.Future} representing the jobs in
   *         execution
   * @param <T>
   *          the data type of the result
   */
  @SuppressWarnings("unchecked")
  static final <T> Future<T>[] _executeImmediately(final T result,
      final Runnable... jobs) {
    final Future<T>[] results;
    final int length;
    Future<T> success;
    int i;

    length = jobs.length;
    results = new Future[length];

    success = null;
    for (i = 0; i < length; ++i) {
      try {
        jobs[i].run();
        if (success == null) {
          success = _ImmediateFuture._create(result);
        }
        results[i] = success;
      } catch (final Throwable error) {
        results[i] = new _ExceptionFuture<>(error);
      }
    }
    return results;
  }

  /**
   * Execute a {@link java.util.concurrent.Callable}s.
   *
   * @param jobs
   *          the jobs to run
   * @return {@link java.util.concurrent.Future} representing the job in
   *         execution
   * @param <T>
   *          the data type of the result
   */
  @SuppressWarnings("unchecked")
  static final <T> Future<T>[] _executeImmediately(
      final Callable<T>... jobs) {
    final Future<T>[] results;
    final int length;
    int i;

    length = jobs.length;
    results = new Future[length];

    for (i = 0; i < length; ++i) {
      try {
        results[i] = _ImmediateFuture._create(jobs[i].call());
      } catch (final Throwable error) {
        results[i] = new _ExceptionFuture<>(error);
      }
    }
    return results;
  }

  /**
   * Execute a set of {@link java.lang.Runnable}s and append the
   * corresponding {@link java.util.concurrent.Future}s to a
   * {@java.util.Collection collection}.
   *
   * @param dest
   *          the collection to receive the
   *          {@link java.util.concurrent.Future}s
   * @param jobs
   *          the jobs to run
   * @param result
   *          the result to be returned by the
   *          {@link java.util.concurrent.Future}s representing the jobs
   * @param <T>
   *          the data type of the result
   */
  static final <T> void _executeImmediately(
      final Collection<Future<? super T>> dest, final T result,
      final Runnable... jobs) {
    final int length;
    Future<T> success;
    int i;

    length = jobs.length;

    success = null;
    for (i = 0; i < length; ++i) {
      try {
        jobs[i].run();
        if (success == null) {
          success = _ImmediateFuture._create(result);
        }
        dest.add(success);
      } catch (final Throwable error) {
        dest.add(new _ExceptionFuture<>(error));
      }
    }
  }

  /**
   * Execute a {@link java.util.concurrent.Callable}s and append the
   * corresponding {@link java.util.concurrent.Future}s to a
   * {@java.util.Collection collection}.
   *
   * @param dest
   *          the collection to receive the
   *          {@link java.util.concurrent.Future}s
   * @param jobs
   *          the jobs to run
   * @param <T>
   *          the data type of the result
   */
  @SuppressWarnings("unchecked")
  static final <T> void _executeImmediately(
      final Collection<Future<? super T>> dest,
      final Callable<T>... jobs) {
    final int length;
    int i;

    length = jobs.length;

    for (i = 0; i < length; ++i) {
      try {
        dest.add(_ImmediateFuture._create(jobs[i].call()));
      } catch (final Throwable error) {
        dest.add(new _ExceptionFuture<>(error));
      }
    }
  }

  /**
   * Obtain the globally shared, common
   * {@link java.util.concurrent.ForkJoinPool}. Starting in Java 1.8, such
   * a pool exists. If we are under Java 1.8 and such a pool exists, this
   * method will return it. If we are under Java 1.7 and/or no such pool
   * exists, this method returns {@code null}.
   *
   * @return the globally shared, common
   *         {@link java.util.concurrent.ForkJoinPool}, or {@code null} if
   *         no such pool exists
   */
  static final ForkJoinPool _getForkJoinPool() {
    final ForkJoinPool pool;

    pool = ForkJoinTask.getPool();
    if (pool != null) {
      return pool;
    }
    return __CommonForkJoinPool.COMMON_FORK_JOIN_POOL;
  }

  /**
   * The internal class for obtaining the globally shared
   * {@link java.util.concurrent.ForkJoinPool} instance
   */
  private static final class __CommonForkJoinPool {

    /**
     * the common shared {@link java.util.concurrent.ForkJoinPool} instance
     */
    static final ForkJoinPool COMMON_FORK_JOIN_POOL = __CommonForkJoinPool
        .__getCommonPool();

    /**
     * this method tries to load the global, common
     * {@link java.util.concurrent.ForkJoinPool}
     *
     * @return the global, common {@link java.util.concurrent.ForkJoinPool}
     *         , or {@code null} if we are not under {@code Java 1.8} and
     *         thus cannot detect it.
     */
    @SuppressWarnings("unused")
    private static ForkJoinPool __getCommonPool() {
      try {
        return ((ForkJoinPool) (ForkJoinPool.class.getMethod(//
            "commonPool").invoke(null))); //$NON-NLS-1$
      } catch (final Throwable error) {
        return null;
      }
    }

  }
}
