package org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A graphic which simply discards all of its output. This object is
 * designed for high-throughput and low memory footprint.
 */
public abstract class SimpleGraphic extends Graphic {

  /** the default foreground color */
  private static final Color DEFAULT_FOREGROUND_COLOR = Color.BLACK;
  /** the default background color */
  private static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;

  /** the basic stroke */
  static Stroke s_default_stroke;

  /** the default composite */
  static Composite s_default_composite;

  /** the default font */
  static Font s_default_font;

  /** the default font render context */
  static FontRenderContext s_default_font_render_context;

  /** the width */
  protected final int m_width;
  /** the height */
  protected final int m_height;
  /** the device configuration */
  private GraphicsConfiguration m_config;
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
  /** the font render context */
  private FontRenderContext m_fontRenderContext;

  /**
   * create the null graphic
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
  protected SimpleGraphic(final Logger logger,
      final IFileProducerListener listener, final Path path,
      final int width, final int height) {
    super(logger, listener, path);

    if ((width <= 0) || (height <= 0)) {
      throw new IllegalArgumentException(((((((//
          TextUtils.className(this.getClass()) + " must have non-empty dimensions, but has (") + width) //$NON-NLS-1$
          + ',') + ' ') + height) + ')') + '.');
    }

    this.m_width = width;
    this.m_height = height;

    this.m_paint = this.m_color = DEFAULT_FOREGROUND_COLOR;
    this.m_bgcolor = DEFAULT_BACKGROUND_COLOR;
    this.m_clip = new Rectangle(0, 0, this.m_width, this.m_height);
  }

  /** {@inheritDoc} */
  @Override
  public Rectangle getBounds() {
    return new Rectangle(0, 0, this.m_width, this.m_height);
  }

  /** {@inheritDoc} */
  @Override
  public boolean hit(final Rectangle rect, final Shape s,
      final boolean onStroke) {
    return s.intersects(rect);
  }

  /**
   * Create the graphics device configuration
   * 
   * @return the graphics device configuration
   */
  protected GraphicsConfiguration createDeviceConfiguration() {
    return new __InternalGraphicsConfiguration();
  }

  /** {@inheritDoc} */
  @Override
  public GraphicsConfiguration getDeviceConfiguration() {
    if (this.m_config == null) {
      this.m_config = this.createDeviceConfiguration();
    }
    return this.m_config;
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetComposite(final Composite comp) {
    this.m_composite = comp;
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetPaint(final Paint paint) {
    this.m_paint = paint;
    if (paint instanceof Color) {
      this.m_color = ((Color) paint);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetStroke(final Stroke s) {
    this.m_stroke = s;
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetRenderingHint(final Key hintKey,
      final Object hintValue) {
    this.getRenderingHints().put(hintKey, hintValue);
  }

  /** {@inheritDoc} */
  @Override
  public Object getRenderingHint(final Key hintKey) {
    return this.getRenderingHints().get(hintKey);
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetRenderingHints(final Map<?, ?> hints) {
    this.getRenderingHints().putAll(hints);
  }

  /** {@inheritDoc} */
  @Override
  protected void doAddRenderingHints(final Map<?, ?> hints) {
    this.getRenderingHints().putAll(hints);
  }

  /**
   * Create the default rendering hints for this graphic.
   * 
   * @return the default rendering hints for this graphic
   */
  protected RenderingHints createRenderingHints() {
    return new RenderingHints(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_OFF);
  }

  /** {@inheritDoc} */
  @Override
  public final RenderingHints getRenderingHints() {
    if (this.m_hints == null) {
      this.m_hints = this.createRenderingHints();
    }
    return this.m_hints;
  }

  /**
   * get the affine transform
   *
   * @return the transform
   */
  private final AffineTransform __getTransform() {
    if (this.m_trafo == null) {
      this.m_trafo = new AffineTransform();
    }
    return this.m_trafo;
  }

  /** {@inheritDoc} */
  @Override
  protected void doTranslate(final int x, final int y) {
    this.__getTransform().translate(x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected void doTranslate(final double tx, final double ty) {
    this.__getTransform().translate(tx, ty);
  }

  /** {@inheritDoc} */
  @Override
  protected void doRotate(final double theta) {
    this.__getTransform().rotate(theta);
  }

  /** {@inheritDoc} */
  @Override
  protected void doRotate(final double theta, final double x,
      final double y) {
    this.__getTransform().rotate(theta, x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected void doScale(final double sx, final double sy) {
    this.__getTransform().scale(sx, sy);
  }

  /** {@inheritDoc} */
  @Override
  protected void doShear(final double shx, final double shy) {
    this.__getTransform().shear(shx, shy);
  }

  /** {@inheritDoc} */
  @Override
  protected void doTransform(final AffineTransform Tx) {
    this.__getTransform().concatenate(Tx);
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetTransform(final AffineTransform Tx) {
    this.__getTransform().setTransform(Tx);
  }

  /** {@inheritDoc} */
  @Override
  public final AffineTransform getTransform() {
    return ((AffineTransform) (this.__getTransform().clone()));
  }

  /** {@inheritDoc} */
  @Override
  public final Paint getPaint() {
    return this.m_paint;
  }

  /**
   * create the default composite for this graphic
   *
   * @return the basic composite
   */
  protected Composite createComposite() {
    if (SimpleGraphic.s_default_composite == null) {
      SimpleGraphic.s_default_composite = AlphaComposite.SrcOver;
    }
    return SimpleGraphic.s_default_composite;
  }

  /** {@inheritDoc} */
  @Override
  public final Composite getComposite() {
    if (this.m_composite == null) {
      this.m_composite = this.createComposite();
    }
    return this.m_composite;
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetBackground(final Color color) {
    this.m_bgcolor = color;
  }

  /** {@inheritDoc} */
  @Override
  public final Color getBackground() {
    return this.m_bgcolor;
  }

  /**
   * create the default stroke for this graphic
   *
   * @return the basic stroke
   */
  protected Stroke createStroke() {
    if (SimpleGraphic.s_default_stroke == null) {
      SimpleGraphic.s_default_stroke = new BasicStroke(1.0f,
          BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, null,
          0.0f);
    }
    return SimpleGraphic.s_default_stroke;
  }

  /** {@inheritDoc} */
  @Override
  public Stroke getStroke() {
    if (this.m_stroke == null) {
      this.m_stroke = this.createStroke();
    }
    return this.m_stroke;
  }

  /** {@inheritDoc} */
  @Override
  protected void doClip(final Shape s) {
    this.m_clip = s;
  }

  /**
   * create the default font render context for this graphic
   *
   * @return the font render context
   */
  protected FontRenderContext createFontRenderContext() {
    if (SimpleGraphic.s_default_font_render_context == null) {
      SimpleGraphic.s_default_font_render_context = new FontRenderContext(
          null, true, true);
    }
    return SimpleGraphic.s_default_font_render_context;
  }

  /** {@inheritDoc} */
  @Override
  public final FontRenderContext getFontRenderContext() {
    if (this.m_fontRenderContext == null) {
      this.m_fontRenderContext = this.createFontRenderContext();
    }
    return this.m_fontRenderContext;
  }

  /** {@inheritDoc} */
  @Override
  public final Color getColor() {
    return this.m_color;
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetColor(final Color c) {
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

  /**
   * Create the default font for this graphic
   *
   * @return the default font
   */
  protected Font createFont() {
    if (SimpleGraphic.s_default_font == null) {
      SimpleGraphic.s_default_font = new Font(null, Font.PLAIN, 10);
    }
    return SimpleGraphic.s_default_font;
  }

  /** {@inheritDoc} */
  @Override
  public final Font getFont() {
    if (this.m_font == null) {
      this.m_font = this.createFont();
    }
    return this.m_font;
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetFont(final Font font) {
    this.m_font = font;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("deprecation")
  @Override
  public final FontMetrics getFontMetrics(final Font f) {
    return Toolkit.getDefaultToolkit().getFontMetrics(f);
  }

  /** {@inheritDoc} */
  @Override
  public final Rectangle getClipBounds() {
    return this.m_clip.getBounds();
  }

  /** {@inheritDoc} */
  @Override
  protected void doClipRect(final int x, final int y, final int width,
      final int height) {
    this.doClipRect(((double) x), ((double) y), ((double) width),
        ((double) height));
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetClip(final int x, final int y, final int width,
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
  protected void doSetClip(final Shape clip) {
    this.m_clip = clip;
  }

  /** {@inheritDoc} */
  @Override
  protected void doClipRect(final double x, final double y,
      final double width, final double height) {
    final Rectangle2D bounds;

    if (this.m_clip instanceof Rectangle2D) {
      bounds = ((Rectangle2D) (this.m_clip));
    } else {
      bounds = this.m_clip.getBounds2D();

    }
    this.m_clip = bounds.createIntersection(new Rectangle2D.Double(x, y,
        width, height));
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetClip(final double x, final double y,
      final double width, final double height) {
    this.m_clip = new Rectangle2D.Double(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  public Shape createShape(final double[] xPoints, final double[] yPoints,
      final int nPoints, final boolean close) {
    final GeneralPath path;

    this.checkClosed();
    path = new GeneralPath(Path2D.WIND_EVEN_ODD);
    if (nPoints > 0) {
      path.moveTo(((float) (xPoints[0])), ((float) (yPoints[0])));
      for (int i = 1; i < nPoints; i++) {
        path.lineTo(((float) (xPoints[i])), ((float) (yPoints[i])));
      }
      if (close) {
        path.closePath();
      }
    }
    return path;
  }

  /**
   * Get the color model
   * 
   * @return the color model
   */
  protected ColorModel getColorModel() {
    return ColorModel.getRGBdefault();
  }

  /**
   * Get the color model
   * 
   * @param transparency
   *          the transparency
   * @return the color model
   */
  protected ColorModel getColorModel(final int transparency) {
    return this.getColorModel();
  }

  /**
   * Get the default transformation
   * 
   * @return the default transformation
   */
  protected AffineTransform getDefaultTransform() {
    return new AffineTransform();
  }

  /**
   * Get the normalizing transformation
   * 
   * @return the normalizing transformation
   */
  protected AffineTransform getNormalizingTransform() {
    return new AffineTransform();
  }

  /** the internal configuration */
  private final class __InternalGraphicsConfiguration extends
      GraphicsConfiguration {

    /** the device */
    private __InternalGraphicsDevice m_d;

    /** create */
    __InternalGraphicsConfiguration() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final GraphicsDevice getDevice() {
      if (this.m_d == null) {
        this.m_d = new __InternalGraphicsDevice();
      }
      return this.m_d;
    }

    /** {@inheritDoc} */
    @Override
    public final ColorModel getColorModel() {
      return SimpleGraphic.this.getColorModel();
    }

    /** {@inheritDoc} */
    @Override
    public final ColorModel getColorModel(final int transparency) {
      return SimpleGraphic.this.getColorModel(transparency);
    }

    /** {@inheritDoc} */
    @Override
    public final AffineTransform getDefaultTransform() {
      return SimpleGraphic.this.getDefaultTransform();
    }

    /** {@inheritDoc} */
    @Override
    public final AffineTransform getNormalizingTransform() {
      return SimpleGraphic.this.getNormalizingTransform();
    }

    /** {@inheritDoc} */
    @Override
    public final Rectangle getBounds() {
      return SimpleGraphic.this.getBounds();
    }

    /** the internal device */
    private final class __InternalGraphicsDevice extends GraphicsDevice {

      /** create */
      __InternalGraphicsDevice() {
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

        return (TextUtils.className(SimpleGraphic.this.getClass()) + //
            System.identityHashCode(SimpleGraphic.this) + //
        "Device"); //$NON-NLS-1$
      }

      /** {@inheritDoc} */
      @Override
      public final GraphicsConfiguration[] getConfigurations() {
        return new GraphicsConfiguration[] { __InternalGraphicsConfiguration.this };
      }

      /** {@inheritDoc} */
      @Override
      public final GraphicsConfiguration getDefaultConfiguration() {
        return __InternalGraphicsConfiguration.this;
      }
    }
  }
}
