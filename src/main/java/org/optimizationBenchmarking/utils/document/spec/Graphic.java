package org.optimizationBenchmarking.utils.document.spec;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * A graphics 2d object designed as output context for graphics. The size
 * of this graphic in device coordinates can be obtained via
 * {@link #getBounds()}, whereas its width and height in device-independent
 * units can be obtained via {@link #getWidth(ELength)} and
 * {@link #getHeight(ELength)}.
 */
public abstract class Graphic extends Graphics2D implements IDocumentPart {

  /** instantiate */
  protected Graphic() {
    super();
  }

  /**
   * Obtain the bounds of this graphic in device/pixel/whatever coordinates
   * 
   * @return the bounds of this graphic
   */
  public abstract Rectangle getBounds();

  /**
   * Get the width of this graphic in a given unit
   * 
   * @param unit
   *          the unit of length
   * @return the width of this graphic in the given unit
   */
  public abstract double getWidth(final ELength unit);

  /**
   * Get the height of this graphic in a given unit
   * 
   * @param unit
   *          the unit of length
   * @return the height of this graphic in the given unit
   */
  public abstract double getHeight(final ELength unit);

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

}
