package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/**
 * An evaluation job which can be executed by an
 * {@link java.util.concurrent.ExecutorService} such as a
 * {@link java.util.concurrent.ForkJoinPool} or
 * {@link java.util.concurrent.Executors#newCachedThreadPool() thread pool}
 * .
 */
public interface IEvaluation extends Runnable, IToolJob {

  /**
   * Perform the evaluation.
   */
  @Override
  public abstract void run();

}
