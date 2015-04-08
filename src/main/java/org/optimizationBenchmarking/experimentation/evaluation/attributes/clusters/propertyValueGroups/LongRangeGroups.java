package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.experimentation.data.Property;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A set of property long range groups.
 * 
 * @param <DT>
 *          the data element type
 */
public final class LongRangeGroups<DT extends DataElement> extends
    ValueRangeGroups<DT> {

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
  LongRangeGroups(final Property<?> property, final EGroupingMode mode,
      final Number info, final LongRangeGroup<DT>[] groups,
      final UnspecifiedValueGroup<DT> unspecified) {
    super(property, mode, info, groups, unspecified);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final ArraySetView<? extends LongRangeGroup<DT>> getGroups() {
    return ((ArraySetView) (this.m_data));
  }
}
