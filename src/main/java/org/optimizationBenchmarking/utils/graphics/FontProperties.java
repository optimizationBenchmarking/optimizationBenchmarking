package org.optimizationBenchmarking.utils.graphics;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** A class managing font properties */
public class FontProperties extends HashObject {

  /** the bold font flag */
  protected static final int FONT_FLAG_BOLD = 1;
  /** the italic font flag */
  protected static final int FONT_FLAG_ITALIC = (FontProperties.FONT_FLAG_BOLD << 1);
  /** the underlined font flag */
  protected static final int FONT_FLAG_UNDERLINED = (FontProperties.FONT_FLAG_ITALIC << 1);
  /** the monospace font flag */
  protected static final int FONT_FLAG_MONOSPACE = (FontProperties.FONT_FLAG_UNDERLINED << 1);
  /** the serif font flag */
  protected static final int FONT_FLAG_SERIF = (FontProperties.FONT_FLAG_MONOSPACE << 1);
  /** the sans-serif font flag */
  protected static final int FONT_FLAG_SANS_SERIF = (FontProperties.FONT_FLAG_SERIF << 1);
  /** the dialog font flag */
  protected static final int FONT_FLAG_DIALOG = (FontProperties.FONT_FLAG_SANS_SERIF << 1);
  /** the dialog input font flag */
  protected static final int FONT_FLAG_DIALOG_INPUT = (FontProperties.FONT_FLAG_DIALOG << 1);

  /** the font family flags */
  private static final int FONT_FLAG_FAMILY = (FontProperties.FONT_FLAG_MONOSPACE
      | FontProperties.FONT_FLAG_SERIF
      | FontProperties.FONT_FLAG_SANS_SERIF
      | FontProperties.FONT_FLAG_DIALOG | FontProperties.FONT_FLAG_DIALOG_INPUT);

  /** all the font flags */
  private static final int FONT_FLAG_ALL = (FontProperties.FONT_FLAG_BOLD
      | FontProperties.FONT_FLAG_ITALIC
      | FontProperties.FONT_FLAG_UNDERLINED
      | FontProperties.FONT_FLAG_MONOSPACE
      | FontProperties.FONT_FLAG_SERIF
      | FontProperties.FONT_FLAG_SANS_SERIF
      | FontProperties.FONT_FLAG_DIALOG | FontProperties.FONT_FLAG_DIALOG_INPUT);

  /** the flags */
  protected final int m_flags;

  /**
   * Create the font properties object
   *
   * @param flags
   *          the flags
   */
  protected FontProperties(final int flags) {
    super();
    this.m_flags = (flags & FontProperties.FONT_FLAG_ALL);
  }

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.hashCode(this.m_flags);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof FontProperties) {
      return (((FontProperties) o).m_flags == this.m_flags);
    }
    return false;
  }

  /**
   * Get the font family
   *
   * @return the font family, or {@code null} if no family is defined
   */
  public final EFontFamily getFamily() {
    switch (this.m_flags & FontProperties.FONT_FLAG_FAMILY) {
      case FONT_FLAG_SERIF: {
        return EFontFamily.SERIF;
      }
      case FONT_FLAG_SANS_SERIF: {
        return EFontFamily.SANS_SERIF;
      }
      case FONT_FLAG_MONOSPACE: {
        return EFontFamily.MONOSPACED;
      }
      case FONT_FLAG_DIALOG: {
        return EFontFamily.DIALOG;
      }
      case FONT_FLAG_DIALOG_INPUT: {
        return EFontFamily.DIALOG_INPUT;
      }
      default: {
        return null;
      }
    }
  }

  /**
   * Is this font italic?
   *
   * @return {@code true} for italic fonts, {@code false} for normal ones
   */
  public final boolean isItalic() {
    return ((this.m_flags & FontProperties.FONT_FLAG_ITALIC) != 0);
  }

  /**
   * Is this font bold?
   *
   * @return {@code true} for bold fonts, {@code false} for normal ones
   */
  public final boolean isBold() {
    return ((this.m_flags & FontProperties.FONT_FLAG_BOLD) != 0);
  }

  /**
   * Is this font underlined?
   *
   * @return {@code true} for underlined fonts, {@code false} for normal
   *         ones
   */
  public final boolean isUnderlined() {
    return ((this.m_flags & FontProperties.FONT_FLAG_UNDERLINED) != 0);
  }

  /**
   * <p>
   * Get the {@link java.awt.Font#getStyle() style flags} of this font
   * property set. If the font properties is obtained via
   * {@link #getFontProperties(Font,boolean)} or
   * {@link #getFontProperties(String)} , then this method's result is
   * based on a heuristic detection whether a font is
   * {@link java.awt.Font#BOLD bold} or {@link java.awt.Font#ITALIC} based
   * on the font name.
   * </p>
   * <p>
   * Some fonts have particular name parts which already enforce certain
   * style features. Examples for this are the LaTeX fonts such as
   * {@code cmbx}, {@code cmti}, etc. Although the documentation of
   * {@link java.awt.Font} states that
   * <em>The style argument is merged with the specified face's style, not
   * added or subtracted.</em>, this may not work: The fonts may not be
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
   * @return the style flags, a combination of {@link java.awt.Font#PLAIN},
   *         {@link java.awt.Font#ITALIC}, and {@link java.awt.Font#BOLD}
   */
  public final int getStyle() {
    int f;

    f = 0;
    if ((this.m_flags & FontProperties.FONT_FLAG_ITALIC) != 0) {
      f |= Font.ITALIC;
    }

    if ((this.m_flags & FontProperties.FONT_FLAG_BOLD) != 0) {
      f |= Font.BOLD;
    }

    if (f != 0) {
      return f;
    }
    return Font.PLAIN;
  }

  /**
   * Get the font properties for a font name in a heuristic way
   *
   * @param name
   *          the font name
   * @return the font family
   */
  @SuppressWarnings("null")
  public static final FontProperties getFontProperties(final String name) {
    String str, beginner;
    int cmpMode, j, i;
    char ch;

    if (name == null) {
      return null;
    }
    beginner = TextUtils.normalize(name);
    if (beginner == null) {
      return null;
    }
    beginner = TextUtils.toLowerCase(beginner);

    for (cmpMode = 0; cmpMode <= 3; cmpMode++) {
      str = beginner;

      inner: for (;;) {
        // test all flags
        for (final __FontFlags f : __FontFlags.FLAGS) {
          switch (cmpMode) {
            case 0: {
              if (f.m_name.equals(str)) {
                return f;
              }
              break;
            }
            case 1: {
              if (f.m_name.startsWith(str)) {
                return f;
              }
              break;
            }
            case 2: {
              if (f.m_name.endsWith(str)) {
                return f;
              }
              break;
            }
            default: {
              if (f.m_name.contains(str)) {
                return f;
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

    return null;
  }

  /**
   * Get the font properties for a given font
   *
   * @param font
   *          the font object
   * @param useOnlyName
   *          only use the font name to get the properties
   * @return the font properties
   */
  @SuppressWarnings("incomplete-switch")
  public static final FontProperties getFontProperties(final Font font,
      final boolean useOnlyName) {
    FontProperties fp;
    int style, fam1, fam2;

    if (font == null) {
      return null;
    }
    fp = FontProperties.getFontProperties(font.getFontName());
    if (fp == null) {
      fp = FontProperties.getFontProperties(font.getFontName(Locale.US));
      if (fp == null) {
        fp = FontProperties.getFontProperties(font.getPSName());
        if (fp == null) {
          fp = FontProperties.getFontProperties(font.getName());
          if (fp == null) {
            fp = FontProperties.getFontProperties(font.getFamily());
            if (fp == null) {
              fp = FontProperties.getFontProperties(//
                  font.getFamily(Locale.US));
            }
          }
        }
      }
    }

    if (useOnlyName) {
      return fp;
    }

    style = 0;
    if (font.isBold()) {
      style |= FontProperties.FONT_FLAG_BOLD;
    }
    if (font.isItalic()) {
      style |= FontProperties.FONT_FLAG_ITALIC;
    }

    if (FontProperties.isFontUnderlined(font)) {
      style |= FontProperties.FONT_FLAG_UNDERLINED;
    }

    switch (font.getFamily()) {
      case Font.SANS_SERIF: {
        style |= FontProperties.FONT_FLAG_SANS_SERIF;
        break;
      }
      case Font.SERIF: {
        style |= FontProperties.FONT_FLAG_SERIF;
        break;
      }
      case Font.MONOSPACED: {
        style |= FontProperties.FONT_FLAG_MONOSPACE;
        break;
      }
      case Font.DIALOG: {
        style |= FontProperties.FONT_FLAG_DIALOG;
        break;
      }
      case Font.DIALOG_INPUT: {
        style |= FontProperties.FONT_FLAG_DIALOG_INPUT;
        break;
      }
    }

    if (fp != null) {
      fam1 = (style & FontProperties.FONT_FLAG_FAMILY);
      fam2 = (style & FontProperties.FONT_FLAG_FAMILY);
      style |= (fp.m_flags & (~FontProperties.FONT_FLAG_FAMILY));
      if (((fam1 == 0) || (fam1 == FontProperties.FONT_FLAG_DIALOG) || (fam1 == FontProperties.FONT_FLAG_DIALOG_INPUT))
          && (((fam2 != 0) && (fam2 != FontProperties.FONT_FLAG_DIALOG) && (fam2 != FontProperties.FONT_FLAG_DIALOG_INPUT)))) {
        style = ((style & (~FontProperties.FONT_FLAG_FAMILY)) | fam2);
      }
      if (style == fp.m_flags) {
        return fp;
      }
    }

    return new FontProperties(style);
  }

  /**
   * Check whether a font is underlined or not. This method bases its
   * result solely on the properties of the font, it does not perform a
   * resource-based lookup. If you want this, use
   * <code>{@link #getFontProperties(Font, boolean) getFontProperties(Font, false)}.{@link #isUnderlined()}</code>
   * instead.
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

  /**
   * get the flags
   *
   * @param data
   *          the data string
   * @param idx
   *          the start index
   * @return the flags
   */
  static final int _flags(final String data, final int idx) {
    int i, f;

    i = idx;

    f = 0;
    for (; (--i) >= 0;) {
      switch (data.charAt(i)) {
        case 'b':
        case 'B': {
          f |= FontProperties.FONT_FLAG_BOLD;
          break;
        }
        case 'i':
        case 'I': {
          f |= FontProperties.FONT_FLAG_ITALIC;
          break;
        }
        case 'm':
        case 'M': {
          f |= FontProperties.FONT_FLAG_MONOSPACE;
          break;
        }
        case 's':
        case 'S': {
          f |= FontProperties.FONT_FLAG_SANS_SERIF;
          break;
        }
        case 'r':
        case 'R': {
          f |= FontProperties.FONT_FLAG_SERIF;
          break;
        }
        case 'u':
        case 'U': {
          f |= FontProperties.FONT_FLAG_UNDERLINED;
          break;
        }
        default: {
          throw new IllegalArgumentException("Illegal flag: " //$NON-NLS-1$
              + data.charAt(i));
        }
      }
    }

    return f;
  }

  /** the internal font flag class */
  private static final class __FontFlags extends FontProperties implements
      Comparable<__FontFlags> {

    /** the array with the font flags */
    static final __FontFlags[] FLAGS;

    // load the flags of the known fonts
    static {
      ArrayList<__FontFlags> al;
      String s;

      al = new ArrayList<>();
      try (final InputStream is = //
      FontProperties.class.getResourceAsStream("knownFonts.txt")) { //$NON-NLS-1$
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
        ErrorUtils.logError(Configuration.getGlobalLogger(),//
            "Error when reading list of known fonts.", //$NON-NLS-1$
            ioe, true, RethrowMode.AS_RUNTIME_EXCEPTION);
      }

      FLAGS = al.toArray(new __FontFlags[al.size()]);
      Arrays.sort(__FontFlags.FLAGS);
    }

    /** the font name */
    final String m_name;

    /**
     * create
     *
     * @param data
     *          the data string
     * @param idx
     *          the index
     */
    private __FontFlags(final String data, final int idx) {
      super(FontProperties._flags(data, idx));
      this.m_name = TextUtils.toLowerCase(TextUtils.prepare(//
          data.substring(idx + 1)));
    }

    /**
     * create
     *
     * @param data
     *          the data string
     */
    __FontFlags(final String data) {
      this(data, data.indexOf(' '));
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
}
