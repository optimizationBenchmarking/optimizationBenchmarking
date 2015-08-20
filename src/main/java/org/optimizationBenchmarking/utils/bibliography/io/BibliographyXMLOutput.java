package org.optimizationBenchmarking.utils.bibliography.io;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.bibliography.data.BibArticle;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthor;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthors;
import org.optimizationBenchmarking.utils.bibliography.data.BibBook;
import org.optimizationBenchmarking.utils.bibliography.data.BibDate;
import org.optimizationBenchmarking.utils.bibliography.data.BibInBook;
import org.optimizationBenchmarking.utils.bibliography.data.BibInCollection;
import org.optimizationBenchmarking.utils.bibliography.data.BibInProceedings;
import org.optimizationBenchmarking.utils.bibliography.data.BibOrganization;
import org.optimizationBenchmarking.utils.bibliography.data.BibProceedings;
import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.bibliography.data.BibTechReport;
import org.optimizationBenchmarking.utils.bibliography.data.BibThesis;
import org.optimizationBenchmarking.utils.bibliography.data.BibWebsite;
import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.data.EBibMonth;
import org.optimizationBenchmarking.utils.bibliography.data.EBibQuarter;
import org.optimizationBenchmarking.utils.bibliography.data.EThesisType;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLOutputTool;
import org.optimizationBenchmarking.utils.io.xml.XMLBase;
import org.optimizationBenchmarking.utils.io.xml.XMLElement;

/** the configuration xml output */
public final class BibliographyXMLOutput extends XMLOutputTool<Object> {

  /** create */
  BibliographyXMLOutput() {
    super();
  }

  /**
   * Get the instance of the {@link BibliographyXMLOutput}
   *
   * @return the instance of the {@link BibliographyXMLOutput}
   */
  public static final BibliographyXMLOutput getInstance() {
    return __BibliographyXMLOutputLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected void xml(final IOJob job, final Object data, final XMLBase dest) {
    try (XMLElement root = dest.element()) {
      root.namespaceSetPrefix(BibliographyXML.NAMESPACE_URI, "bib"); //$NON-NLS-1$
      root.name(BibliographyXML.NAMESPACE_URI,
          BibliographyXML.ELEMENT_BIBLIOGRAPHY);
      BibliographyXMLOutput.__write(data, root);
    }
  }

  /**
   * Write an object
   *
   * @param o
   *          the object to write
   * @param dest
   *          the destination
   */
  private static final void __write(final Object o, final XMLBase dest) {

    if (o instanceof Bibliography) {
      BibliographyXMLOutput.__writeBibliography(((Bibliography) o), dest);
      return;
    }

    if (o instanceof BibArticle) {
      BibliographyXMLOutput.__writeArticle(((BibArticle) o), dest);
      return;
    }

    if (o instanceof BibProceedings) {
      BibliographyXMLOutput.__writeProceedings(((BibProceedings) o), dest);
      return;
    }

    if (o instanceof BibThesis) {
      BibliographyXMLOutput.__writeThesis(((BibThesis) o), dest);
      return;
    }

    if (o instanceof BibBook) {
      BibliographyXMLOutput.__writeBook(((BibBook) o), dest);
      return;
    }

    if (o instanceof BibInProceedings) {
      BibliographyXMLOutput.__writeInProceedings(((BibInProceedings) o),
          dest);
      return;
    }

    if (o instanceof BibInCollection) {
      BibliographyXMLOutput.__writeInCollection(((BibInCollection) o),
          dest);
      return;
    }

    if (o instanceof BibTechReport) {
      BibliographyXMLOutput.__writeTechReport(((BibTechReport) o), dest);
      return;
    }

    if (o instanceof BibWebsite) {
      BibliographyXMLOutput.__writeWebsite(((BibWebsite) o), dest);
      return;
    }

    if (o instanceof BibAuthors) {
      BibliographyXMLOutput.__writeAuthors(((BibAuthors) o),
          BibliographyXML.ELEMENT_AUTHORS, dest);
      return;
    }

    if (o instanceof BibAuthor) {
      BibliographyXMLOutput.__writeAuthor(((BibAuthor) o), dest);
      return;
    }

    if (o instanceof BibOrganization) {
      BibliographyXMLOutput.__writePlace(((BibOrganization) o),
          BibliographyXML.ELEMENT_PUBLISHER, dest);
      return;
    }

    if (o instanceof BibDate) {
      BibliographyXMLOutput.__writeDate(((BibDate) o),
          BibliographyXML.ELEMENT_DATE, dest);
      return;
    }

    throw new IllegalArgumentException(
        '\'' + (o + "' cannot be serialized to bibliography XML.")); //$NON-NLS-1$
  }

  /**
   * Write the bibliography record's default attributes
   *
   * @param rec
   *          the bib record
   * @param dest
   *          the destination element
   */
  private static final void __writeRecordAttrs(final BibRecord rec,
      final XMLElement dest) {
    final URI uri;
    String s;

    s = rec.getTitle();
    if (s != null) {
      dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
          BibliographyXML.ATTR_TITLE, s);
    }

    uri = rec.getURL();
    if (uri != null) {
      dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
          BibliographyXML.ATTR_URL, uri.toString());
    }

    s = rec.getDOI();
    if (s != null) {
      dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
          BibliographyXML.ATTR_DOI, s);
    }
  }

  /**
   * Write a book's default attributes
   *
   * @param rec
   *          the book
   * @param dest
   *          the destination element
   */
  private static final void __writeBookAttrs(final BibBook rec,
      final XMLElement dest) {
    String s;

    BibliographyXMLOutput.__writeRecordAttrs(rec, dest);

    s = rec.getSeries();
    if (s != null) {
      dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
          BibliographyXML.ATTR_SERIES, s);
    }

    s = rec.getVolume();
    if (s != null) {
      dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
          BibliographyXML.ATTR_VOLUME, s);
    }

    s = rec.getISSN();
    if (s != null) {
      dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
          BibliographyXML.ATTR_ISSN, s);
    }

    s = rec.getISBN();
    if (s != null) {
      dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
          BibliographyXML.ATTR_ISBN, s);
    }

    s = rec.getEdition();
    if (s != null) {
      dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
          BibliographyXML.ATTR_EDITION, s);
    }

  }

  /**
   * Write a in-book's default attributes
   *
   * @param rec
   *          the in-book
   * @param dest
   *          the destination element
   */
  private static final void __writeInBookAttrs(final BibInBook rec,
      final XMLElement dest) {
    String s;

    BibliographyXMLOutput.__writeRecordAttrs(rec, dest);

    s = rec.getStartPage();
    if (s != null) {
      dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
          BibliographyXML.ATTR_START_PAGE, s);
    }

    s = rec.getEndPage();
    if (s != null) {
      dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
          BibliographyXML.ATTR_END_PAGE, s);
    }

    s = rec.getChapter();
    if (s != null) {
      dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
          BibliographyXML.ATTR_CHAPTER, s);
    }
  }

  /**
   * Write the bibliography
   *
   * @param rec
   *          the bibliography
   * @param owner
   *          the owning element
   */
  private static final void __writeBibliography(final Bibliography rec,
      final XMLBase owner) {
    if ((rec != null) && (!(rec.isEmpty()))) {
      for (final BibRecord r : rec) {
        BibliographyXMLOutput.__write(r, owner);
      }
    }
  }

  /**
   * Write the bibliography article
   *
   * @param rec
   *          the article
   * @param owner
   *          the owning element
   */
  private static final void __writeArticle(final BibArticle rec,
      final XMLBase owner) {
    String s;

    if (rec != null) {
      try (XMLElement dest = owner.element()) {
        dest.name(BibliographyXML.NAMESPACE_URI,
            BibliographyXML.ELEMENT_ARTICLE);

        BibliographyXMLOutput.__writeRecordAttrs(rec, dest);

        s = rec.getJournal();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_JOURNAL, s);
        }

        s = rec.getVolume();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_VOLUME, s);
        }

        s = rec.getNumber();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_NUMBER, s);
        }

        s = rec.getStartPage();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_START_PAGE, s);
        }

        s = rec.getEndPage();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_END_PAGE, s);
        }

        s = rec.getISSN();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_ISSN, s);
        }

        BibliographyXMLOutput.__writeAuthors(rec.getAuthors(),
            BibliographyXML.ELEMENT_AUTHORS, dest);
        BibliographyXMLOutput.__writePlace(rec.getPublisher(),
            BibliographyXML.ELEMENT_PUBLISHER, dest);
        BibliographyXMLOutput.__writeDate(rec.getDate(),
            BibliographyXML.ELEMENT_DATE, dest);
      }
    }
  }

  /**
   * Write the bibliography book
   *
   * @param rec
   *          the book
   * @param owner
   *          the owning element
   */
  private static final void __writeBook(final BibBook rec,
      final XMLBase owner) {

    if (rec != null) {
      try (XMLElement dest = owner.element()) {
        dest.name(BibliographyXML.NAMESPACE_URI,
            BibliographyXML.ELEMENT_BOOK);

        BibliographyXMLOutput.__writeBookAttrs(rec, dest);

        BibliographyXMLOutput.__writeAuthors(rec.getAuthors(),
            BibliographyXML.ELEMENT_AUTHORS, dest);
        BibliographyXMLOutput.__writeAuthors(rec.getEditors(),
            BibliographyXML.ELEMENT_EDITORS, dest);
        BibliographyXMLOutput.__writePlace(rec.getPublisher(),
            BibliographyXML.ELEMENT_PUBLISHER, dest);
        BibliographyXMLOutput.__writeDate(rec.getDate(),
            BibliographyXML.ELEMENT_DATE, dest);
      }
    }
  }

  /**
   * Write the bibliography technical report
   *
   * @param rec
   *          the technical report
   * @param owner
   *          the owning element
   */
  private static final void __writeTechReport(final BibTechReport rec,
      final XMLBase owner) {
    String s;

    if (rec != null) {
      try (XMLElement dest = owner.element()) {
        dest.name(BibliographyXML.NAMESPACE_URI,
            BibliographyXML.ELEMENT_TECH_REPORT);

        BibliographyXMLOutput.__writeRecordAttrs(rec, dest);

        s = rec.getSeries();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_SERIES, s);
        }

        s = rec.getNumber();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_NUMBER, s);
        }

        s = rec.getISSN();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_ISSN, s);
        }

        BibliographyXMLOutput.__writeAuthors(rec.getAuthors(),
            BibliographyXML.ELEMENT_AUTHORS, dest);
        BibliographyXMLOutput.__writePlace(rec.getPublisher(),
            BibliographyXML.ELEMENT_INSTITUTION, dest);
        BibliographyXMLOutput.__writeDate(rec.getDate(),
            BibliographyXML.ELEMENT_DATE, dest);
      }
    }
  }

  /**
   * Write the bibliography website
   *
   * @param rec
   *          the website
   * @param owner
   *          the owning element
   */
  private static final void __writeWebsite(final BibWebsite rec,
      final XMLBase owner) {

    if (rec != null) {
      try (XMLElement dest = owner.element()) {
        dest.name(BibliographyXML.NAMESPACE_URI,
            BibliographyXML.ELEMENT_WEBSITE);

        BibliographyXMLOutput.__writeRecordAttrs(rec, dest);

        BibliographyXMLOutput.__writeAuthors(rec.getAuthors(),
            BibliographyXML.ELEMENT_AUTHORS, dest);
        BibliographyXMLOutput.__writePlace(rec.getPublisher(),
            BibliographyXML.ELEMENT_PUBLISHER, dest);
        BibliographyXMLOutput.__writeDate(rec.getDate(),
            BibliographyXML.ELEMENT_DATE, dest);
      }
    }
  }

  /**
   * Write the bibliography thesis
   *
   * @param rec
   *          the thesis
   * @param owner
   *          the owning element
   */
  private static final void __writeThesis(final BibThesis rec,
      final XMLBase owner) {
    final EThesisType type;

    if (rec != null) {
      try (XMLElement dest = owner.element()) {
        dest.name(BibliographyXML.NAMESPACE_URI,
            BibliographyXML.ELEMENT_THESIS);

        BibliographyXMLOutput.__writeBookAttrs(rec, dest);
        type = rec.getType();
        if (type != null) {
          dest.attributeRaw(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_THESIS_TYPE,
              BibliographyXML.VAL_THESIS_TYPES[type.ordinal()]);

        }

        BibliographyXMLOutput.__writeAuthors(rec.getAuthors(),
            BibliographyXML.ELEMENT_AUTHORS, dest);
        BibliographyXMLOutput.__writeAuthors(rec.getEditors(),
            BibliographyXML.ELEMENT_EDITORS, dest);
        BibliographyXMLOutput.__writePlace(rec.getPublisher(),
            BibliographyXML.ELEMENT_PUBLISHER, dest);
        BibliographyXMLOutput.__writePlace(rec.getSchool(),
            BibliographyXML.ELEMENT_SCHOOL, dest);
        BibliographyXMLOutput.__writeDate(rec.getDate(),
            BibliographyXML.ELEMENT_DATE, dest);
      }
    }
  }

  /**
   * Write the bibliography in-collection
   *
   * @param rec
   *          the in-collection
   * @param owner
   *          the owning element
   */
  private static final void __writeInCollection(final BibInCollection rec,
      final XMLBase owner) {

    if (rec != null) {
      try (XMLElement dest = owner.element()) {
        dest.name(BibliographyXML.NAMESPACE_URI,
            BibliographyXML.ELEMENT_IN_COLLECTION);

        BibliographyXMLOutput.__writeInBookAttrs(rec, dest);

        BibliographyXMLOutput.__writeAuthors(rec.getAuthors(),
            BibliographyXML.ELEMENT_AUTHORS, dest);
        BibliographyXMLOutput.__writeBook(rec.getBook(), dest);
      }
    }
  }

  /**
   * Write the bibliography in-proceedings
   *
   * @param rec
   *          the in-collection
   * @param owner
   *          the owning element
   */
  private static final void __writeInProceedings(
      final BibInProceedings rec, final XMLBase owner) {

    if (rec != null) {
      try (XMLElement dest = owner.element()) {
        dest.name(BibliographyXML.NAMESPACE_URI,
            BibliographyXML.ELEMENT_IN_PROCEEDINGS);

        BibliographyXMLOutput.__writeInBookAttrs(rec, dest);

        BibliographyXMLOutput.__writeAuthors(rec.getAuthors(),
            BibliographyXML.ELEMENT_AUTHORS, dest);
        BibliographyXMLOutput.__writeProceedings(rec.getBook(), dest);
      }
    }
  }

  /**
   * Write the bibliography proceedings
   *
   * @param rec
   *          the proceedings
   * @param owner
   *          the owning element
   */
  private static final void __writeProceedings(final BibProceedings rec,
      final XMLBase owner) {

    if (rec != null) {
      try (XMLElement dest = owner.element()) {
        dest.name(BibliographyXML.NAMESPACE_URI,
            BibliographyXML.ELEMENT_PROCEEDINGS);

        BibliographyXMLOutput.__writeBookAttrs(rec, dest);

        BibliographyXMLOutput.__writeAuthors(rec.getEditors(),
            BibliographyXML.ELEMENT_EDITORS, dest);
        BibliographyXMLOutput.__writePlace(rec.getPublisher(),
            BibliographyXML.ELEMENT_PUBLISHER, dest);
        BibliographyXMLOutput.__writeDate(rec.getStartDate(),
            BibliographyXML.ELEMENT_START_DATE, dest);
        BibliographyXMLOutput.__writeDate(rec.getEndDate(),
            BibliographyXML.ELEMENT_END_DATE, dest);
        BibliographyXMLOutput.__writePlace(rec.getLocation(),
            BibliographyXML.ELEMENT_LOCATION, dest);
      }
    }
  }

  /**
   * write a set of authors
   *
   * @param authors
   *          the authors
   * @param tag
   *          the tag
   * @param owner
   *          the owning element
   */
  private static final void __writeAuthors(final BibAuthors authors,
      final String tag, final XMLBase owner) {

    if ((authors != null) && (!(authors.isEmpty()))) {
      try (final XMLElement dest = owner.element()) {
        dest.name(BibliographyXML.NAMESPACE_URI, tag);
        for (final BibAuthor ba : authors) {
          BibliographyXMLOutput.__writeAuthor(ba, dest);
        }
      }
    }
  }

  /**
   * write an author
   *
   * @param author
   *          the author
   * @param owner
   *          the owning element
   */
  private static final void __writeAuthor(final BibAuthor author,
      final XMLBase owner) {
    String s;
    if (author != null) {
      try (final XMLElement dest = owner.element()) {

        dest.name(BibliographyXML.NAMESPACE_URI,
            BibliographyXML.ELEMENT_PERSON);

        s = author.getFamilyName();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_FAMILY_NAME, s);
        }

        s = author.getPersonalName();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_PERSONAL_NAME, s);
        }

        s = author.getOriginalSpelling();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_NAME_ORIGINAL_SPELLING, s);
        }
      }
    }
  }

  /**
   * write a place
   *
   * @param place
   *          the place
   * @param tag
   *          the tag
   * @param owner
   *          the owning element
   */
  private static final void __writePlace(final BibOrganization place,
      final String tag, final XMLBase owner) {
    String s;

    if (place != null) {
      try (final XMLElement dest = owner.element()) {
        dest.name(BibliographyXML.NAMESPACE_URI, tag);

        s = place.getName();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_ORGANIZATION, s);
        }

        s = place.getAddress();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_ADDRESS, s);
        }

        s = place.getOriginalSpelling();
        if (s != null) {
          dest.attributeEncoded(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_PLACE_ORIGINAL_SPELLING, s);
        }
      }
    }
  }

  /**
   * write a date
   *
   * @param date
   *          the date
   * @param tag
   *          the tag
   * @param owner
   *          the owning element
   */
  private static final void __writeDate(final BibDate date,
      final String tag, final XMLBase owner) {
    int d;
    EBibMonth month;
    EBibQuarter quarter;

    if (date != null) {
      try (final XMLElement dest = owner.element()) {
        dest.name(BibliographyXML.NAMESPACE_URI, tag);

        dest.attributeRaw(BibliographyXML.NAMESPACE_URI,
            BibliographyXML.ATTR_YEAR, Integer.toString(date.getYear()));

        month = date.getMonth();
        if (month != null) {
          dest.attributeRaw(BibliographyXML.NAMESPACE_URI,
              BibliographyXML.ATTR_MONTH,
              BibliographyXML.VAL_MONTHS[month.ordinal()]);

          d = date.getDay();
          if ((d > 0) && (d <= 31)) {
            dest.attributeRaw(BibliographyXML.NAMESPACE_URI,
                BibliographyXML.ATTR_DAY, Integer.toString(d));
          }
        } else {
          quarter = date.getQuarter();
          if (quarter != null) {
            dest.attributeRaw(BibliographyXML.NAMESPACE_URI,
                BibliographyXML.ATTR_QUARTER,
                BibliographyXML.VAL_QUARTERS[quarter.ordinal()]);
          }
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Bibliography XML Output"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final String getDefaultPlainOutputFileName() {
    return ("bibliography." + //$NON-NLS-1$
    BibliographyXML.BIBLIOGRAPHY_XML.getDefaultSuffix());
  }

  /** {@inheritDoc} */
  @Override
  protected final void file(final IOJob job, final Object data,
      final Path file, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    super.file(job, data, file, encoding);
    if (Files.exists(file)) {
      this.addFile(job, file, BibliographyXML.BIBLIOGRAPHY_XML);
    }
  }

  /** the loader for lazy initialization */
  private static final class __BibliographyXMLOutputLoader {
    /** the configuration xml */
    static final BibliographyXMLOutput INSTANCE = new BibliographyXMLOutput();
  }
}
