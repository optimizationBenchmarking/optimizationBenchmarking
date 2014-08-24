package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** A builder for a bibliographic record for a technical report. */
public final class BibTechReportBuilder extends
    BibRecordWithPublisherBuilder {

  /** the series has been set */
  private static final int FLAG_SERIES_SET = (BibRecordWithPublisherBuilder.FLAG_PUBLISHER_LAST << 1);
  /** the number has been set */
  private static final int FLAG_NUMBER_SET = (BibTechReportBuilder.FLAG_SERIES_SET << 1);
  /** the issn has been set */
  private static final int FLAG_ISSN_SET = (BibTechReportBuilder.FLAG_NUMBER_SET << 1);

  /** the series */
  private String m_series;
  /** the number */
  private String m_number;
  /** the issn */
  private String m_issn;

  /** create */
  public BibTechReportBuilder() {
    this(null);
  }

  /**
   * create the article builder
   *
   * @param owner
   *          the owner
   */
  BibTechReportBuilder(final HierarchicalFSM owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_SERIES_SET: {
        append.append("seriesSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_NUMBER_SET: {
        append.append("numberSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_ISSN_SET: {
        append.append("issnSet"); //$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized BibDateBuilder setDate() {
    return super.setDate();
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized void setDate(final BibDate date) {
    super.setDate(date);
  }

  /**
   * Set the series
   *
   * @param series
   *          the volume
   */
  public synchronized final void setSeries(final String series) {
    this.fsmFlagsAssertAndUpdate(
        FSM.FLAG_NOTHING,
        (BibTechReportBuilder.FLAG_SERIES_SET | _BibBuilder.FLAG_FINALIZED),
        BibTechReportBuilder.FLAG_SERIES_SET, FSM.FLAG_NOTHING);
    if ((this.m_series = this.normalize(series)) == null) {
      throw new IllegalArgumentException(//
          "Series cannot be set to empty or null, but '" //$NON-NLS-1$
              + series + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the number
   *
   * @param number
   *          the number
   */
  public synchronized final void setNumber(final String number) {
    this.fsmFlagsAssertAndUpdate(
        FSM.FLAG_NOTHING,
        (BibTechReportBuilder.FLAG_NUMBER_SET | _BibBuilder.FLAG_FINALIZED),
        BibTechReportBuilder.FLAG_NUMBER_SET, FSM.FLAG_NOTHING);
    if ((this.m_number = this.normalize(number)) == null) {
      throw new IllegalArgumentException(//
          "Number cannot be set to empty or null, but '" //$NON-NLS-1$
              + number + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the issn
   *
   * @param issn
   *          the issn
   */
  public synchronized final void setISSN(final String issn) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibTechReportBuilder.FLAG_ISSN_SET | _BibBuilder.FLAG_FINALIZED),
        BibTechReportBuilder.FLAG_ISSN_SET, FSM.FLAG_NOTHING);
    if ((this.m_issn = this.normalize(issn)) == null) {
      throw new IllegalArgumentException(//
          "ISSN cannot be set to empty or null, but '" //$NON-NLS-1$
              + issn + "' is."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized BibTechReport getResult() {
    return ((BibTechReport) (super.getResult()));
  }

  /** {@inheritDoc} */
  @Override
  final BibTechReport _compile() {
    return new BibTechReport(true, this.m_authors, this.m_title,
        this.m_date, this.m_series, this.m_number, this.m_issn,
        this.m_publisher, this.m_url, this.m_doi);
  }
}
