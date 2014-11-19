package org.optimizationBenchmarking.utils.document.spec;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.spec.IFileProducerBuilder;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A builder for documents.
 */
public interface IDocumentBuilder extends IFileProducerBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IDocumentBuilder setFileProducerListener(
      final IFileProducerListener listener);

  /** {@inheritDoc} */
  @Override
  public abstract IDocumentBuilder setBasePath(final Path basePath);

  /** {@inheritDoc} */
  @Override
  public abstract IDocumentBuilder setMainDocumentNameSuggestion(
      final String name);

  /** {@inheritDoc} */
  @Override
  public abstract IDocumentBuilder setLogger(final Logger logger);

  /**
   * Create the document
   * 
   * @return the document
   */
  @Override
  public abstract IDocument create();

}
