package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.experimentation.data.Property;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A set of distinct property value groups.
 * 
 * @param <DT>
 *          the data type
 */
public final class DistinctValueGroups<DT extends DataElement> extends
    PropertyValueGroups<DT> {

  /**
   * create the property value groups
   * 
   * @param property
   *          the property
   * @param groups
   *          the groups
   * @param unspecified
   *          a group holding all elements for which the property value was
   *          unspecified, or {@code null} if no such elements exist
   */
  DistinctValueGroups(final Property<?> property,
      final DistinctValueGroup<DT>[] groups,
      final UnspecifiedValueGroup<DT> unspecified) {
    super(property, EGroupingMode.DISTINCT, null, groups, unspecified);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final ArraySetView<? extends DistinctValueGroup<DT>> getGroups() {
    return ((ArraySetView) (this.m_data));
  }
}
