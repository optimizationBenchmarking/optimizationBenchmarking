package org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr;

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
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.nio.file.Path;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * An abstract wrapper which maps {@link java.awt.Graphics2D} objects to
 * instances of
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic}
 * . This wrapper allows us to forward all calls to a given graphics
 * object. Its {@link #onClose()} routine must be overridden to dispose
 * that object when the graphic context is released.
 * 
 * @param <GT>
 *          the proxy graphics type
 */
public abstract class GraphicProxy<GT extends Graphics2D> extends Graphic {

  /** the real object */
  protected final GT m_out;

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
  protected GraphicProxy(final GT graphic, final Logger log,
      final IFileProducerListener listener, final Path path) {
    super(log, listener, path);
    if (graphic == null) {
      throw new IllegalArgumentException(//
          "Delegate graphic must not be null for a proxy graphic."); //$NON-NLS-1$
    }
    this.m_out = graphic;
  }

  /** {@inheritDoc} */
  @Override
  public Rectangle2D getBounds() {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).getBounds();
    }
    return super.getBounds();
  }

  /** {@inheritDoc} */
  @Override
  protected void doDraw3DRect(final int x, final int y, final int width,
      final int height, final boolean raised) {
    this.m_out.draw3DRect(x, y, width, height, raised);
  }

  /** {@inheritDoc} */
  @Override
  protected void doFill3DRect(final int x, final int y, final int width,
      final int height, final boolean raised) {
    this.m_out.fill3DRect(x, y, width, height, raised);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDraw(final Shape s) {
    this.m_out.draw(s);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawImage(final BufferedImage img,
      final BufferedImageOp op, final int x, final int y) {
    this.m_out.drawImage(img, op, x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawRenderedImage(final RenderedImage img,
      final AffineTransform xform) {
    this.m_out.drawRenderedImage(img, xform);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawRenderableImage(final RenderableImage img,
      final AffineTransform xform) {
    this.m_out.drawRenderableImage(img, xform);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawString(final String str, final int x, final int y) {
    this.m_out.drawString(str, x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawString(final String str, final float x,
      final float y) {
    this.m_out.drawString(str, x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawString(final AttributedCharacterIterator iterator,
      final int x, final int y) {
    this.m_out.drawString(iterator, x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawString(final AttributedCharacterIterator iterator,
      final float x, final float y) {
    this.m_out.drawString(iterator, x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawGlyphVector(final GlyphVector g, final float x,
      final float y) {
    this.m_out.drawGlyphVector(g, x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected void doFill(final Shape s) {
    this.m_out.fill(s);
  }

  /** {@inheritDoc} */
  @Override
  public boolean hit(final Rectangle rect, final Shape s,
      final boolean onStroke) {
    this.checkClosed();
    return this.m_out.hit(rect, s, onStroke);
  }

  /** {@inheritDoc} */
  @Override
  public GraphicsConfiguration getDeviceConfiguration() {
    this.checkClosed();
    return this.m_out.getDeviceConfiguration();
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetComposite(final Composite comp) {
    this.m_out.setComposite(comp);
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetPaint(final Paint paint) {
    this.m_out.setPaint(paint);
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetStroke(final Stroke s) {
    this.m_out.setStroke(s);
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetRenderingHint(final Key hintKey,
      final Object hintValue) {
    this.m_out.setRenderingHint(hintKey, hintValue);
  }

  /** {@inheritDoc} */
  @Override
  public Object getRenderingHint(final Key hintKey) {
    this.checkClosed();
    return this.m_out.getRenderingHint(hintKey);
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetRenderingHints(final Map<?, ?> hints) {
    this.m_out.setRenderingHints(hints);
  }

  /** {@inheritDoc} */
  @Override
  public RenderingHints getRenderingHints() {
    this.checkClosed();
    return this.m_out.getRenderingHints();
  }

  /** {@inheritDoc} */
  @Override
  protected void doTranslate(final int x, final int y) {
    this.m_out.translate(x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected void doTranslate(final double tx, final double ty) {
    this.m_out.translate(tx, ty);
  }

  /** {@inheritDoc} */
  @Override
  protected void doRotate(final double theta) {
    this.m_out.rotate(theta);
  }

  /** {@inheritDoc} */
  @Override
  protected void doRotate(final double theta, final double x,
      final double y) {
    this.m_out.rotate(theta, x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected void doScale(final double sx, final double sy) {
    this.m_out.scale(sx, sy);
  }

  /** {@inheritDoc} */
  @Override
  protected void doShear(final double shx, final double shy) {
    this.m_out.shear(shx, shy);
  }

  /** {@inheritDoc} */
  @Override
  protected void doTransform(final AffineTransform Tx) {
    this.m_out.transform(Tx);
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetTransform(final AffineTransform Tx) {
    this.m_out.setTransform(Tx);
  }

  /** {@inheritDoc} */
  @Override
  public AffineTransform getTransform() {
    this.checkClosed();
    return this.m_out.getTransform();
  }

  /** {@inheritDoc} */
  @Override
  public Paint getPaint() {
    this.checkClosed();
    return this.m_out.getPaint();
  }

  /** {@inheritDoc} */
  @Override
  public Composite getComposite() {
    this.checkClosed();
    return this.m_out.getComposite();
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetBackground(final Color color) {
    this.m_out.setBackground(color);
  }

  /** {@inheritDoc} */
  @Override
  public Color getBackground() {
    this.checkClosed();
    return this.m_out.getBackground();
  }

  /** {@inheritDoc} */
  @Override
  public Stroke getStroke() {
    this.checkClosed();
    return this.m_out.getStroke();
  }

  /** {@inheritDoc} */
  @Override
  protected void doClip(final Shape s) {
    this.m_out.clip(s);
  }

  /** {@inheritDoc} */
  @Override
  public FontRenderContext getFontRenderContext() {
    this.checkClosed();
    return this.m_out.getFontRenderContext();
  }

  /** {@inheritDoc} */
  @Override
  protected Graphics doCreate() {
    return this.m_out.create();
  }

  /** {@inheritDoc} */
  @Override
  protected Graphics doCreate(final int x, final int y, final int width,
      final int height) {
    return this.m_out.create(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public Color getColor() {
    this.checkClosed();
    return this.m_out.getColor();
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetColor(final Color c) {
    this.m_out.setColor(c);
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetPaintMode() {
    this.m_out.setPaintMode();
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetXORMode(final Color c1) {
    this.m_out.setXORMode(c1);
  }

  /** {@inheritDoc} */
  @Override
  public Font getFont() {
    this.checkClosed();
    return this.m_out.getFont();
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetFont(final Font font) {
    this.m_out.setFont(font);
  }

  /** {@inheritDoc} */
  @Override
  public FontMetrics getFontMetrics() {
    this.checkClosed();
    return this.m_out.getFontMetrics();
  }

  /** {@inheritDoc} */
  @Override
  public FontMetrics getFontMetrics(final Font f) {
    this.checkClosed();
    return this.m_out.getFontMetrics(f);
  }

  /** {@inheritDoc} */
  @Override
  public Rectangle getClipBounds() {
    this.checkClosed();
    return this.m_out.getClipBounds();
  }

  /** {@inheritDoc} */
  @Override
  protected void doClipRect(final int x, final int y, final int width,
      final int height) {
    this.m_out.clipRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetClip(final int x, final int y, final int width,
      final int height) {
    this.m_out.setClip(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public Shape getClip() {
    this.checkClosed();
    return this.m_out.getClip();
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetClip(final Shape clip) {
    this.m_out.setClip(clip);
  }

  /** {@inheritDoc} */
  @Override
  protected void doCopyArea(final int x, final int y, final int width,
      final int height, final int dx, final int dy) {
    this.m_out.copyArea(x, y, width, height, dx, dy);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawLine(final int x1, final int y1, final int x2,
      final int y2) {
    this.m_out.drawLine(x1, y1, x2, y2);
  }

  /** {@inheritDoc} */
  @Override
  protected void doFillRect(final int x, final int y, final int width,
      final int height) {
    this.m_out.fillRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawRect(final int x, final int y, final int width,
      final int height) {
    this.m_out.drawRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected void doClearRect(final int x, final int y, final int width,
      final int height) {
    this.m_out.clearRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    this.m_out.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
  }

  /** {@inheritDoc} */
  @Override
  protected void doFillRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    this.m_out.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawOval(final int x, final int y, final int width,
      final int height) {
    this.m_out.drawOval(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected void doFillOval(final int x, final int y, final int width,
      final int height) {
    this.m_out.fillOval(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawArc(final int x, final int y, final int width,
      final int height, final int startAngle, final int arcAngle) {
    this.m_out.drawArc(x, y, width, height, startAngle, arcAngle);
  }

  /** {@inheritDoc} */
  @Override
  protected void doFillArc(final int x, final int y, final int width,
      final int height, final int startAngle, final int arcAngle) {
    this.m_out.fillArc(x, y, width, height, startAngle, arcAngle);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawPolyline(final int[] xPoints, final int[] yPoints,
      final int nPoints) {
    this.m_out.drawPolyline(xPoints, yPoints, nPoints);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawPolygon(final int[] xPoints, final int[] yPoints,
      final int nPoints) {
    this.m_out.drawPolygon(xPoints, yPoints, nPoints);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawPolygon(final Polygon p) {
    this.m_out.drawPolygon(p);
  }

  /** {@inheritDoc} */
  @Override
  protected void doFillPolygon(final int xPoints[], final int yPoints[],
      final int nPoints) {
    this.m_out.fillPolygon(xPoints, yPoints, nPoints);
  }

  /** {@inheritDoc} */
  @Override
  protected void doFillPolygon(final Polygon p) {
    this.m_out.fillPolygon(p);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawChars(final char data[], final int offset,
      final int length, final int x, final int y) {
    this.m_out.drawChars(data, offset, length, x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawBytes(final byte data[], final int offset,
      final int length, final int x, final int y) {
    this.m_out.drawBytes(data, offset, length, x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final int x, final int y,
      final ImageObserver observer) {
    return this.m_out.drawImage(img, x, y, observer);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final int x, final int y,
      final int width, final int height, final ImageObserver observer) {
    return this.m_out.drawImage(img, x, y, width, height, observer);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final int x, final int y,
      final Color bgcolor, final ImageObserver observer) {
    return this.m_out.drawImage(img, x, y, bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final int x, final int y,
      final int width, final int height, final Color bgcolor,
      final ImageObserver observer) {
    return this.m_out.drawImage(img, x, y, width, height, bgcolor,
        observer);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2,
      final ImageObserver observer) {
    return this.m_out.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2,
        sy2, observer);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2, final Color bgcolor,
      final ImageObserver observer) {
    return this.m_out.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2,
        sy2, bgcolor, observer);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  @Deprecated
  public Rectangle getClipRect() {
    this.checkClosed();
    return this.m_out.getClipRect();
  }

  /** {@inheritDoc} */
  @Override
  public boolean hitClip(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    return this.m_out.hitClip(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public Rectangle getClipBounds(final Rectangle r) {
    this.checkClosed();
    return this.m_out.getClipBounds(r);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img,
      final AffineTransform xform, final ImageObserver obs) {
    return this.m_out.drawImage(img, xform, obs);
  }

  /** {@inheritDoc} */
  @Override
  protected void doAddRenderingHints(final Map<?, ?> hints) {
    this.m_out.addRenderingHints(hints);
  }

  // new functionality

  /** {@inheritDoc} */
  @Override
  protected void doDraw3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).draw3DRect(x, y, width, height, raised);
    } else {
      super.doDraw3DRect(x, y, width, height, raised);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doFill3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).fill3DRect(x, y, width, height, raised);
    } else {
      super.doFill3DRect(x, y, width, height, raised);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawImage(final BufferedImage img,
      final BufferedImageOp op, final double x, final double y) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawImage(img, op, x, y);
    } else {
      super.doDrawImage(img, op, x, y);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawString(final String str, final double x,
      final double y) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawString(str, x, y);
    } else {
      super.doDrawString(str, x, y);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawString(final AttributedCharacterIterator iterator,
      final double x, final double y) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawString(iterator, x, y);
    } else {
      super.doDrawString(iterator, x, y);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawGlyphVector(final GlyphVector g, final double x,
      final double y) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawGlyphVector(g, x, y);
    } else {
      super.doDrawGlyphVector(g, x, y);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected Graphics doCreate(final double x, final double y,
      final double width, final double height) {
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).create(x, y, width, height);
    }
    return super.doCreate(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected void doClipRect(final double x, final double y,
      final double width, final double height) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).clipRect(x, y, width, height);
    } else {
      super.doClipRect(x, y, width, height);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetClip(final double x, final double y,
      final double width, final double height) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).setClip(x, y, width, height);
    } else {
      super.setClip(x, y, width, height);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doCopyArea(final double x, final double y,
      final double width, final double height, final double dx,
      final double dy) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).copyArea(x, y, width, height, dx, dy);
    } else {
      super.doCopyArea(x, y, width, height, dx, dy);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawLine(final double x1, final double y1,
      final double x2, final double y2) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawLine(x1, y1, x2, y2);
    } else {
      super.doDrawLine(x1, y1, x2, y2);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doFillRect(final double x, final double y,
      final double width, final double height) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).fillRect(x, y, width, height);
    } else {
      super.doFillRect(x, y, width, height);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawRect(final double x, final double y,
      final double width, final double height) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawRect(x, y, width, height);
    } else {
      super.doDrawRect(x, y, width, height);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doClearRect(final double x, final double y,
      final double width, final double height) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).clearRect(x, y, width, height);
    } else {
      super.doClearRect(x, y, width, height);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawRoundRect(x, y, width, height,
          arcWidth, arcHeight);
    } else {
      super.doDrawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doFillRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).fillRoundRect(x, y, width, height,
          arcWidth, arcHeight);
    } else {
      super.doFillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawOval(final double x, final double y,
      final double width, final double height) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawOval(x, y, width, height);
    } else {
      super.doDrawOval(x, y, width, height);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doFillOval(final double x, final double y,
      final double width, final double height) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).fillOval(x, y, width, height);
    } else {
      super.doFillOval(x, y, width, height);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawArc(final double x, final double y,
      final double width, final double height, final double startAngle,
      final double arcAngle) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawArc(x, y, width, height, startAngle,
          arcAngle);
    } else {
      super.doDrawArc(x, y, width, height, startAngle, arcAngle);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doFillArc(final double x, final double y,
      final double width, final double height, final double startAngle,
      final double arcAngle) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).fillArc(x, y, width, height, startAngle,
          arcAngle);
    } else {
      super.doFillArc(x, y, width, height, startAngle, arcAngle);
    }
  }

  /** {@inheritDoc} */
  @Override
  public Shape createShape(final double[] xPoints, final double[] yPoints,
      final int nPoints, final boolean close) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).createShape(xPoints, yPoints,
          nPoints, close);
    }
    return super.createShape(xPoints, yPoints, nPoints, close);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawPolyline(final double[] xPoints,
      final double[] yPoints, final int nPoints) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawPolyline(xPoints, yPoints, nPoints);
    } else {
      super.doDrawPolyline(xPoints, yPoints, nPoints);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawPolygon(final double xPoints[],
      final double yPoints[], final int nPoints) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawPolygon(xPoints, yPoints, nPoints);
    } else {
      super.doDrawPolygon(xPoints, yPoints, nPoints);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doFillPolygon(final double xPoints[],
      final double yPoints[], final int nPoints) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).fillPolygon(xPoints, yPoints, nPoints);
    } else {
      super.doFillPolygon(xPoints, yPoints, nPoints);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawChars(final char data[], final int offset,
      final int length, final double x, final double y) {
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawChars(data, offset, length, x, y);
    } else {
      super.doDrawChars(data, offset, length, x, y);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final double x,
      final double y, final ImageObserver observer) {
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).drawImage(img, x, y, observer);
    }
    return super.doDrawImage(img, x, y, observer);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final ImageObserver observer) {
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).drawImage(img, x, y, width, height,
          observer);
    }
    return super.doDrawImage(img, x, y, width, height, observer);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final double x,
      final double y, final Color bgcolor, final ImageObserver observer) {
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).drawImage(img, x, y, bgcolor,
          observer);
    }
    return super.doDrawImage(img, x, y, bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final Color bgcolor, final ImageObserver observer) {
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).drawImage(img, x, y, width, height,
          bgcolor, observer);
    }
    return super.doDrawImage(img, x, y, width, height, bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final ImageObserver observer) {
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).drawImage(img, dx1, dy1, dx2, dy2,
          sx1, sy1, sx2, sy2, observer);
    }
    return super.doDrawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2,
        observer);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final Color bgcolor, final ImageObserver observer) {
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).drawImage(img, dx1, dy1, dx2, dy2,
          sx1, sy1, sx2, sy2, bgcolor, observer);
    }
    return super.doDrawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2,
        bgcolor, observer);
  }

}
