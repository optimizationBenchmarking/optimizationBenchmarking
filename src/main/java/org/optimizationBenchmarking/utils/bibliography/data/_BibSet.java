package org.optimizationBenchmarking.utils.bibliography.data;

import java.util.Set;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * The internal, abstract base class for sets of bibliographic elements.
 *
 * @param <DT>
 *          the element type
 * @param <CT>
 *          the comparable type
 */
class _BibSet<DT extends _BibElement<? super DT>, CT extends _BibSet<DT, CT>>
extends ArrayListView<DT> implements Set<DT>, Comparable<CT> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * instantiate
   *
   * @param data
   *          the data of the set
   * @param cannotBeEmpty
   *          can the data be empty?
   * @param check
   *          do we need to check for duplicates?
   */
  _BibSet(final boolean cannotBeEmpty, final boolean check, final DT[] data) {
    super(data);

    DT el;
    int i, j;

    if (cannotBeEmpty && (data.length <= 0)) {
      throw new IllegalArgumentException("Elements must not be empty."); //$NON-NLS-1$
    }

    if (check) {
      for (i = data.length; (--i) > 0;) {
        el = data[i];
        if (el == null) {
          throw new IllegalArgumentException(//
              "Elements must not be null, but the one at index " //$NON-NLS-1$
              + i + " is."); //$NON-NLS-1$
        }
        for (j = i; (--j) >= 0;) {
          if (el.equals(data[j])) {
            throw new IllegalArgumentException(//
                "There must not be two equal elements, but elements equal to '" //$NON-NLS-1$
                + el + "' exist at index " + j + //$NON-NLS-1$
                " and " + i); //$NON-NLS-1$
          }
        }
      }
    }
  }

  /**
   * check if the data is the same
   *
   * @param data
   *          the data
   * @param len
   *          the length
   * @return {@code true} if it is the same, {@code false} otherwise
   */
  final boolean _same(final DT[] data, final int len) {
    int i;

    if (len == this.m_data.length) {
      i = 0;
      for (final DT x : this.m_data) {
        if (x != data[i++]) {
          return false;
        }
      }
      return true;
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final CT o) {
    final int l1, l2, l;
    int i, r;

    if (o == null) {
      return (-1);
    }
    if (o == this) {
      return 0;
    }

    l1 = this.m_data.length;
    l2 = o.m_data.length;
    l = Math.min(l1, l2);

    for (i = 0; i < l; i++) {
      r = this.m_data[i].compareTo(o.m_data[i]);
      if (r != 0) {
        return r;
      }
    }

    return (l1 - l2);
  }
}
