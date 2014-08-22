package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for sections
 */
public class Section extends _StyledElement implements ISection {
  /** the state when the header has been created */
  private static final int STATE_TITLE_CREATED = (DocumentElement.STATE_MAX_ELEMENT + 1);
  /** the state before the header */
  private static final int STATE_TITLE_BEFORE_OPEN = (Section.STATE_TITLE_CREATED + 1);
  /** the state in the header */
  private static final int STATE_TITLE_OPENED = (Section.STATE_TITLE_BEFORE_OPEN + 1);
  /** the state after the header */
  private static final int STATE_TITLE_CLOSED = (Section.STATE_TITLE_OPENED + 1);
  /** the state when the body has been created */
  private static final int STATE_BODY_CREATED = (Section.STATE_TITLE_CLOSED + 1);
  /** the state before the body */
  private static final int STATE_BODY_BEFORE_OPEN = (Section.STATE_BODY_CREATED + 1);
  /** the state in the body */
  private static final int STATE_BODY_OPENED = (Section.STATE_BODY_BEFORE_OPEN + 1);
  /** the state after the body */
  private static final int STATE_BODY_CLOSED = (Section.STATE_BODY_OPENED + 1);
  /** the state names */
  private static final String[] STATE_NAMES;

  static {
    STATE_NAMES = new String[Section.STATE_BODY_CLOSED + 1];
    Section.STATE_NAMES[Section.STATE_TITLE_CREATED] = "titleCreated"; //$NON-NLS-1$
    Section.STATE_NAMES[Section.STATE_TITLE_BEFORE_OPEN] = "titleBeforeOpen"; //$NON-NLS-1$
    Section.STATE_NAMES[Section.STATE_TITLE_OPENED] = "titleOpened"; //$NON-NLS-1$
    Section.STATE_NAMES[Section.STATE_TITLE_CLOSED] = "titleClosed"; //$NON-NLS-1$
    Section.STATE_NAMES[Section.STATE_BODY_CREATED] = "bodyCreated"; //$NON-NLS-1$
    Section.STATE_NAMES[Section.STATE_BODY_BEFORE_OPEN] = "bodyBeforeOpen"; //$NON-NLS-1$
    Section.STATE_NAMES[Section.STATE_BODY_OPENED] = "bodyOpened"; //$NON-NLS-1$
    Section.STATE_NAMES[Section.STATE_BODY_CLOSED] = "bodyClosed"; //$NON-NLS-1$
  }

  /** the index of the section inside the owning section (starts at 1) */
  private final int m_index;

  /** is this an appendix section? */
  private final boolean m_isAppendix;

  /** the depth of this section */
  private final int m_depth;

  /** the label */
  private final Label m_label;

  /** the section id */
  final String m_id;

  /**
   * Create a new enumeration
   * 
   * @param owner
   *          the owning text
   * @param useLabel
   *          the label to use
   * @param index
   *          the section index
   */
  @SuppressWarnings("resource")
  protected Section(final DocumentPart owner, final ILabel useLabel,
      final int index) {
    super(owner);

    final Section o;

    if (index <= 0) {
      throw new IllegalArgumentException(//
          "Section index must be greater or equal to 1."); //$NON-NLS-1$
    }
    this.m_index = index;

    if (owner instanceof DocumentFooter) {
      this.m_isAppendix = true;
      this.m_depth = 0;
      o = null;
    } else {
      if (owner instanceof DocumentBody) {
        this.m_isAppendix = false;
        this.m_depth = 0;
        o = null;
      } else {
        if (owner instanceof SectionBody) {
          o = ((Section) (owner._owner()));
          this.m_isAppendix = o.m_isAppendix;
          this.m_depth = (o.m_depth + 1);
        } else {
          throw new IllegalArgumentException(owner + //
              " is not a valid container for a section.");//$NON-NLS-1$
        }
      }
    }

    if (o != null) {
      this.m_id = (o.m_id + this.renderSectionIndex(index));
    } else {
      this.m_id = this.renderSectionIndex(index);
    }

    if (useLabel != null) {
      this.m_label = this.m_doc.m_manager._getLabel(ELabelType.SECTION,
          useLabel, this.m_id);
    } else {
      this.m_label = null;
    }
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
   * Get the section id, i.e., a string holding all indexes of this and the
   * owning sections
   * 
   * @return the section id
   */
  public final String getIndexes() {
    return this.m_id;
  }

  /**
   * Obtain the section index
   * 
   * @return the section index
   */
  public final int getIndex() {
    return this.m_index;
  }

  /**
   * Is this an appendix section?
   * 
   * @return {@code true} if and only if this section is part of the
   *         {@link org.optimizationBenchmarking.utils.document.impl.abstr.DocumentFooter}
   */
  public final boolean isAppendix() {
    return this.m_isAppendix;
  }

  /**
   * Obtain the number of sections in the hierarchy above this one, or
   * {@code 0} if this is a top-level section.
   * 
   * @return {@code 0} if this is a top-level section or the number of
   *         sections above it in the hierarchy otherwise
   */
  public final int getDepth() {
    return this.m_depth;
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput sb) {
    if ((state >= Section.STATE_TITLE_CREATED)
        && (state < Section.STATE_NAMES.length)) {
      sb.append(Section.STATE_NAMES[state]);
    } else {
      super.fsmStateAppendName(state, sb);
    }
  }

  /**
   * Render this section's index only.
   * 
   * @param index
   *          the index to render
   * @return the section index string
   */
  protected String renderSectionIndex(final int index) {
    String s;

    s = Integer.toString(index);
    if ((this.m_isAppendix) && (this.m_depth <= 0)) {
      return ('A' + s + '.');
    }
    return (s + '.');
  }

  /**
   * Create the section title
   * 
   * @return the section title
   */
  protected SectionTitle createSectionTitle() {
    return new SectionTitle(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final SectionTitle title() {
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        Section.STATE_TITLE_CREATED);
    return this.createSectionTitle();
  }

  /**
   * Create the section body
   * 
   * @return the section body
   */
  protected SectionBody createSectionBody() {
    return new SectionBody(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final SectionBody body() {
    this.fsmStateAssertAndSet(Section.STATE_TITLE_CLOSED,
        Section.STATE_BODY_CREATED);
    return this.createSectionBody();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (child instanceof SectionTitle) {
      this.fsmStateAssertAndSet(Section.STATE_TITLE_CREATED,
          Section.STATE_TITLE_BEFORE_OPEN);
      return;
    }
    if (child instanceof SectionBody) {
      this.fsmStateAssertAndSet(Section.STATE_BODY_CREATED,
          Section.STATE_BODY_BEFORE_OPEN);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (child instanceof SectionTitle) {
      this.fsmStateAssertAndSet(Section.STATE_TITLE_BEFORE_OPEN,
          Section.STATE_TITLE_OPENED);
      return;
    }
    if (child instanceof SectionBody) {
      this.fsmStateAssertAndSet(Section.STATE_BODY_BEFORE_OPEN,
          Section.STATE_BODY_OPENED);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (child instanceof SectionTitle) {
      this.fsmStateAssertAndSet(Section.STATE_TITLE_OPENED,
          Section.STATE_TITLE_CLOSED);
      return;
    }
    if (child instanceof DocumentBody) {
      this.fsmStateAssertAndSet(Section.STATE_BODY_OPENED,
          Section.STATE_BODY_CLOSED);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    this.fsmStateAssertAndSet(Section.STATE_BODY_CLOSED,
        DocumentElement.STATE_DEAD);
    super.onClose();
  }

}
