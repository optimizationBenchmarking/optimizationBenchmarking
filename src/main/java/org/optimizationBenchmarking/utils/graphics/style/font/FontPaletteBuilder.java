package org.optimizationBenchmarking.utils.graphics.style.font;

import java.util.ArrayList;

import org.optimizationBenchmarking.utils.graphics.style.PaletteBuilder;
import org.optimizationBenchmarking.utils.graphics.style.PaletteElementBuilder;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A builder for font palettes.
 */
public class FontPaletteBuilder extends
PaletteBuilder<FontStyle, FontPalette> {

  /** did we set the default font */
  private static final int FLAG_HAS_DEFAULT = (PaletteBuilder.FLAG_HAS_ELEMENTS << 1);
  /** did we set the emph font */
  private static final int FLAG_HAS_EMPH = (FontPaletteBuilder.FLAG_HAS_DEFAULT << 1);
  /** did we set the code font */
  private static final int FLAG_HAS_CODE = (FontPaletteBuilder.FLAG_HAS_EMPH << 1);

  /** the default font style */
  private FontStyle m_default;
  /** the emph font style */
  private FontStyle m_emph;
  /** the code font style */
  private FontStyle m_code;
  /** the font counter */
  private int m_count;

  /** create */
  public FontPaletteBuilder() {
    super(null);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_HAS_DEFAULT: {
        append.append("hasDefaultFontStyle"); //$NON-NLS-1$
        return;
      }
      case FLAG_HAS_EMPH: {
        append.append("hasEmphFontStyle"); //$NON-NLS-1$
        return;
      }
      case FLAG_HAS_CODE: {
        append.append("hasCodeFontStyle"); //$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /**
   * Set the default font style
   *
   * @param style
   *          the default font style
   */
  public synchronized final void setDefaultFont(final FontStyle style) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.fsmFlagsAssertFalse(PaletteBuilder.FLAG_HAS_ELEMENTS);
    FontPalette._checkDefaultFont(style);
    this.m_default = style;
    this.fsmFlagsSet(FontPaletteBuilder.FLAG_HAS_DEFAULT);
  }

  /**
   * Set the emph style
   *
   * @param style
   *          the emph font style
   */
  public synchronized final void setEmphFont(final FontStyle style) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.fsmFlagsAssertFalse(PaletteBuilder.FLAG_HAS_ELEMENTS);
    FontPalette._checkEmphFont(style);
    this.m_emph = style;
    this.fsmFlagsSet(FontPaletteBuilder.FLAG_HAS_EMPH);
  }

  /**
   * Set the code style
   *
   * @param style
   *          the code font style
   */
  public synchronized final void setCodeFont(final FontStyle style) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.fsmFlagsAssertFalse(PaletteBuilder.FLAG_HAS_ELEMENTS);
    FontPalette._checkCodeFont(style);
    this.m_code = style;
    this.fsmFlagsSet(FontPaletteBuilder.FLAG_HAS_CODE);
  }

  /**
   * create a builder
   *
   * @param id
   *          the id
   * @param idStr
   *          the id
   * @return the builder
   */
  private final FontStyleBuilder __build(final int id, final String idStr) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new FontStyleBuilder(this, id, idStr);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final FontStyleBuilder add() {
    return ((FontStyleBuilder) (super.add()));
  }

  /**
   * Set the default font style
   *
   * @return the builder for the default font style
   */
  public synchronized final FontStyleBuilder setDefaultFont() {
    this.fsmFlagsAssertFalse(PaletteBuilder.FLAG_HAS_ELEMENTS);
    return this.__build(0, FontPaletteXML.ELEMENT_DEFAULT_FONT);
  }

  /**
   * Set the emphasize font style
   *
   * @return the builder for the emphasize font style
   */
  public synchronized final FontStyleBuilder setEmphFont() {
    this.fsmFlagsAssertFalse(PaletteBuilder.FLAG_HAS_ELEMENTS);
    return this.__build(1, FontPaletteXML.ELEMENT_EMPHASIZED_FONT);
  }

  /**
   * Set the code font style
   *
   * @return the builder for the code font style
   */
  public synchronized final FontStyleBuilder setCodeFont() {
    this.fsmFlagsAssertFalse(PaletteBuilder.FLAG_HAS_ELEMENTS);
    return this.__build(2, FontPaletteXML.ELEMENT_CODE_FONT);
  }

  /** {@inheritDoc} */
  @Override
  protected final FontStyleBuilder createElementBuilder() {
    this.fsmFlagsAssertTrue(FontPaletteBuilder.FLAG_HAS_DEFAULT
        | FontPaletteBuilder.FLAG_HAS_EMPH
        | FontPaletteBuilder.FLAG_HAS_CODE);
    return this.__build(3, (FontPaletteXML.ELEMENT_FONT + //
        Integer.toHexString(this.m_count++)));
  }

  /**
   * Create the font palette
   *
   * @param def
   *          the default
   * @param emph
   *          the emphasize font style
   * @param code
   *          the code font style
   * @param data
   *          the data
   * @return the palette
   */
  protected FontPalette createPalette(final FontStyle def,
      final FontStyle emph, final FontStyle code, final FontStyle[] data) {
    this.fsmFlagsAssertTrue(PaletteBuilder.FLAG_HAS_ELEMENTS
        | FontPaletteBuilder.FLAG_HAS_DEFAULT
        | FontPaletteBuilder.FLAG_HAS_EMPH
        | FontPaletteBuilder.FLAG_HAS_CODE);
    return new FontPalette(def, emph, code, data);
  }

  /** {@inheritDoc} */
  @Override
  protected final FontPalette createPalette(final ArrayList<FontStyle> data) {
    return this.createPalette(this.m_default, this.m_emph, this.m_code,
        data.toArray(new FontStyle[data.size()]));
  }

  /** {@inheritDoc} */
  @Override
  protected final void processElementBuilder(
      final PaletteElementBuilder<FontStyle> child) {
    final FontStyleBuilder ssb;

    ssb = ((FontStyleBuilder) child);
    switch (ssb.m_choice) {
      case 0: {
        this.setDefaultFont(ssb.getResult());
        break;
      }
      case 1: {
        this.setEmphFont(ssb.getResult());
        break;
      }
      case 2: {
        this.setCodeFont(ssb.getResult());
        break;
      }
      case 3: {
        this.add(ssb.getResult());
        break;
      }
      default: {
        throw new IllegalArgumentException(child.toString());
      }
    }
  }
}
