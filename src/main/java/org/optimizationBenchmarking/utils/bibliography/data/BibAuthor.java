package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ISequenceable;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a bibliographic record describing an author */
public class BibAuthor extends _BibElement<BibAuthor> implements
ISequenceable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * the personal name
   *
   * @serial a string
   */
  private final String m_personalName;

  /**
   * the family name
   *
   * @serial a string
   */
  private final String m_familyName;

  /**
   * the full name of the author, in case that there is 1:1 English
   * transcript of the name
   *
   * @serial a string
   */
  private final String m_originalSpelling;

  /**
   * Create the bibliography author.
   *
   * @param personalName
   *          the personal name
   * @param familyName
   *          the family name
   */
  public BibAuthor(final String personalName, final String familyName) {
    this(personalName, familyName, null);
  }

  /**
   * Create the bibliography author.
   *
   * @param personalName
   *          the personal name
   * @param familyName
   *          the family name
   * @param originalSpelling
   *          the original spelling of the name, or {@code null} if none
   *          needed
   */
  public BibAuthor(final String personalName, final String familyName,
      final String originalSpelling) {
    this(false, TextUtils.normalize(personalName), TextUtils
        .normalize(familyName), TextUtils.normalize(originalSpelling));
  }

  /**
   * Create the bibliography author.
   *
   * @param direct
   *          a placeholder parameter
   * @param personalName
   *          the personal name
   * @param familyName
   *          the family name
   * @param originalSpelling
   *          the original spelling of the name, or {@code null} if none
   *          needed
   */
  BibAuthor(final boolean direct, final String personalName,
      final String familyName, final String originalSpelling) {
    super();

    if ((this.m_personalName = personalName) == null) {
      throw new IllegalArgumentException(//
          "Personal name cannot be null."); //$NON-NLS-1$
    }

    if ((this.m_familyName = familyName) == null) {
      throw new IllegalArgumentException(//
          "Family name cannot be null."); //$NON-NLS-1$
    }

    this.m_originalSpelling = originalSpelling;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_familyName),//
        HashUtils.hashCode(this.m_personalName)),//
        HashUtils.hashCode(this.m_originalSpelling));
  }

  /**
   * get the personal (given, first) name of this author (or editor,
   * supervisor, &hellip;)
   *
   * @return the personal (given, first) name of this author (or editor,
   *         supervisor, &hellip;)
   */
  public final String getPersonalName() {
    return this.m_personalName;
  }

  /**
   * get the family name of this author (or editor, supervisor, &hellip;)
   *
   * @return the family name of this author (or editor, supervisor,
   *         &hellip;)
   */
  public final String getFamilyName() {
    return this.m_familyName;
  }

  /**
   * Get the full name of the author, written in an obscure language with
   * no 1:1 transcription to English, Will be {@code null} if the author's
   * name stems from a normal language.
   *
   * @return the full name of the author, written in an obscure language
   *         with no 1:1 transcription to English, {@code null} if the
   *         author's name stems from a normal language.
   */
  public final String getOriginalSpelling() {
    return this.m_originalSpelling;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final BibAuthor b;
    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o instanceof BibAuthor) {
      b = ((BibAuthor) o);
      return (EComparison.equals(this.m_familyName, b.m_familyName) && //
          EComparison.equals(this.m_personalName, b.m_personalName) && //
          EComparison.equals(this.m_originalSpelling, b.m_originalSpelling));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final BibAuthor o) {
    int i;

    if (o == null) {
      return (-1);
    }
    if (o == this) {
      return 0;
    }

    i = EComparison.compareObjects(this.m_familyName, o.m_familyName);
    if (i != 0) {
      return i;
    }

    i = EComparison.compareObjects(this.m_personalName, o.m_personalName);
    if (i != 0) {
      return i;
    }

    return EComparison.compareObjects(this.m_originalSpelling,
        o.m_originalSpelling);
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append(this.m_personalName);
    textOut.append(' ');
    textOut.append(this.m_familyName);
    if (this.m_originalSpelling != null) {
      textOut.append(' ');
      textOut.append('[');
      textOut.append(this.m_originalSpelling);
      textOut.append(']');
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void toSequence(final boolean isFirstInSequence,
      final boolean isLastInSequence, final ETextCase textCase,
      final ITextOutput textOut) {
    textCase.appendWord(this.m_familyName, textOut);
  }
}
