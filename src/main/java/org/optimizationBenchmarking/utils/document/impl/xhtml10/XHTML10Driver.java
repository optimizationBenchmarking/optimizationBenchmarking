package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.Code;
import org.optimizationBenchmarking.utils.document.impl.abstr.CodeBody;
import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.Document;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentDriver;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentHeader;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentPart;
import org.optimizationBenchmarking.utils.document.impl.abstr.EMathOperators;
import org.optimizationBenchmarking.utils.document.impl.abstr.Enumeration;
import org.optimizationBenchmarking.utils.document.impl.abstr.Figure;
import org.optimizationBenchmarking.utils.document.impl.abstr.FigureSeries;
import org.optimizationBenchmarking.utils.document.impl.abstr.Itemization;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathFunction;
import org.optimizationBenchmarking.utils.document.impl.abstr.Section;
import org.optimizationBenchmarking.utils.document.impl.abstr.SectionBody;
import org.optimizationBenchmarking.utils.document.impl.abstr.StructuredText;
import org.optimizationBenchmarking.utils.document.impl.abstr.Table;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableBody;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableBodyRow;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooter;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooterRow;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeader;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeaderRow;
import org.optimizationBenchmarking.utils.document.impl.abstr.Text;
import org.optimizationBenchmarking.utils.document.impl.object.IObjectListener;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.PageDimension;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.PaletteIODriver;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteBuilder;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.transformations.XMLCharTransformer;

/**
 * The driver for xhtml output
 */
public final class XHTML10Driver extends DocumentDriver {
  /** the main file */
  public static final String XHTML_MAIN_FILE = "xhtml 1.0 main file"; //$NON-NLS-1$
  /** a css style file */
  public static final String CSS_STYLE_FILE = "css style file"; //$NON-NLS-1$

  /** the attributed tag end */
  static final char[] ATTRIB_TAG_BEGIN_END = { '"', '>' };

  /** the label end */
  static final char[] EMPTY_ATTRIB_TAG_END = { '"', ' ', '/', '>' };

  /** the start of the section div */
  static final char[] SECTION_DIV_BEGIN = { '<', 'd', 'i', 'v', ' ', 'c',
      'l', 'a', 's', 's', '=', '"', 's', 'e', 'c', 't', 'i', 'o', 'n',
      '"', '>' };
  /** the start of the section head div */
  static final char[] SECTION_HEAD_DIV_BEGIN = { '<', 'd', 'i', 'v', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 's', 'e', 'c', 't', 'i', 'o',
      'n', 'H', 'e', 'a', 'd', '"', '>' };
  /** the start of the section body div */
  static final char[] SECTION_BODY_DIV_BEGIN = { '<', 'd', 'i', 'v', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 's', 'e', 'c', 't', 'i', 'o',
      'n', 'B', 'o', 'd', 'y', '"', '>' };

  /** the head line start */
  static final char[][] HEADLINE_BEGIN = {
      { '<', 'h', '1', '>' },
      { '<', 'h', '2', '>' },
      { '<', 'h', '3', '>' },
      { '<', 'h', '4', '>' },
      { '<', 'h', '5', '>' },
      { '<', 'h', '6', '>' },
      { '<', 's', 'p', 'a', 'n', ' ', 'c', 'l', 'a', 's', 's', '=', '"',
          'h', '7', '"', '>' } };

  /** the span class begin */
  static final char[] SPAN_CLASS_BEGIN = { '<', 's', 'p', 'a', 'n', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', };
  /** the span end */
  static final char[] SPAN_END = { '<', '/', 's', 'p', 'a', 'n', '>', };

  /** the head line end */
  static final char[][] HEADLINE_END = { { '<', '/', 'h', '1', '>' },
      { '<', '/', 'h', '2', '>' }, { '<', '/', 'h', '3', '>' },
      { '<', '/', 'h', '4', '>' }, { '<', '/', 'h', '5', '>' },
      { '<', '/', 'h', '6', '>' }, XHTML10Driver.SPAN_END };

  /** the div end */
  static final char[] DIV_END = { '<', '/', 'd', 'i', 'v', '>' };

  /** the synchronizer */
  private static final Object SYNCH = new Object();

  /** the internal font palette */
  private static FontPalette s_fonts;
  /** The default XHTML driver */
  private static XHTML10Driver s_default;

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
  public static final XHTML10Driver getDefaultDriver() {
    synchronized (XHTML10Driver.SYNCH) {
      if (XHTML10Driver.s_default == null) {
        XHTML10Driver.s_default = new XHTML10Driver(null, null, null);
      }
      return XHTML10Driver.s_default;
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
  public XHTML10Driver(final IGraphicDriver gd,
      final PhysicalDimension size, final FontPalette fonts) {
    super("xhtml"); //$NON-NLS-1$
    final PhysicalDimension d;

    this.m_graphicDriver = ((gd != null) ? gd : EGraphicFormat.PNG
        .getDefaultDriver());

    d = ((size != null) ? size : EScreenSize.DEFAULT
        .getPhysicalSize(EScreenSize.DEFAULT_SCREEN_DPI));

    this.m_size = ((d instanceof PageDimension) ? ((PageDimension) d)
        : new PageDimension(d));

    this.m_fonts = ((fonts == null) ? XHTML10Driver.__defaultFonts()
        : fonts);
  }

  /** {@inheritDoc} */
  @Override
  protected final ITextOutput encode(final ITextOutput raw) {
    return XMLCharTransformer.INSTANCE.transform(raw,
        TextUtils.DEFAULT_NORMALIZER_FORM);
  }

  /**
   * obtain the default font palette
   * 
   * @return the default font palette
   */
  private static final FontPalette __defaultFonts() {
    synchronized (XHTML10Driver.SYNCH) {
      if (XHTML10Driver.s_fonts == null) {
        try (final FontPaletteBuilder tb = new FontPaletteBuilder()) {
          PaletteIODriver.INSTANCE.loadResource(tb, XHTML10Driver.class,
              "xhtml10.font.palette"); //$NON-NLS-1$
          XHTML10Driver.s_fonts = tb.getResult();
        } catch (final Throwable tt) {
          ErrorUtils.throwAsRuntimeException(tt);
        }
      }
      return XHTML10Driver.s_fonts;
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

  /** {@inheritDoc} */
  @Override
  protected final Document doCreateDocument(final Path file,
      final IObjectListener listener) {
    return new _XHTML10Document(this, file, listener);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10DocumentHeader createDocumentHeader(
      final Document owner) {
    return new _XHTML10DocumentHeader((_XHTML10Document) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10DocumentBody createDocumentBody(
      final Document owner) {
    return new _XHTML10DocumentBody((_XHTML10Document) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10DocumentFooter createDocumentFooter(
      final Document owner) {
    return new _XHTML10DocumentFooter((_XHTML10Document) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10InBraces createInBraces(final Text owner) {
    return new _XHTML10InBraces(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10InQuotes createInQuotes(final Text owner) {
    return new _XHTML10InQuotes(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10StyledText createStyledText(
      final ComplexText owner, final IStyle style) {
    return new _XHTML10StyledText(owner, style);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10InlineCode createInlineCode(
      final ComplexText owner) {
    return new _XHTML10InlineCode(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10Emphasize createEmphasize(final ComplexText owner) {
    return new _XHTML10Emphasize(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10Subscript createSubscript(final ComplexText owner) {
    return new _XHTML10Subscript(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10Superscript createSuperscript(
      final ComplexText owner) {
    return new _XHTML10Superscript(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10InlineMath createInlineMath(
      final ComplexText owner) {
    return new _XHTML10InlineMath(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10DocumentTitle createDocumentTitle(
      final DocumentHeader owner) {
    return new _XHTML10DocumentTitle((_XHTML10DocumentHeader) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10DocumentSummary createDocumentSummary(
      final DocumentHeader owner) {
    return new _XHTML10DocumentSummary((_XHTML10DocumentHeader) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10Section createSection(final DocumentPart owner,
      final ILabel useLabel, final int index) {
    return new _XHTML10Section(owner, useLabel, index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10SectionTitle createSectionTitle(
      final Section owner) {
    return new _XHTML10SectionTitle((_XHTML10Section) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10SectionBody createSectionBody(final Section owner) {
    return new _XHTML10SectionBody((_XHTML10Section) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10Enumeration createEnumeration(
      final StructuredText owner) {
    return new _XHTML10Enumeration(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10Itemization createItemization(
      final StructuredText owner) {
    return new _XHTML10Itemization(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10Table createTable(final SectionBody owner,
      final ILabel useLabel, final boolean spansAllColumns,
      final int index, final TableCellDef... cells) {
    return new _XHTML10Table(((_XHTML10SectionBody) owner), useLabel,
        spansAllColumns, index, cells);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10Figure createFigure(final SectionBody owner,
      final ILabel useLabel, final EFigureSize size, final String path,
      final int index) {
    return new _XHTML10Figure(((_XHTML10SectionBody) owner), useLabel,
        size, path, index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10FigureSeries createFigureSeries(
      final SectionBody owner, final ILabel useLabel,
      final EFigureSize size, final String path, final int index) {
    return new _XHTML10FigureSeries(((_XHTML10SectionBody) owner),
        useLabel, size, path, index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10Code createCode(final SectionBody owner,
      final ILabel useLabel, final boolean spansAllColumns, final int index) {
    return new _XHTML10Code(((_XHTML10SectionBody) owner), useLabel,
        spansAllColumns, index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10Equation createEquation(final SectionBody owner,
      final ILabel useLabel, final int index) {
    return new _XHTML10Equation(((_XHTML10SectionBody) owner), useLabel,
        index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10EnumerationItem createEnumerationItem(
      final Enumeration owner) {
    return new _XHTML10EnumerationItem((_XHTML10Enumeration) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10ItemizationItem createItemizationItem(
      final Itemization owner) {
    return new _XHTML10ItemizationItem((_XHTML10Itemization) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathFunction createMathFunction(
      final BasicMath owner, final EMathOperators operator) {
    return new _XHTML10MathFunction(owner, operator);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathSubscript createMathSubscript(
      final BasicMath owner) {
    return new _XHTML10MathSubscript(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathSuperscript createMathSuperscript(
      final BasicMath owner) {
    return new _XHTML10MathSuperscript(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathInBraces createMathInBraces(
      final BasicMath owner) {
    return new _XHTML10MathInBraces(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10FigureCaption createFigureCaption(
      final Figure owner) {
    return new _XHTML10FigureCaption((_XHTML10Figure) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10CodeCaption createCaption(final Code owner) {
    return new _XHTML10CodeCaption((_XHTML10Code) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10CodeBody createBody(final Code owner) {
    return new _XHTML10CodeBody((_XHTML10Code) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10CodeInBraces createCodeInBraces(
      final CodeBody owner) {
    return new _XHTML10CodeInBraces((_XHTML10CodeBody) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10FigureSeriesCaption createFigureSeriesCaption(
      final FigureSeries owner) {
    return new _XHTML10FigureSeriesCaption((_XHTML10FigureSeries) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10SubFigure createFigure(final FigureSeries owner,
      final ILabel useLabel, final String path) {
    return new _XHTML10SubFigure(((_XHTML10FigureSeries) owner), useLabel,
        path);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathArgument createMathArgument(
      final MathFunction owner) {
    return new _XHTML10MathArgument((_XHTML10MathFunction) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10TableCaption createTableCaption(final Table owner) {
    return new _XHTML10TableCaption((_XHTML10Table) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10TableHeader createTableHeader(final Table owner) {
    return new _XHTML10TableHeader((_XHTML10Table) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10TableBody createTableBody(final Table owner) {
    return new _XHTML10TableBody((_XHTML10Table) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10TableFooter createTableFooter(final Table owner) {
    return new _XHTML10TableFooter((_XHTML10Table) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10TableBodyRow createTableBodyRow(
      final TableBody owner) {
    return new _XHTML10TableBodyRow((_XHTML10TableBody) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10TableFooterRow createTableFooterRow(
      final TableFooter owner) {
    return new _XHTML10TableFooterRow((_XHTML10TableFooter) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10TableHeaderRow createTableHeaderRow(
      final TableHeader owner) {
    return new _XHTML10TableHeaderRow((_XHTML10TableHeader) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10TableBodyCell createTableBodyCell(
      final TableBodyRow owner, final int rowSpan, final int colSpan,
      final TableCellDef[] def) {
    return new _XHTML10TableBodyCell(((_XHTML10TableBodyRow) owner),
        rowSpan, colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10TableHeaderCell createTableHeaderCell(
      final TableHeaderRow owner, final int rowSpan, final int colSpan,
      final TableCellDef[] def) {
    return new _XHTML10TableHeaderCell(((_XHTML10TableHeaderRow) owner),
        rowSpan, colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10TableFooterCell createTableFooterCell(
      final TableFooterRow owner, final int rowSpan, final int colSpan,
      final TableCellDef[] def) {
    return new _XHTML10TableFooterCell(((_XHTML10TableFooterRow) owner),
        rowSpan, colSpan, def);
  }

}
