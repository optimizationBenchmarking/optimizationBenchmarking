package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.bibliography.data.BibAuthor;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ISequenceable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the author holder */
final class _XHTML10BibAuthorHolder implements ISequenceable {

  /** the original spelling connector */
  static final char[] ORIGINAL_CONNECTOR = { '&', 'n', 'b', 's', 'p', ';',
      '[' };

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
    final String s;
    final ITextOutput raw;
    final BibAuthor a;

    raw = this.m_raw;
    a = this.m_author;

    raw.append(this.m_start);
    textOut.append(a.getPersonalName());
    textOut.append(' ');
    textOut.append(a.getFamilyName());

    s = a.getOriginalSpelling();
    if ((s != null) && (s.length() > 0)) {
      raw.append(_XHTML10BibAuthorHolder.ORIGINAL_CONNECTOR);
      textOut.append(s);
      raw.append(']');
    }
    raw.append(XHTML10Driver.SPAN_END);
  }
}
