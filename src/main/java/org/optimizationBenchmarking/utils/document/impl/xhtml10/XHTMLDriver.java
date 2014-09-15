package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.impl.abstr.Document;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentDriver;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.PageDimension;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;

/**
 * The driver for xhtml output
 */
public final class XHTMLDriver extends DocumentDriver {

  /** The default XHTML driver */
  public static final XHTMLDriver DEFAULT = new XHTMLDriver(null, null);

  /** the graphic driver */
  private final IGraphicDriver m_graphicDriver;

  /** the screen size */
  private final PageDimension m_size;

  /**
   * Create a new xhtml driver
   * 
   * @param gd
   *          the graphic driver to use
   * @param size
   *          the physical screen size to render for
   */
  private XHTMLDriver(final IGraphicDriver gd, final PhysicalDimension size) {
    super();
    final PhysicalDimension d;

    this.m_graphicDriver = ((gd != null) ? gd : EGraphicFormat.PNG
        .getDefaultDriver());

    d = ((size != null) ? size : EScreenSize.DEFAULT
        .getPhysicalSize(EScreenSize.DEFAULT_SCREEN_DPI));

    this.m_size = ((d instanceof PageDimension) ? ((PageDimension) d)
        : new PageDimension(d));
  }

  @Override
  public Document createDocument(final Path destination) {
    // TODO Auto-generated method stub
    return null;
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

  @Override
  protected StyleSet createStyleSet() {
    // TODO Auto-generated method stub
    return null;
  }

}
