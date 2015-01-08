package org.optimizationBenchmarking.utils.tools.spec;

import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * The basic interface for the builder for a file producing job.
 */
public interface IDocumentProducerBuilder extends IFileProducerBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IDocumentProducerBuilder setFileProducerListener(
      final IFileProducerListener listener);

  /**
   * Set the basic path in which the file(s) should be produced
   * 
   * @param basePath
   *          the basic path in which the file(s) should be produced
   * @return this file producer builder, for chaining purposes
   */
  public abstract IDocumentProducerBuilder setBasePath(final Path basePath);

  /**
   * Set the base name for the main output document
   * 
   * @param name
   *          the base name for the main output document
   * @return this file producer builder, for chaining purposes
   */
  public abstract IDocumentProducerBuilder setMainDocumentNameSuggestion(
      final String name);

  /** {@inheritDoc} */
  @Override
  public abstract IDocumentProducerBuilder setLogger(final Logger logger);
}
