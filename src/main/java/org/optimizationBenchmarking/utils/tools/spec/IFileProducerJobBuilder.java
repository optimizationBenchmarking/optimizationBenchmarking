package org.optimizationBenchmarking.utils.tools.spec;

import java.util.logging.Logger;

/**
 * The basic interface for the builder for a file producing job.
 */
public interface IFileProducerJobBuilder extends IToolJobBuilder {

  /**
   * Set the listener to receive a notification about the produced files
   *
   * @param listener
   *          the listener
   * @return this file producer builder, for chaining purposes
   */
  public abstract IFileProducerJobBuilder setFileProducerListener(
      final IFileProducerListener listener);

  /** {@inheritDoc} */
  @Override
  public abstract IFileProducerJobBuilder setLogger(final Logger logger);
}
