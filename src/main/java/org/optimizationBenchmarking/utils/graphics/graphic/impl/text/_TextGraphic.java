package org.optimizationBenchmarking.utils.graphics.graphic.impl.text;

import java.awt.Color;
import java.awt.Image;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.text.AttributedCharacterIterator;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.SimpleGraphic;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/** the text output graphic */
final class _TextGraphic extends SimpleGraphic implements ITextOutput {

  /** the internal writer */
  private final BufferedWriter m_writer;
  /** the internal interface to which we delegate */
  private final ITextOutput m_delegate;

  /**
   * create the text graphic
   *
   * @param logger
   *          the logger
   * @param listener
   *          the listener
   * @param path
   *          the path
   * @param width
   *          the width
   * @param height
   *          the height
   */
  _TextGraphic(final Logger logger, final IFileProducerListener listener,
      final Path path, final int width, final int height) {
    super(logger, listener, path, width, height);

    BufferedWriter bw;

    bw = null;
    try {
      bw = new BufferedWriter(new OutputStreamWriter(
          PathUtils.openOutputStream(path)));
    } catch (final IOException ioe) {//
      ErrorUtils.logError(logger,//
          (("Error while creating output stream to path '" + //$NON-NLS-1$
          path) + '\''), ioe, false, RethrowMode.AS_RUNTIME_EXCEPTION);
      // we never get here
    }
    this.m_writer = bw;
    this.m_delegate = AbstractTextOutput.wrap(bw);
  }

  /** {@inheritDoc} */
  @Override
  public final ITextOutput append(final CharSequence csq) {
    return this.m_delegate.append(csq);
  }

  /** {@inheritDoc} */
  @Override
  public final ITextOutput append(final CharSequence csq, final int start,
      final int end) {
    return this.m_delegate.append(csq, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public final ITextOutput append(final char c) {
    return this.m_delegate.append(c);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s) {
    this.m_delegate.append(s);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s, final int start, final int end) {
    this.m_delegate.append(s, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] chars) {
    this.m_delegate.append(chars);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] chars, final int start,
      final int end) {
    this.m_delegate.append(chars, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final byte v) {
    this.m_delegate.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final short v) {
    this.m_delegate.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final int v) {
    this.m_delegate.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long v) {
    this.m_delegate.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final float v) {
    this.m_delegate.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double v) {
    this.m_delegate.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final boolean v) {
    this.m_delegate.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final Object o) {
    this.m_delegate.append(o);
  }

  /** {@inheritDoc} */
  @Override
  public final void appendLineBreak() {
    this.m_delegate.appendLineBreak();
  }

  /** {@inheritDoc} */
  @Override
  public final void appendNonBreakingSpace() {
    this.m_delegate.appendNonBreakingSpace();
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() {
    this.m_delegate.flush();
  }

  /** {@inheritDoc} */
  @Override
  protected final void onClose() {
    try {
      try {
        this.m_delegate.flush();
      } finally {
        this.m_writer.close();
      }
    } catch (final IOException ioe) {//
      ErrorUtils.logError(
          this.getLogger(),//
          (("Error while closing buffereds writer to path '" + //$NON-NLS-1$
          this.getPath()) + '\''), ioe, false,
          RethrowMode.AS_RUNTIME_EXCEPTION);

    } finally {
      super.onClose();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final EGraphicFormat getGraphicFormat() {
    return EGraphicFormat.TEXT;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDraw(final Shape s) {
    // ignored
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawImage(final BufferedImage img,
      final BufferedImageOp op, final int x, final int y) {
    // ignored
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRenderedImage(final RenderedImage img,
      final AffineTransform xform) {
    // ignored
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRenderableImage(final RenderableImage img,
      final AffineTransform xform) {
    // ignored
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(final String str, final int x,
      final int y) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doDrawString(final String str, final float x,
      final float y) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doDrawString(
      final AttributedCharacterIterator iterator, final int x, final int y) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doDrawString(
      final AttributedCharacterIterator iterator, final float x,
      final float y) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doDrawGlyphVector(final GlyphVector g,
      final float x, final float y) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doFill(final Shape s) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doCopyArea(final int x, final int y,
      final int width, final int height, final int dx, final int dy) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doDrawLine(final int x1, final int y1,
      final int x2, final int y2) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doFillRect(final int x, final int y,
      final int width, final int height) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doDrawRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doFillRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doDrawOval(final int x, final int y,
      final int width, final int height) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doFillOval(final int x, final int y,
      final int width, final int height) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doDrawArc(final int x, final int y,
      final int width, final int height, final int startAngle,
      final int arcAngle) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doFillArc(final int x, final int y,
      final int width, final int height, final int startAngle,
      final int arcAngle) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doFillPolygon(final int[] xPoints,
      final int[] yPoints, final int nPoints) {
    // ignored

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final boolean doDrawImage(final Image img, final int x,
      final int y, final ImageObserver observer) {
    // ignored
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final boolean doDrawImage(final Image img, final int x,
      final int y, final int width, final int height,
      final ImageObserver observer) {
    // ignored
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final boolean doDrawImage(final Image img, final int x,
      final int y, final Color bgcolor, final ImageObserver observer) {
    // ignored
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final boolean doDrawImage(final Image img, final int x,
      final int y, final int width, final int height, final Color bgcolor,
      final ImageObserver observer) {
    // ignored
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final boolean doDrawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2,
      final ImageObserver observer) {
    // ignored
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final boolean doDrawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2, final Color bgcolor,
      final ImageObserver observer) {
    // ignored
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final boolean doDrawImage(final Image img,
      final AffineTransform xform, final ImageObserver obs) {
    // ignored
    return false;
  }

}
