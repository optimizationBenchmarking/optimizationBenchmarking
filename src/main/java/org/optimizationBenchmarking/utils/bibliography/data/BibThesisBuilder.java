package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.parsers.StringParser;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** A builder for thesis records */
public final class BibThesisBuilder extends BibBookBuilder {

  /** the type has been set */
  private static final int FLAG_TYPE_SET = (BibBookRecordBuilder.FLAG_BOOK_RECORD_LAST << 1);
  /** the school has been set */
  private static final int FLAG_SCHOOL_SET = (BibThesisBuilder.FLAG_TYPE_SET << 1);

  /** the thesis type */
  private EThesisType m_type;

  /** the school */
  private BibOrganization m_school;

  /** create the thesis builder */
  public BibThesisBuilder() {
    this(null);
  }

  /**
   * create the thesis builder
   * 
   * @param owner
   *          the owner
   */
  BibThesisBuilder(final HierarchicalFSM owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_TYPE_SET: {
        append.append("typeSet");//$NON-NLS-1$
        return;
      }
      case FLAG_SCHOOL_SET: {
        append.append("schoolSet");//$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /**
   * Set the school
   * 
   * @param school
   *          the school
   */
  public synchronized final void setSchool(final BibOrganization school) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibThesisBuilder.FLAG_SCHOOL_SET | _BibBuilder.FLAG_FINALIZED),
        BibThesisBuilder.FLAG_SCHOOL_SET, FSM.FLAG_NOTHING);
    if ((this.m_school = this.normalize(school)) == null) {
      throw new IllegalArgumentException(//
          "School  cannot be set to empty or null, but '" //$NON-NLS-1$
              + school + "' is.");//$NON-NLS-1$
    }
  }

  /**
   * Set the school
   * 
   * @return the schoolbuilder
   */
  public synchronized final BibOrganizationBuilder setSchool() {
    this.fsmFlagsAssertFalse(BibThesisBuilder.FLAG_SCHOOL_SET
        | _BibBuilder.FLAG_FINALIZED);
    return new BibOrganizationBuilder(this, 1, true);
  }

  /** {@inheritDoc} */
  @Override
  final void _handleOrganization(final BibOrganization org, final int tag) {
    if (tag == 1) {
      this.setSchool(org);
    } else {
      super._handleOrganization(org, tag);
    }
  }

  /**
   * Set the thesis type
   * 
   * @param type
   *          the type
   */
  public synchronized final void setType(final EThesisType type) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibThesisBuilder.FLAG_TYPE_SET | _BibBuilder.FLAG_FINALIZED),
        BibThesisBuilder.FLAG_TYPE_SET, FSM.FLAG_NOTHING);
    if ((this.m_type = type) == null) {
      throw new IllegalArgumentException(//
          "Thesis type cannot be set to null.");//$NON-NLS-1$
    }
  }

  /**
   * Set the type
   * 
   * @param type
   *          the type
   */
  public final void setType(final String type) {
    final String m;
    EThesisType res;

    m = StringParser.INSTANCE.parseString(type);
    if (m == null) {
      throw new IllegalArgumentException(//
          "Type strings must not be null or empty, but '" //$NON-NLS-1$
              + type + "' is.");//$NON-NLS-1$
    }

    res = null;
    finder: {
      for (final EThesisType mon : EThesisType.TYPES) {
        if (m.equalsIgnoreCase(mon.m_name)) {
          res = mon;
          break finder;
        }
      }

      res = EThesisType.valueOf(m);
    }

    this.setType(res);
  }

  /** {@inheritDoc} */
  @Override
  final BibThesis _compile() {

    this.fsmFlagsAssertTrue(BibRecordBuilder.FLAG_TITLE_SET
        | BibRecordBuilder.FLAG_DATE_SET
        | BibThesisBuilder.FLAG_SCHOOL_SET
        | BibThesisBuilder.FLAG_TYPE_SET);

    return new BibThesis(true, this.m_authors, this.m_title, this.m_date,
        this.m_type, this.m_school, this.m_publisher, this.m_series,
        this.m_issn, this.m_volume, this.m_edition, this.m_isbn,
        this.m_url, this.m_doi);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized BibThesis getResult() {
    return ((BibThesis) (super.getResult()));
  }
}
