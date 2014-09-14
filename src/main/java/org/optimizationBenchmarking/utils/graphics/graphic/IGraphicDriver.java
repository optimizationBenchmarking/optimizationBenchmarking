package org.optimizationBenchmarking.utils.graphics.graphic;

import java.awt.geom.Dimension2D;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * An interface for graphics drivers, i.e., objects that can create
 * graphics objects.
 */
public interface IGraphicDriver {

  /**
   * <p>
   * Create a graphic ID based on the given path {@code suggestion}. The
   * graphic driver may use the path {@code suggestion} directly as graphic
   * path, it may append a file extension, or it may perform any other
   * modification. This method has two main purposes:
   * <ol>
   * <li>It keeps the file base-name generation independent from the type
   * of file which the
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.Graphic}
   * produces.</li>
   * <li>It provides a unique identifier for each graphic that can be
   * passed to the
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.IGraphicListener#onGraphicClosed(GraphicID)}
   * method of the
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.IGraphicListener
   * graphic listener interface}.</li>
   * </ol>
   * <p>
   * Each graphic id can only be used at most once.
   * </p>
   * 
   * @param suggestion
   *          the suggested path
   * @return an id that can be passed to
   *         {@link #createGraphic(GraphicID, Dimension2D, ELength, IGraphicListener)}
   * @see #createGraphic(GraphicID, Dimension2D, ELength, IGraphicListener)
   */
  public abstract GraphicID createGraphicID(final Path suggestion);

  /**
   * Create a graphics object which writes its contents to the destination
   * identified by {@code id}, has the size {@code size} in the length unit
   * {@code sizeUnit} and which will notify the provided {@code listener}
   * interface when it is closed.
   * 
   * @param id
   *          the destination id, <em>must</em> be the result of a call to
   *          {@link #createGraphicID(Path)}
   * @param size
   *          the size of the graphic, in unit {@code sizeUnit}
   * @param sizeUnit
   *          the unit in which the values in {@code size} are specified
   * @param listener
   *          the listener interface to be notified when the graphic is
   *          closed
   * @return the graphic object
   * @see #createGraphicID(Path)
   */
  public abstract Graphic createGraphic(final GraphicID id,
      final Dimension2D size, final ELength sizeUnit,
      final IGraphicListener listener);

}
