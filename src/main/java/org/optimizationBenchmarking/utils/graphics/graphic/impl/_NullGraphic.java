package org.optimizationBenchmarking.utils.graphics.graphic.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.DoubleDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.SimpleGraphic;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A graphic which simply discards all of its output. This object is
 * designed for high-throughput and low memory footprint.
 */
final class _NullGraphic extends SimpleGraphic {

  /**
   * create the null graphic
   *
   * @param logger
   *          the logger
   * @param listener
   *          the listener
   * @param size
   *          the size
   */
  _NullGraphic(final Logger logger, final IFileProducerListener listener,
      final Dimension2D size) {
    super(logger, listener, null,//
        Math.max(1, ((int) (0.5d + size.getWidth()))),//
        Math.max(1, ((int) (0.5d + size.getHeight()))));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDraw(final Shape s) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img,
      final AffineTransform xform, final ImageObserver obs) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawImage(final BufferedImage img,
      final BufferedImageOp op, final int x, final int y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRenderedImage(final RenderedImage img,
      final AffineTransform xform) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRenderableImage(final RenderableImage img,
      final AffineTransform xform) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(final String str, final int x,
      final int y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(final String str, final float x,
      final float y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(
      final AttributedCharacterIterator iterator, final int x, final int y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(
      final AttributedCharacterIterator iterator, final float x,
      final float y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawGlyphVector(final GlyphVector g,
      final float x, final float y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFill(final Shape s) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doCopyArea(final int x, final int y,
      final int width, final int height, final int dx, final int dy) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawLine(final int x1, final int y1,
      final int x2, final int y2) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillRect(final int x, final int y,
      final int width, final int height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doClearRect(final int x, final int y,
      final int width, final int height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawOval(final int x, final int y,
      final int width, final int height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillOval(final int x, final int y,
      final int width, final int height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawArc(final int x, final int y,
      final int width, final int height, final int startAngle,
      final int arcAngle) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillArc(final int x, final int y,
      final int width, final int height, final int startAngle,
      final int arcAngle) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawPolyline(final int[] xPoints,
      final int[] yPoints, final int nPoints) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawPolygon(final int[] xPoints,
      final int[] yPoints, final int nPoints) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillPolygon(final int[] xPoints,
      final int[] yPoints, final int nPoints) {
    // TODO Auto-generated method stub
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final int x,
      final int y, final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final int x,
      final int y, final int width, final int height,
      final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final int x,
      final int y, final Color bgcolor, final ImageObserver observer) {
    // TODO Auto-generated method stub
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final int x,
      final int y, final int width, final int height, final Color bgcolor,
      final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2,
      final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2, final Color bgcolor,
      final ImageObserver observer) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDraw3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFill3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(final String str, final double x,
      final double y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(
      final AttributedCharacterIterator iterator, final double x,
      final double y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawGlyphVector(final GlyphVector g,
      final double x, final double y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final Graphics doCreate(final double x, final double y,
      final double width, final double height) {
    return new _NullGraphic(this.getLogger(), null,//
        new DoubleDimension(width, height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doCopyArea(final double x, final double y,
      final double width, final double height, final double dx,
      final double dy) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawLine(final double x1, final double y1,
      final double x2, final double y2) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillRect(final double x, final double y,
      final double width, final double height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRect(final double x, final double y,
      final double width, final double height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doClearRect(final double x, final double y,
      final double width, final double height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawOval(final double x, final double y,
      final double width, final double height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillOval(final double x, final double y,
      final double width, final double height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawArc(final double x, final double y,
      final double width, final double height, final double startAngle,
      final double arcAngle) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillArc(final double x, final double y,
      final double width, final double height, final double startAngle,
      final double arcAngle) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawPolyline(final double xPoints[],
      final double yPoints[], final int nPoints) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawPolygon(final double xPoints[],
      final double yPoints[], final int nPoints) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillPolygon(final double xPoints[],
      final double yPoints[], final int nPoints) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawChars(final char data[], final int offset,
      final int length, final double x, final double y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final double x,
      final double y, final ImageObserver observer) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final double x,
      final double y, final Color bgcolor, final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final Color bgcolor, final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean doDrawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final Color bgcolor, final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final EGraphicFormat getGraphicFormat() {
    return EGraphicFormat.NULL;
  }

}
