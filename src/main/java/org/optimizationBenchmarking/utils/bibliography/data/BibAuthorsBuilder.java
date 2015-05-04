package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/** A builder for author objects. */
public final class BibAuthorsBuilder extends BuilderFSM<BibAuthors> {

  /** the tag */
  final int m_tag;

  /** the authors list */
  private BibAuthor[] m_list;

  /** the size */
  private int m_size;

  /** create the authors builder */
  public BibAuthorsBuilder() {
    this(null);
  }

  /**
   * create the authors builder
   *
   * @param owner
   *          the owner
   */
  public BibAuthorsBuilder(final HierarchicalFSM owner) {
    this(owner, 0);
  }

  /**
   * create the author builder
   *
   * @param tag
   *          the tag
   * @param owner
   *          the owner
   */
  BibAuthorsBuilder(final HierarchicalFSM owner, final int tag) {
    super(owner);
    this.m_tag = tag;
    this.m_list = new BibAuthor[8];
    this.open();
  }

  /**
   * Add a given author to this author list
   *
   * @param author
   *          the author to add
   */
  public final void addAuthor(final BibAuthor author) {
    final int oldSize;
    BibAuthor[] data;
    int i;

    if (author == null) {
      throw new IllegalArgumentException("Author to add cannot be null."); //$NON-NLS-1$
    }

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    data = this.m_list;
    oldSize = this.m_size;

    for (i = 0; i < oldSize; i++) {
      if (EComparison.equals(data[i], author)) {
        throw new IllegalArgumentException(//
            "An author set cannot contain two equal elements, so you cannot add element '" //$NON-NLS-1$
                + author + "', which already exists at index " //$NON-NLS-1$
                + i);
      }
    }

    if (oldSize >= data.length) {
      data = new BibAuthor[(oldSize + 1) << 1];
      System.arraycopy(this.m_list, 0, data, 0, oldSize);
      this.m_list = data;
    }

    data[oldSize] = author;
    this.m_size = (oldSize + 1);
  }

  /**
   * Add a set of authors
   *
   * @param authors
   *          the authors to add
   */
  public final void addAuthors(final Iterable<BibAuthor> authors) {
    if (authors == null) {
      throw new IllegalArgumentException("Authors to add cannot be null."); //$NON-NLS-1$
    }

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    for (final BibAuthor author : authors) {
      this.addAuthor(author);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final BibAuthors compile() {
    final int len;
    BibAuthor[] data, d2;

    data = this.m_list;
    this.m_list = null;
    len = this.m_size;
    this.m_size = (-1);
    if (len <= 0) {
      return BibAuthors.EMPTY_AUTHORS;
    }
    if (data.length != len) {
      d2 = new BibAuthor[len];
      System.arraycopy(data, 0, d2, 0, len);
      data = d2;
    }
    return new BibAuthors(false, data);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    super.beforeChildOpens(child, hasOtherChildren);
    if (!(child instanceof BibAuthorBuilder)) {
      this.throwChildNotAllowed(child);
    }
  }

  /**
   * Create a new author builder
   *
   * @return the new author builder
   */
  public final BibAuthorBuilder author() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibAuthorBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {
    super.afterChildClosed(child);

    if (child instanceof BibAuthorBuilder) {
      this.addAuthor((((BibAuthorBuilder) child).getResult()));
    } else {
      this.throwChildNotAllowed(child);
    }
  }
}
