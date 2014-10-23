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

import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;

/**
 * An abstract wrapper which maps {@link java.awt.Graphics2D} objects to
 * instances of
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic}
 * . This wrapper allows us to forward all calls to a given graphics
 * object. Its {@link #onClose()} routine must be overridden to dispose
 * that object when the graphic context is released.
 * 
 * @param <GT>
 *          the proxy graphics type <@javaAuthorVersion/>
 */
public abstract class GraphicProxy<GT extends Graphics2D> extends Graphic {

  /** the real object */
  protected final GT m_out;

  /**
   * instantiate
   * 
   * @param graphic
   *          the graphic to use
   * @param path
   *          the path to be managed
   * @param listener
   *          the listener to notify
   */
  protected GraphicProxy(final GT graphic, final Path path,
      final IObjectListener listener) {
    super(listener, path);
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
  public void draw3DRect(final int x, final int y, final int width,
      final int height, final boolean raised) {
    this.checkClosed();
    this.m_out.draw3DRect(x, y, width, height, raised);
  }

  /** {@inheritDoc} */
  @Override
  public void fill3DRect(final int x, final int y, final int width,
      final int height, final boolean raised) {
    this.checkClosed();
    this.m_out.fill3DRect(x, y, width, height, raised);
  }

  /** {@inheritDoc} */
  @Override
  public void draw(final Shape s) {
    this.checkClosed();
    this.m_out.draw(s);
  }

  /** {@inheritDoc} */
  @Override
  public void drawImage(final BufferedImage img, final BufferedImageOp op,
      final int x, final int y) {
    this.checkClosed();
    this.m_out.drawImage(img, op, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public void drawRenderedImage(final RenderedImage img,
      final AffineTransform xform) {
    this.checkClosed();
    this.m_out.drawRenderedImage(img, xform);
  }

  /** {@inheritDoc} */
  @Override
  public void drawRenderableImage(final RenderableImage img,
      final AffineTransform xform) {
    this.checkClosed();
    this.m_out.drawRenderableImage(img, xform);
  }

  /** {@inheritDoc} */
  @Override
  public void drawString(final String str, final int x, final int y) {
    this.checkClosed();
    this.m_out.drawString(str, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public void drawString(final String str, final float x, final float y) {
    this.checkClosed();
    this.m_out.drawString(str, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public void drawString(final AttributedCharacterIterator iterator,
      final int x, final int y) {
    this.checkClosed();
    this.m_out.drawString(iterator, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public void drawString(final AttributedCharacterIterator iterator,
      final float x, final float y) {
    this.checkClosed();
    this.m_out.drawString(iterator, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public void drawGlyphVector(final GlyphVector g, final float x,
      final float y) {
    this.checkClosed();
    this.m_out.drawGlyphVector(g, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public void fill(final Shape s) {
    this.checkClosed();
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
  public void setComposite(final Composite comp) {
    this.checkClosed();
    this.m_out.setComposite(comp);
  }

  /** {@inheritDoc} */
  @Override
  public void setPaint(final Paint paint) {
    this.checkClosed();
    this.m_out.setPaint(paint);
  }

  /** {@inheritDoc} */
  @Override
  public void setStroke(final Stroke s) {
    this.checkClosed();
    this.m_out.setStroke(s);
  }

  /** {@inheritDoc} */
  @Override
  public void setRenderingHint(final Key hintKey, final Object hintValue) {
    this.checkClosed();
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
  public void setRenderingHints(final Map<?, ?> hints) {
    this.checkClosed();
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
  public void translate(final int x, final int y) {
    this.checkClosed();
    this.m_out.translate(x, y);
  }

  /** {@inheritDoc} */
  @Override
  public void translate(final double tx, final double ty) {
    this.checkClosed();
    this.m_out.translate(tx, ty);
  }

  /** {@inheritDoc} */
  @Override
  public void rotate(final double theta) {
    this.checkClosed();
    this.m_out.rotate(theta);
  }

  /** {@inheritDoc} */
  @Override
  public void rotate(final double theta, final double x, final double y) {
    this.checkClosed();
    this.m_out.rotate(theta, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public void scale(final double sx, final double sy) {
    this.checkClosed();
    this.m_out.scale(sx, sy);
  }

  /** {@inheritDoc} */
  @Override
  public void shear(final double shx, final double shy) {
    this.checkClosed();
    this.m_out.shear(shx, shy);
  }

  /** {@inheritDoc} */
  @Override
  public void transform(final AffineTransform Tx) {
    this.checkClosed();
    this.m_out.transform(Tx);
  }

  /** {@inheritDoc} */
  @Override
  public void setTransform(final AffineTransform Tx) {
    this.checkClosed();
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
  public void setBackground(final Color color) {
    this.checkClosed();
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
  public void clip(final Shape s) {
    this.checkClosed();
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
  public Graphics create() {
    this.checkClosed();
    return this.m_out.create();
  }

  /** {@inheritDoc} */
  @Override
  public Graphics create(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
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
  public void setColor(final Color c) {
    this.checkClosed();
    this.m_out.setColor(c);
  }

  /** {@inheritDoc} */
  @Override
  public void setPaintMode() {
    this.checkClosed();
    this.m_out.setPaintMode();
  }

  /** {@inheritDoc} */
  @Override
  public void setXORMode(final Color c1) {
    this.checkClosed();
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
  public void setFont(final Font font) {
    this.checkClosed();
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
  public void clipRect(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    this.m_out.clipRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public void setClip(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
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
  public void setClip(final Shape clip) {
    this.checkClosed();
    this.m_out.setClip(clip);
  }

  /** {@inheritDoc} */
  @Override
  public void copyArea(final int x, final int y, final int width,
      final int height, final int dx, final int dy) {
    this.checkClosed();
    this.m_out.copyArea(x, y, width, height, dx, dy);
  }

  /** {@inheritDoc} */
  @Override
  public void drawLine(final int x1, final int y1, final int x2,
      final int y2) {
    this.checkClosed();
    this.m_out.drawLine(x1, y1, x2, y2);
  }

  /** {@inheritDoc} */
  @Override
  public void fillRect(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    this.m_out.fillRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public void drawRect(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    this.m_out.drawRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public void clearRect(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    this.m_out.clearRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public void drawRoundRect(final int x, final int y, final int width,
      final int height, final int arcWidth, final int arcHeight) {
    this.checkClosed();
    this.m_out.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
  }

  /** {@inheritDoc} */
  @Override
  public void fillRoundRect(final int x, final int y, final int width,
      final int height, final int arcWidth, final int arcHeight) {
    this.checkClosed();
    this.m_out.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
  }

  /** {@inheritDoc} */
  @Override
  public void drawOval(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    this.m_out.drawOval(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public void fillOval(final int x, final int y, final int width,
      final int height) {
    this.checkClosed();
    this.m_out.fillOval(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public void drawArc(final int x, final int y, final int width,
      final int height, final int startAngle, final int arcAngle) {
    this.checkClosed();
    this.m_out.drawArc(x, y, width, height, startAngle, arcAngle);
  }

  /** {@inheritDoc} */
  @Override
  public void fillArc(final int x, final int y, final int width,
      final int height, final int startAngle, final int arcAngle) {
    this.checkClosed();
    this.m_out.fillArc(x, y, width, height, startAngle, arcAngle);
  }

  /** {@inheritDoc} */
  @Override
  public void drawPolyline(final int xPoints[], final int yPoints[],
      final int nPoints) {
    this.checkClosed();
    this.m_out.drawPolyline(xPoints, yPoints, nPoints);
  }

  /** {@inheritDoc} */
  @Override
  public void drawPolygon(final int xPoints[], final int yPoints[],
      final int nPoints) {
    this.checkClosed();
    this.m_out.drawPolygon(xPoints, yPoints, nPoints);
  }

  /** {@inheritDoc} */
  @Override
  public void drawPolygon(final Polygon p) {
    this.checkClosed();
    this.m_out.drawPolygon(p);
  }

  /** {@inheritDoc} */
  @Override
  public void fillPolygon(final int xPoints[], final int yPoints[],
      final int nPoints) {
    this.checkClosed();
    this.m_out.fillPolygon(xPoints, yPoints, nPoints);
  }

  /** {@inheritDoc} */
  @Override
  public void fillPolygon(final Polygon p) {
    this.checkClosed();
    this.m_out.fillPolygon(p);
  }

  /** {@inheritDoc} */
  @Override
  public void drawChars(final char data[], final int offset,
      final int length, final int x, final int y) {
    this.checkClosed();
    this.m_out.drawChars(data, offset, length, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public void drawBytes(final byte data[], final int offset,
      final int length, final int x, final int y) {
    this.checkClosed();
    this.m_out.drawBytes(data, offset, length, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final int x, final int y,
      final ImageObserver observer) {
    this.checkClosed();
    return this.m_out.drawImage(img, x, y, observer);
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final int x, final int y,
      final int width, final int height, final ImageObserver observer) {
    this.checkClosed();
    return this.m_out.drawImage(img, x, y, width, height, observer);
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final int x, final int y,
      final Color bgcolor, final ImageObserver observer) {
    this.checkClosed();
    return this.m_out.drawImage(img, x, y, bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final int x, final int y,
      final int width, final int height, final Color bgcolor,
      final ImageObserver observer) {
    this.checkClosed();
    return this.m_out.drawImage(img, x, y, width, height, bgcolor,
        observer);
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final int dx1, final int dy1,
      final int dx2, final int dy2, final int sx1, final int sy1,
      final int sx2, final int sy2, final ImageObserver observer) {
    this.checkClosed();
    return this.m_out.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2,
        sy2, observer);
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final int dx1, final int dy1,
      final int dx2, final int dy2, final int sx1, final int sy1,
      final int sx2, final int sy2, final Color bgcolor,
      final ImageObserver observer) {
    this.checkClosed();
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
  public boolean drawImage(final Image img, final AffineTransform xform,
      final ImageObserver obs) {
    this.checkClosed();
    return this.m_out.drawImage(img, xform, obs);
  }

  /** {@inheritDoc} */
  @Override
  public void addRenderingHints(final Map<?, ?> hints) {
    this.checkClosed();
    this.m_out.addRenderingHints(hints);
  }

  // new functionality

  /** {@inheritDoc} */
  @Override
  public void draw3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).draw3DRect(x, y, width, height, raised);
    } else {
      super.draw3DRect(x, y, width, height, raised);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void fill3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).fill3DRect(x, y, width, height, raised);
    } else {
      super.fill3DRect(x, y, width, height, raised);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void drawImage(final BufferedImage img, final BufferedImageOp op,
      final double x, final double y) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawImage(img, op, x, y);
    } else {
      super.drawImage(img, op, x, y);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void drawString(final String str, final double x, final double y) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawString(str, x, y);
    } else {
      super.drawString(str, x, y);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void drawString(final AttributedCharacterIterator iterator,
      final double x, final double y) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawString(iterator, x, y);
    } else {
      super.drawString(iterator, x, y);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void drawGlyphVector(final GlyphVector g, final double x,
      final double y) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawGlyphVector(g, x, y);
    } else {
      super.drawGlyphVector(g, x, y);
    }
  }

  /** {@inheritDoc} */
  @Override
  public Graphics create(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).create(x, y, width, height);
    }
    return super.create(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public void clipRect(final double x, final double y, final double width,
      final double height) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).clipRect(x, y, width, height);
    } else {
      super.clipRect(x, y, width, height);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void setClip(final double x, final double y, final double width,
      final double height) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).setClip(x, y, width, height);
    } else {
      super.setClip(x, y, width, height);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void copyArea(final double x, final double y, final double width,
      final double height, final double dx, final double dy) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).copyArea(x, y, width, height, dx, dy);
    } else {
      super.copyArea(x, y, width, height, dx, dy);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void drawLine(final double x1, final double y1, final double x2,
      final double y2) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawLine(x1, y1, x2, y2);
    } else {
      super.drawLine(x1, y1, x2, y2);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void fillRect(final double x, final double y, final double width,
      final double height) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).fillRect(x, y, width, height);
    } else {
      super.fillRect(x, y, width, height);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void drawRect(final double x, final double y, final double width,
      final double height) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawRect(x, y, width, height);
    } else {
      super.drawRect(x, y, width, height);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void clearRect(final double x, final double y,
      final double width, final double height) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).clearRect(x, y, width, height);
    } else {
      super.clearRect(x, y, width, height);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void drawRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawRoundRect(x, y, width, height,
          arcWidth, arcHeight);
    } else {
      super.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void fillRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).fillRoundRect(x, y, width, height,
          arcWidth, arcHeight);
    } else {
      super.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void drawOval(final double x, final double y, final double width,
      final double height) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawOval(x, y, width, height);
    } else {
      super.drawOval(x, y, width, height);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void fillOval(final double x, final double y, final double width,
      final double height) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).fillOval(x, y, width, height);
    } else {
      super.fillOval(x, y, width, height);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void drawArc(final double x, final double y, final double width,
      final double height, final double startAngle, final double arcAngle) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawArc(x, y, width, height, startAngle,
          arcAngle);
    } else {
      super.drawArc(x, y, width, height, startAngle, arcAngle);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void fillArc(final double x, final double y, final double width,
      final double height, final double startAngle, final double arcAngle) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).fillArc(x, y, width, height, startAngle,
          arcAngle);
    } else {
      super.fillArc(x, y, width, height, startAngle, arcAngle);
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
  public void drawPolyline(final double xPoints[], final double yPoints[],
      final int nPoints) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawPolyline(xPoints, yPoints, nPoints);
    } else {
      super.drawPolyline(xPoints, yPoints, nPoints);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void drawPolygon(final double xPoints[], final double yPoints[],
      final int nPoints) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawPolygon(xPoints, yPoints, nPoints);
    } else {
      super.drawPolygon(xPoints, yPoints, nPoints);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void fillPolygon(final double xPoints[], final double yPoints[],
      final int nPoints) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).fillPolygon(xPoints, yPoints, nPoints);
    } else {
      super.fillPolygon(xPoints, yPoints, nPoints);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void drawChars(final char data[], final int offset,
      final int length, final double x, final double y) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      ((Graphic) (this.m_out)).drawChars(data, offset, length, x, y);
    } else {
      super.drawChars(data, offset, length, x, y);
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final double x,
      final double y, final ImageObserver observer) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).drawImage(img, x, y, observer);
    }
    return super.drawImage(img, x, y, observer);
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final ImageObserver observer) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).drawImage(img, x, y, width, height,
          observer);
    }
    return super.drawImage(img, x, y, width, height, observer);
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final double x,
      final double y, final Color bgcolor, final ImageObserver observer) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).drawImage(img, x, y, bgcolor,
          observer);
    }
    return super.drawImage(img, x, y, bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final Color bgcolor, final ImageObserver observer) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).drawImage(img, x, y, width, height,
          bgcolor, observer);
    }
    return super.drawImage(img, x, y, width, height, bgcolor, observer);
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final ImageObserver observer) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).drawImage(img, dx1, dy1, dx2, dy2,
          sx1, sy1, sx2, sy2, observer);
    }
    return super.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2,
        observer);
  }

  /** {@inheritDoc} */
  @Override
  public boolean drawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final Color bgcolor, final ImageObserver observer) {
    this.checkClosed();
    if (this.m_out instanceof Graphic) {
      return ((Graphic) (this.m_out)).drawImage(img, dx1, dy1, dx2, dy2,
          sx1, sy1, sx2, sy2, bgcolor, observer);
    }
    return super.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2,
        bgcolor, observer);
  }

}
