package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.experimentation.data.Property;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A set of property value range groups.
 * 
 * @param <DT>
 *          the data element type
 */
public class ValueRangeGroups<DT extends DataElement> extends
    PropertyValueGroups<DT> {

  /**
   * create the property value groups
   * 
   * @param property
   *          the property
   * @param mode
   *          the grouping mode
   * @param info
   *          the grouping info
   * @param groups
   *          the groups
   * @param unspecified
   *          a group holding all elements for which the property value was
   *          unspecified, or {@code null} if no such elements exist
   */
  ValueRangeGroups(final Property<?> property, final EGroupingMode mode,
      final Object info, final ValueRangeGroup<?, DT>[] groups,
      final UnspecifiedValueGroup<DT> unspecified) {
    super(property, mode, info, groups, unspecified);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ArraySetView<? extends ValueRangeGroup<?, DT>> getGroups() {
    return ((ArraySetView) (this.m_data));
  }
}
