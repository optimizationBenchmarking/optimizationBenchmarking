package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.ICode;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * The base class for tables
 */
public class Code extends ComplexObject implements ICode {

  /** the state when the caption has been created */
  private static final int STATE_CAPTION_CREATED = (DocumentElement.STATE_MAX_ELEMENT + 1);
  /** the state before the caption */
  private static final int STATE_CAPTION_BEFORE_OPEN = (Code.STATE_CAPTION_CREATED + 1);
  /** the state in the caption */
  private static final int STATE_CAPTION_OPENED = (Code.STATE_CAPTION_BEFORE_OPEN + 1);
  /** the state after the caption */
  private static final int STATE_CAPTION_CLOSED = (Code.STATE_CAPTION_OPENED + 1);

  /** the state when the body has been created */
  private static final int STATE_BODY_CREATED = (Code.STATE_CAPTION_CLOSED + 1);
  /** the state before the body */
  private static final int STATE_BODY_BEFORE_OPEN = (Code.STATE_BODY_CREATED + 1);
  /** the state in the body */
  private static final int STATE_BODY_OPENED = (Code.STATE_BODY_BEFORE_OPEN + 1);
  /** the state after the body */
  private static final int STATE_BODY_CLOSED = (Code.STATE_BODY_OPENED + 1);

  /** the state names */
  private static final String[] STATE_NAMES;

  static {
    STATE_NAMES = new String[Code.STATE_BODY_CLOSED + 1];

    Code.STATE_NAMES[Code.STATE_CAPTION_CREATED] = "captionCreated"; //$NON-NLS-1$
    Code.STATE_NAMES[Code.STATE_CAPTION_BEFORE_OPEN] = "captionBeforeOpen"; //$NON-NLS-1$
    Code.STATE_NAMES[Code.STATE_CAPTION_OPENED] = "captionOpened"; //$NON-NLS-1$
    Code.STATE_NAMES[Code.STATE_CAPTION_CLOSED] = "captionClosed"; //$NON-NLS-1$
    Code.STATE_NAMES[Code.STATE_BODY_CREATED] = "bodyCreated"; //$NON-NLS-1$
    Code.STATE_NAMES[Code.STATE_BODY_BEFORE_OPEN] = "bodyBeforeOpen"; //$NON-NLS-1$
    Code.STATE_NAMES[Code.STATE_BODY_OPENED] = "bodyOpened"; //$NON-NLS-1$
    Code.STATE_NAMES[Code.STATE_BODY_CLOSED] = "bodyClosed"; //$NON-NLS-1$
  }

  /** does the code block span all columns */
  private final boolean m_spansAllColumns;

  /**
   * Create a code
   *
   * @param owner
   *          the owning section body
   * @param index
   *          the table index in the owning section
   * @param useLabel
   *          the label to use
   * @param spansAllColumns
   *          does the table span all columns
   */
  protected Code(final SectionBody owner, final ILabel useLabel,
      final boolean spansAllColumns, final int index) {
    super(owner, useLabel, index);

    this.m_spansAllColumns = spansAllColumns;
  }

  /** {@inheritDoc} */
  @Override
  final ELabelType _labelType() {
    return ELabelType.CODE;
  }

  /**
   * Does this code block span all columns?
   *
   * @return {@code true} if the code block spans all columns,
   *         {@code false} if it is only one column wide
   */
  public final boolean spansAllColumns() {
    return this.m_spansAllColumns;
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
  public synchronized final CodeCaption caption() {
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        Code.STATE_CAPTION_CREATED);
    return this.m_driver.createCodeCaption(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final CodeBody body() {
    this.fsmStateAssertAndSet(Code.STATE_CAPTION_CLOSED,
        Code.STATE_BODY_CREATED);
    return this.m_driver.createCodeBody(this);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (child instanceof CodeCaption) {
      this.fsmStateAssertAndSet(Code.STATE_CAPTION_CREATED,
          Code.STATE_CAPTION_BEFORE_OPEN);
      return;
    }
    if (child instanceof CodeBody) {
      this.fsmStateAssertAndSet(Code.STATE_BODY_CREATED,
          Code.STATE_BODY_BEFORE_OPEN);
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (child instanceof CodeCaption) {
      this.fsmStateAssertAndSet(Code.STATE_CAPTION_BEFORE_OPEN,
          Code.STATE_CAPTION_OPENED);
      return;
    }
    if (child instanceof CodeBody) {
      this.fsmStateAssertAndSet(Code.STATE_BODY_BEFORE_OPEN,
          Code.STATE_BODY_OPENED);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (child instanceof CodeCaption) {
      this.fsmStateAssertAndSet(Code.STATE_CAPTION_OPENED,
          Code.STATE_CAPTION_CLOSED);
      return;
    }
    if (child instanceof CodeBody) {
      this.fsmStateAssertAndSet(Code.STATE_BODY_OPENED,
          Code.STATE_BODY_CLOSED);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    this.fsmStateAssertAndSet(Code.STATE_BODY_CLOSED,
        DocumentElement.STATE_DEAD);
    super.onClose();
  }
}
