package org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr;

import java.awt.Dimension;

import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.graphics.style.stroke.DefaultStrokePalette;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokePalette;
import org.optimizationBenchmarking.utils.math.units.ELength;
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

  /**
   * Get the integer dimensions measures in a given
   * {@link org.optimizationBenchmarking.utils.math.units.ELength length
   * unit} and check that they are not empty.
   *
   * @param size
   *          the physical dimensions
   * @param target
   *          the target unit
   * @return the integer dimensions in the
   *         {@link org.optimizationBenchmarking.utils.math.units.ELength#POINT
   *         length unit}
   */
  public static final Dimension getIntegerSizeInUnit(
      final PhysicalDimension size, final ELength target) {
    final Dimension dim;
    final ELength sizeUnit;
    double wd, hd;

    if (target == null) {
      throw new IllegalArgumentException(
          "Target length unit cannot be null.");//$NON-NLS-1$
    }
    if (size == null) {
      throw new IllegalArgumentException("Size cannot be null.");//$NON-NLS-1$
    }

    sizeUnit = size.getUnit();

    wd = size.getWidth();
    hd = size.getHeight();

    if (sizeUnit != target) {
      wd = sizeUnit.convertTo(wd, target);
      hd = sizeUnit.convertTo(hd, target);
    }

    dim = new Dimension(((int) (0.5d + wd)), ((int) (0.5d + hd)));

    if ((wd <= 0d) || (wd >= Integer.MAX_VALUE) || (wd != wd) || //
        (hd <= 0d) || (hd >= Integer.MAX_VALUE) || (hd != hd) || //
        (dim.width <= 0) || (dim.height <= 0)) {
      throw new IllegalArgumentException((((((//
          "Invalid size " + size) + //$NON-NLS-1$
          " translated to ") + dim) + //$NON-NLS-1$
          " in ")//$NON-NLS-1$
          + target) + '.');
    }

    return dim;
  }

  /**
   * Get the integer dimensions measures in
   * {@link org.optimizationBenchmarking.utils.math.units.ELength#POINT
   * points} and check that they are not empty.
   *
   * @param size
   *          the physical dimensions
   * @return the integer dimensions in
   *         {@link org.optimizationBenchmarking.utils.math.units.ELength#POINT
   *         points}
   */
  public static final Dimension getIntegerSizeInPoints(
      final PhysicalDimension size) {
    return AbstractGraphicDriver.getIntegerSizeInUnit(size, ELength.POINT);
  }
}
