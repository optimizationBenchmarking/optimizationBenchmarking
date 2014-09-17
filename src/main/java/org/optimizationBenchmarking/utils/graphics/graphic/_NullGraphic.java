package org.optimizationBenchmarking.utils.graphics.graphic;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import org.optimizationBenchmarking.utils.document.IObjectListener;

/**
 * A graphic which simply discards all of its output. This object is
 * designed for high-throughput and low memory footprint.
 */
final class _NullGraphic extends Graphic {

  /** the internal basic composite */
  private static Composite s_CMP = null;
  /** the internal basic stroke */
  private static Stroke s_STR = null;
  /** the basic font render context */
  private static FontRenderContext s_FRC = null;
  /** the basic font */
  private static Font s_F = null;

  /** the bounds */
  private final Rectangle m_bounds;
  /** the device configuration */
  private _Config m_cfg;
  /** the transformation */
  private AffineTransform m_trafo;
  /** the composite */
  private Composite m_composite;
  /** the paint */
  private Paint m_paint;
  /** the color */
  private Color m_color;
  /** the background color */
  private Color m_bgcolor;
  /** the stroke */
  private Stroke m_stroke;
  /** the clip */
  private Shape m_clip;
  /** the hints */
  private RenderingHints m_hints;
  /** the font */
  private Font m_font;

  /**
   * create the null graphic
   * 
   * @param listener
   *          the listener
   * @param size
   *          the size
   */
  _NullGraphic(final IObjectListener listener, final Dimension2D size) {
    super(listener, null);

    this.m_bounds = new Rectangle(0, 0,//
        Math.max(1, ((int) (0.5d + size.getWidth()))),//
        Math.max(1, ((int) (0.5d + size.getHeight()))));

    this.m_paint = this.m_color = Color.black;
    this.m_bgcolor = Color.white;
    this.m_clip = this.m_bounds;
  }

  /**
   * get the font render context
   * 
   * @return the font render context
   */
  static final FontRenderContext _frc() {
    if (_NullGraphic.s_FRC == null) {
      _NullGraphic.s_FRC = new FontRenderContext(null, false, false);
    }
    return _NullGraphic.s_FRC;
  }

  /**
   * get the basic stroke
   * 
   * @return the basic stroke
   */
  private static final Stroke __bs() {
    if (_NullGraphic.s_STR == null) {
      _NullGraphic.s_STR = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
    }
    return _NullGraphic.s_STR;
  }

  /**
   * get the basic composite
   * 
   * @return the basic composite
   */
  private static final Composite __bc() {
    if (_NullGraphic.s_CMP == null) {
      _NullGraphic.s_CMP = AlphaComposite
          .getInstance(AlphaComposite.SRC_OVER);
    }
    return _NullGraphic.s_CMP;
  }

  /**
   * get the default font
   * 
   * @return the default font
   */
  private static final Font __f() {
    if (_NullGraphic.s_F == null) {
      _NullGraphic.s_F = Font.decode(Font.DIALOG);
    }
    return _NullGraphic.s_F;
  }

  /** {@inheritDoc} */
  @Override
  public final Rectangle getBounds() {
    return ((Rectangle) (this.m_bounds.clone()));
  }

  /** {@inheritDoc} */
  @Override
  public final void draw(final Shape s) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img,
      final AffineTransform xform, final ImageObserver obs) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final void drawImage(final BufferedImage img,
      final BufferedImageOp op, final int x, final int y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawRenderedImage(final RenderedImage img,
      final AffineTransform xform) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawRenderableImage(final RenderableImage img,
      final AffineTransform xform) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawString(final String str, final int x, final int y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawString(final String str, final float x,
      final float y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawString(final AttributedCharacterIterator iterator,
      final int x, final int y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawString(final AttributedCharacterIterator iterator,
      final float x, final float y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawGlyphVector(final GlyphVector g, final float x,
      final float y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void fill(final Shape s) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hit(final Rectangle rect, final Shape s,
      final boolean onStroke) {
    return s.intersects(rect);
  }

  /** {@inheritDoc} */
  @Override
  public final GraphicsConfiguration getDeviceConfiguration() {
    if (this.m_cfg == null) {
      this.m_cfg = new _Config();
    }
    return this.m_cfg;
  }

  /** {@inheritDoc} */
  @Override
  public final void setComposite(final Composite comp) {
    this.m_composite = comp;
  }

  /** {@inheritDoc} */
  @Override
  public final void setPaint(final Paint paint) {
    this.m_paint = paint;
  }

  /** {@inheritDoc} */
  @Override
  public final void setStroke(final Stroke s) {
    this.m_stroke = s;
  }

  /** {@inheritDoc} */
  @Override
  public final void setRenderingHint(final Key hintKey,
      final Object hintValue) {
    this.getRenderingHints().put(hintKey, hintValue);
  }

  /** {@inheritDoc} */
  @Override
  public final Object getRenderingHint(final Key hintKey) {
    return this.getRenderingHints().get(hintKey);
  }

  /** {@inheritDoc} */
  @Override
  public final void setRenderingHints(final Map<?, ?> hints) {
    this.getRenderingHints().putAll(hints);
  }

  /** {@inheritDoc} */
  @Override
  public final void addRenderingHints(final Map<?, ?> hints) {
    this.getRenderingHints().putAll(hints);
  }

  /** {@inheritDoc} */
  @Override
  public final RenderingHints getRenderingHints() {
    if (this.m_hints == null) {
      this.m_hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
          RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    return this.m_hints;
  }

  /**
   * get the affine transform
   * 
   * @return the transform
   */
  private final AffineTransform __gt() {
    if (this.m_trafo == null) {
      this.m_trafo = new AffineTransform();
    }
    return this.m_trafo;
  }

  /** {@inheritDoc} */
  @Override
  public final void translate(final int x, final int y) {
    this.__gt().translate(x, y);
  }

  /** {@inheritDoc} */
  @Override
  public final void translate(final double tx, final double ty) {
    this.__gt().translate(tx, ty);
  }

  /** {@inheritDoc} */
  @Override
  public final void rotate(final double theta) {
    this.__gt().rotate(theta);
  }

  /** {@inheritDoc} */
  @Override
  public final void rotate(final double theta, final double x,
      final double y) {
    this.__gt().rotate(theta, x, y);
  }

  /** {@inheritDoc} */
  @Override
  public final void scale(final double sx, final double sy) {
    this.__gt().scale(sx, sy);
  }

  /** {@inheritDoc} */
  @Override
  public final void shear(final double shx, final double shy) {
    this.__gt().shear(shx, shy);
  }

  /** {@inheritDoc} */
  @Override
  public final void transform(final AffineTransform Tx) {
    this.__gt().concatenate(Tx);
  }

  /** {@inheritDoc} */
  @Override
  public final void setTransform(final AffineTransform Tx) {
    this.__gt().setTransform(Tx);
  }

  /** {@inheritDoc} */
  @Override
  public final AffineTransform getTransform() {
    return ((AffineTransform) (this.__gt().clone()));
  }

  /** {@inheritDoc} */
  @Override
  public final Paint getPaint() {
    return this.m_paint;
  }

  /** {@inheritDoc} */
  @Override
  public final Composite getComposite() {
    if (this.m_composite == null) {
      this.m_composite = _NullGraphic.__bc();
    }
    return this.m_composite;
  }

  /** {@inheritDoc} */
  @Override
  public final void setBackground(final Color color) {
    this.m_bgcolor = color;
  }

  /** {@inheritDoc} */
  @Override
  public final Color getBackground() {
    return this.m_bgcolor;
  }

  /** {@inheritDoc} */
  @Override
  public final Stroke getStroke() {
    if (this.m_stroke == null) {
      this.m_stroke = _NullGraphic.__bs();
    }
    return this.m_stroke;
  }

  /** {@inheritDoc} */
  @Override
  public final void clip(final Shape s) {
    this.m_clip = s;
  }

  /** {@inheritDoc} */
  @Override
  public final FontRenderContext getFontRenderContext() {
    return _NullGraphic._frc();
  }

  /** {@inheritDoc} */
  @Override
  public final Graphics create() {
    return new _NullGraphic(null, new Dimension(this.m_bounds.width,
        this.m_bounds.height));
  }

  /** {@inheritDoc} */
  @Override
  public final Color getColor() {
    return this.m_color;
  }

  /** {@inheritDoc} */
  @Override
  public final void setColor(final Color c) {
    this.m_paint = this.m_color = c;
  }

  /** {@inheritDoc} */
  @Override
  public final void setPaintMode() {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void setXORMode(final Color c1) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final Font getFont() {
    if (this.m_font == null) {
      this.m_font = _NullGraphic.__f();
    }
    return this.m_font;
  }

  /** {@inheritDoc} */
  @Override
  public final void setFont(final Font font) {
    this.m_font = font;
  }

  /** {@inheritDoc} */
  @Override
  public final FontMetrics getFontMetrics(final Font f) {
    return new _DummyFM(f);
  }

  /** {@inheritDoc} */
  @Override
  public final Rectangle getClipBounds() {
    return this.m_clip.getBounds();
  }

  /** {@inheritDoc} */
  @Override
  public final void clipRect(final int x, final int y, final int width,
      final int height) {
    this.m_clip = new Rectangle(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public final void setClip(final int x, final int y, final int width,
      final int height) {
    this.m_clip = new Rectangle(x, y, width, height);

  }

  /** {@inheritDoc} */
  @Override
  public final Shape getClip() {
    return this.m_clip;
  }

  /** {@inheritDoc} */
  @Override
  public final void setClip(final Shape clip) {
    this.m_clip = clip;
  }

  /** {@inheritDoc} */
  @Override
  public final void copyArea(final int x, final int y, final int width,
      final int height, final int dx, final int dy) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawLine(final int x1, final int y1, final int x2,
      final int y2) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void fillRect(final int x, final int y, final int width,
      final int height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void clearRect(final int x, final int y, final int width,
      final int height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void fillRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawOval(final int x, final int y, final int width,
      final int height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void fillOval(final int x, final int y, final int width,
      final int height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawArc(final int x, final int y, final int width,
      final int height, final int startAngle, final int arcAngle) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void fillArc(final int x, final int y, final int width,
      final int height, final int startAngle, final int arcAngle) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawPolyline(final int[] xPoints, final int[] yPoints,
      final int nPoints) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawPolygon(final int[] xPoints, final int[] yPoints,
      final int nPoints) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void fillPolygon(final int[] xPoints, final int[] yPoints,
      final int nPoints) {
    // TODO Auto-generated method stub
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final int x,
      final int y, final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final int x,
      final int y, final int width, final int height,
      final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final int x,
      final int y, final Color bgcolor, final ImageObserver observer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean drawImage(final Image img, final int x, final int y,
      final int width, final int height, final Color bgcolor,
      final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2,
      final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2, final Color bgcolor,
      final ImageObserver observer) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final void draw3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void fill3DRect(final double x, final double y,
      final double width, final double height, final boolean raised) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawString(final String str, final double x,
      final double y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawString(final AttributedCharacterIterator iterator,
      final double x, final double y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawGlyphVector(final GlyphVector g, final double x,
      final double y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final Graphics create(final double x, final double y,
      final double width, final double height) {
    _NullGraphic g;
    g = ((_NullGraphic) (this.create()));
    g.m_bounds.setBounds(((int) x), ((int) y), ((int) width),
        ((int) height));
    return g;
  }

  /** {@inheritDoc} */
  @Override
  public final void clipRect(final double x, final double y,
      final double width, final double height) {
    this.clipRect(((int) x), ((int) y), ((int) width), ((int) height));
  }

  /** {@inheritDoc} */
  @Override
  public final void setClip(final double x, final double y,
      final double width, final double height) {
    this.setClip(((int) x), ((int) y), ((int) width), ((int) height));
  }

  /** {@inheritDoc} */
  @Override
  public final void copyArea(final double x, final double y,
      final double width, final double height, final double dx,
      final double dy) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawLine(final double x1, final double y1,
      final double x2, final double y2) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void fillRect(final double x, final double y,
      final double width, final double height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawRect(final double x, final double y,
      final double width, final double height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void clearRect(final double x, final double y,
      final double width, final double height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void fillRoundRect(final double x, final double y,
      final double width, final double height, final double arcWidth,
      final double arcHeight) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawOval(final double x, final double y,
      final double width, final double height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void fillOval(final double x, final double y,
      final double width, final double height) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawArc(final double x, final double y,
      final double width, final double height, final double startAngle,
      final double arcAngle) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void fillArc(final double x, final double y,
      final double width, final double height, final double startAngle,
      final double arcAngle) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected Shape createShape(final double[] xPoints,
      final double[] yPoints, final int nPoints, final boolean close) {
    return ((Shape) (this.m_bounds.clone()));
  }

  /** {@inheritDoc} */
  @Override
  public final void drawPolyline(final double xPoints[],
      final double yPoints[], final int nPoints) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawPolygon(final double xPoints[],
      final double yPoints[], final int nPoints) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void fillPolygon(final double xPoints[],
      final double yPoints[], final int nPoints) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final void drawChars(final char data[], final int offset,
      final int length, final double x, final double y) {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final double x,
      final double y, final ImageObserver observer) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final double x,
      final double y, final Color bgcolor, final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final double x,
      final double y, final double width, final double height,
      final Color bgcolor, final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final ImageObserver observer) {
    return false; // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final boolean drawImage(final Image img, final double dx1,
      final double dy1, final double dx2, final double dy2,
      final double sx1, final double sy1, final double sx2,
      final double sy2, final Color bgcolor, final ImageObserver observer) {
    return false; // do nothing
  }

  /** the internal configuration */
  private final class _Config extends GraphicsConfiguration {

    /** the device */
    private _Device m_d;

    /** create */
    _Config() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final GraphicsDevice getDevice() {
      if (this.m_d == null) {
        this.m_d = new _Device();
      }
      return this.m_d;
    }

    /** {@inheritDoc} */
    @Override
    public final ColorModel getColorModel() {
      return ColorModel.getRGBdefault();
    }

    /** {@inheritDoc} */
    @Override
    public final ColorModel getColorModel(final int transparency) {
      return this.getColorModel();
    }

    /** {@inheritDoc} */
    @Override
    public final AffineTransform getDefaultTransform() {
      return new AffineTransform();
    }

    /** {@inheritDoc} */
    @Override
    public final AffineTransform getNormalizingTransform() {
      return new AffineTransform();
    }

    /** {@inheritDoc} */
    @Override
    public final Rectangle getBounds() {
      return _NullGraphic.this.getBounds();
    }

    /** the internal device */
    private final class _Device extends GraphicsDevice {

      /** create */
      _Device() {
        super();
      }

      /** {@inheritDoc} */
      @Override
      public final int getType() {
        return GraphicsDevice.TYPE_IMAGE_BUFFER;
      }

      /** {@inheritDoc} */
      @Override
      public final String getIDstring() {
        return ("nullGraphicDevice" + this.hashCode()); //$NON-NLS-1$
      }

      /** {@inheritDoc} */
      @Override
      public final GraphicsConfiguration[] getConfigurations() {
        return new GraphicsConfiguration[] { _Config.this };
      }

      /** {@inheritDoc} */
      @Override
      public final GraphicsConfiguration getDefaultConfiguration() {
        return _Config.this;
      }

    }

  }

  /** the dummy font metrics */
  private static final class _DummyFM extends FontMetrics {

    /** the dummy fm's serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * create
     * 
     * @param f
     *          the font
     */
    _DummyFM(final Font f) {
      super(f);
    }

    /** {@inheritDoc} */
    @Override
    public final FontRenderContext getFontRenderContext() {
      return _NullGraphic._frc();
    }

  }
}
