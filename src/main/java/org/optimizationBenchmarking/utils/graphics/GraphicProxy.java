package org.optimizationBenchmarking.utils.graphics;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import org.optimizationBenchmarking.utils.ErrorUtils;

/**
 * A wrapper for graphics 2d objects. This wrapper allows us to forward all
 * calls to a different graphics object. Additionally, it provides the
 * interface {@link java.lang.AutoCloseable} which can be used to put all
 * work on an output graphics object into a {@code try...with} statement.
 * 
 * @param <GT>
 *          the proxy graphics type <@javaAuthorVersion/>
 */
public abstract class GraphicProxy<GT extends Graphics2D> extends Graphic {

  /** the real object */
  private final GT m_out;

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
   */
  protected GraphicProxy(final GT graphic, final GraphicID id,
      final IGraphicListener listener) {
    super(listener, id);
    if (graphic == null) {
      throw new IllegalArgumentException(//
          "Delegate graphic must not be null for a proxy graphic."); //$NON-NLS-1$
    }
    this.m_out = graphic;
  }

  /** check the state of the graphic */
  private final void __check() {
    if (this.m_closed) {
      throw new IllegalStateException("Graphic has already been closed."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void draw3DRect(final int x, final int y,
      final int width, final int height, final boolean raised) {
    this.__check();
    this.m_out.draw3DRect(x, y, width, height, raised);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void fill3DRect(final int x, final int y,
      final int width, final int height, final boolean raised) {
    this.__check();
    this.m_out.fill3DRect(x, y, width, height, raised);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void draw(final Shape s) {
    this.__check();
    this.m_out.draw(s);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawImage(final BufferedImage img,
      final BufferedImageOp op, final int x, final int y) {
    this.__check();
    this.m_out.drawImage(img, op, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawRenderedImage(
      final RenderedImage img, final AffineTransform xform) {
    this.__check();
    this.m_out.drawRenderedImage(img, xform);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawRenderableImage(
      final RenderableImage img, final AffineTransform xform) {
    this.__check();
    this.m_out.drawRenderableImage(img, xform);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawString(final String str, final int x,
      final int y) {
    this.__check();
    this.m_out.drawString(str, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawString(final String str,
      final float x, final float y) {
    this.__check();
    this.m_out.drawString(str, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawString(
      final AttributedCharacterIterator iterator, final int x, final int y) {
    this.__check();
    this.m_out.drawString(iterator, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawString(
      final AttributedCharacterIterator iterator, final float x,
      final float y) {
    this.__check();
    this.m_out.drawString(iterator, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawGlyphVector(final GlyphVector g,
      final float x, final float y) {
    this.__check();
    this.m_out.drawGlyphVector(g, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void fill(final Shape s) {
    this.__check();
    this.m_out.fill(s);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final boolean hit(final Rectangle rect,
      final Shape s, final boolean onStroke) {
    this.__check();
    return this.m_out.hit(rect, s, onStroke);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final GraphicsConfiguration getDeviceConfiguration() {
    this.__check();
    return this.m_out.getDeviceConfiguration();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setComposite(final Composite comp) {
    this.__check();
    this.m_out.setComposite(comp);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setPaint(final Paint paint) {
    this.__check();
    this.m_out.setPaint(paint);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setStroke(final Stroke s) {
    this.__check();
    this.m_out.setStroke(s);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setRenderingHint(final Key hintKey,
      final Object hintValue) {
    this.__check();
    this.m_out.setRenderingHint(hintKey, hintValue);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Object getRenderingHint(final Key hintKey) {
    this.__check();
    return this.m_out.getRenderingHint(hintKey);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setRenderingHints(final Map<?, ?> hints) {
    this.__check();
    this.m_out.setRenderingHints(hints);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final RenderingHints getRenderingHints() {
    this.__check();
    return this.m_out.getRenderingHints();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void translate(final int x, final int y) {
    this.__check();
    this.m_out.translate(x, y);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void translate(final double tx, final double ty) {
    this.__check();
    this.m_out.translate(tx, ty);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void rotate(final double theta) {
    this.__check();
    this.m_out.rotate(theta);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void rotate(final double theta,
      final double x, final double y) {
    this.__check();
    this.m_out.rotate(theta, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void scale(final double sx, final double sy) {
    this.__check();
    this.m_out.scale(sx, sy);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void shear(final double shx, final double shy) {
    this.__check();
    this.m_out.shear(shx, shy);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void transform(final AffineTransform Tx) {
    this.__check();
    this.m_out.transform(Tx);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setTransform(final AffineTransform Tx) {
    this.__check();
    this.m_out.setTransform(Tx);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final AffineTransform getTransform() {
    this.__check();
    return this.m_out.getTransform();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Paint getPaint() {
    this.__check();
    return this.m_out.getPaint();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Composite getComposite() {
    this.__check();
    return this.m_out.getComposite();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setBackground(final Color color) {
    this.__check();
    this.m_out.setBackground(color);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Color getBackground() {
    this.__check();
    return this.m_out.getBackground();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Stroke getStroke() {
    this.__check();
    return this.m_out.getStroke();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void clip(final Shape s) {
    this.__check();
    this.m_out.clip(s);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final FontRenderContext getFontRenderContext() {
    this.__check();
    return this.m_out.getFontRenderContext();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Graphics create() {
    this.__check();
    return this.m_out.create();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Graphics create(final int x, final int y,
      final int width, final int height) {
    this.__check();
    return this.m_out.create(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Color getColor() {
    this.__check();
    return this.m_out.getColor();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setColor(final Color c) {
    this.__check();
    this.m_out.setColor(c);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setPaintMode() {
    this.__check();
    this.m_out.setPaintMode();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setXORMode(final Color c1) {
    this.__check();
    this.m_out.setXORMode(c1);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Font getFont() {
    this.__check();
    return this.m_out.getFont();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setFont(final Font font) {
    this.__check();
    this.m_out.setFont(font);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final FontMetrics getFontMetrics() {
    this.__check();
    return this.m_out.getFontMetrics();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final FontMetrics getFontMetrics(final Font f) {
    this.__check();
    return this.m_out.getFontMetrics(f);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Rectangle getClipBounds() {
    this.__check();
    return this.m_out.getClipBounds();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void clipRect(final int x, final int y,
      final int width, final int height) {
    this.__check();
    this.m_out.clipRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setClip(final int x, final int y,
      final int width, final int height) {
    this.__check();
    this.m_out.setClip(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Shape getClip() {
    this.__check();
    return this.m_out.getClip();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setClip(final Shape clip) {
    this.__check();
    this.m_out.setClip(clip);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void copyArea(final int x, final int y,
      final int width, final int height, final int dx, final int dy) {
    this.__check();
    this.m_out.copyArea(x, y, width, height, dx, dy);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawLine(final int x1, final int y1,
      final int x2, final int y2) {
    this.__check();
    this.m_out.drawLine(x1, y1, x2, y2);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void fillRect(final int x, final int y,
      final int width, final int height) {
    this.__check();
    this.m_out.fillRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawRect(final int x, final int y,
      final int width, final int height) {
    this.__check();
    this.m_out.drawRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void clearRect(final int x, final int y,
      final int width, final int height) {
    this.__check();
    this.m_out.clearRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    this.__check();
    this.m_out.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void fillRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    this.__check();
    this.m_out.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawOval(final int x, final int y,
      final int width, final int height) {
    this.__check();
    this.m_out.drawOval(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void fillOval(final int x, final int y,
      final int width, final int height) {
    this.__check();
    this.m_out.fillOval(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawArc(final int x, final int y,
      final int width, final int height, final int startAngle,
      final int arcAngle) {
    this.__check();
    this.m_out.drawArc(x, y, width, height, startAngle, arcAngle);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void fillArc(final int x, final int y,
      final int width, final int height, final int startAngle,
      final int arcAngle) {
    this.__check();
    this.m_out.fillArc(x, y, width, height, startAngle, arcAngle);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawPolyline(final int xPoints[],
      final int yPoints[], final int nPoints) {
    this.__check();
    this.m_out.drawPolyline(xPoints, yPoints, nPoints);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawPolygon(final int xPoints[],
      final int yPoints[], final int nPoints) {
    this.__check();
    this.m_out.drawPolygon(xPoints, yPoints, nPoints);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawPolygon(final Polygon p) {
    this.__check();
    this.m_out.drawPolygon(p);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void fillPolygon(final int xPoints[],
      final int yPoints[], final int nPoints) {
    this.__check();
    this.m_out.fillPolygon(xPoints, yPoints, nPoints);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void fillPolygon(final Polygon p) {
    this.__check();
    this.m_out.fillPolygon(p);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawChars(final char data[],
      final int offset, final int length, final int x, final int y) {
    this.__check();
    this.m_out.drawChars(data, offset, length, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void drawBytes(final byte data[],
      final int offset, final int length, final int x, final int y) {
    this.__check();
    this.m_out.drawBytes(data, offset, length, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final boolean drawImage(final Image img,
      final int x, final int y, final ImageObserver observer) {
    this.__check();
    return this.m_out.drawImage(img, x, y, observer);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final boolean drawImage(final Image img,
      final int x, final int y, final int width, final int height,
      final ImageObserver observer) {
    this.__check();
    return this.m_out.drawImage(img, x, y, width, height, observer);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final boolean drawImage(final Image img,
      final int x, final int y, final Color bgcolor,
      final ImageObserver observer) {
    this.__check();
    return this.m_out.drawImage(img, x, y, bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final boolean drawImage(final Image img,
      final int x, final int y, final int width, final int height,
      final Color bgcolor, final ImageObserver observer) {
    this.__check();
    return this.m_out.drawImage(img, x, y, width, height, bgcolor,
        observer);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final boolean drawImage(final Image img,
      final int dx1, final int dy1, final int dx2, final int dy2,
      final int sx1, final int sy1, final int sx2, final int sy2,
      final ImageObserver observer) {
    this.__check();
    return this.m_out.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2,
        sy2, observer);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized boolean drawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2, final Color bgcolor,
      final ImageObserver observer) {
    this.__check();
    return this.m_out.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2,
        sy2, bgcolor, observer);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  @Deprecated
  public synchronized final Rectangle getClipRect() {
    this.__check();
    return this.m_out.getClipRect();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final boolean hitClip(final int x, final int y,
      final int width, final int height) {
    this.__check();
    return this.m_out.hitClip(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Rectangle getClipBounds(final Rectangle r) {
    this.__check();
    return this.m_out.getClipBounds(r);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final boolean drawImage(final Image img,
      final AffineTransform xform, final ImageObserver obs) {
    this.__check();
    return this.m_out.drawImage(img, xform, obs);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void addRenderingHints(final Map<?, ?> hints) {
    this.__check();
    this.m_out.addRenderingHints(hints);
  }

  /**
   * close the graphics object
   * 
   * @param graphics
   *          the graphics object
   * @throws Throwable
   *           if something goes wrong
   */
  protected void closeInner(final GT graphics) throws Throwable {
    try {
      graphics.dispose();
    } finally {
      if (graphics instanceof AutoCloseable) {
        ((AutoCloseable) graphics).close();
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void onClose() {
    Throwable error;

    error = null;
    try {
      this.closeInner(this.m_out);
    } catch (final Throwable tt) {
      error = tt;
    } finally {
      try {
        super.onClose();
      } catch (final Throwable aa) {
        error = ErrorUtils.aggregateError(error, aa);
      }
    }

    if (error != null) {
      ErrorUtils.throwAsRuntimeException(error);
    }
  }

}
