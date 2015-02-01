package org.optimizationBenchmarking.utils.tools.spec;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;

/**
 * The basic interface for the builder for a file producing job.
 */
public interface IDocumentProducerJobBuilder extends
    IFileProducerJobBuilder, IConfigurableToolJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IDocumentProducerJobBuilder setFileProducerListener(
      final IFileProducerListener listener);

  /**
   * Set the basic path in which the file(s) should be produced
   * 
   * @param basePath
   *          the basic path in which the file(s) should be produced
   * @return this file producer builder, for chaining purposes
   */
  public abstract IDocumentProducerJobBuilder setBasePath(
      final Path basePath);

  /**
   * Set the base name for the main output document
   * 
   * @param name
   *          the base name for the main output document
   * @return this file producer builder, for chaining purposes
   */
  public abstract IDocumentProducerJobBuilder setMainDocumentNameSuggestion(
      final String name);

  /** {@inheritDoc} */
  @Override
  public abstract IDocumentProducerJobBuilder setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IDocumentProducerJobBuilder configure(
      final Configuration config);
}
