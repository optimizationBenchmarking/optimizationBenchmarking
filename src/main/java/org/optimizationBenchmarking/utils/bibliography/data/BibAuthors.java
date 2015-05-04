package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A set of authors, i.e., a set of
 * {@link org.optimizationBenchmarking.utils.bibliography.data.BibAuthor}s.
 */
public final class BibAuthors extends _BibSet<BibAuthor, BibAuthors> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the empty list view */
  public static final BibAuthors EMPTY_AUTHORS = new BibAuthors(false,
      false, new BibAuthor[0]);

  /**
   * create the bib set
   *
   * @param data
   *          the data of the set
   */
  public BibAuthors(final BibAuthor[] data) {
    this(true, data);
  }

  /**
   * instantiate
   *
   * @param data
   *          the data of the set
   * @param cannotBeEmpty
   *          can the data be empty?
   * @param check
   *          do we need to check
   */
  private BibAuthors(final boolean cannotBeEmpty, final boolean check,
      final BibAuthor[] data) {
    super(cannotBeEmpty, check, data);
  }

  /**
   * instantiate
   *
   * @param data
   *          the data of the set
   * @param check
   *          do we need to check
   */
  BibAuthors(final boolean check, final BibAuthor[] data) {
    this(true, check, data);
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    int i;
    final int l;
    final BibAuthor[] nn;

    nn = this.m_data;
    l = (nn.length - 1);
    for (i = 0; i <= l; i++) {
      if (i > 0) {
        if (l > 1) {
          textOut.append(',');
        }
        textOut.append(' ');
        if (i >= l) {
          textOut.append("and "); //$NON-NLS-1$
        }
      }
      nn[i].toText(textOut);
    }
  }

}
