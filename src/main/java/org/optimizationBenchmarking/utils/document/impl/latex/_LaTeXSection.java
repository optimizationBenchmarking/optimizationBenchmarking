package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentPart;
import org.optimizationBenchmarking.utils.document.impl.abstr.Section;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a section in a LaTeX document */
final class _LaTeXSection extends Section {

  /** the sections */
  private static final char[][] SECTIONS;

  static {
    SECTIONS = new char[ELaTeXSection.INSTANCES.size()][];

    _LaTeXSection.SECTIONS[ELaTeXSection.PART.ordinal()] = new char[] {
        '\\', 'p', 'a', 'r', 't', '{' };

    _LaTeXSection.SECTIONS[ELaTeXSection.CHAPTER.ordinal()] = new char[] {
        '\\', 'c', 'h', 'a', 'p', 't', 'e', 'r', '{' };

    _LaTeXSection.SECTIONS[ELaTeXSection.SECTION.ordinal()] = new char[] {
        '\\', 's', 'e', 'c', 't', 'i', 'o', 'n', '{' };

    _LaTeXSection.SECTIONS[ELaTeXSection.SUBSECTION.ordinal()] = new char[] {
        '\\', 's', 'u', 'b', 's', 'e', 'c', 't', 'i', 'o', 'n', '{' };

    _LaTeXSection.SECTIONS[ELaTeXSection.SUBSUBSECTION.ordinal()] = new char[] {
        '\\', 's', 'u', 'b', 's', 'u', 'b', 's', 'e', 'c', 't', 'i', 'o',
        'n', '{' };

    _LaTeXSection.SECTIONS[ELaTeXSection.PARAGRAPH.ordinal()] = new char[] {
        '\\', 'p', 'a', 'r', 'a', 'g', 'r', 'a', 'p', 'h', '{' };

    _LaTeXSection.SECTIONS[ELaTeXSection.SUBPARAGRAPH.ordinal()] = new char[] {
        '\\', 's', 'u', 'b', 'p', 'a', 'r', 'a', 'g', 'r', 'a', 'p', 'h',
        '{' };
  }

  /** start the section emulation */
  private static final char[] SECTION_EMULATOR_A = { '\\', 's', 't', 'r',
      'u', 't', '\\', '\\', '\\', 'n', 'o', 'i', 'n', 'd', 'e', 'n', 't',
      '\\', 't', 'e', 'x', 't', 'b', 'f', '{' };

  /** start the section emulation */
  private static final char[] APPENDIX = { 'A', 'p', 'p', 'e', 'n', 'd',
      'i', 'x', ':', '~' };

  /** is the section emulated? */
  boolean m_emulated;

  /**
   * Create a new section
   * 
   * @param owner
   *          the owning text
   * @param useLabel
   *          the label to use
   * @param index
   *          the section index
   */
  _LaTeXSection(final DocumentPart owner, final ILabel useLabel,
      final int index) {
    super(owner, useLabel, index);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final LaTeXDocumentClass clazz;
    final int ofs, highest;
    final ITextOutput out;

    super.onOpen();

    clazz = ((LaTeXDocument) (this.getDocument())).m_class;
    highest = clazz.getHighestSectionType().ordinal();
    ofs = (highest + this.getDepth());

    out = this.getTextOutput();
    LaTeXDriver._endLine(out);

    this.m_emulated = (ofs > clazz.getLowestSectionType().ordinal());

    if (this.m_emulated) {
      out.append(_LaTeXSection.SECTION_EMULATOR_A);
    } else {
      out.append(_LaTeXSection.SECTIONS[ofs]);
    }

    if (this.isAppendix() && (highest == ofs)) {
      out.append(_LaTeXSection.APPENDIX);
    }
  }
}
