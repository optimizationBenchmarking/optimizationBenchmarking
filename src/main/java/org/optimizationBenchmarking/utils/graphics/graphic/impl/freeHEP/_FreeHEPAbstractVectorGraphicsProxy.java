package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.nio.file.Path;
import java.text.AttributedCharacterIterator;
import java.util.logging.Logger;

import org.freehep.graphics2d.AbstractVectorGraphics;
import org.optimizationBenchmarking.utils.graphics.FontProperties;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.SimplifyingGraphicProxy;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * <p>
 * an internal base class for <a
 * href="http://java.freehep.org/vectorgraphics">FreeHEP</a>-based vector
 * graphics drivers
 * </p>
 * <p>
 * Create _f-protected shape, affine transform, and polygon classes.
 * </p>
 *
 * @param <T>
 *          the proxied type
 */
abstract class _FreeHEPAbstractVectorGraphicsProxy<T extends AbstractVectorGraphics>
    extends SimplifyingGraphicProxy<T> {

  /** the maximum permissible coordinate as integer */
  private static final int MAX_COORD_I = ((((1 << 22) - 3) >> 1) - 3);
  /** the minimum permissible coordinate as integer */
  private static final int MIN_COORD_I = (-_FreeHEPAbstractVectorGraphicsProxy.MAX_COORD_I);
  /** the maximum permissible coordinate as float */
  private static final float MAX_COORD_F = _FreeHEPAbstractVectorGraphicsProxy.MAX_COORD_I;
  /** the minimum permissible coordinate as float */
  private static final float MIN_COORD_F = _FreeHEPAbstractVectorGraphicsProxy.MIN_COORD_I;
  /** the maximum permissible coordinate as double */
  private static final float MAX_COORD_D = _FreeHEPAbstractVectorGraphicsProxy.MAX_COORD_I;
  /** the minimum permissible coordinate as double */
  private static final float MIN_COORD_D = _FreeHEPAbstractVectorGraphicsProxy.MIN_COORD_I;

  /** the width */
  final int m_w;

  /** the height */
  final int m_h;

  /** the underline state */
  private transient int m_underlineState;

  /**
   * instantiate
   *
   * @param graphic
   *          the graphic to use
   * @param log
   *          the logger
   * @param path
   *          the path under which the contents of the graphic are stored
   * @param listener
   *          the object to notify when we are closed, or {@code null} if
   *          none needs to be notified
   * @param w
   *          the width
   * @param h
   *          the height
   */
  _FreeHEPAbstractVectorGraphicsProxy(final T graphic, final Logger log,
      final IFileProducerListener listener, final Path path, final int w,
      final int h) {
    super(graphic, log, listener, path);
    this.m_w = w;
    this.m_h = h;
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean autoConvertCoordinatesToInt() {
    return false;
  }

  /**
   * format an {@code int}eger for output
   *
   * @param v
   *          the value
   * @return the formatted value
   */
  static final int _f(final int v) {
    return Math.max(_FreeHEPAbstractVectorGraphicsProxy.MIN_COORD_I,
        Math.min(_FreeHEPAbstractVectorGraphicsProxy.MAX_COORD_I, v));
  }

  /**
   * format a {@code float} for output
   *
   * @param v
   *          the value
   * @return the formatted value
   */
  static final float _f(final float v) {
    return Math.max(_FreeHEPAbstractVectorGraphicsProxy.MIN_COORD_F,
        Math.min(_FreeHEPAbstractVectorGraphicsProxy.MAX_COORD_F, v));
  }

  /**
   * format a {@code double} for output
   *
   * @param v
   *          the value
   * @return the formatted value
   */
  static final double _f(final double v) {
    return Math.max(_FreeHEPAbstractVectorGraphicsProxy.MIN_COORD_D,
        Math.min(_FreeHEPAbstractVectorGraphicsProxy.MAX_COORD_D, v));
  }

  /**
   * format an array of {@code doubles}
   *
   * @param v
   *          the array
   * @param nPoints
   *          the number of points
   * @return the new array
   */
  private static final double[] __f(final double[] v, final int nPoints) {
    double[] d;
    double o, z;
    int i;

    d = v;
    for (i = nPoints; (--i) >= 0;) {
      z = _FreeHEPAbstractVectorGraphicsProxy._f(o = d[i]);
      if (z != o) {
        if (d == v) {
          d = new double[nPoints];
          System.arraycopy(v, 0, d, 0, nPoints);
        }
        d[i] = z;
      }
    }

    return d;
  }

  /**
   * format an array of {@code int}s
   *
   * @param v
   *          the array
   * @param nPoints
   *          the number of points
   * @return the new array
   */
  private static final int[] __f(final int[] v, final int nPoints) {
    int[] d;
    int o, z;
    int i;

    d = v;
    for (i = nPoints; (--i) >= 0;) {
      z = _FreeHEPAbstractVectorGraphicsProxy._f(o = d[i]);
      if (z != o) {
        if (d == v) {
          d = new int[nPoints];
          System.arraycopy(v, 0, d, 0, nPoints);
        }
        d[i] = z;
      }
    }

    return d;
  }

  /** {@inheritDoc} */
  @Override
  public final Rectangle2D getBounds() {
    return new Rectangle(0, 0, this.m_w, this.m_h);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetFont(final Font font) {
    this.m_underlineState = 0;
    super.doSetFont(font);
  }

  // new functionality

  /** {@inheritDoc} */
  @Override
  protected final Graphics doCreate(final double x, final double y,
      final double width, final double height) {
    return this.m_out.create(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doClipRect(final double x, final double y,
      final double width, final double height) {
    this.m_out.clipRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetClip(final double x, final double y,
      final double width, final double height) {
    this.m_out.setClip(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void flushDrawLine(final double x1, final double y1,
      final double x2, final double y2) {
    this.m_out.drawLine(_FreeHEPAbstractVectorGraphicsProxy._f(x1),
        _FreeHEPAbstractVectorGraphicsProxy._f(y1),
        _FreeHEPAbstractVectorGraphicsProxy._f(x2),
        _FreeHEPAbstractVectorGraphicsProxy._f(y2));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillRect(final double x, final double y,
      final double width, final double height) {
    this.m_out.fillRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void flushDrawRect(final double x, final double y,
      final double width, final double height) {
    this.m_out.drawRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doClearRect(final double x, final double y,
      final double width, final double height) {
    this.m_out.clearRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    this.m_out.drawRoundRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcWidth),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcHeight));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    this.m_out.fillRoundRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcWidth),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcHeight));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawOval(final double x, final double y,
      final double width, final double height) {
    this.m_out.drawOval(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillOval(final double x, final double y,
      final double width, final double height) {
    this.m_out.fillOval(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawArc(final double x, final double y,
      final double width, final double height, final double startAngle,
      final double arcAngle) {
    this.m_out.drawArc(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(startAngle),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcAngle));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillArc(final double x, final double y,
      final double width, final double height, final double startAngle,
      final double arcAngle) {
    this.m_out.fillArc(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(startAngle),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcAngle));
  }

  /** {@inheritDoc} */
  @Override
  protected final void flushDrawPolyline(final double[] xPoints,
      final double[] yPoints, final int nPoints) {
    this.m_out
        .drawPolyline(
            _FreeHEPAbstractVectorGraphicsProxy.__f(xPoints, nPoints),
            _FreeHEPAbstractVectorGraphicsProxy.__f(yPoints, nPoints),
            nPoints);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillPolygon(final double[] xPoints,
      final double[] yPoints, final int nPoints) {
    this.m_out
        .fillPolygon(
            _FreeHEPAbstractVectorGraphicsProxy.__f(xPoints, nPoints),
            _FreeHEPAbstractVectorGraphicsProxy.__f(yPoints, nPoints),
            nPoints);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(final String str, final int x,
      final int y) {
    this.doDrawString(str,
        ((double) _FreeHEPAbstractVectorGraphicsProxy._f(x)),
        ((double) _FreeHEPAbstractVectorGraphicsProxy._f(y)));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(final String str, final float x,
      final float y) {
    this.doDrawString(str,
        ((double) _FreeHEPAbstractVectorGraphicsProxy._f(x)),
        ((double) _FreeHEPAbstractVectorGraphicsProxy._f(y)));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawChars(final char data[], final int offset,
      final int length, final double x, final double y) {
    this.doDrawString(new String(data, offset, length),
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(final String str, final double x,
      final double y) {
    final Font font;
    final AbstractVectorGraphics graph;
    final LineMetrics lineMetrics;
    final FontRenderContext frc;
    final Rectangle2D bounds;
    final Stroke old;
    double startX, yCoord;

    graph = this.m_out;
    font = graph.getFont();

    if (this.m_underlineState == 0) {
      this.m_underlineState = (FontProperties.isFontUnderlined(font) ? 1
          : 2);
    }

    if (this.m_underlineState == 1) {
      frc = graph.getFontRenderContext();

      bounds = font.getStringBounds(str, frc);
      startX = (bounds.getX() + x);

      lineMetrics = font.getLineMetrics(str, frc);
      yCoord = (y + lineMetrics.getUnderlineOffset());
      old = this.getStroke();
      try {
        this.setStroke(new BasicStroke(
            lineMetrics.getUnderlineThickness(), BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND, 10f));

        graph.drawLine(
            _FreeHEPAbstractVectorGraphicsProxy._f(startX),
            _FreeHEPAbstractVectorGraphicsProxy._f(yCoord),
            _FreeHEPAbstractVectorGraphicsProxy._f(startX
                + bounds.getWidth()),
            _FreeHEPAbstractVectorGraphicsProxy._f(yCoord));
      } finally {
        this.setStroke(old);
      }
    }
    graph.drawString(str, _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(
      final AttributedCharacterIterator iterator, final float x,
      final float y) {
    final Font font;
    final AbstractVectorGraphics graph;
    final LineMetrics lineMetrics;
    final FontRenderContext frc;
    final Rectangle2D bounds;
    final Stroke old;
    double startX, yCoord;

    graph = this.m_out;
    font = graph.getFont();

    if (this.m_underlineState == 0) {
      this.m_underlineState = (FontProperties.isFontUnderlined(font) ? 1
          : 2);
    }

    if (this.m_underlineState == 1) {
      frc = graph.getFontRenderContext();

      bounds = font.getStringBounds(iterator, iterator.getBeginIndex(),
          iterator.getEndIndex(), frc);
      startX = (bounds.getX() + x);

      lineMetrics = font.getLineMetrics(iterator,
          iterator.getBeginIndex(), iterator.getEndIndex(), frc);
      yCoord = (y + lineMetrics.getUnderlineOffset());
      old = this.getStroke();
      try {
        this.setStroke(new BasicStroke(
            lineMetrics.getUnderlineThickness(), BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND, 10f));

        graph.drawLine(
            _FreeHEPAbstractVectorGraphicsProxy._f(startX),
            _FreeHEPAbstractVectorGraphicsProxy._f(yCoord),
            _FreeHEPAbstractVectorGraphicsProxy._f(startX
                + bounds.getWidth()),
            _FreeHEPAbstractVectorGraphicsProxy._f(yCoord));
      } finally {
        this.setStroke(old);
      }
    }
    graph.drawString(iterator, _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(
      final AttributedCharacterIterator iterator, final int x, final int y) {
    this.drawString(iterator,
        ((float) _FreeHEPAbstractVectorGraphicsProxy._f(x)),
        ((float) _FreeHEPAbstractVectorGraphicsProxy._f(y)));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawChars(final char[] data, final int offset,
      final int length, final int x, final int y) {
    this.drawChars(data, offset, length, ((double) x), ((double) y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDraw3DRect(final int x, final int y,
      final int width, final int height, final boolean raised) {
    this.m_out.draw3DRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), raised);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFill3DRect(final int x, final int y,
      final int width, final int height, final boolean raised) {
    this.m_out.fill3DRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), raised);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawImage(final BufferedImage img,
      final BufferedImageOp op, final int x, final int y) {
    this.m_out.drawImage(img, op,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doTranslate(final int x, final int y) {
    this.m_out.translate(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doTranslate(final double tx, final double ty) {
    this.m_out.translate(_FreeHEPAbstractVectorGraphicsProxy._f(tx),
        _FreeHEPAbstractVectorGraphicsProxy._f(ty));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doRotate(final double theta) {
    this.m_out.rotate(_FreeHEPAbstractVectorGraphicsProxy._f(theta));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doRotate(final double theta, final double x,
      final double y) {
    this.m_out.rotate(_FreeHEPAbstractVectorGraphicsProxy._f(theta),
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doScale(final double sx, final double sy) {
    this.m_out.scale(_FreeHEPAbstractVectorGraphicsProxy._f(sx),
        _FreeHEPAbstractVectorGraphicsProxy._f(sy));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doShear(final double shx, final double shy) {
    this.m_out.shear(_FreeHEPAbstractVectorGraphicsProxy._f(shx),
        _FreeHEPAbstractVectorGraphicsProxy._f(shy));
  }

  /** {@inheritDoc} */
  @Override
  protected final Graphics doCreate(final int x, final int y,
      final int width, final int height) {
    return this.m_out.create(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doClipRect(final int x, final int y,
      final int width, final int height) {
    this.m_out.clipRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetClip(final int x, final int y,
      final int width, final int height) {
    this.m_out.setClip(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doCopyArea(final int x, final int y,
      final int width, final int height, final int dx, final int dy) {
    this.m_out.copyArea(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(dx),
        _FreeHEPAbstractVectorGraphicsProxy._f(dy));
  }

  /** {@inheritDoc} */
  @Override
  protected final void flushDrawLine(final int x1, final int y1,
      final int x2, final int y2) {
    this.m_out.drawLine(_FreeHEPAbstractVectorGraphicsProxy._f(x1),
        _FreeHEPAbstractVectorGraphicsProxy._f(y1),
        _FreeHEPAbstractVectorGraphicsProxy._f(x2),
        _FreeHEPAbstractVectorGraphicsProxy._f(y2));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillRect(final int x, final int y,
      final int width, final int height) {
    this.m_out.fillRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void flushDrawRect(final int x, final int y,
      final int width, final int height) {
    this.m_out.drawRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doClearRect(final int x, final int y,
      final int width, final int height) {
    this.m_out.clearRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    this.m_out.drawRoundRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcWidth),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcHeight));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    this.m_out.fillRoundRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcWidth),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcHeight));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawOval(final int x, final int y,
      final int width, final int height) {
    this.m_out.drawOval(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillOval(final int x, final int y,
      final int width, final int height) {
    this.m_out.fillOval(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawArc(final int x, final int y,
      final int width, final int height, final int startAngle,
      final int arcAngle) {
    this.m_out.drawArc(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(startAngle),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcAngle));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillArc(final int x, final int y,
      final int width, final int height, final int startAngle,
      final int arcAngle) {
    this.m_out.fillArc(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(startAngle),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcAngle));
  }

  /** {@inheritDoc} */
  @Override
  protected final void flushDrawPolyline(final int xPoints[],
      final int yPoints[], final int nPoints) {
    this.m_out
        .drawPolyline(
            _FreeHEPAbstractVectorGraphicsProxy.__f(xPoints, nPoints),
            _FreeHEPAbstractVectorGraphicsProxy.__f(yPoints, nPoints),
            nPoints);
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final int x,
      final int y, final ImageObserver observer) {
    return this.m_out.drawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y), observer);
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final int x,
      final int y, final int width, final int height,
      final ImageObserver observer) {
    return this.m_out.drawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), observer);
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final int x,
      final int y, final Color bgcolor, final ImageObserver observer) {
    return this.m_out.drawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y), bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final int x,
      final int y, final int width, final int height, final Color bgcolor,
      final ImageObserver observer) {
    return this.m_out.drawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2,
      final ImageObserver observer) {
    return this.m_out.drawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(dx1),
        _FreeHEPAbstractVectorGraphicsProxy._f(dy1),
        _FreeHEPAbstractVectorGraphicsProxy._f(dx2),
        _FreeHEPAbstractVectorGraphicsProxy._f(dy2),
        _FreeHEPAbstractVectorGraphicsProxy._f(sx1),
        _FreeHEPAbstractVectorGraphicsProxy._f(sy1),
        _FreeHEPAbstractVectorGraphicsProxy._f(sx2),
        _FreeHEPAbstractVectorGraphicsProxy._f(sy2), observer);
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2, final Color bgcolor,
      final ImageObserver observer) {
    return this.m_out.drawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(dx1),
        _FreeHEPAbstractVectorGraphicsProxy._f(dy1),
        _FreeHEPAbstractVectorGraphicsProxy._f(dx2),
        _FreeHEPAbstractVectorGraphicsProxy._f(dy2),
        _FreeHEPAbstractVectorGraphicsProxy._f(sx1),
        _FreeHEPAbstractVectorGraphicsProxy._f(sy1),
        _FreeHEPAbstractVectorGraphicsProxy._f(sx2),
        _FreeHEPAbstractVectorGraphicsProxy._f(sy2), bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hitClip(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    return this.m_out.hitClip(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  // new functionality

  /** {@inheritDoc} */
  @Override
  protected final void doDraw3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    super.doDraw3DRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), raised);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFill3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    super.doFill3DRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), raised);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawImage(final BufferedImage img,
      final BufferedImageOp op, final double x, final double y) {
    super.doDrawImage(img, op, _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(
      final AttributedCharacterIterator iterator, final double x,
      final double y) {
    super.doDrawString(iterator,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawGlyphVector(final GlyphVector g,
      final double x, final double y) {
    super.doDrawGlyphVector(g, _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doCopyArea(final double x, final double y,
      final double width, final double height, final double dx,
      final double dy) {
    super.doCopyArea(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(dx),
        _FreeHEPAbstractVectorGraphicsProxy._f(dy));
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final double x,
      final double y, final ImageObserver observer) {
    return super.doDrawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y), observer);
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final ImageObserver observer) {
    return super.doDrawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), observer);
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final double x,
      final double y, final Color bgcolor, final ImageObserver observer) {
    return super.doDrawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y), bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final Color bgcolor, final ImageObserver observer) {
    return super.doDrawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final ImageObserver observer) {
    return super.doDrawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(dx1),
        _FreeHEPAbstractVectorGraphicsProxy._f(dy1),
        _FreeHEPAbstractVectorGraphicsProxy._f(dx2),
        _FreeHEPAbstractVectorGraphicsProxy._f(dy2),
        _FreeHEPAbstractVectorGraphicsProxy._f(sx1),
        _FreeHEPAbstractVectorGraphicsProxy._f(sy1),
        _FreeHEPAbstractVectorGraphicsProxy._f(sx2),
        _FreeHEPAbstractVectorGraphicsProxy._f(sy2), observer);
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final Color bgcolor, final ImageObserver observer) {
    return super.doDrawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(dx1),
        _FreeHEPAbstractVectorGraphicsProxy._f(dy1),
        _FreeHEPAbstractVectorGraphicsProxy._f(dx2),
        _FreeHEPAbstractVectorGraphicsProxy._f(dy2),
        _FreeHEPAbstractVectorGraphicsProxy._f(sx1),
        _FreeHEPAbstractVectorGraphicsProxy._f(sy1),
        _FreeHEPAbstractVectorGraphicsProxy._f(sx2),
        _FreeHEPAbstractVectorGraphicsProxy._f(sy2), bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  public final Shape createShape(final double[] xPoints,
      final double[] yPoints, final int nPoints, final boolean close) {
    final GeneralPath path;

    this.checkClosed();
    path = new GeneralPath(Path2D.WIND_EVEN_ODD);
    if (nPoints > 0) {
      path.moveTo(
          _FreeHEPAbstractVectorGraphicsProxy._f((float) (xPoints[0])),
          _FreeHEPAbstractVectorGraphicsProxy._f((float) (yPoints[0])));
      for (int i = 1; i < nPoints; i++) {
        path.lineTo(
            _FreeHEPAbstractVectorGraphicsProxy._f((float) (xPoints[i])),
            _FreeHEPAbstractVectorGraphicsProxy._f((float) (yPoints[i])));
      }
      if (close) {
        path.closePath();
      }
    }
    return path;
  }

  /** {@inheritDoc} */
  @Override
  protected final void flushDraw(final Shape s) {
    this.m_out.draw(new _ProtectedShape(s));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFill(final Shape s) {
    this.m_out.fill(new _ProtectedShape(s));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doClip(final Shape s) {
    this.m_out.clip(new _ProtectedShape(s));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetClip(final Shape clip) {
    this.m_out.setClip(new _ProtectedShape(clip));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillPolygon(final Polygon p) {
    this.m_out.fillPolygon(new Polygon(_FreeHEPAbstractVectorGraphicsProxy
        .__f(p.xpoints, p.npoints), _FreeHEPAbstractVectorGraphicsProxy
        .__f(p.ypoints, p.npoints), p.npoints));
  }
}
