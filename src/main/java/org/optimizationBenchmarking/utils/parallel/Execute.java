package org.optimizationBenchmarking.utils.parallel;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.optimizationBenchmarking.utils.collections.iterators.ArrayIterator;
import org.optimizationBenchmarking.utils.error.ErrorUtils;

/**
 * This class is a bridge from "normal" Java to code running in an
 * {@link java.util.concurrent.ExecutorService}. It allows you to execute
 * jobs (i.e., either {@link java.lang.Runnable}s or
 * {@link java.util.concurrent.Callable}s) in the current thread or
 * executor service according to some specific policy. The executor service
 * may be automatically detected and used if the current thread is inside
 * an executor service. In other words, this allows for transparent use of
 * parallelism. Currently, only {@link java.util.concurrent.ForkJoinPool}s
 * can be detected.
 */
public final class Execute {

  /** the forbidden constructor */
  private Execute() {
    ErrorUtils.doNotCall();
  }

  /**
   * Submit a {@link java.lang.Runnable} to the common
   * {@link java.util.concurrent.ForkJoinPool}.
   *
   * @param task
   *          the task
   * @param result
   *          the result
   * @return the {@link java.util.concurrent.Future} denoting the task's
   *         completion
   */
  public static final <T> Future<T> submitToCommonPool(final Runnable task,
      final T result) {
    return __CommonForkJoinPool.COMMON_POOL.submit(task, result);
  }

  /**
   * Submit a {@link java.util.concurrent.Callable} to the common
   * {@link java.util.concurrent.ForkJoinPool}.
   *
   * @param task
   *          the task
   * @return the {@link java.util.concurrent.Future} denoting the task's
   *         completion
   */
  public static final <T> Future<T> submitToCommonPool(
      final Callable<T> task) {
    return __CommonForkJoinPool.COMMON_POOL.submit(task);
  }

  /**
   * Submit a {@link java.util.concurrent.ForkJoinTask} to the common
   * {@link java.util.concurrent.ForkJoinPool}.
   *
   * @param task
   *          the task
   * @return the {@link java.util.concurrent.Future} denoting the task's
   *         completion
   */
  public static final <T> Future<T> submitToCommonPool(
      final ForkJoinTask<T> task) {
    return __CommonForkJoinPool.COMMON_POOL.submit(task);
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
   *          the destination array to receive the results, or {@code null}
   *          to ignore all results
   * @param start
   *          the start index in the destination array
   * @param ignoreNullResults
   *          should {@code null} results returned by the
   *          {@linkplain java.util.concurrent.Future#get() futures} be
   *          ignored, i.e., not stored in {@code destination}?
   * @return the index of the next element in {@code destination} right
   *         after the last copied result item (or {@code start} if
   *         {@code destination==null})
   * @param <X>
   *          the data type of the destination array's elements
   * @param <Y>
   *          the result type of the future tasks
   */
  public static final <X, Y extends X> int join(final Future<Y>[] tasks,
      final X[] destination, final int start,
      final boolean ignoreNullResults) {
    return Execute.join(new ArrayIterator<>(tasks), destination, start,
        ignoreNullResults);
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
   *          the destination array to receive the results, or {@code null}
   *          to ignore all results
   * @param start
   *          the start index in the destination array
   * @param ignoreNullResults
   *          should {@code null} results returned by the
   *          {@linkplain java.util.concurrent.Future#get() futures} be
   *          ignored, i.e., not stored in {@code destination}?
   * @return the index of the next element in {@code destination} right
   *         after the last copied result item (or {@code start} if
   *         {@code destination==null})
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
        if (destination != null) {
          destination[index++] = result;
        }
      }
    }

    return index;
  }

  /**
   * Wait for a set of {@code tasks} to complete while ignoring their
   * results.
   *
   * @param tasks
   *          the tasks to wait for
   * @param <Y>
   *          the result type of the future tasks
   */
  public static final <Y> void join(final Iterable<Future<Y>> tasks) {
    Execute.join(tasks, null, 0, true);
  }

  /**
   * Wait for a set of {@code tasks} to complete while ignoring their
   * results.
   *
   * @param tasks
   *          the tasks to wait for
   * @param <Y>
   *          the result type of the future tasks
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final <Y> void join(final Future... tasks) {
    Execute.join(new ArrayIterator(tasks));
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
      return Execute.__createImmediateFuture(result);
    } catch (final Throwable error) {
      return new __ExceptionFuture<>(error);
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
      return Execute.__createImmediateFuture(job.call());
    } catch (final Throwable error) {
      return new __ExceptionFuture<>(error);
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
    if (ForkJoinTask.inForkJoinPool()) {
      return ForkJoinTask.adapt(job, result).fork();
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
    if (ForkJoinTask.inForkJoinPool()) {
      return ForkJoinTask.adapt(job).fork();
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
    int i;

    i = jobs.length;
    if (i <= 0) {// no tasks, we can quit directly
      return new Future[0];
    }

    if (ForkJoinTask.inForkJoinPool()) {
      tasks = new ForkJoinTask[i];
      for (; (--i) >= 0;) {
        tasks[i] = ForkJoinTask.adapt(jobs[i], result).fork();
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
    int i;

    i = jobs.length;
    if (i <= 0) {// no tasks, we can quit directly
      return new Future[0];
    }

    if (ForkJoinTask.inForkJoinPool()) {
      tasks = new ForkJoinTask[i];
      for (; (--i) >= 0;) {
        tasks[i] = ForkJoinTask.adapt(jobs[i]).fork();
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
    int i;

    i = jobs.length;
    if (i > 0) {

      if (ForkJoinTask.inForkJoinPool()) {
        for (; (--i) >= 0;) {
          dest.add(ForkJoinTask.adapt(jobs[i], result).fork());
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
    int i;

    i = jobs.length;
    if (i > 0) {

      if (ForkJoinTask.inForkJoinPool()) {
        for (; (--i) >= 0;) {
          dest.add(ForkJoinTask.adapt(jobs[i]).fork());
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
          success = Execute.__createImmediateFuture(result);
        }
        results[i] = success;
      } catch (final Throwable error) {
        results[i] = new __ExceptionFuture<>(error);
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
        results[i] = Execute.__createImmediateFuture(jobs[i].call());
      } catch (final Throwable error) {
        results[i] = new __ExceptionFuture<>(error);
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
          success = Execute.__createImmediateFuture(result);
        }
        dest.add(success);
      } catch (final Throwable error) {
        dest.add(new __ExceptionFuture<>(error));
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
        dest.add(Execute.__createImmediateFuture(jobs[i].call()));
      } catch (final Throwable error) {
        dest.add(new __ExceptionFuture<>(error));
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
        if (ForkJoinTask.inForkJoinPool()) {

          tasks = new ForkJoinTask[i];
          for (; (--i) >= 0;) {
            tasks[i] = ForkJoinTask.adapt(jobs[i], result);
          }
          ForkJoinTask.invokeAll(tasks);
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
        if (ForkJoinTask.inForkJoinPool()) {

          tasks = new ForkJoinTask[i];
          for (; (--i) >= 0;) {
            tasks[i] = ForkJoinTask.adapt(jobs[i]);
          }

          ForkJoinTask.invokeAll(tasks);
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
        if (ForkJoinTask.inForkJoinPool()) {

          tasks = new ForkJoinTask[i];
          for (; (--i) >= 0;) {
            dest.add(tasks[i] = ForkJoinTask.adapt(jobs[i], result));
          }

          ForkJoinTask.invokeAll(tasks);
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
        if (ForkJoinTask.inForkJoinPool()) {

          tasks = new ForkJoinTask[i];
          for (; (--i) >= 0;) {
            dest.add(tasks[i] = ForkJoinTask.adapt(jobs[i]));
          }

          ForkJoinTask.invokeAll(tasks);
          return;
        }

        // not in a fork-join pool: execute as is
        Execute.__executeImmediately(dest, jobs);
      }
    }
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
  static final <T> Future<T> __createImmediateFuture(final T result) {
    return ((result == null) ? __ImmediateFuture.NULL_FUTURE
        : new __ImmediateFuture<>(result));
  }

  /**
   * A future which represents an error in execution.
   *
   * @param <T>
   *          the result type
   */
  private static final class __ExceptionFuture<T> implements Future<T> {

    /** the encountered error */
    private final Throwable m_error;

    /**
     * create the future
     *
     * @param error
     *          the encountered error
     */
    __ExceptionFuture(final Throwable error) {
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

  /**
   * A future which represents an immediate, successful execution.
   *
   * @param <T>
   *          the result type
   */
  private static final class __ImmediateFuture<T> implements Future<T> {

    /** a shared future with {@code null} result */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    static final __ImmediateFuture NULL_FUTURE = new __ImmediateFuture(
        null);

    /** the result */
    private final T m_result;

    /**
     * create the future
     *
     * @param result
     *          the result
     */
    __ImmediateFuture(final T result) {
      super();
      this.m_result = result;
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

  /** a class holding the common fork-join pool */
  private static final class __CommonForkJoinPool {

    /** the common fork-join pool */
    static final ForkJoinPool COMMON_POOL = __CommonForkJoinPool
        .__getCommonPool();

    /**
     * get the common fork-join pool
     *
     * @return the common pool
     */
    private static final ForkJoinPool __getCommonPool() {
      ForkJoinPool pool;

      try {
        pool = ((ForkJoinPool) (ForkJoinPool.class.getMethod("commonPool") //$NON-NLS-1$
            .invoke(null)));
        if (pool != null) {
          return pool;
        }
      } catch (@SuppressWarnings("unused") final Throwable error) {
        // can be ignored
      }

      return new ForkJoinPool();
    }

  }
}
