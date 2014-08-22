package org.optimizationBenchmarking.utils.bibliography.data;

/** A builder for citation sets objects. */
public class CitationsBuilder extends
    _BibSetBuilder<BibRecord, Bibliography> {

  /** create the author builder */
  public CitationsBuilder() {
    super(null);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  final BibRecord[] _create(final int len) {
    return new BibRecord[len];
  }

  /** {@inheritDoc} */
  @Override
  final Bibliography _empty() {
    return Bibliography.EMPTY_BIBLIOGRAPHY;
  }

  /** {@inheritDoc} */
  @Override
  final Bibliography _make(final BibRecord[] data) {
    return new Bibliography(false, data);
  }

  /**
   * Add a bibliographic record
   * 
   * @param rec
   *          the record to add
   * @return The index at which the element was added (or the index where
   *         an equal element existed)
   */
  public synchronized final int add(final BibRecord rec) {
    return this._add(rec, false);
  }

}
