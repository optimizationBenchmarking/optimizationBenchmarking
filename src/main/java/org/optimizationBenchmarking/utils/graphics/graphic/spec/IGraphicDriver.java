package org.optimizationBenchmarking.utils.graphics.graphic.spec;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokePalette;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerTool;

/**
 * An interface for graphics drivers, i.e., objects that can create
 * graphics objects.
 */
public interface IGraphicDriver extends IFileProducerTool {

  /** {@inheritDoc} */
  @Override
  public abstract IGraphicBuilder use();

  /**
   * Obtain the graphic format managed by this driver
   * 
   * @return the graphic format managed by this driver
   */
  @Override
  public abstract EGraphicFormat getFileType();

  /**
   * Get the default color palette associated with this graphic driver
   * 
   * @return the default color palette associated with this graphic driver
   */
  public abstract ColorPalette getColorPalette();

  /**
   * Get the default stroke palette associated with this graphic driver
   * 
   * @return the default stroke palette associated with this graphic driver
   */
  public abstract StrokePalette getStrokePalette();
}
