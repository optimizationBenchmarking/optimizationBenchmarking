package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ITable;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for tables
 */
public class Table extends DocumentPart implements ITable {

  /** the state when the caption has been created */
  private static final int STATE_CAPTION_CREATED = (DocumentElement.STATE_MAX_ELEMENT + 1);
  /** the state before the caption */
  private static final int STATE_CAPTION_BEFORE_OPEN = (Table.STATE_CAPTION_CREATED + 1);
  /** the state in the caption */
  private static final int STATE_CAPTION_OPENED = (Table.STATE_CAPTION_BEFORE_OPEN + 1);
  /** the state after the caption */
  private static final int STATE_CAPTION_CLOSED = (Table.STATE_CAPTION_OPENED + 1);

  /** the state when the header has been created */
  private static final int STATE_HEADER_CREATED = (Table.STATE_CAPTION_CLOSED + 1);
  /** the state before the header */
  private static final int STATE_HEADER_BEFORE_OPEN = (Table.STATE_HEADER_CREATED + 1);
  /** the state in the header */
  private static final int STATE_HEADER_OPENED = (Table.STATE_HEADER_BEFORE_OPEN + 1);
  /** the state after the header */
  private static final int STATE_HEADER_CLOSED = (Table.STATE_HEADER_OPENED + 1);
  /** the state when the body has been created */
  private static final int STATE_BODY_CREATED = (Table.STATE_HEADER_CLOSED + 1);
  /** the state before the body */
  private static final int STATE_BODY_BEFORE_OPEN = (Table.STATE_BODY_CREATED + 1);
  /** the state in the body */
  private static final int STATE_BODY_OPENED = (Table.STATE_BODY_BEFORE_OPEN + 1);
  /** the state after the body */
  private static final int STATE_BODY_CLOSED = (Table.STATE_BODY_OPENED + 1);
  /** the state when the footer has been created */
  private static final int STATE_FOOTER_CREATED = (Table.STATE_BODY_CLOSED + 1);
  /** the state before the footer */
  private static final int STATE_FOOTER_BEFORE_OPEN = (Table.STATE_FOOTER_CREATED + 1);
  /** the state in the footer */
  private static final int STATE_FOOTER_OPENED = (Table.STATE_FOOTER_BEFORE_OPEN + 1);
  /** the state after the footer */
  private static final int STATE_FOOTER_CLOSED = (Table.STATE_FOOTER_OPENED + 1);

  /** the state names */
  private static final String[] STATE_NAMES;

  static {
    STATE_NAMES = new String[Table.STATE_FOOTER_CLOSED + 1];

    Table.STATE_NAMES[Table.STATE_CAPTION_CREATED] = "captionCreated"; //$NON-NLS-1$
    Table.STATE_NAMES[Table.STATE_CAPTION_BEFORE_OPEN] = "captionBeforeOpen"; //$NON-NLS-1$
    Table.STATE_NAMES[Table.STATE_CAPTION_OPENED] = "captionOpened"; //$NON-NLS-1$
    Table.STATE_NAMES[Table.STATE_CAPTION_CLOSED] = "captionClosed"; //$NON-NLS-1$    
    Table.STATE_NAMES[Table.STATE_HEADER_CREATED] = "headerCreated"; //$NON-NLS-1$
    Table.STATE_NAMES[Table.STATE_HEADER_BEFORE_OPEN] = "headerBeforeOpen"; //$NON-NLS-1$
    Table.STATE_NAMES[Table.STATE_HEADER_OPENED] = "headerOpened"; //$NON-NLS-1$
    Table.STATE_NAMES[Table.STATE_HEADER_CLOSED] = "headerClosed"; //$NON-NLS-1$
    Table.STATE_NAMES[Table.STATE_BODY_CREATED] = "bodyCreated"; //$NON-NLS-1$
    Table.STATE_NAMES[Table.STATE_BODY_BEFORE_OPEN] = "bodyBeforeOpen"; //$NON-NLS-1$
    Table.STATE_NAMES[Table.STATE_BODY_OPENED] = "bodyOpened"; //$NON-NLS-1$
    Table.STATE_NAMES[Table.STATE_BODY_CLOSED] = "bodyClosed"; //$NON-NLS-1$
    Table.STATE_NAMES[Table.STATE_FOOTER_CREATED] = "footerCreated"; //$NON-NLS-1$
    Table.STATE_NAMES[Table.STATE_FOOTER_BEFORE_OPEN] = "footerBeforeOpen"; //$NON-NLS-1$
    Table.STATE_NAMES[Table.STATE_FOOTER_OPENED] = "footerOpened"; //$NON-NLS-1$
    Table.STATE_NAMES[Table.STATE_FOOTER_CLOSED] = "footerClosed"; //$NON-NLS-1$
  }

  /** the total number of rows */
  int m_rowCount;

  /** the table index in the owning section */
  private final int m_index;

  /** the id of the table */
  private final String m_id;

  /** the label */
  private final Label m_label;

  /** does the table span all columns */
  private final boolean m_spansAllColumns;

  /** the table cells */
  private final ArrayListView<TableCellDef> m_cells;

  /**
   * Create a table
   * 
   * @param owner
   *          the owning FSM
   * @param index
   *          the table index in the owning section
   * @param useLabel
   *          the label to use
   * @param spansAllColumns
   *          does the table span all columns=
   * @param cells
   *          the table cell def
   */
  public Table(final SectionBody owner, final ILabel useLabel,
      final boolean spansAllColumns, final int index,
      final TableCellDef... cells) {
    super(owner, null);

    if (cells.length <= 0) {
      throw new IllegalArgumentException(//
          "A table must have at least one cell."); //$NON-NLS-1$
    }

    this.m_index = index;
    this.m_id = (owner.getOwner().m_id + this.renderTableIndex(index));

    if (useLabel != null) {
      this.m_label = this.m_doc.m_manager._getLabel(ELabelType.TABLE,
          useLabel, this.m_id);
    } else {
      this.m_label = null;
    }

    this.m_cells = new ArrayListView<>(cells);
    this.m_spansAllColumns = spansAllColumns;
  }

  /**
   * Get the label of this section
   * 
   * @return the label of this section
   */
  public final Label getLabel() {
    return this.m_label;
  }

  /**
   * Get the cell definition
   * 
   * @return the cell definition
   */
  public final ArrayListView<TableCellDef> getCellDefinition() {
    return this.m_cells;
  }

  /**
   * Does this table span all columns?
   * 
   * @return {@code true} if the table spans all columns, {@code false} if
   *         it is only one column wide
   */
  public final boolean spansAllColumns() {
    return this.m_spansAllColumns;
  }

  /**
   * Get the index of the table within the owning section (starts at
   * {@code 1})
   * 
   * @return the index of the table within the owning section
   */
  public final int getIndex() {
    return this.m_index;
  }

  /**
   * Get the index of the owning section combined with the table's index
   * 
   * @return the index of the owning section combined with the table's
   *         index
   */
  public final String getIndexes() {
    return this.m_id;
  }

  /**
   * Render the table index
   * 
   * @param index
   *          the index to render
   * @return the table index
   */
  protected String renderTableIndex(final int index) {
    return (Integer.toString(index) + '.');
  }

  /**
   * Get the number of rows
   * 
   * @return the number of rows
   */
  public final int getRowCount() {
    return this.m_rowCount;
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput sb) {
    if ((state >= Table.STATE_CAPTION_CREATED)
        && (state < Table.STATE_NAMES.length)) {
      sb.append(Table.STATE_NAMES[state]);
    } else {
      super.fsmStateAppendName(state, sb);
    }
  }

  /**
   * Get the owning section body
   * 
   * @return the owning section body
   */
  @Override
  protected SectionBody getOwner() {
    return ((SectionBody) (super.getOwner()));
  }

  /**
   * Create the table caption
   * 
   * @return the table caption
   */
  protected TableCaption createCaption() {
    return new TableCaption(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final TableCaption caption() {
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        Table.STATE_CAPTION_CREATED);
    return this.createCaption();
  }

  /**
   * Create the table header
   * 
   * @return the table header
   */
  protected TableHeader createHeader() {
    return new TableHeader(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final TableHeader header() {
    this.fsmStateAssertAndSet(Table.STATE_CAPTION_CLOSED,
        Table.STATE_HEADER_CREATED);
    return this.createHeader();
  }

  /**
   * Create the table body
   * 
   * @return the table body
   */
  protected TableBody createBody() {
    return new TableBody(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final TableBody body() {
    this.fsmStateAssertAndSet(Table.STATE_HEADER_CLOSED,
        Table.STATE_BODY_CREATED);
    return this.createBody();
  }

  /**
   * Create the table footer
   * 
   * @return the table footer
   */
  protected TableFooter createFooter() {
    return new TableFooter(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final TableFooter footer() {
    this.fsmStateAssertAndSet(Table.STATE_BODY_CLOSED,
        Table.STATE_FOOTER_CREATED);
    return this.createFooter();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (child instanceof TableCaption) {
      this.fsmStateAssertAndSet(Table.STATE_CAPTION_CREATED,
          Table.STATE_CAPTION_BEFORE_OPEN);
      return;
    }
    if (child instanceof TableHeader) {
      this.fsmStateAssertAndSet(Table.STATE_HEADER_CREATED,
          Table.STATE_HEADER_BEFORE_OPEN);
      return;
    }
    if (child instanceof TableBody) {
      this.fsmStateAssertAndSet(Table.STATE_HEADER_CREATED,
          Table.STATE_BODY_BEFORE_OPEN);
      return;
    }
    if (child instanceof TableFooter) {
      this.fsmStateAssertAndSet(Table.STATE_FOOTER_CREATED,
          Table.STATE_FOOTER_BEFORE_OPEN);
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (child instanceof TableCaption) {
      this.fsmStateAssertAndSet(Table.STATE_CAPTION_BEFORE_OPEN,
          Table.STATE_CAPTION_OPENED);
      return;
    }
    if (child instanceof TableHeader) {
      this.fsmStateAssertAndSet(Table.STATE_HEADER_BEFORE_OPEN,
          Table.STATE_HEADER_OPENED);
      return;
    }
    if (child instanceof TableBody) {
      this.fsmStateAssertAndSet(Table.STATE_BODY_BEFORE_OPEN,
          Table.STATE_BODY_OPENED);
      return;
    }
    if (child instanceof TableFooter) {
      this.fsmStateAssertAndSet(Table.STATE_FOOTER_BEFORE_OPEN,
          Table.STATE_FOOTER_OPENED);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (child instanceof TableCaption) {
      this.fsmStateAssertAndSet(Table.STATE_CAPTION_OPENED,
          Table.STATE_CAPTION_CLOSED);
      return;
    }
    if (child instanceof TableHeader) {
      this.fsmStateAssertAndSet(Table.STATE_HEADER_OPENED,
          Table.STATE_HEADER_CLOSED);
      return;
    }
    if (child instanceof TableBody) {
      this.fsmStateAssertAndSet(Table.STATE_BODY_OPENED,
          Table.STATE_BODY_CLOSED);
      return;
    }
    if (child instanceof TableFooter) {
      this.fsmStateAssertAndSet(Table.STATE_FOOTER_OPENED,
          Table.STATE_FOOTER_CLOSED);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    this.fsmStateAssertAndSet(Table.STATE_FOOTER_CLOSED,
        DocumentElement.STATE_DEAD);
    super.onClose();
  }
}
