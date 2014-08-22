package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A builder for sets of
 * {@link org.optimizationBenchmarking.utils.bibliography.data.BibAuthor}
 * objects.
 */
public final class BibAuthorBuilder extends _BibBuilder<BibAuthor> {

  /** the personal name has been set */
  private static final int FLAG_PERSONAL_NAME_SET = (_BibBuilder.FLAG_FINALIZED << 1);
  /** the family name has been set */
  private static final int FLAG_FAMILY_NAME_SET = (BibAuthorBuilder.FLAG_PERSONAL_NAME_SET << 1);
  /** the original spelling has been set */
  private static final int FLAG_ORIGINAL_SPELLING_SET = (BibAuthorBuilder.FLAG_FAMILY_NAME_SET << 1);

  /** the personal name */
  private String m_personalName;
  /** the family name */
  private String m_familyName;
  /**
   * the full name of the author, in case that there is 1:1 English
   * transcript of the name
   */
  private String m_originalSpelling;

  /** create the author builder */
  public BibAuthorBuilder() {
    this(null);
  }

  /**
   * create the author builder
   * 
   * @param owner
   *          the owner
   */
  BibAuthorBuilder(final BibAuthorsBuilder owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_PERSONAL_NAME_SET: {
        append.append("personalNameSet");//$NON-NLS-1$
        return;
      }
      case FLAG_FAMILY_NAME_SET: {
        append.append("familyNameSet");//$NON-NLS-1$
        return;
      }
      case FLAG_ORIGINAL_SPELLING_SET: {
        append.append("originalSpellingSet");//$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /**
   * Set the personal (given, first) name of this author (or editor,
   * supervisor, &hellip;)
   * 
   * @param personalName
   *          the personal (given, first) name of this author (or editor,
   *          supervisor, &hellip;)
   */
  public synchronized final void setPersonalName(final String personalName) {
    this.fsmFlagsAssertAndUpdate(
        FSM.FLAG_NOTHING,
        (BibAuthorBuilder.FLAG_PERSONAL_NAME_SET | _BibBuilder.FLAG_FINALIZED),
        BibAuthorBuilder.FLAG_PERSONAL_NAME_SET, FSM.FLAG_NOTHING);
    if ((this.m_personalName = this.normalize(personalName)) == null) {
      throw new IllegalArgumentException(//
          "Personal name cannot be empty or null, but '" //$NON-NLS-1$
              + personalName + "' is.");//$NON-NLS-1$
    }
  }

  /**
   * set the family name of this author (or editor, supervisor, &hellip;)
   * 
   * @param familyName
   *          the family name of this author (or editor, supervisor,
   *          &hellip;)
   */
  public synchronized final void setFamilyName(final String familyName) {
    this.fsmFlagsAssertAndUpdate(
        FSM.FLAG_NOTHING,
        (BibAuthorBuilder.FLAG_FAMILY_NAME_SET | _BibBuilder.FLAG_FINALIZED),
        BibAuthorBuilder.FLAG_FAMILY_NAME_SET, FSM.FLAG_NOTHING);
    if ((this.m_familyName = this.normalize(familyName)) == null) {
      throw new IllegalArgumentException(//
          "Family name cannot be empty or null, but '" //$NON-NLS-1$
              + familyName + "' is.");//$NON-NLS-1$
    }
  }

  /**
   * Set the full name of the author, written in an obscure language with
   * no 1:1 transcription to English.
   * 
   * @param originalSpelling
   *          the full name of the author, written in an obscure language
   *          with no 1:1 transcription to English
   */
  public synchronized final void setOriginalSpelling(
      final String originalSpelling) {
    this.fsmFlagsAssertAndUpdate(
        FSM.FLAG_NOTHING,
        (BibAuthorBuilder.FLAG_ORIGINAL_SPELLING_SET | _BibBuilder.FLAG_FINALIZED),
        BibAuthorBuilder.FLAG_ORIGINAL_SPELLING_SET, FSM.FLAG_NOTHING);
    if ((this.m_originalSpelling = this.normalize(originalSpelling)) == null) {
      throw new IllegalArgumentException(//
          "If you set the original name spelling, it cannot be empty or null, but '" //$NON-NLS-1$
              + originalSpelling
              + "' is. Maybe you should just not set it?");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  final BibAuthor _compile() {
    this.fsmFlagsAssertTrue(BibAuthorBuilder.FLAG_PERSONAL_NAME_SET
        | BibAuthorBuilder.FLAG_FAMILY_NAME_SET);

    return new BibAuthor(//
        true,//
        this.m_personalName,//
        this.m_familyName,//
        this.m_originalSpelling);//
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    super.beforeChildOpens(child, hasOtherChildren);
    this.throwChildNotAllowed(child);
  }
}
