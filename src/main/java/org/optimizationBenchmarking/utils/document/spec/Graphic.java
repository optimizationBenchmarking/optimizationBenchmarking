package org.optimizationBenchmarking.utils.document.spec;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * A graphics 2d object designed as output context for graphics. The size
 * of this graphic in device coordinates can be obtained via
 * {@link #getBounds()}, whereas its width and height in device-independent
 * units can be obtained via the {@link #deviceLengthToUnitLength(ELength)}
 * and {@link #deviceHeightToUnitHeight(ELength)} conversion functions.
 */
public abstract class Graphic extends Graphics2D implements
    IDocumentElement {

  /**
   * the object to notify when we are closed, or {@code null} if none needs
   * to be notified
   */
  private final IGraphicListener m_listener;

  /** has we been closed ? */
  private boolean m_closed;

  /** the path to the destination file */
  private final Path m_path;

  /**
   * instantiate
   * 
   * @param path
   *          the path to the destination file, or {@code null} if no file
   *          is created
   * @param listener
   *          the object to notify when we are closed, or {@code null} if
   *          none needs to be notified
   */
  protected Graphic(final Path path, final IGraphicListener listener) {
    super();
    this.m_listener = listener;
    this.m_path = path;
  }

  /**
   * Obtain the bounds of this graphic in device coordinates.
   * 
   * @return the bounds of this graphic
   */
  public abstract Rectangle2D getBounds();

  /**
   * A function which transforms lengths ({@code x}-coordinates) from
   * device units to the provided unit
   * 
   * @param unit
   *          the unit to convert device lengths to
   * @return a
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction}
   *         which can convert device length values to the specified unit
   */
  public abstract UnaryFunction deviceLengthToUnitLength(final ELength unit);

  /**
   * A function which transforms heights ({@code y}-coordinates) from
   * device units to the provided unit
   * 
   * @param unit
   *          the unit to convert device heights to
   * @return a
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction}
   *         which can convert device heights values to the specified unit
   */
  public abstract UnaryFunction deviceHeightToUnitHeight(final ELength unit);

  /**
   * Use a given style for some time and then reset the graphical output
   * settings to what they were before. The style may affect the line
   * color, background color, fonts, etc.
   * 
   * @param style
   *          the style to use
   * @return a {@link java.lang.AutoCloseable} which reverts to the
   *         previous style when closed
   */
  public abstract IStyleSetting useStyle(final IStyle style);

  /**
   * Close this graphic object. This method will be called one time by
   * {@link #close()}.
   */
  protected void doClose() {
    this.dispose();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void close() {
    if (this.m_closed) {
      return;
    }
    this.m_closed = true;
    try {
      this.doClose();
    } finally {
      if (this.m_listener != null) {
        this.m_listener.onClosed(this, this.m_path);
      }
    }
  }
}
