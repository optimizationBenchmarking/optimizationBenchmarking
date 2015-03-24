package org.optimizationBenchmarking.utils.graphics.graphic.spec;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.io.Closeable;
import java.nio.file.Path;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;
import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/**
 * <p>
 * An abstract {@link java.awt.Graphics2D} implementation designed as
 * output context for graphics. Each graphic is set up so that 1 logical
 * unit equals
 * <code>1{@link org.optimizationBenchmarking.utils.math.units.ELength#POINT}</code>
 * .
 * </p>
 * <p>
 * A graphic can be implemented in form of a
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicProxy}
 * which forwards the calls to some library. The whole underlying
 * implementation becomes transparent if an implementation of
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver}
 * is used to create such wrappers.
 * </p>
 * <p>
 * Inspired by the <a
 * href="http://java.freehep.org/vectorgraphics">FreeHEP</a> library, this
 * class also provides {@code double}-precision routines which, by default,
 * try to reasonable map to the {@code int}-precision routines of
 * {@link java.awt.Graphics2D}. However, if an underlying device supports
 * high-precision output (like the
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP}
 * freeHEP drivers} do), these routines may map to something better.
 * </p>
 */
public abstract class Graphic extends Graphics2D implements Closeable,
    IToolJob {

  /** the font attributes */
  private static final Map<TextAttribute, Object> FONT_ATTRIBUTES;

  static {
    FONT_ATTRIBUTES = new HashMap<>();
    Graphic.FONT_ATTRIBUTES.put(TextAttribute.KERNING,
        TextAttribute.KERNING_ON);
    Graphic.FONT_ATTRIBUTES.put(TextAttribute.LIGATURES,
        TextAttribute.LIGATURES_ON);
  }

  /** has we been closed ? */
  private volatile boolean m_closed;

  /** the logger */
  private final Logger m_log;
  /**
   * the object to notify when we are closed, or {@code null} if none needs
   * to be notified
   */
  private final IFileProducerListener m_listener;

  /** the graphic path to which the graphic is written */
  protected final Path m_path;

  /**
   * instantiate
   * 
   * @param listener
   *          the object to notify when we are closed, or {@code null} if
   *          none needs to be notified
   * @param path
   *          the path associated with this object, or {@code null} if no
   *          file needs to be explicitly created
   * @param logger
   *          the logger
   */
  protected Graphic(final Logger logger,
      final IFileProducerListener listener, final Path path) {
    super();
    this.m_log = logger;
    this.m_path = path;
    this.m_listener = listener;
  }

  /** check the state of the graphic */
  protected final void checkClosed() {
    if (this.m_closed) {
      throw new IllegalStateException("Graphic has already been closed."); //$NON-NLS-1$
    }
  }

  /**
   * Obtain the bounds of this graphic in
   * {@link org.optimizationBenchmarking.utils.math.units.ELength#PT pt}
   * 
   * @return the bounds of this graphic
   */
  public Rectangle2D getBounds() {
    Rectangle2D r;
    Point2D.Double a, b;
    AffineTransform at;

    r = this.getClipBounds();
    if (r != null) {
      return r;
    }

    at = this.getTransform();
    try {
      at.invert();
    } catch (final Throwable t) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(((//
          "Error while inverting transform ") + at),//$NON-NLS-1$
          true, t);
    }

    r = this.getDeviceConfiguration().getBounds();
    a = new Point2D.Double(r.getMinX(), r.getMinY());

    b = new Point2D.Double();
    at.transform(a, b);

    a.x += r.getWidth();
    a.y += r.getHeight();
    at.transform(a, b);
    r.setFrameFromDiagonal(b, a);

    return r;
  }

  /**
   * Get the graphics format to which this graphic belongs
   * 
   * @return the graphics format to which this graphic belongs
   */
  public abstract EGraphicFormat getGraphicFormat();

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

  /**
   * get this graphic's id to a text output
   * 
   * @return the name
   */
  private final String __name() {
    return (((((((((TextUtils.className(this.getClass())) + //
    '#') + System.identityHashCode(this)) + ' ') + '(') + //
    this.getGraphicFormat()) + '@') + this.m_path) + ')');
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public synchronized final void close() {
    final ArrayListView v;
    String s;

    if (this.m_closed) {
      return;
    }
    this.m_closed = true;

    s = null;
    if ((this.m_log != null) && (this.m_log.isLoggable(Level.FINEST))) {
      s = this.__name();
      this.m_log.finest("Now closing " + s); //$NON-NLS-1$
    }

    try {
      this.onClose();

      if (this.m_listener != null) {
        if (this.m_path != null) {
          v = new ArrayListView<>(
              new ImmutableAssociation[] { new ImmutableAssociation(
                  this.m_path, this.getGraphicFormat()) });
        } else {
          v = ArraySetView.EMPTY_SET_VIEW;
        }

        this.m_listener.onFilesFinalized(v);
      }
    } catch (final Throwable t) {
      if (s == null) {
        s = this.__name();
      }

      ErrorUtils.logError(this.m_log, ("Error when closing " //$NON-NLS-1$
          + s), t, false, RethrowMode.AS_RUNTIME_EXCEPTION);
    }

    if ((this.m_log != null) && (this.m_log.isLoggable(Level.FINEST))) {
      if (s == null) {
        s = this.__name();
      }
      this.m_log.finest(s + " closed."); //$NON-NLS-1$
    }
  }

  // new functionality

  /**
   * convert a coordinate
   * 
   * @param d
   *          the coordinate
   * @return the integer version
   */
  static final int _c(final double d) {
    return ((int) (d + 0.5d));
  }

  /**
   * convert a length
   * 
   * @param d
   *          the coordinate
   * @return the integer version
   */
  static final int _l(final double d) {
    int r;

    r = Graphic._c(d);
    return ((r != 0) ? r : ((d < 0d) ? (-1) : 1));
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
    try (final TranslatedAndScaled t = new TranslatedAndScaled(x, y,
        width, height)) {
      this.draw3DRect(t.m_x, t.m_y, t.m_w, t.m_h, raised);
    }
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
    try (final TranslatedAndScaled t = new TranslatedAndScaled(x, y,
        width, height)) {
      this.fill3DRect(t.m_x, t.m_y, t.m_w, t.m_h, raised);
    }
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
    try (final Translated t = new Translated(x, y)) {
      this.drawImage(img, op, t.m_x, t.m_y);
    }
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
    try (final TranslatedAndScaled t = new TranslatedAndScaled(x, y,
        width, height)) {
      return this.create(t.m_x, t.m_y, t.m_w, t.m_h);
    }
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
    this.copyArea(Graphic._c(x), Graphic._c(y), Graphic._l(width),
        Graphic._l(height), Graphic._c(dx), Graphic._c(dy));
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
    try {
      this.setPaint(this.getBackground());
      this.fillRect(x, y, width, height);
    } finally {
      this.setPaint(p);
    }
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
  public Shape createShape(final double[] xPoints, final double[] yPoints,
      final int nPoints, final boolean close) {
    final GeneralPath path;

    path = new GeneralPath(Path2D.WIND_EVEN_ODD);
    if (nPoints > 0) {
      path.moveTo(((float) (xPoints[0])), ((float) (yPoints[0])));
      for (int i = 1; i < nPoints; i++) {
        path.lineTo(((float) (xPoints[i])), ((float) (yPoints[i])));
      }
      if (close) {
        path.closePath();
      }
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
    try (final Translated t = new Translated(x, y)) {
      this.drawChars(data, offset, length, t.m_x, t.m_y);
    }
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
    try (final Translated t = new Translated(x, y)) {
      return this.drawImage(img, t.m_x, t.m_y, observer);
    }
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
      b = this.drawImage(img, this.__scale(img, width, height, observer),
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
    try (final Translated t = new Translated(x, y)) {
      return this.drawImage(img, t.m_x, t.m_y, bgcolor, observer);
    }
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
    return this.drawImage(img, Graphic._c(dx1), Graphic._c(dy1),
        Graphic._c(dx2), Graphic._c(dy2), Graphic._c(sx1),
        Graphic._c(sy1), Graphic._c(sx2), Graphic._c(sy2), observer);
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
    return this.drawImage(img, Graphic._c(dx1), Graphic._c(dy1),
        Graphic._c(dx2), Graphic._c(dy2), Graphic._c(sx1),
        Graphic._c(sy1), Graphic._c(sx2), Graphic._c(sy2), bgcolor,
        observer);
  }

  /** a translated and scaled context */
  private final class Translated implements Closeable {
    /** the preserved original transform */
    private final AffineTransform m_orig;

    /** the x-coordinate to use */
    final int m_x;
    /** the y-coordinate to use */
    final int m_y;

    /**
     * Translate the coordinate system
     * 
     * @param x
     *          the translation along the x-axis
     * @param y
     *          the translation along the y-axis
     */
    Translated(final double x, final double y) {
      super();

      final int xx, yy;

      if ((x <= Integer.MIN_VALUE) || (x >= Integer.MAX_VALUE) || (x != x)) {
        throw new IllegalArgumentException("Invalid x-coordinate " + x); //$NON-NLS-1$
      }
      if ((y <= Integer.MIN_VALUE) || (y >= Integer.MAX_VALUE) || (y != y)) {
        throw new IllegalArgumentException("Invalid y-coordinate " + y); //$NON-NLS-1$
      }

      xx = Graphic._c(x);
      yy = Graphic._c(y);

      if ((xx == x) && (yy == y)) {
        this.m_orig = null;
        this.m_x = xx;
        this.m_y = yy;
      } else {
        this.m_orig = Graphic.this.getTransform();
        Graphic.this.translate(x, y);
        this.m_x = 0;
        this.m_y = 0;
      }
    }

    /** {@inheritDoc} */
    @Override
    public final void close() {
      if (this.m_orig != null) {
        Graphic.this.setTransform(this.m_orig);
      }
    }
  }

  /** a translated and scaled context */
  private final class TranslatedAndScaled implements Closeable {
    /** the preserved original transform */
    private final AffineTransform m_orig;

    /** the x-coordinate */
    final int m_x;
    /** the y-coordinate */
    final int m_y;

    /** the width */
    final int m_w;
    /** the height */
    final int m_h;

    /**
     * create
     * 
     * @param x
     *          the starting x-coordinate
     * @param y
     *          the starting y-coordinate
     * @param w
     *          the width
     * @param h
     *          the height
     */
    TranslatedAndScaled(final double x, final double y, final double w,
        final double h) {
      super();

      final int xx, yy;
      double sw, sh;
      int ww, hh;
      final boolean needsTranslate;
      boolean needsW, needsH, needsScale;

      if ((x <= Integer.MIN_VALUE) || (x >= Integer.MAX_VALUE) || (x != x)) {
        throw new IllegalArgumentException("Invalid x-coordinate " + x); //$NON-NLS-1$
      }
      if ((y <= Integer.MIN_VALUE) || (y >= Integer.MAX_VALUE) || (y != y)) {
        throw new IllegalArgumentException("Invalid y-coordinate " + y); //$NON-NLS-1$
      }
      if ((w <= Integer.MIN_VALUE) || (w >= Integer.MAX_VALUE) || (w != w)) {
        throw new IllegalArgumentException("Invalid width " + w); //$NON-NLS-1$
      }
      if ((h <= Integer.MIN_VALUE) || (h >= Integer.MAX_VALUE) || (h != h)) {
        throw new IllegalArgumentException("Invalid height " + h); //$NON-NLS-1$
      }

      xx = Graphic._c(x);
      yy = Graphic._c(y);

      needsTranslate = ((xx != x) || (yy != y));
      if (needsTranslate) {
        this.m_x = 0;
        this.m_y = 0;
      } else {
        this.m_x = xx;
        this.m_y = yy;
      }

      this.m_w = ww = Graphic._l(w);
      needsW = (ww != w);
      if (needsW) {
        sw = (w / ww);
      } else {
        sw = 1d;
      }

      this.m_h = hh = Graphic._l(h);
      needsH = (hh != h);
      if (needsH) {
        sh = (h / hh);
      } else {
        sh = 1d;
      }

      needsScale = false;
      if (needsW || needsH) {
        needsScale = ((Math.abs(sw / sh) - 1d) < 0.02d);
        if (!needsScale) {
          if (Math.abs(sw - 1d) < 0.01d) {
            needsScale = true;
          } else {
            sw = 1d;
          }
          if (Math.abs(sh - 1d) < 0.01d) {
            needsScale = true;
          } else {
            sh = 1d;
          }
        }
      }

      if (needsTranslate || needsScale) {
        this.m_orig = Graphic.this.getTransform();

        if (needsTranslate) {
          Graphic.this.translate(x, y);
        }
        if (needsScale) {
          Graphic.this.scale(sw, sh);
        }

      } else {
        this.m_orig = null;
      }

    }

    /** {@inheritDoc} */
    @Override
    public final void close() {
      if (this.m_orig != null) {
        Graphic.this.setTransform(this.m_orig);
      }
    }
  }

}
