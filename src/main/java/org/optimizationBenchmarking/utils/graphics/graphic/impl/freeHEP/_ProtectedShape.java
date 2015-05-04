package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/** an internal class offering protected shape features */
final class _ProtectedShape implements Shape {

  /** the shape */
  private final Shape m_s;

  /**
   * create the protected shape
   *
   * @param s
   *          the protected shape
   */
  _ProtectedShape(final Shape s) {
    super();
    this.m_s = s;
  }

  /** {@inheritDoc} */
  @Override
  public final Rectangle getBounds() {
    Rectangle r;

    r = this.m_s.getBounds();
    r.height = _FreeHEPAbstractVectorGraphicsProxy._f(r.height);
    r.width = _FreeHEPAbstractVectorGraphicsProxy._f(r.width);
    r.x = _FreeHEPAbstractVectorGraphicsProxy._f(r.x);
    r.y = _FreeHEPAbstractVectorGraphicsProxy._f(r.y);

    return r;
  }

  /** {@inheritDoc} */
  @Override
  public final Rectangle2D getBounds2D() {
    final Rectangle2D r;

    r = this.m_s.getBounds2D();

    return new Rectangle2D.Double(//
        _FreeHEPAbstractVectorGraphicsProxy._f(r.getX()),//
        _FreeHEPAbstractVectorGraphicsProxy._f(r.getY()),//
        _FreeHEPAbstractVectorGraphicsProxy._f(r.getWidth()),//
        _FreeHEPAbstractVectorGraphicsProxy._f(r.getHeight()));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean contains(final double x, final double y) {
    return this.m_s.contains(x, y);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean contains(final Point2D p) {
    return this.m_s.contains(p);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean intersects(final double x, final double y,
      final double w, final double h) {
    return this.m_s.intersects(x, y, w, h);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean intersects(final Rectangle2D r) {
    return this.m_s.intersects(r);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean contains(final double x, final double y,
      final double w, final double h) {
    return this.m_s.contains(x, y, w, h);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean contains(final Rectangle2D r) {
    return this.contains(r);
  }

  /** {@inheritDoc} */
  @Override
  public final PathIterator getPathIterator(final AffineTransform at) {
    return new _ProtectedPathIterator(this.m_s.getPathIterator(at));
  }

  /** {@inheritDoc} */
  @Override
  public final PathIterator getPathIterator(final AffineTransform at,
      final double flatness) {
    return new _ProtectedPathIterator(this.m_s.getPathIterator(at,
        flatness));
  }

}
