package org.optimizationBenchmarking.utils.graphics.graphic.impl.pgf;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.SimpleGraphic;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.RadiansToDegrees;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.text.transformations.LaTeXCharTransformer;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/** The internal, un-simplified PGF graphic */
final class _PGFGraphic extends SimpleGraphic {

  /** begin the environment */
  private static final char[] PICTURE_BEGIN = { '\\', 'b', 'e', 'g', 'i',
    'n', '{', 'p', 'g', 'f', 'p', 'i', 'c', 't', 'u', 'r', 'e', };

  /** the post-amble */
  private static final char[] PICTURE_END = { '\\', 'e', 'n', 'd', '{',
    'p', 'g', 'f', 'p', 'i', 'c', 't', 'u', 'r', 'e', };

  /** begin using a path */
  private static final char[] USE_PATH_BEGIN = { '\\', 'p', 'g', 'f', 'u',
    's', 'e', 'p', 'a', 't', 'h', '{', };

  /** begin a rectangle path */
  private static final char[] PATH_RECTANGLE_BEGIN = { '\\', 'p', 'g',
    'f', 'p', 'a', 't', 'h', 'r', 'e', 'c', 't', 'a', 'n', 'g', 'l',
    'e', '{', };

  /** the start of a pgf point */
  private static final char[] POINT_ORIGIN = { '\\', 'p', 'g', 'f', 'p',
    'o', 'i', 'n', 't', 'o', 'r', 'i', 'g', 'i', 'n' };
  /** the start of a pgf point */
  private static final char[] POINT_BEGIN = { '\\', 'p', 'g', 'f', 'p',
    'o', 'i', 'n', 't', '{', };
  /** the point unit */
  private static final char[] PT = { 'p', 't' };

  /** the use a path as bounding box */
  private static final char[] USE_PATH_BOUNDING_BOX = { 'u', 's', 'e',
    ' ', 'a', 's', ' ', 'b', 'o', 'u', 'n', 'd', 'i', 'n', 'g', ' ',
    'b', 'o', 'x', ',', 'c', 'l', 'i', 'p', };
  /** fill a path */
  private static final char[] USE_PATH_FILL = { 'f', 'i', 'l', 'l' };
  /** stroke a path */
  private static final char[] USE_PATH_STROKE = { 's', 't', 'r', 'o', 'k',
  'e' };
  /** clip a path */
  private static final char[] USE_PATH_CLIP = { 'c', 'l', 'i', 'p' };

  /** the beginning of a new scope */
  private static final char[] SCOPE_BEGIN = { '\\', 'b', 'e', 'g', 'i',
    'n', '{', 'p', 'g', 'f', 's', 'c', 'o', 'p', 'e', };

  /** the end of a scope */
  private static final char[] SCOPE_END = { '\\', 'e', 'n', 'd', '{', 'p',
    'g', 'f', 's', 'c', 'o', 'p', 'e', };

  /** begin a canvas transform */
  private static final char[] CANVAS_TRANSFORM_BEGIN = { '\\', 'p', 'g',
    'f', 'l', 'o', 'w', 'l', 'e', 'v', 'e', 'l', '{', };

  /** begin a rotation */
  private static final char[] TRANSFORM_ROTATE_BEGIN = { '\\', 'p', 'g',
    'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r', 'm', 'r', 'o', 't',
    'a', 't', 'e', '{', };

  /** begin an scaling */
  private static final char[] TRANSFORM_SCALE_BEGIN = { '\\', 'p', 'g',
    'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r', 'm', 's', 'c', 'a',
    'l', 'e', '{', };

  /** begin an x-scaling */
  private static final char[] TRANSFORM_SCALE_X_BEGIN = { '\\', 'p', 'g',
    'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r', 'm', 'x', 's', 'c',
    'a', 'l', 'e', '{', };

  /** begin a y-scaling */
  private static final char[] TRANSFORM_SCALE_Y_BEGIN = { '\\', 'p', 'g',
    'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r', 'm', 'y', 's', 'c',
    'a', 'l', 'e', '{', };

  /** begin a shift */
  private static final char[] TRANSFORM_SHIFT_BEGIN = { '\\', 'p', 'g',
    'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r', 'm', 's', 'h', 'i',
    'f', 't', '{', };

  /** begin a x-shift */
  private static final char[] TRANSFORM_SHIFT_X_BEGIN = { '\\', 'p', 'g',
    'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r', 'm', 'x', 's', 'h',
    'i', 'f', 't', '{', };

  /** begin a y-shift */
  private static final char[] TRANSFORM_SHIFT_Y_BEGIN = { '\\', 'p', 'g',
    'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r', 'm', 'y', 's', 'h',
    'i', 'f', 't', '{', };

  /** begin a x-slant */
  private static final char[] TRANSFORM_SLANT_X_BEGIN = { '\\', 'p', 'g',
    'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r', 'm', 'x', 's', 'l',
    'a', 'n', 't', '{', };
  /** begin a y-slant */
  private static final char[] TRANSFORM_SLANT_Y_BEGIN = { '\\', 'p', 'g',
    'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r', 'm', 'y', 's', 'l',
    'a', 'n', 't', '{', };

  /** the prefix for defining a color */
  private static final char[] COLOR_PREFIX = { 't', '@' };
  /** begin defining a color */
  private static final char[] DEFINE_COLOR_BEGIN = { '\\', 'd', 'e', 'f',
    'i', 'n', 'e', 'c', 'o', 'l', 'o', 'r', '{' };
  /** begin defining a color in rgb */
  private static final char[] DEFINE_COLOR_RGB = { '}', '{', 'r', 'g',
    'b', '}', '{', };
  /** begin defining a color in gray */
  private static final char[] DEFINE_COLOR_GRAY = { '}', '{', 'g', 'r',
    'a', 'y', '}', '{', };

  /** set the color */
  private static final char[] SET_COLOR = { '\\', 'p', 'g', 'f', 's', 'e',
    't', 'c', 'o', 'l', 'o', 'r', '{', };
  /** set the fill opacity */
  private static final char[] SET_FILL_OPACITY = { '\\', 'p', 'g', 'f',
    's', 'e', 't', 'f', 'i', 'l', 'l', 'o', 'p', 'a', 'c', 'i', 't',
    'y', '{', };
  /** set the stroke opacity */
  private static final char[] SET_STROKE_OPACITY = { '\\', 'p', 'g', 'f',
    's', 'e', 't', 's', 't', 'r', 'o', 'k', 'e', 'o', 'p', 'a', 'c',
    'i', 't', 'y', '{', };

  /** path move to */
  private static final char[] PATH_MOVE_TO = { '\\', 'p', 'g', 'f', 'p',
    'a', 't', 'h', 'm', 'o', 'v', 'e', 't', 'o', '{' };
  /** path line to */
  private static final char[] PATH_LINE_TO = { '\\', 'p', 'g', 'f', 'p',
    'a', 't', 'h', 'l', 'i', 'n', 'e', 't', 'o', '{' };
  /** path curve to */
  private static final char[] PATH_CURVE_TO = { '\\', 'p', 'g', 'f', 'p',
    'a', 't', 'h', 'c', 'u', 'r', 'v', 'e', 't', 'o' };
  /** close the path */
  private static final char[] PATH_CLOSE = { '\\', 'p', 'g', 'f', 'p',
    'a', 't', 'h', 'c', 'l', 'o', 's', 'e' };
  /** path curve to */
  private static final char[] PATH_QUAD_TO = { '\\', 'p', 'g', 'f', 'p',
    'a', 't', 'h', 'q', 'u', 'a', 'd', 'r', 'a', 't', 'i', 'c', 'c',
    'u', 'r', 'v', 'e', 't', 'o', '{', };

  /** the winding rule */
  private static final char[] PATH_OE_WINDING_RULE = { '\\', 'p', 'g',
    'f', 's', 'e', 't', 'e', 'o', 'r', 'u', 'l', 'e' };
  /** the winding rule */
  private static final char[] PATH_NZ_WINDING_RULE = { '\\', 'p', 'g',
    'f', 's', 'e', 't', 'n', 'o', 'n', 'z', 'e', 'r', 'o', 'r', 'u',
    'l', 'e' };
  /** the graphic body */
  private MemoryTextOutput m_body;
  /** LaTeX encoded output */
  private ITextOutput m_encodedBody;
  /** are we in scope-reset mode? */
  private int m_inScopeReset;
  /** the state scopes */
  private final ArrayList<_PGFState> m_scopes;
  /** the active proxies */
  private final ArrayList<_PGFProxy> m_activeProxies;

  /** the registered colors */
  private HashMap<Color, char[]> m_colors;

  /**
   * instantiate
   *
   * @param listener
   *          the object to notify when we are closed, or {@code null} if
   *          none needs to be notified
   * @param path
   *          the path associated with this object, or {@code null} if no
   *          file needs to be explicitly created
   * @param logger
   *          the logger
   * @param width
   *          the width in pt
   * @param height
   *          the height in pt
   * @throws IOException
   *           if I/O fails
   */
  _PGFGraphic(final Logger logger, final IFileProducerListener listener,
      final Path path, final int width, final int height)
          throws IOException {
    super(logger, listener, path, width, height);

    this.m_body = new MemoryTextOutput();

    this.m_scopes = new ArrayList<>();
    this.m_activeProxies = new ArrayList<>();
    this.m_colors = new HashMap<>();
  }

  /**
   * get the name associated with a given color
   *
   * @param color
   *          the color
   * @return the color name
   */
  private final char[] __getColorName(final Color color) {
    MemoryTextOutput mto;
    char[] name;
    int size, mod;

    name = this.m_colors.get(color);
    if (name != null) {
      return name;
    }

    size = this.m_colors.size();
    mto = new MemoryTextOutput();
    mto.append(_PGFGraphic.COLOR_PREFIX);
    while (size != 0) {
      mod = Math.abs(size % 52);
      size /= 52;
      if (mod < 26) {
        mto.append((char) ('a' + mod));
      } else {
        mto.append((char) (('A' + mod) - 26));
      }
    }
    name = mto.toChars();
    this.m_colors.put(color, name);
    return name;
  }

  /**
   * flush all colors
   *
   * @param dest
   *          the destination to flush to
   */
  private final void __flushColors(final ITextOutput dest) {
    Color color;
    int red, green, blue;

    for (final Map.Entry<Color, char[]> entry : this.m_colors.entrySet()) {
      dest.append(_PGFGraphic.DEFINE_COLOR_BEGIN);
      dest.append(entry.getValue());
      color = entry.getKey();
      red = color.getRed();
      green = color.getGreen();
      blue = color.getBlue();

      if ((red == green) && (green == blue)) {
        dest.append(_PGFGraphic.DEFINE_COLOR_GRAY);
        _PGFGraphic.__number((red / 255d), dest);
      } else {
        dest.append(_PGFGraphic.DEFINE_COLOR_RGB);
        _PGFGraphic.__number((red / 255d), dest);
        dest.append(',');
        _PGFGraphic.__number((green / 255d), dest);
        dest.append(',');
        _PGFGraphic.__number((blue / 255d), dest);
      }

      _PGFGraphic.__commandEndNL(dest);
    }
    this.m_colors = null;
  }

  /**
   * append a {@code double} to the given text output
   *
   * @param d
   *          the double
   * @param textOut
   *          the text output
   */
  private static final void __number(final double d,
      final ITextOutput textOut) {
    if (NumericalTypes.isLong(d)) {
      textOut.append((long) d);
    } else {
      SimpleNumberAppender.INSTANCE.appendTo(d, ETextCase.IN_SENTENCE,
          textOut);
    }
  }

  /**
   * append a {@code double} in points to the given text output
   *
   * @param d
   *          the double
   * @param textOut
   *          the text output
   */
  private static final void __pt(final double d, final ITextOutput textOut) {
    _PGFGraphic.__number(d, textOut);
    textOut.append(_PGFGraphic.PT);
  }

  /**
   * append a {@code long} to the given text output
   *
   * @param d
   *          the long
   * @param textOut
   *          the text output
   */
  private static final void __number(final long d,
      final ITextOutput textOut) {
    textOut.append(d);
  }

  /**
   * append a {@code long} in points to the given text output
   *
   * @param d
   *          the double
   * @param textOut
   *          the text output
   */
  private static final void __pt(final long d, final ITextOutput textOut) {
    _PGFGraphic.__number(d, textOut);
    textOut.append(_PGFGraphic.PT);
  }

  /**
   * Write a pgf point to the given output
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param textOut
   *          the text output
   */
  private static final void __pgfPoint(final double x, final double y,
      final ITextOutput textOut) {
    if ((x == 0d) && (y == 0d)) {
      textOut.append(_PGFGraphic.POINT_ORIGIN);
    } else {
      textOut.append(_PGFGraphic.POINT_BEGIN);
      _PGFGraphic.__pt(x, textOut);
      textOut.append('}');
      textOut.append('{');
      _PGFGraphic.__pt(y, textOut);
      _PGFGraphic.__commandEnd(textOut);
    }
  }

  /**
   * Write a pgf point to the given output
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param textOut
   *          the text output
   */
  private static final void __pgfPoint(final long x, final long y,
      final ITextOutput textOut) {
    if ((x == 0) && (y == 0)) {
      textOut.append(_PGFGraphic.POINT_ORIGIN);
    } else {
      textOut.append(_PGFGraphic.POINT_BEGIN);
      _PGFGraphic.__pt(x, textOut);
      textOut.append('}');
      textOut.append('{');
      _PGFGraphic.__pt(y, textOut);
      _PGFGraphic.__commandEnd(textOut);
    }
  }

  /**
   * create a rectangular path
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param textOut
   *          the destination
   */
  private static final void __pgfRectangle(final int x, final int y,
      final int width, final int height, final ITextOutput textOut) {
    textOut.append(_PGFGraphic.PATH_RECTANGLE_BEGIN);
    _PGFGraphic.__pgfPoint(x, y, textOut);
    textOut.append('}');
    textOut.append('{');
    _PGFGraphic.__pgfPoint(width, height, textOut);
    _PGFGraphic.__commandEndNL(textOut);
  }

  /**
   * create a rectangular path
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   * @param width
   *          the width
   * @param height
   *          the height
   * @param textOut
   *          the destination
   */
  private static final void __pgfRectangle(final double x, final double y,
      final double width, final double height, final ITextOutput textOut) {
    textOut.append(_PGFGraphic.PATH_RECTANGLE_BEGIN);
    _PGFGraphic.__pgfPoint(x, y, textOut);
    textOut.append('}');
    textOut.append('{');
    _PGFGraphic.__pgfPoint(width, height, textOut);
    _PGFGraphic.__commandEndNL(textOut);
  }

  /**
   * End a command
   *
   * @param textOut
   *          the text output
   */
  private static final void __commandEnd(final ITextOutput textOut) {
    textOut.append('}');
  }

  /**
   * End a command and write a newline
   *
   * @param textOut
   *          the text output
   */
  private static final void __commandEndNL(final ITextOutput textOut) {
    _PGFGraphic.__commandEnd(textOut);
    _PGFGraphic.__nl(textOut);
  }

  /**
   * write a newline
   *
   * @param textOut
   *          the text output
   */
  private static final void __nl(final ITextOutput textOut) {
    textOut.append('%');
    textOut.appendLineBreak();
  }

  /**
   * use a path for something
   *
   * @param as
   *          for what?
   * @param textOut
   *          the text output
   */
  private static final void __pgfUsePath(final char[] as,
      final ITextOutput textOut) {
    textOut.append(_PGFGraphic.USE_PATH_BEGIN);
    textOut.append(as);
    _PGFGraphic.__commandEndNL(textOut);
  }

  /**
   * define a path
   *
   * @param iterator
   *          the path iterator
   * @param needsWindingRule
   *          is a winding rule needed?
   * @param textOut
   *          the text output
   */
  private static final void __pgfPath(final PathIterator iterator,
      final boolean needsWindingRule, final ITextOutput textOut) {
    final double[] s;
    int type;

    s = new double[6];

    if (needsWindingRule) {
      switch (iterator.getWindingRule()) {
        case PathIterator.WIND_EVEN_ODD: {
          textOut.append(_PGFGraphic.PATH_OE_WINDING_RULE);
          break;
        }
        case PathIterator.WIND_NON_ZERO: {
          textOut.append(_PGFGraphic.PATH_NZ_WINDING_RULE);
          break;
        }
        default: {
          throw new UnsupportedOperationException();
        }
      }
      _PGFGraphic.__nl(textOut);
    }

    outer: while (!iterator.isDone()) {
      type = iterator.currentSegment(s);
      iterator.next();
      switch (type) {
        case PathIterator.SEG_LINETO: {
          textOut.append(_PGFGraphic.PATH_LINE_TO);
          _PGFGraphic.__pgfPoint(s[0], s[1], textOut);
          _PGFGraphic.__commandEndNL(textOut);
          continue outer;
        }
        case PathIterator.SEG_MOVETO: {
          textOut.append(_PGFGraphic.PATH_MOVE_TO);
          _PGFGraphic.__pgfPoint(s[0], s[1], textOut);
          _PGFGraphic.__commandEndNL(textOut);
          continue outer;
        }
        case PathIterator.SEG_QUADTO: {
          textOut.append(_PGFGraphic.PATH_QUAD_TO);
          _PGFGraphic.__pgfPoint(s[0], s[1], textOut);
          textOut.append('}');
          textOut.append('{');
          _PGFGraphic.__pgfPoint(s[2], s[3], textOut);
          _PGFGraphic.__commandEndNL(textOut);
          continue outer;
        }
        case PathIterator.SEG_CUBICTO: {
          textOut.append(_PGFGraphic.PATH_CURVE_TO);
          _PGFGraphic.__pgfPoint(s[0], s[1], textOut);
          textOut.append('}');
          textOut.append('{');
          _PGFGraphic.__pgfPoint(s[2], s[3], textOut);
          textOut.append('}');
          textOut.append('{');
          _PGFGraphic.__pgfPoint(s[4], s[5], textOut);
          _PGFGraphic.__commandEndNL(textOut);
          continue outer;
        }
        case PathIterator.SEG_CLOSE: {
          textOut.append(_PGFGraphic.PATH_CLOSE);
          _PGFGraphic.__nl(textOut);
          continue outer;
        }
        default: {
          throw new UnsupportedOperationException();
        }
      }
    }
  }

  /**
   * get the encoded text output
   *
   * @return the encoded text output
   */
  final ITextOutput __getEncoded() {
    if (this.m_encodedBody == null) {
      this.m_encodedBody = LaTeXCharTransformer.getInstance().transform(
          this.m_body, TextUtils.DEFAULT_NORMALIZER_FORM);
    }
    return this.m_encodedBody;
  }

  /** {@inheritDoc} */
  @Override
  protected final void onClose() {
    final ITextOutput textOut;
    final Rectangle boundingBox;

    this.m_encodedBody = null;

    try (final OutputStream os = PathUtils
        .openOutputStream(this.getPath())) {
      try (final OutputStreamWriter writer = new OutputStreamWriter(os)) {
        try (final BufferedWriter bw = new BufferedWriter(writer)) {
          textOut = AbstractTextOutput.wrap(bw);

          textOut.append(_PGFGraphic.PICTURE_BEGIN);
          _PGFGraphic.__commandEndNL(textOut);

          this.__flushColors(textOut);

          boundingBox = this.getBounds();
          _PGFGraphic.__pgfRectangle(boundingBox.x, boundingBox.y,
              boundingBox.width, boundingBox.height, textOut);
          _PGFGraphic.__pgfUsePath(_PGFGraphic.USE_PATH_BOUNDING_BOX,
              textOut);

          textOut.append(_PGFGraphic.CANVAS_TRANSFORM_BEGIN);
          textOut.append(_PGFGraphic.TRANSFORM_SHIFT_Y_BEGIN);
          _PGFGraphic.__number(this.m_height, textOut);
          _PGFGraphic.__commandEnd(textOut);
          _PGFGraphic.__commandEndNL(textOut);

          textOut.append(_PGFGraphic.CANVAS_TRANSFORM_BEGIN);
          textOut.append(_PGFGraphic.TRANSFORM_SCALE_Y_BEGIN);
          textOut.append('-');
          textOut.append('1');
          _PGFGraphic.__commandEnd(textOut);
          _PGFGraphic.__commandEndNL(textOut);

          this.m_body.toText(textOut);

          this.m_body = null;
          textOut.append(_PGFGraphic.PICTURE_END);
          _PGFGraphic.__commandEndNL(textOut);
        }
      }
    } catch (final Throwable error) {
      ErrorUtils.logError(this.getLogger(),
          "Error while closing PGF graphic.", error, false, //$NON-NLS-1$
          RethrowMode.AS_ILLEGAL_STATE_EXCEPTION);
    } finally {
      super.onClose();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final EGraphicFormat getGraphicFormat() {
    return EGraphicFormat.PGF;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDraw(final Shape s) {
    _PGFGraphic.__pgfPath(s.getPathIterator(null), false, this.m_body);
    _PGFGraphic.__pgfUsePath(_PGFGraphic.USE_PATH_STROKE, this.m_body);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawImage(final BufferedImage img,
      final BufferedImageOp op, final int x, final int y) {
    // TODO

  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawRenderedImage(final RenderedImage img,
      final AffineTransform xform) {
    // TODO
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawRenderableImage(final RenderableImage img,
      final AffineTransform xform) {
    // TODO
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawString(final String str, final int x, final int y) {
    // TODO
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawString(final String str, final float x,
      final float y) {
    // TODO
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawString(final AttributedCharacterIterator iterator,
      final int x, final int y) {
    // TODO
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawString(final AttributedCharacterIterator iterator,
      final float x, final float y) {
    // TODO
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawGlyphVector(final GlyphVector g, final float x,
      final float y) {
    // TODO

  }

  /** {@inheritDoc} */
  @Override
  protected final void doFill(final Shape s) {
    _PGFGraphic.__pgfPath(s.getPathIterator(null), true, this.m_body);
    _PGFGraphic.__pgfUsePath(_PGFGraphic.USE_PATH_FILL, this.m_body);
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetComposite(final Composite comp) {
    super.doSetComposite(comp);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetPaint(final Paint paint) {
    super.doSetPaint(paint);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetStroke(final Stroke s) {
    super.doSetStroke(s);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doTranslate(final int x, final int y) {
    this.doTranslate(((double) x), ((double) y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doTranslate(final double x, final double y) {
    final boolean canX, canY;

    if (this.m_inScopeReset <= 0) {
      canX = (x != 0d);
      canY = (y != 0d);
      if (canX && canY) {
        this.m_body.append(_PGFGraphic.CANVAS_TRANSFORM_BEGIN);
        this.m_body.append(_PGFGraphic.TRANSFORM_SHIFT_BEGIN);
        _PGFGraphic.__pgfPoint(x, y, this.m_body);
        _PGFGraphic.__commandEnd(this.m_body);
        _PGFGraphic.__commandEndNL(this.m_body);
      } else {
        if (canX) {
          this.m_body.append(_PGFGraphic.CANVAS_TRANSFORM_BEGIN);
          this.m_body.append(_PGFGraphic.TRANSFORM_SHIFT_X_BEGIN);
          _PGFGraphic.__pt(x, this.m_body);
          _PGFGraphic.__commandEnd(this.m_body);
          _PGFGraphic.__commandEndNL(this.m_body);
        } else {
          if (canY) {
            this.m_body.append(_PGFGraphic.CANVAS_TRANSFORM_BEGIN);
            this.m_body.append(_PGFGraphic.TRANSFORM_SHIFT_Y_BEGIN);
            _PGFGraphic.__pt(y, this.m_body);
            _PGFGraphic.__commandEnd(this.m_body);
            _PGFGraphic.__commandEndNL(this.m_body);
          } else {
            return;
          }
        }
      }
    }
    super.doTranslate(x, y);
  }

  /**
   * Convert an angle to degrees
   *
   * @param theta
   *          the angle
   * @return the degrees
   */
  private static final double __toDeg(final double theta) {
    final double value;
    value = RadiansToDegrees.INSTANCE.computeAsDouble(theta);
    return (value % 360d);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doRotate(final double theta) {
    final double value;

    if (this.m_inScopeReset <= 0) {
      value = _PGFGraphic.__toDeg(theta);
      if (value != 0d) {
        this.m_body.append(_PGFGraphic.CANVAS_TRANSFORM_BEGIN);
        this.m_body.append(_PGFGraphic.TRANSFORM_ROTATE_BEGIN);
        _PGFGraphic.__number(value, this.m_body);
        _PGFGraphic.__commandEnd(this.m_body);
        _PGFGraphic.__commandEndNL(this.m_body);
      } else {
        return;
      }
    }

    super.doRotate(theta);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doRotate(final double theta, final double x,
      final double y) {
    if ((_PGFGraphic.__toDeg(theta) != 0d) || (this.m_inScopeReset > 0)) {
      this.translate(x, y);
      this.rotate(theta);
      this.translate(-x, -y);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doScale(final double sx, final double sy) {
    boolean done;

    done = false;

    if (this.m_inScopeReset <= 0) {
      if (sx == sy) {
        if (sx == 0d) {
          return;
        }
        this.m_body.append(_PGFGraphic.CANVAS_TRANSFORM_BEGIN);
        this.m_body.append(_PGFGraphic.TRANSFORM_SCALE_BEGIN);
        _PGFGraphic.__number(sx, this.m_body);
        _PGFGraphic.__commandEnd(this.m_body);
        _PGFGraphic.__commandEndNL(this.m_body);
        done = true;
      } else {
        if (sx != 0d) {
          this.m_body.append(_PGFGraphic.CANVAS_TRANSFORM_BEGIN);
          this.m_body.append(_PGFGraphic.TRANSFORM_SCALE_X_BEGIN);
          _PGFGraphic.__number(sx, this.m_body);
          _PGFGraphic.__commandEnd(this.m_body);
          _PGFGraphic.__commandEndNL(this.m_body);
          done = true;
        }

        if (sy != 0d) {
          this.m_body.append(_PGFGraphic.CANVAS_TRANSFORM_BEGIN);
          this.m_body.append(_PGFGraphic.TRANSFORM_SCALE_Y_BEGIN);
          _PGFGraphic.__number(sy, this.m_body);
          _PGFGraphic.__commandEnd(this.m_body);
          _PGFGraphic.__commandEndNL(this.m_body);
          done = true;
        }
      }
    } else {
      done = true;
    }
    if (done) {
      super.doScale(sx, sy);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doShear(final double shx, final double shy) {
    boolean done;

    if (this.m_inScopeReset <= 0) {
      done = false;
      if (shx != 0d) {
        this.m_body.append(_PGFGraphic.CANVAS_TRANSFORM_BEGIN);
        this.m_body.append(_PGFGraphic.TRANSFORM_SLANT_X_BEGIN);
        _PGFGraphic.__number(shx, this.m_body);
        _PGFGraphic.__commandEnd(this.m_body);
        _PGFGraphic.__commandEndNL(this.m_body);
        done = true;
      }

      if (shy != 0d) {
        this.m_body.append(_PGFGraphic.CANVAS_TRANSFORM_BEGIN);
        this.m_body.append(_PGFGraphic.TRANSFORM_SLANT_Y_BEGIN);
        _PGFGraphic.__number(shy, this.m_body);
        _PGFGraphic.__commandEnd(this.m_body);
        _PGFGraphic.__commandEndNL(this.m_body);
        done = true;
      }
    } else {
      done = true;
    }

    if (done) {
      super.doShear(shx, shy);
    }
  }

  // /** {@inheritDoc} */
  // @Override
  // protected void doTransform(final AffineTransform Tx) {
  // this.__getTransform().concatenate(Tx);
  // }
  //
  // /** {@inheritDoc} */
  // @Override
  // protected void doSetTransform(final AffineTransform Tx) {
  // this.__getTransform().setTransform(Tx);
  // }

  /** {@inheritDoc} */
  @Override
  protected final void doSetBackground(final Color color) {
    super.doSetBackground(color);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doClip(final Shape s) {
    super.doClip(s);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetColor(final Color c) {
    final char[] name;
    final double d;

    if (this.m_inScopeReset <= 0) {
      name = this.__getColorName(c);

      this.m_body.append(_PGFGraphic.SET_COLOR);
      this.m_body.append(name);
      _PGFGraphic.__commandEndNL(this.m_body);

      this.m_body.append(_PGFGraphic.SET_FILL_OPACITY);
      d = (c.getAlpha() / 255d);
      _PGFGraphic.__number(d, this.m_body);
      _PGFGraphic.__commandEndNL(this.m_body);

      this.m_body.append(_PGFGraphic.SET_STROKE_OPACITY);
      _PGFGraphic.__number(d, this.m_body);
      _PGFGraphic.__commandEndNL(this.m_body);

    }
    super.doSetColor(c);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetFont(final Font font) {
    super.doSetFont(font);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doSetClip(final Shape clip) {
    if (this.m_inScopeReset <= 0) {
      _PGFGraphic.__pgfPath(clip.getPathIterator(null), true, this.m_body);
      _PGFGraphic.__pgfUsePath(_PGFGraphic.USE_PATH_CLIP, this.m_body);
    }
    super.doSetClip(clip);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doClipRect(final int x, final int y,
      final int width, final int height) {
    if (this.m_inScopeReset <= 0) {
      _PGFGraphic.__pgfRectangle(x, y, width, height, this.m_body);
      _PGFGraphic.__pgfUsePath(_PGFGraphic.USE_PATH_CLIP, this.m_body);
    }
    super.doClipRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetClip(final int x, final int y, final int width,
      final int height) {
    if (this.m_inScopeReset <= 0) {
      _PGFGraphic.__pgfRectangle(x, y, width, height, this.m_body);
      _PGFGraphic.__pgfUsePath(_PGFGraphic.USE_PATH_CLIP, this.m_body);
    }
    super.doSetClip(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected void doClipRect(final double x, final double y,
      final double width, final double height) {
    if (this.m_inScopeReset <= 0) {
      _PGFGraphic.__pgfRectangle(x, y, width, height, this.m_body);
      _PGFGraphic.__pgfUsePath(_PGFGraphic.USE_PATH_CLIP, this.m_body);
    }
    super.doClipRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetClip(final double x, final double y,
      final double width, final double height) {
    if (this.m_inScopeReset <= 0) {
      _PGFGraphic.__pgfRectangle(x, y, width, height, this.m_body);
      _PGFGraphic.__pgfUsePath(_PGFGraphic.USE_PATH_CLIP, this.m_body);
    }
    super.doSetClip(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected void doCopyArea(final int x, final int y, final int width,
      final int height, final int dx, final int dy) {
    // TODO
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawLine(final int x1, final int y1, final int x2,
      final int y2) {
    this.m_body.append(_PGFGraphic.PATH_MOVE_TO);
    _PGFGraphic.__pgfPoint(x1, y1, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
    this.m_body.append(_PGFGraphic.PATH_LINE_TO);
    _PGFGraphic.__pgfPoint(x2, y2, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
    _PGFGraphic.__pgfUsePath(_PGFGraphic.USE_PATH_STROKE, this.m_body);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawLine(final double x1, final double y1,
      final double x2, final double y2) {
    this.m_body.append(_PGFGraphic.PATH_MOVE_TO);
    _PGFGraphic.__pgfPoint(x1, y1, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
    this.m_body.append(_PGFGraphic.PATH_LINE_TO);
    _PGFGraphic.__pgfPoint(x2, y2, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
    _PGFGraphic.__pgfUsePath(_PGFGraphic.USE_PATH_STROKE, this.m_body);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillRect(final int x, final int y,
      final int width, final int height) {
    _PGFGraphic.__pgfRectangle(x, y, width, height, this.m_body);
    _PGFGraphic.__pgfUsePath(_PGFGraphic.USE_PATH_FILL, this.m_body);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillRect(final double x, final double y,
      final double width, final double height) {
    _PGFGraphic.__pgfRectangle(x, y, width, height, this.m_body);
    _PGFGraphic.__pgfUsePath(_PGFGraphic.USE_PATH_FILL, this.m_body);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRect(final int x, final int y,
      final int width, final int height) {
    _PGFGraphic.__pgfRectangle(x, y, width, height, this.m_body);
    _PGFGraphic.__pgfUsePath(_PGFGraphic.USE_PATH_STROKE, this.m_body);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRect(final double x, final double y,
      final double width, final double height) {
    _PGFGraphic.__pgfRectangle(x, y, width, height, this.m_body);
    _PGFGraphic.__pgfUsePath(_PGFGraphic.USE_PATH_STROKE, this.m_body);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    throw new UnsupportedOperationException();

  }

  /** {@inheritDoc} */
  @Override
  protected void doFillRoundRect(final int x, final int y,
      final int width, final int height, final int arcWidth,
      final int arcHeight) {
    // TODO
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawOval(final int x, final int y, final int width,
      final int height) {
    // TODO
  }

  /** {@inheritDoc} */
  @Override
  protected void doFillOval(final int x, final int y, final int width,
      final int height) {
    // TODO

  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawArc(final int x, final int y, final int width,
      final int height, final int startAngle, final int arcAngle) {
    // TODO
  }

  /** {@inheritDoc} */
  @Override
  protected void doFillArc(final int x, final int y, final int width,
      final int height, final int startAngle, final int arcAngle) {
    // TODO
  }

  /** {@inheritDoc} */
  @Override
  protected void doFillPolygon(final int[] xPoints, final int[] yPoints,
      final int nPoints) {
    this.doFill(new Polygon(xPoints, yPoints, nPoints));
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final int x, final int y,
      final ImageObserver observer) {
    // TODO
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final int x, final int y,
      final int width, final int height, final ImageObserver observer) {
    // TODO
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final int x, final int y,
      final Color bgcolor, final ImageObserver observer) {
    // TODO
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final int x, final int y,
      final int width, final int height, final Color bgcolor,
      final ImageObserver observer) {
    // TODO
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2,
      final ImageObserver observer) {
    // TODO
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img, final int dx1,
      final int dy1, final int dx2, final int dy2, final int sx1,
      final int sy1, final int sx2, final int sy2, final Color bgcolor,
      final ImageObserver observer) {
    // TODO
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected boolean doDrawImage(final Image img,
      final AffineTransform xform, final ImageObserver obs) {
    // TODO
    return false;
  }

  /**
   * begin a new proxy
   *
   * @param proxy
   *          the proxy
   */
  final void _beginProxy(final _PGFProxy proxy) {
    this.m_activeProxies.add(proxy);
  }

  /**
   * end a new proxy
   *
   * @param proxy
   *          the proxy
   */
  final void _endProxy(final _PGFProxy proxy) {
    if (this.m_activeProxies.remove(proxy)) {
      if (this.m_activeProxies.isEmpty()) {
        this.close();
      } else {
        this._endScope();
      }
    }
  }

  /** begin a new scope */
  final void _beginScope() {
    this.m_body.append(_PGFGraphic.SCOPE_BEGIN);
    _PGFGraphic.__commandEndNL(this.m_body);

    this.m_scopes.add(new _PGFState(this.getFont(), this.getClip(),//
        this.getTransform(), this.getColor(), this.getPaint(),//
        this.getBackground(), this.getStroke()));
  }

  /** end a scope */
  final void _endScope() {
    final _PGFState state;

    this.m_inScopeReset++;
    try {
      state = this.m_scopes.remove(this.m_scopes.size() - 1);
      this.setFont(state.m_font);
      this.setClip(state.m_clip);
      this.setPaint(state.m_foregroundPaint);
      this.setColor(state.m_foregroundColor);
      this.setBackground(state.m_backgroundColor);
      this.setTransform(state.m_transform);
      this.setStroke(state.m_stroke);

      this.m_body.append(_PGFGraphic.SCOPE_END);
      _PGFGraphic.__commandEndNL(this.m_body);
    } finally {
      this.m_inScopeReset--;
    }

  }

  /** {@inheritDoc} */
  @Override
  protected final Graphics doCreate() {
    this._beginScope();
    return new _PGFProxy(this, this.getLogger());
  }

  /** {@inheritDoc} */
  @Override
  protected Graphics doCreate(final int x, final int y, final int width,
      final int height) {
    this._beginScope();
    this.setClip(x, y, width, height);
    this.translate(x, y);
    return new _PGFProxy(this, this.getLogger());
  }

  /** {@inheritDoc} */
  @Override
  protected final void before(final int what) {
    super.before(what);
    for (final _PGFProxy proxy : this.m_activeProxies) {
      proxy._before(what);
    }
  }
}
