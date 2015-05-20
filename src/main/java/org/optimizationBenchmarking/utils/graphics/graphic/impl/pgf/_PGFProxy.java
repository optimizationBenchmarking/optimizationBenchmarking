package org.optimizationBenchmarking.utils.graphics.graphic.impl.pgf;

import java.awt.Graphics;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.SimplifyingGraphicProxy;

/** a proxy graphic */
final class _PGFProxy extends SimplifyingGraphicProxy<_PGFGraphic> {

  /**
   * create
   *
   * @param graphic
   *          the graphic
   * @param log
   *          the logger
   */
  _PGFProxy(final _PGFGraphic graphic, final Logger log) {
    super(graphic, log, null, null);
    graphic._beginProxy(this);
  }

  /** close */
  @Override
  protected final void doClose() {
    try {
      this.m_out._endProxy(this);
    } finally {
      super.doClose();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final EGraphicFormat getGraphicFormat() {
    return this.m_out.getGraphicFormat();
  }

  /**
   * Artificially invoke the {@link #before(int)} method
   *
   * @param what
   *          what has changed
   */
  final void _before(final int what) {
    this.before(what);
  }

  /** {@inheritDoc} */
  @Override
  protected final Graphics wrapCreatedGraphic(final Graphics graphics) {
    return graphics;
  }

}