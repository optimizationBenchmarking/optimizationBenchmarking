package org.optimizationBenchmarking.utils.document.impl.latex;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.config.Configuration;
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
import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBuilder;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteBuilder;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPaletteXMLInput;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.transformations.LaTeXCharTransformer;
import org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeX;

/**
 * The driver for LaTeX output
 */
public final class LaTeXDriver extends DocumentDriver {

  /** the item command */
  static final char[] ITEM = { '\\', 'i', 't', 'e', 'm', ' ' };

  /** the label command */
  private static final char[] LABEL = { '\\', 'l', 'a', 'b', 'e', 'l', '{' };
  /** the enforced referencable command */
  private static final char[] PHANTOMSECTION = { '{', '\\', 'p', 'h', 'a',
      'n', 't', 'o', 'm', 's', 'e', 'c', 't', 'i', 'o', 'n', '}' };

  /** begin the center environment */
  static final char[] CENTER_BEGIN = { '\\', 'b', 'e', 'g', 'i', 'n', '{',
      'c', 'e', 'n', 't', 'e', 'r', '}' };
  /** end the center environment */
  static final char[] CENTER_END = { '\\', 'e', 'n', 'd', '{', 'c', 'e',
      'n', 't', 'e', 'r', '}' };

  /** the caption */
  static final char[] CAPTION_BEGIN = { '\\', 'c', 'a', 'p', 't', 'i',
      'o', 'n', '{' };

  /** the citation begin */
  static final char[] CITE_BEGIN = { '{', '\\', 'c', 'i', 't', 'e', '{' };

  /** Create a new LaTeX driver */
  LaTeXDriver() {
    super();
  }

  /**
   * Put a label
   * 
   * @param label
   *          the label
   * @param out
   *          the text output
   */
  static final void _label(final Label label, final ITextOutput out) {
    LaTeXDriver._label(label, out, false);
  }

  /**
   * Put a label
   * 
   * @param label
   *          the label
   * @param out
   *          the text output
   * @param enforce
   *          should we enforce the label location?
   */
  static final void _label(final Label label, final ITextOutput out,
      final boolean enforce) {
    final String id;

    if (label == null) {
      return;
    }
    id = label.getLabelMark();
    if (id == null) {
      return;
    }
    if (enforce) {
      out.append(LaTeXDriver.PHANTOMSECTION);
    }
    out.append(LaTeXDriver.LABEL);
    out.append(id);
    LaTeXDriver._endCommandLine(out);
  }

  /**
   * Write a comment and end the line.
   * 
   * @param comment
   *          the comment
   * @param out
   *          the text output
   */
  static final void _commentLine(final String comment,
      final ITextOutput out) {
    out.append('%');
    out.append(' ');
    out.append(comment);
    LaTeXDriver._endLine(out);
  }

  /**
   * End the line
   * 
   * @param out
   *          the text output
   */
  static final void _endLine(final ITextOutput out) {
    out.append('%');
    out.appendLineBreak();
  }

  /**
   * End a command line
   * 
   * @param out
   *          the text output
   */
  static final void _endCommandLine(final ITextOutput out) {
    out.append('}');
    LaTeXDriver._endLine(out);
  }

  /**
   * Get the default latex driver
   * 
   * @return the default latex driver
   */
  public static final LaTeXDriver getInstance() {
    return __LaTeXDriverLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "LaTeX 2\u03b5 Document Driver"; //$NON-NLS-1$
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
      final int index, final ETableCellDef... cells) {
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
      final ETableCellDef[] def) {
    return new _LaTeXTableBodyCell(((_LaTeXTableBodyRow) owner), rowSpan,
        colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXTableHeaderCell createTableHeaderCell(
      final TableHeaderRow owner, final int rowSpan, final int colSpan,
      final ETableCellDef[] def) {
    return new _LaTeXTableHeaderCell(((_LaTeXTableHeaderRow) owner),
        rowSpan, colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXTableFooterCell createTableFooterCell(
      final TableFooterRow owner, final int rowSpan, final int colSpan,
      final ETableCellDef[] def) {
    return new _LaTeXTableFooterCell(((_LaTeXTableFooterRow) owner),
        rowSpan, colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXSuperscript createSuperscript(final Text owner) {
    return new _LaTeXSuperscript(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXSubscript createSubscript(final Text owner) {
    return new _LaTeXSubscript(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXEmphasize createEmphasize(final ComplexText owner) {
    return new _LaTeXEmphasize(owner);
  }

  /** {@inheritDoc} */
  @Override
  public final IFileType getFileType() {
    return ELaTeXFileType.TEX;
  }

  /** {@inheritDoc} */
  @Override
  public IDocumentBuilder use() {
    this.checkCanUse();
    return new LaTeXDocumentBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (__LaTeXDefaultGraphicDriverLoader.INSTANCE != null)
        && (_LaTeXDefaultFontPaletteLoader.INSTANCE != null);
  }

  /** {@inheritDoc} */
  @Override
  public final IGraphicDriver getDefaultGraphicDriver() {
    return LaTeXDriver.defaultGraphicDriver();
  }

  /** {@inheritDoc} */
  @Override
  protected final ITextOutput encode(final ITextOutput raw) {
    return LaTeXCharTransformer.getInstance().transform(raw,
        Normalizer.Form.NFC);
  }

  /** {@inheritDoc} */
  @Override
  public final void checkCanUse() {
    UnsupportedOperationException errorA, errorB, error;

    if (__LaTeXDefaultGraphicDriverLoader.INSTANCE == null) {
      errorA = __LaTeXDefaultGraphicDriverLoader.ERROR;
    } else {
      errorA = null;
    }
    if (_LaTeXDefaultFontPaletteLoader.INSTANCE == null) {
      errorB = _LaTeXDefaultFontPaletteLoader.ERROR;
    } else {
      errorB = null;
    }

    if (errorA != null) {
      if (errorB != null) {
        error = new UnsupportedOperationException(//
            "Cannot use LaTeX Document Driver"); //$NON-NLS-1$
        error.addSuppressed(errorA);
        error.addSuppressed(errorB);
        throw error;
      }
      throw errorA;
    }
    if (errorB != null) {
      throw errorB;
    }

    super.checkCanUse();
  }

  /**
   * Get the default graphic driver for LaTeX documents
   * 
   * @return the default graphic driver for LaTeX documents
   */
  public static final IGraphicDriver defaultGraphicDriver() {
    return __LaTeXDefaultGraphicDriverLoader.INSTANCE;
  }

  /**
   * obtain the default font palette
   * 
   * @return the default font palette
   */
  public static final FontPalette defaultFontPalette() {
    return _LaTeXDefaultFontPaletteLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected void checkGraphicDriver(final IGraphicDriver driver) {
    final EGraphicFormat format;

    format = driver.getFileType();
    if (_LaTeXSupportedFormatsLoader.SUPPORTED_GRAPHIC_FORMATS[//
    format.ordinal()]) {
      return;
    }

    throw new IllegalArgumentException(//
        "LaTeX driver does not support graphic format " + format); //$NON-NLS-1$
  }

  /**
   * Encode a text output to be used for code. TODO: This will not work
   * with special characters. A better method needs to be found.
   * 
   * @param raw
   *          the raw text output
   * @param encoded
   *          the encoded text output
   * @return the encoded text output
   */
  final ITextOutput _encodeCode(final ITextOutput raw,
      final ITextOutput encoded) {
    return raw;
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXCitationItem createCitationItem(
      final BibRecord item, final ECitationMode mode) {
    return new _LaTeXCitationItem(item, mode);
  }

  /** {@inheritDoc} */
  @Override
  protected final void cite(final Bibliography bib,
      final ECitationMode citationMode, final ETextCase textCase,
      final ESequenceMode sequenceMode, final ComplexText complexText,
      final ITextOutput raw) {
    boolean b;

    if ((bib.size() == 1)
        || ((sequenceMode == ESequenceMode.COMMA) && (citationMode == ECitationMode.ID))) {
      raw.append(LaTeXDriver.CITE_BEGIN);
      b = false;
      for (final BibRecord rec : bib) {
        if (b) {
          raw.append(',');
        } else {
          b = true;
        }
        raw.append(rec.getKey());
      }
      raw.append('}');
      raw.append('}');
    } else {
      super.cite(bib, citationMode, textCase, sequenceMode,//
          complexText, raw);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final _LaTeXLabel createLabel(final Document owner,
      final ELabelType type, final String mark, final String refText) {
    return new _LaTeXLabel(((_LaTeXDocument) owner), type, mark, refText);
  }

  /** the loader for the default LaTeX driver */
  private static final class __LaTeXDriverLoader {
    /** the shared instance */
    static final LaTeXDriver INSTANCE = new LaTeXDriver();
  }

  /** the default font palette */
  static final class _LaTeXDefaultFontPaletteLoader {

    /** the internal font palette */
    static final FontPalette INSTANCE;

    /** the error */
    static final UnsupportedOperationException ERROR;

    static {
      final Logger logger;
      FontPalette p;
      Throwable error;
      String msg;

      p = null;
      error = null;
      logger = Configuration.getGlobalLogger();
      try (final FontPaletteBuilder tb = new FontPaletteBuilder()) {
        FontPaletteXMLInput
            .getInstance()
            .use()
            .setLogger(logger)
            .setDestination(tb)
            .addResource(LaTeXDriver.class, "latex.fontPalette").create().call(); //$NON-NLS-1$
        p = tb.getResult();
      } catch (final Throwable tt) {
        error = tt;
        p = null;
        ErrorUtils
            .logError(
                logger,
                "Error while loading the default font palette for the LaTeX Document Driver. This will creating LaTeX documents using this palette impossible.", //$NON-NLS-1$
                error, true);
      }

      if (p != null) {
        INSTANCE = p;
        ERROR = null;
      } else {
        INSTANCE = null;
        msg = "Could not load default font palette."; //$NON-NLS-1$
        ERROR = ((error != null) ? new UnsupportedOperationException(msg,
            error) : new UnsupportedOperationException(msg));
      }
    }

  }

  /** the loader for the supported graphics formats */
  static final class _LaTeXSupportedFormatsLoader {
    /** the supported graphic formats */
    static final boolean[] SUPPORTED_GRAPHIC_FORMATS;

    static {
      SUPPORTED_GRAPHIC_FORMATS = new boolean[EGraphicFormat.INSTANCES
          .size()];
      for (final EGraphicFormat format : LaTeX
          .getAllSupportedGraphicFormats()) {
        _LaTeXSupportedFormatsLoader.SUPPORTED_GRAPHIC_FORMATS[format
            .ordinal()] = true;
      }
    }
  }

  /** the default graphic driver */
  private static final class __LaTeXDefaultGraphicDriverLoader {

    /** the default graphic driver */
    static final IGraphicDriver INSTANCE;

    /** the error */
    static final UnsupportedOperationException ERROR;

    static {
      IGraphicDriver driver;
      ArrayList<Throwable> error;

      error = null;
      driver = null;
      for (final EGraphicFormat format : new EGraphicFormat[] {
          EGraphicFormat.PDF, EGraphicFormat.EPS, EGraphicFormat.SVG,
          EGraphicFormat.SVGZ, EGraphicFormat.EMF, EGraphicFormat.PNG,
          EGraphicFormat.BMP, EGraphicFormat.GIF, EGraphicFormat.JPEG, }) {
        if (_LaTeXSupportedFormatsLoader.SUPPORTED_GRAPHIC_FORMATS[//
        format.ordinal()]) {
          try {
            driver = format.getDefaultDriver();
            driver.checkCanUse();
            break;
          } catch (final Throwable t) {
            driver = null;
            if (error == null) {
              error = new ArrayList<>();
            }
            error.add(t);
          }
        }
      }
      if (driver == null) {
        INSTANCE = null;
        ERROR = new UnsupportedOperationException(//
            "Could not load a driver for any supported graphic format."); //$NON-NLS-1$
        if (error != null) {
          for (final Throwable tt : error) {
            __LaTeXDefaultGraphicDriverLoader.ERROR.addSuppressed(tt);
          }
        }
      } else {
        INSTANCE = driver;
        ERROR = null;
      }
    }
  }
}
