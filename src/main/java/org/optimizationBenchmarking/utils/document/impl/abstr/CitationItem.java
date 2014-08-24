package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.document.spec.ECitationMode;

/**
 * A container for a citation bibliographic record
 */
public class CitationItem {

  /** the bibliographic bibliographic record */
  private final BibRecord m_record;

  /** the index of the sequence bibliographic record */
  final int m_index;

  /** the citation mode */
  private final ECitationMode m_mode;

  /**
   * Create the bibliographic record
   * 
   * @param record
   *          the bibliographic record
   * @param index
   *          the bibliographic index
   * @param mode
   *          the citation mode
   */
  public CitationItem(final BibRecord record, final int index,
      final ECitationMode mode) {
    super();

    if (record == null) {
      throw new IllegalArgumentException(//
          "Bibliographic bibliographic record cannot be null."); //$NON-NLS-1$
    }
    this.m_record = record;

    if (index < 0) {
      throw new IllegalArgumentException(//
          "Bibliographic index cannot be less than 0."); //$NON-NLS-1$
    }
    this.m_index = index;

    if (mode == null) {
      throw new IllegalArgumentException(//
          "Citation mode cannot be null.");//$NON-NLS-1$
    }
    this.m_mode = mode;
  }

  /**
   * Obtain the bibliographic index (0-based) of the reference
   * 
   * @return the bibliographic index (0-based) of the reference
   */
  public final int getIndex() {
    return this.m_index;
  }

  /**
   * Get the citation mode
   * 
   * @return the citation mode
   */
  public final ECitationMode getMode() {
    return this.m_mode;
  }

  /**
   * Get the bibliographic record
   * 
   * @return the bibliographic record
   */
  public final BibRecord getRecord() {
    return this.m_record;
  }
}
