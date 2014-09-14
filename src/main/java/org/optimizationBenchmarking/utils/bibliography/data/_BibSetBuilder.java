package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * The internal, abstract base class for builders of sets of bibliographic
 * elements.
 * 
 * @param <DT>
 *          the element type
 * @param <BST>
 *          the bib set type
 */
abstract class _BibSetBuilder<DT extends _BibElement<? super DT>, BST extends _BibSet<DT, ?>>
    extends BuilderFSM<BST> {

  /** the personal name */
  private DT[] m_list;

  /** the size */
  private int m_size;

  /**
   * create the author builder
   * 
   * @param owner
   *          the owner
   */
  _BibSetBuilder(final HierarchicalFSM owner) {
    super(owner);
    this.m_list = this._create(16);
  }

  /**
   * create the array
   * 
   * @param len
   *          the length
   * @return the array
   */
  abstract DT[] _create(final int len);

  /**
   * return the empty bib set
   * 
   * @return the empty bib set
   */
  abstract BST _empty();

  /**
   * instantiate the type
   * 
   * @param data
   *          the data
   * @return the type
   */
  abstract BST _make(final DT[] data);

  /**
   * search for a given element
   * 
   * @param element
   *          the element to find
   * @return the index where it is, or {@code -1} if it could not be found
   */
  int _find(final DT element) {
    final int oldSize;
    final DT[] data;
    int i;

    data = this.m_list;
    oldSize = this.m_size;
    for (i = 0; i < oldSize; i++) {
      if (EComparison.equals(data[i], element)) {
        return i;
      }
    }
    return (-1);
  }

  /**
   * add an element
   * 
   * @param element
   *          the element to add
   * @param throwOnExists
   *          {@code true} to throw an error if an element exists,
   *          {@code false} to simply ignore duplicates
   * @return the index at which the element was added
   */
  final int _add(final DT element, final boolean throwOnExists) {
    final int oldSize;
    DT[] data;
    int i;

    if (element == null) {
      throw new IllegalArgumentException("Element cannot be null."); //$NON-NLS-1$
    }

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    data = this.m_list;
    oldSize = this.m_size;

    for (i = 0; i < oldSize; i++) {
      if (EComparison.equals(data[i], element)) {
        if (throwOnExists) {
          throw new IllegalArgumentException(//
              "A set cannot contain two equal elements, so you cannot add element '" //$NON-NLS-1$
                  + element + "', which already exists at index " //$NON-NLS-1$
                  + i);
        }
        return i;
      }
    }

    if (oldSize >= data.length) {
      data = this._create((oldSize + 1) << 1);
      System.arraycopy(this.m_list, 0, data, 0, oldSize);
      this.m_list = data;
    }

    data[oldSize] = element;
    this.m_size = (oldSize + 1);
    return oldSize;
  }

  /** {@inheritDoc} */
  @Override
  protected final BST compile() {
    final int len;
    DT[] data, d2;

    data = this.m_list;
    this.m_list = null;
    len = this.m_size;
    this.m_size = (-1);
    if (len <= 0) {
      return this._empty();
    }
    if (data.length != len) {
      d2 = this._create(len);
      System.arraycopy(data, 0, d2, 0, len);
      data = d2;
    }
    return this._make(data);
  }

}
