package org.optimizationBenchmarking.utils.graphics.graphic.impl;

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
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

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
   * @param logger
   *          the logger
   * @param listener
   *          the listener
   * @param size
   *          the size
   */
  _NullGraphic(final Logger logger, final IFileProducerListener listener,
      final Dimension2D size) {
    super(logger, listener, null);

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
  protected final void doSetComposite(final Composite comp) {
    this.m_composite = comp;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetPaint(final Paint paint) {
    this.m_paint = paint;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetStroke(final Stroke s) {
    this.m_stroke = s;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetRenderingHint(final Key hintKey,
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
  protected final void doSetRenderingHints(final Map<?, ?> hints) {
    this.getRenderingHints().putAll(hints);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doAddRenderingHints(final Map<?, ?> hints) {
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
  protected final void doTranslate(final int x, final int y) {
    this.__gt().translate(x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doTranslate(final double tx, final double ty) {
    this.__gt().translate(tx, ty);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doRotate(final double theta) {
    this.__gt().rotate(theta);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doRotate(final double theta, final double x,
      final double y) {
    this.__gt().rotate(theta, x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doScale(final double sx, final double sy) {
    this.__gt().scale(sx, sy);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doShear(final double shx, final double shy) {
    this.__gt().shear(shx, shy);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doTransform(final AffineTransform Tx) {
    this.__gt().concatenate(Tx);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetTransform(final AffineTransform Tx) {
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
  protected final void doSetBackground(final Color color) {
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
  protected final void doClip(final Shape s) {
    this.m_clip = s;
  }

  /** {@inheritDoc} */
  @Override
  public final FontRenderContext getFontRenderContext() {
    return _NullGraphic._frc();
  }

  /** {@inheritDoc} */
  @Override
  protected final Graphics doCreate() {
    return new _NullGraphic(null, null, new Dimension(this.m_bounds.width,
        this.m_bounds.height));
  }

  /** {@inheritDoc} */
  @Override
  public final Color getColor() {
    return this.m_color;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetColor(final Color c) {
    this.m_paint = this.m_color = c;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetPaintMode() {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetXORMode(final Color c1) {
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
  protected final void doSetFont(final Font font) {
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
  protected final void doClipRect(final int x, final int y,
      final int width, final int height) {
    this.m_clip = new Rectangle(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetClip(final int x, final int y,
      final int width, final int height) {
    this.m_clip = new Rectangle(x, y, width, height);

  }

  /** {@inheritDoc} */
  @Override
  public final Shape getClip() {
    return this.m_clip;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetClip(final Shape clip) {
    this.m_clip = clip;
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
    _NullGraphic g;
    g = ((_NullGraphic) (this.create()));
    g.m_bounds.setBounds(((int) x), ((int) y), ((int) width),
        ((int) height));
    return g;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doClipRect(final double x, final double y,
      final double width, final double height) {
    this.clipRect(((int) x), ((int) y), ((int) width), ((int) height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetClip(final double x, final double y,
      final double width, final double height) {
    this.setClip(((int) x), ((int) y), ((int) width), ((int) height));
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
  public Shape createShape(final double[] xPoints, final double[] yPoints,
      final int nPoints, final boolean close) {
    return ((Shape) (this.m_bounds.clone()));
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

    /** {@inheritDoc} */
    @Override
    public final int stringWidth(final String str) {
      return (str.length() << 3);
    }

    /** {@inheritDoc} */
    @Override
    public final int charsWidth(final char data[], final int off,
        final int len) {
      return (len << 3);
    }
  }
}
