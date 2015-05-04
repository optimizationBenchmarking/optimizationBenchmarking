package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.HashSet;

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
abstract class _PlainSelection<ST extends IElementSet, ET extends IDataElement>
    extends _Selection<ST, ET> {

  /** the selection */
  HashSet<ET> m_selection;

  /**
   * create the selection
   *
   * @param set
   *          the set to select from
   */
  _PlainSelection(final ST set) {
    super(set);
  }

  /** {@inheritDoc} */
  @Override
  final boolean _add(final ET value) {
    if (this._checkCanAdd(value)) {
      if (this.m_selection == null) {
        this.m_selection = new HashSet<>();
      }
      return this.m_selection.add(value);
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  final ST _compile() {
    final int size;

    if ((this.m_selection == null)
        || ((size = this.m_selection.size()) <= 0)) {
      return null;
    }

    if (this.m_set.getData().size() <= size) {
      return this.m_set;
    }

    return this._shadow(this.m_set, this.m_selection);
  }
}
