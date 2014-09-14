package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/** A builder for author objects. */
public final class BibAuthorsBuilder extends
    _BibSetBuilder<BibAuthor, BibAuthors> {

  /** the tag */
  final int m_tag;

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
    this.open();
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
   * Add a given author to this author list
   * 
   * @param author
   *          the author to add
   */
  public final void addAuthor(final BibAuthor author) {
    this._add(author, true);
  }

  /**
   * Create a new author builder
   * 
   * @return the new author builder
   */
  public final BibAuthorBuilder addAuthor() {
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

  /** {@inheritDoc} */
  @Override
  final BibAuthor[] _create(final int len) {
    return new BibAuthor[len];
  }

  /** {@inheritDoc} */
  @Override
  final BibAuthors _empty() {
    return BibAuthors.EMPTY_AUTHORS;
  }

  /** {@inheritDoc} */
  @Override
  final BibAuthors _make(final BibAuthor[] data) {
    return new BibAuthors(false, data);
  }
}
