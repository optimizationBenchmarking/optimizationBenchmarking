package org.optimizationBenchmarking.utils.collections.lists;

import java.util.Collection;

/**
 * The base class for sub lists
 *
 * @param <ET>
 *          the element type
 * @param <LT>
 *          the list type
 */
final class _BasicSubList<ET, LT extends BasicList<ET>> extends
    BasicList<ET> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the list to access */
  private final LT m_list;

  /** the starting index */
  private final int m_index;

  /** the sublist's size */
  private int m_size;

  /**
   * Instantiate the list base
   *
   * @param list
   *          the list to represent
   * @param index
   *          the starting index
   * @param size
   *          the size value
   */
  _BasicSubList(final LT list, final int index, final int size) {
    super();
    this.m_list = list;
    this.m_index = index;
    this.m_size = size;
  }

  /** {@inheritDoc } */
  @Override
  public final int size() {
    return this.m_size;
  }

  /** {@inheritDoc } */
  @Override
  public final ET get(final int index) {
    BasicList._checkIndexForAccess(index, this.m_size);
    return this.m_list.get(this.m_index + index);
  }

  /** {@inheritDoc } */
  @Override
  public final ET set(final int index, final ET element) {
    BasicList._checkIndexForAccess(index, this.m_size);
    return this.m_list.set((this.m_index + index), element);
  }

  /** {@inheritDoc } */
  @Override
  public final void add(final int index, final ET element) {
    BasicList._checkIndexForAdd(index, this.m_size);
    this.m_list.add((this.m_index + index), element);
    this.m_size++;
  }

  /** {@inheritDoc } */
  @Override
  public final ET remove(final int index) {
    ET z;

    BasicList._checkIndexForAccess(index, this.m_size);
    z = this.m_list.remove(this.m_index + index);
    this.m_size--;

    return z;
  }

  /** {@inheritDoc} */
  @Override
  public final void removeRange(final int fromIndex, final int toIndex) {

    BasicList._checkIndexRangeForAccess(fromIndex, toIndex, this.m_size);

    if (toIndex > fromIndex) {
      this.m_list.removeRange((this.m_index + fromIndex),
          (this.m_index + toIndex));
      this.m_size -= (toIndex - fromIndex);
    }
  }

  /** {@inheritDoc } */
  @Override
  public final boolean addAll(final int index,
      final Collection<? extends ET> c) {
    final int t;

    BasicList._checkIndexForAdd(index, this.m_size);
    t = this.m_list.size();
    if (this.m_list.addAll((this.m_index + index), c)) {
      this.m_size += (this.m_list.size() - t);
      return true;
    }
    return false;
  }
}
