package org.optimizationBenchmarking.utils.graphics.graphic.impl.pgf;

import java.awt.BasicStroke;
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
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
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
import org.optimizationBenchmarking.utils.graphics.FontProperties;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.SimpleGraphic;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.MathConstants;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.Hypot;
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

  /** the prefix for defining a color */
  private static final char[] COLOR_PREFIX = { 't', 'j', };
  /** the prefix for defining a command */
  private static final char[] COMMAND_PREFIX = { 'r', 'g', };

  /** the command for defining a command */
  private static final char[] DEF = { '\\', 'd', 'e', 'f' };

  /** begin the environment */
  private static final char[] RESIZE_BEGIN = { '\\', 'r', 'e', 's', 'i',
      'z', 'e', 'b', 'o', 'x', '{' };

  /** begin the environment */
  private static final char[] PICTURE_BEGIN = { '\\', 'b', 'e', 'g', 'i',
      'n', '{', 'p', 'g', 'f', 'p', 'i', 'c', 't', 'u', 'r', 'e', '}' };

  /** the post-amble */
  private static final char[] PICTURE_END = { '\\', 'e', 'n', 'd', '{',
      'p', 'g', 'f', 'p', 'i', 'c', 't', 'u', 'r', 'e', '}', '}' };

  /**
   * set a stroke: #1 = width, #2=cap, #3 join, #4 miter limit, if any
   */
  private static final __Command SET_STROKE = new __Command(4, new char[] {
  /* set the line width */
  '\\', 'p', 'g', 'f', 's', 'e', 't', 'l', 'i', 'n', 'e', 'w', 'i', 'd',
      't', 'h', '{', '#', '1', 'p', 't', '}',
      /* set the cap */
      '\\', 'i', 'f', 'c', 'a', 's', 'e', '#', '2',
      /* case the cap: BasicStroke.CAP_BUTT == 0 */
      ' ', '\\', 'p', 'g', 'f', 's', 'e', 't', 'b', 'u', 't', 't', 'c',
      'a', 'p',
      /* BasicStroke.CAP_ROUND == 1 */
      '\\', 'o', 'r', '\\', 'p', 'g', 'f', 's', 'e', 't', 'r', 'o', 'u',
      'n', 'd', 'c', 'a', 'p',
      /* BasicStroke.CAP_SQUARE==2 */
      '\\', 'e', 'l', 's', 'e', '\\', 'p', 'g', 'f', 's', 'e', 't', 'r',
      'e', 'c', 't', 'c', 'a', 'p',
      /* end of set cap */
      '\\', 'f', 'i',
      /* set the join */
      '\\', 'i', 'f', 'c', 'a', 's', 'e', '#', '3',
      /* BasicStroke.JOIN_MITER == 0 */
      ' ', '\\', 'p', 'g', 'f', 's', 'e', 't', 'm', 'i', 't', 'e', 'r',
      'j', 'o', 'i', 'n', '\\', 'p', 'g', 'f', 's', 'e', 't', 'm', 'i',
      't', 'e', 'r', 'l', 'i', 'm', 'i', 't', '{', '#', '4', '}',
      /* BasicStroke.JOIN_ROUND==1 */
      '\\', 'o', 'r', '\\', 'p', 'g', 'f', 's', 'e', 't', 'r', 'o', 'u',
      'n', 'd', 'j', 'o', 'i', 'n',
      /* BasicStroke.JOIN_BEVEL==2 */
      '\\', 'e', 'l', 's', 'e', '\\', 'p', 'g', 'f', 's', 'e', 't', 'b',
      'e', 'v', 'e', 'l', 'j', 'o', 'i', 'n',
      /* end of set join */
      '\\', 'f', 'i', });

  /**
   * set a dash
   */
  private static final __Command SET_DASH = new __Command(2, new char[] {
      '\\', 'p', 'g', 'f', 's', 'e', 't', 'd', 'a', 's', 'h', '{', '#',
      '1', '}', '{', '#', '2', 'p', 't', '}', });

  /** create a rectangular path */
  private static final __Command PATH_RECTANGLE = new __Command(4,
      new char[] { '\\', 'p', 'g', 'f', 'p', 'a', 't', 'h', 'r', 'e', 'c',
          't', 'a', 'n', 'g', 'l', 'e', '{',/* */
          '\\', 'p', 'g', 'f', 'p', 'o', 'i', 'n', 't', '{', '#', '1',
          'p', 't', '}', '{', '#', '2', 'p', 't', '}', '}', '{',/* */
          '\\', 'p', 'g', 'f', 'p', 'o', 'i', 'n', 't', '{', '#', '3',
          'p', 't', '}', '{', '#', '4', 'p', 't', '}', '}', });

  /** the use a path as bounding box */
  private static final char[] USE_PATH_BOUNDING_BOX = { '\\', 'p', 'g',
      'f', 'u', 's', 'e', 'p', 'a', 't', 'h', '{', 'u', 's', 'e', ' ',
      'a', 's', ' ', 'b', 'o', 'u', 'n', 'd', 'i', 'n', 'g', ' ', 'b',
      'o', 'x', ',', 'c', 'l', 'i', 'p', '}' };
  /** fill a path */
  private static final __Command USE_PATH_FILL = new __Command(0,
      new char[] { '\\', 'p', 'g', 'f', 'u', 's', 'e', 'p', 'a', 't', 'h',
          '{', 'f', 'i', 'l', 'l', '}' });
  /** stroke a path */
  private static final __Command USE_PATH_STROKE = new __Command(0,
      new char[] { '\\', 'p', 'g', 'f', 'u', 's', 'e', 'p', 'a', 't', 'h',
          '{', 's', 't', 'r', 'o', 'k', 'e', '}' });
  /** clip a path */
  private static final __Command USE_PATH_CLIP = new __Command(0,
      new char[] { '\\', 'p', 'g', 'f', 'u', 's', 'e', 'p', 'a', 't', 'h',
          '{', 'c', 'l', 'i', 'p', '}', });

  /** the beginning of a new scope */
  private static final __Command SCOPE_BEGIN = new __Command(0,
      new char[] { '\\', 'p', 'g', 'f', 's', 'c', 'o', 'p', 'e', });

  /** the end of a scope */
  private static final __Command SCOPE_END = new __Command(0, new char[] {
      '\\', 'e', 'n', 'd', 'p', 'g', 'f', 's', 'c', 'o', 'p', 'e', });

  /** perform a rotation */
  private static final __Command TRANSFORM_ROTATE = new __Command(1,
      new char[] { '\\', 'p', 'g', 'f', 'l', 'o', 'w', 'l', 'e', 'v', 'e',
          'l', '{',/* */
          '\\', 'p', 'g', 'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r',
          'm', 'r', 'o', 't', 'a', 't', 'e', '{', '#', '1', '}',/* */
          '}' });

  /** perform a scaling */
  private static final __Command TRANSFORM_SCALE = new __Command(1,
      new char[] { '\\', 'p', 'g', 'f', 'l', 'o', 'w', 'l', 'e', 'v', 'e',
          'l', '{',/* */
          '\\', 'p', 'g', 'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r',
          'm', 's', 'c', 'a', 'l', 'e', '{', '#', '1', '}',/* */
          '}' });
  /** do an x-scaling */
  private static final __Command TRANSFORM_SCALE_X = new __Command(1,
      new char[] { '\\', 'p', 'g', 'f', 'l', 'o', 'w', 'l', 'e', 'v', 'e',
          'l', '{',/* */
          '\\', 'p', 'g', 'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r',
          'm', 'x', 's', 'c', 'a', 'l', 'e', '{', '#', '1', '}',/* */
          '}' });
  /** do a y-scaling */
  private static final __Command TRANSFORM_SCALE_Y = new __Command(1,
      new char[] { '\\', 'p', 'g', 'f', 'l', 'o', 'w', 'l', 'e', 'v', 'e',
          'l', '{',/* */
          '\\', 'p', 'g', 'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r',
          'm', 'y', 's', 'c', 'a', 'l', 'e', '{', '#', '1', '}',/* */
          '}' });
  /** do an x and y-scaling */
  private static final __Command TRANSFORM_SCALE_XY = new __Command(2,
      new char[] { '\\', 'p', 'g', 'f', 'l', 'o', 'w', 'l', 'e', 'v', 'e',
          'l', '{',/* */
          '\\', 'p', 'g', 'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r',
          'm', 'x', 's', 'c', 'a', 'l', 'e', '{', '#', '1', '}',/* */
          '\\', 'p', 'g', 'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r',
          'm', 'y', 's', 'c', 'a', 'l', 'e', '{', '#', '2', '}',/* */
          '}' });

  /** do a shift */
  private static final __Command TRANSFORM_SHIFT = new __Command(2,
      new char[] { '\\', 'p', 'g', 'f', 'l', 'o', 'w', 'l', 'e', 'v', 'e',
          'l', '{',/* */
          '\\', 'p', 'g', 'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r',
          'm', 's', 'h', 'i', 'f', 't', '{', /* */
          '\\', 'p', 'g', 'f', 'p', 'o', 'i', 'n', 't', '{', '#', '1',
          'p', 't', '}', '{', '#', '2', 'p', 't', '}', '}', '}' });

  /** do an x-shift */
  private static final __Command TRANSFORM_SHIFT_X = new __Command(1,
      new char[] { '\\', 'p', 'g', 'f', 'l', 'o', 'w', 'l', 'e', 'v', 'e',
          'l', '{',/* */
          '\\', 'p', 'g', 'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r',
          'm', 'x', 's', 'h', 'i', 'f', 't', '{', '#', '1', 'p', 't', '}',
          '}' });

  /** do an y-shift */
  private static final __Command TRANSFORM_SHIFT_Y = new __Command(1,
      new char[] { '\\', 'p', 'g', 'f', 'l', 'o', 'w', 'l', 'e', 'v', 'e',
          'l', '{',/* */
          '\\', 'p', 'g', 'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r',
          'm', 'y', 's', 'h', 'i', 'f', 't', '{', '#', '1', 'p', 't', '}',
          '}' });

  /** do a slant */
  private static final __Command TRANSFORM_SLANT = new __Command(2,
      new char[] { '\\', 'p', 'g', 'f', 'l', 'o', 'w', 'l', 'e', 'v', 'e',
          'l', '{',/* */
          '\\', 'p', 'g', 'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r',
          'm', 'x', 's', 'l', 'a', 'n', 't', '{', '#', '1', '}',/* */
          '\\', 'p', 'g', 'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r',
          'm', 'y', 's', 'l', 'a', 'n', 't', '{', '#', '2', '}', '}' });

  /** do an x-slant */
  private static final __Command TRANSFORM_SLANT_X = new __Command(1,
      new char[] { '\\', 'p', 'g', 'f', 'l', 'o', 'w', 'l', 'e', 'v', 'e',
          'l', '{',/* */
          '\\', 'p', 'g', 'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r',
          'm', 'x', 's', 'l', 'a', 'n', 't', '{', '#', '1', '}', '}' });

  /** do an y-slant */
  private static final __Command TRANSFORM_SLANT_Y = new __Command(1,
      new char[] { '\\', 'p', 'g', 'f', 'l', 'o', 'w', 'l', 'e', 'v', 'e',
          'l', '{',/* */
          '\\', 'p', 'g', 'f', 't', 'r', 'a', 'n', 's', 'f', 'o', 'r',
          'm', 'y', 's', 'l', 'a', 'n', 't', '{', '#', '1', '}', '}' });

  /** begin defining a color */
  private static final __Command DEFINE_COLOR = new __Command(
      3,
      new char[] { '\\', 'd', 'e', 'f', 'i', 'n', 'e', 'c', 'o', 'l', 'o',
          'r', '{', '#', '1', '}', '{', '#', '2', '}', '{', '#', '3', '}' });

  /** begin defining a color in rgb */
  private static final char[] DEFINE_COLOR_RGB = { '}', '{', 'r', 'g',
      'b', '}', '{', };
  /** begin defining a color in gray */
  private static final char[] DEFINE_COLOR_GRAY = { '}', '{', 'g', 'r',
      'a', 'y', '}', '{', };

  /** set the color */
  private static final __Command SET_COLOR = new __Command(2, new char[] {
      '\\', 'p', 'g', 'f', 's', 'e', 't', 'c', 'o', 'l', 'o', 'r', '{',
      '#', '1', '}',/* */
      '\\', 'p', 'g', 'f', 's', 'e', 't', 'f', 'i', 'l', 'l', 'o', 'p',
      'a', 'c', 'i', 't', 'y', '{', '#', '2', '}',/* */
      '\\', 'p', 'g', 'f', 's', 'e', 't', 's', 't', 'r', 'o', 'k', 'e',
      'o', 'p', 'a', 'c', 'i', 't', 'y', '{', '#', '2', '}',/* */
  });

  /** set an opaque color */
  private static final __Command SET_OPAQUE_COLOR = new __Command(1,
      new char[] { '\\', 'p', 'g', 'f', 's', 'e', 't', 'c', 'o', 'l', 'o',
          'r', '{', '#', '1', '}',/* */
          '\\', 'p', 'g', 'f', 's', 'e', 't', 'f', 'i', 'l', 'l', 'o',
          'p', 'a', 'c', 'i', 't', 'y', '{', '1', '}',/* */
          '\\', 'p', 'g', 'f', 's', 'e', 't', 's', 't', 'r', 'o', 'k',
          'e', 'o', 'p', 'a', 'c', 'i', 't', 'y', '{', '1', '}',/* */
      });

  /** path move to */
  private static final __Command PATH_MOVE_TO = new __Command(2,
      new char[] { '\\', 'p', 'g', 'f', 'p', 'a', 't', 'h', 'm', 'o', 'v',
          'e', 't', 'o', '{',/* */
          '\\', 'p', 'g', 'f', 'p', 'o', 'i', 'n', 't', '{', '#', '1',
          'p', 't', '}', '{', '#', '2', 'p', 't', '}', '}', });
  /** path line to */
  private static final __Command PATH_LINE_TO = new __Command(2,
      new char[] { '\\', 'p', 'g', 'f', 'p', 'a', 't', 'h', 'l', 'i', 'n',
          'e', 't', 'o', '{',/* */
          '\\', 'p', 'g', 'f', 'p', 'o', 'i', 'n', 't', '{', '#', '1',
          'p', 't', '}', '{', '#', '2', 'p', 't', '}', '}', });
  /** path curve to */
  private static final __Command PATH_CURVE_TO = new __Command(6,
      new char[] { '\\', 'p', 'g', 'f', 'p', 'a', 't', 'h', 'c', 'u', 'r',
          'v', 'e', 't', 'o', '{',/* */
          '\\', 'p', 'g', 'f', 'p', 'o', 'i', 'n', 't', '{', '#', '1',
          'p', 't', '}', '{', '#', '2', 'p', 't', '}', '}', '{',/* */
          '\\', 'p', 'g', 'f', 'p', 'o', 'i', 'n', 't', '{', '#', '3',
          'p', 't', '}', '{', '#', '4', 'p', 't', '}', '}', '{',/* */
          '\\', 'p', 'g', 'f', 'p', 'o', 'i', 'n', 't', '{', '#', '5',
          'p', 't', '}', '{', '#', '6', 'p', 't', '}', '}', });
  /** close the path */
  private static final __Command PATH_CLOSE = new __Command(0, new char[] {
      '\\', 'p', 'g', 'f', 'p', 'a', 't', 'h', 'c', 'l', 'o', 's', 'e' });
  /** path curve to */
  private static final __Command PATH_QUAD_TO = new __Command(4,
      new char[] { '\\', 'p', 'g', 'f', 'p', 'a', 't', 'h', 'q', 'u', 'a',
          'd', 'r', 'a', 't', 'i', 'c', 'c', 'u', 'r', 'v', 'e', 't', 'o',
          '{',/* */
          '{',/* */
          '\\', 'p', 'g', 'f', 'p', 'o', 'i', 'n', 't', '{', '#', '1',
          'p', 't', '}', '{', '#', '2', 'p', 't', '}', '}', '{',/* */
          '\\', 'p', 'g', 'f', 'p', 'o', 'i', 'n', 't', '{', '#', '3',
          'p', 't', '}', '{', '#', '4', 'p', 't', '}', '}', });

  /** the winding rule */
  private static final __Command PATH_OE_WINDING_RULE = new __Command(0,
      new char[] { '\\', 'p', 'g', 'f', 's', 'e', 't', 'e', 'o', 'r', 'u',
          'l', 'e' });
  /** the winding rule */
  private static final __Command PATH_NZ_WINDING_RULE = new __Command(0,
      new char[] { '\\', 'p', 'g', 'f', 's', 'e', 't', 'n', 'o', 'n', 'z',
          'e', 'r', 'o', 'r', 'u', 'l', 'e' });

  /** render some text */
  private static final __Command TEXT = new __Command(5, new char[] {
      '\\', 'p', 'g', 'f', 't', 'e', 'x', 't', '[', 'l', 'e', 'f', 't',
      ',', 'b', 'o', 't', 't', 'o', 'm', ',', 'a', 't', '=', '{', '\\',
      'p', 'g', 'f', 'p', 'o', 'i', 'n', 't', '{', '#', '1', 'p', 't',
      '}', '{', '#', '2', 'p', 't', '}', '}', ']', '{',/* */
      '\\', 'r', 'e', 's', 'i', 'z', 'e', 'b', 'o', 'x', '{', '#', '3',
      'p', 't', '}', '{', '-', '#', '4', 'p', 't', '}', '{', '#', '5',
      '}', '}' });

  /** text rm */
  private static final __Command TEXT_RM = new __Command(1, new char[] {
      '\\', 't', 'e', 'x', 't', 'r', 'm', '{', '#', '1', '}', });
  /** text sf */
  private static final __Command TEXT_SF = new __Command(1, new char[] {
      '\\', 't', 'e', 'x', 't', 's', 'f', '{', '#', '1', '}', });
  /** text tt */
  private static final __Command TEXT_TT = new __Command(1, new char[] {
      '\\', 't', 'e', 'x', 't', 't', 't', '{', '#', '1', '}', });
  /** text bf */
  private static final __Command TEXT_BF = new __Command(1, new char[] {
      '\\', 't', 'e', 'x', 't', 'b', 'f', '{', '#', '1', '}', });
  /** text it */
  private static final __Command TEXT_IT = new __Command(1, new char[] {
      '\\', 't', 'e', 'x', 't', 'i', 't', '{', '#', '1', '}', });
  /** text ul */
  private static final __Command TEXT_UL = new __Command(1, new char[] {
      '\\', 'u', 'n', 'd', 'e', 'r', 'l', 'i', 'n', 'e', '{', '#', '1',
      '}', });

  /** the graphic body */
  private __Buffer m_body;
  /** LaTeX encoded output */
  private ITextOutput m_encodedBody;
  /** are we in scope-reset mode? */
  private int m_inScopeReset;
  /** the state scopes */
  private final ArrayList<_PGFState> m_scopes;
  /** the active proxies */
  private final ArrayList<_PGFProxy> m_activeProxies;

  /** the registered names */
  private HashMap<Object, char[]> m_names;

  /** the number of colors */
  private int m_colorCount;

  /** the number of commands */
  private int m_commandCount;

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

    final Rectangle boundingBox;

    this.m_scopes = new ArrayList<>();
    this.m_activeProxies = new ArrayList<>();
    this.m_names = new HashMap<>();

    this.m_body = new __Buffer();

    boundingBox = this.getBounds();
    this.__rectangle(boundingBox.x, boundingBox.y, boundingBox.width,
        boundingBox.height);
    this.m_body.append(_PGFGraphic.USE_PATH_BOUNDING_BOX);

    this.__translateY(boundingBox.height);
    this.__scaleY(-1);
    this.__setStroke(this.getStroke());
    this.__setColor(this.getColor());
  }

  /**
   * Transform an integer to a name
   *
   * @param prefix
   *          the name prefix
   * @param value
   *          the value
   * @param prefixBackslash
   *          should we pre-pend '\'
   * @return the name
   */
  private static final char[] __intToName(final char[] prefix,
      final int value, final boolean prefixBackslash) {
    final MemoryTextOutput mto;
    int size, mod;

    size = value;
    mto = new MemoryTextOutput();
    if (prefixBackslash) {
      mto.append('\\');
    }
    mto.append(prefix);
    for (;;) {
      mod = Math.abs(size % 52);
      if (mod < 26) {
        mto.append((char) ('a' + mod));
      } else {
        mto.append((char) (('A' + mod) - 26));
      }

      if ((size /= 52) == 0) {
        break;
      }
      if (size < 0) {
        size++;
      } else {
        size--;
      }
    }
    return mto.toChars();
  }

  /**
   * define a command
   *
   * @param name
   *          the name
   * @param cmd
   *          the command
   * @param textOut
   *          the text output
   */
  private static final void __defineCommand(final char[] name,
      final __Command cmd, final __Buffer textOut) {
    int i;

    textOut.append(_PGFGraphic.DEF);
    textOut.append(name);
    for (i = 1; i <= cmd.m_args; i++) {
      textOut.append('#');
      textOut.append((char) ('0' + i));
    }
    textOut.append('{');
    textOut.append(cmd.m_name);
    _PGFGraphic.__commandEndNL(textOut);
  }

  /**
   * get the name associated with a given color
   *
   * @param color
   *          the color
   * @return the color name
   */
  private final char[] __getColorName(final Color color) {
    final Integer key;
    char[] name;

    key = Integer.valueOf(color.getRGB() & 0xffffff);
    name = this.m_names.get(key);
    if (name != null) {
      return name;
    }
    name = _PGFGraphic.__intToName(_PGFGraphic.COLOR_PREFIX,
        (this.m_colorCount++), false);
    this.m_names.put(key, name);
    return name;
  }

  /**
   * get the name associated with a given command
   *
   * @param command
   *          the command
   * @return the command name
   */
  private final char[] __getCommandName(final __Command command) {
    char[] name;

    name = this.m_names.get(command);
    if (name != null) {
      return name;
    }

    name = _PGFGraphic.__intToName(_PGFGraphic.COMMAND_PREFIX,
        (this.m_commandCount++), true);
    this.m_names.put(command, name);
    return name;
  }

  /**
   * set a color
   *
   * @param color
   *          the color
   */
  private final void __setColor(final Color color) {
    final int alpha;
    final char[] name;

    name = this.__getColorName(color);
    alpha = color.getAlpha();
    if (alpha >= 255) {
      this.m_body.append(this
          .__getCommandName(_PGFGraphic.SET_OPAQUE_COLOR));
      this.m_body.append('{');
      this.m_body.append(name);
    } else {
      this.m_body.append(this.__getCommandName(_PGFGraphic.SET_COLOR));
      this.m_body.append('{');
      this.m_body.append(name);
      this.m_body.append('}');
      this.m_body.append('{');
      _PGFGraphic.__number((alpha / 255d), this.m_body);
    }

    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /**
   * flush all colors
   *
   * @param dest
   *          the destination to flush to
   */
  private final void __flush(final __Buffer dest) {
    char[] defc;
    Object key;
    int rgb, red, green, blue;

    if (this.m_colorCount > 0) {
      defc = this.__getCommandName(_PGFGraphic.DEFINE_COLOR);
      _PGFGraphic.__defineCommand(defc, _PGFGraphic.DEFINE_COLOR, dest);
    } else {
      defc = null;
    }

    for (final Map.Entry<Object, char[]> entry : this.m_names.entrySet()) {
      key = entry.getKey();
      if (key instanceof Integer) {
        dest.append(defc);
        dest.append('{');
        dest.append(entry.getValue());

        rgb = ((Integer) key).intValue();
        blue = (rgb & 0xff);
        green = ((rgb >>> 8) & 0xff);
        red = ((rgb >>> 16) & 0xff);

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
      } else {
        if (key != _PGFGraphic.DEFINE_COLOR) {
          _PGFGraphic.__defineCommand(entry.getValue(), ((__Command) key),
              dest);
        }
      }
    }
    this.m_names = null;
  }

  /**
   * append a {@code double} to the given text output
   *
   * @param d
   *          the double
   * @param textOut
   *          the text output
   */
  private static final void __number(final double d, final __Buffer textOut) {
    if (NumericalTypes.isLong(d)) {
      textOut.append((long) d);
    } else {
      SimpleNumberAppender.INSTANCE.appendTo(d, ETextCase.IN_SENTENCE,
          textOut);
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
   */
  private final void __rectangle(final double x, final double y,
      final double width, final double height) {
    this.m_body.append(this.__getCommandName(_PGFGraphic.PATH_RECTANGLE));
    this.m_body.append('{');
    _PGFGraphic.__number(x, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(y, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(width, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(height, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /**
   * End a command
   *
   * @param textOut
   *          the text output
   */
  private static final void __commandEnd(final __Buffer textOut) {
    textOut.append('}');
  }

  /**
   * End a command and write a newline
   *
   * @param textOut
   *          the text output
   */
  private static final void __commandEndNL(final __Buffer textOut) {
    _PGFGraphic.__commandEnd(textOut);
    _PGFGraphic.__nl(textOut);
  }

  /**
   * write a newline
   *
   * @param textOut
   *          the text output
   */
  private static final void __nl(final __Buffer textOut) {
    if ((textOut.length() - textOut.m_lastNewLine) > 256) {
      textOut.append('%');
      textOut.appendLineBreak();
      textOut.m_lastNewLine = textOut.length();
    }
  }

  /** use a path for filling */
  private final void __usePathFill() {
    this.m_body.append(this.__getCommandName(_PGFGraphic.USE_PATH_FILL));
  }

  /** use a path for stroke */
  private final void __usePathStroke() {
    this.m_body.append(this.__getCommandName(_PGFGraphic.USE_PATH_STROKE));
  }

  /** use a path for clipping */
  private final void __usePathClip() {
    this.m_body.append(this.__getCommandName(_PGFGraphic.USE_PATH_CLIP));
  }

  /**
   * connect the given coordinates with a line
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  private final void __pathLineTo(final double x, final double y) {
    this.m_body.append(this.__getCommandName(_PGFGraphic.PATH_LINE_TO));
    this.m_body.append('{');
    _PGFGraphic.__number(x, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(y, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /**
   * move to the given coordinates
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  private final void __pathMoveTo(final double x, final double y) {
    this.m_body.append(this.__getCommandName(_PGFGraphic.PATH_MOVE_TO));
    this.m_body.append('{');
    _PGFGraphic.__number(x, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(y, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /**
   * connect the given coordinates with a quadratic bezier
   *
   * @param x1
   *          the x1-coordinate
   * @param y1
   *          the y1-coordinate
   * @param x2
   *          the x2-coordinate
   * @param y2
   *          the y2-coordinate
   */
  private final void __pathQuadTo(final double x1, final double y1,
      final double x2, final double y2) {
    this.m_body.append(this.__getCommandName(_PGFGraphic.PATH_QUAD_TO));
    this.m_body.append('{');
    _PGFGraphic.__number(x1, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(y1, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(x2, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(y2, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /**
   * connect the given coordinates with a ternary bezier
   *
   * @param x1
   *          the x1-coordinate
   * @param y1
   *          the y1-coordinate
   * @param x2
   *          the x2-coordinate
   * @param y2
   *          the y2-coordinate
   * @param x3
   *          the x3-coordinate
   * @param y3
   *          the y3-coordinate
   */
  private final void __pathCurveTo(final double x1, final double y1,
      final double x2, final double y2, final double x3, final double y3) {
    this.m_body.append(this.__getCommandName(_PGFGraphic.PATH_CURVE_TO));
    this.m_body.append('{');
    _PGFGraphic.__number(x1, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(y1, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(x2, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(y2, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(x3, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(y3, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /** close the current path */
  private final void __pathClose() {
    this.m_body.append(this.__getCommandName(_PGFGraphic.PATH_CLOSE));
  }

  /**
   * define a path
   *
   * @param iterator
   *          the path iterator
   * @param needsWindingRule
   *          is a winding rule needed?
   */
  private final void __path(final PathIterator iterator,
      final boolean needsWindingRule) {
    final double[] s;
    int type;

    s = new double[6];

    if (needsWindingRule) {
      switch (iterator.getWindingRule()) {
        case PathIterator.WIND_EVEN_ODD: {
          this.m_body.append(this.__getCommandName(//
              _PGFGraphic.PATH_OE_WINDING_RULE));
          break;
        }
        case PathIterator.WIND_NON_ZERO: {
          this.m_body.append(this.__getCommandName(//
              _PGFGraphic.PATH_NZ_WINDING_RULE));
          break;
        }
        default: {
          throw new UnsupportedOperationException();
        }
      }
      _PGFGraphic.__nl(this.m_body);
    }

    outer: while (!iterator.isDone()) {
      type = iterator.currentSegment(s);
      iterator.next();
      switch (type) {
        case PathIterator.SEG_LINETO: {
          this.__pathLineTo(s[0], s[1]);
          continue outer;
        }
        case PathIterator.SEG_MOVETO: {
          this.__pathMoveTo(s[0], s[1]);
          continue outer;
        }
        case PathIterator.SEG_QUADTO: {
          this.__pathQuadTo(s[0], s[1], s[2], s[3]);
          continue outer;
        }
        case PathIterator.SEG_CUBICTO: {
          this.__pathCurveTo(s[0], s[1], s[2], s[3], s[4], s[5]);
          continue outer;
        }
        case PathIterator.SEG_CLOSE: {
          this.__pathClose();
          continue outer;
        }
        default: {
          throw new UnsupportedOperationException();
        }
      }
    }
  }

  /**
   * set the stroke
   *
   * @param stroke
   *          the stroke
   */
  private final void __setStroke(final Stroke stroke) {
    final Rectangle2D rect;

    if (stroke instanceof BasicStroke) {
      this.__setStroke((BasicStroke) stroke);
    } else {
      rect = stroke.createStrokedShape(new Line2D.Double(0d, 0d, 0d, 0d))
          .getBounds2D();
      this.__setStroke(Hypot.INSTANCE.computeAsDouble(rect.getWidth(),
          rect.getHeight()), BasicStroke.CAP_ROUND,
          BasicStroke.JOIN_ROUND, 0d, null, 0f);
    }
  }

  /**
   * set the basic stroke
   *
   * @param stroke
   *          the stroke
   */
  private final void __setStroke(final BasicStroke stroke) {
    this.__setStroke(stroke.getLineWidth(), stroke.getEndCap(),
        stroke.getLineJoin(), stroke.getMiterLimit(),
        stroke.getDashArray(), stroke.getDashPhase());
  }

  /**
   * set a basic stroke
   *
   * @param width
   *          the width
   * @param cap
   *          the cap
   * @param join
   *          the join
   * @param miterLimit
   *          the miter limit
   * @param dash
   *          the dash
   * @param phase
   *          the phase
   */
  private final void __setStroke(final double width, final int cap,
      final int join, final double miterLimit, final float[] dash,
      final float phase) {
    this.m_body.append(this.__getCommandName(_PGFGraphic.SET_STROKE));
    this.m_body.append('{');
    _PGFGraphic.__number(width, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    this.m_body.append(((char) ('0' + cap)));
    this.m_body.append('}');
    this.m_body.append('{');
    this.m_body.append(((char) ('0' + join)));
    this.m_body.append('}');
    this.m_body.append('{');
    if (join == BasicStroke.JOIN_MITER) {
      _PGFGraphic.__number(miterLimit, this.m_body);
    }
    _PGFGraphic.__commandEndNL(this.m_body);
    if ((dash != null) && (dash.length > 0)) {
      this.m_body.append(this.__getCommandName(_PGFGraphic.SET_DASH));
      this.m_body.append('{');
      for (final float f : dash) {
        this.m_body.append('{');
        _PGFGraphic.__number(f, this.m_body);
        this.m_body.append('p');
        this.m_body.append('t');
        this.m_body.append('}');
      }
      this.m_body.append('}');
      this.m_body.append('{');
      _PGFGraphic.__number(phase, this.m_body);
      this.m_body.append('p');
      this.m_body.append('t');
      _PGFGraphic.__commandEndNL(this.m_body);
    }
  }

  /**
   * get the encoded text output
   *
   * @return the encoded text output
   */
  private final ITextOutput __getEncoded() {
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
    __Buffer header;

    this.m_encodedBody = null;

    header = new __Buffer();
    header.append(_PGFGraphic.RESIZE_BEGIN);
    header.append(this.m_width);
    header.append('p');
    header.append('t');
    header.append('}');
    header.append('{');
    header.append(this.m_height);
    header.append('p');
    header.append('t');
    header.append('}');
    header.append('{');

    header.append(_PGFGraphic.PICTURE_BEGIN);
    _PGFGraphic.__nl(header);
    this.__flush(header);

    this.m_body.append(_PGFGraphic.PICTURE_END);
    _PGFGraphic.__nl(this.m_body);

    try (final OutputStream os = PathUtils
        .openOutputStream(this.getPath())) {
      try (final OutputStreamWriter writer = new OutputStreamWriter(os)) {
        try (final BufferedWriter bw = new BufferedWriter(writer)) {
          textOut = AbstractTextOutput.wrap(bw);
          header.toText(textOut);
          header = null;
          this.m_body.toText(textOut);
          if (this.m_body.m_lastNewLine < this.m_body.length()) {
            bw.append('%');
            bw.newLine();
          }
          this.m_body = null;
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
    this.__path(s.getPathIterator(null), false);
    this.__usePathStroke();
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

  /**
   * insert a given text at a given position
   *
   * @param str
   *          the string
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  @SuppressWarnings("incomplete-switch")
  private final void __text(final String str, final double x,
      final double y) {
    final Font font;
    final TextLayout layout;
    final Rectangle2D bounds;
    FontProperties properties;
    int closing;

    font = this.getFont();
    layout = new TextLayout(str, font, this.getFontRenderContext());
    bounds = layout.getBounds();

    this.m_body.append(this.__getCommandName(_PGFGraphic.TEXT));
    this.m_body.append('{');
    _PGFGraphic.__number(x, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number((y + bounds.getY()), this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');

    _PGFGraphic.__number(bounds.getWidth(), this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(bounds.getHeight(), this.m_body);

    this.m_body.append('}');
    this.m_body.append('{');

    closing = 0;
    properties = FontProperties.getFontProperties(font, true);
    if (properties == null) {
      properties = FontProperties.getFontProperties(font, false);
    }

    if (properties.isBold()) {
      this.m_body.append(this.__getCommandName(_PGFGraphic.TEXT_BF));
      this.m_body.append('{');
      closing++;
    }
    if (properties.isItalic()) {
      this.m_body.append(this.__getCommandName(_PGFGraphic.TEXT_IT));
      this.m_body.append('{');
      closing++;
    }
    if (properties.isUnderlined()) {
      this.m_body.append(this.__getCommandName(_PGFGraphic.TEXT_UL));
      this.m_body.append('{');
      closing++;
    }
    switch (properties.getFamily()) {
      case MONOSPACED: {
        this.m_body.append(this.__getCommandName(_PGFGraphic.TEXT_TT));
        this.m_body.append('{');
        closing++;
        break;
      }
      case SANS_SERIF: {
        this.m_body.append(this.__getCommandName(_PGFGraphic.TEXT_SF));
        this.m_body.append('{');
        closing++;
        break;
      }
      case SERIF: {
        this.m_body.append(this.__getCommandName(_PGFGraphic.TEXT_RM));
        this.m_body.append('{');
        closing++;
        break;
      }
    }

    this.__getEncoded().append(str);

    for (; (--closing) >= 0;) {
      this.m_body.append('}');
    }

    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawString(final String str, final double x,
      final double y) {
    this.__text(str, x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(final String str, final int x,
      final int y) {
    this.doDrawString(str, ((double) x), ((double) y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(final String str, final float x,
      final float y) {
    this.doDrawString(str, ((double) x), ((double) y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(
      final AttributedCharacterIterator iterator, final double x,
      final double y) {

    final MemoryTextOutput mto;

    mto = new MemoryTextOutput();
    mto.append(iterator);
    this.doDrawString(mto.toString(), x, y);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(
      final AttributedCharacterIterator iterator, final int x, final int y) {
    this.doDrawString(iterator, ((double) x), ((double) y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawString(
      final AttributedCharacterIterator iterator, final float x,
      final float y) {
    this.doDrawString(iterator, ((double) x), ((double) y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawGlyphVector(final GlyphVector g,
      final double x, final double y) {
    this.translate(x, y);
    this.draw(g.getOutline());
    this.translate((-x), (-y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawGlyphVector(final GlyphVector g,
      final float x, final float y) {
    this.doDrawGlyphVector(g, ((double) x), ((double) y));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFill(final Shape s) {
    this.__path(s.getPathIterator(null), true);
    this.__usePathFill();
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
    if (this.m_inScopeReset <= 0) {
      this.__setStroke(s);
    }
    super.doSetStroke(s);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doTranslate(final int x, final int y) {
    this.doTranslate(((double) x), ((double) y));
  }

  /**
   * perform a shift along the x-axis
   *
   * @param v
   *          the x-coordinate
   */
  private final void __translateX(final double v) {
    this.m_body.append(this
        .__getCommandName(_PGFGraphic.TRANSFORM_SHIFT_X));
    this.m_body.append('{');
    _PGFGraphic.__number(v, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /**
   * perform a shift along the y-axis
   *
   * @param v
   *          the y-coordinate
   */
  private final void __translateY(final double v) {
    this.m_body.append(this
        .__getCommandName(_PGFGraphic.TRANSFORM_SHIFT_Y));
    this.m_body.append('{');
    _PGFGraphic.__number(v, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /**
   * perform a shift
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  private final void __translateXY(final double x, final double y) {
    this.m_body.append(this.__getCommandName(_PGFGraphic.TRANSFORM_SHIFT));
    this.m_body.append('{');
    _PGFGraphic.__number(x, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(y, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doTranslate(final double x, final double y) {
    final boolean canX, canY;

    if (this.m_inScopeReset <= 0) {
      canX = (x != 0d);
      canY = (y != 0d);
      if (canX && canY) {
        this.__translateXY(x, y);
      } else {
        if (canX) {
          this.__translateX(x);
        } else {
          if (canY) {
            this.__translateY(y);
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
    final int integ;

    if ((theta % MathConstants.TWO_PI) == 0d) {
      return 0d;
    }

    value = (RadiansToDegrees.INSTANCE.computeAsDouble(theta) % 360d);
    integ = ((int) (Math.round(value)));

    if ((integ <= Math.nextUp(value)) && //
        (integ >= Math.nextAfter(value, Double.NEGATIVE_INFINITY))) {
      if (integ == 360) {
        return 0d;
      }
      return integ;
    }

    return value;
  }

  /**
   * do rotate
   *
   * @param thetaDeg
   *          the angle in degrees
   */
  private final void __rotate(final double thetaDeg) {
    this.m_body
        .append(this.__getCommandName(_PGFGraphic.TRANSFORM_ROTATE));
    this.m_body.append('{');
    _PGFGraphic.__number(thetaDeg, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doRotate(final double theta) {
    final double value;

    if (this.m_inScopeReset <= 0) {
      value = _PGFGraphic.__toDeg(theta);
      if (value != 0d) {
        this.__rotate(value);
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
    final double value;

    if (this.m_inScopeReset > 0) {
      value = _PGFGraphic.__toDeg(theta);
      if (value != 0d) {
        this.translate(x, y);
        this.__rotate(theta);
        this.translate(-x, -y);
      }
    }
  }

  /**
   * scale
   *
   * @param v
   *          the value
   */
  private final void __scale(final double v) {
    this.m_body.append(this.__getCommandName(_PGFGraphic.TRANSFORM_SCALE));
    this.m_body.append('{');
    _PGFGraphic.__number(v, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /**
   * scale x
   *
   * @param v
   *          the value
   */
  private final void __scaleX(final double v) {
    this.m_body.append(this
        .__getCommandName(_PGFGraphic.TRANSFORM_SCALE_X));
    this.m_body.append('{');
    _PGFGraphic.__number(v, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /**
   * scale y
   *
   * @param v
   *          the value
   */
  private final void __scaleY(final double v) {
    this.m_body.append(this
        .__getCommandName(_PGFGraphic.TRANSFORM_SCALE_Y));
    this.m_body.append('{');
    _PGFGraphic.__number(v, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /**
   * scale x+y
   *
   * @param x
   *          the x-value
   * @param y
   *          the y-value
   */
  private final void __scaleXY(final double x, final double y) {
    this.m_body.append(this
        .__getCommandName(_PGFGraphic.TRANSFORM_SCALE_XY));
    this.m_body.append('{');
    _PGFGraphic.__number(x, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(y, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doScale(final double sx, final double sy) {
    boolean xNot0, yNot0;

    if (this.m_inScopeReset <= 0) {
      xNot0 = (sx != 0);
      yNot0 = (sy != 0);

      if (xNot0 && yNot0) {
        if (sx == sy) {
          this.__scale(sx);
        } else {
          this.__scaleXY(sx, sy);
        }
      } else {
        if (xNot0) {
          this.__scaleX(sx);
        } else {
          if (yNot0) {
            this.__scaleY(sy);
          } else {
            return;
          }
        }
      }
    }

    super.doScale(sx, sy);
  }

  /**
   * shear x
   *
   * @param v
   *          the value
   */
  private final void __shearX(final double v) {
    this.m_body.append(this
        .__getCommandName(_PGFGraphic.TRANSFORM_SLANT_X));
    this.m_body.append('{');
    _PGFGraphic.__number(v, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /**
   * shear y
   *
   * @param v
   *          the value
   */
  private final void __shearY(final double v) {
    this.m_body.append(this
        .__getCommandName(_PGFGraphic.TRANSFORM_SLANT_Y));
    this.m_body.append('{');
    _PGFGraphic.__number(v, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /**
   * shear x and y
   *
   * @param x
   *          the x-value
   * @param y
   *          the y-value
   */
  private final void __shearXY(final double x, final double y) {
    this.m_body.append(this.__getCommandName(_PGFGraphic.TRANSFORM_SLANT));
    this.m_body.append('{');
    _PGFGraphic.__number(x, this.m_body);
    this.m_body.append('}');
    this.m_body.append('{');
    _PGFGraphic.__number(y, this.m_body);
    _PGFGraphic.__commandEndNL(this.m_body);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doShear(final double shx, final double shy) {
    boolean xNot0, yNot0;

    if (this.m_inScopeReset <= 0) {
      xNot0 = (shx != 0);
      yNot0 = (shy != 0);

      if (xNot0 && yNot0) {
        if (shx == shy) {
          this.__scale(shx);
        } else {
          this.__shearXY(shx, shy);
        }
      } else {
        if (xNot0) {
          this.__shearX(shx);
        } else {
          if (yNot0) {
            this.__shearY(shy);
          } else {
            return;
          }
        }
      }
    }

    super.doShear(shx, shy);
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
    if (this.m_inScopeReset <= 0) {
      this.__setColor(c);
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
      this.__path(clip.getPathIterator(null), true);
      this.__usePathClip();
    }
    super.doSetClip(clip);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doClipRect(final int x, final int y,
      final int width, final int height) {
    if (this.m_inScopeReset <= 0) {
      this.__rectangle(x, y, width, height);
      this.__usePathClip();
    }
    super.doClipRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetClip(final int x, final int y, final int width,
      final int height) {
    if (this.m_inScopeReset <= 0) {
      this.__rectangle(x, y, width, height);
      this.__usePathClip();
    }
    super.doSetClip(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected void doClipRect(final double x, final double y,
      final double width, final double height) {
    if (this.m_inScopeReset <= 0) {
      this.__rectangle(x, y, width, height);
      this.__usePathClip();
    }
    super.doClipRect(x, y, width, height);
  }

  /** {@inheritDoc} */
  @Override
  protected void doSetClip(final double x, final double y,
      final double width, final double height) {
    if (this.m_inScopeReset <= 0) {
      this.__rectangle(x, y, width, height);
      this.__usePathClip();
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
    this.doDrawLine(((double) x1), ((double) y1), ((double) x2),
        ((double) y2));
  }

  /** {@inheritDoc} */
  @Override
  protected void doDrawLine(final double x1, final double y1,
      final double x2, final double y2) {
    this.__pathMoveTo(x1, y1);
    this.__pathLineTo(x2, y2);
    this.__usePathStroke();
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillRect(final int x, final int y,
      final int width, final int height) {
    this.doFillRect(((double) x), ((double) y), ((double) width),
        ((double) height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doFillRect(final double x, final double y,
      final double width, final double height) {
    this.__rectangle(x, y, width, height);
    this.__usePathFill();
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRect(final int x, final int y,
      final int width, final int height) {
    this.doDrawRect(((double) x), ((double) y), ((double) width),
        ((double) height));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDrawRect(final double x, final double y,
      final double width, final double height) {
    this.__rectangle(x, y, width, height);
    this.__usePathStroke();
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
    this.m_body.append(this.__getCommandName(_PGFGraphic.SCOPE_BEGIN));

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

      this.m_body.append(this.__getCommandName(_PGFGraphic.SCOPE_END));
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

  /** the internal command holder */
  private static final class __Command {
    /** the name */
    final char[] m_name;
    /** the number of arguments */
    final int m_args;

    /**
     * create the command
     *
     * @param args
     *          the args
     * @param name
     *          the name
     */
    __Command(final int args, final char[] name) {
      super();
      this.m_name = name;
      this.m_args = args;
    }
  }

  /** an internal buffer */
  private static final class __Buffer extends MemoryTextOutput {
    /** the last new line */
    int m_lastNewLine;

    /** create the buffer */
    __Buffer() {
      super();
    }
  }
}
