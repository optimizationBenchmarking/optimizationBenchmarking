package org.optimizationBenchmarking.utils.graphics;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.style.EFontFamily;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** a utility class for graphics stuff */
public final class GraphicUtils {

  /** the default renHashMap<K, V>hints */
  private static final RenderingHintHolder[] DEFAULT_RENDERING_HINTS;

  static {
    DEFAULT_RENDERING_HINTS = new RenderingHintHolder[10];

    GraphicUtils.DEFAULT_RENDERING_HINTS[0] = new RenderingHintHolder(
        RenderingHints.KEY_FRACTIONALMETRICS,
        RenderingHints.VALUE_FRACTIONALMETRICS_ON);

    GraphicUtils.DEFAULT_RENDERING_HINTS[1] = new RenderingHintHolder(
        RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    GraphicUtils.DEFAULT_RENDERING_HINTS[2] = new RenderingHintHolder(
        RenderingHints.KEY_COLOR_RENDERING,
        RenderingHints.VALUE_COLOR_RENDER_QUALITY);

    GraphicUtils.DEFAULT_RENDERING_HINTS[3] = new RenderingHintHolder(
        RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

    GraphicUtils.DEFAULT_RENDERING_HINTS[4] = new RenderingHintHolder(
        RenderingHints.KEY_STROKE_CONTROL,
        RenderingHints.VALUE_STROKE_NORMALIZE);

    GraphicUtils.DEFAULT_RENDERING_HINTS[5] = new RenderingHintHolder(
        RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    GraphicUtils.DEFAULT_RENDERING_HINTS[6] = new RenderingHintHolder(
        RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    GraphicUtils.DEFAULT_RENDERING_HINTS[7] = new RenderingHintHolder(
        RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);

    GraphicUtils.DEFAULT_RENDERING_HINTS[8] = new RenderingHintHolder(
        RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);

    GraphicUtils.DEFAULT_RENDERING_HINTS[9] = new RenderingHintHolder(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

  }

  /** the bold font flag */
  private static final int FONT_FLAG_BOLD = 1;
  /** the italic font flag */
  private static final int FONT_FLAG_ITALIC = (GraphicUtils.FONT_FLAG_BOLD << 1);
  /** the monospace font flag */
  private static final int FONT_FLAG_MONOSPACE = (GraphicUtils.FONT_FLAG_ITALIC << 1);
  /** the serif font flag */
  private static final int FONT_FLAG_SERIF = (GraphicUtils.FONT_FLAG_MONOSPACE << 1);
  /** the sans-serif font flag */
  private static final int FONT_FLAG_SANS_SERIF = (GraphicUtils.FONT_FLAG_SERIF << 1);

  /** the font family */
  private static final int FONT_FLAG_FAMILY = (GraphicUtils.FONT_FLAG_MONOSPACE
      | GraphicUtils.FONT_FLAG_SERIF | GraphicUtils.FONT_FLAG_SANS_SERIF);

  /** the array with the font flags */
  private static final __FontFlags[] FLAGS;

  // load the flags of the known fonts
  static {
    ArrayList<__FontFlags> al;
    String s;

    al = new ArrayList<>();
    try (final InputStream is = //
    GraphicUtils.class.getResourceAsStream("knownFonts.txt")) { //$NON-NLS-1$
      try (final InputStreamReader isr = new InputStreamReader(is)) {
        try (final BufferedReader br = new BufferedReader(isr)) {
          while ((s = br.readLine()) != null) {
            s = TextUtils.normalize(s);
            if ((s != null) && (s.charAt(0) != '#')) {
              al.add(new __FontFlags(s));
            }
          }
        }
      }
    } catch (final Throwable ioe) {
      ErrorUtils.throwAsRuntimeException(ioe);
    }

    FLAGS = al.toArray(new __FontFlags[al.size()]);
    Arrays.sort(GraphicUtils.FLAGS);
  }

  /**
   * Check whether a font is underlined or not
   * 
   * @param font
   *          the font
   * @return {@code true} if the font is underlined, {@code false}
   *         otherwise
   */
  public static final boolean isFontUnderlined(final Font font) {
    final Number underlineAtt;

    underlineAtt = ((Number) (font.getAttributes()
        .get(TextAttribute.UNDERLINE)));
    return ((underlineAtt != null) && (underlineAtt.intValue() >= 0));
  }

  /** the forbidden constructor */
  private GraphicUtils() {
    ErrorUtils.doNotCall();
  }

  /**
   * Set the default rendering hints
   * 
   * @param g
   *          the graphic to initialize
   */
  public static final void setDefaultRenderingHints(final Graphics2D g) {
    for (final RenderingHintHolder hint : GraphicUtils.DEFAULT_RENDERING_HINTS) {
      g.setRenderingHint(hint.m_key, hint.m_value);
    }
  }

  /**
   * Create the default rendering hints
   * 
   * @return a default set of rendering hints
   */
  public static final RenderingHints createDefaultRenderingHints() {
    RenderingHintHolder hint;
    RenderingHints h;
    int i;

    i = GraphicUtils.DEFAULT_RENDERING_HINTS.length;
    hint = GraphicUtils.DEFAULT_RENDERING_HINTS[--i];
    h = new RenderingHints(hint.m_key, hint.m_value);
    for (; (--i) >= 0;) {
      hint = GraphicUtils.DEFAULT_RENDERING_HINTS[i];
      h.put(hint.m_key, hint.m_value);
    }

    return h;
  }

  /**
   * Get the flags for a font name in a heuristic way
   * 
   * @param name
   *          the font name
   * @return the font family
   */
  private static final int __fontFlags(final String name) {
    String str, beginner;
    int cmpMode, j, i;
    char ch;

    if (name == null) {
      return 0;
    }
    beginner = TextUtils.normalize(name);
    if (beginner == null) {
      return 0;
    }
    beginner = beginner.toLowerCase();

    for (cmpMode = 0; cmpMode <= 3; cmpMode++) {
      str = beginner;

      inner: for (;;) {
        // test all flags
        for (final __FontFlags f : GraphicUtils.FLAGS) {
          switch (cmpMode) {
            case 0: {
              if (name.equals(str)) {
                return f.m_flags;
              }
              break;
            }
            case 1: {
              if (name.startsWith(str)) {
                return f.m_flags;
              }
              break;
            }
            case 2: {
              if (name.endsWith(str)) {
                return f.m_flags;
              }
              break;
            }
            default: {
              if (name.contains(str)) {
                return f.m_flags;
              }
              break;
            }
          }
        }

        // no match: try to make string shorter

        // first: try to remove font size suffixes
        j = str.length();
        numberSuffix: for (i = j; (--i) >= 0;) {
          ch = str.charAt(i);
          if ((ch < '0') || (ch > '9')) {
            break numberSuffix;
          }
        }
        i++;
        if ((i < j) && (i > 0)) {
          str = TextUtils.prepare(str.substring(0, i));
          if (str != null) {
            continue inner;
          }
        }

        // try to divide the name by removing suffixes
        i = str.lastIndexOf(' ');
        j = str.lastIndexOf('-');
        if (j > i) {
          i = j;
        }
        if (i > 0) {
          str = TextUtils.prepare(str.substring(0, i));
          if (str != null) {
            continue inner;
          }
        }

        break inner;
      }
    }

    return 0;
  }

  /**
   * Try to obtain the font family from a font name heuristically. Return
   * {@code null} if the family could not be derived. No guarantee can be
   * given that this method will work and provide the correct family. It
   * should only be used in absence of any other information.
   * 
   * @param name
   *          the font name
   * @return the font family
   */
  public static final EFontFamily getFontFamilyFromFontName(
      final String name) {
    switch (GraphicUtils.__fontFlags(name) & GraphicUtils.FONT_FLAG_FAMILY) {
      case FONT_FLAG_SERIF: {
        return EFontFamily.SERIF;
      }
      case FONT_FLAG_SANS_SERIF: {
        return EFontFamily.SANS_SERIF;
      }
      case FONT_FLAG_MONOSPACE: {
        return EFontFamily.MONOSPACED;
      }
      default: {
        return null;
      }
    }
  }

  /**
   * <p>
   * This method tries to heuristically determine whether a font is
   * {@link java.awt.Font#BOLD bold} or {@link java.awt.Font#ITALIC} based
   * on the font name.
   * </p>
   * <p>
   * Some fonts have particular name parts which already enforce certain
   * style features. Examples for this are the LaTeX fonts such as
   * {@code cmbx}, {@code cmti}, etc. Although the documentation of
   * {@link java.awt.Font} states that
   * <q>The style argument is merged with the specified face's style, not
   * added or subtracted.</q>, this may not work: The fonts may not be
   * annotated as bold while being bold, for instance, resulting in
   * super-bold fonts if loaded in the &quot;bold&quot; way. Other fonts
   * may already be italic, slanted, or oblique, but get italic-ized
   * additionally by the rendering engine, leading to odd results.
   * </p>
   * <p>
   * Thus, the information returned by this method may be quite helpful
   * when loading fonts. Let's say it returns {@code mask} for a given
   * font. Then, the &quot;goal&quot; style constant based to the
   * {@link java.awt.Font#Font(String, int, int) font constructor} may be
   * {@code and-not}-ed ({@code style & (~mask)}) with this return value.
   * The output of {@link java.awt.Font#getStyle()}, on the other hand, may
   * be {@code or}-ed ({@code | mask}) with the result of this method.
   * </p>
   * 
   * @param name
   *          the font name to analyze
   * @return a bit mask that can be xor-ed with the base style
   */
  public static final int getFontStyleFromFontName(final String name) {
    final int f;
    int r;

    f = GraphicUtils.__fontFlags(name);
    r = 0;
    if ((f & GraphicUtils.FONT_FLAG_BOLD) != 0) {
      r |= Font.BOLD;
    }
    if ((f & GraphicUtils.FONT_FLAG_ITALIC) != 0) {
      r |= Font.ITALIC;
    }

    return r;
  }

  /** the font flags */
  private static final class __FontFlags implements
      Comparable<__FontFlags> {
    /** the font name */
    final String m_name;
    /** the flags */
    final int m_flags;

    /**
     * create
     * 
     * @param data
     *          the data string
     */
    __FontFlags(final String data) {
      super();
      int i, f;

      i = data.indexOf(' ');
      this.m_name = TextUtils.prepare(data.substring(i + 1)).toLowerCase();

      f = 0;
      for (; (--i) >= 0;) {
        switch (data.charAt(i)) {
          case 'b':
          case 'B': {
            f |= GraphicUtils.FONT_FLAG_BOLD;
            break;
          }
          case 'i':
          case 'I': {
            f |= GraphicUtils.FONT_FLAG_ITALIC;
            break;
          }
          case 'm':
          case 'M': {
            f |= GraphicUtils.FONT_FLAG_MONOSPACE;
            break;
          }
          case 's':
          case 'S': {
            f |= GraphicUtils.FONT_FLAG_SANS_SERIF;
            break;
          }
          case 'r':
          case 'R': {
            f |= GraphicUtils.FONT_FLAG_SERIF;
            break;
          }
          default: {
            throw new IllegalArgumentException("Illegal flag:" //$NON-NLS-1$
                + data.charAt(i));
          }
        }
      }
      this.m_flags = f;
    }

    /** {@inheritDoc} */
    @Override
    public final int compareTo(final __FontFlags o) {
      String a, b;
      int r;

      if (this == o) {
        return 0;
      }
      if (o == null) {
        return (-1);
      }
      a = this.m_name;
      b = o.m_name;
      r = Integer.compare(b.length(), a.length());
      if (r != 0) {
        return r;
      }
      return a.compareTo(b);
    }
  }

  /** the internal rendering hint holder */
  private static final class RenderingHintHolder {
    /** the key */
    final Key m_key;
    /** the value */
    final Object m_value;

    /**
     * create the hint holder
     * 
     * @param key
     *          the key
     * @param value
     *          the value
     */
    RenderingHintHolder(final Key key, final Object value) {
      super();
      this.m_key = key;
      this.m_value = value;
    }
  }
}
