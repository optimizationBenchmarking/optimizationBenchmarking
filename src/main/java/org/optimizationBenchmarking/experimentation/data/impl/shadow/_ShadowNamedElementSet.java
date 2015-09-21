package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElementSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A shadow named element set is basically a shadow of another named
 * element set with a different owner and potentially different attributes.
 * If all associated data of this element is the same, it will delegate
 * attribute-based computations to that named element set.
 *
 * @param <OT>
 *          the owner type
 * @param <ST>
 *          the shadow type
 * @param <PT>
 *          the element type
 */
abstract class _ShadowNamedElementSet<OT extends IDataElement, ST extends INamedElementSet, //
PT extends INamedElement> extends //
    _ShadowElementSet<OT, ST, PT> implements INamedElementSet {

  /**
   * create the shadow named element set
   *
   * @param owner
   *          the owning element
   * @param shadow
   *          the named element set to shadow
   * @param selection
   *          the selection of named elements
   */
  _ShadowNamedElementSet(final OT owner, final ST shadow,
      final Collection<? extends PT> selection) {
    super(owner, shadow, selection);
  }

  /** {@inheritDoc} */
  @Override
  public final PT find(final String name) {
    final ArrayListView<? extends PT> list;

    list = this.getData();
    for (final PT property : list) {
      if (property.getName().equals(name)) {
        return property;
      }
    }

    return null;
  }

  /**
   * Compare two lists of named elements
   *
   * @param listA
   *          the first list
   * @param listB
   *          the second list
   * @return {@code true} if they contain the same elements (or shadows
   *         thereof), {@code false} otherwise
   */
  static final boolean _compare(
      final ArrayListView<? extends INamedElement> listA,
      final ArrayListView<? extends INamedElement> listB) {
    final int size;
    int i;
    INamedElement a, b;

    if (listA == listB) {
      return true;
    }
    size = listA.size();
    if (size != listB.size()) {
      return false;
    }

    for (i = size; (--i) >= 0;) {
      a = listA.get(i);
      b = listB.get(i);
      if (a == b) {
        continue;
      }
      if (a.getName().equals(b.getName())) {
        continue;
      }
      return false;
    }

    return true;
  }
}
