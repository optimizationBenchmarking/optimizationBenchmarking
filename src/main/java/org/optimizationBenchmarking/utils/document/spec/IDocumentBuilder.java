package org.optimizationBenchmarking.utils.document.spec;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.graphics.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.tools.spec.IDocumentProducerJobBuilder;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A builder for documents.
 */
public interface IDocumentBuilder extends IDocumentProducerJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IDocumentBuilder setFileProducerListener(
      final IFileProducerListener listener);

  /** {@inheritDoc} */
  @Override
  public abstract IDocumentBuilder configure(final Configuration config);

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
   * Set the <a href="http://en.wikipedia.org/wiki/Dots_per_inch">dots
   * per-inch</a> for the document and its graphics. Not all document
   * drivers may support this property.
   * 
   * @param dotsPerInch
   *          the dots per inch, must be between {@code 26} and
   *          {@code 100000}
   * @return this builder
   * @see org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder#setDotsPerInch(int)
   */
  public abstract IDocumentBuilder setDotsPerInch(final int dotsPerInch);

  /**
   * Set the
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder#setColorModel(EColorModel)
   * color model} of the document and its graphics. Setting a color model
   * is not necessarily supported by all document drivers. If supported,
   * setting a color model only takes effect during storing the document to
   * a stream. You can still use all colors during the process of creating
   * the document, they may just be transformed to the chosen model at the
   * end.
   * 
   * @param colorModel
   *          the color model
   * @return this builder
   * @see org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder#setColorModel(EColorModel)
   */
  public abstract IDocumentBuilder setColorModel(
      final EColorModel colorModel);

  /**
   * Set the
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder#setQuality(double)
   * quality} of the document and its graphics. {@code 0} means that focus
   * is on small size and encoding speed, {@code 1} means that focus is on
   * rendering quality.
   * 
   * @param quality
   *          the quality, must be in {@code [0,1]}
   * @return this builder
   * @see org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder#setQuality(double)
   */
  public abstract IDocumentBuilder setQuality(final double quality);

  /**
   * Set the graphic driver to be used for creating the
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic
   * graphics} inside the document.
   * 
   * @param driver
   *          the graphic driver
   * @return this builder
   */
  public abstract IDocumentBuilder setGraphicDriver(
      final IGraphicDriver driver);

  /**
   * Set the chart driver for creating
   * {@link org.optimizationBenchmarking.utils.graphics.chart.spec.IChartDriver#use()
   * charts}.
   * 
   * @param driver
   *          the chart driver
   * @return this builder
   */
  public abstract IDocumentBuilder setChartDriver(final IChartDriver driver);

  /**
   * Create the document
   * 
   * @return the document
   */
  @Override
  public abstract IDocument create();

}
