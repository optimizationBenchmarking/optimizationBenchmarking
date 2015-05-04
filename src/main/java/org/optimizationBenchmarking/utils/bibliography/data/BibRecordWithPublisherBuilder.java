package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** A builder for bibliographic records with publishers. */
public abstract class BibRecordWithPublisherBuilder extends
    BibRecordBuilder {

  /** the publisher has been set */
  static final int FLAG_PUBLISHER_SET = (BibRecordBuilder.FLAG_RECORD_LAST << 1);

  /** the last flag of a publisher record */
  static final int FLAG_PUBLISHER_LAST = BibRecordWithPublisherBuilder.FLAG_PUBLISHER_SET;

  /** the publisher */
  BibOrganization m_publisher;

  /**
   * create the website builder
   *
   * @param owner
   *          the owner
   */
  BibRecordWithPublisherBuilder(final BuilderFSM<?> owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_PUBLISHER_SET: {
        append.append("publisherSet"); //$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /**
   * Set the publisher
   *
   * @param publisher
   *          the publisher
   */
  public synchronized final void setPublisher(
      final BibOrganization publisher) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibRecordWithPublisherBuilder.FLAG_PUBLISHER_SET),
        BibRecordWithPublisherBuilder.FLAG_PUBLISHER_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_publisher = this.normalize(publisher)) == null) {
      throw new IllegalArgumentException(//
          "Publisher  cannot be set to empty or null, but '" //$NON-NLS-1$
              + publisher + "' is."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  void _handleBeforeChildOpens(final HierarchicalFSM child) {
    if (!(child instanceof BibOrganizationBuilder)) {
      super._handleBeforeChildOpens(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  void _handleAfterChildClosed(final HierarchicalFSM child) {
    final BibOrganizationBuilder o;
    if (child instanceof BibOrganizationBuilder) {
      o = ((BibOrganizationBuilder) child);
      this._handleOrganization(o.getResult(), o.m_tag);
    } else {
      super._handleAfterChildClosed(child);
    }
  }

  /**
   * handle an organization with a given tag
   *
   * @param org
   *          the organization
   * @param tag
   *          the tag
   */
  void _handleOrganization(final BibOrganization org, final int tag) {
    if (tag == 0) {
      this.setPublisher(org);
    } else {
      throw new IllegalArgumentException("Organization with tag " + tag + //$NON-NLS-1$
          " cannot be handled."); //$NON-NLS-1$
    }
  }

  /**
   * Set publisher
   *
   * @return the publisher builder
   */
  public synchronized final BibOrganizationBuilder publisher() {
    this.fsmFlagsAssertFalse(BibRecordWithPublisherBuilder.FLAG_PUBLISHER_SET);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibOrganizationBuilder(this, 0, true);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized BibRecordWithPublisher getResult() {
    return ((BibRecordWithPublisher) (super.getResult()));
  }
}
