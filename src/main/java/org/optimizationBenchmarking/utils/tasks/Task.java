package org.optimizationBenchmarking.utils.tasks;

import java.util.concurrent.Callable;

/**
 * A base class for tasks.
 * 
 * @param <T>
 *          the task's return parameter
 */
public class Task<T> implements Callable<T> {

  /** the globally shared task context */
  private static volatile ITaskContext s_context = new SimpleTaskContext();

  /** create the task */
  protected Task() {
    super();
  }

  /**
   * Obtain the the globally shared task context of the application.
   * 
   * @return the globally shared task context of the application
   */
  public static final ITaskContext getTaskContext() {
    return Task.s_context;
  }

  /**
   * Set the globally shared task context.
   * 
   * @param context
   *          the new globally shared task context
   */
  public static final void setTaskContext(final ITaskContext context) {
    if (context == null) {
      throw new NullPointerException(//
          "Task context must not be null."); //$NON-NLS-1$
    }
    Task.s_context = context;
  }

  /** {@inheritDoc} */
  @Override
  public T call() throws Exception {
    throw new UnsupportedOperationException();
  }
}
