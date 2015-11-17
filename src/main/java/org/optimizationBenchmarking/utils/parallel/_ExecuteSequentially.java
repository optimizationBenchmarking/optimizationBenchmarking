package org.optimizationBenchmarking.utils.parallel;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RecursiveAction;

/**
 * Execute jobs in sequence (if possible) and without waiting for their
 * completion. If the current thread is part of a
 * {@link java.util.concurrent.ForkJoinPool}, the submitted tasks will
 * actually be submitted to said pool. Otherwise, they will simple be
 * executed in a sequential fashion.
 */
final class _ExecuteSequentially extends Execute {

  /** the globally shared instance */
  static final _ExecuteSequentially INSTANCE = new _ExecuteSequentially();

  /** create */
  private _ExecuteSequentially() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final <T> Future<T> execute(final T result, final Runnable job) {
    return Execute._executeInParallel(result, job);
  }

  /** {@inheritDoc} */
  @Override
  public final <T> Future<T> execute(final Callable<T> job) {
    return Execute._executeInParallel(job);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final <T> Future<T>[] execute(final T result,
      final Runnable... jobs) {
    final FutureTask<T>[] tasks;
    final ForkJoinPool pool;
    final int length;
    int i;

    length = jobs.length;
    switch (length) {

      case 0: {// no tasks, we can quit directly
        return new Future[0];
      }
      case 1: {// one task: execute directly
        return new Future[] { //
            Execute._executeInParallel(result, jobs[0]) };
      }
      default: {// multiple tasks:let's see what to do
        if ((pool = Execute._getForkJoinPool()) != null) {

          tasks = new FutureTask[length];
          for (i = 0; i < length; ++i) {
            tasks[i] = new FutureTask<>(jobs[i], result);
          }

          pool.submit(new _SequenceTask(tasks.clone()));

          return tasks;
        }

        // not in a fork-join pool: execute as is
        return Execute._executeImmediately(result, jobs);
      }
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final <T> Future<T>[] execute(final Callable<T>... jobs) {
    final FutureTask<T>[] tasks;
    final ForkJoinPool pool;
    final int length;
    int i;

    length = jobs.length;
    switch (length) {

      case 0: {// no tasks, we can quit directly
        return new Future[0];
      }
      case 1: {// one task: execute directly
        return new Future[] { //
            Execute._executeInParallel(jobs[0]) };
      }
      default: {// multiple tasks:let's see what to do
        if ((pool = Execute._getForkJoinPool()) != null) {

          tasks = new FutureTask[length];
          for (i = 0; i < length; ++i) {
            tasks[i] = new FutureTask<>(jobs[i]);
          }

          pool.submit(new _SequenceTask(tasks.clone()));

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
    final FutureTask<T>[] tasks;
    final ForkJoinPool pool;
    final int length;
    int i;

    length = jobs.length;
    switch (length) {
      case 0: {// no tasks, we can quit directly
        return;
      }
      case 1: {// one task: execute directly
        dest.add(Execute._executeInParallel(result, jobs[0]));
        return;
      }
      default: {// multiple tasks:let's see what to do
        if ((pool = Execute._getForkJoinPool()) != null) {

          tasks = new FutureTask[length];
          for (i = 0; i < length; ++i) {
            dest.add(tasks[i] = new FutureTask<>(jobs[i], result));
          }

          pool.submit(new _SequenceTask(tasks.clone()));
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
    final FutureTask<T>[] tasks;
    final ForkJoinPool pool;
    final int length;
    int i;

    length = jobs.length;
    switch (length) {
      case 0: {// no tasks, we can quit directly
        return;
      }
      case 1: {// one task: execute directly
        dest.add(Execute._executeInParallel(jobs[0]));
        return;
      }
      default: {// multiple tasks:let's see what to do
        if ((pool = Execute._getForkJoinPool()) != null) {

          tasks = new FutureTask[length];
          for (i = 0; i < length; ++i) {
            dest.add(tasks[i] = new FutureTask<>(jobs[i]));
          }

          pool.submit(new _SequenceTask(tasks.clone()));
          return;
        }

        // not in a fork-join pool: execute as is
        Execute._executeImmediately(dest, jobs);
      }
    }
  }

  /** a task for executing some other tasks in sequence */
  private static final class _SequenceTask extends RecursiveAction {

    /** the serial version uid */
    private static final long serialVersionUID = 1L;

    /** the tasks */
    private final FutureTask<?>[] m_tasks;

    /**
     * create the sequence task
     *
     * @param tasks
     *          the tasks to execute
     */
    _SequenceTask(final FutureTask<?>[] tasks) {
      super();
      this.m_tasks = tasks;
    }

    /** {@inheritDoc} */
    @Override
    protected final void compute() {
      for (final FutureTask<?> task : this.m_tasks) {
        task.run();
      }
    }
  }
}
