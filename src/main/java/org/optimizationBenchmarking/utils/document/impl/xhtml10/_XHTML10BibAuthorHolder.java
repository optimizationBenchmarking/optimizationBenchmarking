package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.bibliography.data.BibAuthor;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ISequenceable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the author holder */
final class _XHTML10BibAuthorHolder implements ISequenceable {

  /** the start chars */
  private final char[] m_start;

  /** the author */
  private final BibAuthor m_author;

  /** the raw output */
  private final ITextOutput m_raw;

  /**
   * create
   * 
   * @param start
   *          the start chars
   * @param author
   *          the author
   * @param raw
   *          the raw output
   */
  _XHTML10BibAuthorHolder(final char[] start, final BibAuthor author,
      final ITextOutput raw) {
    super();
    this.m_start = start;
    this.m_author = author;
    this.m_raw = raw;
  }

  /** {@inheritDoc} */
  @Override
  public final void toSequence(final boolean isFirstInSequence,
      final boolean isLastInSequence, final ETextCase textCase,
      final ITextOutput textOut) {
    this.m_raw.append(this.m_start);
    textOut.append(this.m_author.getPersonalName());
    textOut.append(' ');
    textOut.append(this.m_author.getFamilyName());
    this.m_raw.append(XHTML10Driver.SPAN_END);
  }
}
