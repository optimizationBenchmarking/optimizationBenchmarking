package test.junit.org.optimizationBenchmarking.utils.hierarchy;

import org.junit.Assert;
import org.junit.Ignore;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A dummy hierarchy element.
 */
@Ignore
class _HFTDummyHierarchicalFSM extends HierarchicalFSM {

  /** open */
  private static final int FLAG_OPEN = 1;
  /** closed */
  private static final int FLAG_CLOSED = (_HFTDummyHierarchicalFSM.FLAG_OPEN << 1);
  /** before open */
  private static final int FLAG_CHILD_BEFORE_OPEN = (_HFTDummyHierarchicalFSM.FLAG_CLOSED << 1);
  /** after open */
  private static final int FLAG_CHILD_AFTER_OPEN = (_HFTDummyHierarchicalFSM.FLAG_CHILD_BEFORE_OPEN << 1);
  /** closed */
  private static final int FLAG_CHILD_CLOSED = (_HFTDummyHierarchicalFSM.FLAG_CHILD_AFTER_OPEN << 1);

  /** are we opened? */
  private volatile boolean m_isOpened;

  /** are we closed? */
  private volatile boolean m_isClosed;

  /** how many children have passed the beforeChildOpens */
  private volatile int m_beforeChildOpens;

  /** how many children have passed the afterChildOpens */
  private volatile int m_afterChildOpened;

  /** how many children have passed the afterChildClosed */
  private volatile int m_afterChildClosed;

  /** the id */
  private volatile int m_id;

  /** the last id of a child that was closed */
  private volatile int m_lastOnClosed;

  /** the last id of a child that was opened */
  private volatile int m_lastOnOpens;

  /**
   * create
   *
   * @param owner
   *          the owner
   * @param doOpen
   *          should we open?
   */
  _HFTDummyHierarchicalFSM(final _HFTDummyHierarchicalFSM owner,
      final boolean doOpen) {
    super(owner);
    this.m_id = (-1);
    this.m_lastOnClosed = (0);
    this.m_lastOnOpens = (0);
    if (doOpen) {
      this.open();
    }
  }

  /**
   * create
   *
   * @param owner
   *          the owner
   */
  public _HFTDummyHierarchicalFSM(final _HFTDummyHierarchicalFSM owner) {
    this(owner, true);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onOpen() {

    this.fsmFlagsAssertFalse(_HFTDummyHierarchicalFSM.FLAG_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CLOSED
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_BEFORE_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_AFTER_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_CLOSED);
    this.fsmFlagsSet(_HFTDummyHierarchicalFSM.FLAG_OPEN);

    Assert.assertFalse(this.m_isOpened);
    Assert.assertFalse(this.m_isClosed);
    Assert.assertEquals(this.m_beforeChildOpens, 0);
    Assert.assertEquals(this.m_afterChildOpened, 0);
    Assert.assertEquals(this.m_afterChildClosed, 0);
    Assert.assertTrue((this.getOwner() != null) ^ (this.m_id == (-1)));

    super.onOpen();

    this.m_isOpened = true;
    Assert.assertTrue(this.m_isOpened);
    Assert.assertFalse(this.m_isClosed);
    Assert.assertEquals(this.m_beforeChildOpens, 0);
    Assert.assertEquals(this.m_afterChildOpened, 0);
    Assert.assertEquals(this.m_afterChildClosed, 0);
    Assert.assertTrue((this.getOwner() != null) ^ (this.m_id == (-1)));

    this.fsmFlagsAssertFalse(_HFTDummyHierarchicalFSM.FLAG_CLOSED
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_BEFORE_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_AFTER_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_CLOSED);
    this.fsmFlagsAssertTrue(_HFTDummyHierarchicalFSM.FLAG_OPEN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    this.fsmFlagsAssertFalse(_HFTDummyHierarchicalFSM.FLAG_CLOSED);
    this.fsmFlagsAssertTrue(_HFTDummyHierarchicalFSM.FLAG_OPEN);
    this.fsmFlagsSet(_HFTDummyHierarchicalFSM.FLAG_CHILD_BEFORE_OPEN);

    Assert.assertTrue(this.m_isOpened);
    Assert.assertFalse(this.m_isClosed);
    Assert
        .assertTrue(this.m_beforeChildOpens >= (hasOtherChildren ? 1 : 0));
    Assert.assertTrue(this.m_afterChildOpened <= this.m_beforeChildOpens);
    Assert.assertTrue(this.m_afterChildClosed <= this.m_afterChildOpened);
    Assert.assertTrue((((_HFTDummyHierarchicalFSM) child).m_id) == (-1));

    super.beforeChildOpens(child, hasOtherChildren);

    this.m_lastOnOpens = ((((_HFTDummyHierarchicalFSM) child).m_id) = (this.m_beforeChildOpens++));

    Assert.assertTrue(this.m_isOpened);
    Assert.assertFalse(this.m_isClosed);
    Assert
        .assertTrue(this.m_beforeChildOpens > (hasOtherChildren ? 1 : 0));
    Assert.assertTrue(this.m_afterChildOpened < this.m_beforeChildOpens);
    Assert.assertTrue(this.m_afterChildClosed <= this.m_afterChildOpened);
    Assert.assertTrue((((_HFTDummyHierarchicalFSM) child).m_id) >= 0);

    this.fsmFlagsAssertFalse(_HFTDummyHierarchicalFSM.FLAG_CLOSED);
    this.fsmFlagsAssertTrue(_HFTDummyHierarchicalFSM.FLAG_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_BEFORE_OPEN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    this.fsmFlagsAssertFalse(_HFTDummyHierarchicalFSM.FLAG_CLOSED);
    this.fsmFlagsAssertTrue(_HFTDummyHierarchicalFSM.FLAG_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_BEFORE_OPEN);
    this.fsmFlagsSet(_HFTDummyHierarchicalFSM.FLAG_CHILD_AFTER_OPEN);

    Assert.assertTrue(this.m_isOpened);
    Assert.assertFalse(this.m_isClosed);
    Assert
        .assertTrue(this.m_beforeChildOpens > (hasOtherChildren ? 1 : 0));
    Assert.assertTrue(this.m_afterChildOpened < this.m_beforeChildOpens);
    Assert.assertTrue(this.m_afterChildClosed <= this.m_afterChildOpened);
    Assert
        .assertTrue(this.m_afterChildOpened >= (hasOtherChildren ? 1 : 0));
    super.afterChildOpened(child, hasOtherChildren);
    this.m_afterChildOpened++;

    Assert.assertEquals(this.m_lastOnOpens,
        (((_HFTDummyHierarchicalFSM) child).m_id));

    Assert.assertTrue(this.m_isOpened);
    Assert.assertFalse(this.m_isClosed);
    Assert
        .assertTrue(this.m_beforeChildOpens > (hasOtherChildren ? 1 : 0));
    Assert
        .assertTrue(this.m_afterChildOpened >= (hasOtherChildren ? 2 : 1));
    Assert.assertTrue(this.m_afterChildOpened <= this.m_beforeChildOpens);
    Assert.assertTrue(this.m_afterChildClosed < this.m_afterChildOpened);

    this.fsmFlagsAssertFalse(_HFTDummyHierarchicalFSM.FLAG_CLOSED);
    this.fsmFlagsAssertTrue(_HFTDummyHierarchicalFSM.FLAG_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_BEFORE_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_AFTER_OPEN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void afterChildClosed(final HierarchicalFSM child) {
    this.fsmFlagsAssertFalse(_HFTDummyHierarchicalFSM.FLAG_CLOSED);
    this.fsmFlagsAssertTrue(_HFTDummyHierarchicalFSM.FLAG_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_BEFORE_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_AFTER_OPEN);
    this.fsmFlagsSet(_HFTDummyHierarchicalFSM.FLAG_CHILD_CLOSED);

    Assert.assertTrue(this.m_isOpened);
    Assert.assertTrue(this.m_beforeChildOpens > 0);
    Assert.assertTrue(this.m_afterChildOpened <= this.m_beforeChildOpens);
    Assert.assertTrue(this.m_afterChildClosed < this.m_afterChildOpened);
    super.afterChildClosed(child);

    Assert.assertEquals((this.m_lastOnClosed++),
        (((_HFTDummyHierarchicalFSM) child).m_id));

    this.m_afterChildClosed++;
    Assert.assertTrue(this.m_isOpened);
    Assert.assertTrue(this.m_beforeChildOpens > 0);
    Assert.assertTrue(this.m_afterChildOpened <= this.m_beforeChildOpens);
    Assert.assertTrue(this.m_afterChildClosed <= this.m_afterChildOpened);

    this.fsmFlagsAssertFalse(_HFTDummyHierarchicalFSM.FLAG_CLOSED);
    this.fsmFlagsAssertTrue(_HFTDummyHierarchicalFSM.FLAG_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_BEFORE_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_AFTER_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CHILD_CLOSED);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {

    this.fsmFlagsAssertFalse(_HFTDummyHierarchicalFSM.FLAG_CLOSED);
    this.fsmFlagsAssertTrue(_HFTDummyHierarchicalFSM.FLAG_OPEN);
    this.fsmFlagsSet(_HFTDummyHierarchicalFSM.FLAG_CLOSED);

    Assert.assertTrue(this.m_isOpened);
    Assert.assertFalse(this.m_isClosed);
    Assert.assertTrue(this.m_beforeChildOpens >= 0);
    Assert.assertEquals(this.m_afterChildOpened, this.m_beforeChildOpens);
    Assert.assertEquals(this.m_afterChildClosed, this.m_afterChildOpened);
    super.onClose();
    this.m_isClosed = true;
    Assert.assertTrue(this.m_isOpened);
    Assert.assertTrue(this.m_isClosed);
    Assert.assertTrue(this.m_beforeChildOpens >= 0);
    Assert.assertEquals(this.m_afterChildOpened, this.m_beforeChildOpens);
    Assert.assertEquals(this.m_afterChildClosed, this.m_afterChildOpened);

    this.fsmFlagsAssertTrue(_HFTDummyHierarchicalFSM.FLAG_OPEN
        | _HFTDummyHierarchicalFSM.FLAG_CLOSED);
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_OPEN: {
        append.append("open"); //$NON-NLS-1$
        break;
      }
      case FLAG_CLOSED: {
        append.append("close"); //$NON-NLS-1$
        break;
      }
      case FLAG_CHILD_BEFORE_OPEN: {
        append.append("childBeforeOpen"); //$NON-NLS-1$
        break;
      }
      case FLAG_CHILD_AFTER_OPEN: {
        append.append("childAfterOpen"); //$NON-NLS-1$
        break;
      }
      case FLAG_CHILD_CLOSED: {
        append.append("childClosed"); //$NON-NLS-1$
        break;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }
}
