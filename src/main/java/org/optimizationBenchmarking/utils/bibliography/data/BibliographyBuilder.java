package org.optimizationBenchmarking.utils.bibliography.data;

import java.util.HashMap;
import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/** A builder for bibliographies. */
public class BibliographyBuilder extends BuilderFSM<Bibliography> {

  /** the list */
  private LinkedHashSet<BibRecord> m_bibliography;

  /** the data to canonicalize */
  private HashMap<Object, Object> m_data;

  /** the bibliography consumer */
  private IBibliographyConsumer m_consumer;

  /**
   * create the author builder
   *
   * @param owner
   *          the owning fsm
   * @param consumer
   *          the bibliography consumer
   */
  BibliographyBuilder(final BibliographyBuilder owner,
      final IBibliographyConsumer consumer) {
    super(owner);

    if (owner != null) {
      this.m_data = owner.m_data;
      if (consumer == null) {
        throw new IllegalArgumentException(
            "If an owning BibliographyBuilder is set, you also must define a bibliography consumer (i.e., one which is not null)."); //$NON-NLS-1$
      }
    } else {
      this.m_data = new HashMap<>();
    }
    this.m_bibliography = new LinkedHashSet<>();
    this.m_consumer = consumer;
    this.open();
  }

  /** create the author builder */
  public BibliographyBuilder() {
    this(null, null);
  }

  /**
   * Register a bibliography record: Each bibliography record created
   * somewhere in the bibliography
   *
   * @param element
   *          the element to register
   * @param mustClone
   *          do we need to clone the element if it does not exist yet?
   * @return the registered bibliography record
   */
  final BibRecord _register(final BibRecord element,
      final boolean mustClone) {
    final BibRecord old, n;

    if (element == null) {
      throw new IllegalArgumentException("Element cannot be null."); //$NON-NLS-1$
    }
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    synchronized (this.m_data) {
      old = ((BibRecord) (this.m_data.get(element)));
      if (old != null) {
        return old;
      }
      if (mustClone) {
        n = element.clone();
      } else {
        n = element;
      }
      this.m_data.put(n, n);
    }
    return n;
  }

  /**
   * Add a bibliography record
   *
   * @param element
   *          the element to register
   * @param mustClone
   *          do we need to clone?
   * @return the registered bibliography record
   */
  @SuppressWarnings("resource")
  final BibRecord _add(final BibRecord element, final boolean mustClone) {
    BibRecord res;
    BibliographyBuilder owner;

    owner = ((BibliographyBuilder) (this.getOwner()));
    if (owner != null) {
      this.fsmStateAssert(BuilderFSM.STATE_OPEN);
      res = owner._add(element, mustClone);
    } else {
      res = this._register(element, mustClone);
    }

    synchronized (this.m_bibliography) {
      if (this.m_bibliography.add(res)) {
        if (owner == null) {
          res._setID(this.m_bibliography.size() - 1);
        }
      }
    }

    return res;
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    super.beforeChildOpens(child, hasOtherChildren);

    if ((child instanceof BibRecordBuilder)
        || (child instanceof BibliographyBuilder)) {
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (child instanceof BibRecordBuilder) {
      this._add(((BibRecordBuilder) child).getResult(), false);
      return;
    }

    if (child instanceof BibliographyBuilder) {
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /**
   * Add a sub-bibliography: This can be used for building references in a
   * bibliography
   *
   * @param consumer
   *          a non-{@code null} interface to consume bibliographies
   * @return the sub-bibliography builder
   */
  public synchronized final BibliographyBuilder subBibliography(
      final IBibliographyConsumer consumer) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibliographyBuilder(this, consumer);
  }

  /**
   * Build a website record
   *
   * @return the website record builder
   */
  public synchronized final BibWebsiteBuilder website() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibWebsiteBuilder(this);
  }

  /**
   * Build a article record
   *
   * @return the article record builder
   */
  public synchronized final BibArticleBuilder article() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibArticleBuilder(this);
  }

  /**
   * Build a thesis record
   *
   * @return the thesis record builder
   */
  public synchronized final BibThesisBuilder thesis() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibThesisBuilder(this);
  }

  /**
   * Build a technical report
   *
   * @return the technical report record builder
   */
  public synchronized final BibTechReportBuilder techReport() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibTechReportBuilder(this);
  }

  /**
   * Build a book record
   *
   * @return the book record builder
   */
  public synchronized final BibBookBuilder book() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibBookBuilder(this);
  }

  /**
   * Build a proceedings record
   *
   * @return the proceedings record builder
   */
  public synchronized final BibProceedingsBuilder proceedings() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibProceedingsBuilder(this);
  }

  /**
   * Build an in-proceedings record
   *
   * @return the in-proceedings record builder
   */
  public synchronized final BibInProceedingsBuilder inProceedings() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibInProceedingsBuilder(this);
  }

  /**
   * Build an in-collection record
   *
   * @return the in-collection record builder
   */
  public synchronized final BibInCollectionBuilder inCollection() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibInCollectionBuilder(this);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  protected final <T> T doNormalizePersistently(final T input) {
    Object r;
    r = input;
    if (this.m_data != null) {
      synchronized (this.m_data) {
        r = this.m_data.get(r);
        if (r == null) {
          r = input;
          this.m_data.put(r, r);
        }
      }
    }
    return ((T) r);
  }

  /** {@inheritDoc} */
  @Override
  protected final Bibliography compile() {
    final int s;
    final BibRecord[] r;
    final Bibliography bib;

    this.m_data = null;

    synchronized (this.m_bibliography) {
      s = this.m_bibliography.size();
      if (s <= 0) {
        return Bibliography.EMPTY_BIBLIOGRAPHY;
      }
      r = this.m_bibliography.toArray(new BibRecord[s]);
    }
    this.m_bibliography = null;

    bib = new Bibliography(r);
    if (this.m_consumer != null) {
      this.m_consumer.consumeBibliography(bib);
      this.m_consumer = null;
    }

    return bib;
  }

  /**
   * Add a set of bibliographic records
   *
   * @param recs
   *          the set of records to add
   */
  public final void addAll(final Iterable<BibRecord> recs) {
    if (recs != null) {
      for (final BibRecord rec : recs) {
        this._add(rec, true);
      }
    }
  }

  /**
   * Add a set of bibliographic records
   *
   * @param recs
   *          the set of records to add
   */
  public final void addAll(final BibRecord... recs) {
    if (recs != null) {
      for (final BibRecord rec : recs) {
        this._add(rec, true);
      }
    }
  }

  /**
   * Add a bibliographic record
   *
   * @param rec
   *          the record to add
   */
  public final void add(final BibRecord rec) {
    this._add(rec, true);
  }
}