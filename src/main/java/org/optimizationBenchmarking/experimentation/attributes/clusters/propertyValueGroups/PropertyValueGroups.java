package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.attributes.clusters.IClustering;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A set of property value groups.
 */
public abstract class PropertyValueGroups extends DataElement implements
    IClustering {

  /** the property */
  final IProperty m_property;

  /** the data */
  final ArrayListView<PropertyValueGroup<?>> m_data;

  /** the grouping mode */
  private final EGroupingMode m_groupingMode;

  /**
   * a group holding all elements for which the property value was
   * unspecified, or {@code null} if no such elements exist
   */
  private final UnspecifiedValueGroup m_unspecified;

  /**
   * create the property value groups
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
  @SuppressWarnings({ "unchecked", "rawtypes" })
  PropertyValueGroups(final IProperty property, final _Groups groups,
      final DataSelection unspecified, final Object unspecifiedValue) {
    super();

    final PropertyValueGroup[] list;
    final int size;
    int index;

    if (property == null) {
      throw new IllegalArgumentException(//
          "Property must not be null."); //$NON-NLS-1$
    }
    if (groups == null) {
      throw new IllegalArgumentException(((//
          "Property groups must not be null, but are for property '" //$NON-NLS-1$
          + property.getName()) + '\'') + '.');
    }
    if ((size = groups.m_groups.length) < 1) {
      throw new IllegalArgumentException(((//
          "Property groups must contain at least one group, but do not for property '" //$NON-NLS-1$
          + property.getName()) + '\'') + '.');
    }
    if (groups.m_groupingMode == null) {
      throw new IllegalArgumentException(((//
          "Grouping mode must not be null, but is for property '" //$NON-NLS-1$
          + property.getName()) + '\'') + '.');
    }

    list = new PropertyValueGroup[size];
    index = 0;
    for (final _Group group : groups.m_groups) {
      list[index++] = this._group(group);
    }

    this.m_data = new ArrayListView(list);
    this.m_property = property;
    this.m_groupingMode = groups.m_groupingMode;

    if (unspecified == null) {
      this.m_unspecified = null;
    } else {
      this.m_unspecified = new UnspecifiedValueGroup(this, unspecified,
          unspecifiedValue);
    }
  }

  /**
   * create the group
   *
   * @param group
   *          the group data
   * @return the group
   */
  abstract PropertyValueGroup<?> _group(final _Group group);

  /** {@inheritDoc} */
  @Override
  public final IProperty getOwner() {
    return this.m_property;
  }

  /**
   * Get the group for which the property value is unspecified. For
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Feature
   * features}, this method will always return {@code null}. For
   * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.Parameter
   * parameters}, this method might either return {@code null} (in case
   * that all experiments have this parameter value specified) or a group
   * with the distinct, unspecified value.
   *
   * @return the distinct value group holding all elements for which the
   *         property is not specified, or {@code null} if all elements
   *         have the property value specified
   */
  public final UnspecifiedValueGroup getUnspecifiedGroup() {
    return this.m_unspecified;
  }

  /**
   * Get the grouping mode
   *
   * @return the grouping mode
   */
  public final EGroupingMode getGroupingMode() {
    return this.m_groupingMode;
  }

  /**
   * Get the property groups
   *
   * @return the property groups
   */
  @Override
  public ArrayListView<? extends PropertyValueGroup<?>> getData() {
    return this.m_data;
  }
}
