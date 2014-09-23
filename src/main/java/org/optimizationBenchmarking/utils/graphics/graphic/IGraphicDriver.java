package org.optimizationBenchmarking.utils.graphics.graphic;

import java.io.OutputStream;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.impl.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokePalette;

/**
 * An interface for graphics drivers, i.e., objects that can create
 * graphics objects.
 */
public interface IGraphicDriver {

  /**
   * Create a graphics object with the size {@code size} in the length unit
   * {@code size.getUnit()}. If the resulting object is an object which
   * writes contents to a file, then it will write its contents to a file
   * in the specified by {@code folder}. The file name will be generated
   * based on a {@code nameSuggestion}. It may be slightly different,
   * though, maybe with a different suffix. Once the graphic is
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.Graphic#close()
   * closed}, it will notify the provided {@code listener} interface
   * (unless {@code listener==null}).
   * 
   * @param folder
   *          the folder to create the graphic in
   * @param nameSuggestion
   *          the name suggestion
   * @param size
   *          the size of the graphic
   * @param listener
   *          the listener interface to be notified when the graphic is
   *          closed
   * @return the graphic object
   */
  public abstract Graphic createGraphic(final Path folder,
      final String nameSuggestion, final PhysicalDimension size,
      final IObjectListener listener);

  /**
   * Create a graphics object with the size {@code size} in the length unit
   * {@code size.getUnit()}. If the resulting object is an object which
   * writes contents to a stream, then it will write its contents to the
   * stream in the specified in {@code os}. The file name will be generated
   * based on a {@code nameSuggestion}. It may be slightly different,
   * though, maybe with a different suffix. Once the graphic is
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.Graphic#close()
   * closed}, it will notify the provided {@code listener} interface
   * (unless {@code listener==null}).
   * 
   * @param os
   *          the output stream to write to
   * @param size
   *          the size of the graphic
   * @param listener
   *          the listener interface to be notified when the graphic is
   *          closed
   * @return the graphic object
   */
  public abstract Graphic createGraphic(final OutputStream os,
      final PhysicalDimension size, final IObjectListener listener);

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
