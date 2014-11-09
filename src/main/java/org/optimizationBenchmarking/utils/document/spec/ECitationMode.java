package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** The set of citation modes. */
public enum ECitationMode {
  /** cite by id */
  ID(true, false, false, false),

  /** cite by providing only the authors */
  AUTHORS(false, true, false, false),

  /** provide authors and an id */
  AUTHORS_AND_ID(true, true, false, false),

  /** provide authors and year */
  AUTHORS_AND_YEAR(false, true, true, false),

  /** cite by title */
  TITLE(true, false, false, true), ;

  /** the citation modes */
  public static final ArraySetView<ECitationMode> INSTANCES = new ArraySetView<>(
      ECitationMode.values());

  /** print the id */
  private transient final boolean m_id;

  /** print the authors */
  private transient final boolean m_authors;

  /** print the year */
  private transient final boolean m_year;

  /** print the title */
  private transient final boolean m_title;

  /**
   * create the citation mode
   * 
   * @param id
   *          {@code true} print the id?
   * @param authors
   *          {@code true} print the authors?
   * @param year
   *          {@code true} print the year?
   * @param title
   *          {@code true} print the title?
   */
  ECitationMode(final boolean id, final boolean authors,
      final boolean year, final boolean title) {
    this.m_id = id;
    this.m_authors = authors;
    this.m_year = year;
    this.m_title = title;
  }

  /**
   * Should we print the year?
   * 
   * @return the {@code true} if the year should be printed, {@code false}
   *         otherwise
   */
  public final boolean printYear() {
    return this.m_year;
  }

  /**
   * Should we print the authors?
   * 
   * @return the {@code true} if the authors should be printed,
   *         {@code false} otherwise
   */
  public final boolean printAuthors() {
    return this.m_authors;
  }

  /**
   * Should we print the id?
   * 
   * @return the {@code true} if the id should be printed, {@code false}
   *         otherwise
   */
  public final boolean printID() {
    return this.m_id;
  }

  /**
   * Should we print the title?
   * 
   * @return the {@code true} if the title should be printed, {@code false}
   *         otherwise
   */
  public final boolean printTitle() {
    return this.m_title;
  }

}
