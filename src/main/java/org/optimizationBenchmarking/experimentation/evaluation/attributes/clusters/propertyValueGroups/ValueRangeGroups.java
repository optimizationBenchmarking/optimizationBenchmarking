package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.Property;

/**
 * A set of property value range groups.
 * 
 * @param <VT>
 *          the value type
 * @param <PVG>
 *          the property value group type
 */
public class ValueRangeGroups<VT, PVG extends ValueRangeGroup<VT, ?>>
    extends PropertyValueGroups<PVG> {
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
   */
  ValueRangeGroups(final Property<?> property, final EGroupingMode mode,
      final Object info, final PVG[] groups) {
    super(property, mode, info, groups);
  }
}
