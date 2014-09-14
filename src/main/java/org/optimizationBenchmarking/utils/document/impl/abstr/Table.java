package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.collections.iterators.ArrayIterator;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ITable;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for tables
 */
public class Table extends ComplexObject implements ITable,
    Iterable<TableCellDef> {

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

  /** does the table span all columns */
  private final boolean m_spansAllColumns;

  /** the table cells */
  final TableCellDef[] m_cells;

  /** the blocked columns */
  final int[] m_blocked;

  /** the links between cell index and definitions */
  final int[] m_cellToDef;

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
  public Table(final SectionBody owner, final ILabel useLabel,
      final boolean spansAllColumns, final int index,
      final TableCellDef[] definition) {
    super(owner, useLabel, index);

    final TableCellDef[] cells;
    int i, cc;

    cells = definition.clone();
    cc = Table._checkDef(cells);

    this.m_cells = cells;
    this.m_spansAllColumns = spansAllColumns;

    this.m_blocked = new int[cc];
    this.m_cellToDef = new int[cc];

    i = cc = 0;
    for (final TableCellDef d : cells) {
      if (d != TableCellDef.VERTICAL_SEPARATOR) {
        this.m_cellToDef[i++] = cc;
      }
      cc++;
    }
  }

  /** {@inheritDoc} */
  @Override
  final ELabelType _labelType() {
    return ELabelType.TABLE;
  }

  /**
   * Check if the table definition is OK
   * 
   * @param def
   *          the table definition
   * @return the number of cells
   */
  static final int _checkDef(final TableCellDef[] def) {
    int cells;

    if (def == null) {
      throw new IllegalArgumentException(//
          "Table cell definition must not be null."); //$NON-NLS-1$
    }

    cells = 0;
    for (final TableCellDef d : def) {
      if (d == null) {
        throw new IllegalArgumentException(//
            "No cell definition element can be null, but one of " + //$NON-NLS-1$
                def + " is."); //$NON-NLS-1$
      }
      if (d != TableCellDef.VERTICAL_SEPARATOR) {
        cells++;
      }
    }
    if (cells <= 0) {
      throw new IllegalArgumentException(//
          "Table definition cannot be empty, i.e., must contain at least one regular cell - but " //$NON-NLS-1$
              + def + " does not."); //$NON-NLS-1$
    }

    return cells;
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
   * Get the current number of rows
   * 
   * @return the current number of rows
   */
  public final int getRowCount() {
    return this.m_rowCount;
  }

  /**
   * Obtain the cells per row
   * 
   * @return the cells per row
   */
  public final int getCellsPerRow() {
    return this.m_blocked.length;
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

  /** {@inheritDoc} */
  @Override
  public synchronized final TableCaption caption() {
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        Table.STATE_CAPTION_CREATED);
    return this.m_driver.createTableCaption(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final TableHeader header() {
    this.fsmStateAssertAndSet(Table.STATE_CAPTION_CLOSED,
        Table.STATE_HEADER_CREATED);
    return this.m_driver.createTableHeader(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final TableBody body() {
    this.fsmStateAssertAndSet(Table.STATE_HEADER_CLOSED,
        Table.STATE_BODY_CREATED);
    return this.m_driver.createTableBody(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final TableFooter footer() {
    this.fsmStateAssertAndSet(Table.STATE_BODY_CLOSED,
        Table.STATE_FOOTER_CREATED);
    return this.m_driver.createTableFooter(this);
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

  /**
   * Get the cell definition
   * 
   * @return the cell definition
   */
  @Override
  public final ArrayIterator<TableCellDef> iterator() {
    return new ArrayIterator<>(this.m_cells);
  }
}
