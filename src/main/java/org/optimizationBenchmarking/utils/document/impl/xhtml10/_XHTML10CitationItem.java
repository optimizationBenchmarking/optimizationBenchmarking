package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.bibliography.data.BibAuthors;
import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.document.impl.abstr.CitationItem;
import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.spec.ECitationMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A container for a citation bibliographic record in XHTML style
 */
final class _XHTML10CitationItem extends CitationItem {

  /**
   * Create the bibliographic record
   * 
   * @param record
   *          the bibliographic record
   * @param mode
   *          the citation mode
   */
  _XHTML10CitationItem(final BibRecord record, final ECitationMode mode) {
    super(record, mode);
  }

  /** {@inheritDoc} */
  @Override
  protected final void renderAuthors(final BibAuthors authors,
      final ETextCase textCase, final ComplexText out,
      final ITextOutput raw) {
    super.renderAuthors(authors, textCase, out, raw);
  }

  /** {@inheritDoc} */
  @Override
  protected final void renderYear(final int year,
      final ETextCase textCase, final ComplexText out,
      final ITextOutput raw) {
    super.renderYear(year, textCase, out, raw);
  }

  /** {@inheritDoc} */
  @Override
  protected final void renderID(final int index, final String id,
      final ETextCase textCase, final ComplexText out,
      final ITextOutput raw) {

    raw.append('[');
    raw.append(XHTML10Driver.A_REF);
    raw.append(id);
    raw.append(XHTML10Driver.ATTRIB_TAG_BEGIN_END);
    raw.append(index + 1);
    raw.append(XHTML10Driver.A_REF_END);
    raw.append(']');
  }

  /** {@inheritDoc} */
  @Override
  protected final void renderTitle(final String title,
      final ETextCase textCase, final ComplexText out,
      final ITextOutput raw) {
    super.renderTitle(title, textCase, out, raw);
  }

}
