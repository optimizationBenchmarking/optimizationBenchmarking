package org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * <p>
 * This is a
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicProxy}
 * which tries to simplify the commands passed to the underlying graphic.
 * For instance, if multiple simple lines are drawn on it, it tries to
 * merge them to a poly-line. Also, if several points on a line are on the
 * same straight, this class will try to remove redundant points in the
 * middle.This costs a significant amount of memory and runtime and is
 * entirely useless if the graphic renders a pixel graphic. However, if the
 * graphic renders to a vector graphic, the result may be much more
 * efficient. The following methods support caching:
 * </p>
 * <ol>
 * <li>{@link #drawLine(double, double, double, double)}</li>
 * <li>{@link #drawLine(int, int, int, int)}</li>
 * <li>{@link #drawRect(double, double, double, double)}</li>
 * <li>{@link #drawRect(int, int, int, int)}</li>
 * <li>{@link #draw(Shape)}</li>
 * <li>{@link #drawPolygon(Polygon)}</li>
 * <li>{@link #drawPolygon(double[], double[], int)}</li>
 * <li>{@link #drawPolygon(int[], int[], int)}</li>
 * <li>{@link #drawPolyline(double[], double[], int)}</li>
 * <li>{@link #drawPolyline(int[], int[], int)}</li>
 * </ol>
 *
 * @param <GT>
 *          the wrapped graphics type
 */
public abstract class SimplifyingGraphicProxy<GT extends Graphics2D>
    extends GraphicProxy<GT> {

  /** the maximum allowed coordinate: {@value} */
  private static final double MAX_COORD = (Double.MAX_VALUE * 0.5d);
  /** the minimum allowed coordinate: {@value} */
  private static final double MIN_COORD = (-SimplifyingGraphicProxy.MAX_COORD);
  /** the ulp factor for considering points to be on a line */
  private static final double ULP_FACTOR = 10d;

  /** when we need to flush */
  private static final int NEEDS_FLUSH_LINES = (Graphic.BEFORE_CLEAR
      | Graphic.BEFORE_IMAGE | Graphic.BEFORE_3D_RECT
      | Graphic.BEFORE_CREATE_GRAPHIC | Graphic.BEFORE_COPY_AREA
      | Graphic.BEFORE_CHANGE_COMPOSITE | Graphic.BEFORE_CHANGE_PAINT
      | Graphic.BEFORE_CHANGE_COLOR
      | Graphic.BEFORE_CHANGE_BACKGROUND_COLOR
      | Graphic.BEFORE_CHANGE_STROKE
      | Graphic.BEFORE_CHANGE_RENDERING_HINTS
      | Graphic.BEFORE_CHANGE_TRANSFORMATION | Graphic.BEFORE_CHANGE_CLIP | Graphic.BEFORE_CHANGE_PAINT_MODE);

  /** the segment list */
  private final LinkedHashSet<__LineSegment> m_lineSegments;

  /** the working list */
  private final ArrayList<__LineSegment> m_lineWork;

  /** the poly line x-cache for int */
  private int[] m_polyIntX;
  /** the poly line y-cache for int */
  private int[] m_polyIntY;
  /** the poly line x-cache for double */
  private double[] m_polyDoubleX;
  /** the poly line y-cache for double */
  private double[] m_polyDoubleY;

  /**
   * instantiate
   *
   * @param graphic
   *          the graphic to use
   * @param log
   *          the logger
   * @param path
   *          the path to be managed
   * @param listener
   *          the listener to notify
   */
  protected SimplifyingGraphicProxy(final GT graphic, final Logger log,
      final IFileProducerListener listener, final Path path) {
    super(graphic, log, listener, path);
    this.m_lineSegments = new LinkedHashSet<>();
    this.m_lineWork = new ArrayList<>();

    this.m_polyDoubleX = new double[128];
    this.m_polyDoubleY = new double[128];
    if (this.autoConvertCoordinatesToInt()) {
      this.m_polyIntX = new int[128];
      this.m_polyIntY = new int[128];
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void before(final int what) {
    this.checkClosed();
    if ((what & SimplifyingGraphicProxy.NEEDS_FLUSH_LINES) != 0) {
      this.__flushLineCache();
    }
  }

  /**
   * format a {@code double} value for caching in a line cache
   *
   * @param d
   *          the double value
   * @return the formatted result
   */
  static final double __format(final double d) {
    if (d <= SimplifyingGraphicProxy.MIN_COORD) {
      return SimplifyingGraphicProxy.MIN_COORD;
    }
    if (d >= SimplifyingGraphicProxy.MAX_COORD) {
      return SimplifyingGraphicProxy.MAX_COORD;
    }
    if (d == 0d) {
      return 0d;
    }
    return d;
  }

  // The following methods try to cache line segments drawn to the graphic.
  // The line segments will then be merged, simplified, and flushed later.

  /** {@inheritDoc} */
  @Override
  protected final void doDrawLine(final double x1, final double y1,
      final double x2, final double y2) {
    this.m_lineSegments.add(new __LineSegment(//
        SimplifyingGraphicProxy.__format(x1),//
        SimplifyingGraphicProxy.__format(y1),//
        SimplifyingGraphicProxy.__format(x2),//
        SimplifyingGraphicProxy.__format(y2)));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawLine(final int x1, final int y1,
      final int x2, final int y2) {
    this.m_lineSegments.add(new __LineSegment(x1, y1, x2, y2));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawPolyline(final double[] xPoints,
      final double[] yPoints, final int nPoints) {
    int i;
    double x1, y1, x2, y2;

    if (nPoints > 0) {
      x2 = xPoints[0];
      y2 = yPoints[0];
      if (nPoints > 1) {
        for (i = 1; i < nPoints; i++) {
          x1 = x2;
          y1 = y2;
          x2 = xPoints[i];
          y2 = yPoints[i];
          if ((x1 != x2) || (y1 != y2) || (nPoints == 2)) {
            this.doDrawLine(x1, y1, x2, y2);
          }
        }
      } else {
        this.doDrawLine(x2, y2, x2, y2);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawPolyline(final int xPoints[],
      final int yPoints[], final int nPoints) {
    int i, x1, y1, x2, y2;

    if (nPoints > 0) {
      x2 = xPoints[0];
      y2 = yPoints[0];
      if (nPoints > 1) {
        for (i = 1; i < nPoints; i++) {
          x1 = x2;
          y1 = y2;
          x2 = xPoints[i];
          y2 = yPoints[i];
          if ((x1 != x2) || (y1 != y2) || (nPoints == 2)) {
            this.doDrawLine(x1, y1, x2, y2);
          }
        }
      } else {
        this.doDrawLine(x2, y2, x2, y2);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRect(final double x, final double y,
      final double width, final double height) {
    final double x2, y2;

    x2 = (x + width);
    y2 = (y + width);
    this.doDrawLine(x, y, x2, y);
    this.doDrawLine(x2, y, x2, y2);
    this.doDrawLine(x2, y2, x, y2);
    this.doDrawLine(x, y2, x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRect(final int x, final int y,
      final int width, final int height) {
    final int x2, y2;

    x2 = (x + width);
    y2 = (y + width);
    this.doDrawLine(x, y, x2, y);
    this.doDrawLine(x2, y, x2, y2);
    this.doDrawLine(x2, y2, x, y2);
    this.doDrawLine(x, y2, x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawPolygon(final int[] xPoints,
      final int[] yPoints, final int nPoints) {
    int i, x1, y1, x2, y2;

    if (nPoints > 0) {
      x2 = xPoints[0];
      y2 = yPoints[0];
      if (nPoints > 1) {
        for (i = 1; i < nPoints; i++) {
          x1 = x2;
          y1 = y2;
          x2 = xPoints[i];
          y2 = yPoints[i];
          if ((x1 != x2) || (y1 != y2) || (nPoints == 2)) {
            this.doDrawLine(x1, y1, x2, y2);
          }
        }

        x1 = x2;
        y1 = y2;
        x2 = xPoints[0];
        y2 = yPoints[0];
        if ((x1 != x2) || (y1 != y2)) {
          this.doDrawLine(x1, y1, x2, y2);
        }

      } else {
        this.doDrawLine(x2, y2, x2, y2);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawPolygon(final Polygon p) {
    this.doDrawPolygon(p.xpoints, p.ypoints, p.npoints);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawPolygon(final double[] xPoints,
      final double[] yPoints, final int nPoints) {
    int i;
    double x1, y1, x2, y2;

    if (nPoints > 0) {
      x2 = xPoints[0];
      y2 = yPoints[0];
      if (nPoints > 1) {
        for (i = 1; i < nPoints; i++) {
          x1 = x2;
          y1 = y2;
          x2 = xPoints[i];
          y2 = yPoints[i];
          if ((x1 != x2) || (y1 != y2) || (nPoints == 2)) {
            this.doDrawLine(x1, y1, x2, y2);
          }
        }

        x1 = x2;
        y1 = y2;
        x2 = xPoints[0];
        y2 = yPoints[0];
        if ((x1 != x2) || (y1 != y2)) {
          this.doDrawLine(x1, y1, x2, y2);
        }

      } else {
        this.doDrawLine(x2, y2, x2, y2);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDraw(final Shape s) {
    final Rectangle2D rect;
    final Line2D line;
    final Rectangle2D rectangle;
    final Point2D point;
    final Polygon poly;
    final RoundRectangle2D round;
    final Ellipse2D ellipse;
    final Arc2D arc;
    final PathIterator iterator;
    final ArrayList<__LineSegment> list;
    final double[] coords;
    double oldX, oldY, curX, curY, firstX, firstY;
    boolean first;

    if (s instanceof Line2D) {
      line = ((Line2D) s);
      this.doDrawLine(line.getX1(), line.getY1(), line.getX2(),
          line.getY2());
      return;
    }

    if (s instanceof Rectangle2D) {
      rectangle = ((Rectangle2D) s);
      this.doDrawRect(rectangle.getX(), rectangle.getY(),
          rectangle.getWidth(), rectangle.getHeight());
      return;
    }

    if (s instanceof Polygon) {
      poly = ((Polygon) s);
      this.doDrawPolygon(poly.xpoints, poly.ypoints, poly.npoints);
      return;
    }

    if (s instanceof RoundRectangle2D) {
      round = ((RoundRectangle2D) s);
      this.doDrawRoundRect(round.getX(), round.getY(), round.getWidth(),
          round.getHeight(), round.getArcWidth(), round.getArcHeight());
      return;
    }

    if (s instanceof Arc2D) {
      arc = ((Arc2D) s);
      this.doDrawArc(arc.getX(), arc.getY(), arc.getWidth(),
          arc.getHeight(), arc.getAngleStart(), arc.getAngleExtent());
    }

    if (s instanceof Point2D) {
      point = ((Point2D) s);
      this.doDrawLine(point.getX(), point.getY(), point.getX(),
          point.getY());
      return;
    }

    if (s instanceof Ellipse2D) {
      ellipse = ((Ellipse2D) s);
      if ((ellipse.getWidth() == 0d) || (ellipse.getHeight() == 0d)) {
        this.doDrawLine(ellipse.getMinX(), ellipse.getMinY(),
            ellipse.getMaxX(), ellipse.getMaxY());
        return;
      }
    }

    rect = s.getBounds2D();
    if (rect.isEmpty()) {
      this.doDrawLine(rect.getMinX(), rect.getMinY(), rect.getMaxX(),
          rect.getMaxY());
      return;
    }

    iterator = s.getPathIterator(null);
    if (iterator != null) {
      coords = this.m_polyDoubleX;
      list = this.m_lineWork;
      firstX = firstY = curX = curY = 0d;
      first = true;

      canDo: {
        while (!(iterator.isDone())) {
          switch (iterator.currentSegment(coords)) {
            case PathIterator.SEG_MOVETO: {
              curX = coords[0];
              curY = coords[1];
              if (first) {
                firstX = curX;
                firstY = curY;
                first = false;
              }
              break;
            }
            case PathIterator.SEG_LINETO: {
              if (first) {
                break canDo;
              }
              oldX = curX;
              oldY = curY;
              curX = coords[0];
              curY = coords[1];
              list.add(new __LineSegment(oldX, oldY, curX, curY));
              break;
            }
            case PathIterator.SEG_CLOSE: {
              if (first) {
                break canDo;
              }
              list.add(new __LineSegment(curX, curY, firstX, firstY));
              break;
            }
            default: {
              break canDo;
            }
          }

          iterator.next();
        }

        this.m_lineSegments.addAll(list);
      }

      list.clear();
    }

    this.flushDraw(s);
  }

  /**
   * Actually draw a shape
   *
   * @param s
   *          the shape
   */
  protected void flushDraw(final Shape s) {
    super.doDraw(s);
  }

  /**
   * Flush the caches of this graphic. This method must be called in every
   * method which modifies the graphic. It will actually paint cached
   * primitives to the output graphic (after trying to simplify them).
   */
  private final void __flushLineCache() {
    final ArrayList<__LineSegment> work;
    final HashSet<__LineSegment> cache;
    Iterator<__LineSegment> iterator;
    __LineSegment current, first, next;
    boolean nothing;

    cache = this.m_lineSegments;
    work = this.m_lineWork;

    while (!(cache.isEmpty())) {
      iterator = cache.iterator();
      if (!(iterator.hasNext())) {
        return;
      }

      first = current = iterator.next();
      work.add(current);
      iterator.remove();

      finder: {
        for (;;) {
          nothing = true;

          while (iterator.hasNext()) {
            next = iterator.next();

            if (next._attachAfter(current)) {
              work.add(next);
              current = next;
              iterator.remove();
              nothing = false;
            }
          }

          if (nothing) {
            break;
          }
          if (cache.isEmpty()) {
            break finder;
          }
          iterator = cache.iterator();
        }

        current = first;
        for (;;) {
          nothing = true;
          iterator = cache.iterator();

          while (iterator.hasNext()) {
            next = iterator.next();

            if (next._attachBefore(current)) {
              work.add(0, next);
              current = next;
              iterator.remove();
              nothing = false;
            }
          }

          if (nothing) {
            break;
          }
          if (cache.isEmpty()) {
            break finder;
          }
        }

      }

      SimplifyingGraphicProxy.__simplifyLineSegments(work);
      this.__flushLineSegments(work);
    }
  }

  /**
   * Try to simplify a list of segments as much as possible by deleting
   * redundant segments
   *
   * @param work
   *          the list of segments
   */
  private static final void __simplifyLineSegments(
      final ArrayList<__LineSegment> work) {
    int size, index;
    __LineSegment before, after;
    double midX, midY, divA, divB, ulp;

    size = work.size();
    if (size > 1) {
      inner: for (index = (size - 1); index > 0; index--) {
        after = work.get(index);
        before = work.get(index - 1);

        // after.x1 == before.x2 and after.y1 == before.y2
        midX = after.x1;
        midY = after.y1;

        cannotMerge: {
          if ((before.x1 == midX) && (midX == after.x2)) {
            if (SimplifyingGraphicProxy.__between(before.y1, midY,
                after.y2)) {
              break cannotMerge;
            }
          }

          if ((before.y1 == midY) && (midY == after.y2)) {
            if (SimplifyingGraphicProxy.__between(before.x1, midX,
                after.x2)) {
              break cannotMerge;
            }
          }

          divA = ((after.x2 - before.x1) / (after.y2 - before.y1));
          divB = ((midX - before.x1) / (midY - before.y1));
          ulp = (SimplifyingGraphicProxy.ULP_FACTOR * Math.ulp(divB));
          if ((divA >= (divB - ulp)) && (divA <= (divB + ulp))) {
            break cannotMerge;
          }
          ulp = (SimplifyingGraphicProxy.ULP_FACTOR * Math.ulp(divA));
          if ((divB >= (divA - ulp)) && (divB <= (divA + ulp))) {
            break cannotMerge;
          }

          divA = ((after.y2 - before.y1) / (after.x2 - before.x1));
          divB = ((midY - before.y1) / (midX - before.x1));
          ulp = (SimplifyingGraphicProxy.ULP_FACTOR * Math.ulp(divB));
          if ((divA >= (divB - ulp)) && (divA <= (divB + ulp))) {
            break cannotMerge;
          }
          ulp = (SimplifyingGraphicProxy.ULP_FACTOR * Math.ulp(divA));
          if ((divB >= (divA - ulp)) && (divB <= (divA + ulp))) {
            break cannotMerge;
          }

          continue inner;
        }

        work.remove(index);
        before.x2 = after.x2;
        before.y2 = after.y2;
        size--;
      }
    }
  }

  /** do close */
  protected void doClose() {
    //
  }

  /** {@inheritDoc} */
  @Override
  protected final void onClose() {
    try {
      try {
        this.__flushLineCache();
      } finally {
        this.doClose();
      }
    } finally {
      super.onClose();
    }
  }

  /**
   * Check whether {@code b} is between {@code a} and {@code c}
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @param c
   *          the third number
   * @return {@code true} if {@code b} is between {@code a} and {@code c}
   */
  private static final boolean __between(final double a, final double b,
      final double c) {
    return (((a <= b) && (b <= c)) || ((c <= b) && (b <= a)));
  }

  /**
   * Flush an ordered list of connected segments
   *
   * @param work
   *          the list of segments
   */
  @SuppressWarnings("fallthrough")
  private final void __flushLineSegments(
      final ArrayList<__LineSegment> work) {
    __LineSegment a, b, c, d;

    switch (work.size()) {
      case 0: {
        return; // nothing to draw, quit
      }
      case 1: {// draw a single line segment
        a = work.get(0);
        this.__flushLine(a.x1, a.y1, a.x2, a.y2);
        break;
      }
      case 4: { // 4 line segments: could be a rectangle
        a = work.get(0);
        b = work.get(1);
        c = work.get(2);
        d = work.get(3);
        // If all lines are either horizontal or vertical and the end of
        // the fourth segment is the start of the first one, we have a
        // rectangle.
        if (((a.x1 == a.x2) || (a.y1 == a.y2)) && //
            ((b.x1 == b.x2) || (b.y1 == b.y2)) && //
            ((c.x1 == c.x2) || (c.y1 == c.y2)) && //
            ((d.x1 == d.x2) || (d.y1 == d.y2)) && //
            ((a.x1 == d.x2) || (a.y1 == d.y2))) {
          this.__flushRectangle(a, b, c, d);
          break;
        }
      }

      default: {
        this.__flushPolyLine(work);
      }
    }

    work.clear();
  }

  /**
   * Flush a single line
   *
   * @param x1
   *          the first x coordinate
   * @param y1
   *          the first y coordinate
   * @param x2
   *          the second x coordinate
   * @param y2
   *          the second y coordinate
   */
  private final void __flushLine(final double x1, final double y1,
      final double x2, final double y2) {
    int a, b, c, d;

    if (this.autoConvertCoordinatesToInt()) {
      a = ((int) x1);
      if ((x1 >= Integer.MIN_VALUE) && (x1 <= Integer.MAX_VALUE)
          && (a == x1)) {
        b = ((int) y1);
        if ((y1 >= Integer.MIN_VALUE) && (y1 <= Integer.MAX_VALUE)
            && (b == y1)) {
          c = ((int) x2);
          if ((x2 >= Integer.MIN_VALUE) && (x2 <= Integer.MAX_VALUE)
              && (c == x2)) {
            d = ((int) y2);
            if ((y2 >= Integer.MIN_VALUE) && (y2 <= Integer.MAX_VALUE)
                && (d == y2)) {
              this.flushDrawLine(a, b, c, d);
              return;
            }
          }
        }
      }
    }
    this.flushDrawLine(x1, y1, x2, y2);
  }

  /**
   * Flush a line
   *
   * @param x1
   *          the first x-coordinate
   * @param y1
   *          the first y-coordinate
   * @param x2
   *          the second x-coordinate
   * @param y2
   *          the second y-coordinate
   */
  protected void flushDrawLine(final int x1, final int y1, final int x2,
      final int y2) {
    super.doDrawLine(x1, y1, x2, y2);
  }

  /**
   * Flush a line
   *
   * @param x1
   *          the first x-coordinate
   * @param y1
   *          the first y-coordinate
   * @param x2
   *          the second x-coordinate
   * @param y2
   *          the second y-coordinate
   */
  protected void flushDrawLine(final double x1, final double y1,
      final double x2, final double y2) {
    super.doDrawLine(x1, y1, x2, y2);
  }

  /**
   * Flush a rectangle
   *
   * @param a
   *          the first segment
   * @param b
   *          the second segment
   * @param c
   *          the third segment
   * @param d
   *          the fourth segment
   */
  private final void __flushRectangle(final __LineSegment a,
      final __LineSegment b, final __LineSegment c, final __LineSegment d) {
    double minX, minY, maxX, maxY, width, height;
    int x, y, w, h;

    minX = maxX = a.x1;
    minY = maxY = a.y1;

    if (b.x1 < minX) {
      minX = b.x1;
    }
    if (b.x1 > maxX) {
      maxX = b.x1;
    }
    if (b.y1 < minY) {
      minY = b.y1;
    }
    if (b.y1 > maxY) {
      maxY = b.y1;
    }

    if (c.x1 < minX) {
      minX = c.x1;
    }
    if (c.x1 > maxX) {
      maxX = c.x1;
    }
    if (c.y1 < minY) {
      minY = c.y1;
    }
    if (c.y1 > maxY) {
      maxY = c.y1;
    }

    if (d.x1 < minX) {
      minX = d.x1;
    }
    if (d.x1 > maxX) {
      maxX = d.x1;
    }
    if (d.y1 < minY) {
      minY = d.y1;
    }
    if (d.y1 > maxY) {
      maxY = d.y1;
    }

    width = (maxX - minX);
    height = (maxY - minY);
    if ((width <= 0d) || (height <= 0d)) {
      this.__flushLine(minX, minY, maxX, maxY);
      return;
    }

    if (this.autoConvertCoordinatesToInt()) {
      x = ((int) minX);
      if ((minX >= Integer.MIN_VALUE) && (minX <= Integer.MAX_VALUE)
          && (x == minX)) {

        y = ((int) minY);
        if ((minY >= Integer.MIN_VALUE) && (minY <= Integer.MAX_VALUE)
            && (y == minY)) {

          w = ((int) width);
          if ((width >= Integer.MIN_VALUE) && (width <= Integer.MAX_VALUE)
              && (w == width)) {

            h = ((int) height);
            if ((height >= Integer.MIN_VALUE)
                && (height <= Integer.MAX_VALUE) && (h == height)) {

              this.flushDrawRect(x, y, w, h);
              return;
            }
          }
        }
      }
    }

    this.flushDrawRect(minX, minY, width, height);
  }

  /**
   * Flush drawing a rectangle
   *
   * @param x
   *          the first x-coordinate
   * @param y
   *          the first y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  protected void flushDrawRect(final int x, final int y, final int width,
      final int height) {
    super.doDrawRect(x, y, width, height);
  }

  /**
   * Flush drawing a rectangle
   *
   * @param x
   *          the first x-coordinate
   * @param y
   *          the first y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   */
  protected void flushDrawRect(final double x, final double y,
      final double width, final double height) {
    super.doDrawRect(x, y, width, height);
  }

  /**
   * Flush a poly line
   *
   * @param work
   *          the list of segments of the poly-line
   */
  private final void __flushPolyLine(final ArrayList<__LineSegment> work) {
    final int size;
    int[] polyIntX, polyIntY;
    double[] polyDoubleX, polyDoubleY;
    int index;
    double doubleX, doubleY;
    int intX, intY;
    boolean canInt;
    __LineSegment last;

    size = (work.size() + 1);
    polyDoubleX = this.m_polyDoubleX;

    canInt = this.autoConvertCoordinatesToInt();

    if ((polyDoubleX == null) || (polyDoubleX.length < size)) {
      if (canInt) {
        this.m_polyIntX = polyIntX = new int[size];
        this.m_polyIntY = polyIntY = new int[size];
      } else {
        polyIntX = polyIntY = null;
      }
      this.m_polyDoubleX = polyDoubleX = new double[size];
      this.m_polyDoubleY = polyDoubleY = new double[size];
    } else {
      polyIntY = this.m_polyIntY;
      polyIntX = this.m_polyIntX;
      polyDoubleX = this.m_polyDoubleX;
      polyDoubleY = this.m_polyDoubleY;
    }

    index = 0;
    for (final __LineSegment segment : work) {
      polyDoubleX[index] = doubleX = segment.x1;
      polyDoubleY[index] = doubleY = segment.y1;
      if (canInt) {
        polyIntX[index] = intX = ((int) doubleX);
        canInt &= ((doubleX >= Integer.MIN_VALUE)
            && (doubleX <= Integer.MAX_VALUE) && (intX == doubleX));
        polyIntY[index] = intY = ((int) doubleY);
        canInt &= ((doubleY >= Integer.MIN_VALUE)
            && (doubleY <= Integer.MAX_VALUE) && (intY == doubleY));
      }
      index++;
    }

    last = work.get(size - 2);
    polyDoubleX[index] = doubleX = last.x2;
    polyDoubleY[index] = doubleY = last.y2;
    if (canInt) {
      polyIntX[index] = intX = ((int) doubleX);
      canInt &= ((doubleX >= Integer.MIN_VALUE)
          && (doubleX <= Integer.MAX_VALUE) && (intX == doubleX));
      polyIntY[index] = intY = ((int) doubleY);
      canInt &= ((doubleY >= Integer.MIN_VALUE)
          && (doubleY <= Integer.MAX_VALUE) && (intY == doubleY));
    }

    if (canInt) {
      this.flushDrawPolyline(polyIntX, polyIntY, size);
    } else {
      this.flushDrawPolyline(polyDoubleX, polyDoubleY, size);
    }
  }

  /**
   * Flush drawing a poly line
   *
   * @param xPoints
   *          the x-coordinates
   * @param yPoints
   *          the y-coordinates
   * @param nPoints
   *          the number of points
   */
  protected void flushDrawPolyline(final int[] xPoints,
      final int[] yPoints, final int nPoints) {
    super.doDrawPolyline(xPoints, yPoints, nPoints);
  }

  /**
   * Flush drawing a poly line
   *
   * @param xPoints
   *          the x-coordinates
   * @param yPoints
   *          the y-coordinates
   * @param nPoints
   *          the number of points
   */
  protected void flushDrawPolyline(final double[] xPoints,
      final double[] yPoints, final int nPoints) {
    super.doDrawPolyline(xPoints, yPoints, nPoints);
  }

  /** a line segment as double */
  static final class __LineSegment extends Line2D.Double {

    /** the serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * Create the double segment
     *
     * @param _x1
     *          the start x
     * @param _y1
     *          the start y
     * @param _x2
     *          the end x
     * @param _y2
     *          the end y
     */
    __LineSegment(final double _x1, final double _y1, final double _x2,
        final double _y2) {
      super(_x1, _y1, _x2, _y2);
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      if ((this.x1 < this.x2)
          || ((this.x1 == this.x2) && (this.y1 < this.y2))) {
        return HashUtils.combineHashes(//
            HashUtils.combineHashes(//
                HashUtils.hashCode(this.x1),//
                HashUtils.hashCode(this.y1)),//
            HashUtils.combineHashes(//
                HashUtils.hashCode(this.x2),//
                HashUtils.hashCode(this.y2)));
      }
      return HashUtils.combineHashes(//
          HashUtils.combineHashes(//
              HashUtils.hashCode(this.x2),//
              HashUtils.hashCode(this.y2)),//
          HashUtils.combineHashes(//
              HashUtils.hashCode(this.x1),//
              HashUtils.hashCode(this.y1)));
    }

    /** {@inheritDoc} */
    @Override
    public final boolean equals(final Object o) {
      __LineSegment segment;
      double x1a, x1b, y1a, y1b, x2a, x2b, y2a, y2b;

      if (o == this) {
        return true;
      }

      if (o instanceof __LineSegment) {
        segment = ((__LineSegment) o);

        if ((this.x1 < this.x2)
            || ((this.x1 == this.x2) && (this.y1 < this.y2))) {
          x1a = this.x1;
          y1a = this.y1;
          x2a = this.x2;
          y2a = this.y2;
        } else {
          x1a = this.x2;
          y1a = this.y2;
          x2a = this.x1;
          y2a = this.y1;
        }

        if ((segment.x1 < segment.x2)
            || ((segment.x1 == segment.x2) && (segment.y1 < segment.y2))) {
          x1b = segment.x1;
          y1b = segment.y1;
          x2b = segment.x2;
          y2b = segment.y2;
        } else {
          x1b = segment.x2;
          y1b = segment.y2;
          x2b = segment.x1;
          y2b = segment.y1;
        }

        return ((x1a == x1b) && (y1a == y1b) && (x2a == x2b) && (y2a == y2b));
      }
      return false;
    }

    /**
     * Can we somehow attach this segment to x1/y1 of the other segment?
     *
     * @param other
     *          the other segment
     * @return {@code true} if we can
     */
    final boolean _attachBefore(final __LineSegment other) {
      double temp;

      if ((this.x2 == other.x1) && (this.y2 == other.y1)) {
        return true;
      }

      if ((this.x1 == other.x1) && (this.y1 == other.y1)) {
        temp = this.x1;
        this.x1 = this.x2;
        this.x2 = temp;
        temp = this.y1;
        this.y1 = this.y2;
        this.y2 = temp;
        return true;
      }

      return false;
    }

    /**
     * Can we somehow attach this segment to x2/y2 of the other segment?
     *
     * @param other
     *          the other segment
     * @return {@code true} if we can
     */
    final boolean _attachAfter(final __LineSegment other) {
      double temp;

      if ((this.x1 == other.x2) && (this.y1 == other.y2)) {
        return true;
      }

      if ((this.x2 == other.x2) && (this.y2 == other.y2)) {
        temp = this.x1;
        this.x1 = this.x2;
        this.x2 = temp;
        temp = this.y1;
        this.y1 = this.y2;
        this.y2 = temp;
        return true;
      }

      return false;
    }

    /** {@inheritDoc} */
    @Override
    public final String toString() {
      return (((((((("(" + this.x1) + //$NON-NLS-1$
      ',') + this.y1) + ',') + this.x2) + ',') + this.y2) + ')');
    }
  }
}
