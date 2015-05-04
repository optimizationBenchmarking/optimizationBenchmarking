package org.optimizationBenchmarking.utils.bibliography.io;

import java.util.ArrayList;

import org.optimizationBenchmarking.utils.bibliography.data.BibArticleBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibBookBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibBookRecordBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibInBookBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibInCollectionBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibInProceedingsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibOrganizationBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibProceedingsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.bibliography.data.BibRecordBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibRecordWithPublisherBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibTechReportBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibThesisBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibWebsiteBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.EBibMonth;
import org.optimizationBenchmarking.utils.bibliography.data.EBibQuarter;
import org.optimizationBenchmarking.utils.bibliography.data.EThesisType;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.io.xml.DelegatingHandler;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** a handler for bibliography xml data */
public final class BibliographyXMLHandler extends DelegatingHandler {

  /** the builder hierarchy */
  private final ArrayList<BuilderFSM<?>> m_builders;

  /**
   * Create
   *
   * @param owner
   *          the owning handler, or {@code null} if not used
   * @param dest
   *          the bibliograpy builder to load the data into
   */
  public BibliographyXMLHandler(final DelegatingHandler owner,
      final BibliographyBuilder dest) {
    super(owner);
    this.m_builders = new ArrayList<>();
    this.m_builders.add(dest);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "resource", "rawtypes", "unchecked" })
  @Override
  protected final void doStartElement(final String uri,
      final String localName, final String qName,
      final Attributes attributes) throws SAXException {
    final BibArticleBuilder bab;
    final BibBookBuilder bb;
    final BibProceedingsBuilder bpb;
    final BuilderFSM<?> bfsm;
    final BibThesisBuilder btb;
    final BibWebsiteBuilder bwb;
    final BibTechReportBuilder btrb;
    final BibInProceedingsBuilder bipb;
    final BibInCollectionBuilder bicb;
    final BibDateBuilder bdb;
    final BibAuthorBuilder bppb;
    final BibOrganizationBuilder bob;
    final Object reflect;
    int i;
    String s;

    if ((uri == null) || (BibliographyXML.NAMESPACE.equalsIgnoreCase(uri))) {

      if (BibliographyXML.ELEMENT_BIBLIOGRAPHY.equalsIgnoreCase(localName)) {
        return;
      }

      bfsm = this.m_builders.get(this.m_builders.size() - 1);

      if (BibliographyXML.ELEMENT_FROM_JAVA.equalsIgnoreCase(localName)) {
        s = DelegatingHandler.getAttributeNormalized(attributes,
            BibliographyXML.NAMESPACE, BibliographyXML.ATTR_INSTANCE);
        try {
          reflect = ReflectionUtils.getInstanceByName(Object.class, s);
        } catch (final Throwable t) {
          throw new IllegalArgumentException(//
              ((("Error when trying to load singleton bibliogrphy entry '" //$NON-NLS-1$
                  + s) + '\'') + '.'), t);
        }

        if (reflect instanceof BibRecord) {
          ((BibliographyBuilder) (bfsm)).add((BibRecord) reflect);
          return;
        }

        if (reflect instanceof Iterable) {
          ((BibliographyBuilder) (bfsm)).addAll((Iterable) reflect);
          return;
        }

        throw new IllegalStateException(//
            "Can use only load bibliography records or iterables of them (such as bibliographies) from Java, but found instance of " + //$NON-NLS-1$
            TextUtils.className(reflect.getClass()));
      }

      if (BibliographyXML.ELEMENT_ARTICLE.equalsIgnoreCase(localName)) {
        this.m_builders.add(//
            bab = ((BibliographyBuilder) (bfsm)).article());
        BibliographyXMLHandler.__setRecordAtts(bab, attributes);

        if ((s = DelegatingHandler.getAttributeNormalized(attributes,
            BibliographyXML.NAMESPACE, BibliographyXML.ATTR_JOURNAL)) != null) {
          bab.setJournal(s);
        }

        if ((s = DelegatingHandler.getAttributeNormalized(attributes,
            BibliographyXML.NAMESPACE, BibliographyXML.ATTR_NUMBER)) != null) {
          bab.setNumber(s);
        }

        if ((s = DelegatingHandler.getAttributeNormalized(attributes,
            BibliographyXML.NAMESPACE, BibliographyXML.ATTR_VOLUME)) != null) {
          bab.setVolume(s);
        }

        if ((s = DelegatingHandler.getAttributeNormalized(attributes,
            BibliographyXML.NAMESPACE, BibliographyXML.ATTR_START_PAGE)) != null) {
          bab.setStartPage(s);
        }

        if ((s = DelegatingHandler.getAttributeNormalized(attributes,
            BibliographyXML.NAMESPACE, BibliographyXML.ATTR_END_PAGE)) != null) {
          bab.setEndPage(s);
        }

        if ((s = DelegatingHandler.getAttributeNormalized(attributes,
            BibliographyXML.NAMESPACE, BibliographyXML.ATTR_ISSN)) != null) {
          bab.setISSN(s);
        }

        return;
      }

      if (BibliographyXML.ELEMENT_BOOK.equalsIgnoreCase(localName)) {

        if (bfsm instanceof BibliographyBuilder) {
          bb = ((BibliographyBuilder) (bfsm)).book();
        } else {
          bb = ((BibInCollectionBuilder) (bfsm)).book();
        }

        this.m_builders.add(bb);
        BibliographyXMLHandler.__setBookAtts(bb, attributes);
        return;
      }

      if (BibliographyXML.ELEMENT_PROCEEDINGS.equalsIgnoreCase(localName)) {
        if (bfsm instanceof BibliographyBuilder) {
          bpb = ((BibliographyBuilder) (bfsm)).proceedings();
        } else {
          bpb = ((BibInProceedingsBuilder) (bfsm)).proceedings();
        }

        this.m_builders.add(bpb);
        BibliographyXMLHandler.__setBookAtts(bpb, attributes);
        return;
      }

      if (BibliographyXML.ELEMENT_THESIS.equalsIgnoreCase(localName)) {
        this.m_builders.add(//
            btb = ((BibliographyBuilder) (bfsm)).thesis());
        BibliographyXMLHandler.__setBookAtts(btb, attributes);
        if ((s = DelegatingHandler.getAttributeNormalized(attributes,
            BibliographyXML.NAMESPACE, BibliographyXML.ATTR_THESIS_TYPE)) != null) {
          for (i = BibliographyXML.VAL_THESIS_TYPES.length; (--i) >= 0;) {
            if (BibliographyXML.VAL_THESIS_TYPES[i].equalsIgnoreCase(s)) {
              btb.setType(EThesisType.TYPES.get(i));
              return;
            }
          }
        }
        return;
      }

      if (BibliographyXML.ELEMENT_WEBSITE.equalsIgnoreCase(localName)) {
        this.m_builders.add(//
            bwb = ((BibliographyBuilder) (bfsm)).website());
        BibliographyXMLHandler.__setRecordAtts(bwb, attributes);
        return;
      }

      if (BibliographyXML.ELEMENT_TECH_REPORT.equalsIgnoreCase(localName)) {
        this.m_builders.add(//
            btrb = ((BibliographyBuilder) (bfsm)).techReport());
        BibliographyXMLHandler.__setRecordAtts(btrb, attributes);

        if ((s = DelegatingHandler.getAttributeNormalized(attributes,
            BibliographyXML.NAMESPACE, BibliographyXML.ATTR_SERIES)) != null) {
          btrb.setSeries(s);
        }
        if ((s = DelegatingHandler.getAttributeNormalized(attributes,
            BibliographyXML.NAMESPACE, BibliographyXML.ATTR_NUMBER)) != null) {
          btrb.setNumber(s);
        }
        if ((s = DelegatingHandler.getAttributeNormalized(attributes,
            BibliographyXML.NAMESPACE, BibliographyXML.ATTR_ISSN)) != null) {
          btrb.setISSN(s);
        }
      }

      if (BibliographyXML.ELEMENT_IN_COLLECTION
          .equalsIgnoreCase(localName)) {
        this.m_builders.add(//
            bicb = ((BibliographyBuilder) (bfsm)).inCollection());
        BibliographyXMLHandler.__setInBookAtts(bicb, attributes);
        return;
      }

      if (BibliographyXML.ELEMENT_IN_PROCEEDINGS
          .equalsIgnoreCase(localName)) {
        this.m_builders.add(//
            bipb = ((BibliographyBuilder) (bfsm)).inProceedings());
        BibliographyXMLHandler.__setInBookAtts(bipb, attributes);
        return;
      }

      if (BibliographyXML.ELEMENT_DATE.equalsIgnoreCase(localName)) {

        if (bfsm instanceof BibArticleBuilder) {
          bdb = ((BibArticleBuilder) bfsm).date();
        } else {
          if (bfsm instanceof BibBookBuilder) {
            bdb = ((BibBookBuilder) bfsm).date();
          } else {
            if (bfsm instanceof BibTechReportBuilder) {
              bdb = ((BibTechReportBuilder) bfsm).date();
            } else {
              if (bfsm instanceof BibWebsiteBuilder) {
                bdb = ((BibWebsiteBuilder) bfsm).date();
              } else {
                if (bfsm instanceof BibThesisBuilder) {
                  bdb = ((BibThesisBuilder) bfsm).date();
                } else {
                  bdb = null;
                }
              }
            }
          }
        }

        this.m_builders.add(bdb);
        BibliographyXMLHandler.__setDateAtts(bdb, attributes);

        return;
      }

      if (BibliographyXML.ELEMENT_START_DATE.equalsIgnoreCase(localName)) {
        this.m_builders.add(bdb = ((BibProceedingsBuilder) bfsm)
            .startDate());
        BibliographyXMLHandler.__setDateAtts(bdb, attributes);
        return;
      }

      if (BibliographyXML.ELEMENT_END_DATE.equalsIgnoreCase(localName)) {
        this.m_builders
        .add(bdb = ((BibProceedingsBuilder) bfsm).endDate());
        BibliographyXMLHandler.__setDateAtts(bdb, attributes);
        return;
      }

      if (BibliographyXML.ELEMENT_AUTHORS.equalsIgnoreCase(localName)) {
        this.m_builders.add(((BibRecordBuilder) bfsm).setAuthors());
        return;
      }

      if (BibliographyXML.ELEMENT_EDITORS.equalsIgnoreCase(localName)) {
        this.m_builders.add(((BibBookRecordBuilder) bfsm).setEditors());
        return;
      }

      if (BibliographyXML.ELEMENT_PERSON.equalsIgnoreCase(localName)) {
        this.m_builders.add(bppb = ((BibAuthorsBuilder) bfsm).author());
        BibliographyXMLHandler.__setAuthorAtts(bppb, attributes);
        return;
      }

      if (BibliographyXML.ELEMENT_PUBLISHER.equalsIgnoreCase(localName)) {
        this.m_builders.add(bob = ((BibRecordWithPublisherBuilder) bfsm)
            .publisher());
        BibliographyXMLHandler.__setPlaceAtts(bob, attributes);
        return;
      }

      if (BibliographyXML.ELEMENT_LOCATION.equalsIgnoreCase(localName)) {
        this.m_builders.add(bob = ((BibProceedingsBuilder) bfsm)
            .location());
        BibliographyXMLHandler.__setPlaceAtts(bob, attributes);
        return;
      }

      if (BibliographyXML.ELEMENT_SCHOOL.equalsIgnoreCase(localName)) {
        this.m_builders.add(bob = ((BibThesisBuilder) bfsm).school());
        BibliographyXMLHandler.__setPlaceAtts(bob, attributes);
        return;
      }

      if (BibliographyXML.ELEMENT_INSTITUTION.equalsIgnoreCase(localName)) {
        this.m_builders.add(bob = ((BibTechReportBuilder) bfsm)
            .publisher());
        BibliographyXMLHandler.__setPlaceAtts(bob, attributes);
        return;
      }
    }
  }

  /**
   * set the default place attributes
   *
   * @param rec
   *          the place
   * @param atts
   *          the attributes
   */
  private static final void __setPlaceAtts(
      final BibOrganizationBuilder rec, final Attributes atts) {
    String s;

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_ADDRESS)) != null) {
      rec.setAddress(s);
    }

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_ORGANIZATION)) != null) {
      rec.setName(s);
    }

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE,
        BibliographyXML.ATTR_PLACE_ORIGINAL_SPELLING)) != null) {
      rec.setOriginalSpelling(s);
    }

  }

  /**
   * set the default author attributes
   *
   * @param rec
   *          the author
   * @param atts
   *          the attributes
   */
  private static final void __setAuthorAtts(final BibAuthorBuilder rec,
      final Attributes atts) {
    String s;

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_FAMILY_NAME)) != null) {
      rec.setFamilyName(s);
    }

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_PERSONAL_NAME)) != null) {
      rec.setPersonalName(s);
    }

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE,
        BibliographyXML.ATTR_NAME_ORIGINAL_SPELLING)) != null) {
      rec.setOriginalSpelling(s);
    }

  }

  /**
   * set the default record attributes
   *
   * @param rec
   *          the record
   * @param atts
   *          the attributes
   */
  private static final void __setRecordAtts(final BibRecordBuilder rec,
      final Attributes atts) {
    String s;

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_DOI)) != null) {
      rec.setDOI(s);
    }

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_URL)) != null) {
      rec.setURL(s);
    }

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_TITLE)) != null) {
      rec.setTitle(s);
    }
  }

  /**
   * set the default in-book attributes
   *
   * @param rec
   *          the record
   * @param atts
   *          the attributes
   */
  private static final void __setInBookAtts(final BibInBookBuilder rec,
      final Attributes atts) {
    String s;

    BibliographyXMLHandler.__setRecordAtts(rec, atts);

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_START_PAGE)) != null) {
      rec.setStartPage(s);
    }

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_END_PAGE)) != null) {
      rec.setEndPage(s);
    }

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_CHAPTER)) != null) {
      rec.setChapter(s);
    }
  }

  /**
   * set the default date attributes
   *
   * @param rec
   *          the date builder
   * @param atts
   *          the attributes
   */
  private static final void __setDateAtts(final BibDateBuilder rec,
      final Attributes atts) {
    String s;
    int i;

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_YEAR)) != null) {
      rec.setYear(s);
    }

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_MONTH)) != null) {

      for (i = BibliographyXML.VAL_MONTHS.length; (--i) >= 0;) {
        if (BibliographyXML.VAL_MONTHS[i].equalsIgnoreCase(s)) {
          rec.setMonth(EBibMonth.MONTHS.get(i));
          break;
        }
      }
      if ((s = DelegatingHandler.getAttributeNormalized(atts,
          BibliographyXML.NAMESPACE, BibliographyXML.ATTR_DAY)) != null) {
        rec.setDay(s);
      }
    } else {

      if ((s = DelegatingHandler.getAttributeNormalized(atts,
          BibliographyXML.NAMESPACE, BibliographyXML.ATTR_QUARTER)) != null) {

        for (i = BibliographyXML.VAL_QUARTERS.length; (--i) >= 0;) {
          if (BibliographyXML.VAL_QUARTERS[i].equalsIgnoreCase(s)) {
            rec.setQuarter(EBibQuarter.QUARTERS.get(i));
            break;
          }
        }

      }
    }

  }

  /**
   * set the default book attributes
   *
   * @param rec
   *          the book
   * @param atts
   *          the attributes
   */
  private static final void __setBookAtts(final BibBookRecordBuilder rec,
      final Attributes atts) {
    String s;

    BibliographyXMLHandler.__setRecordAtts(rec, atts);

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_EDITION)) != null) {
      rec.setEdition(s);
    }

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_VOLUME)) != null) {
      rec.setVolume(s);
    }

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_SERIES)) != null) {
      rec.setSeries(s);
    }

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_ISBN)) != null) {
      rec.setISBN(s);
    }

    if ((s = DelegatingHandler.getAttributeNormalized(atts,
        BibliographyXML.NAMESPACE, BibliographyXML.ATTR_ISSN)) != null) {
      rec.setISSN(s);
    }

  }

  /** enforce closing */
  private final void __close() {
    int s;
    for (s = this.m_builders.size(); (--s) >= 0;) {
      this.m_builders.remove(s).close();
    }
    this.close();
  }

  /** {@inheritDoc} */
  @Override
  protected final void doEndElement(final String uri,
      final String localName, final String qName) throws SAXException {

    if ((uri == null) || (BibliographyXML.NAMESPACE.equalsIgnoreCase(uri))) {

      if (BibliographyXML.ELEMENT_BIBLIOGRAPHY.equalsIgnoreCase(localName)) {
        this.__close();
      } else {

        if (BibliographyXML.ELEMENT_ARTICLE.equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_BOOK.equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_PROCEEDINGS
            .equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_THESIS.equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_WEBSITE.equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_TECH_REPORT
            .equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_IN_COLLECTION
            .equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_IN_PROCEEDINGS
            .equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_DATE.equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_START_DATE.equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_END_DATE.equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_AUTHORS.equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_EDITORS.equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_PERSON.equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_PUBLISHER.equalsIgnoreCase(localName)
            || //
            BibliographyXML.ELEMENT_LOCATION.equalsIgnoreCase(localName) || //
            BibliographyXML.ELEMENT_SCHOOL.equalsIgnoreCase(localName) || //
            BibliographyXML.ELEMENT_INSTITUTION
            .equalsIgnoreCase(localName)) {
          this.m_builders.remove(this.m_builders.size() - 1).close();
        }

        if (this.m_builders.isEmpty()) {
          this.close();
        }
      }
    } else {
      this.__close();
    }
  }
}
