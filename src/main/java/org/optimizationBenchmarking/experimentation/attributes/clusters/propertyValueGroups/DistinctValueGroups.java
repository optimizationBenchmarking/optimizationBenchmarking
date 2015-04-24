package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A set of distinct property value groups.
 */
public final class DistinctValueGroups extends PropertyValueGroups {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create the distinct value groups
   * 
   * @param groups
   *          the groups
   * @param property
   *          the property
   * @param unspecified
   *          a group holding all elements for which the property value was
   *          unspecified, or {@code null} if no such elements exist
   * @param unspecifiedValue
   *          the unspecified value
   */
  DistinctValueGroups(final IProperty property, final _Groups groups,
      final DataSelection unspecified, final Object unspecifiedValue) {
    super(property, groups, unspecified, unspecifiedValue);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final ArrayListView<DistinctValueGroup> getGroups() {
    return ((ArrayListView) (this.m_data));
  }

  /** {@inheritDoc} */
  @Override
  final DistinctValueGroup _group(final _Group group) {
    return new DistinctValueGroup(this, group.m_selection, group.m_lower);
  }
}
