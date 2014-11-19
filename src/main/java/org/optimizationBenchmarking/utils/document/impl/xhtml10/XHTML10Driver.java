package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.impl.EDocumentFormat;
import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.Code;
import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.Document;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentDriver;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentHeader;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentPart;
import org.optimizationBenchmarking.utils.document.impl.abstr.Enumeration;
import org.optimizationBenchmarking.utils.document.impl.abstr.Figure;
import org.optimizationBenchmarking.utils.document.impl.abstr.FigureSeries;
import org.optimizationBenchmarking.utils.document.impl.abstr.Itemization;
import org.optimizationBenchmarking.utils.document.impl.abstr.Label;
import org.optimizationBenchmarking.utils.document.impl.abstr.PlainText;
import org.optimizationBenchmarking.utils.document.impl.abstr.Section;
import org.optimizationBenchmarking.utils.document.impl.abstr.SectionBody;
import org.optimizationBenchmarking.utils.document.impl.abstr.StructuredText;
import org.optimizationBenchmarking.utils.document.impl.abstr.SubFigure;
import org.optimizationBenchmarking.utils.document.impl.abstr.Table;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableBody;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableBodyRow;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooter;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooterRow;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeader;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeaderRow;
import org.optimizationBenchmarking.utils.document.impl.abstr.Text;
import org.optimizationBenchmarking.utils.document.spec.ECitationMode;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.PageDimension;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.PaletteInputDriver;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteBuilder;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.transformations.XMLCharTransformer;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * The driver for xhtml output
 */
public final class XHTML10Driver extends DocumentDriver {
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
      { '<', 'd', 'i', 'v', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'h',
          '7', '"', '>' } };

  /** the span class begin */
  static final char[] SPAN_CLASS_BEGIN = { '<', 's', 'p', 'a', 'n', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', };
  /** the span end */
  static final char[] SPAN_END = { '<', '/', 's', 'p', 'a', 'n', '>', };

  /** the div end */
  static final char[] DIV_END = { '<', '/', 'd', 'i', 'v', '>' };

  /** the head line end */
  static final char[][] HEADLINE_END = { { '<', '/', 'h', '1', '>' },
      { '<', '/', 'h', '2', '>' }, { '<', '/', 'h', '3', '>' },
      { '<', '/', 'h', '4', '>' }, { '<', '/', 'h', '5', '>' },
      { '<', '/', 'h', '6', '>' }, XHTML10Driver.DIV_END };

  /** the span end followed by a non-breakable space */
  static final char[] SPAN_END_NBSP = { '<', '/', 's', 'p', 'a', 'n', '>',
      '&', 'n', 'b', 's', 'p', ';' };

  /** the label start */
  private static final char[] LABEL_BEGIN = { '<', 'a', ' ', 'i', 'd',
      '=', '"', };
  /** the label end */
  private static final char[] LABEL_END = {// avoid empty tags
  '"', '>', '<', '/', 'a', '>' };
  /** non-breaking space */
  static final char[] NBSP = { '&', 'n', 'b', 's', 'p', ';' };

  /** the br */
  static final char[] BR = { '<', 'b', 'r', '/', '>', '&', 'n', 'b', 's',
      'p', ';', '&', 'n', 'b', 's', 'p', ';', '&', 'n', 'b', 's', 'p',
      ';', '&', 'n', 'b', 's', 'p', ';' };

  /** the reference href id */
  static final char[] A_REF = { '<', 'a', ' ', 'c', 'l', 'a', 's', 's',
      '=', '"', 'r', 'e', 'f', '"', ' ', 'h', 'r', 'e', 'f', '=', '"', '#' };

  /** the 3rd part of a reference */
  static final char[] A_REF_END = { '<', '/', 'a', '>' };

  /** the graphic driver */
  private final IGraphicDriver m_graphicDriver;

  /** the screen size */
  private final PageDimension m_size;

  /** the font palette */
  private final FontPalette m_fonts;

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
  XHTML10Driver(final IGraphicDriver gd, final PhysicalDimension size,
      final FontPalette fonts) {
    super();

    PhysicalDimension dim;

    if (gd == null) {
      this.m_graphicDriver = __XHTML10DefaultGraphicDriverLoader.INSTANCE;
    } else {
      if (gd.canUse()) {
        this.m_graphicDriver = gd;
      } else {
        if ((__XHTML10DefaultGraphicDriverLoader.INSTANCE != null)
            && (__XHTML10DefaultGraphicDriverLoader.INSTANCE.canUse())) {
          this.m_graphicDriver = __XHTML10DefaultGraphicDriverLoader.INSTANCE;
        } else {
          this.m_graphicDriver = gd;
        }
      }
    }

    if (size instanceof PageDimension) {
      this.m_size = ((PageDimension) size);
    } else {
      dim = ((size != null) ? size : EScreenSize.DEFAULT
          .getPageSize(EScreenSize.DEFAULT_SCREEN_DPI));
      dim = new PhysicalDimension((0.91d * dim.getWidth()),//
          (0.91d * dim.getHeight()), dim.getUnit());
      this.m_size = new PageDimension(dim);
    }

    this.m_fonts = ((fonts == null) ? XHTML10Driver
        .getDefaultFontPalette() : fonts);
  }

  /** {@inheritDoc} */
  @Override
  public final IFileType getFileType() {
    return EDocumentFormat.XHTML_1_0;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final XHTML10Driver d;
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (o instanceof XHTML10Driver) {
      d = ((XHTML10Driver) o);
      return (EComparison.equals(this.m_graphicDriver, d.m_graphicDriver)
          && EComparison.equals(this.m_size, d.m_size)//
      && EComparison.equals(this.m_fonts, d.m_fonts));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_graphicDriver),//
        HashUtils.combineHashes(HashUtils.hashCode(this.m_size),//
            HashUtils.hashCode(this.m_fonts)));
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append("XHTML 1.0 Document Driver with "); //$NON-NLS-1$
    this.m_graphicDriver.toText(textOut);
    textOut.append(" Graphics and "); //$NON-NLS-1$
    this.m_size.toText(textOut);
    textOut.append(" Pages"); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final ITextOutput encode(final ITextOutput raw) {
    return XHTML10Driver._encode(raw);
  }

  /**
   * Encode a text output
   * 
   * @param raw
   *          the raw output
   * @return the encoded output
   */
  static final ITextOutput _encode(final ITextOutput raw) {
    return XMLCharTransformer.getInstance().transform(raw,
        TextUtils.DEFAULT_NORMALIZER_FORM);
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
  protected final Document doCreateDocument(final Logger logger,
      final IFileProducerListener listener, final Path basePath,
      final String mainDocumentNameSuggestion) {
    return new _XHTML10Document(this, this.makePath(basePath,
        mainDocumentNameSuggestion), logger, listener);
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
  protected final _XHTML10InBraces createInBraces(final PlainText owner) {
    return new _XHTML10InBraces(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10InQuotes createInQuotes(final PlainText owner) {
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
  protected final _XHTML10Subscript createSubscript(final Text owner) {
    return new _XHTML10Subscript(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10Superscript createSuperscript(final Text owner) {
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
  protected final _XHTML10Label createLabel(final Document owner,
      final ELabelType type, final String mark, final String refText) {
    return new _XHTML10Label(((_XHTML10Document) owner), type, mark,
        refText);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathAdd createMathAdd(final BasicMath owner) {
    return new _XHTML10MathAdd(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathSub createMathSub(final BasicMath owner) {
    return new _XHTML10MathSub(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathMul createMathMul(final BasicMath owner) {
    return new _XHTML10MathMul(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathDiv createMathDiv(final BasicMath owner) {
    return new _XHTML10MathDiv(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathDivInline createMathDivInline(
      final BasicMath owner) {
    return new _XHTML10MathDivInline(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathLn createMathLn(final BasicMath owner) {
    return new _XHTML10MathLn(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathLg createMathLg(final BasicMath owner) {
    return new _XHTML10MathLg(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathLd createMathLd(final BasicMath owner) {
    return new _XHTML10MathLd(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathLog createMathLog(final BasicMath owner) {
    return new _XHTML10MathLog(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathMod createMathMod(final BasicMath owner) {
    return new _XHTML10MathMod(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathPow createMathPow(final BasicMath owner) {
    return new _XHTML10MathPow(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathRoot createMathRoot(final BasicMath owner) {
    return new _XHTML10MathRoot(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathSqrt createMathSqrt(final BasicMath owner) {
    return new _XHTML10MathSqrt(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathCompare createMathCompare(
      final BasicMath owner, final EComparison comp) {
    return new _XHTML10MathCompare(owner, comp);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathNegate createMathNegate(final BasicMath owner) {
    return new _XHTML10MathNegate(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathAbs createMathAbsolute(final BasicMath owner) {
    return new _XHTML10MathAbs(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathFactorial createMathFactorial(
      final BasicMath owner) {
    return new _XHTML10MathFactorial(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathSin createMathSin(final BasicMath owner) {
    return new _XHTML10MathSin(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathCos createMathCos(final BasicMath owner) {
    return new _XHTML10MathCos(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathTan createMathTan(final BasicMath owner) {
    return new _XHTML10MathTan(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathNumber createMathNumber(final BasicMath owner) {
    return new _XHTML10MathNumber(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathName createMathName(final BasicMath owner) {
    return new _XHTML10MathName(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10MathText createMathText(final BasicMath owner) {
    return new _XHTML10MathText(owner);
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
  protected final _XHTML10CodeCaption createCodeCaption(final Code owner) {
    return new _XHTML10CodeCaption((_XHTML10Code) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10CodeBody createCodeBody(final Code owner) {
    return new _XHTML10CodeBody((_XHTML10Code) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10FigureSeriesCaption createFigureSeriesCaption(
      final FigureSeries owner) {
    return new _XHTML10FigureSeriesCaption((_XHTML10FigureSeries) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10SubFigure createSubFigure(
      final FigureSeries owner, final ILabel useLabel, final String path) {
    return new _XHTML10SubFigure(((_XHTML10FigureSeries) owner), useLabel,
        path);
  }

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10SubFigureCaption createSubFigureCaption(
      final SubFigure owner) {
    return new _XHTML10SubFigureCaption((_XHTML10SubFigure) owner);
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

  /** {@inheritDoc} */
  @Override
  protected final _XHTML10CitationItem createCitationItem(
      final BibRecord item, final ECitationMode mode) {
    return new _XHTML10CitationItem(item, mode);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return ((this.m_graphicDriver != null)
        && (this.m_graphicDriver.canUse()) && (this.m_fonts != null));
  }

  /**
   * write a label
   * 
   * @param label
   *          the label
   * @param out
   *          the output
   */
  static final void _label(final Label label, final ITextOutput out) {
    if (label != null) {
      XHTML10Driver._label(label.getLabelMark(), out);
    }
  }

  /**
   * write a label
   * 
   * @param label
   *          the label
   * @param out
   *          the output
   */
  static final void _label(final String label, final ITextOutput out) {
    if (label != null) {
      out.append(XHTML10Driver.LABEL_BEGIN);
      out.append(label);
      out.append(XHTML10Driver.LABEL_END);
    }
  }

  /**
   * Get the default XHTML driver
   * 
   * @return the default XHTML driver
   */
  public static final XHTML10Driver getDefaultDriver() {
    return __DefaultXHTML10DriverLoader.INSTANCE;
  }

  /**
   * Create a new XHTML driver
   * 
   * @param gd
   *          the graphic driver to use
   * @param size
   *          the physical screen size to render for
   * @param fonts
   *          the font palette
   * @return the instance of the driver
   */
  public static final XHTML10Driver getInstance(final IGraphicDriver gd,
      final PhysicalDimension size, final FontPalette fonts) {
    return new XHTML10Driver(gd, size, fonts);
  }

  /**
   * obtain the default font palette
   * 
   * @return the default font palette
   */
  public static final FontPalette getDefaultFontPalette() {
    return __XHTML10DefaultFontPaletteLoader.INSTANCE;
  }

  /** the loader class for the default xhtml driver */
  private static final class __DefaultXHTML10DriverLoader {
    /** the instance */
    static final XHTML10Driver INSTANCE = new XHTML10Driver(null, null,
        null);

  }

  /** the graphic driver loader */
  private static final class __XHTML10DefaultGraphicDriverLoader {

    /** the default graphic driver */
    static final IGraphicDriver INSTANCE;

    static {
      IGraphicDriver a, b;

      finder: {
        a = b = EGraphicFormat.PNG.getDefaultDriver();
        if ((a != null) && a.canUse()) {
          break finder;
        }

        b = EGraphicFormat.GIF.getDefaultDriver();
        if (b != null) {
          if (b.canUse()) {
            a = b;
            break finder;
          }
          if (a == null) {
            a = b;
          }
        }

        b = EGraphicFormat.SVGZ.getDefaultDriver();
        if (b != null) {
          if (b.canUse()) {
            a = b;
            break finder;
          }
          if (a == null) {
            a = b;
          }
        }

        b = EGraphicFormat.SVG.getDefaultDriver();
        if (b != null) {
          if (b.canUse()) {
            a = b;
            break finder;
          }
          if (a == null) {
            a = b;
          }
        }

        b = EGraphicFormat.JPEG.getDefaultDriver();
        if (b != null) {
          if (b.canUse()) {
            a = b;
            break finder;
          }
          if (a == null) {
            a = b;
          }
        }

        b = EGraphicFormat.BMP.getDefaultDriver();
        if (b != null) {
          if (b.canUse()) {
            a = b;
            break finder;
          }
          if (a == null) {
            a = b;
          }
        }
      }

      INSTANCE = a;
    }
  }

  /** the default font palette */
  private static final class __XHTML10DefaultFontPaletteLoader {

    /** the internal font palette */
    static final FontPalette INSTANCE;

    static {
      FontPalette p;

      p = null;
      try (final FontPaletteBuilder tb = new FontPaletteBuilder()) {
        PaletteInputDriver.INSTANCE.loadResource(tb, XHTML10Driver.class,
            "xhtml10.fontPalette"); //$NON-NLS-1$
        p = tb.getResult();
      } catch (final Throwable tt) {
        ErrorUtils.throwAsRuntimeException(tt);
      }

      INSTANCE = p;
    }

  }

}
