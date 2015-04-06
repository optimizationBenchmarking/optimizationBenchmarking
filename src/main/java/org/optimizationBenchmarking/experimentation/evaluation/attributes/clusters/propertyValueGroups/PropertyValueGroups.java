package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import java.util.Arrays;

import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.experimentation.data.Property;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A set of property value groups.
 * 
 * @param <DT>
 *          the data element type
 * @param <PVG>
 *          the property value group type
 */
public class PropertyValueGroups<DT extends DataElement, PVG extends PropertyValueGroup<DT>>
    extends ArraySetView<PVG> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the property */
  final Property<?> m_property;

  /** the grouping mode */
  private final EGroupingMode m_groupingMode;

  /** the grouping info */
  private final Object m_groupingInfo;

  /**
   * a group holding all elements for which the property value was
   * unspecified, or {@code null} if no such elements exist
   */
  private final UnspecifiedValueGroup<DT> m_unspecified;

  /**
   * create the property value groups
   * 
   * @param groups
   *          the groups
   * @param mode
   *          the grouping mode
   * @param info
   *          the grouping info
   * @param property
   *          the property
   * @param unspecified
   *          a group holding all elements for which the property value was
   *          unspecified, or {@code null} if no such elements exist
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  PropertyValueGroups(final Property<?> property,
      final EGroupingMode mode, final Object info,
      final PropertyValueGroup<DT>[] groups,
      final UnspecifiedValueGroup<DT> unspecified) {
    super((PVG[]) groups);

    if (groups.length < 1) {
      throw new IllegalArgumentException(//
          "There must be at least one group in the value group set of a property."); //$NON-NLS-1$
    }
    if (property == null) {
      throw new IllegalArgumentException(//
          "Property must not be null."); //$NON-NLS-1$
    }
    if (mode == null) {
      throw new IllegalArgumentException(//
          "Grouping mode must not be null."); //$NON-NLS-1$
    }

    for (final PropertyValueGroup p : groups) {
      p.m_owner = this;
    }

    this.m_property = property;
    this.m_groupingMode = mode;
    this.m_groupingInfo = info;
    this.m_unspecified = unspecified;

    if (unspecified != null) {
      unspecified.m_owner = this;
    }
  }

  /**
   * Get the group for which the property value is unspecified. For
   * {@link org.optimizationBenchmarking.experimentation.data.Feature
   * features}, this method will always return {@code null}. For
   * {@link org.optimizationBenchmarking.experimentation.data.Parameter
   * parameters}, this method might either return {@code null} (in case
   * that all experiments have this parameter value specified) or a group
   * with the distinct, unspecified value.
   * 
   * @return the distinct value group holding all elements for which the
   *         property is not specified, or {@code null} if all elements
   *         have the property value specified
   */
  public final UnspecifiedValueGroup<DT> getUnspecifiedGroup() {
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

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_groupingMode),//
            HashUtils.hashCode(this.m_groupingInfo)),//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_property),//
            HashUtils.combineHashes(//
                HashUtils.hashCode(this.m_unspecified),//
                super.calcHashCode())));
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public boolean equals(final Object o) {
    final PropertyValueGroups x;
    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o instanceof PropertyValueGroups) {
      x = ((PropertyValueGroups) o);
      return (EComparison.equals(this.m_property, x.m_property) //
          && EComparison.equals(this.m_groupingMode, x.m_groupingMode)//
          && EComparison.equals(this.m_groupingInfo, x.m_groupingInfo)//
          && EComparison.equals(this.m_unspecified, x.m_unspecified)//
      && Arrays.equals(this.m_data, x.m_data));
    }
    return false;
  }
}
