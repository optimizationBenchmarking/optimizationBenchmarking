package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Table;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableBody;
import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** a table in a XHTML document */
final class _XHTML10Table extends Table {
  /** the end of the table */
  static final char[] TABLE_END = { '<', '/', 't', 'a', 'b', 'l', 'e', '>' };
  /** the end of a tr */
  static final char[] TR_END = { '<', '/', 't', 'r', '>' };
  /** the end of a td */
  static final char[] TD_END = { '<', '/', 't', 'd', '>' };

  /** the start of the table */
  private static final char[] TABLE_DIV_BEGIN = { '<', 'd', 'i', 'v', ' ',
    'c', 'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', 'l', 'e', '"',
    '>', };

  /** the start of the table */
  private static final char[] TABLE_TABLE_BEGIN = { '<', 't', 'a', 'b',
    'l', 'e', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 't', 'a', 'b',
    'l', 'e', '"', '>' };

  /** the start of the float tr caption */
  private static final char[] TABLE_TR_CAPTION_BEGIN = { '<', 't', 'r',
    ' ', 'c', 'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', 'l', 'e',
    'C', 'a', 'p', 't', 'i', 'o', 'n', '"', '>' };
  /** the start of the float td */
  private static final char[] TABLE_TD_CAPTION_SPAN_BEGIN = { '<', 't',
    'd', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', 'l',
    'e', 'C', 'a', 'p', 't', 'i', 'o', 'n', '"', '>', '<', 's', 'p',
    'a', 'n', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'c', 'a', 'p',
    't', 'i', 'o', 'n', 'T', 'i', 't', 'l', 'e', '"', '>', 'T', 'b',
    'l', '.', '&', 'n', 'b', 's', 'p', ';' };

  /** the cached body */
  private char[] m_body;

  /**
   * Create a table
   *
   * @param owner
   *          the owning section body
   * @param index
   *          the table index in the owning section
   * @param useLabel
   *          the label to use
   * @param spansAllColumns
   *          does the table span all columns
   * @param definition
   *          the table cell definition
   */
  _XHTML10Table(final _XHTML10SectionBody owner, final ILabel useLabel,
      final boolean spansAllColumns, final int index,
      final ETableCellDef[] definition) {
    super(owner, useLabel, spansAllColumns, index, definition);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;

    super.onOpen();

    out = this.getTextOutput();

    out.append(_XHTML10Table.TABLE_DIV_BEGIN);
    out.append(_XHTML10Table.TABLE_TABLE_BEGIN);
    out.append(_XHTML10Table.TABLE_TR_CAPTION_BEGIN);
    out.append(_XHTML10Table.TABLE_TD_CAPTION_SPAN_BEGIN);
    out.append(this.getGlobalID());
    out.append(XHTML10Driver.SPAN_END_NBSP);
    XHTML10Driver._label(this.getLabel(), out);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    out.append(this.m_body);
    this.m_body = null;
    out.append(_XHTML10Table.TABLE_END);
    out.append(_XHTML10Table.TD_END);
    out.append(_XHTML10Table.TR_END);
    out.append(_XHTML10Table.TABLE_END);
    out.append(XHTML10Driver.DIV_END);
    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean mustChildBeBuffered(final HierarchicalText child) {
    return (child instanceof TableBody);
  }

  /** {@inheritDoc} */
  @Override
  protected final void processBufferedOutputFromChild(
      final HierarchicalText child, final MemoryTextOutput out) {
    if (child instanceof TableBody) {
      this.m_body = out.toChars();
    } else {
      super.processBufferedOutputFromChild(child, out);
    }
  }
}
