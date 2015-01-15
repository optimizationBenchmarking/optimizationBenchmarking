package org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.graphics.style.stroke.DefaultStrokePalette;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokePalette;
import org.optimizationBenchmarking.utils.tools.impl.abstr.DocumentProducerTool;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver}
 * interface.
 */
public abstract class AbstractGraphicDriver extends DocumentProducerTool
    implements IGraphicDriver {

  /** the graphic format managed by this driver */
  private final EGraphicFormat m_format;

  /**
   * instantiate
   * 
   * @param format
   *          the format
   */
  protected AbstractGraphicDriver(final EGraphicFormat format) {
    super();
    this.m_format = format;
  }

  /**
   * Create the graphic
   * 
   * @param logger
   *          the logger
   * @param listener
   *          the listener
   * @param basePath
   *          the base path where to create the graphics file
   * @param mainDocumentNameSuggestion
   *          the name suggestion
   * @param size
   *          the size
   * @return the graphic
   */
  protected abstract Graphic createGraphic(final Logger logger,
      final IFileProducerListener listener, final Path basePath,
      final String mainDocumentNameSuggestion, final PhysicalDimension size);

  /** {@inheritDoc} */
  @Override
  public final GraphicBuilder use() {
    this.checkCanUse();
    return new GraphicBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  public final EGraphicFormat getFileType() {
    return this.m_format;
  }

  /** {@inheritDoc} */
  @Override
  public ColorPalette getColorPalette() {
    return EColorModel.RBGA_32_BIT.getDefaultPalette();
  }

  /** {@inheritDoc} */
  @Override
  public StrokePalette getStrokePalette() {
    return DefaultStrokePalette.getInstance();
  }

}
