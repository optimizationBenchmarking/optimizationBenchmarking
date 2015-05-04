package org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP;

import java.awt.geom.PathIterator;

/** a protected path iterator */
final class _ProtectedPathIterator implements PathIterator {

  /** the internal path iterator */
  private final PathIterator m_p;

  /**
   * create
   *
   * @param p
   *          the iterator wrap
   */
  _ProtectedPathIterator(final PathIterator p) {
    super();
    this.m_p = p;
  }

  /** {@inheritDoc} */
  @Override
  public final int getWindingRule() {
    return this.m_p.getWindingRule();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isDone() {
    return this.m_p.isDone();
  }

  /** {@inheritDoc} */
  @Override
  public final void next() {
    this.m_p.next();
  }

  /** {@inheritDoc} */
  @Override
  public final int currentSegment(final float[] coords) {
    final int z;
    int i;

    z = this.m_p.currentSegment(coords);

    for (i = coords.length; (--i) >= 0;) {
      coords[i] = _FreeHEPAbstractVectorGraphicsProxy._f(coords[i]);
    }

    return z;
  }

  /** {@inheritDoc} */
  @Override
  public final int currentSegment(final double[] coords) {
    final int z;
    int i;

    z = this.m_p.currentSegment(coords);

    for (i = coords.length; (--i) >= 0;) {
      coords[i] = _FreeHEPAbstractVectorGraphicsProxy._f(coords[i]);
    }

    return z;
  }

}
