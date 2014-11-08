package org.optimizationBenchmarking.utils.bibliography.io;

import java.net.URI;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.bibliography.data.EBibMonth;
import org.optimizationBenchmarking.utils.bibliography.data.EBibQuarter;
import org.optimizationBenchmarking.utils.bibliography.data.EThesisType;

/**
 * the string constants for the bibliography xml format of
 * optimizationBenchmarking
 */
public final class BibliographyXMLConstants {

  /** the namespace uri */
  public static final URI NAMESPACE_URI = URI.create(//
      "http://www.optimizationBenchmarking.org/formats/bibliography/bibliography.1.0.xsd"); //$NON-NLS-1$

  /** the namespace uri as string */
  public static final String NAMESPACE = BibliographyXMLConstants.NAMESPACE_URI
      .toString();

  /** the schema name */
  public static final String SCHEMA = BibliographyXMLConstants.NAMESPACE
      .substring(BibliographyXMLConstants.NAMESPACE.lastIndexOf('/') + 1);

  /** the person element */
  static final String ELEMENT_PERSON = "person"; //$NON-NLS-1$

  /** the personal name attribute */
  static final String ATTR_PERSONAL_NAME = "personalName"; //$NON-NLS-1$

  /** the family name attribute */
  static final String ATTR_FAMILY_NAME = "familyName"; //$NON-NLS-1$

  /** the original name spelling attribute */
  static final String ATTR_NAME_ORIGINAL_SPELLING = "originalSpelling"; //$NON-NLS-1$

  /** the organization name attribute */
  static final String ATTR_ORGANIZATION = "organization"; //$NON-NLS-1$

  /** the address attribute */
  static final String ATTR_ADDRESS = "address"; //$NON-NLS-1$

  /** the organization original spelling attribute */
  static final String ATTR_PLACE_ORIGINAL_SPELLING = BibliographyXMLConstants.ATTR_NAME_ORIGINAL_SPELLING;

  /** the year attribute */
  static final String ATTR_YEAR = "year"; //$NON-NLS-1$

  /** the month attribute */
  static final String ATTR_MONTH = "month"; //$NON-NLS-1$

  /** the day attribute */
  static final String ATTR_DAY = "day"; //$NON-NLS-1$

  /** the quarter attribute */
  static final String ATTR_QUARTER = "quarter"; //$NON-NLS-1$

  /** the authors element */
  static final String ELEMENT_AUTHORS = "authors"; //$NON-NLS-1$

  /** the article element */
  static final String ELEMENT_ARTICLE = "article"; //$NON-NLS-1$

  /** the title attribute */
  static final String ATTR_TITLE = "title"; //$NON-NLS-1$

  /** the url attribute */
  static final String ATTR_URL = "url"; //$NON-NLS-1$

  /** the doi attribute */
  static final String ATTR_DOI = "doi"; //$NON-NLS-1$

  /** the journal attribute */
  static final String ATTR_JOURNAL = "journal"; //$NON-NLS-1$

  /** the volume attribute */
  static final String ATTR_VOLUME = "volume"; //$NON-NLS-1$

  /** the number attribute */
  static final String ATTR_NUMBER = "number"; //$NON-NLS-1$

  /** the start page attribute */
  static final String ATTR_START_PAGE = "startPage"; //$NON-NLS-1$

  /** the end page attribute */
  static final String ATTR_END_PAGE = "endPage"; //$NON-NLS-1$

  /** the issn attribute */
  static final String ATTR_ISSN = "issn"; //$NON-NLS-1$

  /** the book element */
  static final String ELEMENT_BOOK = "book"; //$NON-NLS-1$

  /** the editors element */
  static final String ELEMENT_EDITORS = "editors"; //$NON-NLS-1$

  /** the publisher element */
  static final String ELEMENT_PUBLISHER = "publisher"; //$NON-NLS-1$

  /** the date element */
  static final String ELEMENT_DATE = "date"; //$NON-NLS-1$

  /** the edition attribute */
  static final String ATTR_EDITION = "edition"; //$NON-NLS-1$

  /** the series attribute */
  static final String ATTR_SERIES = "series"; //$NON-NLS-1$

  /** the isbn attribute */
  static final String ATTR_ISBN = "isbn"; //$NON-NLS-1$

  /** the chapter attribute */
  static final String ATTR_CHAPTER = "chapter"; //$NON-NLS-1$

  /** the location element */
  static final String ELEMENT_LOCATION = "location"; //$NON-NLS-1$

  /** the start date element */
  static final String ELEMENT_START_DATE = "startDate"; //$NON-NLS-1$

  /** the end date element */
  static final String ELEMENT_END_DATE = "endDate"; //$NON-NLS-1$

  /** the proceedings element */
  static final String ELEMENT_PROCEEDINGS = "proceedings"; //$NON-NLS-1$

  /** the in-proceedings element */
  static final String ELEMENT_IN_PROCEEDINGS = "inProceedings"; //$NON-NLS-1$

  /** the in-collection element */
  static final String ELEMENT_IN_COLLECTION = "inCollection"; //$NON-NLS-1$

  /** the tech report element */
  static final String ELEMENT_TECH_REPORT = "techReport"; //$NON-NLS-1$

  /** the institution element */
  static final String ELEMENT_INSTITUTION = "institution"; //$NON-NLS-1$

  /** the thesis element */
  static final String ELEMENT_THESIS = "thesis"; //$NON-NLS-1$

  /** the school element */
  static final String ELEMENT_SCHOOL = "school"; //$NON-NLS-1$

  /** the thesis type attribute */
  static final String ATTR_THESIS_TYPE = "thesisType"; //$NON-NLS-1$

  /** the website element */
  static final String ELEMENT_WEBSITE = "website"; //$NON-NLS-1$

  /** the bibliography element */
  static final String ELEMENT_BIBLIOGRAPHY = "bibliography"; //$NON-NLS-1$

  /** the month values */
  static final String[] VAL_MONTHS;

  static {
    VAL_MONTHS = new String[EBibMonth.MONTHS.size()];
    BibliographyXMLConstants.VAL_MONTHS[EBibMonth.JANUARY.ordinal()] = "jan";//$NON-NLS-1$
    BibliographyXMLConstants.VAL_MONTHS[EBibMonth.FEBRUARY.ordinal()] = "feb";//$NON-NLS-1$
    BibliographyXMLConstants.VAL_MONTHS[EBibMonth.MARCH.ordinal()] = "mar";//$NON-NLS-1$
    BibliographyXMLConstants.VAL_MONTHS[EBibMonth.APRIL.ordinal()] = "apr";//$NON-NLS-1$
    BibliographyXMLConstants.VAL_MONTHS[EBibMonth.MAY.ordinal()] = "may";//$NON-NLS-1$
    BibliographyXMLConstants.VAL_MONTHS[EBibMonth.JUNE.ordinal()] = "jun";//$NON-NLS-1$
    BibliographyXMLConstants.VAL_MONTHS[EBibMonth.JULY.ordinal()] = "jul";//$NON-NLS-1$
    BibliographyXMLConstants.VAL_MONTHS[EBibMonth.AUGUST.ordinal()] = "aug";//$NON-NLS-1$
    BibliographyXMLConstants.VAL_MONTHS[EBibMonth.SEPTEMBER.ordinal()] = "sep";//$NON-NLS-1$
    BibliographyXMLConstants.VAL_MONTHS[EBibMonth.OCTOBER.ordinal()] = "oct";//$NON-NLS-1$
    BibliographyXMLConstants.VAL_MONTHS[EBibMonth.NOVEMBER.ordinal()] = "nov";//$NON-NLS-1$
    BibliographyXMLConstants.VAL_MONTHS[EBibMonth.DECEMBER.ordinal()] = "dec";//$NON-NLS-1$
  }

  /** the quarter values */
  static final String[] VAL_QUARTERS;

  static {
    VAL_QUARTERS = new String[EBibQuarter.QUARTERS.size()];
    BibliographyXMLConstants.VAL_QUARTERS[EBibQuarter.SPRING.ordinal()] = "spring";//$NON-NLS-1$
    BibliographyXMLConstants.VAL_QUARTERS[EBibQuarter.SUMMER.ordinal()] = "summer";//$NON-NLS-1$
    BibliographyXMLConstants.VAL_QUARTERS[EBibQuarter.FALL.ordinal()] = "fall";//$NON-NLS-1$
    BibliographyXMLConstants.VAL_QUARTERS[EBibQuarter.WINTER.ordinal()] = "winter";//$NON-NLS-1$
  }

  /** the thesis type values */
  static final String[] VAL_THESIS_TYPES;

  static {
    VAL_THESIS_TYPES = new String[EThesisType.TYPES.size()];
    BibliographyXMLConstants.VAL_THESIS_TYPES[EThesisType.BACHELOR_THESIS
        .ordinal()] = "bachelor";//$NON-NLS-1$1$
    BibliographyXMLConstants.VAL_THESIS_TYPES[EThesisType.MASTER_THESIS
        .ordinal()] = "master";//$NON-NLS-1$1$
    BibliographyXMLConstants.VAL_THESIS_TYPES[EThesisType.PHD_THESIS
        .ordinal()] = "phd";//$NON-NLS-1$1$
  }

  /** create */
  private BibliographyXMLConstants() {
    ErrorUtils.doNotCall();
  }
}
