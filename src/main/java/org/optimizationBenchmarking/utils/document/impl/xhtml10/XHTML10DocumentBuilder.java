package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.impl.abstr.Document;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentBuilder;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.PageDimension;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.style.PaletteInputDriver;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteBuilder;

/**
 * The XHTML 1.0 document builder
 */
public final class XHTML10DocumentBuilder extends DocumentBuilder {

  /** the screen size */
  public static final String PARAM_SCREEN_SIZE = "screenSize"; //$NON-NLS-1$

  /** the screen size */
  private PageDimension m_size;

  /** the font palette */
  private FontPalette m_fonts;

  /**
   * the XHTML 1.0 document builder
   * 
   * @param driver
   *          the driver to use
   */
  XHTML10DocumentBuilder(final XHTML10Driver driver) {
    super(driver);
  }

  /** {@inheritDoc} */
  @Override
  public XHTML10DocumentBuilder configure(final Configuration config) {
    final EScreenSize newSize;

    super.configure(config);

    newSize = config.getInstance(PARAM_SCREEN_SIZE, EScreenSize.class,
        null);
    if (newSize != null) {
      this.setScreenSize(newSize);
    }

    return this;
  }

  /**
   * Set the screen size for the document builder
   * 
   * @param size
   *          the screen size
   * @return this document builder
   */
  public final XHTML10DocumentBuilder setScreenSize(
      final PhysicalDimension size) {
    this.m_size = _pageDimension(size);
    return this;
  }

  /**
   * make a page dimension
   * 
   * @param size
   *          the physical size
   * @return the page dimension
   */
  static final PageDimension _pageDimension(final PhysicalDimension size) {
    _checkScreenSize(size);
    if (size instanceof PageDimension) {
      return ((PageDimension) size);
    }
    return new PageDimension((0.91d * size.getWidth()),//
        (0.91d * size.getHeight()), size.getUnit());
  }

  /**
   * Set the screen size of this builder
   * 
   * @param size
   *          the screen size
   * @return this builder
   */
  public final XHTML10DocumentBuilder setScreenSize(final EScreenSize size) {
    _checkScreenSize(size);
    this.m_size = _pageDimension(size.getPageSize());
    return this;
  }

  /**
   * Get the screen size
   * 
   * @return the screen size
   */
  public final PageDimension getScreenSize() {
    final PageDimension dim;
    dim = this.m_size;
    return ((dim != null) ? dim : __XHTML10DefaultScreenSize.INSTANCE);
  }

  /**
   * Set the font palette to be used
   * 
   * @param fonts
   *          the font palette to be used
   * @return this builder
   */
  public final XHTML10DocumentBuilder setFontPalette(
      final FontPalette fonts) {
    _checkFontPalette(fonts);
    this.m_fonts = fonts;
    return this;
  }

  /**
   * Get the font palette of this document builder
   * 
   * @return the font palette of this document builder
   */
  public final FontPalette getFontPalette() {
    final FontPalette fonts;
    fonts = this.m_fonts;
    return ((fonts != null) ? fonts
        : __XHTML10DefaultFontPaletteLoader.INSTANCE);
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

  /**
   * Set the size of the screen
   * 
   * @param size
   *          the size of the screen
   */
  static final void _checkScreenSize(final EScreenSize size) {
    if (size == null) {
      throw new IllegalArgumentException("Screen size cannot be null."); //$NON-NLS-1$
    }
  }

  /** the default screen size */
  private static final class __XHTML10DefaultScreenSize {

    /** the default page dimension */
    static final PageDimension INSTANCE = _pageDimension(EScreenSize.DEFAULT
        .getPageSize());
  }

  /** the default font palette */
  private static final class __XHTML10DefaultFontPaletteLoader {

    /** the internal font palette */
    static final FontPalette INSTANCE;

    static {
      FontPalette p;

      p = null;
      try (final FontPaletteBuilder tb = new FontPaletteBuilder()) {
        PaletteInputDriver
            .getInstance()
            .use()
            .setDestination(tb)
            .addResource(XHTML10Driver.class, "xhtml10.fontPalette").create().call(); //$NON-NLS-1$
        p = tb.getResult();
      } catch (final Throwable tt) {
        ErrorUtils.throwAsRuntimeException(tt);
      }

      INSTANCE = p;
    }

  }

  /** {@inheritDoc} */
  @Override
  protected final StyleSet createStyleSet() {
    return new StyleSet(this.getFontPalette(), this.getColorModel()
        .getDefaultPalette(), this.getGraphicDriver().getStrokePalette());
  }

  /** {@inheritDoc} */
  @Override
  protected final Document doCreateDocument() {
    return new _XHTML10Document(this);
  }
}
