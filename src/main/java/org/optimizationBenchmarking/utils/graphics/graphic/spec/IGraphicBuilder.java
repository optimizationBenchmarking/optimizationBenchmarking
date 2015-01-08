package org.optimizationBenchmarking.utils.graphics.graphic.spec;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.tools.spec.IDocumentProducerBuilder;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/** A builder for graphics objects */
public interface IGraphicBuilder extends IDocumentProducerBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IGraphicBuilder setFileProducerListener(
      final IFileProducerListener listener);

  /** {@inheritDoc} */
  @Override
  public abstract IGraphicBuilder setBasePath(final Path basePath);

  /** {@inheritDoc} */
  @Override
  public abstract IGraphicBuilder setMainDocumentNameSuggestion(
      final String name);

  /** {@inheritDoc} */
  @Override
  public abstract IGraphicBuilder setLogger(final Logger logger);

  /**
   * Set the size of the graphic
   * 
   * @param size
   *          the physical dimensions, i.e., the graphic's size
   * @return this builder, for chaining purposes
   */
  public abstract IGraphicBuilder setSize(final PhysicalDimension size);

  /**
   * Create the graphic
   * 
   * @return the graphic
   */
  @Override
  public abstract Graphic create();
}
