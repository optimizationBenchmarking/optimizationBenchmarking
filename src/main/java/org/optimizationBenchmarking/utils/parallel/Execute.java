package org.optimizationBenchmarking.utils.parallel;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;

import org.optimizationBenchmarking.utils.error.ErrorUtils;

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
public final class Execute {

  /** the forbidden constructor */
  private Execute() {
    ErrorUtils.doNotCall();
  }

  /**
   * Wait for a set of {@code tasks} to complete and store their results
   * into the {@code destination} array starting at index {@code start}.
   * {@code null} futures are ignored. The tasks array is pruned in the
   * process, i.e., filled with {@code null}. If any of the tasks fails
   * with an exception, the remaining tasks will be ignored (and also not
   * be set to {@code null}).
   *
   * @param tasks
   *          the tasks to wait for
   * @param destination
   *          the destination array to receive the results
   * @param start
   *          the start index in the destination array
   * @param ignoreNullResults
   *          should {@code null} results returned by the
   *          {@linkplain java.util.concurrent.Future#get() futures} be
   *          ignored, i.e., not stored in {@code destination}?
   * @return the index of the next element in {@code destination} right
   *         after the last copied result item
   * @param <X>
   *          the data type of the destination array's elements
   * @param <Y>
   *          the result type of the future tasks
   */
  public static final <X, Y extends X> int join(final Future<Y>[] tasks,
      final X[] destination, final int start,
      final boolean ignoreNullResults) {
    Throwable cause;
    Future<Y> future;
    Y result;
    int index, futureIndex;

    index = start;
    futureIndex = 0;
    for (futureIndex = 0; futureIndex < tasks.length; futureIndex++) {
      future = tasks[futureIndex];
      if (future != null) {
        tasks[futureIndex] = null;
        try {
          result = future.get();
        } catch (final ExecutionException executionError) {
          cause = executionError.getCause();
          if (cause instanceof RuntimeException) {
            throw ((RuntimeException) cause);
          }
          throw new RuntimeException("The execution of a task failed.", //$NON-NLS-1$
              executionError);
        } catch (final InterruptedException interrupted) {
          throw new RuntimeException(
              "A task was interrupted before completing.", //$NON-NLS-1$
              interrupted);
        }
        if ((result == null) && ignoreNullResults) {
          continue;
        }
        destination[index++] = result;
      }
    }

    return index;
  }

  /**
   * Wait for a set of {@code tasks} to complete and store their results
   * into the {@code destination} array starting at index {@code start}.
   * {@code null} futures are ignored. If any of the tasks fails with an
   * exception, the remaining tasks will be ignored.
   *
   * @param tasks
   *          the tasks to wait for
   * @param destination
   *          the destination array to receive the results
   * @param start
   *          the start index in the destination array
   * @param ignoreNullResults
   *          should {@code null} results returned by the
   *          {@linkplain java.util.concurrent.Future#get() futures} be
   *          ignored, i.e., not stored in {@code destination}?
   * @return the index of the next element in {@code destination} right
   *         after the last copied result item
   * @param <X>
   *          the data type of the destination array's elements
   * @param <Y>
   *          the result type of the future tasks
   */
  public static final <X, Y extends X> int join(
      final Iterable<Future<Y>> tasks, final X[] destination,
      final int start, final boolean ignoreNullResults) {
    Throwable cause;
    Y result;
    int index;

    index = start;
    for (final Future<Y> future : tasks) {
      if (future != null) {
        try {
          result = future.get();
        } catch (final ExecutionException executionError) {
          cause = executionError.getCause();
          if (cause instanceof RuntimeException) {
            throw ((RuntimeException) cause);
          }
          throw new RuntimeException("The execution of a task failed.", //$NON-NLS-1$
              executionError);
        } catch (final InterruptedException interrupted) {
          throw new RuntimeException(
              "A task was interrupted before completing.", //$NON-NLS-1$
              interrupted);
        }
        if ((result == null) && ignoreNullResults) {
          continue;
        }
        destination[index++] = result;
      }
    }

    return index;
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
  private static final <T> Future<T> __executeImmediately(final T result,
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
  private static final <T> Future<T> __executeImmediately(
      final Callable<T> job) {
    try {
      return _ImmediateFuture._create(job.call());
    } catch (final Throwable error) {
      return new _ExceptionFuture<>(error);
    }
  }

  /**
   * Try to execute a {@link java.lang.Runnable} in parallel without
   * waiting for its termination. This method does not guarantee that the
   * task will actually be executed in parallel. If parallel execution is
   * not possible, because, e.g., no
   * {@link java.util.concurrent.ForkJoinPool} could be detected, the task
   * is directly executed.
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
  public static final <T> Future<T> parallel(final T result,
      final Runnable job) {
    final ForkJoinPool pool;

    if ((pool = Execute.__getForkJoinPool()) != null) {
      return pool.submit(job, result);
    }

    return Execute.__executeImmediately(result, job);
  }

  /**
   * Try to execute a {@link java.util.concurrent.Callable} in parallel
   * without waiting for its termination. This method does not guarantee
   * that the task will actually be executed in parallel. If parallel
   * execution is not possible, because, e.g., no
   * {@link java.util.concurrent.ForkJoinPool} could be detected, the task
   * is directly executed.
   *
   * @param job
   *          the job to run
   * @return {@link java.util.concurrent.Future} representing the job in
   *         execution
   * @param <T>
   *          the data type of the result
   */
  public static final <T> Future<T> parallel(final Callable<T> job) {
    final ForkJoinPool pool;

    if ((pool = Execute.__getForkJoinPool()) != null) {
      return pool.submit(job);
    }

    return Execute.__executeImmediately(job);
  }

  /**
   * Execute a set of {@link java.lang.Runnable}s in parallel. No guarantee
   * about the execution order is given. This method does not guarantee
   * that the task will actually be executed in parallel. If parallel
   * execution is not possible, because, e.g., no
   * {@link java.util.concurrent.ForkJoinPool} could be detected, the task
   * is directly executed.
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
  public static final <T> Future<T>[] parallel(final T result,
      final Runnable... jobs) {
    final ForkJoinTask<T>[] tasks;
    final ForkJoinPool pool;
    int i;

    i = jobs.length;
    if (i <= 0) {// no tasks, we can quit directly
      return new Future[0];
    }

    if ((pool = Execute.__getForkJoinPool()) != null) {
      tasks = new ForkJoinTask[i];
      for (; (--i) >= 0;) {
        tasks[i] = pool.submit(jobs[i], result);
      }
      return tasks;
    }

    // not in a fork-join pool: execute as is
    return Execute.__executeImmediately(result, jobs);
  }

  /**
   * Execute a {@link java.util.concurrent.Callable}s in parallel. No
   * guarantee about the execution order is given. This method does not
   * guarantee that the task will actually be executed in parallel. If
   * parallel execution is not possible, because, e.g., no
   * {@link java.util.concurrent.ForkJoinPool} could be detected, the task
   * is directly executed.
   *
   * @param jobs
   *          the jobs to run
   * @return {@link java.util.concurrent.Future} representing the job in
   *         execution
   * @param <T>
   *          the data type of the result
   */
  @SuppressWarnings("unchecked")
  public static final <T> Future<T>[] parallel(final Callable<T>... jobs) {
    final ForkJoinTask<T>[] tasks;
    final ForkJoinPool pool;
    int i;

    i = jobs.length;
    if (i <= 0) {// no tasks, we can quit directly
      return new Future[0];
    }

    if ((pool = Execute.__getForkJoinPool()) != null) {
      tasks = new ForkJoinTask[i];
      for (; (--i) >= 0;) {
        tasks[i] = pool.submit(jobs[i]);
      }
      return tasks;
    }

    // not in a fork-join pool: execute as is
    return Execute.__executeImmediately(jobs);
  }

  /**
   * Execute a set of {@link java.lang.Runnable}s in parallel and append
   * the corresponding {@link java.util.concurrent.Future}s to a
   * {@java.util.Collection collection}. No guarantee about the execution
   * order is given. This method does not guarantee that the task will
   * actually be executed in parallel. If parallel execution is not
   * possible, because, e.g., no {@link java.util.concurrent.ForkJoinPool}
   * could be detected, the task is directly executed.
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
  public static final <T> void parallel(
      final Collection<Future<? super T>> dest, final T result,
      final Runnable... jobs) {
    final ForkJoinPool pool;
    int i;

    i = jobs.length;
    if (i > 0) {

      if ((pool = Execute.__getForkJoinPool()) != null) {
        for (; (--i) >= 0;) {
          dest.add(pool.submit(jobs[i], result));
        }
      }

      // not in a fork-join pool: execute as is
      Execute.__executeImmediately(dest, result, jobs);
    }
  }

  /**
   * Execute a {@link java.util.concurrent.Callable}s in parallel and
   * append the corresponding {@link java.util.concurrent.Future}s to a
   * {@java.util.Collection collection}. No guarantee about the execution
   * order is given. This method does not guarantee that the task will
   * actually be executed in parallel. If parallel execution is not
   * possible, because, e.g., no {@link java.util.concurrent.ForkJoinPool}
   * could be detected, the task is directly executed.
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
  public static final <T> void parallel(
      final Collection<Future<? super T>> dest,
      final Callable<T>... jobs) {
    final ForkJoinPool pool;
    int i;

    i = jobs.length;
    if (i > 0) {

      if ((pool = Execute.__getForkJoinPool()) != null) {
        for (; (--i) >= 0;) {
          dest.add(pool.submit(jobs[i]));
        }
      }

      // not in a fork-join pool: execute as is
      Execute.__executeImmediately(dest, jobs);
    }
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
  private static final <T> Future<T>[] __executeImmediately(final T result,
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
  private static final <T> Future<T>[] __executeImmediately(
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
  private static final <T> void __executeImmediately(
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
  private static final <T> void __executeImmediately(
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
   * Execute a set of {@link java.lang.Runnable}s in parallel and wait
   * until all of them have terminated. No guarantee about the execution
   * order is given. This method does not guarantee that the task will
   * actually be executed in parallel. If parallel execution is not
   * possible, because, e.g., no {@link java.util.concurrent.ForkJoinPool}
   * could be detected, the task is directly executed.
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
  public static final <T> Future<T>[] parallelAndWait(final T result,
      final Runnable... jobs) {
    final ForkJoinTask<T>[] tasks;
    final ForkJoinPool pool;
    int i;

    i = jobs.length;
    switch (i) {

      case 0: {// no tasks, we can quit directly
        return new Future[0];
      }
      case 1: {// one task: execute directly
        return new Future[] { //
            Execute.__executeImmediately(result, jobs[0]) };
      }
      default: {// multiple tasks:let's see what to do
        if ((pool = Execute.__getForkJoinPool()) != null) {

          tasks = new ForkJoinTask[i];
          for (; (--i) >= 0;) {
            tasks[i] = ForkJoinTask.adapt(jobs[i], result);
          }

          Execute.__executeTasks(tasks, pool);
          return tasks;
        }

        // not in a fork-join pool: execute as is
        return Execute.__executeImmediately(result, jobs);
      }
    }
  }

  /**
   * Execute the tasks if we are inside a
   * {@link java.util.concurrent.ForkJoinPool}.
   *
   * @param tasks
   *          the tasks
   */
  static final void _executeTasksInPool(final ForkJoinTask<?>[] tasks) {
    if (tasks.length == 2) {
      ForkJoinTask.invokeAll(tasks[0], tasks[1]);
    } else {
      ForkJoinTask.invokeAll(tasks);
    }
  }

  /**
   * Execute the tasks if we are or are not inside a
   * {@link java.util.concurrent.ForkJoinPool}.
   *
   * @param tasks
   *          the tasks
   * @param pool
   *          the fork join pool
   */
  @SuppressWarnings("unused")
  private static final void __executeTasks(final ForkJoinTask<?>[] tasks,
      final ForkJoinPool pool) {
    if (ForkJoinTask.inForkJoinPool()) {
      Execute._executeTasksInPool(tasks);
    } else {
      try {
        pool.submit(new _ParallelTask(tasks)).get();
      } catch (ExecutionException | InterruptedException ignore) {
        // we ignore this one
      }
    }
  }

  /**
   * Execute a {@link java.util.concurrent.Callable}s in parallel and wait
   * until all of them have terminated. No guarantee about the execution
   * order is given. This method does not guarantee that the task will
   * actually be executed in parallel. If parallel execution is not
   * possible, because, e.g., no {@link java.util.concurrent.ForkJoinPool}
   * could be detected, the task is directly executed.
   *
   * @param jobs
   *          the jobs to run
   * @return {@link java.util.concurrent.Future} representing the job in
   *         execution
   * @param <T>
   *          the data type of the result
   */
  @SuppressWarnings("unchecked")
  public static final <T> Future<T>[] parallelAndWait(
      final Callable<T>... jobs) {
    final ForkJoinTask<T>[] tasks;
    final ForkJoinPool pool;
    int i;

    i = jobs.length;
    switch (i) {

      case 0: {// no tasks, we can quit directly
        return new Future[0];
      }
      case 1: {// one task: execute directly
        return new Future[] { //
            Execute.__executeImmediately(jobs[0]) };
      }
      default: {// multiple tasks:let's see what to do
        if ((pool = Execute.__getForkJoinPool()) != null) {

          tasks = new ForkJoinTask[i];
          for (; (--i) >= 0;) {
            tasks[i] = ForkJoinTask.adapt(jobs[i]);
          }

          Execute.__executeTasks(tasks, pool);
          return tasks;
        }

        // not in a fork-join pool: execute as is
        return Execute.__executeImmediately(jobs);
      }
    }
  }

  /**
   * Execute a set of {@link java.lang.Runnable}s in parallel and wait
   * until all of them have terminated. Append the corresponding
   * {@link java.util.concurrent.Future}s to a
   * {@java.util.Collection collection}. No guarantee about the execution
   * order is given. This method does not guarantee that the task will
   * actually be executed in parallel. If parallel execution is not
   * possible, because, e.g., no {@link java.util.concurrent.ForkJoinPool}
   * could be detected, the task is directly executed.
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
  @SuppressWarnings("unchecked")
  public static final <T> void parallelAndWait(
      final Collection<Future<? super T>> dest, final T result,
      final Runnable... jobs) {
    final ForkJoinTask<T>[] tasks;
    final ForkJoinPool pool;
    int i;

    i = jobs.length;
    switch (i) {
      case 0: {// no tasks, we can quit directly
        return;
      }
      case 1: {// one task: execute directly
        dest.add(Execute.__executeImmediately(result, jobs[0]));
        return;
      }
      default: {// multiple tasks:let's see what to do
        if ((pool = Execute.__getForkJoinPool()) != null) {

          tasks = new ForkJoinTask[i];
          for (; (--i) >= 0;) {
            dest.add(tasks[i] = ForkJoinTask.adapt(jobs[i], result));
          }

          Execute.__executeTasks(tasks, pool);
          return;
        }

        // not in a fork-join pool: execute as is
        Execute.__executeImmediately(dest, result, jobs);
      }
    }
  }

  /**
   * Execute a {@link java.util.concurrent.Callable}s in parallel and wait
   * until all of them have terminated. Append the corresponding
   * {@link java.util.concurrent.Future}s to a
   * {@java.util.Collection collection}. No guarantee about the execution
   * order is given. This method does not guarantee that the task will
   * actually be executed in parallel. If parallel execution is not
   * possible, because, e.g., no {@link java.util.concurrent.ForkJoinPool}
   * could be detected, the task is directly executed.
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
  public static final <T> void parallelAndWait(
      final Collection<Future<? super T>> dest,
      final Callable<T>... jobs) {
    final ForkJoinTask<T>[] tasks;
    final ForkJoinPool pool;
    int i;

    i = jobs.length;
    switch (i) {
      case 0: {// no tasks, we can quit directly
        return;
      }
      case 1: {// one task: execute directly
        dest.add(Execute.__executeImmediately(jobs[0]));
        return;
      }
      default: {// multiple tasks:let's see what to do
        if ((pool = Execute.__getForkJoinPool()) != null) {

          tasks = new ForkJoinTask[i];
          for (; (--i) >= 0;) {
            dest.add(tasks[i] = ForkJoinTask.adapt(jobs[i]));
          }

          Execute.__executeTasks(tasks, pool);
          return;
        }

        // not in a fork-join pool: execute as is
        Execute.__executeImmediately(dest, jobs);
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
  private static final ForkJoinPool __getForkJoinPool() {
    final ForkJoinPool pool;

    pool = ForkJoinTask.getPool();
    if (pool != null) {
      return pool;
    }
    return __CommonForkJoinPool.COMMON_FORK_JOIN_POOL;
  }

  /** a task for executing some other tasks in parallel */
  private static final class _ParallelTask extends RecursiveAction {

    /** the serial version uid */
    private static final long serialVersionUID = 1L;

    /** the tasks */
    private final ForkJoinTask<?>[] m_tasks;

    /**
     * create the parallel task
     *
     * @param tasks
     *          the tasks to execute
     */
    _ParallelTask(final ForkJoinTask<?>[] tasks) {
      super();
      this.m_tasks = tasks;
    }

    /** {@inheritDoc} */
    @Override
    protected final void compute() {
      Execute._executeTasksInPool(this.m_tasks);
    }
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
