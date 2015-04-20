package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.impl.ref.Property;
import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A set of property double range groups.
 * 
 * @param <DT>
 *          the data element type
 */
public class DoubleRangeGroups<DT extends DataElement> extends
    ValueRangeGroups<DT> {

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
  DoubleRangeGroups(final Property<?> property, final EGroupingMode mode,
      final Number info, final DoubleRangeGroup<DT>[] groups,
      final UnspecifiedValueGroup<DT> unspecified) {
    super(property, mode, info, groups, unspecified);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final ArraySetView<? extends DoubleRangeGroup<DT>> getGroups() {
    return ((ArraySetView) (this.m_data));
  }
}
