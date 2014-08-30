package org.optimizationBenchmarking.utils.graphics.drivers.freeHEP;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import org.freehep.graphics2d.AbstractVectorGraphics;
import org.optimizationBenchmarking.utils.graphics.GraphicID;
import org.optimizationBenchmarking.utils.graphics.GraphicProxy;
import org.optimizationBenchmarking.utils.graphics.IGraphicListener;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * an internal base class for <a
 * href="http://java.freehep.org/vectorgraphics">FreeHEP</a>-based vector
 * graphics drivers
 * 
 * @param <T>
 *          the proxied type
 */
class _FreeHEPAbstractVectorGraphicsProxy<T extends AbstractVectorGraphics>
    extends GraphicProxy<T> {

  /** the width */
  final int m_w;

  /** the height */
  final int m_h;

  /**
   * instantiate
   * 
   * @param graphic
   *          the graphic to use
   * @param id
   *          the graphic id identifying this graphic and the path under
   *          which the contents of the graphic are stored
   * @param listener
   *          the object to notify when we are closed, or {@code null} if
   *          none needs to be notified
   * @param w
   *          the width
   * @param h
   *          the height
   */
  protected _FreeHEPAbstractVectorGraphicsProxy(final T graphic,
      final GraphicID id, final IGraphicListener listener, final int w,
      final int h) {
    super(graphic, id, listener);
    this.m_w = w;
    this.m_h = h;
  }

  /** {@inheritDoc} */
  @Override
  public final Rectangle2D getDeviceBounds() {
    return new Rectangle(0, 0, this.m_w, this.m_h);
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction deviceToUnitWidth(final ELength unit) {
    return ELength.POINT.getConversion(unit);
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction unitToDeviceWidth(final ELength unit) {
    return unit.getConversion(ELength.POINT);
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction deviceToUnitHeight(final ELength unit) {
    return ELength.POINT.getConversion(unit);
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction unitToDeviceHeight(final ELength unit) {
    return unit.getConversion(ELength.POINT);
  }

  // new functionality

  /** {@inheritDoc} */
  @Override
  public final void drawString(final String str, final double x,
      final double y) {
    this.checkClosed();
    this.m_out.drawString(str, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public final Graphics create(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    return this.m_out.create(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public final void clipRect(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    this.m_out.clipRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public final void setClip(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    this.m_out.setClip(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawLine(final double x1, final double y1,
      final double x2, final double y2) {
    this.checkClosed();
    this.m_out.drawLine(x1, y1, x2, y2);
  }

  /** {@inheritDoc} */
  @Override
  public final void fillRect(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    this.m_out.fillRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawRect(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    this.m_out.drawRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public final void clearRect(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    this.m_out.clearRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    this.checkClosed();
    this.m_out.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
  }

  /** {@inheritDoc} */
  @Override
  public final void fillRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    this.checkClosed();
    this.m_out.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawOval(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    this.m_out.drawOval(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public final void fillOval(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    this.m_out.fillOval(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawArc(final double x, final double y,
      final double width, final double height, final double startAngle,
      final double arcAngle) {
    this.checkClosed();
    this.m_out.drawArc(x, y, width, height, startAngle, arcAngle);
  }

  /** {@inheritDoc} */
  @Override
  public final void fillArc(final double x, final double y,
      final double width, final double height, final double startAngle,
      final double arcAngle) {
    this.checkClosed();
    this.m_out.fillArc(x, y, width, height, startAngle, arcAngle);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawPolyline(final double xPoints[],
      final double yPoints[], final int nPoints) {
    this.checkClosed();
    this.m_out.drawPolyline(xPoints, yPoints, nPoints);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawPolygon(final double xPoints[],
      final double yPoints[], final int nPoints) {
    this.checkClosed();
    this.m_out.drawPolygon(xPoints, yPoints, nPoints);
  }

  /** {@inheritDoc} */
  @Override
  public final void fillPolygon(final double xPoints[],
      final double yPoints[], final int nPoints) {
    this.checkClosed();
    this.m_out.fillPolygon(xPoints, yPoints, nPoints);
  }

  /** {@inheritDoc} */
  @Override
  public final void drawChars(final char data[], final int offset,
      final int length, final double x, final double y) {
    this.checkClosed();
    this.m_out.drawString(new String(data, offset, length), x, y);
  }
}
