package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentDriver;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.PageDimension;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.PaletteIODriver;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteBuilder;

/**
 * The driver for xhtml output
 */
public final class XHTMLDriver extends DocumentDriver {

  /** the synchronizer */
  private static final Object SYNCH = new Object();

  /** the internal font palette */
  private static FontPalette s_fonts;
  /** The default XHTML driver */
  private static XHTMLDriver s_default;

  /** the graphic driver */
  private final IGraphicDriver m_graphicDriver;

  /** the screen size */
  private final PageDimension m_size;

  /** the font palette */
  private final FontPalette m_fonts;

  /**
   * Get the default XHTML driver
   * 
   * @return the default XHTML driver
   */
  public static final XHTMLDriver getDefaultDriver() {
    synchronized (XHTMLDriver.SYNCH) {
      if (XHTMLDriver.s_default == null) {
        XHTMLDriver.s_default = new XHTMLDriver(null, null, null);
      }
      return XHTMLDriver.s_default;
    }
  }

  /**
   * Create a new xhtml driver
   * 
   * @param gd
   *          the graphic driver to use
   * @param size
   *          the physical screen size to render for
   * @param fonts
   *          the font palette
   */
  public XHTMLDriver(final IGraphicDriver gd,
      final PhysicalDimension size, final FontPalette fonts) {
    super("xhtml"); //$NON-NLS-1$
    final PhysicalDimension d;

    this.m_graphicDriver = ((gd != null) ? gd : EGraphicFormat.PNG
        .getDefaultDriver());

    d = ((size != null) ? size : EScreenSize.DEFAULT
        .getPhysicalSize(EScreenSize.DEFAULT_SCREEN_DPI));

    this.m_size = ((d instanceof PageDimension) ? ((PageDimension) d)
        : new PageDimension(d));

    this.m_fonts = ((fonts == null) ? XHTMLDriver.__defaultFonts() : fonts);
  }

  /**
   * obtain the default font palette
   * 
   * @return the default font palette
   */
  private static final FontPalette __defaultFonts() {
    synchronized (XHTMLDriver.SYNCH) {
      if (XHTMLDriver.s_fonts == null) {
        try (final FontPaletteBuilder tb = new FontPaletteBuilder()) {
          PaletteIODriver.INSTANCE.loadResource(tb, XHTMLDriver.class,
              "xhtml10.font.palette"); //$NON-NLS-1$
          XHTMLDriver.s_fonts = tb.getResult();
        } catch (final Throwable tt) {
          ErrorUtils.throwAsRuntimeException(tt);
        }
      }
      return XHTMLDriver.s_fonts;
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final IGraphicDriver getGraphicDriver() {
    return this.m_graphicDriver;
  }

  /** {@inheritDoc} */
  @Override
  protected final PhysicalDimension getSize(final EFigureSize size) {
    return size.approximateSize(this.m_size);
  }

  /** {@inheritDoc} */
  @Override
  protected final StyleSet createStyleSet() {
    final IGraphicDriver driver;
    driver = this.getGraphicDriver();
    return new StyleSet(this.m_fonts, driver.getColorPalette(),
        driver.getStrokePalette());
  }

}
