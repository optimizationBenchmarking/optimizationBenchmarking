package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** A builder for conference proceedings objects. */
public final class BibProceedingsBuilder extends BibBookRecordBuilder {
  /** the location has been set */
  private static final int FLAG_LOCATION_SET = (BibBookRecordBuilder.FLAG_BOOK_RECORD_LAST << 1);
  /** the end date has been set */
  private static final int FLAG_END_DATE_SET = (BibProceedingsBuilder.FLAG_LOCATION_SET << 1);

  /**
   * the location
   *
   * @serial serial field
   */
  private BibOrganization m_location;

  /**
   * the end date
   *
   * @serial serial field
   */
  private BibDate m_endDate;

  /**
   * create the book builder
   *
   * @param owner
   *          the owner
   */
  BibProceedingsBuilder(final BuilderFSM<?> owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_LOCATION_SET: {
        append.append("locationSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_END_DATE_SET: {
        append.append("endDateSet"); //$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /**
   * Build the start date
   *
   * @return the builder for the start date
   */
  public final BibDateBuilder startDate() {
    return super.date();
  }

  /**
   * Set the start date
   *
   * @param date
   *          the start date
   */
  public final void setStartDate(final BibDate date) {
    super.setDate(date);
  }

  /** {@inheritDoc} */
  @Override
  final void _handleDate(final BibDate date, final int tag) {
    if (tag == 1) {
      this.setEndDate(date);
    } else {
      super._handleDate(date, tag);
    }
  }

  /**
   * Create the end date builder
   *
   * @return the end date builder
   */
  public synchronized final BibDateBuilder endDate() {
    this.fsmFlagsAssertFalse(BibProceedingsBuilder.FLAG_END_DATE_SET);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibDateBuilder(this, 1);
  }

  /**
   * Set the end date
   *
   * @param endDate
   *          the date
   */
  public synchronized final void setEndDate(final BibDate endDate) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibProceedingsBuilder.FLAG_END_DATE_SET),
        BibProceedingsBuilder.FLAG_END_DATE_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_endDate = this.normalize(endDate)) == null) {
      throw new IllegalArgumentException("Cannot set null end date."); //$NON-NLS-1$
    }
  }

  /**
   * Set the location
   *
   * @param location
   *          the location
   */
  public synchronized final void setLocation(final BibOrganization location) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibProceedingsBuilder.FLAG_LOCATION_SET),
        BibProceedingsBuilder.FLAG_LOCATION_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_location = this.normalize(location)) == null) {
      throw new IllegalArgumentException(//
          "Location cannot be empty or null, but '" //$NON-NLS-1$
              + location + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the location
   *
   * @return the location builder
   */
  public synchronized final BibOrganizationBuilder location() {
    this.fsmFlagsAssertFalse(BibProceedingsBuilder.FLAG_LOCATION_SET);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibOrganizationBuilder(this, 1, false);
  }

  /** {@inheritDoc} */
  @Override
  final void _handleOrganization(final BibOrganization org, final int tag) {
    if (tag == 1) {
      this.setLocation(org);
    } else {
      super._handleOrganization(org, tag);
    }
  }

  /** {@inheritDoc} */
  @Override
  final BibProceedings _doCompile() {

    this.fsmFlagsAssertTrue(BibRecordBuilder.FLAG_TITLE_SET
        | BibRecordBuilder.FLAG_DATE_SET
        | BibRecordWithPublisherBuilder.FLAG_PUBLISHER_SET
        | BibProceedingsBuilder.FLAG_LOCATION_SET);

    return new BibProceedings(true, this.m_title, this.m_date,
        this.m_endDate, this.m_editors, this.m_location, this.m_publisher,
        this.m_series, this.m_issn, this.m_volume, this.m_isbn,
        this.m_url, this.m_doi);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final BibProceedings getResult() {
    return ((BibProceedings) (super.getResult()));
  }
}
