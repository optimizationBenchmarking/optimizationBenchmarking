package org.optimizationBenchmarking.utils.graphics.graphic.spec;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.tools.spec.IDocumentProducerJobBuilder;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/** A builder for graphics objects */
public interface IGraphicBuilder extends IDocumentProducerJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IGraphicBuilder setFileProducerListener(
      final IFileProducerListener listener);

  /** {@inheritDoc} */
  @Override
  public abstract IGraphicBuilder configure(final Configuration config);

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
   * <p>
   * Set the <a href="http://en.wikipedia.org/wiki/Dots_per_inch">dots
   * per-inch</a> of the graphic.
   * </p>
   * <p>
   * Not all graphics may support this property. <a
   * href="http://en.wikipedia.org/wiki/Raster_graphics">Raster
   * graphics</a>, such as
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#PNG
   * PNG}s or
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#JPEG
   * JPG}s should support it. For <a
   * href="http://en.wikipedia.org/wiki/Vector_graphics">vector graphic</a>
   * formats such as
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#EPS
   * EPS} or
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#PDF
   * PDF}, it makes no much sense. These formats may still support this
   * property, though, maybe to deal with raster graphics inserted into
   * them, via, e.g.,
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic#drawImage(java.awt.Image, int, int, java.awt.image.ImageObserver)}
   * .
   * </p>
   * <p>
   * If this parameter is not set, the
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#DEFAULT_DPI
   * default value} for DPI is used.
   * </p>
   * 
   * @param dotsPerInch
   *          the dots per inch, must be between {@code 26} and
   *          {@code 100000}
   * @return this builder
   */
  public abstract IGraphicBuilder setDotsPerInch(final int dotsPerInch);

  /**
   * <p>
   * Set the
   * {@link org.optimizationBenchmarking.utils.graphics.style.color.EColorModel
   * color model} for <em>storing</em> the produced image. This color model
   * will <em>only</em> take effect when writing the contents of the
   * graphics object to a stream. It will not limit the colors you can use
   * to paint on the
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic}
   * , such as the colors you can pass to
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic#setColor(java.awt.Color)}
   * .
   * </p>
   * <p>
   * If supported, the impact of this color model parameter is as follows:
   * It may change the way the graphic data is stored in memory or to a
   * stream. Some graphics formats, such as
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#GIF
   * GIF} may support only very limited color models (in that case, only
   * 256 colors). You may set the color model here to
   * {@link org.optimizationBenchmarking.utils.graphics.style.color.EColorModel#RGB_24_BIT
   * 24bit RBG}, in which case the image is rendered with 1 byte per color
   * channel in memory and then serialized to 256 colors on output.
   * </p>
   * <p>
   * Some graphics formats may not support this parameter and, hence,
   * ignore it.
   * </p>
   * <p>
   * If this parameter is not set, the
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#DEFAULT_COLOR_MODEL
   * default color model} is used.
   * </p>
   * 
   * @param colorModel
   *          the color model
   * @return this builder
   */
  public abstract IGraphicBuilder setColorModel(
      final EColorModel colorModel);

  /**
   * <p>
   * Some graphics drivers may support tuning the image quality. If a
   * driver supports this setting, then a {@code quality} value of
   * {@code 0} means focus on speed and reduced file size while {@code 1}
   * means image quality is more important than speed or file size.
   * </p>
   * <p>
   * If this parameter is not set, the default image quality setting is
   * used:
   * {@value org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#DEFAULT_QUALITY}
   * .
   * </p>
   * 
   * @param quality
   *          the quality of the produced image
   * @return this builder
   */
  public abstract IGraphicBuilder setQuality(final double quality);

  /**
   * Create the graphic
   * 
   * @return the graphic
   */
  @Override
  public abstract Graphic create();
}
