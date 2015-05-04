package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfigurationBuilder;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.PageDimension;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfiguration;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A configuration builder for XHTML 1.0 documents */
public class XHTML10ConfigurationBuilder extends
    DocumentConfigurationBuilder {

  /** the screen size */
  public static final String PARAM_SCREEN_SIZE = "screenSize"; //$NON-NLS-1$

  /** the screen size */
  private PageDimension m_size;

  /** the font palette */
  private FontPalette m_fonts;

  /** create the XHTML 1.0 document configuration builder */
  public XHTML10ConfigurationBuilder() {
    this(null);
  }

  /**
   * create the XHTML 1.0 document configuration builder
   *
   * @param driver
   *          the driver to use
   */
  protected XHTML10ConfigurationBuilder(final XHTML10Driver driver) {
    super((driver != null) ? driver : XHTML10Driver.getInstance());
  }

  /** {@inheritDoc} */
  @Override
  public XHTML10Configuration immutable() {
    return new XHTML10Configuration(this);
  }

  /** {@inheritDoc} */
  @Override
  protected void configureWithoutDriver(final Configuration config) {
    final EScreenSize newSize;

    super.configureWithoutDriver(config);

    newSize = config.getInstance(
        XHTML10ConfigurationBuilder.PARAM_SCREEN_SIZE, EScreenSize.class,
        null);
    if (newSize != null) {
      this.setScreenSize(newSize);
    }
  }

  /**
   * Set the screen size for the document configuration builder
   *
   * @param size
   *          the screen size
   */
  public final void setScreenSize(final PhysicalDimension size) {
    this.m_size = XHTML10ConfigurationBuilder._pageDimension(size);
  }

  /**
   * make a page dimension
   *
   * @param size
   *          the physical size
   * @return the page dimension
   */
  static final PageDimension _pageDimension(final PhysicalDimension size) {

    XHTML10Configuration._checkScreenSize(size);
    if (size instanceof PageDimension) {
      return ((PageDimension) size);
    }

    return new PageDimension(XHTML10ConfigurationBuilder.__trim(size
        .getWidth()),//
        XHTML10ConfigurationBuilder.__trim(size.getHeight()),
        size.getUnit());
  }

  /**
   * trim a dimension
   *
   * @param dim
   *          the dimension
   * @return the trimmed result
   */
  private static final double __trim(final double dim) {
    final double res;
    final long round;

    res = (0.91d * dim);
    round = Math.round(100d * res);
    if (round > 200) {
      return (round * 0.01d);
    }
    return res;
  }

  /**
   * Set the screen size of this builder
   *
   * @param size
   *          the screen size
   */
  public final void setScreenSize(final EScreenSize size) {
    XHTML10ConfigurationBuilder._checkScreenSize(size);
    this.m_size = XHTML10ConfigurationBuilder._pageDimension(size
        .getPageSize());
  }

  /**
   * Get the screen size
   *
   * @return the screen size
   */
  public final PageDimension getScreenSize() {
    final PageDimension dim;
    dim = this.m_size;
    return ((dim != null) ? dim : XHTML10Driver.defaultPageDimension());
  }

  /**
   * Set the font palette to be used
   *
   * @param fonts
   *          the font palette to be used
   */
  public final void setFontPalette(final FontPalette fonts) {
    XHTML10Configuration._checkFontPalette(fonts);
    this.m_fonts = fonts;
  }

  /**
   * Get the font palette of this document builder
   *
   * @return the font palette of this document builder
   */
  public final FontPalette getFontPalette() {
    final FontPalette fonts;
    fonts = this.m_fonts;
    return ((fonts != null) ? fonts : XHTML10DefaultFontPalette
        .getInstance());
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
        return (EComparison.equals(this.m_fonts, config.getFontPalette()) && EComparison
            .equals(this.m_size, config.getScreenSize()));
      }
    } else {
      if (o instanceof XHTML10ConfigurationBuilder) {
        if (super.equals(o)) {
          builder = ((XHTML10ConfigurationBuilder) o);
          return (EComparison.equals(this.m_fonts, builder.m_fonts) && EComparison
              .equals(this.m_size, builder.m_size));
        }
      }
    }

    return false;
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
      XHTML10Configuration._font(this.m_fonts.getDefaultFont(), textOut);
      textOut.append('@');
    }
  }

  /** {@inheritDoc} */
  @Override
  public void assign(final GraphicConfiguration copyFrom) {
    final XHTML10ConfigurationBuilder builder;
    final XHTML10Configuration config;
    final PageDimension size;
    final FontPalette fonts;

    if (copyFrom != null) {
      if (copyFrom instanceof XHTML10ConfigurationBuilder) {
        builder = ((XHTML10ConfigurationBuilder) copyFrom);
        if ((size = builder.m_size) != null) {
          this.setScreenSize(size);
        }
        if ((fonts = builder.m_fonts) != null) {
          this.setFontPalette(fonts);
        }
      } else {
        if (copyFrom instanceof XHTML10Configuration) {
          config = ((XHTML10Configuration) copyFrom);
          if ((size = config.getScreenSize()) != null) {
            this.setScreenSize(size);
          }
          if ((fonts = config.getFontPalette()) != null) {
            this.setFontPalette(fonts);
          }
        }
      }

      super.assign(copyFrom);
    }
  }
}
