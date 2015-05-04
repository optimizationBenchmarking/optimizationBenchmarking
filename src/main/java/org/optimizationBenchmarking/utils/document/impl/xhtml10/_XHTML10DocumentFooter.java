package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentFooter;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the XHTML document footer */
final class _XHTML10DocumentFooter extends DocumentFooter {

  /** the html end */
  private static final char[] BODY_HTML_END = { '<', '/', 'b', 'o', 'd',
      'y', '>', '<', '/', 'h', 't', 'm', 'l', '>' };

  /** the reference table */
  private static final char[] REF_TAB = { '<', 't', 'a', 'b', 'l', 'e',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'r', 'e', 'f', '"', '>' };
  /** the reference table tr */
  private static final char[] REF_TR = { '<', 't', 'r', ' ', 'c', 'l',
      'a', 's', 's', '=', '"', 'r', 'e', 'f', '"', '>' };
  /** the reference table idx td */
  private static final char[] REF_ID_TD = { '<', 't', 'd', ' ', 'c', 'l',
      'a', 's', 's', '=', '"', 'r', 'e', 'f', 'I', 'd', '"', '>' };
  /** the reference table text td */
  private static final char[] REF_TXT_TD = { '<', 't', 'd', ' ', 'c', 'l',
      'a', 's', 's', '=', '"', 'r', 'e', 'f', 'T', 'x', 't', '"', '>' };

  /**
   * Create a document footer
   *
   * @param owner
   *          the owning document
   */
  _XHTML10DocumentFooter(final _XHTML10Document owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected void doClose() {
    final ITextOutput out;

    super.doClose();

    out = this.getTextOutput();

    out.append(XHTML10Driver.DIV_END);
    out.append(XHTML10Driver.DIV_END);
    out.append(_XHTML10DocumentFooter.BODY_HTML_END);
  }

  /** {@inheritDoc} */
  @Override
  protected void processCitations(final Bibliography bib) {
    final ITextOutput out, enc;
    int i;

    out = this.getTextOutput();
    out.append(XHTML10Driver.SECTION_DIV_BEGIN);
    out.append(XHTML10Driver.SECTION_HEAD_DIV_BEGIN);

    out.append(XHTML10Driver.HEADLINE_BEGIN[0]);
    out.append("References"); //$NON-NLS-1$
    out.append(XHTML10Driver.HEADLINE_END[0]);

    out.append(XHTML10Driver.DIV_END);

    out.append(XHTML10Driver.SECTION_BODY_DIV_BEGIN);

    out.append(_XHTML10DocumentFooter.REF_TAB);

    enc = XHTML10Driver._encode(out);
    i = 1;
    for (final BibRecord ref : bib) {
      out.append(_XHTML10DocumentFooter.REF_TR);
      out.append(_XHTML10DocumentFooter.REF_ID_TD);
      XHTML10Driver._label(ref.getKey(), out);
      out.append(i++);
      out.append('.');
      out.append(_XHTML10Table.TD_END);

      out.append(_XHTML10DocumentFooter.REF_TXT_TD);
      _XHTML10Bibliography._appendRecord(ref, enc, out);
      out.append(_XHTML10Table.TD_END);
      out.append(_XHTML10Table.TR_END);
    }

    out.append(_XHTML10Table.TABLE_END);

    out.append(XHTML10Driver.DIV_END);
    out.append(XHTML10Driver.DIV_END);
  }
}
