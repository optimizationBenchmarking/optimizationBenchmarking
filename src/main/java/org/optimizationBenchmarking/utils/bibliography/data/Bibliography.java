package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** An ordered set of bibliographic records. */
public class Bibliography extends _BibSet<BibRecord, Bibliography> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the empty bibliography */
  public static final Bibliography EMPTY_BIBLIOGRAPHY = new Bibliography(
      false, false, new BibRecord[0]);

  /**
   * create the bib set
   *
   * @param data
   *          the data of the set
   */
  public Bibliography(final BibRecord[] data) {
    this(true, data);
  }

  /**
   * instantiate
   *
   * @param data
   *          the data of the set
   * @param check
   *          do we need to check?
   */
  Bibliography(final boolean check, final BibRecord[] data) {
    super(true, check, data);
  }

  /**
   * instantiate
   *
   * @param data
   *          the data of the set
   * @param cannotBeEmpty
   *          can the data be empty?
   * @param check
   *          do we need to check?
   */
  private Bibliography(final boolean cannotBeEmpty, final boolean check,
      final BibRecord[] data) {
    super(cannotBeEmpty, check, data);
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOutput) {
    int i;
    final int l;
    final BibRecord[] nn;
    BibRecord r;

    nn = this.m_data;
    l = (nn.length - 1);
    for (i = 0; i <= l;) {
      if (i > 0) {
        textOutput.appendLineBreak();
      }
      r = nn[i++];
      textOutput.append(i);
      textOutput.append('.');
      textOutput.append(' ');
      r.toText(textOutput);
    }
  }

}
