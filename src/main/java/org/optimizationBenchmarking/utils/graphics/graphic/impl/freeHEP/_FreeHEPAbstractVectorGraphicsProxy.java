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

import org.freehep.graphics2d.AbstractVectorGraphics;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.FontProperties;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicProxy;

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
class _FreeHEPAbstractVectorGraphicsProxy<T extends AbstractVectorGraphics>
    extends GraphicProxy<T> {

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
  _FreeHEPAbstractVectorGraphicsProxy(final T graphic, final Path path,
      final IObjectListener listener, final int w, final int h) {
    super(graphic, path, listener);
    this.m_w = w;
    this.m_h = h;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isVectorGraphic() {
    return true;
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
  public final void setFont(final Font font) {
    this.m_underlineState = 0;
    super.setFont(font);
  }

  // new functionality

  /** {@inheritDoc} */
  @Override
  public final Graphics create(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    return this.m_out.create(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void clipRect(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    this.m_out.clipRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void setClip(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    this.m_out.setClip(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawLine(final double x1, final double y1,
      final double x2, final double y2) {
    this.checkClosed();
    this.m_out.drawLine(_FreeHEPAbstractVectorGraphicsProxy._f(x1),
        _FreeHEPAbstractVectorGraphicsProxy._f(y1),
        _FreeHEPAbstractVectorGraphicsProxy._f(x2),
        _FreeHEPAbstractVectorGraphicsProxy._f(y2));
  }

  /** {@inheritDoc} */
  @Override
  public final void fillRect(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    this.m_out.fillRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawRect(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    this.m_out.drawRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void clearRect(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    this.m_out.clearRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    this.checkClosed();
    this.m_out.drawRoundRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcWidth),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcHeight));
  }

  /** {@inheritDoc} */
  @Override
  public final void fillRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    this.checkClosed();
    this.m_out.fillRoundRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcWidth),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcHeight));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawOval(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    this.m_out.drawOval(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void fillOval(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    this.m_out.fillOval(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawArc(final double x, final double y,
      final double width, final double height, final double startAngle,
      final double arcAngle) {
    this.checkClosed();
    this.m_out.drawArc(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(startAngle),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcAngle));
  }

  /** {@inheritDoc} */
  @Override
  public final void fillArc(final double x, final double y,
      final double width, final double height, final double startAngle,
      final double arcAngle) {
    this.checkClosed();
    this.m_out.fillArc(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(startAngle),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcAngle));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawPolyline(final double[] xPoints,
      final double[] yPoints, final int nPoints) {
    this.checkClosed();
    this.m_out
        .drawPolyline(
            _FreeHEPAbstractVectorGraphicsProxy.__f(xPoints, nPoints),
            _FreeHEPAbstractVectorGraphicsProxy.__f(yPoints, nPoints),
            nPoints);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawPolygon(final double[] xPoints,
      final double[] yPoints, final int nPoints) {
    this.checkClosed();
    this.m_out
        .drawPolygon(
            _FreeHEPAbstractVectorGraphicsProxy.__f(xPoints, nPoints),
            _FreeHEPAbstractVectorGraphicsProxy.__f(yPoints, nPoints),
            nPoints);
  }

  /** {@inheritDoc} */
  @Override
  public final void fillPolygon(final double[] xPoints,
      final double[] yPoints, final int nPoints) {
    this.checkClosed();
    this.m_out
        .fillPolygon(
            _FreeHEPAbstractVectorGraphicsProxy.__f(xPoints, nPoints),
            _FreeHEPAbstractVectorGraphicsProxy.__f(yPoints, nPoints),
            nPoints);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawString(final String str, final int x, final int y) {
    this.drawString(str,
        ((double) _FreeHEPAbstractVectorGraphicsProxy._f(x)),
        ((double) _FreeHEPAbstractVectorGraphicsProxy._f(y)));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawString(final String str, final float x,
      final float y) {
    this.drawString(str,
        ((double) _FreeHEPAbstractVectorGraphicsProxy._f(x)),
        ((double) _FreeHEPAbstractVectorGraphicsProxy._f(y)));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawChars(final char data[], final int offset,
      final int length, final double x, final double y) {
    this.checkClosed();
    this.drawString(new String(data, offset, length),
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawString(final String str, final double x,
      final double y) {
    final Font font;
    final AbstractVectorGraphics graph;
    final LineMetrics lineMetrics;
    final FontRenderContext frc;
    final Rectangle2D bounds;
    final Stroke old;
    double startX, yCoord;

    this.checkClosed();

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

      graph.setStroke(new BasicStroke(lineMetrics.getUnderlineThickness(),
          BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10f));

      graph.drawLine(
          _FreeHEPAbstractVectorGraphicsProxy._f(startX),
          _FreeHEPAbstractVectorGraphicsProxy._f(yCoord),
          _FreeHEPAbstractVectorGraphicsProxy._f(startX
              + bounds.getWidth()),
          _FreeHEPAbstractVectorGraphicsProxy._f(yCoord));

      this.setStroke(old);
    }
    this.m_out.drawString(str, _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawString(final AttributedCharacterIterator iterator,
      final float x, final float y) {
    final Font font;
    final AbstractVectorGraphics graph;
    final LineMetrics lineMetrics;
    final FontRenderContext frc;
    final Rectangle2D bounds;
    final Stroke old;
    double startX, yCoord;

    this.checkClosed();

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

      graph.setStroke(new BasicStroke(lineMetrics.getUnderlineThickness(),
          BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10f));

      graph.drawLine(
          _FreeHEPAbstractVectorGraphicsProxy._f(startX),
          _FreeHEPAbstractVectorGraphicsProxy._f(yCoord),
          _FreeHEPAbstractVectorGraphicsProxy._f(startX
              + bounds.getWidth()),
          _FreeHEPAbstractVectorGraphicsProxy._f(yCoord));

      this.setStroke(old);
    }
    this.m_out.drawString(iterator,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawString(final AttributedCharacterIterator iterator,
      final int x, final int y) {
    this.drawString(iterator,
        ((float) _FreeHEPAbstractVectorGraphicsProxy._f(x)),
        ((float) _FreeHEPAbstractVectorGraphicsProxy._f(y)));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawChars(final char data[], final int offset,
      final int length, final int x, final int y) {
    this.checkClosed();
    this.m_out.drawChars(data, offset, length, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public final void draw3DRect(final int x, final int y, final int width,
      final int height, final boolean raised) {
    this.checkClosed();
    this.m_out.draw3DRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), raised);
  }

  /** {@inheritDoc} */
  @Override
  public final void fill3DRect(final int x, final int y, final int width,
      final int height, final boolean raised) {
    this.checkClosed();
    this.m_out.fill3DRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), raised);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawImage(final BufferedImage img,
      final BufferedImageOp op, final int x, final int y) {
    this.checkClosed();
    this.m_out.drawImage(img, op,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  public final void translate(final int x, final int y) {
    this.checkClosed();
    this.m_out.translate(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  public final void translate(final double tx, final double ty) {
    this.checkClosed();
    this.m_out.translate(_FreeHEPAbstractVectorGraphicsProxy._f(tx),
        _FreeHEPAbstractVectorGraphicsProxy._f(ty));
  }

  /** {@inheritDoc} */
  @Override
  public final void rotate(final double theta) {
    this.checkClosed();
    this.m_out.rotate(_FreeHEPAbstractVectorGraphicsProxy._f(theta));
  }

  /** {@inheritDoc} */
  @Override
  public final void rotate(final double theta, final double x,
      final double y) {
    this.checkClosed();
    this.m_out.rotate(_FreeHEPAbstractVectorGraphicsProxy._f(theta),
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  public final void scale(final double sx, final double sy) {
    this.checkClosed();
    this.m_out.scale(_FreeHEPAbstractVectorGraphicsProxy._f(sx),
        _FreeHEPAbstractVectorGraphicsProxy._f(sy));
  }

  /** {@inheritDoc} */
  @Override
  public final void shear(final double shx, final double shy) {
    this.checkClosed();
    this.m_out.shear(_FreeHEPAbstractVectorGraphicsProxy._f(shx),
        _FreeHEPAbstractVectorGraphicsProxy._f(shy));
  }

  /** {@inheritDoc} */
  @Override
  public Graphics create(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    return this.m_out.create(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void clipRect(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    this.m_out.clipRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void setClip(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    this.m_out.setClip(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void copyArea(final int x, final int y, final int width,
      final int height, final int dx, final int dy) {
    this.checkClosed();
    this.m_out.copyArea(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(dx),
        _FreeHEPAbstractVectorGraphicsProxy._f(dy));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawLine(final int x1, final int y1, final int x2,
      final int y2) {
    this.checkClosed();
    this.m_out.drawLine(_FreeHEPAbstractVectorGraphicsProxy._f(x1),
        _FreeHEPAbstractVectorGraphicsProxy._f(y1),
        _FreeHEPAbstractVectorGraphicsProxy._f(x2),
        _FreeHEPAbstractVectorGraphicsProxy._f(y2));
  }

  /** {@inheritDoc} */
  @Override
  public final void fillRect(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    this.m_out.fillRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawRect(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    this.m_out.drawRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void clearRect(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    this.m_out.clearRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    this.checkClosed();
    this.m_out.drawRoundRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcWidth),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcHeight));
  }

  /** {@inheritDoc} */
  @Override
  public final void fillRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    this.checkClosed();
    this.m_out.fillRoundRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcWidth),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcHeight));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawOval(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    this.m_out.drawOval(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void fillOval(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    this.m_out.fillOval(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawArc(final int x, final int y, final int width,
      final int height, final int startAngle, final int arcAngle) {
    this.checkClosed();
    this.m_out.drawArc(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(startAngle),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcAngle));
  }

  /** {@inheritDoc} */
  @Override
  public final void fillArc(final int x, final int y, final int width,
      final int height, final int startAngle, final int arcAngle) {
    this.checkClosed();
    this.m_out.fillArc(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(startAngle),
        _FreeHEPAbstractVectorGraphicsProxy._f(arcAngle));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawPolyline(final int xPoints[], final int yPoints[],
      final int nPoints) {
    this.checkClosed();
    this.m_out
        .drawPolyline(
            _FreeHEPAbstractVectorGraphicsProxy.__f(xPoints, nPoints),
            _FreeHEPAbstractVectorGraphicsProxy.__f(yPoints, nPoints),
            nPoints);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawPolygon(final int xPoints[], final int yPoints[],
      final int nPoints) {
    this.checkClosed();
    this.m_out
        .drawPolygon(
            _FreeHEPAbstractVectorGraphicsProxy.__f(xPoints, nPoints),
            _FreeHEPAbstractVectorGraphicsProxy.__f(yPoints, nPoints),
            nPoints);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final int x,
      final int y, final ImageObserver observer) {
    this.checkClosed();
    return this.m_out.drawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y), observer);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final int x,
      final int y, final int width, final int height,
      final ImageObserver observer) {
    this.checkClosed();
    return this.m_out.drawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), observer);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final int x,
      final int y, final Color bgcolor, final ImageObserver observer) {
    this.checkClosed();
    return this.m_out.drawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y), bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final int x,
      final int y, final int width, final int height, final Color bgcolor,
      final ImageObserver observer) {
    this.checkClosed();
    return this.m_out.drawImage(img,
        _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2,
      final ImageObserver observer) {
    this.checkClosed();
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
  public final boolean drawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2, final Color bgcolor,
      final ImageObserver observer) {
    this.checkClosed();
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
  public final void draw3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    super.draw3DRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), raised);
  }

  /** {@inheritDoc} */
  @Override
  public final void fill3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    this.checkClosed();
    super.fill3DRect(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), raised);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawImage(final BufferedImage img,
      final BufferedImageOp op, final double x, final double y) {
    this.checkClosed();
    super.drawImage(img, op, _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawString(final AttributedCharacterIterator iterator,
      final double x, final double y) {
    this.checkClosed();
    super.drawString(iterator, _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawGlyphVector(final GlyphVector g, final double x,
      final double y) {
    this.checkClosed();
    super.drawGlyphVector(g, _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y));
  }

  /** {@inheritDoc} */
  @Override
  public final void copyArea(final double x, final double y,
      final double width, final double height, final double dx,
      final double dy) {
    this.checkClosed();
    super.copyArea(_FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height),
        _FreeHEPAbstractVectorGraphicsProxy._f(dx),
        _FreeHEPAbstractVectorGraphicsProxy._f(dy));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final double x,
      final double y, final ImageObserver observer) {
    this.checkClosed();
    return super.drawImage(img, _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y), observer);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final ImageObserver observer) {
    this.checkClosed();
    return super.drawImage(img, _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), observer);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final double x,
      final double y, final Color bgcolor, final ImageObserver observer) {
    this.checkClosed();
    return super.drawImage(img, _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y), bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final Color bgcolor, final ImageObserver observer) {
    this.checkClosed();
    return super.drawImage(img, _FreeHEPAbstractVectorGraphicsProxy._f(x),
        _FreeHEPAbstractVectorGraphicsProxy._f(y),
        _FreeHEPAbstractVectorGraphicsProxy._f(width),
        _FreeHEPAbstractVectorGraphicsProxy._f(height), bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final ImageObserver observer) {
    this.checkClosed();
    return super.drawImage(img,
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
  public final boolean drawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final Color bgcolor, final ImageObserver observer) {
    this.checkClosed();
    return super.drawImage(img,
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
  public final void draw(final Shape s) {
    this.checkClosed();
    this.m_out.draw(new _ProtectedShape(s));
  }

  /** {@inheritDoc} */
  @Override
  public final void fill(final Shape s) {
    this.checkClosed();
    this.m_out.fill(new _ProtectedShape(s));
  }

  /** {@inheritDoc} */
  @Override
  public final void clip(final Shape s) {
    this.checkClosed();
    this.m_out.clip(new _ProtectedShape(s));
  }

  /** {@inheritDoc} */
  @Override
  public final void setClip(final Shape clip) {
    this.checkClosed();
    this.m_out.setClip(new _ProtectedShape(clip));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawPolygon(final Polygon p) {
    this.checkClosed();
    this.m_out.drawPolygon(new Polygon(_FreeHEPAbstractVectorGraphicsProxy
        .__f(p.xpoints, p.npoints), _FreeHEPAbstractVectorGraphicsProxy
        .__f(p.ypoints, p.npoints), p.npoints));
  }

  /** {@inheritDoc} */
  @Override
  public final void fillPolygon(final Polygon p) {
    this.checkClosed();
    this.m_out.fillPolygon(new Polygon(_FreeHEPAbstractVectorGraphicsProxy
        .__f(p.xpoints, p.npoints), _FreeHEPAbstractVectorGraphicsProxy
        .__f(p.ypoints, p.npoints), p.npoints));
  }
}
