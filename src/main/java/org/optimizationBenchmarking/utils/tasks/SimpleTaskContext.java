package org.optimizationBenchmarking.utils.tasks;

import java.util.concurrent.Future;

/**
 * A simple, non-parallelized implementation of the
 * {@link org.optimizationBenchmarking.utils.tasks.ITaskContext} interface.
 */
public class SimpleTaskContext implements ITaskContext {

  /** create */
  protected SimpleTaskContext() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public <T> Future<T> submit(final Task<T> task) {
    T res;
    Throwable err;

    res = null;
    err = null;
    try {
      res = task.call();
    } catch (final Throwable t) {
      err = t;
      res = null;
    }

    return new _SimpleFuture<>(res, err);
  }

}