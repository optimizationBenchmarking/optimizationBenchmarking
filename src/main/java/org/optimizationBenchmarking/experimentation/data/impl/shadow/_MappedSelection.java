package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.ArrayList;
import java.util.HashMap;

import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;

/**
 * a mapped selection
 *
 * @param <ST>
 *          the set type
 * @param <ET>
 *          the element type
 * @param <SET>
 *          the sub-element type
 */
abstract class _MappedSelection<ST extends IElementSet, ET extends IElementSet, SET extends IDataElement>
    extends _Selection<ST, ET> {

  /** the selection */
  HashMap<ET, Object> m_selection;

  /**
   * create the property selection
   *
   * @param set
   *          the property set to select from
   */
  _MappedSelection(final ST set) {
    super(set);
  }

  /**
   * create a sub-selection
   *
   * @param element
   *          the element
   * @return the selection
   */
  abstract _Selection<ET, SET> _createSelection(final ET element);

  /**
   * Check whether a sub-element can be added
   *
   * @param value
   *          the element to check
   * @return {@code true} if the element can be added, {@code false}
   *         otherwise
   */
  boolean _checkCanAddSub(final SET value) {
    if (value == null) {
      throw new IllegalArgumentException(//
          "Sub-element to add cannot be null."); //$NON-NLS-1$
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  final boolean _add(final ET element) {
    if (this._checkCanAdd(element)) {
      if (this.m_selection == null) {
        this.m_selection = new HashMap<>();
      }
      return (this.m_selection.put(element, element) != element);
    }
    return false;
  }

  /**
   * add a sub-element
   *
   * @param element
   *          the element to add
   * @return {@code true} if something changed because of the addition,
   *         {@code false} otherwise
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  final boolean _addSubElement(final SET element) {
    final ET owner;
    _Selection<ET, SET> set;
    Object current;

    if (this._checkCanAddSub(element)) {
      owner = ((ET) (element.getOwner()));
      if (owner == null) {
        throw new IllegalArgumentException(//
            "Owner of sub-element cannot be null.");//$NON-NLS-1$
      }

      if (owner.getOwner() != this.m_set) {
        throw new IllegalArgumentException(//
            "Element does not belong to the right set."); //$NON-NLS-1$
      }

      if (this.m_selection == null) {
        set = null;
        this.m_selection = new HashMap<>();
      } else {
        current = this.m_selection.get(owner);
        if (current instanceof DataElement) {
          return false;// we already have added the full element
        }
        set = ((_Selection) current);
      }
      if (set == null) {
        set = this._createSelection(owner);
        this.m_selection.put(owner, set);
      }
      return set._add(element);
    }

    return false;
  }

  /**
   * add a set of sub-elements
   *
   * @param elements
   *          the elements
   */
  final void _addSubElements(final Iterable<SET> elements) {
    if (elements == null) {
      throw new IllegalArgumentException(//
          "Sub elements cannot be null."); //$NON-NLS-1$
    }
    for (final SET element : elements) {
      this._addSubElement(element);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  final ST _compile() {
    final ArrayList<ET> list;
    _Selection<ET, SET> selection;
    boolean oneDifferent;
    int size;
    ET comp;

    if ((this.m_selection == null) || //
        ((size = this.m_selection.size()) <= 0)) {
      return null;
    }

    list = new ArrayList<>(size);
    oneDifferent = false;
    for (final Object selected : this.m_selection.values()) {
      if (selected == null) {
        continue;
      }
      if (selected instanceof IDataElement) {
        list.add((ET) selected);
        continue;
      }
      selection = ((_Selection) selected);
      comp = selection._compile();
      if (comp != null) {
        list.add(comp);
        oneDifferent |= (comp != selection.m_set);
      }
    }

    size = list.size();
    if (size <= 0) {
      return null;
    }

    if (oneDifferent || (size < this.m_set.getData().size())) {
      return this._shadow(this.m_set, list);
    }

    return this.m_set;
  }

}
