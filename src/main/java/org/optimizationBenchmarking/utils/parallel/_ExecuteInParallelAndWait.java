package org.optimizationBenchmarking.utils.parallel;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;

/**
 * Execute jobs in parallel (if possible) and wait for their completion. If
 * the current thread is part of a
 * {@link java.util.concurrent.ForkJoinPool}, the submitted tasks will
 * actually be submitted to said pool. Otherwise, they will simple be
 * executed in a sequential fashion. The {@code execute} methods will
 * always wait until all tasks have completed (or failed).
 */
final class _ExecuteInParallelAndWait extends Execute {

  /** the globally shared instance */
  static final _ExecuteInParallelAndWait INSTANCE = new _ExecuteInParallelAndWait();

  /** create */
  private _ExecuteInParallelAndWait() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final <T> Future<T> execute(final T result, final Runnable job) {
    return Execute._executeImmediately(result, job);
  }

  /** {@inheritDoc} */
  @Override
  public final <T> Future<T> execute(final Callable<T> job) {
    return Execute._executeImmediately(job);
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
    switch (i) {

      case 0: {// no tasks, we can quit directly
        return new Future[0];
      }
      case 1: {// one task: execute directly
        return new Future[] { //
            Execute._executeImmediately(result, jobs[0]) };
      }
      default: {// multiple tasks:let's see what to do
        if ((pool = Execute._getForkJoinPool()) != null) {

          tasks = new ForkJoinTask[i];
          for (; (--i) >= 0;) {
            tasks[i] = ForkJoinTask.adapt(jobs[i], result);
          }

          _ExecuteInParallelAndWait._executeTasks(tasks, pool);
          return tasks;
        }

        // not in a fork-join pool: execute as is
        return Execute._executeImmediately(result, jobs);
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
  static final void _executeTasks(final ForkJoinTask<?>[] tasks,
      final ForkJoinPool pool) {
    if (ForkJoinTask.inForkJoinPool()) {
      _ExecuteInParallelAndWait._executeTasksInPool(tasks);
    } else {
      try {
        pool.submit(new _ParallelTask(tasks)).get();
      } catch (ExecutionException | InterruptedException ignore) {
        // we ignore this one
      }
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final <T> Future<T>[] execute(final Callable<T>... jobs) {
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
            Execute._executeImmediately(jobs[0]) };
      }
      default: {// multiple tasks:let's see what to do
        if ((pool = Execute._getForkJoinPool()) != null) {

          tasks = new ForkJoinTask[i];
          for (; (--i) >= 0;) {
            tasks[i] = ForkJoinTask.adapt(jobs[i]);
          }

          _ExecuteInParallelAndWait._executeTasks(tasks, pool);
          return tasks;
        }

        // not in a fork-join pool: execute as is
        return Execute._executeImmediately(jobs);
      }
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final <T> void execute(final Collection<Future<? super T>> dest,
      final T result, final Runnable... jobs) {
    final ForkJoinTask<T>[] tasks;
    final ForkJoinPool pool;
    int i;

    i = jobs.length;
    switch (i) {
      case 0: {// no tasks, we can quit directly
        return;
      }
      case 1: {// one task: execute directly
        dest.add(Execute._executeImmediately(result, jobs[0]));
        return;
      }
      default: {// multiple tasks:let's see what to do
        if ((pool = Execute._getForkJoinPool()) != null) {

          tasks = new ForkJoinTask[i];
          for (; (--i) >= 0;) {
            dest.add(tasks[i] = ForkJoinTask.adapt(jobs[i], result));
          }

          _ExecuteInParallelAndWait._executeTasks(tasks, pool);
          return;
        }

        // not in a fork-join pool: execute as is
        Execute._executeImmediately(dest, result, jobs);
      }
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final <T> void execute(final Collection<Future<? super T>> dest,
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
        dest.add(Execute._executeImmediately(jobs[0]));
        return;
      }
      default: {// multiple tasks:let's see what to do
        if ((pool = Execute._getForkJoinPool()) != null) {

          tasks = new ForkJoinTask[i];
          for (; (--i) >= 0;) {
            dest.add(tasks[i] = ForkJoinTask.adapt(jobs[i]));
          }

          _ExecuteInParallelAndWait._executeTasks(tasks, pool);
          return;
        }

        // not in a fork-join pool: execute as is
        Execute._executeImmediately(dest, jobs);
      }
    }
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
      _ExecuteInParallelAndWait._executeTasksInPool(this.m_tasks);
    }
  }
}
