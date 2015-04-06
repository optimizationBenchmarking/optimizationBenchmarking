package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.experimentation.data.Property;

/**
 * A set of distinct property value groups.
 * 
 * @param <DT>
 *          the data type
 */
public class DistinctValueGroups<DT extends DataElement> extends
    PropertyValueGroups<DT, DistinctValueGroup<DT>> {
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
  DistinctValueGroups(final Property<?> property,
      final EGroupingMode mode, final Object info,
      final DistinctValueGroup<DT>[] groups,
      final UnspecifiedValueGroup<DT> unspecified) {
    super(property, mode, info, groups, unspecified);
  }
}
