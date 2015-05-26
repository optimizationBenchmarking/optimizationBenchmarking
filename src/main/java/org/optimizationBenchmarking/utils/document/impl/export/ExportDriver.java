package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.chart.impl.EChartFormat;
import org.optimizationBenchmarking.utils.chart.spec.IChartDriver;
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
import org.optimizationBenchmarking.utils.document.impl.abstr.Text;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.EMathComparison;
import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.text.ETextFileType;

/**
 * The driver for export output
 */
public final class ExportDriver extends DocumentDriver {

  /** Create a new export driver */
  ExportDriver() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final IGraphicDriver getDefaultGraphicDriver() {
    return EGraphicFormat.TEXT.getDefaultDriver();
  }

  /** {@inheritDoc} */
  @Override
  public final IFileType getFileType() {
    return ETextFileType.TXT;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Export Document Driver"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportDocumentHeader createDocumentHeader(
      final Document owner) {
    return new _ExportDocumentHeader((_ExportDocument) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportDocumentBody createDocumentBody(
      final Document owner) {
    return new _ExportDocumentBody((_ExportDocument) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportDocumentFooter createDocumentFooter(
      final Document owner) {
    return new _ExportDocumentFooter((_ExportDocument) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportInBraces createInBraces(final PlainText owner) {
    return new _ExportInBraces(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportInQuotes createInQuotes(final PlainText owner) {
    return new _ExportInQuotes(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportStyledText createStyledText(
      final ComplexText owner, final IStyle style) {
    return new _ExportStyledText(owner, style);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportInlineCode createInlineCode(
      final ComplexText owner) {
    return new _ExportInlineCode(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportEmphasize createEmphasize(final ComplexText owner) {
    return new _ExportEmphasize(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportSubscript createSubscript(final Text owner) {
    return new _ExportSubscript(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportSuperscript createSuperscript(final Text owner) {
    return new _ExportSuperscript(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportInlineMath createInlineMath(
      final ComplexText owner) {
    return new _ExportInlineMath(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportDocumentTitle createDocumentTitle(
      final DocumentHeader owner) {
    return new _ExportDocumentTitle((_ExportDocumentHeader) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportDocumentSummary createDocumentSummary(
      final DocumentHeader owner) {
    return new _ExportDocumentSummary((_ExportDocumentHeader) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportSection createSection(final DocumentPart owner,
      final ILabel useLabel, final int index) {
    return new _ExportSection(owner, useLabel, index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportSectionTitle createSectionTitle(
      final Section owner) {
    return new _ExportSectionTitle((_ExportSection) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportSectionBody createSectionBody(final Section owner) {
    return new _ExportSectionBody((_ExportSection) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportEnumeration createEnumeration(
      final StructuredText owner) {
    return new _ExportEnumeration(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportItemization createItemization(
      final StructuredText owner) {
    return new _ExportItemization(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportTable createTable(final SectionBody owner,
      final ILabel useLabel, final boolean spansAllColumns,
      final int index, final ETableCellDef... cells) {
    return new _ExportTable(((_ExportSectionBody) owner), useLabel,
        spansAllColumns, index, cells);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportFigure createFigure(final SectionBody owner,
      final ILabel useLabel, final EFigureSize size, final String path,
      final int index) {
    return new _ExportFigure(((_ExportSectionBody) owner), useLabel, size,
        path, index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportFigureSeries createFigureSeries(
      final SectionBody owner, final ILabel useLabel,
      final EFigureSize size, final String path, final int index) {
    return new _ExportFigureSeries(((_ExportSectionBody) owner), useLabel,
        size, path, index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportCode createCode(final SectionBody owner,
      final ILabel useLabel, final boolean spansAllColumns, final int index) {
    return new _ExportCode(((_ExportSectionBody) owner), useLabel,
        spansAllColumns, index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportEquation createEquation(final SectionBody owner,
      final ILabel useLabel, final int index) {
    return new _ExportEquation(((_ExportSectionBody) owner), useLabel,
        index);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportEnumerationItem createEnumerationItem(
      final Enumeration owner) {
    return new _ExportEnumerationItem((_ExportEnumeration) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportItemizationItem createItemizationItem(
      final Itemization owner) {
    return new _ExportItemizationItem((_ExportItemization) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathAdd createMathAdd(final BasicMath owner) {
    return new _ExportMathAdd(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathSub createMathSub(final BasicMath owner) {
    return new _ExportMathSub(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathMul createMathMul(final BasicMath owner) {
    return new _ExportMathMul(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathDiv createMathDiv(final BasicMath owner) {
    return new _ExportMathDiv(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathDivInline createMathDivInline(
      final BasicMath owner) {
    return new _ExportMathDivInline(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathLn createMathLn(final BasicMath owner) {
    return new _ExportMathLn(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathLg createMathLg(final BasicMath owner) {
    return new _ExportMathLg(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathLd createMathLd(final BasicMath owner) {
    return new _ExportMathLd(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathLog createMathLog(final BasicMath owner) {
    return new _ExportMathLog(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathMod createMathMod(final BasicMath owner) {
    return new _ExportMathMod(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathPow createMathPow(final BasicMath owner) {
    return new _ExportMathPow(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathRoot createMathRoot(final BasicMath owner) {
    return new _ExportMathRoot(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathSqrt createMathSqrt(final BasicMath owner) {
    return new _ExportMathSqrt(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathCompare createMathCompare(
      final BasicMath owner, final EMathComparison comp) {
    return new _ExportMathCompare(owner, comp);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathNegate createMathNegate(final BasicMath owner) {
    return new _ExportMathNegate(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathAbs createMathAbsolute(final BasicMath owner) {
    return new _ExportMathAbs(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathFactorial createMathFactorial(
      final BasicMath owner) {
    return new _ExportMathFactorial(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathSin createMathSin(final BasicMath owner) {
    return new _ExportMathSin(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathCos createMathCos(final BasicMath owner) {
    return new _ExportMathCos(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathTan createMathTan(final BasicMath owner) {
    return new _ExportMathTan(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathNAryFunction createMathNAryFunction(
      final BasicMath owner, final String name, final int minArity,
      final int maxArity) {
    return new _ExportMathNAryFunction(owner, name, minArity, maxArity);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathNumber createMathNumber(final BasicMath owner) {
    return new _ExportMathNumber(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathName createMathName(final BasicMath owner) {
    return new _ExportMathName(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathText createMathText(final BasicMath owner) {
    return new _ExportMathText(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportMathInBraces createMathInBraces(
      final BasicMath owner) {
    return new _ExportMathInBraces(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportFigureCaption createFigureCaption(
      final Figure owner) {
    return new _ExportFigureCaption((_ExportFigure) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportCodeCaption createCodeCaption(final Code owner) {
    return new _ExportCodeCaption((_ExportCode) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportCodeBody createCodeBody(final Code owner) {
    return new _ExportCodeBody((_ExportCode) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportFigureSeriesCaption createFigureSeriesCaption(
      final FigureSeries owner) {
    return new _ExportFigureSeriesCaption((_ExportFigureSeries) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportSubFigure createSubFigure(
      final FigureSeries owner, final ILabel useLabel, final String path) {
    return new _ExportSubFigure(((_ExportFigureSeries) owner), useLabel,
        path);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportSubFigureCaption createSubFigureCaption(
      final SubFigure owner) {
    return new _ExportSubFigureCaption((_ExportSubFigure) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportTableCaption createTableCaption(final Table owner) {
    return new _ExportTableCaption((_ExportTable) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportTableHeader createTableHeader(final Table owner) {
    return new _ExportTableHeader((_ExportTable) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportTableBody createTableBody(final Table owner) {
    return new _ExportTableBody((_ExportTable) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportTableFooter createTableFooter(final Table owner) {
    return new _ExportTableFooter((_ExportTable) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportTableBodyRow createTableBodyRow(
      final TableBody owner) {
    return new _ExportTableBodyRow((_ExportTableBody) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportTableFooterRow createTableFooterRow(
      final TableFooter owner) {
    return new _ExportTableFooterRow((_ExportTableFooter) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportTableHeaderRow createTableHeaderRow(
      final TableHeader owner) {
    return new _ExportTableHeaderRow((_ExportTableHeader) owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportTableBodyCell createTableBodyCell(
      final TableBodyRow owner, final int rowSpan, final int colSpan,
      final ETableCellDef[] def) {
    return new _ExportTableBodyCell(((_ExportTableBodyRow) owner),
        rowSpan, colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportTableHeaderCell createTableHeaderCell(
      final TableHeaderRow owner, final int rowSpan, final int colSpan,
      final ETableCellDef[] def) {
    return new _ExportTableHeaderCell(((_ExportTableHeaderRow) owner),
        rowSpan, colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  protected final _ExportTableFooterCell createTableFooterCell(
      final TableFooterRow owner, final int rowSpan, final int colSpan,
      final ETableCellDef[] def) {
    return new _ExportTableFooterCell(((_ExportTableFooterRow) owner),
        rowSpan, colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final void checkCanUse() {
    //
  }

  /** {@inheritDoc} */
  @Override
  public final ExportDocumentBuilder use() {
    return new ExportDocumentBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  protected void checkGraphicDriver(final IGraphicDriver driver) {
    final EGraphicFormat format;

    if ((format = driver.getFileType()) != EGraphicFormat.TEXT) {
      throw new IllegalArgumentException(//
          "The export driver only supports the TEXT pseudo-graphic formats, but you supplied "//$NON-NLS-1$
              + format);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final IChartDriver getDefaultChartDriver() {
    return EChartFormat.EXPORT.getDefaultDriver();
  }

  /** {@inheritDoc} */
  @Override
  protected final void checkChartDriver(final IChartDriver driver) {
    if (driver != this.getDefaultChartDriver()) {
      throw new IllegalArgumentException(//
          "The export driver only supports the export pseudo-chart driver, but you supplied "//$NON-NLS-1$
              + driver);
    }
  }

  /**
   * Get the export driver
   *
   * @return the export driver
   */
  public static final ExportDriver getInstance() {
    return __ExportDriverLoader.INSTANCE;
  }

  /** the loader class for the default export driver */
  private static final class __ExportDriverLoader {
    /** the instance */
    static final ExportDriver INSTANCE = new ExportDriver();
  }
}
