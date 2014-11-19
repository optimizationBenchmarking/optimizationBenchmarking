package org.optimizationBenchmarking.utils.document.impl.latex;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.comparison.EComparison;
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
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * The driver for LaTeX output
 */
public final class LaTeXDriver extends DocumentDriver {

  /** the graphic driver */
  private final IGraphicDriver m_graphicDriver;

  /** the document class */
  private final LaTeXDocumentClass m_class;

  /**
   * Create a new LaTeX driver
   * 
   * @param gd
   *          the graphic driver to use
   * @param clazz
   *          the document class
   */
  public LaTeXDriver(final IGraphicDriver gd,
      final LaTeXDocumentClass clazz) {
    super();

    if (clazz == null) {
      throw new IllegalArgumentException(
          "Document class must not be null."); //$NON-NLS-1$
    }

    this.m_graphicDriver = ((gd != null) ? gd : EGraphicFormat.EPS
        .getDefaultDriver());

    this.m_class = clazz;
  }

  /**
   * Get the default latex driver
   * 
   * @return the default latex driver
   */
  public static final LaTeXDriver getDefaultDriver() {
    return __DefaultLaTeXDriverLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final LaTeXDriver d;
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (o instanceof LaTeXDriver) {
      d = ((LaTeXDriver) o);
      return (EComparison.equals(this.m_graphicDriver, d.m_graphicDriver) && EComparison
          .equals(this.m_class, d.m_class));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_graphicDriver),//
        HashUtils.hashCode(this.m_class));
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append("LaTeX 2\u03b5 Document Driver with "); //$NON-NLS-1$
    textOut.append(this.m_graphicDriver);
    textOut.append(" Graphics and "); //$NON-NLS-1$
    this.m_class.toText(textOut);
    textOut.append(" Pages"); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final IGraphicDriver getGraphicDriver() {
    return this.m_graphicDriver;
  }

  /** {@inheritDoc} */
  @Override
  protected final PhysicalDimension getSize(final EFigureSize size) {
    return size.approximateSize(this.m_class);
  }

  /** {@inheritDoc} */
  @Override
  protected final StyleSet createStyleSet() {
    final IGraphicDriver driver;
    driver = this.getGraphicDriver();
    return new StyleSet(this.m_class.m_fonts, driver.getColorPalette(),
        driver.getStrokePalette());
  }

  /** {@inheritDoc} */
  @Override
  protected final Document doCreateDocument(final Logger logger,
      final IFileProducerListener listener, final Path basePath,
      final String mainDocumentNameSuggestion) {
    return new _LaTeXDocument(this, this.makePath(basePath,
        mainDocumentNameSuggestion), logger, listener);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXDocumentHeader createDocumentHeader(
      final Document owner) {
    return new _LaTeXDocumentHeader((_LaTeXDocument) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXDocumentBody createDocumentBody(
      final Document owner) {
    return new _LaTeXDocumentBody((_LaTeXDocument) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXDocumentFooter createDocumentFooter(
      final Document owner) {
    return new _LaTeXDocumentFooter((_LaTeXDocument) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXInBraces createInBraces(final PlainText owner) {
    return new _LaTeXInBraces(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXInQuotes createInQuotes(final PlainText owner) {
    return new _LaTeXInQuotes(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXStyledText createStyledText(
      final ComplexText owner, final IStyle style) {
    return new _LaTeXStyledText(owner, style);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXInlineCode createInlineCode(final ComplexText owner) {
    return new _LaTeXInlineCode(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXInlineMath createInlineMath(final ComplexText owner) {
    return new _LaTeXInlineMath(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXDocumentTitle createDocumentTitle(
      final DocumentHeader owner) {
    return new _LaTeXDocumentTitle((_LaTeXDocumentHeader) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXDocumentSummary createDocumentSummary(
      final DocumentHeader owner) {
    return new _LaTeXDocumentSummary((_LaTeXDocumentHeader) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXSection createSection(final DocumentPart owner,
      final ILabel useLabel, final int index) {
    return new _LaTeXSection(owner, useLabel, index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXSectionTitle createSectionTitle(final Section owner) {
    return new _LaTeXSectionTitle((_LaTeXSection) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXSectionBody createSectionBody(final Section owner) {
    return new _LaTeXSectionBody((_LaTeXSection) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXEnumeration createEnumeration(
      final StructuredText owner) {
    return new _LaTeXEnumeration(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXItemization createItemization(
      final StructuredText owner) {
    return new _LaTeXItemization(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXTable createTable(final SectionBody owner,
      final ILabel useLabel, final boolean spansAllColumns,
      final int index, final TableCellDef... cells) {
    return new _LaTeXTable(((_LaTeXSectionBody) owner), useLabel,
        spansAllColumns, index, cells);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXFigure createFigure(final SectionBody owner,
      final ILabel useLabel, final EFigureSize size, final String path,
      final int index) {
    return new _LaTeXFigure(((_LaTeXSectionBody) owner), useLabel, size,
        path, index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXFigureSeries createFigureSeries(
      final SectionBody owner, final ILabel useLabel,
      final EFigureSize size, final String path, final int index) {
    return new _LaTeXFigureSeries(((_LaTeXSectionBody) owner), useLabel,
        size, path, index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXCode createCode(final SectionBody owner,
      final ILabel useLabel, final boolean spansAllColumns, final int index) {
    return new _LaTeXCode(((_LaTeXSectionBody) owner), useLabel,
        spansAllColumns, index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXEquation createEquation(final SectionBody owner,
      final ILabel useLabel, final int index) {
    return new _LaTeXEquation(((_LaTeXSectionBody) owner), useLabel, index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXEnumerationItem createEnumerationItem(
      final Enumeration owner) {
    return new _LaTeXEnumerationItem((_LaTeXEnumeration) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXItemizationItem createItemizationItem(
      final Itemization owner) {
    return new _LaTeXItemizationItem((_LaTeXItemization) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathAdd createMathAdd(final BasicMath owner) {
    return new _LaTeXMathAdd(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathSub createMathSub(final BasicMath owner) {
    return new _LaTeXMathSub(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathMul createMathMul(final BasicMath owner) {
    return new _LaTeXMathMul(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathDiv createMathDiv(final BasicMath owner) {
    return new _LaTeXMathDiv(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathDivInline createMathDivInline(
      final BasicMath owner) {
    return new _LaTeXMathDivInline(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathLn createMathLn(final BasicMath owner) {
    return new _LaTeXMathLn(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathLg createMathLg(final BasicMath owner) {
    return new _LaTeXMathLg(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathLd createMathLd(final BasicMath owner) {
    return new _LaTeXMathLd(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathLog createMathLog(final BasicMath owner) {
    return new _LaTeXMathLog(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathMod createMathMod(final BasicMath owner) {
    return new _LaTeXMathMod(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathPow createMathPow(final BasicMath owner) {
    return new _LaTeXMathPow(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathRoot createMathRoot(final BasicMath owner) {
    return new _LaTeXMathRoot(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathSqrt createMathSqrt(final BasicMath owner) {
    return new _LaTeXMathSqrt(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathCompare createMathCompare(
      final BasicMath owner, final EComparison comp) {
    return new _LaTeXMathCompare(owner, comp);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathNegate createMathNegate(final BasicMath owner) {
    return new _LaTeXMathNegate(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathAbs createMathAbsolute(final BasicMath owner) {
    return new _LaTeXMathAbs(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathFactorial createMathFactorial(
      final BasicMath owner) {
    return new _LaTeXMathFactorial(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathSin createMathSin(final BasicMath owner) {
    return new _LaTeXMathSin(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathCos createMathCos(final BasicMath owner) {
    return new _LaTeXMathCos(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathTan createMathTan(final BasicMath owner) {
    return new _LaTeXMathTan(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathInBraces createMathInBraces(
      final BasicMath owner) {
    return new _LaTeXMathInBraces(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXFigureCaption createFigureCaption(
      final Figure owner) {
    return new _LaTeXFigureCaption((_LaTeXFigure) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXCodeCaption createCodeCaption(final Code owner) {
    return new _LaTeXCodeCaption((_LaTeXCode) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXCodeBody createCodeBody(final Code owner) {
    return new _LaTeXCodeBody((_LaTeXCode) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXFigureSeriesCaption createFigureSeriesCaption(
      final FigureSeries owner) {
    return new _LaTeXFigureSeriesCaption((_LaTeXFigureSeries) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXSubFigure createSubFigure(
      final FigureSeries owner, final ILabel useLabel, final String path) {
    return new _LaTeXSubFigure(((_LaTeXFigureSeries) owner), useLabel,
        path);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXSubFigureCaption createSubFigureCaption(
      final SubFigure owner) {
    return new _LaTeXSubFigureCaption((_LaTeXSubFigure) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathNumber createMathNumber(final BasicMath owner) {
    return new _LaTeXMathNumber(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathName createMathName(final BasicMath owner) {
    return new _LaTeXMathName(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXMathText createMathText(final BasicMath owner) {
    return new _LaTeXMathText(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXTableCaption createTableCaption(final Table owner) {
    return new _LaTeXTableCaption((_LaTeXTable) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXTableHeader createTableHeader(final Table owner) {
    return new _LaTeXTableHeader((_LaTeXTable) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXTableBody createTableBody(final Table owner) {
    return new _LaTeXTableBody((_LaTeXTable) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXTableFooter createTableFooter(final Table owner) {
    return new _LaTeXTableFooter((_LaTeXTable) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXTableBodyRow createTableBodyRow(
      final TableBody owner) {
    return new _LaTeXTableBodyRow((_LaTeXTableBody) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXTableFooterRow createTableFooterRow(
      final TableFooter owner) {
    return new _LaTeXTableFooterRow((_LaTeXTableFooter) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXTableHeaderRow createTableHeaderRow(
      final TableHeader owner) {
    return new _LaTeXTableHeaderRow((_LaTeXTableHeader) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXTableBodyCell createTableBodyCell(
      final TableBodyRow owner, final int rowSpan, final int colSpan,
      final TableCellDef[] def) {
    return new _LaTeXTableBodyCell(((_LaTeXTableBodyRow) owner), rowSpan,
        colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXTableHeaderCell createTableHeaderCell(
      final TableHeaderRow owner, final int rowSpan, final int colSpan,
      final TableCellDef[] def) {
    return new _LaTeXTableHeaderCell(((_LaTeXTableHeaderRow) owner),
        rowSpan, colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXTableFooterCell createTableFooterCell(
      final TableFooterRow owner, final int rowSpan, final int colSpan,
      final TableCellDef[] def) {
    return new _LaTeXTableFooterCell(((_LaTeXTableFooterRow) owner),
        rowSpan, colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  public final IFileType getFileType() {
    return ELaTeXFileTypes.LATEX;
  }

  /** the loader for the default LaTeX driver */
  private static final class __DefaultLaTeXDriverLoader {
    /** the shared instance */
    static final LaTeXDriver INSTANCE = new LaTeXDriver(null, null);
  }

}
