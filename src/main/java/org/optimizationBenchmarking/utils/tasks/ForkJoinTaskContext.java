package org.optimizationBenchmarking.utils.tasks;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;

/**
 * A {@link org.optimizationBenchmarking.utils.tasks.ITaskContext} that
 * makes use of Java's Fork-Join framework.
 */
public class ForkJoinTaskContext extends SimpleTaskContext {

  /** create */
  public ForkJoinTaskContext() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public <T> Future<T> submit(final Task<T> task) {
    if (ForkJoinTask.inForkJoinPool()) {
      return ForkJoinTask.adapt(task).fork();
    }
    return super.submit(task);
  }

}