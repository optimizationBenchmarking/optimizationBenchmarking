package org.optimizationBenchmarking.utils.graphics;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.Closeable;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * A graphics 2d object designed as output context for graphics. The size
 * of this graphic in device coordinates can be obtained via
 * {@link #getDeviceBounds()}, whereas its width and height in
 * device-independent units can be obtained via the
 * {@link #deviceToUnitWidth(ELength)} and
 * {@link #deviceToUnitHeight(ELength)} conversion functions.
 */
public abstract class Graphic extends Graphics2D implements Closeable {

  /**
   * the object to notify when we are closed, or {@code null} if none needs
   * to be notified
   */
  private final IGraphicListener m_listener;

  /** has we been closed ? */
  volatile boolean m_closed;

  /**
   * the graphic id identifying this graphic and the path under which the
   * contents of the graphic are stored
   */
  private final GraphicID m_id;

  /**
   * instantiate
   * 
   * @param listener
   *          the object to notify when we are closed, or {@code null} if
   *          none needs to be notified
   * @param id
   *          the graphic id identifying this graphic and the path under
   *          which the contents of the graphic are stored
   */
  protected Graphic(final IGraphicListener listener, final GraphicID id) {
    super();

    if (id == null) {
      throw new IllegalArgumentException("Graphic id must not be null."); //$NON-NLS-1$
    }
    id._use();

    this.m_id = id;
    this.m_listener = listener;
  }

  /**
   * Obtain the bounds of this graphic in device coordinates.
   * 
   * @return the bounds of this graphic
   */
  public abstract Rectangle2D getDeviceBounds();

  /**
   * A function which transforms widths ({@code x}-coordinates) from device
   * units to the provided unit
   * 
   * @param unit
   *          the unit to convert device widths to
   * @return a
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction}
   *         which can convert device with values to the specified unit
   * @see #unitToDeviceWidth(ELength)
   */
  public abstract UnaryFunction deviceToUnitWidth(final ELength unit);

  /**
   * A function which transforms widths ({@code x}-coordinates) from the
   * specified unit to device units.
   * 
   * @param unit
   *          the unit to convert to device widths
   * @return a
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction}
   *         which can convert widths specified in the given unit to device
   *         widths
   * @see #deviceToUnitWidth(ELength)
   */
  public abstract UnaryFunction unitToDeviceWidth(final ELength unit);

  /**
   * A function which transforms heights ({@code y}-coordinates) from
   * device units to the provided unit
   * 
   * @param unit
   *          the unit to convert device heights to
   * @return a
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction}
   *         which can convert device heights values to the specified unit
   * @see #unitToDeviceHeight(ELength)
   */
  public abstract UnaryFunction deviceToUnitHeight(final ELength unit);

  /**
   * A function which transforms heights ({@code y}-coordinates) from the
   * specified unit to device units.
   * 
   * @param unit
   *          the unit to convert to device heights
   * @return a
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction}
   *         which can convert heights specified in the given unit to
   *         device heights
   * @see #deviceToUnitHeight(ELength)
   */
  public abstract UnaryFunction unitToDeviceHeight(final ELength unit);

  /**
   * The {@link #dispose()} method forwards the call to the idempotent
   * {@link #close()} and has the same semantics.
   */
  @Override
  public final void dispose() {
    this.close();
  }

  /**
   * Close this graphic object. This method will be called one time by
   * {@link #close()}.
   */
  protected void onClose() {
    //
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void close() {
    if (this.m_closed) {
      return;
    }
    this.m_closed = true;
    try {
      this.onClose();
    } finally {
      if (this.m_listener != null) {
        this.m_listener.onGraphicClosed(this.m_id);
      }
    }
  }

  /** the test string */
  private static final char[] TEST;
  static {
    final char min, max;
    char i;

    min = 0x21;
    max = 0x7e;
    TEST = new char[(max - min) + 1];
    for (i = max; i >= min; i--) {
      Graphic.TEST[i - min] = i;
    }
  }

  /**
   * Create a font which approximately has a given standard height
   * 
   * @param name
   *          the name of the font
   * @param style
   *          the style
   * @param standardHeight
   *          the standard height to approximate
   * @param unit
   *          the length unit of the standard height
   * @return the font
   */
  public Font createFont(final String name, final int style,
      final double standardHeight, final ELength unit) {
    final FontRenderContext frc;
    final float heightInPT;
    int height;
    final UnaryFunction devToPT;
    Font minFont, maxFont, bestFont, curFont;
    float bestHeight, curHeight, minHeight, maxHeight;

    heightInPT = ((float) (unit.getConversion(ELength.PT)
        .compute(standardHeight)));
    frc = this.getFontRenderContext();
    devToPT = this.deviceToUnitHeight(ELength.PT);

    minFont = maxFont = null;
    maxHeight = Float.NEGATIVE_INFINITY;
    minHeight = Float.POSITIVE_INFINITY;

    findMinFont: for (height = (((int) heightInPT)); height > 0; height--) {
      minFont = new Font(name, style, height);
      minHeight = devToPT.compute(minFont.getLineMetrics(Graphic.TEST, 0,
          Graphic.TEST.length, frc).getHeight());
      if (minHeight < heightInPT) {
        break findMinFont;
      }
      if (minHeight > heightInPT) {
        maxFont = minFont;
        maxHeight = minHeight;
      } else {
        return minFont;
      }
    }

    if (maxFont == null) {
      findMaxFont: for (;;) {
        maxFont = new Font(name, style, (++height));
        maxHeight = devToPT.compute(maxFont.getLineMetrics(Graphic.TEST,
            0, Graphic.TEST.length, frc).getHeight());
        if (maxHeight > heightInPT) {
          break findMaxFont;
        }
        if (maxHeight < heightInPT) {
          minFont = maxFont;
          minHeight = maxHeight;
        } else {
          return maxFont;
        }
      }
    }

    if (Math.abs(maxHeight - heightInPT) < Math
        .abs(minHeight - heightInPT)) {
      bestFont = maxFont;
      bestHeight = maxHeight;
    } else {
      bestFont = minFont;
      bestHeight = minHeight;
    }

    curFont = bestFont.deriveFont(heightInPT);
    curHeight = devToPT.compute(curFont.getLineMetrics(Graphic.TEST, 0,
        Graphic.TEST.length, frc).getHeight());

    if (Math.abs(curHeight - heightInPT) < Math.abs(bestHeight
        - heightInPT)) {
      bestFont = curFont;
      bestHeight = curHeight;
    }

    return bestFont;

    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // final Font oldFont;
    // final FontRenderContext frc;
    // Font bestFont, bestFontAbove, bestFontBelow, font;
    // double bestHeight, bestHeightAbove, bestHeightBelow, height, diff;
    // UnaryFunction toUnit;
    //
    // font = bestFont = new Font(name, style, ((int) (0.5d + unit
    // .getConversion(ELength.PT).compute(standardHeight))));
    // toUnit = this.deviceToUnitHeight(unit);
    // bestHeightBelow = Double.POSITIVE_INFINITY;
    // bestHeightAbove = Double.NEGATIVE_INFINITY;
    // bestFontBelow = bestFontAbove = null;
    //
    // frc = this.getFontRenderContext();
    // oldFont = this.getFont();
    //
    // try {
    // this.setFont(bestFont);
    //
    // findFont: {
    //
    // bestHeight = toUnit.compute((double) (m.getHeight()));
    //
    // if (bestHeight > standardHeight) {
    // bestFontAbove = bestFont;
    // bestHeightAbove = bestHeight;
    // } else {
    // if (bestHeight < standardHeight) {
    // bestFontBelow = bestFont;
    // bestHeightBelow = bestHeight;
    // } else {
    // break findFont;
    // }
    // }
    //
    // // in a first step, we try to approximate the right font size
    // approximationLoop: for (;;) {
    // font = bestFont.deriveFont((float) (bestFont.getSize2D()
    // * standardHeight / bestHeight));
    // if (font.equals(bestFont)) {
    // break;
    // }
    // this.setFont(font);
    // m = this.getFontMetrics();
    //
    // height = toUnit.compute((double) (m.getHeight()));
    // diff = (height - standardHeight);
    // if (diff < 0d) {
    // if (height > bestHeightBelow) {
    // bestHeightBelow = height;
    // bestFontBelow = font;
    // }
    // diff = (-diff);
    // } else {
    // if (diff > 0d) {
    // if (height < bestHeightAbove) {
    // bestHeightAbove = height;
    // bestFontAbove = font;
    // }
    // } else {
    // bestFont = font;
    // break findFont;
    // }
    // }
    //
    // if (Math.abs(bestHeight - standardHeight) <= diff) {
    // break approximationLoop;
    // }
    // bestHeight = height;
    // bestFont = font;
    // }
    //
    // // the approximation loop has completed, now we can try to improve
    // // the result with binary search
    // System.out.println(bestFontAbove);
    // System.out.println(bestFontBelow);
    // }
    // } finally {
    // this.setFont(oldFont);
    // }
    //
    // return bestFont;
  }
}
