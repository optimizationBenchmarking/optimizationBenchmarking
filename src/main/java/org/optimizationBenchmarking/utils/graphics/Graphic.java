package org.optimizationBenchmarking.utils.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.io.Closeable;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Map;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * <p>
 * An abstract {@link java.awt.Graphics2D} implementation designed as
 * output context for graphics.
 * </p>
 * <p>
 * The size of this graphic in device coordinates can be obtained via
 * {@link #getDeviceBounds()}, whereas its width and height in
 * device-independent units can be obtained via the
 * {@link #deviceToUnitWidth(ELength)} and
 * {@link #deviceToUnitHeight(ELength)} conversion functions.
 * </p>
 * <p>
 * A graphic can be implemented in form of a
 * {@link org.optimizationBenchmarking.utils.graphics.GraphicProxy} which
 * forwards the calls to some library. The whole underlying implementation
 * becomes transparent if an implementation of
 * {@link org.optimizationBenchmarking.utils.graphics.IGraphicDriver} is
 * used to create such wrappers.
 * </p>
 * <p>
 * As inspired by <a
 * href="http://java.freehep.org/vectorgraphics">FreeHEP</a>, we provide
 * most of the routines of {@link java.awt.Graphics2D} in {@code double}
 * precision versions. If the underlying library which we wrap supports it,
 * graphics can then be drawn in higher precision. (The
 * {@link org.optimizationBenchmarking.utils.graphics.drivers.freeHEP
 * FreeHEP drivers} do). If there is no support for {@code double}
 * -precision output, the graphic tries to provide a reasonable mapping to
 * the existing {@code int}-based routines.
 * </p>
 */
public abstract class Graphic extends Graphics2D implements Closeable {

  /** the font attributes */
  private static final Map<TextAttribute, Object> FONT_ATTRIBUTES;

  static {
    FONT_ATTRIBUTES = new HashMap<>();
    FONT_ATTRIBUTES.put(TextAttribute.KERNING, TextAttribute.KERNING_ON);
    FONT_ATTRIBUTES.put(TextAttribute.LIGATURES,
        TextAttribute.LIGATURES_ON);
  }

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

  /** check the state of the graphic */
  protected final void checkClosed() {
    if (this.m_closed) {
      throw new IllegalStateException("Graphic has already been closed."); //$NON-NLS-1$
    }
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

  // new functionality

  /**
   * translate a {@code double} x-coordinate to an {@code int}
   * 
   * @param x
   *          the x-coordinate
   * @return the integer value
   */
  private static final int __x(final double x) {
    return ((int) x);
  }

  /**
   * translate a {@code double} y-coordinate to an {@code int}
   * 
   * @param y
   *          the y-coordinate
   * @return the integer value
   */
  private static final int __y(final double y) {
    return ((int) y);
  }

  /**
   * translate a {@code double} width to an {@code int}
   * 
   * @param w
   *          the width
   * @return the integer value
   */
  private static final int __w(final double w) {
    return ((int) (w + 0.5d));
  }

  /**
   * translate a {@code double} height to an {@code int}
   * 
   * @param h
   *          the height
   * @return the integer value
   */
  private static final int __h(final double h) {
    return ((int) (h + 0.5d));
  }

  /**
   * Draw a 3-d rectangle
   * 
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param raised
   *          is it raised?
   */
  public void draw3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    this.draw3DRect(__x(x), __y(y), __w(width), __h(height), raised);
  }

  /**
   * Fill a 3-d rectangle
   * 
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param raised
   *          is it raised?
   */
  public void fill3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    this.fill3DRect(__x(x), __y(y), __w(width), __h(height), raised);
  }

  /**
   * Draw a buffered image
   * 
   * @param img
   *          the image
   * @param op
   *          the operation
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  public void drawImage(final BufferedImage img, final BufferedImageOp op,
      final double x, final double y) {
    this.drawImage(img, op, __x(x), __y(y));
  }

  /**
   * Draw a string
   * 
   * @param str
   *          the string
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  public void drawString(final String str, final double x, final double y) {
    this.drawString(str, ((float) x), ((float) y));
  }

  /**
   * Draw a string
   * 
   * @param iterator
   *          the string
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  public void drawString(final AttributedCharacterIterator iterator,
      final double x, final double y) {
    this.drawString(iterator, ((float) x), ((float) y));
  }

  /**
   * Draw a glyph vector
   * 
   * @param g
   *          the glyph vector
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  public void drawGlyphVector(final GlyphVector g, final double x,
      final double y) {
    this.drawGlyphVector(g, ((float) x), ((float) y));
  }

  /**
   * create a graphics object
   * 
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @return the new graphics object
   */
  public Graphics create(final double x, final double y,
      final double width, final double height) {
    return this.create(__x(x), __y(y), __w(width), __h(height));
  }

  /**
   * clip the given rectangle
   * 
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public void clipRect(final double x, final double y, final double width,
      final double height) {
    this.clip(new Rectangle2D.Double(x, y, width, height));
  }

  /**
   * set the clip to the given rectangle
   * 
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public void setClip(final double x, final double y, final double width,
      final double height) {
    this.setClip(new Rectangle2D.Double(x, y, width, height));
  }

  /**
   * Copy some area
   * 
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param dx
   *          the movement x
   * @param dy
   *          the movement y
   */
  public void copyArea(final double x, final double y, final double width,
      final double height, final double dx, final double dy) {
    this.copyArea(__x(x), __y(y), __w(width), __h(height), __w(dx),
        __h(dy));
  }

  /**
   * draw a line
   * 
   * @param x1
   *          the start x-coordinate
   * @param y1
   *          the start y-coordinate
   * @param x2
   *          the end x-coordinate
   * @param y2
   *          the end y-coordinate
   */
  public void drawLine(final double x1, final double y1, final double x2,
      final double y2) {
    this.draw(new Line2D.Double(x1, y1, x2, y2));
  }

  /**
   * fill a rectangle
   * 
   * @param x
   *          the start x-coordinate
   * @param y
   *          the start y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public void fillRect(final double x, final double y, final double width,
      final double height) {
    this.fill(new Rectangle2D.Double(x, y, width, height));
  }

  /**
   * draw a rectangle
   * 
   * @param x
   *          the start x-coordinate
   * @param y
   *          the start y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public void drawRect(final double x, final double y, final double width,
      final double height) {
    this.draw(new Rectangle2D.Double(x, y, width, height));
  }

  /**
   * clear a rectangle
   * 
   * @param x
   *          the start x-coordinate
   * @param y
   *          the start y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public void clearRect(final double x, final double y,
      final double width, final double height) {
    final Paint p;

    p = this.getPaint();
    this.setPaint(this.getBackground());
    this.fillRect(x, y, width, height);
    this.setPaint(p);
  }

  /**
   * draw a round rectangle
   * 
   * @param x
   *          the start x-coordinate
   * @param y
   *          the start y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param arcWidth
   *          the arc width
   * @param arcHeight
   *          the arc height
   */
  public void drawRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    this.draw(new RoundRectangle2D.Double(x, y, width, height, arcWidth,
        arcHeight));
  }

  /**
   * fill a round rectangle
   * 
   * @param x
   *          the start x-coordinate
   * @param y
   *          the start y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param arcWidth
   *          the arc width
   * @param arcHeight
   *          the arc height
   */
  public void fillRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    this.fill(new RoundRectangle2D.Double(x, y, width, height, arcWidth,
        arcHeight));
  }

  /**
   * Draw an oval
   * 
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public void drawOval(final double x, final double y, final double width,
      final double height) {
    this.draw(new Ellipse2D.Double(x, y, width, height));
  }

  /**
   * Fill an oval
   * 
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public void fillOval(final double x, final double y, final double width,
      final double height) {
    this.fill(new Ellipse2D.Double(x, y, width, height));
  }

  /**
   * Draw an arc
   * 
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param startAngle
   *          the starting angle
   * @param arcAngle
   *          the angle
   */
  public void drawArc(final double x, final double y, final double width,
      final double height, final double startAngle, final double arcAngle) {
    this.draw(new Arc2D.Double(x, y, width, height, startAngle, arcAngle,
        Arc2D.OPEN));
  }

  /**
   * Draw an arc
   * 
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param startAngle
   *          the starting angle
   * @param arcAngle
   *          the angle
   */
  public void fillArc(final double x, final double y, final double width,
      final double height, final double startAngle, final double arcAngle) {
    this.fill(new Arc2D.Double(x, y, width, height, startAngle, arcAngle,
        Arc2D.CHORD));
  }

  /**
   * Create a shape composed of a set of points
   * 
   * @param xPoints
   *          the x points
   * @param yPoints
   *          the y points
   * @param nPoints
   *          the number of points
   * @param close
   *          should we close the shape?
   * @return the shape
   */
  protected Shape createShape(double[] xPoints, double[] yPoints,
      int nPoints, boolean close) {
    final GeneralPath path;

    path = new GeneralPath(Path2D.WIND_EVEN_ODD);
    if (nPoints > 0) {
      path.moveTo((float) xPoints[0], (float) yPoints[0]);
      for (int i = 1; i < nPoints; i++) {
        path.lineTo((float) xPoints[i], (float) yPoints[i]);
      }
      if (close)
        path.closePath();
    }
    return path;
  }

  /**
   * Draw a poly-line
   * 
   * @param xPoints
   *          the x-points
   * @param yPoints
   *          the y-points
   * @param nPoints
   *          the number of points
   */
  public void drawPolyline(final double xPoints[], final double yPoints[],
      final int nPoints) {
    this.draw(this.createShape(xPoints, yPoints, nPoints, false));
  }

  /**
   * Draw a polygon
   * 
   * @param xPoints
   *          the x-points
   * @param yPoints
   *          the y-points
   * @param nPoints
   *          the number of points
   */
  public void drawPolygon(final double xPoints[], final double yPoints[],
      final int nPoints) {
    this.draw(this.createShape(xPoints, yPoints, nPoints, true));
  }

  /**
   * Draw a polygon
   * 
   * @param xPoints
   *          the x-points
   * @param yPoints
   *          the y-points
   * @param nPoints
   *          the number of points
   */
  public void fillPolygon(final double xPoints[], final double yPoints[],
      final int nPoints) {
    this.fill(this.createShape(xPoints, yPoints, nPoints, true));
  }

  /**
   * draw the given characters
   * 
   * @param data
   *          the character data
   * @param offset
   *          the offset
   * @param length
   *          the length
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  public void drawChars(final char data[], final int offset,
      final int length, final double x, final double y) {
    this.drawChars(data, offset, length, __x(x), __y(y));
  }

  /**
   * draw an image
   * 
   * @param img
   *          the image
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param observer
   *          the observer
   * @return the result
   */
  public boolean drawImage(final Image img, final double x,
      final double y, final ImageObserver observer) {
    final AffineTransform translate;
    final boolean b;

    translate = this.getTransform();
    try {
      this.translate(x, y);
      b = this.drawImage(img, 0, 0, observer);
    } finally {
      this.setTransform(translate);
    }
    return b;
  }

  /**
   * Create an affine transform for scaling an image
   * 
   * @param image
   *          the image
   * @param width
   *          the width
   * @param height
   *          the height
   * @param observer
   *          the observer
   * @return the transform
   */
  private final AffineTransform __scale(final Image image,
      final double width, final double height, final ImageObserver observer) {
    final int w, h;
    final AffineTransform t;

    w = image.getWidth(observer);
    h = image.getHeight(observer);
    if ((w <= 0) || (h <= 0)) {
      throw new IllegalArgumentException(//
          "Image not fully loaded or empty."); //$NON-NLS-1$
    }

    t = new AffineTransform();
    if ((w != width) || (h != height)) {
      t.scale((width / w), (height / h));
    }
    return t;
  }

  /**
   * draw an image
   * 
   * @param img
   *          the image
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param observer
   *          the observer
   * @return the result
   */
  public boolean drawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final ImageObserver observer) {
    final AffineTransform translate;
    final boolean b;

    translate = this.getTransform();
    try {
      this.translate(x, y);
      b = this.drawImage(img, __scale(img, width, height, observer),
          observer);
    } finally {
      this.setTransform(translate);
    }
    return b;
  }

  /**
   * draw an image
   * 
   * @param img
   *          the image
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param observer
   *          the observer
   * @param bgcolor
   *          the background color
   * @return the result
   */
  public boolean drawImage(final Image img, final double x,
      final double y, final Color bgcolor, final ImageObserver observer) {
    final AffineTransform translate;
    final boolean b;

    translate = this.getTransform();
    try {
      this.translate(x, y);
      b = this.drawImage(img, 0, 0, bgcolor, observer);
    } finally {
      this.setTransform(translate);
    }
    return b;
  }

  /**
   * draw an image
   * 
   * @param img
   *          the image
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param bgcolor
   *          the background color
   * @param observer
   *          the observer
   * @return the result
   */
  public boolean drawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final Color bgcolor, final ImageObserver observer) {
    final Paint p;

    p = this.getPaint();
    this.setPaint(bgcolor);
    try {
      this.fillRect(x, y, width, height);
    } finally {
      this.setPaint(p);
    }

    return this.drawImage(img, x, y, width, height, observer);
  }

  /**
   * TODO Draw an image
   * 
   * @param img
   *          the specified image to be drawn. This method does nothing if
   *          {@code img} is null.
   * @param dx1
   *          the x coordinate of the first corner of the destination
   *          rectangle.
   * @param dy1
   *          the y coordinate of the first corner of the destination
   *          rectangle.
   * @param dx2
   *          the x coordinate of the second corner of the destination
   *          rectangle.
   * @param dy2
   *          the y coordinate of the second corner of the destination
   *          rectangle.
   * @param sx1
   *          the x coordinate of the first corner of the source rectangle.
   * @param sy1
   *          the y coordinate of the first corner of the source rectangle.
   * @param sx2
   *          the x coordinate of the second corner of the source
   *          rectangle.
   * @param sy2
   *          the y coordinate of the second corner of the source
   *          rectangle.
   * @param observer
   *          object to be notified as more of the image is scaled and
   *          converted.
   * @return {@code false} if the image pixels are still changing;
   *         {@code true} otherwise.
   */
  public boolean drawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final ImageObserver observer) {
    return this.drawImage(img, __x(dx1), __y(dy1), __x(dx2), __y(dy2),
        __x(sx1), __y(sy1), __x(sx2), __y(sy2), observer);
  }

  /**
   * TODO Draw an image
   * 
   * @param img
   *          the specified image to be drawn. This method does nothing if
   *          {@code img} is null.
   * @param dx1
   *          the x coordinate of the first corner of the destination
   *          rectangle.
   * @param dy1
   *          the y coordinate of the first corner of the destination
   *          rectangle.
   * @param dx2
   *          the x coordinate of the second corner of the destination
   *          rectangle.
   * @param dy2
   *          the y coordinate of the second corner of the destination
   *          rectangle.
   * @param sx1
   *          the x coordinate of the first corner of the source rectangle.
   * @param sy1
   *          the y coordinate of the first corner of the source rectangle.
   * @param sx2
   *          the x coordinate of the second corner of the source
   *          rectangle.
   * @param sy2
   *          the y coordinate of the second corner of the source
   *          rectangle.
   * @param observer
   *          object to be notified as more of the image is scaled and
   *          converted.
   * @param bgcolor
   *          the background color
   * @return {@code false} if the image pixels are still changing;
   *         {@code true} otherwise.
   */
  public boolean drawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final Color bgcolor, final ImageObserver observer) {
    return this.drawImage(img, __x(dx1), __y(dy1), __x(dx2), __y(dy2),
        __x(sx1), __y(sy1), __x(sx2), __y(sy2), bgcolor, observer);
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
      minFont = new Font(name, style, height).deriveFont(FONT_ATTRIBUTES);
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
        maxFont = new Font(name, style, (++height))
            .deriveFont(FONT_ATTRIBUTES);
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
