package org.optimizationBenchmarking.utils.graphics.style.font;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.graphics.style.EFontFamily;
import org.optimizationBenchmarking.utils.graphics.style.PaletteBuilder;
import org.optimizationBenchmarking.utils.graphics.style.PaletteElementBuilder;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.parsers.BooleanParser;
import org.optimizationBenchmarking.utils.parsers.IntParser;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.text.transformations.NormalCharTransformer;

/** A builder for font styles */
public final class FontStyleBuilder extends
    PaletteElementBuilder<FontStyle> {

  /** the font attributes */
  private static final HashMap<TextAttribute, Object> FONT_ATTRIBUTES;

  static {
    FONT_ATTRIBUTES = new HashMap<>();
    FontStyleBuilder.FONT_ATTRIBUTES.put(TextAttribute.KERNING,
        TextAttribute.KERNING_ON);
    FontStyleBuilder.FONT_ATTRIBUTES.put(TextAttribute.LIGATURES,
        TextAttribute.LIGATURES_ON);
  }

  /** the font family */
  private volatile EFontFamily m_family;

  /** is the font italic ? */
  private volatile boolean m_italic;

  /** is the font bold? */
  private volatile boolean m_bold;

  /** is the fond underlined? */
  private volatile boolean m_underlined;

  /**
   * the font size in
   * {@link org.optimizationBenchmarking.utils.math.units.ELength#POINT pt}
   */
  private volatile int m_size;

  /** the name and face choices */
  private LinkedHashSet<String> m_faceChoices;

  /** the choice state */
  final int m_choice;

  /** the id */
  private String m_id;

  /** create the font style builder */
  public FontStyleBuilder() {
    this(null, 5, null);
  }

  /**
   * A builder for font styles
   * 
   * @param owner
   *          the owning style builder
   * @param choice
   *          the choice of the style builder
   * @param id
   *          the id
   */
  protected FontStyleBuilder(
      final PaletteBuilder<FontStyle, FontPalette> owner,
      final int choice, final String id) {
    super(owner);
    this.m_family = EFontFamily.SERIF;
    this.m_faceChoices = new LinkedHashSet<>();
    this.m_choice = choice;
    this.m_id = TextUtils.prepare(id);
    this.open();
  }

  /**
   * Check whether a font family is acceptable
   * 
   * @param family
   *          the font family
   */
  static final void _checkFontFamily(final EFontFamily family) {
    if (family == null) {
      throw new IllegalArgumentException(//
          "Font family cannot be set to null."); //$NON-NLS-1$
    }
  }

  /**
   * Set the font family
   * 
   * @param family
   *          the font family
   */
  public synchronized final void setFontFamily(final EFontFamily family) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    FontStyleBuilder._checkFontFamily(family);
    this.m_family = family;
  }

  /**
   * Get the font family
   * 
   * @return the font family
   */
  public final EFontFamily getFamily() {
    return this.m_family;
  }

  /**
   * Set whether this font should be italic
   * 
   * @param italic
   *          {@code true} if this font is italic
   */
  public synchronized final void setItalic(final boolean italic) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.m_italic = italic;
  }

  /**
   * Is this font italic?
   * 
   * @return {@code true} for italic fonts, {@code false} for normal ones
   */
  public final boolean isItalic() {
    return this.m_italic;
  }

  /**
   * Set whether this font should be bold
   * 
   * @param bold
   *          {@code true} if this font is bold
   */
  public synchronized final void setBold(final boolean bold) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.m_bold = bold;
  }

  /**
   * Is this font bold?
   * 
   * @return {@code true} for bold fonts, {@code false} for normal ones
   */
  public final boolean isBold() {
    return this.m_bold;
  }

  /**
   * Set whether this font should be underlined
   * 
   * @param underlined
   *          {@code true} if this font is underlined
   */
  public synchronized final void setUnderlined(final boolean underlined) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.m_underlined = underlined;
  }

  /**
   * Is this font underlined?
   * 
   * @return {@code true} for underlined fonts, {@code false} for normal
   *         ones
   */
  public final boolean isUnderlined() {
    return this.m_underlined;
  }

  /**
   * Check the size of font
   * 
   * @param size
   *          the size to check
   */
  static final void _checkSize(final int size) {
    if (size < 1) {
      throw new IllegalArgumentException(//
          "Size cannot be less than 1, but is " + size); //$NON-NLS-1$
    }
    if (size > 72) {
      throw new IllegalArgumentException(//
          "Size cannot be greater than 72, but is " + size); //$NON-NLS-1$
    }
  }

  /**
   * Set the size of this font style in
   * {@link org.optimizationBenchmarking.utils.math.units.ELength#POINT pt}
   * 
   * @param size
   *          the size of this font style in
   *          {@link org.optimizationBenchmarking.utils.math.units.ELength#POINT
   *          pt}
   */
  public synchronized final void setSize(final int size) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    FontStyleBuilder._checkSize(size);
    this.m_size = size;
  }

  /**
   * Get the size of this font in
   * {@link org.optimizationBenchmarking.utils.math.units.ELength#POINT pt}
   * 
   * @return the size of this font in
   *         {@link org.optimizationBenchmarking.utils.math.units.ELength#POINT
   *         pt}
   */
  public final int getSize() {
    return this.m_size;
  }

  /**
   * Add a font face choice. Generally, the builder maintains an ordered
   * list of face choices. During the {@link #compile() compilation}, it
   * will process this list one by one and try to load the corresponding
   * font faces, until it succeeds. The font actually loaded will then
   * become the first face choice of the {@link FontStyle font style}. The
   * other choices added with {@link #addFaceChoice(String)} are added
   * behind it. The last face choice will be the {@link #getFamily() font
   * family}.
   * 
   * @param s
   *          the string
   * @return {@code true} if the face choice was new and addable,
   *         {@code false} if it was already stored OR if it was an empty
   *         or {@code null} string
   */
  public synchronized final boolean addFaceChoice(final String s) {
    final String t;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    t = this.normalize(s);
    if (t != null) {
      return this.m_faceChoices.add(t);
    }
    return false;
  }

  /**
   * Add several face choices at once, as specified in
   * {@link #addFaceChoice(String)}
   * 
   * @param choices
   *          the choices
   * @return {@code true} if at least one new choice was added
   */
  public synchronized final boolean addFaceChoices(
      final Iterable<String> choices) {
    boolean ret;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    ret = false;
    for (final String s : choices) {
      if (this.addFaceChoice(s)) {
        ret = true;
      }
    }
    return ret;
  }

  /**
   * Copy the contents of a given font style into this style builder
   * 
   * @param base
   *          the font style to copy
   */
  public synchronized final void copyFrom(final FontStyle base) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.setBold(base.isBold());
    this.setFontFamily(base.getFamily());
    this.setItalic(base.isItalic());
    this.setSize(base.getSize());
    this.setUnderlined(base.isUnderlined());
    this.addFaceChoices(base.getFaceChoices());
  }

  /**
   * Does a font fit to a given name choice?
   * 
   * @param font
   *          the font
   * @param name
   *          the name choice
   * @return {@code true} if the font fits, {@code false} otherwise
   */
  private static final boolean __matches(final Font font, final String name) {
    final int ml;
    String s;
    int l;

    ml = name.length();
    s = font.getFontName(Locale.US);
    l = s.length();
    if (l <= ml) {
      if (s.substring(0, ml).equalsIgnoreCase(name)) {
        return true;
      }
    }

    s = font.getFontName();
    l = s.length();
    if (l <= ml) {
      if (s.substring(0, ml).equalsIgnoreCase(name)) {
        return true;
      }
    }

    s = font.getName();
    l = s.length();
    if (l <= ml) {
      if (s.substring(0, ml).equalsIgnoreCase(name)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Some fonts have particular name parts which already enforce certain
   * style features. Examples for this are the LaTeX fonts such as cmbx,
   * cmti, etc. Although the documentation of {@link java.awt.Font} states
   * that <quote>The style argument is merged with the specified face's
   * style, not added or subtracted.</quote>, this may not work: The fonts
   * may not be annotated as bold while being bold, for instance, resulting
   * in super-bold fonts if loaded in the "bold" way.
   * 
   * @param name
   *          the font name to analyze
   * @return a bit mask that can be x-ored with the base style
   */
  private static final int __styleFromName(final String name) {
    final String lc;

    lc = name.toLowerCase();
    if (lc.startsWith("cmbxti")) { //$NON-NLS-1$
      return (Font.ITALIC | Font.BOLD);
    }
    if (lc.startsWith("cmti") || //$NON-NLS-1$
        lc.startsWith("cmssi") || //$NON-NLS-1$
        lc.startsWith("cmitt")) {//$NON-NLS-1$
      return Font.ITALIC;
    }
    if (lc.startsWith("cmbx") || //$NON-NLS-1$
        lc.startsWith("cmssbx")) { //$NON-NLS-1$
      return Font.BOLD;
    }
    return Font.PLAIN;
  }

  /**
   * Build an obtain the font style. During this process, we try to resolve
   * the logical aspects of the font style to a physical one, i.e., a font
   * family to a font. The list of face choices is subsequently updated to
   * represent the actual physical choice.
   * 
   * @return the font style
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected final FontStyle compile() {
    final ArrayList<Font> lst;
    int style, i, mask;
    String used;
    Font f, g;
    EFontFamily fam1, fam2;
    HashMap<TextAttribute, Object> map;
    final LinkedHashSet<String> faceChoices, choices;
    MemoryTextOutput idb;

    style = 0;
    if (this.m_bold) {
      style |= Font.BOLD;
    }
    if (this.m_italic) {
      style |= Font.ITALIC;
    }
    if (style == 0) {
      // this is unnecessary, but to be on the safe side...
      style = Font.PLAIN;
    }

    // try to find a font fitting to the choices we have
    f = null;
    used = null;
    faceChoices = this.m_faceChoices;
    this.m_faceChoices = null;
    finder: {
      for (final String s : faceChoices) {
        try {
          mask = FontStyleBuilder.__styleFromName(s);
          f = new Font(s, (style ^ mask), this.m_size);
          if (FontStyleBuilder.__matches(f, s)) {
            break finder;
          }
        } catch (final Throwable t) {
          // odd, but let's ignore any error here
        }
      }

      mask = Font.PLAIN;
      f = new Font(this.m_family.getFontFamilyName(), style, this.m_size);
    }

    lst = new ArrayList<>();
    lst.add(f);

    // now we try to derive a font fitting exactly to the specifications
    if (f.getSize() != this.m_size) {
      // adapt size
      g = f.deriveFont(this.m_size);
      if (g != f) {
        f = g;
        lst.add(f);
      }
    }

    // adapt style
    if ((style ^ mask) != f.getStyle()) {
      g = f.deriveFont(style ^ mask);
      if (g != f) {
        f = g;
        lst.add(f);
      }
    }

    // adapt rest: underline, kerning, ligantures
    map = ((HashMap) (FontStyleBuilder.FONT_ATTRIBUTES.clone()));

    if (this.m_size != f.getSize()) {
      map.put(TextAttribute.SIZE, Integer.valueOf(this.m_size));
    }

    if (this.m_underlined) {
      map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    } else {
      map.put(TextAttribute.UNDERLINE, Integer.valueOf(-1));
    }

    if ((this.m_bold != f.isBold()) && ((mask & Font.BOLD) == 0)) {
      if (this.m_bold) {
        map.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
      } else {
        map.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR);
      }
    }

    if ((this.m_italic != f.isItalic()) && ((mask & Font.ITALIC) == 0)) {
      if (this.m_italic) {
        map.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
      } else {
        map.put(TextAttribute.POSTURE, TextAttribute.POSTURE_REGULAR);
      }
    }

    g = f.deriveFont(map);
    if (g != f) {
      f = g;
      lst.add(f);
    }

    // now let us update the choices list to represent the setting
    choices = new LinkedHashSet<>(faceChoices.size() + 3
        + (6 * lst.size()));
    for (i = lst.size(); (--i) >= 0;) {
      g = lst.get(i);
      choices.add(g.getFontName(Locale.US));
      choices.add(g.getFontName());
      choices.add(g.getName());
      choices.add(g.getPSName());
      choices.add(g.getFamily(Locale.US));
      choices.add(g.getFamily());
    }
    if (used != null) {
      choices.add(used);
    }
    choices.addAll(faceChoices);

    fam1 = this.m_family;
    if ((fam1 == null) || (fam1 == EFontFamily.DIALOG)
        || (fam1 == EFontFamily.DIALOG_INPUT)) {
      fam2 = FontStyleBuilder.__getFamily(f.getFamily(Locale.US));
      if (fam2 == null) {
        fam2 = FontStyleBuilder.__getFamily(f.getFamily());
        if (fam2 == null) {
          fam2 = FontStyleBuilder.__getFamily(f.getFontName(Locale.US));
          if (fam2 == null) {
            fam2 = FontStyleBuilder.__getFamily(f.getFontName());
            if (fam2 == null) {
              fam2 = FontStyleBuilder.__getFamily(f.getPSName());
            }
          }
        }
      }
    } else {
      fam2 = fam1;
    }

    choices.add(fam1.getFontFamilyName());
    if (fam2 != fam1) {
      choices.add(fam2.getFontFamilyName());
    }

    // generate id
    if (this.m_id == null) {
      idb = new MemoryTextOutput();
      idb.append(choices.iterator().next());
      if (this.m_bold) {
        idb.append('_');
        idb.append('b');
      }
      if (this.m_italic) {
        idb.append('_');
        idb.append('i');
      }
      if (this.m_underlined) {
        idb.append('_');
        idb.append('u');
      }
      idb.append('_');
      idb.append(this.m_size);
      this.m_id = NormalCharTransformer.INSTANCE.transform(idb.toString(),
          TextUtils.DEFAULT_NORMALIZER_FORM);
    }

    // ok, now we have everything
    return new FontStyle(((fam2 != null) ? fam2 : fam1), this.m_size,
        this.m_italic, this.m_bold, this.m_underlined, f,
        new ArrayListView<>(choices.toArray(new String[choices.size()])),
        this.m_id);
  }

  /**
   * Try to obtain the font family from a font name heuristically
   * 
   * @param name
   *          the font name
   * @return the font family
   */
  @SuppressWarnings("incomplete-switch")
  private static final EFontFamily __getFamily(final String name) {
    String sub;
    int i;

    if (name == null) {
      return null;
    }

    sub = name.toLowerCase();

    for (;;) {

      switch (sub) {
        case "arial": { //$NON-NLS-1$
          return EFontFamily.SANS_SERIF;
        }

        case "cambria": {//$NON-NLS-1$
          return EFontFamily.SERIF;
        }

        case "cmr"://$NON-NLS-1$
        case "cmb"://$NON-NLS-1$
        case "cmbx"://$NON-NLS-1$
        case "computer modern": {//$NON-NLS-1$
          return EFontFamily.SERIF;
        }

        case "cms": {//$NON-NLS-1$
          return EFontFamily.SANS_SERIF;
        }

        case "cmt": //$NON-NLS-1$
        case "cmtx": {//$NON-NLS-1$
          return EFontFamily.MONOSPACED;
        }

        case "calibri": {//$NON-NLS-1$
          return EFontFamily.SANS_SERIF;
        }

        case "comic"://$NON-NLS-1$
        case "comic sans": {//$NON-NLS-1$
          return EFontFamily.SANS_SERIF;
        }

        case "consolas": //$NON-NLS-1$
        case "courier": { //$NON-NLS-1$
          return EFontFamily.MONOSPACED;
        }

        case "georgia": { //$NON-NLS-1$
          return EFontFamily.SERIF;
        }

        case "gothic": { //$NON-NLS-1$
          return EFontFamily.SANS_SERIF;
        }

        case "helvetica": { //$NON-NLS-1$
          return EFontFamily.SANS_SERIF;
        }

        case "liberation mono": { //$NON-NLS-1$
          return EFontFamily.MONOSPACED;
        }
        case "liberation sans": { //$NON-NLS-1$
          return EFontFamily.SANS_SERIF;
        }
        case "liberation serif": { //$NON-NLS-1$
          return EFontFamily.SERIF;
        }

        case "lucida console": { //$NON-NLS-1$
          return EFontFamily.MONOSPACED;
        }
        case "lucida sans": { //$NON-NLS-1$
          return EFontFamily.SANS_SERIF;
        }

        case "nimbus mono": { //$NON-NLS-1$
          return EFontFamily.MONOSPACED;
        }

        case "nimbus roman": { //$NON-NLS-1$
          return EFontFamily.SERIF;
        }

        case "nimbus sans": { //$NON-NLS-1$
          return EFontFamily.SANS_SERIF;
        }

        case "terminal": { //$NON-NLS-1$
          return EFontFamily.MONOSPACED;
        }

        case "times": //$NON-NLS-1$          
        case "times new": //$NON-NLS-1$   
        case "times new roman": { //$NON-NLS-1$
          return EFontFamily.SERIF;
        }

        case "verdana": { //$NON-NLS-1$
          return EFontFamily.SANS_SERIF;
        }
      }

      if (sub.endsWith("sans serif")) { //$NON-NLS-1$
        return EFontFamily.SANS_SERIF;
      }
      if (sub.endsWith("serif")) { //$NON-NLS-1$
        return EFontFamily.SERIF;
      }
      if (sub.endsWith("monospaced")) { //$NON-NLS-1$
        return EFontFamily.MONOSPACED;
      }

      i = name.lastIndexOf(' ');
      if (i <= 0) {
        break;
      }

      sub = sub.substring(0, i);
    }

    if (sub.contains("sans serif") || //$NON-NLS-1$
        sub.contains("gothic")) { //$NON-NLS-1$
      return EFontFamily.SANS_SERIF;
    }
    if (sub.contains("serif") || //$NON-NLS-1$
        sub.contains("roman")) { //$NON-NLS-1$
      return EFontFamily.SERIF;
    }

    if (sub.startsWith("cmr") || //$NON-NLS-1$
        sub.startsWith("cmb")) { //$NON-NLS-1$
      return EFontFamily.SERIF;
    }
    if (sub.startsWith("cms")) { //$NON-NLS-1$
      return EFontFamily.SANS_SERIF;
    }
    if (sub.startsWith("cmt")) { //$NON-NLS-1$
      return EFontFamily.SANS_SERIF;
    }

    return null;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void fromStrings(final Iterator<String> strings) {
    String t;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    this.setFontFamily(EFontFamily.valueOf(strings.next().toUpperCase()));

    t = strings.next();
    this.setBold((t != null) && BooleanParser.INSTANCE.parseBoolean(t));

    t = strings.next();
    this.setItalic((t != null) && BooleanParser.INSTANCE.parseBoolean(t));

    t = strings.next();
    this.setUnderlined((t != null)
        && BooleanParser.INSTANCE.parseBoolean(t));

    this.setSize(IntParser.INSTANCE.parseInt(strings.next()));

    while (strings.hasNext()) {
      this.addFaceChoice(strings.next());
    }
  }

}
