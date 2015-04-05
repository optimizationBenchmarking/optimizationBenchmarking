package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.experimentation.data.Property;

/**
 * A set of property double range groups.
 * 
 * @param <DT>
 *          the data element type
 */
public class DoubleRangeGroups<DT extends DataElement> extends
    ValueRangeGroups<Number, DoubleRangeGroup<DT>> {
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
  DoubleRangeGroups(final Property<?> property, final EGroupingMode mode,
      final Object info, final DoubleRangeGroup<DT>[] groups) {
    super(property, mode, info, groups);
  }
}
