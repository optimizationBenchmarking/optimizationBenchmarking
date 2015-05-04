package org.optimizationBenchmarking.utils.tools.spec;

import java.util.logging.Logger;

/**
 * This is a builder object for creating jobs of a given tool. With this
 * object, you can configure the job to your liking and then obtain/execute
 * it.
 */
public interface IToolJobBuilder {

  /**
   * Set the {@link java.util.logging.Logger logger} to receive logging
   * information during the tool usage. You can call this method at most
   * once. Once a {@link java.util.logging.Logger logger} has been set, it
   * cannot be changed. If you do not want the tool usage to produce
   * logging information, simply do not call this method.
   *
   * @param logger
   *          the logger, must never be {@code null}
   * @return this job builder (for chaining purposes)
   */
  public abstract IToolJobBuilder setLogger(final Logger logger);

  /**
   * <p>
   * Create the tool job. This method must be the very last method invoked
   * in a {@link IToolJobBuilder}. When it is called, the configuration
   * parameters set with the methods of this object are compiled into a
   * tool job.
   * </p>
   * <p>
   * This method must be called at most once.
   * </p>
   * <p>
   * The interface
   * {@link org.optimizationBenchmarking.utils.tools.spec.IToolJob} is just
   * a very abstract base interface, actual tool implementations may (and
   * will) override this method to produce more specific return values,
   * e.g., instances of {@link java.util.concurrent.Callable} or documents
   * or graphics which can be edited or something.
   * </p>
   *
   * @return the job
   * @throws Exception
   *           if job creation failed
   */
  public abstract IToolJob create() throws Exception;

}
