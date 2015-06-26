package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator;

import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.document.spec.ECitationMode;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;
import org.optimizationBenchmarking.utils.document.spec.ICode;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IEquation;
import org.optimizationBenchmarking.utils.document.spec.IFigure;
import org.optimizationBenchmarking.utils.document.spec.IFigureSeries;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.IList;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ITable;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the delayed section body */
final class _DelayedSectionBody implements ISectionBody {

  /** the real section body */
  private final ISectionBody m_real;

  /**
   * create
   *
   * @param real
   *          the real body
   */
  _DelayedSectionBody(final ISectionBody real) {
    super();
    this.m_real = real;
  }

  /** close the body */
  final void _close() {
    this.m_real.close();
  }

  /** {@inheritDoc} */
  @Override
  public final IList enumeration() {
    return this.m_real.enumeration();
  }

  /** {@inheritDoc} */
  @Override
  public final IList itemization() {
    return this.m_real.itemization();
  }

  /** {@inheritDoc} */
  @Override
  public final IComplexText style(final IStyle style) {
    return this.m_real.style(style);
  }

  /** {@inheritDoc} */
  @Override
  public final IPlainText subscript() {
    return this.m_real.subscript();
  }

  /** {@inheritDoc} */
  @Override
  public final IPlainText superscript() {
    return this.m_real.superscript();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath inlineMath() {
    return this.m_real.inlineMath();
  }

  /** {@inheritDoc} */
  @Override
  public final IText inlineCode() {
    return this.m_real.inlineCode();
  }

  /** {@inheritDoc} */
  @Override
  public final IPlainText emphasize() {
    return this.m_real.emphasize();
  }

  /** {@inheritDoc} */
  @Override
  public final BibliographyBuilder cite(final ECitationMode citationMode,
      final ETextCase textCase, final ESequenceMode sequenceMode) {
    return this.m_real.cite(citationMode, textCase, sequenceMode);
  }

  /** {@inheritDoc} */
  @Override
  public final void reference(final ETextCase textCase,
      final ESequenceMode sequenceMode, final ILabel... labels) {
    this.m_real.reference(textCase, sequenceMode, labels);
  }

  /** {@inheritDoc} */
  @Override
  public final IPlainText inQuotes() {
    return this.m_real.inQuotes();
  }

  /** {@inheritDoc} */
  @Override
  public final IPlainText inBraces() {
    return this.m_real.inBraces();
  }

  /** {@inheritDoc} */
  @Override
  public final ILabel createLabel(final ELabelType type) {
    return this.m_real.createLabel(type);
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final ITextOutput append(final CharSequence csq) {
    this.m_real.append(csq);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ITextOutput append(final CharSequence csq, final int start,
      final int end) {
    this.m_real.append(csq, start, end);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ITextOutput append(final char c) {
    this.m_real.append(c);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s) {
    this.m_real.append(s);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final String s, final int start, final int end) {
    this.m_real.append(s, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] chars) {
    this.m_real.append(chars);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final char[] chars, final int start,
      final int end) {
    this.m_real.append(chars, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final byte v) {
    this.m_real.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final short v) {
    this.m_real.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final int v) {
    this.m_real.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final long v) {
    this.m_real.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final float v) {
    this.m_real.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final double v) {
    this.m_real.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final boolean v) {
    this.m_real.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final Object o) {
    this.m_real.append(o);
  }

  /** {@inheritDoc} */
  @Override
  public final void appendLineBreak() {
    this.m_real.appendLineBreak();
  }

  /** {@inheritDoc} */
  @Override
  public final void appendNonBreakingSpace() {
    this.m_real.appendNonBreakingSpace();
  }

  /** {@inheritDoc} */
  @Override
  public final void flush() {
    this.m_real.flush();
  }

  /** {@inheritDoc} */
  @Override
  public final ISection section(final ILabel useLabel) {
    return this.m_real.section(useLabel);
  }

  /** {@inheritDoc} */
  @Override
  public final ITable table(final ILabel useLabel,
      final boolean spansAllColumns, final ETableCellDef... cells) {
    return this.m_real.table(useLabel, spansAllColumns, cells);
  }

  /** {@inheritDoc} */
  @Override
  public final IFigure figure(final ILabel useLabel,
      final EFigureSize size, final String path) {
    return this.m_real.figure(useLabel, size, path);
  }

  /** {@inheritDoc} */
  @Override
  public final IFigureSeries figureSeries(final ILabel useLabel,
      final EFigureSize size, final String path) {
    return this.m_real.figureSeries(useLabel, size, path);
  }

  /** {@inheritDoc} */
  @Override
  public final ICode code(final ILabel useLabel,
      final boolean spansAllColumns) {
    return this.m_real.code(useLabel, spansAllColumns);
  }

  /** {@inheritDoc} */
  @Override
  public final IEquation equation(final ILabel useLabel) {
    return this.m_real.equation(useLabel);
  }

}
