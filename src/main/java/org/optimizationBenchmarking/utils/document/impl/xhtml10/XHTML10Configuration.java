package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfiguration;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBuilder;
import org.optimizationBenchmarking.utils.graphics.PageDimension;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontStyle;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A configuration for XHTML 1.0 documents */
public class XHTML10Configuration extends DocumentConfiguration {

  /** the screen size */
  private final PageDimension m_size;

  /** the font palette */
  private final FontPalette m_fonts;

  /**
   * create the XHTML 1.0 document configuration
   * 
   * @param builder
   *          the builder
   */
  public XHTML10Configuration(final XHTML10ConfigurationBuilder builder) {
    super(builder);

    XHTML10Configuration._checkScreenSize(this.m_size = builder
        .getScreenSize());
    XHTML10Configuration._checkFontPalette(this.m_fonts = builder
        .getFontPalette());
  }

  /**
   * Get the screen size
   * 
   * @return the screen size
   */
  public final PageDimension getScreenSize() {
    return this.m_size;
  }

  /**
   * Get the font palette of this document builder
   * 
   * @return the font palette of this document builder
   */
  public final FontPalette getFontPalette() {
    return this.m_fonts;
  }

  /**
   * check the font palette
   * 
   * @param fonts
   *          the font palette
   */
  static final void _checkFontPalette(final FontPalette fonts) {
    if (fonts == null) {
      throw new IllegalArgumentException("Font palette cannot be null.");//$NON-NLS-1$
    }
    if (fonts.isEmpty()) {
      throw new IllegalArgumentException("Font palette cannot be empty."); //$NON-NLS-1$
    }
  }

  /**
   * Set the size of the screen
   * 
   * @param size
   *          the size of the screen
   */
  static final void _checkScreenSize(final PhysicalDimension size) {
    if (size == null) {
      throw new IllegalArgumentException("Screen size cannot be null."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(
        HashUtils.combineHashes(super.hashCode(),
            HashUtils.hashCode(this.m_fonts)),
        HashUtils.hashCode(this.m_size));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    final XHTML10Configuration config;
    final XHTML10ConfigurationBuilder builder;

    if (o == this) {
      return true;
    }
    if (o instanceof XHTML10Configuration) {
      if (super.equals(o)) {
        config = ((XHTML10Configuration) o);
        return (EComparison.equals(this.m_fonts, config.m_fonts) && EComparison
            .equals(this.m_size, config.m_size));
      }
    } else {
      if (o instanceof XHTML10ConfigurationBuilder) {
        if (super.equals(o)) {
          builder = ((XHTML10ConfigurationBuilder) o);
          return (EComparison.equals(this.m_fonts,
              builder.getFontPalette()) && EComparison.equals(this.m_size,
              builder.getScreenSize()));
        }
      }
    }

    return false;
  }

  /**
   * append the font name
   * 
   * @param style
   *          the style
   * @param textOut
   *          the text out
   */
  static final void _font(final FontStyle style, final ITextOutput textOut) {
    final String fn;

    fn = style.getFont().getFontName();
    textOut.append(fn.replace(' ', '_'));
  }

  /** to string */
  @Override
  public void toText(final ITextOutput textOut) {
    super.toText(textOut);
    if (this.m_size != null) {
      textOut.append('@');
      this.m_size.toText(textOut);
    }
    if (this.m_fonts != null) {
      textOut.append('@');
      XHTML10Configuration._font(this.m_fonts.getDefaultFont(), textOut);
    }
  }

  /**
   * Create a document builder with this object's settings
   * 
   * @return the document builder
   */
  @Override
  public IDocumentBuilder createDocumentBuilder() {
    final XHTML10DocumentBuilder builder;

    builder = ((XHTML10DocumentBuilder) (super.createDocumentBuilder()));

    if (this.m_size != null) {
      builder.setScreenSize(this.m_size);
    }
    if (this.m_fonts != null) {
      builder.setFontPalette(this.m_fonts);
    }

    return builder;
  }

}
