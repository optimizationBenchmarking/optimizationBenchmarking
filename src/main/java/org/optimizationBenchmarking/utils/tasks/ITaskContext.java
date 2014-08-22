package org.optimizationBenchmarking.utils.tasks;

import java.util.concurrent.Future;

/**
 * An interface allowing for execution of tasks
 */
public interface ITaskContext {

  /**
   * Submit the given task. The task may be executed immediately or
   * submitted to some parallel activity. The function returns a
   * {@link java.util.concurrent.Future} that can be used to wait for the
   * completion of the task and to obtain its return value.
   * 
   * @param task
   *          the task to execute
   * @return a {@link java.util.concurrent.Future} associated with the
   *         given task
   * @param <T>
   *          the return type
   */
  public abstract <T> Future<T> submit(final Task<T> task);
}
