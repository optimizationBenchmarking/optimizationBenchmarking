package org.optimizationBenchmarking.experimentation.data;

import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * An internal set of id objects.
 * 
 * @param <DT>
 *          the type
 */
abstract class _IDObjectSet<DT extends _IDObject> extends DataSet<DT> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * instantiate
   * 
   * @param data
   *          the data of the set
   * @param clone
   *          should we clone the data?
   * @param sort
   *          should we sort the data?
   * @param own
   *          should we make all elements our own?
   */
  _IDObjectSet(final DT[] data, final boolean clone, final boolean sort,
      final boolean own) {
    super(data, clone, sort, own);
  }

  /** {@inheritDoc} */
  @Override
  final void _setOwnerOfElement(final DT element, final int index) {
    if (element.m_owner != null) {
      throw new IllegalStateException(((((//
          "Owner of element '" + element) + //$NON-NLS-1$
          "' should not yet been set, but is '") + element.m_owner) //$NON-NLS-1$
          + '\'') + '.');
    }

    if (element.m_id != (-1)) {
      throw new IllegalStateException((((//
          "ID of element '" + element) + //$NON-NLS-1$
          "' should not yet been set, but is ") + element.m_id) //$NON-NLS-1$
          + '.');
    }

    element.m_id = index;
    ((_IDObject) element).m_owner = this;
  }

  /** {@inheritDoc} */
  @Override
  final void _validateSingleElement(final DT element, final int index,
      final boolean shouldWeBeOwner) {
    super._validateSingleElement(element, index, shouldWeBeOwner);

    if (element.m_owner == null) {
      throw new IllegalStateException((//
          "Element '" + element) + //$NON-NLS-1$
          "' must have a valid owner, but has owner 'null'."); //$NON-NLS-1$
    }

    if (element.m_id < 0) {
      throw new IllegalStateException((((//
          "Element '" + element) + //$NON-NLS-1$
          "' must have a valid ID (>=0), but has ID ") //$NON-NLS-1$
          + element.m_id) + '.');
    }

    if (shouldWeBeOwner) {
      if (element.m_owner != this) {
        throw new IllegalStateException(((((//
            "Element '" + element) + //$NON-NLS-1$
            "' should be owned by this object but is owned by '") //$NON-NLS-1$
            + element.m_owner) + '\'') + '.');
      }

      if (element.m_id != index) {
        throw new IllegalStateException("Element '" + element + //$NON-NLS-1$
            "' should be at index " + index + //$NON-NLS-1$
            ", but is at index " + element.m_id); //$NON-NLS-1$
      }
    } else {
      if (element.m_owner == this) {
        throw new IllegalStateException((//
            "Element '" + element) + //$NON-NLS-1$
            "' should be not owned by this object but is owned by this object."); //$NON-NLS-1$
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  void _validateElementPair(final DT before, final DT after) {
    super._validateElementPair(before, after);

    final String nameA, nameB;

    if (before.m_id >= after.m_id) {
      if (before.m_id > after.m_id) {
        throw new IllegalArgumentException((((((((//
            "In an instance of " + //$NON-NLS-1$
                TextUtils.className(this.getClass()) + //
                ", an element with lower ID cannot follow an element with higher ID, but '" + //$NON-NLS-1$
            before) + "' with ID ") + //$NON-NLS-1$
            before.m_id) + " follows '") + //$NON-NLS-1$
            after) + "' with ID ") + after.m_id) + '.'); //$NON-NLS-1$
      }

      if (before.m_owner == after.m_owner) {
        throw new IllegalArgumentException(((((((((((//
            "In an instance of " + //$NON-NLS-1$
                TextUtils.className(this.getClass()) + //
                ", if two elements have the same ID, they cannot have the same owner, but '" + //$NON-NLS-1$
            before) + "' with ID ") + //$NON-NLS-1$
            before.m_id) + " follows '") + //$NON-NLS-1$
            after) + "' with ID ") + after.m_id) + //$NON-NLS-1$
            " and both have owner '") + after.m_owner) + //$NON-NLS-1$
            '\'') + '.');
      }
    }

    if (before instanceof _NamedIDObject) {
      nameA = ((_NamedIDObject) before).m_name;
    } else {
      if (before instanceof _NamedIDObjectSet) {
        nameA = ((_NamedIDObjectSet) before).m_name;
      } else {
        nameA = null;
      }
    }

    if (nameA != null) {
      if (after instanceof _NamedIDObject) {
        nameB = ((_NamedIDObject) after).m_name;
      } else {
        if (after instanceof _NamedIDObjectSet) {
          nameB = ((_NamedIDObjectSet) after).m_name;
        } else {
          nameB = null;
        }
      }

      if (nameB != null) {

        if (nameA.equals(nameB)) {
          throw new IllegalArgumentException(((((((((//
              "No two elements in an instance of " + //$NON-NLS-1$
              TextUtils.className(this.getClass())) + " can have the same name, but '") + //$NON-NLS-1$
              before) + "' and '") + //$NON-NLS-1$
              after) + "' both have name '") + nameA) + //$NON-NLS-1$
              '\'') + '.');
        }

        if (nameA.equalsIgnoreCase(nameB)) {
          throw new IllegalArgumentException(
              ((((((((("No two elements in an instance of " + //$NON-NLS-1$
                  TextUtils.className(this.getClass())) + " can names that differ only in their case, but '") + //$NON-NLS-1$
                  before) + "' and '") + //$NON-NLS-1$
                  after) + "' have names '") + nameA) + //$NON-NLS-1$
                  "' and '")//$NON-NLS-1$
                  + nameB + '\'') + '.');
        }

      }
    }
  }

  /**
   * Find the element with the given name
   * 
   * @param name
   *          the name
   * @return the element, or {@code null} if it could not be found
   */
  DT find(final String name) {
    String n;
    int i;

    n = name;
    for (i = 3; (--i) >= 0;) {

      if (i == 1) {
        n = TextUtils.prepare(n);
      } else {
        if (i == 0) {
          n = TextUtils.normalize(n);
        }
      }

      if (n == null) {
        return null;
      }

      for (final DT d : this.m_data) {
        if (d.getName().equalsIgnoreCase(n)) {
          return d;
        }
      }
    }

    return null;
  }

}