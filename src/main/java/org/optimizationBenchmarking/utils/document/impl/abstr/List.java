package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IList;
import org.optimizationBenchmarking.utils.document.spec.IStructuredText;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for lists
 *
 * @param <IT>
 *          the item type
 */
public abstract class List<IT extends ListItem> extends DocumentPart
    implements IList {

  /** does this list have an item? */
  private static final int FLAG_HAS_ITEM = (FSM.FLAG_NOTHING + 1);

  /** the nesting depths of all lists in this document */
  private final int m_listDepth;

  /** the item index */
  int m_index;

  /**
   * Create a new enumeration
   *
   * @param owner
   *          the owning text
   */
  @SuppressWarnings({ "rawtypes", "resource" })
  protected List(final StructuredText owner) {
    super(owner);

    HierarchicalFSM o;
    int add;

    add = 0;
    outer: {
      inner: {
        for (o = owner; o != null;) {
          if (o instanceof List) {
            add += (1 + (((List) o).m_listDepth));
            break outer;
          }
          if (o instanceof IList) {
            add++;
          }

          if (o instanceof DocumentElement) {
            o = ((DocumentElement) o)._owner();
          } else {
            break inner;
          }
        }
      }
    }

    this.m_listDepth = add;
  }

  /**
   * Get the owning structured text
   *
   * @return the owning structured text
   */
  @Override
  protected StructuredText getOwner() {
    return ((StructuredText) (super.getOwner()));
  }

  /**
   * Obtain the number of lists into which this list is nested. These could
   * either be
   * {@link org.optimizationBenchmarking.utils.document.spec.IStructuredText#enumeration()
   * enumerations} or
   * {@link org.optimizationBenchmarking.utils.document.spec.IStructuredText#itemization()
   * itemizations}.
   *
   * @return the number of "outer" lists into which this list is embedded,
   *         or {@code 0} if this is a top-level list
   */
  public final int getListDepth() {
    return this.m_listDepth;
  }

  /**
   * Create an item
   *
   * @return the item
   */
  abstract IT createItem();

  /** {@inheritDoc} */
  @Override
  public synchronized final IStructuredText item() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    this.m_index++;
    return this.createItem();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (child instanceof ListItem) {
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (child instanceof ListItem) {
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (child instanceof ListItem) {
      this.fsmFlagsSet(List.FLAG_HAS_ITEM);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    this.fsmStateAssert(List.FLAG_HAS_ITEM);
    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    if (flagValue == List.FLAG_HAS_ITEM) {
      append.append("hasItem"); //$NON-NLS-1$
    } else {
      super.fsmFlagsAppendName(flagValue, flagIndex, append);
    }
  }
}
