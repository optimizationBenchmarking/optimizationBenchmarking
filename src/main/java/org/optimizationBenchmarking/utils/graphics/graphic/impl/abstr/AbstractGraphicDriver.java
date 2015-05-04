package org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.graphics.style.stroke.DefaultStrokePalette;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokePalette;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.tools.impl.abstr.DocumentProducerTool;

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
   * @param builder
   *          the graphic builder
   * @return the graphic
   */
  protected abstract Graphic createGraphic(final GraphicBuilder builder);

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
    return EColorModel.ARGB_32_BIT.getDefaultPalette();
  }

  /** {@inheritDoc} */
  @Override
  public StrokePalette getStrokePalette() {
    return DefaultStrokePalette.getInstance();
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(this.toString());
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
