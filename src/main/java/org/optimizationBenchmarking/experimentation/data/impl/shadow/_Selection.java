package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;

/**
 * a selection of elements
 *
 * @param <ST>
 *          the set type
 * @param <ET>
 *          the element type
 */
abstract class _Selection<ST extends IElementSet, ET extends IDataElement> {

  /** the set */
  final ST m_set;

  /**
   * create the selection
   *
   * @param set
   *          the set to select from
   */
  _Selection(final ST set) {
    super();

    if (set == null) {
      throw new IllegalArgumentException(//
          "Set to select from must not be null."); //$NON-NLS-1$
    }
    this.m_set = set;
  }

  /**
   * Check whether an element can be added
   *
   * @param value
   *          the element to check
   * @return {@code true} if the element can be added, {@code false}
   *         otherwise
   */
  boolean _checkCanAdd(final ET value) {
    final Object owner;

    if (value == null) {
      throw new IllegalArgumentException(//
          "Element to add cannot be null."); //$NON-NLS-1$
    }

    owner = value.getOwner();
    if (owner == null) {
      throw new IllegalArgumentException(//
          "Element has null owner.");//$NON-NLS-1$
    }
    if (owner != this.m_set) {
      throw new IllegalArgumentException(//
          "Element has the wrong owner."); //$NON-NLS-1$
    }
    return true;
  }

  /**
   * add a value
   *
   * @param value
   *          the value to add
   * @return {@code true} if something changed because of the addition
   */
  abstract boolean _add(final ET value);

  /**
   * add a list of elements
   *
   * @param list
   *          the list
   */
  final void _add(final Iterable<ET> list) {
    if (list == null) {
      throw new IllegalArgumentException(//
          "List to be added cannot be null."); //$NON-NLS-1$
    }
    for (final ET value : list) {
      this._add(value);
    }
  }

  /**
   * create the set
   *
   * @return the compiled set, {@code null} if the compiled set would be
   *         empty can can be omitted, or the {@link #m_set original} if
   *         the set can be shadowed on demand
   */
  abstract ST _compile();

  /**
   * Shadow a given element
   *
   * @param original
   *          the original
   * @param elements
   *          the elements
   * @return the shadow
   */
  abstract ST _shadow(final ST original, final Collection<ET> elements);

}
