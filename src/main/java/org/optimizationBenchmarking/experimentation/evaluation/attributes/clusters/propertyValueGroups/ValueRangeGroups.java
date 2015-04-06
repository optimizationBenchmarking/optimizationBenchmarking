package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.experimentation.data.Property;

/**
 * A set of property value range groups.
 * 
 * @param <DT>
 *          the data element type
 * @param <VT>
 *          the value type
 * @param <PVG>
 *          the property value group type
 */
public class ValueRangeGroups<VT, DT extends DataElement, PVG extends ValueRangeGroup<VT, DT>>
    extends PropertyValueGroups<DT, PVG> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

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
      final Object info, final PVG[] groups,
      final UnspecifiedValueGroup<DT> unspecified) {
    super(property, mode, info, groups, unspecified);
  }
}
