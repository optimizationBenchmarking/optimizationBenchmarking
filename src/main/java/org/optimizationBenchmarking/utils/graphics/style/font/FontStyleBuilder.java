package org.optimizationBenchmarking.utils.graphics.style.font;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.font.TextAttribute;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.logging.Level;

import javax.swing.text.StyleContext;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.EFontFamily;
import org.optimizationBenchmarking.utils.graphics.EFontType;
import org.optimizationBenchmarking.utils.graphics.FontProperties;
import org.optimizationBenchmarking.utils.graphics.style.PaletteElementBuilder;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.parsers.LooseBooleanParser;
import org.optimizationBenchmarking.utils.parsers.LooseIntParser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.text.transformations.NormalCharTransformer;

/**
 * A builder for font styles. This builder can resolve and load a instance
 * of
 * {@code org.optimizationBenchmarking.utils.graphics.style.font.FontStyle}
 * based a set of face choices and attributes. It will try to build fonts
 * which support an as-wide-as-possible range of the Unicode code table,
 * {@linkplain #__makeFont(Font) creating a composite font} if necessary.
 */
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
  private LinkedHashSet<_FaceChoice> m_faceChoices;

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
  protected FontStyleBuilder(final FontPaletteBuilder owner,
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
   * @param name
   *          the face choice name
   * @return {@code true} if the face choice was new and addable,
   *         {@code false} if it was already stored OR if it was an empty
   *         or {@code null} string
   */
  public final boolean addFaceChoice(final String name) {
    return this.addFaceChoice(name, null, null);
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
   * @param name
   *          the face choice name
   * @param resource
   *          a resource from which the face choice may be loaded
   * @param type
   *          the font type
   * @return {@code true} if the face choice was new and addable,
   *         {@code false} if it was already stored OR if it was an empty
   *         or {@code null} string
   */
  public synchronized final boolean addFaceChoice(final String name,
      final String resource, final EFontType type) {

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return this.m_faceChoices.add(new _FaceChoice(this.normalize(name),
        resource, type));
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
   * Check whether a given font name is allowed. Only fonts with actual
   * font families are allowed, logical font families are forbidden. They
   * are only the last resort during the {@linkplain #compile() compilation
   * process}.
   *
   * @param name
   *          the name
   * @return {@code true} if the font name is allowed, {@code false}
   *         otherwise
   */
  private static final boolean __isAllowed(final String name) {
    final int index;

    if (name == null) {
      return false;
    }

    index = name.indexOf('.');

    switch ((index >= 0) ? name.substring(0, index) : name) {
      case Font.DIALOG:
      case Font.DIALOG_INPUT:
      case Font.MONOSPACED:
      case Font.SANS_SERIF:
      case Font.SERIF: {
        return false;
      }

      default: {
        return true;
      }
    }
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
    final int nameLength;
    String fontName, shorter;
    int fontNameLength;

    nameLength = name.length();
    fontName = font.getFontName(Locale.US);
    if (FontStyleBuilder.__isAllowed(fontName)) {
      fontNameLength = fontName.length();
      if (fontNameLength >= nameLength) {

        shorter = ((fontNameLength > nameLength)//
        ? fontName.substring(0, nameLength)//
            : fontName);
        if (shorter.equalsIgnoreCase(name)) {
          return true;
        }
      }

      fontName = font.getFontName();
      if (FontStyleBuilder.__isAllowed(fontName)) {
        fontNameLength = fontName.length();
        if (fontNameLength >= nameLength) {
          shorter = ((fontNameLength > nameLength)//
          ? fontName.substring(0, nameLength)//
              : fontName);
          if (shorter.equalsIgnoreCase(name)) {
            return true;
          }
        }

        fontName = font.getName();
        if (FontStyleBuilder.__isAllowed(fontName)) {
          fontNameLength = fontName.length();
          if (fontNameLength >= nameLength) {
            shorter = ((fontNameLength > nameLength)//
            ? fontName.substring(0, nameLength)//
                : fontName);
            if (shorter.equalsIgnoreCase(name)) {
              return true;
            }
          }
        }
      }
    }

    return false;
  }

  /**
   * Add a face choice to the set of choices
   *
   * @param choices
   *          the set of face choice
   * @param choice
   *          the choice
   */
  private static final void __addFaceChoice(
      final LinkedHashSet<String> choices, final String choice) {
    final String name;
    name = TextUtils.prepare(choice);
    if (FontStyleBuilder.__isAllowed(name)) {
      choices.add(name);
    }
  }

  /**
   * Build an obtain the font style. During this process, we try to resolve
   * the logical aspects of the font style to a physical one, i.e., a font
   * family to a font. The list of face choices is subsequently updated to
   * represent the actual physical choice.
   *
   * @return the font style
   */
  @SuppressWarnings({ "rawtypes", "unchecked", "resource" })
  @Override
  protected final FontStyle compile() {
    final ArrayList<Font> lst;
    final LinkedHashSet<_FaceChoice> faceChoices;
    final LinkedHashSet<String> choices;
    EFontType type;
    InputStream is;
    int style, goalStyle, i, mask;
    String used, resource, chosenName;
    Font font, derivedFont;
    EFontFamily fam1, fam2;
    HashMap<TextAttribute, Object> map;
    MemoryTextOutput idb;
    FontProperties prop;
    boolean set;

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
    font = null;
    used = null;
    faceChoices = this.m_faceChoices;
    this.m_faceChoices = null;
    resource = null;
    chosenName = null;
    type = null;
    finder: {
      for (final _FaceChoice faceChoice : faceChoices) {
        try {
          // some fonts are already bold or italic by default
          prop = FontProperties.getFontProperties(faceChoice.m_name);
          mask = ((prop != null) ? prop.getStyle() : Font.PLAIN);
          // so we do not need to set those features
          goalStyle = (style & (~mask));
          font = new Font(faceChoice.m_name, goalStyle, this.m_size);
          if (FontStyleBuilder.__isAllowed(font.getFamily())) {
            // If the font is physical and not logical, we check if it
            // matches.
            if (FontStyleBuilder.__matches(font, faceChoice.m_name)) {
              chosenName = faceChoice.m_name;
              break finder;
            }
          } else {
            // The font was purely logical, so we could not load it from
            // the system.
            // We check whether a resource for loading the font was
            // specified and if so, try loading it from there.
            if (faceChoice.m_resource != null) {
              is = ReflectionUtils.getResourceAsStream(//
                  faceChoice.m_resource);
              if (is != null) {
                try {
                  font = Font.createFont(
                      faceChoice.m_type.getJavaFontType(), is);
                  if (font != null) {
                    try {
                      GraphicsEnvironment.getLocalGraphicsEnvironment()
                          .registerFont(font);
                    } catch (final Throwable ignore) {
                      ErrorUtils.logError(Configuration.getGlobalLogger(),
                          "Ignorable error during the attempt to register font '"//$NON-NLS-1$
                              + font + //
                              "' with the local graphics environment.", //$NON-NLS-1$
                          ignore, false, RethrowMode.DONT_RETHROW);
                    }
                    if (FontStyleBuilder
                        .__matches(font, faceChoice.m_name)) {
                      chosenName = faceChoice.m_name;
                      resource = faceChoice.m_resource;
                      type = faceChoice.m_type;
                      break finder;
                    }
                  }
                } finally {
                  is.close();
                }
              }
            }
          }
        } catch (final Throwable error) {
          ErrorUtils
              .logError(
                  Configuration.getGlobalLogger(),
                  "Strange but ignorable error during the creation of a font style detected.", //$NON-NLS-1$
                  error, true, RethrowMode.DONT_RETHROW);
        }
      }

      mask = Font.PLAIN;
      goalStyle = style;
      font = new Font(this.m_family.getFontFamilyName(), style,
          this.m_size);
    }

    // OK, by now we have obtained a font

    lst = new ArrayList<>();
    lst.add(font);

    // now we try to derive a font fitting exactly to the specifications
    if (font.getSize() != this.m_size) {
      // adapt size
      derivedFont = font.deriveFont(this.m_size);
      if (derivedFont != font) {
        font = derivedFont;
        lst.add(font);
      }
    }

    // adapt style
    if (goalStyle != font.getStyle()) {
      derivedFont = font.deriveFont(goalStyle);
      if (derivedFont != font) {
        font = derivedFont;
        lst.add(font);
      }
    }

    // adapt rest: underline, kerning, ligantures
    map = ((HashMap) (FontStyleBuilder.FONT_ATTRIBUTES.clone()));

    if (this.m_size != font.getSize()) {
      map.put(TextAttribute.SIZE, Integer.valueOf(this.m_size));
    }

    if (this.m_underlined) {
      map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    } else {
      map.put(TextAttribute.UNDERLINE, Integer.valueOf(-1));
    }

    set = ((goalStyle & Font.BOLD) != 0);
    if (set != font.isBold()) {
      if (set) {
        map.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
      } else {
        map.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR);
      }
    }

    set = ((goalStyle & Font.ITALIC) != 0);
    if (set != font.isItalic()) {
      if (set) {
        map.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
      } else {
        map.put(TextAttribute.POSTURE, TextAttribute.POSTURE_REGULAR);
      }
    }

    derivedFont = font.deriveFont(map);
    if ((derivedFont != font) && (!(derivedFont.equals(font)))) {
      font = derivedFont;
      lst.add(font);
    }

    // now let us update the choices list to represent the setting
    choices = new LinkedHashSet<>(faceChoices.size() + 4
        + (6 * lst.size()));
    choices.add(chosenName);
    for (i = lst.size(); (--i) >= 0;) {
      derivedFont = lst.get(i);
      FontStyleBuilder.__addFaceChoice(choices,
          derivedFont.getFontName(Locale.US));
      FontStyleBuilder.__addFaceChoice(choices, derivedFont.getFontName());
      FontStyleBuilder.__addFaceChoice(choices, derivedFont.getName());
      FontStyleBuilder.__addFaceChoice(choices, derivedFont.getPSName());
      FontStyleBuilder.__addFaceChoice(choices,
          derivedFont.getFamily(Locale.US));
      FontStyleBuilder.__addFaceChoice(choices, derivedFont.getFamily());
    }
    FontStyleBuilder.__addFaceChoice(choices, used);
    for (final _FaceChoice faceChoice : faceChoices) {
      choices.add(faceChoice.m_name);
    }

    fam1 = this.m_family;
    fam2 = null;
    if ((fam1 == null) || (fam1 == EFontFamily.DIALOG)
        || (fam1 == EFontFamily.DIALOG_INPUT)) {
      prop = FontProperties.getFontProperties(font, true);
      if (prop != null) {
        fam2 = prop.getFamily();
      }
    }

    FontStyleBuilder.__addFaceChoice(choices, fam1.getFontFamilyName());
    if ((fam2 != fam1) && (fam2 != null)) {
      FontStyleBuilder.__addFaceChoice(choices, fam2.getFontFamilyName());
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
      this.m_id = NormalCharTransformer.getInstance().transform(
          idb.toString());
    }

    choices.remove(null);// just to be sure
    choices.remove(""); //just to be sure //$NON-NLS-1$

    // ok, now we have everything
    return new FontStyle(((fam2 != null) ? fam2 : fam1), this.m_size,
        this.m_italic, this.m_bold, this.m_underlined,
        this.__makeFont(font), new ArrayListView<>(
            choices.toArray(new String[choices.size()])), resource, type,
        this.m_id);
  }

  /**
   * Some of the standard fonts for publications only support a weird
   * subset of the Unicode character set: The cmr fonts, for instance do
   * not have glyphs for "<", the "less-or-equal"-symbol, "²", or "{" and
   * "}". These symbols will be rendered as empty rectangles, which kind of
   * messes up the experience. With the method call below, we obtain a
   * composite font where these missing glyphs are replaced with glyphs of
   * another font.
   *
   * @param font
   *          the original font
   * @return the new font
   */
  @SuppressWarnings("resource")
  private final Font __makeFont(final Font font) {
    final StyleContext context;
    HierarchicalFSM ownerFSM;
    Font use;

    ownerFSM = this.getOwner();
    if (ownerFSM instanceof FontPaletteBuilder) {
      context = ((FontPaletteBuilder) ownerFSM).m_context;
    } else {
      context = StyleContext.getDefaultStyleContext();
    }

    try {
      use = context.getFont(font.getFamily(), font.getStyle(),
          font.getSize());
      if (use != null) {
        return use;
      }
    } catch (final Throwable error) {
      ErrorUtils.logError(Configuration.getGlobalLogger(), Level.WARNING,
          ("Error while trying build composite font based on font " //$NON-NLS-1$
          + font), error, false, RethrowMode.DONT_RETHROW);
    }
    return font;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void fromStrings(final Iterator<String> strings) {
    String t;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    this.setFontFamily(EFontFamily.valueOf(TextUtils.toUpperCase(//
        strings.next())));

    t = strings.next();
    this.setBold((t != null)
        && LooseBooleanParser.INSTANCE.parseBoolean(t));

    t = strings.next();
    this.setItalic((t != null)
        && LooseBooleanParser.INSTANCE.parseBoolean(t));

    t = strings.next();
    this.setUnderlined((t != null)
        && LooseBooleanParser.INSTANCE.parseBoolean(t));

    this.setSize(LooseIntParser.INSTANCE.parseInt(strings.next()));

    while (strings.hasNext()) {
      this.addFaceChoice(strings.next());
    }
  }

}
