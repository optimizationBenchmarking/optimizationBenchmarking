package org.optimizationBenchmarking.utils.graphics.style.font;

import java.awt.Font;
import java.awt.Graphics2D;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.graphics.style.EFontFamily;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.StyleApplication;
import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A style is something which can be applied to font or a graphics context.
 * They are constructed using {@link FontStyleBuilder}s.
 * <p>
 * A font style has a <em>logical</em> and a <em>physical</em> aspect. From
 * the logical point of view, it is a combination of attributes such as the
 * {@link #getFamily() font family}, and whether it is {@link #isBold()
 * bold}, {@link #isItalic() italic}, or {@link #isUnderlined()}, as well
 * as its {@link #getSize()}. An image or document should never contain two
 * fonts of the same {@link #getFamily() family}. Generally, no two
 * {@link FontStyle font styles} should be equivalent from the logical
 * perspective.
 * </p>
 * <p>
 * The physical perspective is the mapping of the logical perspective to a
 * concrete {@link java.awt.Font font}. For this purpose, there may be
 * different {@link #getFaceChoices() choices}. Each {@link FontStyle font
 * style} also has exactly one such physical mapping, which depends on the
 * fonts available on the underlying computing system.
 * </p>
 * <p>
 * {@link FontStyle Font styles} allow to keep fonts between different
 * documents and image systems identical, i.e., help to ensure that the
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#EPS
 * eps} figures in a LaTeX document apply the exactly same fonts as the
 * main font.
 * </p>
 */
public final class FontStyle extends HashObject implements IStyle {

  /** the font family */
  private final EFontFamily m_family;

  /** is the font italic ? */
  private final boolean m_italic;

  /** is the font bold? */
  private final boolean m_bold;

  /** is the fond underlined? */
  private final boolean m_underlined;

  /**
   * the font size in
   * {@link org.optimizationBenchmarking.utils.math.units.ELength#POINT pt}
   */
  private final int m_size;

  /** the resolved font */
  private final Font m_font;

  /** the font name choices */
  private final ArrayListView<String> m_faceChoices;

  /** the palette */
  FontPalette m_palette;

  /**
   * Create a new font style
   * 
   * @param family
   *          the font family
   * @param size
   *          the font size
   * @param isItalic
   *          is the font italic?
   * @param isBold
   *          is the font bold?
   * @param isUnderlined
   *          is the font underlined?
   * @param font
   *          the font
   * @param faceChoices
   *          the face choices
   */
  FontStyle(final EFontFamily family, final int size,
      final boolean isItalic, final boolean isBold,
      final boolean isUnderlined, final Font font,
      final ArrayListView<String> faceChoices) {
    super();

    FontStyleBuilder._checkFontFamily(family);
    FontStyleBuilder._checkSize(size);

    if (font == null) {
      throw new IllegalArgumentException(//
          "Resolved font must not be null."); //$NON-NLS-1$
    }

    if ((faceChoices == null) || (faceChoices.isEmpty())) {
      throw new IllegalArgumentException(//
          "Font face choices must not be null or empty."); //$NON-NLS-1$
    }

    this.m_family = family;
    this.m_italic = isItalic;// font.isItalic();
    this.m_bold = isBold;// font.isBold();
    this.m_underlined = isUnderlined;// GraphicUtils.isUnderlined(font);
    this.m_size = size;
    this.m_font = font;
    this.m_faceChoices = faceChoices;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(HashUtils.combineHashes(//
        HashUtils.combineHashes(HashUtils.hashCode(this.m_family),
            HashUtils.hashCode(this.m_italic)),//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_bold),//
            HashUtils.hashCode(this.m_underlined))),//
        HashUtils.hashCode(this.m_size));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final FontStyle t;

    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o instanceof FontStyle) {
      t = ((FontStyle) o);
      return ((this.m_family == t.m_family) && //
          (this.m_italic == t.m_italic) && //
          (this.m_bold == t.m_bold) && //
          (this.m_underlined == t.m_underlined) && //
      (this.m_size == t.m_size));
    }
    return false;
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
   * Is this font italic?
   * 
   * @return {@code true} for italic fonts, {@code false} for normal ones
   */
  public final boolean isItalic() {
    return this.m_italic;
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
   * Is this font underlined?
   * 
   * @return {@code true} for underlined fonts, {@code false} for normal
   *         ones
   */
  public final boolean isUnderlined() {
    return this.m_underlined;
  }

  /**
   * Get the size of this font
   * 
   * @return the size of this font
   */
  public final int getSize() {
    return this.m_size;
  }

  /**
   * Get a concrete font which is a best fit to this font definition. This
   * font may differ from the specification of the style and may be a
   * platform-based closest match.
   * 
   * @return the font belonging to this font style
   */
  public final Font getFont() {
    return this.m_font;
  }

  /**
   * Get the list of font name or face choices. The choices are ordered by
   * priority, i.e., the first list element represents the
   * {@link #getFont() physical font} of the style, the following elements
   * may represent aliases, and the last element may represent the
   * {@link #getFamily() font family}. The aim is to provide a list of
   * fonts that an underlying physical system may try to load in order to
   * create the same physical appearance. The earlier the element in the
   * list which could finally be loaded, the more similar the appearance
   * should be.
   * 
   * @return the list of font name or face choices
   */
  public final ArrayListView<String> getFaceChoices() {
    return this.m_faceChoices;
  }

  /** {@inheritDoc} */
  @Override
  public final StyleApplication applyTo(final Graphics2D g) {
    return new _FontApplication(g, this.m_font);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean appendDescription(final ETextCase textCase,
      final ITextOutput dest, final boolean omitDefaults) {
    ETextCase t;
    boolean have, x, haveSpecial;
    int s;
    FontStyle def;
    EFontFamily fam;

    t = ((textCase == null) ? ETextCase.IN_SENTENCE : textCase);

    if (this.m_palette != null) {
      def = this.m_palette.getDefaultFont();
      if (omitDefaults && (def == this)) {
        return false;
      }
    } else {
      def = null;
    }

    have = false;
    haveSpecial = false;

    if (def != null) {
      s = (this.m_size - def.m_size);
      if (s != 0) {
        t.appendWord(((s < 0) ? "small" : "large"), dest); //$NON-NLS-1$ //$NON-NLS-2$
        have = true;
      }
    } else {
      dest.append(this.m_size);
      dest.append('p');
      dest.append('t');
      have = true;
    }

    x = this.m_bold;
    if (x && ((def == null) || (x != def.m_bold) || (!omitDefaults))) {
      haveSpecial = true;
      if (have) {
        dest.append(' ');
      }
      t.appendWord("bold", dest); //$NON-NLS-1$
      t = t.nextCase();
      have = true;
    }

    x = this.m_italic;
    if (x && ((def == null) || (x != def.m_italic) || (!omitDefaults))) {
      if (have) {
        dest.append(haveSpecial ? '-' : ' ');
      }
      haveSpecial = true;
      t.appendWord("italic", dest); //$NON-NLS-1$
      t = t.nextCase();
      have = true;
    }

    x = this.m_underlined;
    if (x && ((def == null) || (x != def.m_underlined) || (!omitDefaults))) {
      if (have) {
        if (haveSpecial) {
          t.appendWord(" and ", dest);//$NON-NLS-1$
        } else {
          dest.append(' ');
        }
      }
      haveSpecial = true;
      t.appendWord("underlined", dest); //$NON-NLS-1$
      t = t.nextCase();
      have = true;
    }

    fam = this.m_family;
    family: {
      if ((def == null) || (fam != def.m_family) || (!omitDefaults)) {
        if (have) {
          dest.append(' ');
        }
        switch (fam) {
          case SANS_SERIF: {
            t.appendWord("sans-serif", dest);//$NON-NLS-1$
            break;
          }
          case SERIF: {
            t.appendWord("serif", dest);//$NON-NLS-1$
            break;
          }
          case MONOSPACED: {
            t.appendWord("monospaced", dest); //$NON-NLS-1$
            break;
          }
          case DIALOG: {
            t.appendWord("dialog", dest); //$NON-NLS-1$
            break;
          }
          case DIALOG_INPUT: {
            t = t.appendWord("dialog", dest); //$NON-NLS-1$
            dest.append(' ');
            t.appendWord("input", dest); //$NON-NLS-1$
            break;
          }
          default: {
            break family;
          }
        }
        have = true;
      }
    }

    return have;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final MemoryTextOutput mo;

    mo = new MemoryTextOutput();
    this.appendDescription(ETextCase.AT_SENTENCE_START, mo, false);
    return mo.toString();
  }
}
