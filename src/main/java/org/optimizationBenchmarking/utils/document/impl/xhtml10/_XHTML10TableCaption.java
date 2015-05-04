package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableCaption;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the caption of a table in a XHTML document */
final class _XHTML10TableCaption extends TableCaption {
  /** the start of the float tr body */
  private static final char[] TABLE_TR_BODY_BEGIN = { '<', 't', 'r', ' ',
    'c', 'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', 'l', 'e', 'B',
    'o', 'd', 'y', '"', '>' };
  /** the start of the float body td */
  private static final char[] TABLE_TD_BODY_BEGIN = { '<', 't', 'd', ' ',
    'c', 'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', 'l', 'e', 'B',
    'o', 'd', 'y', '"', '>' };

  /** the start of table */
  private static final char[] TAB_TABLE_BEGIN = { '<', 't', 'a', 'b', 'l',
    'e', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', '"', '>' };

  /**
   * Create the caption of a table
   *
   * @param owner
   *          the owning table
   */
  _XHTML10TableCaption(final _XHTML10Table owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(' ');
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    out.append(_XHTML10Table.TD_END);
    out.append(_XHTML10Table.TR_END);
    out.append(_XHTML10TableCaption.TABLE_TR_BODY_BEGIN);
    out.append(_XHTML10TableCaption.TABLE_TD_BODY_BEGIN);
    out.append(_XHTML10TableCaption.TAB_TABLE_BEGIN);

    super.onClose();
  }
}
