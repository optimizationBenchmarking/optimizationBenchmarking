package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** A set of property value range groups. */
public class ValueRangeGroups extends PropertyValueGroups {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the grouping parameterrmation */
  private final Number m_parameter;

  /**
   * create the value range groups
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
  ValueRangeGroups(final IProperty property, final _Groups groups,
      final DataSelection unspecified, final Object unspecifiedValue) {
    super(property, groups, unspecified, unspecifiedValue);

    if (groups.m_groupingParameter == null) {
      throw new IllegalArgumentException(//
          "Grouping parameter cannot be null."); //$NON-NLS-1$
    }
    this.m_parameter = groups.m_groupingParameter;
  }

  /** {@inheritDoc} */
  @Override
  final ValueRangeGroup _group(final _Group group) {
    return new ValueRangeGroup(this, group.m_selection,
        ((Number) (group.m_lower)), ((Number) (group.m_upper)),
        group.m_isUpperExclusive);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final ArrayListView<ValueRangeGroup> getGroups() {
    return ((ArrayListView) (this.m_data));
  }

  /**
   * Get the grouping parameter.
   * 
   * @return the grouping parameter.
   */
  public final Number getInfo() {
    return this.m_parameter;
  }
}
